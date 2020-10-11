package com.minijava.compiler.syntactic.analyzer;

import com.minijava.compiler.CompilerException;
import com.minijava.compiler.lexical.analyzer.LexicalAnalyzer;
import com.minijava.compiler.lexical.models.Token;
import com.minijava.compiler.syntactic.exceptions.SyntacticException;

import java.io.IOException;
import java.util.Set;

import static com.minijava.compiler.lexical.models.TokenNames.*;
import static com.minijava.compiler.syntactic.analyzer.FirstSets.*;
import static com.minijava.compiler.syntactic.exceptions.ErrorCodes.EXPECTED_METHOD_TYPE;
import static com.minijava.compiler.syntactic.exceptions.ErrorCodes.EXPECTED_TYPE;

public class SyntacticAnalyzer {
    private LexicalAnalyzer lexicalAnalyzer;

    private Token currentToken;

    public SyntacticAnalyzer(LexicalAnalyzer lexicalAnalyzer) {
        this.lexicalAnalyzer = lexicalAnalyzer;
    }

    public void analyze() throws CompilerException, IOException {
        currentToken = lexicalAnalyzer.nextToken();
        initialNT();
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

    private void match(String expectedTokenName) throws CompilerException, IOException {
        if (expectedTokenName.equals(currentToken.getName())) {
            currentToken = lexicalAnalyzer.nextToken();
        } else {
            throw buildException(expectedTokenName);
        }
    }

    private void matchCurrent() throws CompilerException, IOException {
        match(currentToken.getName());
    }

    private void initialNT() throws CompilerException, IOException {
        classesListNT();
        match(EOF);
    }

    private void classesListNT() throws CompilerException, IOException {
        classNT();
        classesListSuffixOrEmptyNT();
    }

    private void classesListSuffixOrEmptyNT() throws CompilerException, IOException {
        if (canMatch(CLASS_KW)) {
            classesListNT();
        }
    }

    private void classNT() throws CompilerException, IOException {
        match(CLASS_KW);
        match(CLASS_ID);
        inheritanceOrEmptyNT();
        match(OPEN_BRACE);
        membersListOrEmptyNT();
        match(CLOSE_BRACE);
    }

    private void inheritanceOrEmptyNT() throws CompilerException, IOException {
        if (canMatch(EXTENDS_KW)) {
            matchCurrent();
            match(CLASS_ID);
        }
    }

    private void membersListOrEmptyNT() throws CompilerException, IOException {
        if (canMatch(FIRST_MEMBER)) {
            memberNT();
            membersListOrEmptyNT();
        }
    }

    private void memberNT() throws CompilerException, IOException {
        if (canMatch(FIRST_ATTRIBUTE)) {
            attributeNT();
        } else if (canMatch(FIRST_CONSTRUCTOR)) {
            constructorNT();
        } else if (canMatch(FIRST_METHOD)) {
            methodNT();
        } else {
            throw new IllegalStateException(); // TODO: para estados no alcanzables no tiene sentido crear una excepción y un mensaje de error particular, tiro IllegalStateExc?
        }
    }

    private void attributeNT() throws CompilerException, IOException {
        visibilityNT();
        typeNT();
        attrsDecListNT();
        match(SEMICOLON);
    }

    private void visibilityNT() throws CompilerException, IOException {
        if (canMatch(FIRST_VISIBILITY)) {
            matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private void typeNT() throws CompilerException, IOException {
        if (canMatch(FIRST_PRIMITIVE_TYPE)) {
            primitiveTypeNT();
        } else if (canMatch(CLASS_ID)) {
            matchCurrent();
        } else {
            throw buildException(EXPECTED_TYPE);
        }
    }

    private void primitiveTypeNT() throws CompilerException, IOException {
        if (canMatch(FIRST_PRIMITIVE_TYPE)) {
            matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private void attrsDecListNT() throws CompilerException, IOException {
        match(VAR_MET_ID);
        attrsDecListSuffixOrEmptyNT();
    }

    private void attrsDecListSuffixOrEmptyNT() throws CompilerException, IOException {
        if (canMatch(COMMA)) {
            matchCurrent();
            attrsDecListNT();
        }
    }

    private void constructorNT() throws CompilerException, IOException {
        match(CLASS_ID);
        formalArgsNT();
        blockNT();
    }

    private void formalArgsNT() throws CompilerException, IOException {
        match(OPEN_PARENTHESIS);
        formalArgsListOrEmptyNT();
        match(CLOSE_PARENTHESIS);
    }

    private void formalArgsListOrEmptyNT() throws CompilerException, IOException {
        if (canMatch(FIRST_FORMAL_ARGS)) {
            formalArgsListNT();
        }
    }

    private void formalArgsListNT() throws CompilerException, IOException {
        formalArgNT();
        formalArgsListSuffixOrEmptyNT();
    }

    private void formalArgsListSuffixOrEmptyNT() throws CompilerException, IOException {
        if (canMatch(COMMA)) {
            matchCurrent();
            formalArgsListNT();
        }
    }

    private void formalArgNT() throws CompilerException, IOException {
        typeNT();
        match(VAR_MET_ID);
    }

    private void methodNT() throws CompilerException, IOException {
        methodFormNT();
        methodTypeNT();
        match(VAR_MET_ID);
        formalArgsNT();
        blockNT();
    }

    private void methodFormNT() throws CompilerException, IOException {
        if (canMatch(FIRST_METHOD_FORM)) {
            matchCurrent();
        } else {
            throw new IllegalStateException();
        }
    }

    private void methodTypeNT() throws CompilerException, IOException {
        if (canMatch(FIRST_TYPE)) {
            typeNT();
        } else if (canMatch(VOID_KW)) {
            matchCurrent();
        } else {
            throw buildException(EXPECTED_METHOD_TYPE);
        }
    }

    private void blockNT() throws CompilerException, IOException {
        match(OPEN_BRACE);
        // sentencesList()
        match(CLOSE_BRACE);
    }
}
