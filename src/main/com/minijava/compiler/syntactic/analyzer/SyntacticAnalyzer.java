package com.minijava.compiler.syntactic.analyzer;

import com.minijava.compiler.lexical.analyzer.LexicalAnalyzer;
import com.minijava.compiler.lexical.exceptions.LexicalException;
import com.minijava.compiler.lexical.models.Token;
import com.minijava.compiler.syntactic.exceptions.SyntacticException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.minijava.compiler.lexical.models.TokenNames.*;
import static com.minijava.compiler.syntactic.analyzer.TokenGroups.*;

public class SyntacticAnalyzer {
    private LexicalAnalyzer lexicalAnalyzer;
    private Token currentToken;
    private List<Exception> exceptions = new ArrayList<>();

    public SyntacticAnalyzer(LexicalAnalyzer lexicalAnalyzer) {
        this.lexicalAnalyzer = lexicalAnalyzer;
    }

    private void advanceCurrentToken() throws IOException {
        boolean errorOccurred;
        do {
            try {
                currentToken = lexicalAnalyzer.nextToken();
                errorOccurred = false;
            } catch (LexicalException exception) {
                exceptions.add(exception);
                errorOccurred = true;
            }
        } while (errorOccurred);
    }

    public void analyze() throws IOException {
        advanceCurrentToken();
        initialNT();
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }

    private boolean canMatch(String tokenName) {
        return tokenName.equals(currentToken.getName());
    }

    private boolean canMatch(Set<String> tokenNames) {
        return tokenNames.contains(currentToken.getName());
    }

    private SyntacticException buildException(String expectedTokenName) {
        return new SyntacticException(currentToken, expectedTokenName, lexicalAnalyzer.getLexemeStartLine(),
                lexicalAnalyzer.getLexemeStartPosition());
    }

    private void match(String expectedTokenName) throws SyntacticException, IOException {
        if (expectedTokenName.equals(currentToken.getName())) {
            advanceCurrentToken();
        } else {
            throw buildException(expectedTokenName);
        }
    }

    private void matchIfPossible(String expectedTokenName) throws IOException {
        if (expectedTokenName.equals(currentToken.getName())) {
            advanceCurrentToken();
        }
    }

    private void matchCurrent() throws IOException {
        advanceCurrentToken();
    }

    private void recover(Set<String> recoveryTokens) throws IOException {
        while (!canMatch(recoveryTokens)) {
            advanceCurrentToken();
        }
    }

    private void initialNT() throws IOException {
        try {
            classesListNT();
            match(EOF);
        } catch (SyntacticException exception) {
            exceptions.add(exception);
            recover(RECOVERY_INITIAL);
            matchIfPossible(EOF);
        }
    }

    private void classesListNT() throws SyntacticException, IOException {
        classNT();
        classesListSuffixOrEmptyNT();
    }

    private void classesListSuffixOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(CLASS_KW)) {
            classesListNT();
        }
    }

    private void classNT() throws IOException {
        try {
            match(CLASS_KW);
            match(CLASS_ID);
            inheritanceOrEmptyNT();
            match(OPEN_BRACE);
            membersListOrEmptyNT();
            match(CLOSE_BRACE);
        } catch (SyntacticException exception) {
            exceptions.add(exception);
            recover(RECOVERY_CLASS);
            matchIfPossible(CLOSE_BRACE);
        }
    }

    private void inheritanceOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(EXTENDS_KW)) {
            matchCurrent();
            match(CLASS_ID);
        }
    }

    private void membersListOrEmptyNT() throws IOException {
        if (canMatch(FIRST_MEMBER)) {
            memberNT();
            membersListOrEmptyNT();
        }
    }

    private void memberNT() throws IOException {
        if (canMatch(FIRST_ATTRIBUTE)) {
            attributeNT();
        } else if (canMatch(CLASS_ID)) {
            constructorNT();
        } else if (canMatch(FIRST_METHOD)) {
            methodNT();
        } else {
            throw new IllegalStateException(); // TODO: para estados no alcanzables no tiene sentido crear una excepción y un mensaje de error particular, tiro IllegalStateExc?
        }
    }

    private void attributeNT() throws IOException {
        try {
            visibilityNT();
            typeNT();
            attrsDecListNT();
            match(SEMICOLON);
        } catch (SyntacticException exception) {
            exceptions.add(exception);
            recover(RECOVERY_ATTRIBUTE);
            matchIfPossible(SEMICOLON);
        }
    }

    private void visibilityNT() throws IOException {
        if (canMatch(FIRST_VISIBILITY)) { // TODO: para el árbol sintáctico hace falta diferenciar entre los primeros de un NT? matchear sin mirar exactamente cuál es no molesta?
            matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private void typeNT() throws SyntacticException, IOException {
        if (canMatch(FIRST_PRIMITIVE_TYPE)) {
            primitiveTypeNT();
        } else if (canMatch(CLASS_ID)) {
            matchCurrent();
        } else {
            throw buildException(TYPE);
        }
    }

    private void primitiveTypeNT() throws IOException {
        if (canMatch(FIRST_PRIMITIVE_TYPE)) {
            matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private void attrsDecListNT() throws SyntacticException, IOException {
        match(VAR_MET_ID);
        attrsDecListSuffixOrEmptyNT();
    }

    private void attrsDecListSuffixOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(COMMA)) {
            matchCurrent();
            attrsDecListNT();
        }
    }

    private void constructorNT() throws IOException {
        try {
            match(CLASS_ID);
            formalArgsNT();
            blockNT();
        } catch (SyntacticException exception) {
            exceptions.add(exception);
            recover(RECOVERY_CONSTRUCTOR);
            matchIfPossible(CLOSE_BRACE);
        }
    }

    private void formalArgsNT() throws SyntacticException, IOException {
        match(OPEN_PARENTHESIS);
        formalArgsListOrEmptyNT();
        match(CLOSE_PARENTHESIS);
    }

    private void formalArgsListOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(FIRST_FORMAL_ARGS)) {
            formalArgsListNT();
        }
    }

    private void formalArgsListNT() throws SyntacticException, IOException {
        formalArgNT();
        formalArgsListSuffixOrEmptyNT();
    }

    private void formalArgsListSuffixOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(COMMA)) {
            matchCurrent();
            formalArgsListNT();
        }
    }

    private void formalArgNT() throws SyntacticException, IOException {
        typeNT();
        match(VAR_MET_ID);
    }

    private void methodNT() throws IOException {
        try {
            methodFormNT();
            methodTypeNT();
            match(VAR_MET_ID);
            formalArgsNT();
            blockNT();
        } catch (SyntacticException exception) {
            exceptions.add(exception);
            recover(RECOVERY_METHOD);
            matchIfPossible(CLOSE_BRACE); // TODO: está bien la estrategia de: tokens de recuperación son el último de cada NT, encuentro uno pero solo matcheo si es el mío, sin tirar excepción, total no pueden continuar?
        }
    }

    private void methodFormNT() throws IOException {
        if (canMatch(FIRST_METHOD_FORM)) {
            matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private void methodTypeNT() throws SyntacticException, IOException {
        if (canMatch(FIRST_TYPE)) {
            typeNT();
        } else if (canMatch(VOID_KW)) {
            matchCurrent();
        } else {
            throw buildException(METHOD_TYPE);
        }
    }

    private void blockNT() throws IOException {
        try {
            match(OPEN_BRACE);
            sentencesListOrEmptyNT();
            match(CLOSE_BRACE);
        } catch (SyntacticException exception) {
            exceptions.add(exception);
            recover(RECOVERY_BLOCK);
            matchIfPossible(CLOSE_BRACE);
        }
    }

    private void sentencesListOrEmptyNT() throws IOException {
        if (canMatch(FIRST_SENTENCE)) {
            sentenceNT();
            sentencesListOrEmptyNT();
        }
    }

    private void sentenceNT() throws IOException {
        try {
            if (canMatch(SEMICOLON)) {
                matchCurrent();
            } else if (canMatch(FIRST_CALL_OR_ASSIGNMENT)) {
                callOrAssignmentNT();
            } else if (canMatch(FIRST_DECLARATION)) {
                typeNT();
                varsDecListNT();
                match(SEMICOLON);
            } else if (canMatch(IF_KW)) { // TODO: hace falta recuperar expresiones? qué es lo esperado si if () { } else { }? En mi caso (recupero todas las sentencias con ; o con } del bloque que contiene) la llave del if se usa para el bloque que los contiene y la del else para el siguiente bloque (poco intuitivo)
                matchCurrent();
                match(OPEN_PARENTHESIS);
                expressionNT();
                match(CLOSE_PARENTHESIS);
                sentenceNT();
                elseOrEmptyNT();
            } else if (canMatch(WHILE_KW)) {
                matchCurrent();
                match(OPEN_PARENTHESIS);
                expressionNT();
                match(CLOSE_PARENTHESIS);
                sentenceNT();
            } else if (canMatch(OPEN_BRACE)) {
                blockNT();
            } else if (canMatch(RETURN_KW)) {
                matchCurrent();
                expressionOrEmptyNT();
                match(SEMICOLON);
            } else {
                throw new IllegalStateException();
            }
        } catch (SyntacticException exception) {
            exceptions.add(exception);
            recover(RECOVERY_SENTENCE);
            matchIfPossible(SEMICOLON);
        }
    }

    private void varsDecListNT() throws SyntacticException, IOException {
        match(VAR_MET_ID);
        varsDecListSuffixOrEmptyNT();
    }

    private void varsDecListSuffixOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(COMMA)) {
            matchCurrent();
            varsDecListNT();
        }
    }

    private void callOrAssignmentNT() throws SyntacticException, IOException {
        accessNT();
        assignmentOrSentenceEndNT();
    }

    private void assignmentOrSentenceEndNT() throws SyntacticException, IOException {
        if (canMatch(FIRST_ASSIGNMENT_TYPE)) {
            assignmentTypeNT();
            expressionNT();
            match(SEMICOLON);
        } else if (canMatch(SEMICOLON)) {
            matchCurrent();
        } else {
            throw buildException(ASSIGNMENT_OR_SENTENCE_END);
        }
    }

    private void assignmentTypeNT() throws IOException {
        if (canMatch(FIRST_ASSIGNMENT_TYPE)) {
            matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private void elseOrEmptyNT() throws IOException {
        if (canMatch(ELSE_KW)) {
            matchCurrent();
            sentenceNT();
        }
    }

    private void expressionOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(FIRST_EXPRESSION)) {
            expressionNT();
        }
    }

    private void expressionNT() throws SyntacticException, IOException {
        unaryExpressionNT();
        expressionSuffixOrEmptyNT();
    }

    private void unaryExpressionNT() throws SyntacticException, IOException {
        if (canMatch(FIRST_UNARY_OPERATOR)) {
            unaryOperatorNT();
            operandNT();
        } else if (canMatch(FIRST_OPERAND)) {
            operandNT();
        } else {
            throw buildException(EXPRESSION);
        }
    }

    private void unaryOperatorNT() throws IOException {
        if (canMatch(FIRST_UNARY_OPERATOR)) {
            matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private void operandNT() throws SyntacticException, IOException {
        if (canMatch(FIRST_LITERAL)) {
            literalNT();
        } else if (canMatch(FIRST_ACCESS)) {
            accessNT();
        } else {
            throw buildException(OPERAND);
        }
    }

    private void literalNT() throws IOException {
        if (canMatch(FIRST_LITERAL)) {
            matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private void expressionSuffixOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(FIRST_BINARY_OPERATOR)) {
            binaryOperatorNT();
            unaryExpressionNT();
            expressionSuffixOrEmptyNT();
        }
    }

    private void binaryOperatorNT() throws IOException {
        if (canMatch(FIRST_BINARY_OPERATOR)) {
            matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private void accessNT() throws SyntacticException, IOException {
        primaryAccessNT();
        chainedAccessOrEmptyNT();
    }

    private void primaryAccessNT() throws SyntacticException, IOException {
        if (canMatch(THIS_KW)) {
            thisAccessNT();
        } else if (canMatch(VAR_MET_ID)) {
            varMetAccessNT();
        } else if (canMatch(STATIC_KW)) {
            staticAccessNT();
        } else if (canMatch(NEW_KW)) {
            constructorAccessNT();
        } else if (canMatch(OPEN_PARENTHESIS)) {
            matchCurrent();
            expressionNT();
            match(CLOSE_PARENTHESIS);
        } else {
            throw new IllegalStateException();
        }
    }

    private void thisAccessNT() throws SyntacticException, IOException {
        match(THIS_KW);
    }

    private void varMetAccessNT() throws SyntacticException, IOException {
        match(VAR_MET_ID);
        actualArgsOrEmptyNT();
    }

    private void actualArgsOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(OPEN_PARENTHESIS)) {
            actualArgsNT();
        }
    }

    private void actualArgsNT() throws SyntacticException, IOException {
        match(OPEN_PARENTHESIS);
        expressionsListOrEmptyNT();
        match(CLOSE_PARENTHESIS);
    }

    private void expressionsListOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(FIRST_EXPRESSION)) {
            expressionsListNT();
        }
    }

    private void expressionsListNT() throws SyntacticException, IOException {
        expressionNT();
        expressionsListSuffixOrEmpty();
    }

    private void expressionsListSuffixOrEmpty() throws SyntacticException, IOException {
        if (canMatch(COMMA)) {
            matchCurrent();
            expressionsListNT();
        }
    }

    private void staticAccessNT() throws SyntacticException, IOException {
        match(STATIC_KW);
        match(CLASS_ID);
        match(DOT);
        methodAccessNT();
    }

    private void methodAccessNT() throws SyntacticException, IOException {
        match(VAR_MET_ID);
        actualArgsNT();
    }

    private void constructorAccessNT() throws SyntacticException, IOException {
        match(NEW_KW);
        match(CLASS_ID);
        actualArgsNT();
    }

    private void chainedAccessOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(DOT)) {
            varMetChainedNT();
            chainedAccessOrEmptyNT();
        }
    }

    private void varMetChainedNT() throws SyntacticException, IOException {
        match(DOT);
        match(VAR_MET_ID);
        actualArgsOrEmptyNT();
    }
}
