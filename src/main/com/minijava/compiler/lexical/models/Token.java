package com.minijava.compiler.lexical.models;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public class Token {
    private String name;
    private Lexeme lexeme;

    public Token(String name, Lexeme lexeme) {
        this.name = name;
        this.lexeme = lexeme;
    }

    public String getName() {
        return name;
    }

    public Lexeme getLexeme() {
        return lexeme;
    }

    @Override
    public String toString() {
        return String.format("(%s,%s,%d)", name, lexeme.getString(), lexeme.getLineNumber() + 1);
    }
}
