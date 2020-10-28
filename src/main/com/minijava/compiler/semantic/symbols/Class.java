package com.minijava.compiler.semantic.symbols;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public class Class {
    private String name;
    private String parent;

    // detailed error
    private Lexeme lexeme;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Lexeme getLexeme() {
        return lexeme;
    }

    public void setLexeme(Lexeme lexeme) {
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return "Class{" +
                "name='" + name + '\'' +
                ", parent='" + parent + '\'' +
                '}';
    }
}
