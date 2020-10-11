package com.minijava.compiler.syntactic.analyzer;

import com.minijava.compiler.CompilerException;
import com.minijava.compiler.lexical.analyzer.LexicalAnalyzer;
import com.minijava.compiler.lexical.models.Token;
import com.minijava.compiler.syntactic.exceptions.SyntacticException;

import java.io.IOException;
import java.util.Set;

import static com.minijava.compiler.lexical.models.TokenNames.*;
import static com.minijava.compiler.syntactic.analyzer.FirstSets.*;
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
            // constructorNT();
        } else if (canMatch(FIRST_METHOD)) {
            // methodNT();
        } else {
            throw new IllegalStateException(); // TODO: para estados no alcanzables no tiene sentido crear una excepción y un mensaje de error particular, tiro IllegalStateExc?
        }
    }

    private void attributeNT() throws CompilerException, IOException {
        visibilityNT();
        typeNT();
        attrsDecList();
        match(SEMICOLON);
    }

    private void visibilityNT() throws CompilerException, IOException {
        if (canMatch(PUBLIC_KW)) {
            matchCurrent();
        } else if (canMatch(PRIVATE_KW)) {
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

    private void attrsDecList() throws CompilerException, IOException {
        match(VAR_MET_ID);
        attrsDecListSuffixOrEmpty();
    }

    private void attrsDecListSuffixOrEmpty() throws CompilerException, IOException {
        if (canMatch(COMMA)) {
            matchCurrent();
            attrsDecList();
        }
    }
}
