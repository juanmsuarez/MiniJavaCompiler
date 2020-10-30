package com.minijava.compiler.semantic.entities;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.entities.types.Type;

public class Parameter {
    private Type type;
    private Lexeme lexeme;
    private String name;

    public Parameter(Type type, Lexeme lexeme) {
        this.type = type;
        this.lexeme = lexeme;
        this.name = lexeme.getString();
    }

    public Lexeme getLexeme() {
        return lexeme;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "\nParameter{" +
                "type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
