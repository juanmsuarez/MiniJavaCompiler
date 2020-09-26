package com.minijava.compiler.lexicalanalyzer;

import com.minijava.compiler.lexicalanalyzer.exceptions.LexicalException;

import java.io.IOException;

interface State {
    Token execute() throws IOException, LexicalException;
}
