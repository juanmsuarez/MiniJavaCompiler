package com.minijava.compiler.semantic.entities;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.exceptions.DuplicatedAttributeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Class {
    private String name;
    private String parent;
    private Map<String, Attribute> attributes = new HashMap<>();

    // detailed error
    private Lexeme lexeme;

    private List<Exception> exceptions = new ArrayList<>();

    public String getName() {
        return name;
    }

    public Lexeme getLexeme() {
        return lexeme;
    }

    public void setLexeme(Lexeme lexeme) {
        this.lexeme = lexeme;
        this.name = lexeme.getString();
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void add(Attribute attribute) {
        String name = attribute.getName();

        if (!attributes.containsKey(name)) {
            attributes.put(name, attribute);
        } else {
            exceptions.add(new DuplicatedAttributeException(attribute));
        }
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }

    @Override
    public String toString() {
        return "\nClass{" +
                "name='" + name + '\'' +
                ", parent='" + parent + '\'' +
                ", attributes=" + attributes +
                "}";
    }
}
