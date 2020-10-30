package com.minijava.compiler.syntactic.analyzer;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.lexical.analyzer.LexicalAnalyzer;
import com.minijava.compiler.lexical.exceptions.LexicalException;
import com.minijava.compiler.lexical.models.Token;
import com.minijava.compiler.semantic.entities.Class;
import com.minijava.compiler.semantic.entities.*;
import com.minijava.compiler.semantic.entities.modifiers.Form;
import com.minijava.compiler.semantic.entities.modifiers.Visibility;
import com.minijava.compiler.semantic.entities.types.*;
import com.minijava.compiler.syntactic.exceptions.SyntacticException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;
import static com.minijava.compiler.lexical.models.TokenNames.*;
import static com.minijava.compiler.syntactic.analyzer.TokenGroups.*;
import static com.minijava.compiler.syntactic.models.TokenGroupNames.*;

public class SyntacticAnalyzer {
    private LexicalAnalyzer lexicalAnalyzer;
    private Token currentToken, nextToken;
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
    }

    private void advanceCurrentToken() throws IOException {
        if (nextToken == null) {
            advanceNextToken();
        }

        currentToken = nextToken;

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
        return new SyntacticException(currentToken, expectedTokenName);
    }

    private Lexeme match(String expectedTokenName) throws SyntacticException, IOException {
        if (expectedTokenName.equals(currentToken.getName())) {
            Lexeme currentLexeme = currentToken.getLexeme();
            advanceCurrentToken();
            return currentLexeme;
        } else {
            throw buildException(expectedTokenName);
        }
    }

    private Lexeme matchCurrent() throws IOException {
        Lexeme currentLexeme = currentToken.getLexeme();
        advanceCurrentToken();
        return currentLexeme;
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
            recoverAndMatch(Last.INITIAL);
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
            recover(Last.CLASS_OR_INTERFACE, Next.CLASS_OR_INTERFACE);
            exceptions.add(exception);
        }
    }

    private void classesAndInterfacesListSuffixOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(Firsts.CLASS_OR_INTERFACE)) {
            classesAndInterfacesListNT();
        }
    }

    private void classNT() throws SyntacticException, IOException {
        Class currentClass = new Class();
        symbolTable.setCurrentClass(currentClass);

        try {
            match(CLASS_KW);
            Lexeme classLexeme = match(CLASS_ID);
            currentClass.setLexeme(classLexeme);
            symbolTable.add(currentClass);

            genericTypeOrEmptyNT();

            inheritanceOrEmptyNT();

            implementationOrEmptyNT();

            match(OPEN_BRACE);
        } catch (SyntacticException exception) {
            recoverAndMatchIfPossible(Last.CLASS_OR_INTERFACE_SIGNATURE, OPEN_BRACE, Next.CLASS_SIGNATURE, exception);
            exceptions.add(exception);
        }

        try {
            membersListOrEmptyNT();
            match(CLOSE_BRACE);
        } catch (SyntacticException exception) {
            recoverAndMatchIfPossible(Last.CLASS_OR_INTERFACE_BODY, CLOSE_BRACE, Next.CLASS_OR_INTERFACE_BODY, exception);
            exceptions.add(exception);
        }
    }

    private void genericTypeOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(LESS)) {
            matchCurrent();
            match(CLASS_ID);
            match(GREATER);
        }
    }

    private void inheritanceOrEmptyNT() throws SyntacticException, IOException {
        String parentName = Class.OBJECT;

        if (canMatch(EXTENDS_KW)) {
            matchCurrent();
            parentName = match(CLASS_ID).getString();

            genericTypeOrEmptyNT();
        }

        symbolTable.getCurrentClass().setParent(parentName);
    }

    private void implementationOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(IMPLEMENTS_KW)) {
            matchCurrent();
            interfaceNamesListNT();
        }
    }

    private void interfaceNamesListNT() throws SyntacticException, IOException {
        match(CLASS_ID);
        genericTypeOrEmptyNT();
        interfaceNamesListSuffixOrEmptyNT();
    }

    private void interfaceNamesListSuffixOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(COMMA)) {
            matchCurrent();
            interfaceNamesListNT();
        }
    }

    private void membersListOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(Firsts.MEMBER)) {
            memberNT();
            membersListOrEmptyNT();
        }
    }

    private void memberNT() throws SyntacticException, IOException {
        if (canMatch(Firsts.ATTRIBUTE)) {
            attributeNT();
        } else if (canMatch(CLASS_ID)) {
            constructorNT();
        } else if (canMatch(Firsts.METHOD)) {
            methodNT();
        } else {
            throw new IllegalStateException();
        }
    }

    private void attributeNT() throws SyntacticException, IOException {
        try {
            Visibility visibility = visibilityNT();
            Form form = staticOrEmpty();
            Type type = typeNT();
            attrsDecListNT(visibility, form, type);
            match(SEMICOLON);
        } catch (SyntacticException exception) {
            recoverAndMatchIfPossible(Last.ATTRIBUTE, SEMICOLON, Next.ATTRIBUTE, exception);
            exceptions.add(exception);
        }
    }

    private Visibility visibilityNT() throws IOException {
        if (canMatch(PUBLIC_KW)) {
            matchCurrent();
            return Visibility.PUBLIC;
        } else if (canMatch(PRIVATE_KW)) {
            matchCurrent();
            return Visibility.PRIVATE;
        } else {
            throw new IllegalStateException();
        }
    }

    private Form staticOrEmpty() throws IOException {
        if (canMatch(STATIC_KW)) {
            matchCurrent();
            return Form.STATIC;
        } else {
            return Form.DYNAMIC;
        }
    }

    private Type typeNT() throws SyntacticException, IOException {
        if (canMatch(Firsts.PRIMITIVE_TYPE)) {
            return primitiveTypeNT();
        } else if (canMatch(CLASS_ID)) {
            String typeName = matchCurrent().getString();
            genericTypeOrEmptyNT();
            return new ReferenceType(typeName);
        } else {
            throw buildException(TYPE);
        }
    }

    private PrimitiveType primitiveTypeNT() throws IOException {
        if (canMatch(BOOLEAN_KW)) {
            matchCurrent();
            return new BooleanType();
        } else if (canMatch(CHAR_KW)) {
            matchCurrent();
            return new CharType();
        } else if (canMatch(INT_KW)) {
            matchCurrent();
            return new IntType();
        } else if (canMatch(STRING_KW)) {
            matchCurrent();
            return new StringType();
        } else {
            throw new IllegalStateException();
        }
    }

    private void attrsDecListNT(Visibility visibility, Form form, Type type) throws SyntacticException, IOException {
        Lexeme lexeme = match(VAR_MET_ID);
        Attribute attribute = new Attribute(visibility, form, type, lexeme);
        symbolTable.getCurrentClass().add(attribute);

        inlineAssignmentOrEmpty();
        attrsDecListSuffixOrEmptyNT(visibility, form, type);
    }

    private void inlineAssignmentOrEmpty() throws SyntacticException, IOException {
        if (canMatch(ASSIGN)) {
            matchCurrent();
            expressionNT();
        }
    }

    private void attrsDecListSuffixOrEmptyNT(Visibility visibility, Form form, Type type) throws SyntacticException, IOException {
        if (canMatch(COMMA)) {
            matchCurrent();
            attrsDecListNT(visibility, form, type);
        }
    }

    private void constructorNT() throws SyntacticException, IOException {
        try {
            Lexeme lexeme = match(CLASS_ID);
            Constructor constructor = new Constructor(lexeme);
            symbolTable.getCurrentClass().add(constructor);

            formalArgsNT();
        } catch (SyntacticException exception) {
            recoverAndMatchIfPossible(Last.CLASS_METHOD_SIGNATURE, CLOSE_PARENTHESIS, Next.CLASS_METHOD_SIGNATURE, exception);
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
        if (canMatch(Firsts.FORMAL_ARGS)) {
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
        Callable callable = symbolTable.getCurrentClass().getCurrentCallable();

        Type type = typeNT();
        Lexeme lexeme = match(VAR_MET_ID);
        Parameter parameter = new Parameter(type, lexeme);
        callable.add(parameter);
    }

    private void methodNT() throws SyntacticException, IOException {
        try {
            methodSignatureNT();
        } catch (SyntacticException exception) {
            recoverAndMatchIfPossible(Last.CLASS_METHOD_SIGNATURE, CLOSE_PARENTHESIS, Next.CLASS_METHOD_SIGNATURE, exception);
            exceptions.add(exception);
        }
        blockNT();
    }

    private void methodSignatureNT() throws SyntacticException, IOException {
        Form form = methodFormNT();
        Type type = methodTypeNT();
        Lexeme lexeme = match(VAR_MET_ID);
        Method method = new Method(form, type, lexeme);
        symbolTable.getCurrentClass().add(method);

        formalArgsNT();
    }

    private Form methodFormNT() throws IOException {
        if (canMatch(STATIC_KW)) {
            matchCurrent();
            return Form.STATIC;
        } else if (canMatch(DYNAMIC_KW)) {
            matchCurrent();
            return Form.DYNAMIC;
        } else {
            throw new IllegalStateException();
        }
    }

    private Type methodTypeNT() throws SyntacticException, IOException {
        if (canMatch(Firsts.TYPE)) {
            return typeNT();
        } else if (canMatch(VOID_KW)) {
            matchCurrent();
            return new VoidType();
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
            recoverAndMatchIfPossible(Last.BLOCK, CLOSE_BRACE, Next.BLOCK, exception);
            exceptions.add(exception);
        }
    }

    private void sentencesListOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(Firsts.SENTENCE)) {
            sentenceNT();
            sentencesListOrEmptyNT();
        }
    }

    private void sentenceNT() throws SyntacticException, IOException {
        try {
            if (canMatch(SEMICOLON)) {
                matchCurrent();
            } else if (canMatch(Firsts.EXPLICIT_CALL_OR_ASSIGNMENT)
                    || (canMatch(Firsts.IMPLICIT_CALL_OR_ASSIGNMENT) && canMatchNext(Seconds.IMPLICIT_CALL_OR_ASSIGNMENT))) {
                callOrAssignmentNT();
            } else if (canMatch(Firsts.DECLARATION)) {
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
                    recoverAndMatchIfPossible(Last.CONTROL_STRUCTURE, CLOSE_PARENTHESIS, Next.CONTROL_STRUCTURE, exception);
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
                    recoverAndMatchIfPossible(Last.CONTROL_STRUCTURE, CLOSE_PARENTHESIS, Next.CONTROL_STRUCTURE, exception);
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
                throw buildException(SENTENCE);
            }
        } catch (SyntacticException exception) {
            recoverAndMatchIfPossible(Last.SENTENCE, SEMICOLON, Next.SENTENCE, exception);
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
        if (canMatch(Firsts.ASSIGNMENT_TYPE)) {
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
        if (canMatch(Firsts.ASSIGNMENT_TYPE)) {
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
        if (canMatch(Firsts.EXPRESSION)) {
            expressionNT();
        }
    }

    private void expressionNT() throws SyntacticException, IOException {
        expression1NT();
    }

    private void expression1NT() throws SyntacticException, IOException {
        expression2NT();
        expression1SuffixOrEmptyNT();
    }

    private void expression1SuffixOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(Firsts.BINARY_OPERATOR_1)) {
            binaryOperator1NT();
            expression1NT(); // TODO: tener cuidado con usar mismo NT en recursión (último ejemplo del tema 4.5.3)
        }
    }

    private void binaryOperator1NT() throws IOException {
        if (canMatch(Firsts.BINARY_OPERATOR_1)) {
            matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private void expression2NT() throws SyntacticException, IOException {
        expression3NT();
        expression2SuffixOrEmptyNT();
    }

    private void expression2SuffixOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(Firsts.BINARY_OPERATOR_2)) {
            binaryOperator2NT();
            expression2NT();
        }
    }

    private void binaryOperator2NT() throws IOException {
        if (canMatch(Firsts.BINARY_OPERATOR_2)) {
            matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private void expression3NT() throws SyntacticException, IOException {
        expression4NT();
        expression3SuffixOrEmptyNT();
    }

    private void expression3SuffixOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(Firsts.BINARY_OPERATOR_3)) {
            binaryOperator3NT();
            expression3NT();
        }
    }

    private void binaryOperator3NT() throws IOException {
        if (canMatch(Firsts.BINARY_OPERATOR_3)) {
            matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private void expression4NT() throws SyntacticException, IOException {
        expression5NT();
        expression4SuffixOrEmptyNT();
    }

    private void expression4SuffixOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(Firsts.BINARY_OPERATOR_4)) {
            binaryOperator4NT();
            expression4NT();
        }
    }

    private void binaryOperator4NT() throws IOException {
        if (canMatch(Firsts.BINARY_OPERATOR_4)) {
            matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private void expression5NT() throws SyntacticException, IOException {
        expression6NT();
        expression5SuffixOrEmptyNT();
    }

    private void expression5SuffixOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(Firsts.BINARY_OPERATOR_5)) {
            binaryOperator5NT();
            expression5NT();
        }
    }

    private void binaryOperator5NT() throws IOException {
        if (canMatch(Firsts.BINARY_OPERATOR_5)) {
            matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private void expression6NT() throws SyntacticException, IOException {
        unaryExpressionNT();
        expression6SuffixOrEmptyNT();
    }

    private void expression6SuffixOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(Firsts.BINARY_OPERATOR_6)) {
            binaryOperator6NT();
            expression6NT();
        }
    }

    private void binaryOperator6NT() throws IOException {
        if (canMatch(Firsts.BINARY_OPERATOR_6)) {
            matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private void unaryExpressionNT() throws SyntacticException, IOException {
        if (canMatch(Firsts.UNARY_OPERATOR)) {
            unaryOperatorNT();
            operandNT();
        } else if (canMatch(Firsts.OPERAND)) {
            operandNT();
        } else {
            throw buildException(EXPRESSION);
        }
    }

    private void unaryOperatorNT() throws IOException {
        if (canMatch(Firsts.UNARY_OPERATOR)) {
            matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private void operandNT() throws SyntacticException, IOException {
        if (canMatch(Firsts.LITERAL)) {
            literalNT();
        } else if (canMatch(Firsts.ACCESS)) {
            accessNT();
        } else {
            throw buildException(OPERAND);
        }
    }

    private void literalNT() throws IOException {
        if (canMatch(Firsts.LITERAL)) {
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
        } else if (canMatch(Firsts.STATIC_ACCESS)) {
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
        if (canMatch(Firsts.EXPRESSION)) {
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
        optionalGenericTypeOrEmptyNT();
        actualArgsNT();
    }

    private void optionalGenericTypeOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(LESS)) {
            matchCurrent();
            classIdOrEmptyNT();
            match(GREATER);
        }
    }

    private void classIdOrEmptyNT() throws IOException {
        if (canMatch(CLASS_ID)) {
            matchCurrent();
        }
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
            genericTypeOrEmptyNT();
            interfaceInheritanceOrEmptyNT();
            match(OPEN_BRACE);
        } catch (SyntacticException exception) {
            recoverAndMatchIfPossible(Last.CLASS_OR_INTERFACE_SIGNATURE, OPEN_BRACE, Next.INTERFACE_SIGNATURE, exception);
            exceptions.add(exception);
        }

        try {
            interfaceMembersListOrEmptyNT();
            match(CLOSE_BRACE);
        } catch (SyntacticException exception) {
            recoverAndMatchIfPossible(Last.CLASS_OR_INTERFACE_BODY, CLOSE_BRACE, Next.CLASS_OR_INTERFACE_BODY, exception);
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
        if (canMatch(Firsts.INTERFACE_MEMBER)) {
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
            recoverAndMatchIfPossible(Last.INTERFACE_METHOD_SIGNATURE, SEMICOLON, Next.INTERFACE_METHOD_SIGNATURE, exception);
            exceptions.add(exception);
        }
    }
}
