package com.minijava.compiler.syntactic.analyzer;

import com.minijava.compiler.CompilerException;
import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.lexical.analyzer.LexicalAnalyzer;
import com.minijava.compiler.lexical.exceptions.LexicalException;
import com.minijava.compiler.lexical.models.Token;
import com.minijava.compiler.semantic.declarations.entities.Class;
import com.minijava.compiler.semantic.declarations.entities.*;
import com.minijava.compiler.semantic.declarations.entities.modifiers.Form;
import com.minijava.compiler.semantic.declarations.entities.modifiers.Visibility;
import com.minijava.compiler.semantic.declarations.entities.types.*;
import com.minijava.compiler.semantic.sentences.models.asts.*;
import com.minijava.compiler.syntactic.exceptions.SyntacticException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;
import static com.minijava.compiler.lexical.models.TokenNames.*;
import static com.minijava.compiler.semantic.declarations.entities.PredefinedEntities.OBJECT;
import static com.minijava.compiler.syntactic.analyzer.TokenGroups.*;
import static com.minijava.compiler.syntactic.models.TokenGroupNames.*;

public class SyntacticAnalyzer {
    private LexicalAnalyzer lexicalAnalyzer;
    private Token currentToken, nextToken;
    private List<CompilerException> exceptions = new ArrayList<>();

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

    public List<CompilerException> getExceptions() {
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

    private Token match(String expectedTokenName) throws SyntacticException, IOException {
        if (expectedTokenName.equals(currentToken.getName())) {
            Token matchedToken = currentToken;
            advanceCurrentToken();
            return matchedToken;
        } else {
            throw buildException(expectedTokenName);
        }
    }

    private Token matchCurrent() throws IOException {
        Token matchedToken = currentToken;
        advanceCurrentToken();
        return matchedToken;
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
        symbolTable.setCurrentUnit(currentClass);

        try {
            match(CLASS_KW);
            Lexeme classLexeme = match(CLASS_ID).getLexeme();
            currentClass.setLexeme(classLexeme);
            symbolTable.add(currentClass);

            String genericType = genericTypeOrEmptyNT();
            symbolTable.getCurrentUnit().setGenericType(genericType);

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

    private String genericTypeOrEmptyNT() throws SyntacticException, IOException {
        String genericType = null;

        if (canMatch(LESS)) {
            matchCurrent();
            genericType = match(CLASS_ID).getLexeme().getString();
            match(GREATER);
        }

        return genericType;
    }

    private void inheritanceOrEmptyNT() throws SyntacticException, IOException {
        ReferenceType parentType = new ReferenceType(OBJECT.getName(), symbolTable.getCurrentUnit(), Form.DYNAMIC);

        if (canMatch(EXTENDS_KW)) {
            matchCurrent();
            String parentName = match(CLASS_ID).getLexeme().getString();
            parentType.setName(parentName);

            String genericType = genericTypeOrEmptyNT();
            parentType.setGenericType(genericType);
        }

        ((Class) symbolTable.getCurrentUnit()).setParentType(parentType);
    }

    private void implementationOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(IMPLEMENTS_KW)) {
            matchCurrent();
            interfaceNamesListNT();
        }
    }

    private void interfaceNamesListNT() throws SyntacticException, IOException {
        String interfaceName = match(CLASS_ID).getLexeme().getString();
        genericTypeOrEmptyNT();
        symbolTable.getCurrentUnit().add(interfaceName);
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
            symbolTable.setCurrentAccessForm(form);
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
            Lexeme typeLexeme = matchCurrent().getLexeme();
            String genericType = genericTypeOrEmptyNT();
            return new ReferenceType(typeLexeme, genericType, symbolTable.getCurrentUnit(), symbolTable.getCurrentAccessForm());
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
        Lexeme lexeme = match(VAR_MET_ID).getLexeme();
        Attribute attribute = new Attribute(visibility, form, type, lexeme);
        ((Class) symbolTable.getCurrentUnit()).add(attribute);

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
        Constructor constructor = null;
        try {
            Lexeme lexeme = match(CLASS_ID).getLexeme();
            constructor = new Constructor(lexeme);
            ((Class) symbolTable.getCurrentUnit()).add(constructor);
            symbolTable.setCurrentAccessForm(Form.DYNAMIC);

            formalArgsNT();
        } catch (SyntacticException exception) {
            recoverAndMatchIfPossible(Last.CLASS_METHOD_SIGNATURE, CLOSE_PARENTHESIS, Next.CLASS_METHOD_SIGNATURE, exception);
            exceptions.add(exception);
        }

        Block block = blockNT();
        if (constructor != null) {
            constructor.setBlock(block);
        }
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
        Callable callable = symbolTable.getCurrentUnit().getCurrentCallable();

        Type type = typeNT();
        Lexeme lexeme = match(VAR_MET_ID).getLexeme();
        Parameter parameter = new Parameter(type, lexeme);
        callable.add(parameter);
    }

    private void methodNT() throws SyntacticException, IOException {
        Method method = null;
        try {
            method = methodSignatureNT();
        } catch (SyntacticException exception) {
            recoverAndMatchIfPossible(Last.CLASS_METHOD_SIGNATURE, CLOSE_PARENTHESIS, Next.CLASS_METHOD_SIGNATURE, exception);
            exceptions.add(exception);
        }

        Block block = blockNT();
        if (method != null) {
            method.setBlock(block);
        }
    }

    private Method methodSignatureNT() throws SyntacticException, IOException {
        Form form = methodFormNT();
        symbolTable.setCurrentAccessForm(form);
        Type type = methodTypeNT();
        Lexeme lexeme = match(VAR_MET_ID).getLexeme();

        Method method = new Method(form, type, lexeme);
        symbolTable.getCurrentUnit().add(method);

        formalArgsNT();

        return method;
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

    private Block blockNT() throws SyntacticException, IOException {
        Block block = new Block();

        try {
            match(OPEN_BRACE);
            sentencesListOrEmptyNT(block);
            match(CLOSE_BRACE);
        } catch (SyntacticException exception) {
            recoverAndMatchIfPossible(Last.BLOCK, CLOSE_BRACE, Next.BLOCK, exception);
            exceptions.add(exception);
        }

        return block;
    }

    private void sentencesListOrEmptyNT(Block block) throws SyntacticException, IOException {
        if (canMatch(Firsts.SENTENCE)) {
            Sentence sentence = sentenceNT();
            block.add(sentence);
            sentencesListOrEmptyNT(block);
        }
    }

    private Sentence sentenceNT() throws SyntacticException, IOException {
        try {
            if (canMatch(SEMICOLON)) {
                matchCurrent();
                return new EmptySentence();
            } else if (canMatch(Firsts.EXPLICIT_CALL_OR_ASSIGNMENT)
                    || (canMatch(Firsts.IMPLICIT_CALL_OR_ASSIGNMENT) && canMatchNext(Seconds.IMPLICIT_CALL_OR_ASSIGNMENT))) {
                return callOrAssignmentNT();
            } else if (canMatch(Firsts.DECLARATION)) {
                Type declarationType = typeNT();
                DeclarationSentenceList declarationSentenceList = new DeclarationSentenceList(declarationType);

                varsDecListNT(declarationSentenceList);
                match(SEMICOLON);

                return declarationSentenceList;
            } else if (canMatch(IF_KW)) {
                Lexeme ifLexeme = matchCurrent().getLexeme();
                IfElseSentence ifElseSentence = new IfElseSentence(ifLexeme);

                try {
                    match(OPEN_PARENTHESIS);
                    Expression condition = expressionNT();
                    ifElseSentence.setCondition(condition);
                    match(CLOSE_PARENTHESIS);
                } catch (SyntacticException exception) {
                    recoverAndMatchIfPossible(Last.CONTROL_STRUCTURE, CLOSE_PARENTHESIS, Next.CONTROL_STRUCTURE, exception);
                    exceptions.add(exception);
                }

                Sentence mainBody = sentenceNT();
                ifElseSentence.setMainBody(mainBody);

                Sentence elseBody = elseOrEmptyNT();
                ifElseSentence.setElseBody(elseBody);

                return ifElseSentence;
            } else if (canMatch(WHILE_KW)) {
                Lexeme whileLexeme = matchCurrent().getLexeme();
                WhileSentence whileSentence = new WhileSentence(whileLexeme);

                try {
                    match(OPEN_PARENTHESIS);
                    Expression condition = expressionNT();
                    whileSentence.setCondition(condition);
                    match(CLOSE_PARENTHESIS);
                } catch (SyntacticException exception) {
                    recoverAndMatchIfPossible(Last.CONTROL_STRUCTURE, CLOSE_PARENTHESIS, Next.CONTROL_STRUCTURE, exception);
                    exceptions.add(exception);
                }

                Sentence body = sentenceNT();
                whileSentence.setBody(body);

                return whileSentence;
            } else if (canMatch(OPEN_BRACE)) {
                return blockNT();
            } else if (canMatch(RETURN_KW)) {
                Lexeme returnLexeme = matchCurrent().getLexeme();
                Expression expression = expressionOrEmptyNT();
                match(SEMICOLON);
                return new ReturnSentence(returnLexeme, expression);
            } else {
                throw buildException(SENTENCE);
            }
        } catch (SyntacticException exception) {
            recoverAndMatchIfPossible(Last.SENTENCE, SEMICOLON, Next.SENTENCE, exception);
            exceptions.add(exception);
            return null;
        }
    }

    private void varsDecListNT(DeclarationSentenceList declarationSentenceList) throws SyntacticException, IOException {
        Type type = declarationSentenceList.getType();
        Lexeme id = match(VAR_MET_ID).getLexeme();
        DeclarationSentence declarationSentence = new DeclarationSentence(type, id);
        declarationSentenceList.addDeclaration(declarationSentence);

        inlineAssignmentOrEmpty();

        varsDecListSuffixOrEmptyNT(declarationSentenceList);
    }

    private void varsDecListSuffixOrEmptyNT(DeclarationSentenceList declarationSentenceList) throws SyntacticException, IOException {
        if (canMatch(COMMA)) {
            matchCurrent();
            varsDecListNT(declarationSentenceList);
        }
    }

    private Sentence callOrAssignmentNT() throws SyntacticException, IOException {
        Access access = accessNT();
        return assignmentOrSentenceEndNT(access);
    }

    private Sentence assignmentOrSentenceEndNT(Access access) throws SyntacticException, IOException {
        if (canMatch(Firsts.ASSIGNMENT_TYPE)) {
            Token assignmentType = assignmentTypeNT();
            Expression expression = expressionNT();
            match(SEMICOLON);
            return new Assignment(access, assignmentType, expression);
        } else if (canMatch(SEMICOLON)) {
            matchCurrent();
            return new Call(access);
        } else {
            throw buildException(ASSIGNMENT_OR_SENTENCE_END);
        }
    }

    private Token assignmentTypeNT() throws IOException {
        if (canMatch(Firsts.ASSIGNMENT_TYPE)) {
            return matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private Sentence elseOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(ELSE_KW)) {
            matchCurrent();
            return sentenceNT();
        } else {
            return null;
        }
    }

    private Expression expressionOrEmptyNT() throws SyntacticException, IOException {
        if (canMatch(Firsts.EXPRESSION)) {
            return expressionNT();
        } else {
            return null;
        }
    }

    private Expression expressionNT() throws SyntacticException, IOException {
        return expression1NT();
    }

    private Expression expression1NT() throws SyntacticException, IOException {
        Expression left = expression2NT();
        return expression1SuffixOrEmptyNT(left);
    }

    private Expression expression1SuffixOrEmptyNT(Expression left) throws SyntacticException, IOException {
        if (canMatch(Firsts.BINARY_OPERATOR_1)) {
            Token operator = binaryOperator1NT();
            Expression right = expression2NT();
            Expression binaryExpression = new BinaryExpression(left, operator, right);

            return expression1SuffixOrEmptyNT(binaryExpression);
        } else {
            return left;
        }
    }

    private Token binaryOperator1NT() throws IOException {
        if (canMatch(Firsts.BINARY_OPERATOR_1)) {
            return matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private Expression expression2NT() throws SyntacticException, IOException {
        Expression left = expression3NT();
        return expression2SuffixOrEmptyNT(left);
    }

    private Expression expression2SuffixOrEmptyNT(Expression left) throws SyntacticException, IOException {
        if (canMatch(Firsts.BINARY_OPERATOR_2)) {
            Token operator = binaryOperator2NT();
            Expression right = expression3NT();
            Expression binaryExpression = new BinaryExpression(left, operator, right);

            return expression2SuffixOrEmptyNT(binaryExpression);
        } else {
            return left;
        }
    }

    private Token binaryOperator2NT() throws IOException {
        if (canMatch(Firsts.BINARY_OPERATOR_2)) {
            return matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private Expression expression3NT() throws SyntacticException, IOException {
        Expression left = expression4NT();
        return expression3SuffixOrEmptyNT(left);
    }

    private Expression expression3SuffixOrEmptyNT(Expression left) throws SyntacticException, IOException {
        if (canMatch(Firsts.BINARY_OPERATOR_3)) {
            Token operator = binaryOperator3NT();
            Expression right = expression4NT();
            Expression binaryExpression = new BinaryExpression(left, operator, right);

            return expression3SuffixOrEmptyNT(binaryExpression);
        } else {
            return left;
        }
    }

    private Token binaryOperator3NT() throws IOException {
        if (canMatch(Firsts.BINARY_OPERATOR_3)) {
            return matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private Expression expression4NT() throws SyntacticException, IOException {
        Expression left = expression5NT();
        return expression4SuffixOrEmptyNT(left);
    }

    private Expression expression4SuffixOrEmptyNT(Expression left) throws SyntacticException, IOException {
        if (canMatch(Firsts.BINARY_OPERATOR_4)) {
            Token operator = binaryOperator4NT();
            Expression right = expression5NT();
            Expression binaryExpression = new BinaryExpression(left, operator, right);

            return expression4SuffixOrEmptyNT(binaryExpression);
        } else {
            return left;
        }
    }

    private Token binaryOperator4NT() throws IOException {
        if (canMatch(Firsts.BINARY_OPERATOR_4)) {
            return matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private Expression expression5NT() throws SyntacticException, IOException {
        Expression left = expression6NT();
        return expression5SuffixOrEmptyNT(left);
    }

    private Expression expression5SuffixOrEmptyNT(Expression left) throws SyntacticException, IOException {
        if (canMatch(Firsts.BINARY_OPERATOR_5)) {
            Token operator = binaryOperator5NT();
            Expression right = expression6NT();
            Expression binaryExpression = new BinaryExpression(left, operator, right);

            return expression5SuffixOrEmptyNT(binaryExpression);
        } else {
            return left;
        }
    }

    private Token binaryOperator5NT() throws IOException {
        if (canMatch(Firsts.BINARY_OPERATOR_5)) {
            return matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private Expression expression6NT() throws SyntacticException, IOException {
        Expression left = unaryExpressionNT();
        return expression6SuffixOrEmptyNT(left);
    }

    private Expression expression6SuffixOrEmptyNT(Expression left) throws SyntacticException, IOException {
        if (canMatch(Firsts.BINARY_OPERATOR_6)) {
            Token operator = binaryOperator6NT();
            Expression right = unaryExpressionNT();
            Expression binaryExpression = new BinaryExpression(left, operator, right);

            return expression6SuffixOrEmptyNT(binaryExpression);
        } else {
            return left;
        }
    }

    private Token binaryOperator6NT() throws IOException {
        if (canMatch(Firsts.BINARY_OPERATOR_6)) {
            return matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private Expression unaryExpressionNT() throws SyntacticException, IOException {
        if (canMatch(Firsts.UNARY_OPERATOR)) {
            Token operator = unaryOperatorNT();
            Operand operand = operandNT();
            return new UnaryExpression(operator, operand);
        } else if (canMatch(Firsts.OPERAND)) {
            return operandNT();
        } else {
            throw buildException(EXPRESSION);
        }
    }

    private Token unaryOperatorNT() throws IOException {
        if (canMatch(Firsts.UNARY_OPERATOR)) {
            return matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private Operand operandNT() throws SyntacticException, IOException {
        if (canMatch(Firsts.LITERAL)) {
            return literalNT();
        } else if (canMatch(Firsts.ACCESS)) {
            return accessNT();
        } else {
            throw buildException(OPERAND);
        }
    }

    private Literal literalNT() throws IOException {
        if (canMatch(Firsts.LITERAL)) {
            return new Literal(matchCurrent());
        } else {
            throw new IllegalStateException();
        }
    }

    private Access accessNT() throws SyntacticException, IOException {
        Access access = primaryAccessNT();
        access.setChainedAccess(chainedAccessOrEmptyNT());
        return access;
    }

    private Access primaryAccessNT() throws SyntacticException, IOException {
        if (canMatch(THIS_KW)) {
            return thisAccessNT();
        } else if (canMatch(VAR_MET_ID)) {
            return varMetAccessNT();
        } else if (canMatch(Firsts.STATIC_ACCESS)) {
            return staticAccessNT();
        } else if (canMatch(NEW_KW)) {
            return constructorAccessNT();
        } else if (canMatch(OPEN_PARENTHESIS)) {
            Lexeme openParenthesis = matchCurrent().getLexeme();
            Expression expression = expressionNT();
            match(CLOSE_PARENTHESIS);
            return new ExpressionAccess(openParenthesis, expression);
        } else {
            throw new IllegalStateException();
        }
    }

    private ThisAccess thisAccessNT() throws SyntacticException, IOException {
        Lexeme thisLexeme = match(THIS_KW).getLexeme();
        return new ThisAccess(thisLexeme);
    }

    private Access varMetAccessNT() throws SyntacticException, IOException {
        Lexeme varMetLexeme = match(VAR_MET_ID).getLexeme();
        return varMetActualArgsOrEmptyNT(varMetLexeme);
    }

    private Access varMetActualArgsOrEmptyNT(Lexeme varMetLexeme) throws SyntacticException, IOException {
        if (canMatch(OPEN_PARENTHESIS)) {
            MethodAccess methodAccess = new MethodAccess(varMetLexeme);
            varMetActualArgsNT(methodAccess);
            return methodAccess;
        } else {
            return new VariableAccess(varMetLexeme);
        }
    }

    private void varMetActualArgsNT(CallableAccess callableAccess) throws SyntacticException, IOException {
        match(OPEN_PARENTHESIS);
        expressionsListOrEmptyNT(callableAccess);
        match(CLOSE_PARENTHESIS);
    }

    private void expressionsListOrEmptyNT(CallableAccess callableAccess) throws SyntacticException, IOException {
        if (canMatch(Firsts.EXPRESSION)) {
            expressionsListNT(callableAccess);
        }
    }

    private void expressionsListNT(CallableAccess callableAccess) throws SyntacticException, IOException {
        Expression argument = expressionNT();
        callableAccess.add(argument);
        expressionsListSuffixOrEmpty(callableAccess);
    }

    private void expressionsListSuffixOrEmpty(CallableAccess callableAccess) throws SyntacticException, IOException {
        if (canMatch(COMMA)) {
            matchCurrent();
            expressionsListNT(callableAccess);
        }
    }

    private Access staticAccessNT() throws SyntacticException, IOException {
        staticOrEmpty();
        Lexeme classLexeme = match(CLASS_ID).getLexeme();
        match(DOT);
        return staticVarMetAccessNT(classLexeme);
    }

    private Access staticVarMetAccessNT(Lexeme classLexeme) throws SyntacticException, IOException {
        Lexeme varMetLexeme = match(VAR_MET_ID).getLexeme();
        return staticVarMetActualArgsOrEmptyNT(classLexeme, varMetLexeme);
    }

    private Access staticVarMetActualArgsOrEmptyNT(Lexeme classLexeme, Lexeme varMetLexeme) throws SyntacticException, IOException {
        if (canMatch(OPEN_PARENTHESIS)) {
            StaticMethodAccess staticMethodAccess = new StaticMethodAccess(classLexeme, varMetLexeme);
            varMetActualArgsNT(staticMethodAccess);
            return staticMethodAccess;
        } else {
            return new StaticVariableAccess(classLexeme, varMetLexeme);
        }
    }

    private ConstructorAccess constructorAccessNT() throws SyntacticException, IOException {
        Lexeme newLexeme = match(NEW_KW).getLexeme();
        Lexeme classId = match(CLASS_ID).getLexeme();
        ConstructorAccess constructorAccess = new ConstructorAccess(newLexeme, classId);
        optionalGenericTypeOrEmptyNT(); // modificar en caso de hacer genericidad
        varMetActualArgsNT(constructorAccess);
        return constructorAccess;
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

    private ChainedAccess chainedAccessOrEmptyNT() throws SyntacticException, IOException {
        ChainedAccess chainedAccess = null;

        if (canMatch(DOT)) {
            chainedAccess = chainedVarMetAccessNT();
            chainedAccess.setChainedAccess(chainedAccessOrEmptyNT());
        }

        return chainedAccess;
    }

    private ChainedAccess chainedVarMetAccessNT() throws SyntacticException, IOException {
        match(DOT);
        Lexeme varMetLexeme = match(VAR_MET_ID).getLexeme();
        return chainedVarMetActualArgsOrEmptyNT(varMetLexeme);
    }

    private ChainedAccess chainedVarMetActualArgsOrEmptyNT(Lexeme varMetLexeme) throws SyntacticException, IOException {
        if (canMatch(OPEN_PARENTHESIS)) {
            ChainedMethodAccess chainedMethodAccess = new ChainedMethodAccess(varMetLexeme);
            varMetActualArgsNT(chainedMethodAccess);
            return chainedMethodAccess;
        } else {
            return new ChainedVariableAccess(varMetLexeme);
        }
    }

    private void interfaceNT() throws SyntacticException, IOException {
        Interface currentInterface = new Interface();
        symbolTable.setCurrentUnit(currentInterface);

        try {
            match(INTERFACE_KW);
            Lexeme interfaceLexeme = match(CLASS_ID).getLexeme();
            currentInterface.setLexeme(interfaceLexeme);
            symbolTable.add(currentInterface);

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
