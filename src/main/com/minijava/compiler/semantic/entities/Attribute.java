package com.minijava.compiler.semantic.entities;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.entities.modifiers.Form;
import com.minijava.compiler.semantic.entities.modifiers.Visibility;
import com.minijava.compiler.semantic.entities.types.Type;

public class Attribute {
    private Visibility visibility;
    private Form form;
    private Type type;
    private Lexeme lexeme;
    private String name;

    public Attribute(Visibility visibility, Form form, Type type, Lexeme lexeme) {
        this.visibility = visibility;
        this.form = form;
        this.type = type;
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
        return "\nAttribute{" +
                "name='" + name + '\'' +
                ", visibility=" + visibility +
                ", form=" + form +
                ", type=" + type +
                "}";
    }
}
