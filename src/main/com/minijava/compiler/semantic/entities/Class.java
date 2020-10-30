package com.minijava.compiler.semantic.entities;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.exceptions.DuplicateAttributeException;
import com.minijava.compiler.semantic.exceptions.DuplicateConstructorException;
import com.minijava.compiler.semantic.exceptions.DuplicateMethodException;
import com.minijava.compiler.semantic.exceptions.InvalidConstructorException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Class {
    private Lexeme lexeme;
    private String name;
    private String parent;
    private Constructor constructor;
    private Map<String, Attribute> attributes = new HashMap<>();
    private Map<String, Method> methods = new HashMap<>();

    private Callable currentCallable;

    private List<Exception> exceptions = new ArrayList<>();

    public Lexeme getLexeme() {
        return lexeme;
    }

    public void setLexeme(Lexeme lexeme) {
        this.lexeme = lexeme;
        this.name = lexeme.getString();
    }

    public String getName() {
        return name;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void add(Attribute attribute) {
        String name = attribute.getName();

        if (!attributes.containsKey(name)) {
            attributes.put(name, attribute);
        } else {
            exceptions.add(new DuplicateAttributeException(attribute));
        }
    }

    public void add(Constructor constructor) {
        if (constructor.getName().equals(name)) {
            if (this.constructor == null) {
                this.constructor = constructor;
            } else {
                exceptions.add(new DuplicateConstructorException(constructor));
            }
        } else {
            exceptions.add(new InvalidConstructorException(constructor));
        }

        currentCallable = constructor;
    }

    public void add(Method method) {
        String name = method.getName();

        if (!methods.containsKey(name)) {
            methods.put(name, method);
        } else {
            exceptions.add(new DuplicateMethodException(method));
        }

        currentCallable = method;
    }

    public Callable getCurrentCallable() {
        return currentCallable;
    }

    public List<Exception> getExceptions() {
        List<Exception> allExceptions = new ArrayList<>(exceptions);

        if (constructor != null) {
            allExceptions.addAll(constructor.getExceptions());
        }

        for (Method method : methods.values()) {
            allExceptions.addAll(method.getExceptions());
        }

        return allExceptions;
    }


    @Override
    public String toString() {
        return "\nClass{" +
                "name='" + name + '\'' +
                ", parent='" + parent + '\'' +
                ", constructor=" + constructor +
                ", attributes=" + attributes +
                ", methods=" + methods +
                '}';
    }
}
