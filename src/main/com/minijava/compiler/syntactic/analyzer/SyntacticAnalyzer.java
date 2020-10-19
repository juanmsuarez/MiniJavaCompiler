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
    private Token currentToken, nextToken;
    private String currentLexemeStartLine, nextLexemeStartLine;
    private int currentLexemeStartPosition, nextLexemeStartPosition;
    private List<Exception> exceptions = new ArrayList<>();

    public SyntacticAnalyzer(LexicalAnalyzer lexicalAnalyzer) {
        this.lexicalAnalyzer = lexicalAnalyzer;
    }

    private Token nextValidToken() throws IOException {
        Token nextValidToken = null;

        do {
            try {
                nextValidToken = lexicalAnalyzer.nextToken();
            } catch (LexicalException exception) {
                exceptions.add(exception);
            }
        } while (nextValidToken == null);

        return nextValidToken;
    }

    private void advanceNextToken() throws IOException {
        nextToken = nextValidToken();
        nextLexemeStartLine = lexicalAnalyzer.getLexemeStartLine();
        nextLexemeStartPosition = lexicalAnalyzer.getLexemeStartPosition();
    }

    private void advanceCurrentToken() throws IOException {
        if (nextToken == null) {
            advanceNextToken();
        }

        currentToken = nextToken;
        currentLexemeStartLine = nextLexemeStartLine;
        currentLexemeStartPosition = nextLexemeStartPosition;

        advanceNextToken();
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

    private boolean canMatchNext(String tokenName) {
        return tokenName.equals(nextToken.getName());
    }

    private boolean canMatch(Set<String> tokenNames) {
        return tokenNames.contains(currentToken.getName());
    }

    private SyntacticException buildException(String expectedTokenName) {
        return new SyntacticException(currentToken, expectedTokenName, currentLexemeStartLine, currentLexemeStartPosition);
    }

    private void match(String expectedTokenName) throws SyntacticException, IOException {
        if (expectedTokenName.equals(currentToken.getName())) {
            advanceCurrentToken();
        } else {
            throw buildException(expectedTokenName);
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

    private void recoverAndMatch(Set<String> recoveryTokens) throws IOException {
        recover(recoveryTokens);

        matchCurrent();
    }

    private void recover(Set<String> lastTokens, Set<String> nextTokens) throws IOException {
        while (!canMatch(lastTokens) && !canMatch(nextTokens)) {
            advanceCurrentToken();
        }
    }

    private void recoverAndMatchIfPossible(Set<String> lastTokens, String lastOfCurrent, Set<String> nextTokens,
                                           SyntacticException exception) throws IOException, SyntacticException {
        recover(lastTokens, nextTokens);

        if (!canMatch(nextTokens)) {
            if (canMatch(lastOfCurrent)) {
                matchCurrent();
            } else {
                throw exception;
            }
        }
    }

    private void initialNT() throws IOException {
        try {
            classesAndInterfacesListNT();
            match(EOF);
        } catch (SyntacticException exception) {
            recoverAndMatch(LAST_INITIAL);
            exceptions.add(exception);
        }
    }

    private void classesAndInterfacesListNT() throws SyntacticException, IOException {
        classOrInterfaceNT();
        classesAndInterfacesListSuffixOrEmptyNT();
    }

    private void classOrInterfaceNT() throws IOException {
        try {
            if (canMatch(CLASS_KW)) {
                classNT();
            } else if (canMatch(INTERFACE_KW)) {
                interfaceNT();
            } else {
                throw buildException(CLASS_OR_INTERFACE);
            }
        } catch (SyntacticException exception) {
            recover(LAST_CLASS_OR_INTERFACE, NEXT_CLASS_OR_INTERFACE);
            exceptions.add(exception);
        }
    }

    private void classesAndInterfacesListSuffixOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(FIRST_CLASS_OR_INTERFACE)) {
            classesAndInterfacesListNT();
        }
    }

    private void classNT() throws SyntacticException, IOException {
        try {
            match(CLASS_KW);
            match(CLASS_ID);
            genericTypeOrEmpty();
            inheritanceOrEmptyNT();
            implementationOrEmptyNT();
            match(OPEN_BRACE);
        } catch (SyntacticException exception) {
            recoverAndMatchIfPossible(LAST_CLASS_OR_INTERFACE_SIGNATURE, OPEN_BRACE, NEXT_CLASS_SIGNATURE, exception);
            exceptions.add(exception);
        }

        try {
            membersListOrEmptyNT();
            match(CLOSE_BRACE);
        } catch (SyntacticException exception) {
            recoverAndMatchIfPossible(LAST_CLASS_OR_INTERFACE_BODY, CLOSE_BRACE, NEXT_CLASS_OR_INTERFACE_BODY, exception);
            exceptions.add(exception);
        }
    }

    private void genericTypeOrEmpty() throws SyntacticException, IOException {
        if (canMatch(LESS)) {
            matchCurrent();
            match(CLASS_ID);
            match(GREATER);
        }
    }

    private void inheritanceOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(EXTENDS_KW)) {
            matchCurrent();
            match(CLASS_ID);
            genericTypeOrEmpty();
        }
    }

    private void implementationOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(IMPLEMENTS_KW)) {
            matchCurrent();
            interfaceNamesListNT();
        }
    }

    private void interfaceNamesListNT() throws SyntacticException, IOException {
        match(CLASS_ID); // TODO: generic
        interfaceNamesListSuffixOrEmptyNT();
    }

    private void interfaceNamesListSuffixOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(COMMA)) {
            matchCurrent();
            interfaceNamesListNT();
        }
    }

    private void membersListOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(FIRST_MEMBER)) {
            memberNT();
            membersListOrEmptyNT();
        }
    }

    private void memberNT() throws SyntacticException, IOException {
        if (canMatch(FIRST_ATTRIBUTE)) {
            attributeNT();
        } else if (canMatch(CLASS_ID)) {
            constructorNT();
        } else if (canMatch(FIRST_METHOD)) {
            methodNT();
        } else {
            throw new IllegalStateException();
        }
    }

    private void attributeNT() throws SyntacticException, IOException {
        try {
            visibilityNT();
            staticOrEmpty();
            typeNT();
            attrsDecListNT();
            match(SEMICOLON);
        } catch (SyntacticException exception) {
            recoverAndMatchIfPossible(LAST_ATTRIBUTE, SEMICOLON, NEXT_ATTRIBUTE, exception);
            exceptions.add(exception);
        }
    }

    private void visibilityNT() throws IOException {
        if (canMatch(FIRST_VISIBILITY)) {
            matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private void staticOrEmpty() throws IOException {
        if (canMatch(STATIC_KW)) {
            matchCurrent();
        }
    }

    private void typeNT() throws SyntacticException, IOException {
        if (canMatch(FIRST_PRIMITIVE_TYPE)) {
            primitiveTypeNT();
        } else if (canMatch(CLASS_ID)) {
            matchCurrent();
            genericTypeOrEmpty();
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
        inlineAssignmentOrEmpty();
        attrsDecListSuffixOrEmptyNT();
    }

    private void inlineAssignmentOrEmpty() throws SyntacticException, IOException {
        if (canMatch(ASSIGN)) {
            matchCurrent();
            expressionNT();
        }
    }

    private void attrsDecListSuffixOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(COMMA)) {
            matchCurrent();
            attrsDecListNT();
        }
    }

    private void constructorNT() throws SyntacticException, IOException {
        try {
            match(CLASS_ID);
            formalArgsNT();
        } catch (SyntacticException exception) {
            recoverAndMatchIfPossible(LAST_CLASS_METHOD_SIGNATURE, CLOSE_PARENTHESIS, NEXT_CLASS_METHOD_SIGNATURE, exception);
            exceptions.add(exception);
        }

        blockNT();
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

    private void methodNT() throws SyntacticException, IOException {
        try {
            methodSignatureNT();
        } catch (SyntacticException exception) {
            recoverAndMatchIfPossible(LAST_CLASS_METHOD_SIGNATURE, CLOSE_PARENTHESIS, NEXT_CLASS_METHOD_SIGNATURE, exception);
            exceptions.add(exception);
        }
        blockNT();
    }

    private void methodSignatureNT() throws SyntacticException, IOException {
        methodFormNT();
        methodTypeNT();
        match(VAR_MET_ID);
        formalArgsNT();
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

    private void blockNT() throws SyntacticException, IOException {
        try {
            match(OPEN_BRACE);
            sentencesListOrEmptyNT();
            match(CLOSE_BRACE);
        } catch (SyntacticException exception) {
            recoverAndMatchIfPossible(LAST_BLOCK, CLOSE_BRACE, NEXT_BLOCK, exception);
            exceptions.add(exception);
        }
    }

    private void sentencesListOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(FIRST_SENTENCE)) {
            sentenceNT();
            sentencesListOrEmptyNT();
        }
    }

    private void sentenceNT() throws SyntacticException, IOException {
        try {
            if (canMatch(SEMICOLON)) {
                matchCurrent();
            } else if (canMatch(FIRST_EXPLICIT_CALL_OR_ASSIGNMENT)
                    || (canMatch(FIRST_IMPLICIT_CALL_OR_ASSIGNMENT) && canMatchNext(SECOND_IMPLICIT_CALL_OR_ASSIGNMENT))) {
                callOrAssignmentNT();
            } else if (canMatch(FIRST_DECLARATION)) {
                typeNT();
                varsDecListNT();
                match(SEMICOLON);
            } else if (canMatch(IF_KW)) {
                try {
                    matchCurrent();
                    match(OPEN_PARENTHESIS);
                    expressionNT();
                    match(CLOSE_PARENTHESIS);
                } catch (SyntacticException exception) {
                    recoverAndMatchIfPossible(LAST_CONTROL_STRUCTURE, CLOSE_PARENTHESIS, NEXT_CONTROL_STRUCTURE, exception);
                    exceptions.add(exception);
                }
                sentenceNT();
                elseOrEmptyNT();
            } else if (canMatch(WHILE_KW)) {
                try {
                    matchCurrent();
                    match(OPEN_PARENTHESIS);
                    expressionNT();
                    match(CLOSE_PARENTHESIS);
                } catch (SyntacticException exception) {
                    recoverAndMatchIfPossible(LAST_CONTROL_STRUCTURE, CLOSE_PARENTHESIS, NEXT_CONTROL_STRUCTURE, exception);
                    exceptions.add(exception);
                }
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
            recoverAndMatchIfPossible(LAST_SENTENCE, SEMICOLON, NEXT_SENTENCE, exception);
            exceptions.add(exception);
        }
    }

    private void varsDecListNT() throws SyntacticException, IOException {
        match(VAR_MET_ID);
        inlineAssignmentOrEmpty();
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

    private void elseOrEmptyNT() throws SyntacticException, IOException {
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
        } else if (canMatch(FIRST_STATIC_ACCESS)) {
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
        staticOrEmpty();
        match(CLASS_ID);
        match(DOT);
        varMetAccessNT();
    }

    private void constructorAccessNT() throws SyntacticException, IOException {
        match(NEW_KW);
        match(CLASS_ID);
        genericTypeOrEmpty();
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

    private void interfaceNT() throws SyntacticException, IOException {
        try {
            match(INTERFACE_KW);
            match(CLASS_ID);
            // genericTypeOrEmpty(); TODO: generic
            interfaceInheritanceOrEmptyNT();
            match(OPEN_BRACE);
        } catch (SyntacticException exception) {
            recoverAndMatchIfPossible(LAST_CLASS_OR_INTERFACE_SIGNATURE, OPEN_BRACE, NEXT_INTERFACE_SIGNATURE, exception);
            exceptions.add(exception);
        }

        try {
            interfaceMembersListOrEmptyNT();
            match(CLOSE_BRACE);
        } catch (SyntacticException exception) {
            recoverAndMatchIfPossible(LAST_CLASS_OR_INTERFACE_BODY, CLOSE_BRACE, NEXT_CLASS_OR_INTERFACE_BODY, exception);
            exceptions.add(exception);
        }
    }

    private void interfaceInheritanceOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(EXTENDS_KW)) {
            matchCurrent();
            interfaceNamesListNT();
        }
    }

    private void interfaceMembersListOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(FIRST_INTERFACE_MEMBER)) {
            interfaceMemberNT();
            interfaceMembersListOrEmptyNT();
        }
    }

    private void interfaceMemberNT() throws SyntacticException, IOException {
        interfaceMethodNT();
    }

    private void interfaceMethodNT() throws SyntacticException, IOException {
        try {
            methodSignatureNT();
            match(SEMICOLON);
        } catch (SyntacticException exception) {
            recoverAndMatchIfPossible(LAST_INTERFACE_METHOD_SIGNATURE, SEMICOLON, NEXT_INTERFACE_METHOD_SIGNATURE, exception);
            exceptions.add(exception);
        }
    }
}
