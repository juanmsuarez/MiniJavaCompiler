package com.minijava.compiler.syntactic.analyzer;

import com.minijava.compiler.CompilerException;
import com.minijava.compiler.lexical.analyzer.LexicalAnalyzer;
import com.minijava.compiler.lexical.models.Token;
import com.minijava.compiler.syntactic.exceptions.SyntacticException;

import java.io.IOException;

import static com.minijava.compiler.lexical.models.TokenNames.*;

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
        // membersListOrEmptyNT();
        match(CLOSE_BRACE);
    }

    private void inheritanceOrEmptyNT() throws CompilerException, IOException {
        if (canMatch(EXTENDS_KW)) {
            match(EXTENDS_KW);
            match(CLASS_ID);
        }
    }
/*
    private void membersListOrEmptyNT() throws CompilerException, IOException {
        if () {
            memberNT();
            membersListOrEmptyNT();
        }
    }

    private void memberNT() throws CompilerException, IOException {
        if () {

        } else if () {

        } else if () {

        } else {
            throw ;
        }
    }

    private void attributeNT() throws CompilerException, IOException {
        visibilityNT();
    }

    private void visibilityNT() throws CompilerException, IOException {
        if (canMatch(PUBLIC_KW)) {
            match(PUBLIC_KW);
        } else if (canMatch(PRIVATE_KW)) {
            match(PRIVATE_KW);
        } else {
            // throw new SyntacticException();
        }
    }

    private void typeNT() throws CompilerException, IOException {
        if ()
    }

    private void primitiveTypeNT() throws CompilerException, IOException {
        if (canMatch(BOOLEAN_KW)) {
            match(BOOLEAN_KW);
        } else if (canMatch(CHAR_KW)) {
            // ...
        } // else if ...
    }

 */
}
