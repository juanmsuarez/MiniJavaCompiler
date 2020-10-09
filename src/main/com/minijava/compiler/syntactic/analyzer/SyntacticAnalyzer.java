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

    public void analyze() throws CompilerException, IOException { // TODO: para recuperarse de un error, los tokens de sincronización se definen para cada NT o solo para inicial? hace falta guardar el NT en el que nos encontramos?
        currentToken = lexicalAnalyzer.nextToken(); // TODO: ya no hace falta imprimir los tokens del léxico cierto?
        initialNT();
    }

    private void match(String expectedTokenName) throws CompilerException, IOException {
        if (expectedTokenName.equals(currentToken.getName())) {
            currentToken = lexicalAnalyzer.nextToken();
        } else {
            throw new SyntacticException(currentToken, expectedTokenName);
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
        if (CLASS_KW.equals(currentToken.getName())) {
            classesListNT();
        }
    }

    private void classNT() throws CompilerException, IOException {
        match(CLASS_KW);
        match(CLASS_ID);
        inheritanceNT();
        match(OPEN_BRACE);
        membersListNT();
        match(CLOSE_BRACE);
    }

    private void inheritanceNT() throws CompilerException, IOException {
        if (EXTENDS_KW.equals(currentToken.getName())) {
            match(EXTENDS_KW);
            match(CLASS_ID);
        }
    }

    private void membersListNT() throws CompilerException, IOException {

    }
}
