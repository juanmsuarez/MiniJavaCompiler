package com.minijava.compiler.semantic.declarations.entities;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public class Constructor extends Callable {
    private Lexeme lexeme;
    private String name;

    public Constructor(String name) {
        this.name = name;
    }

    public Constructor(Lexeme lexeme) {
        this.lexeme = lexeme;
        this.name = lexeme.getString();
    }

    public String getName() {
        return name;
    }

    public Lexeme getLexeme() {
        return lexeme;
    }

    @Override
    public String toString() {
        return "Constructor{" +
                "name='" + name + '\'' +
                ", parameters=" + parameters +
                '}';
    }

}
