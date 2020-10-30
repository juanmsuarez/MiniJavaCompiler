package com.minijava.compiler.semantic.entities;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.exceptions.*;

import java.util.HashMap;
import java.util.Map;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

public class Class {
    private Lexeme lexeme;
    private String name;
    private String parent;
    private Constructor constructor;
    private Map<String, Attribute> attributes = new HashMap<>();
    private Map<String, Method> methods = new HashMap<>();

    private Callable currentCallable;

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
            symbolTable.occurred(new DuplicateAttributeException(attribute));
        }
    }

    public void add(Constructor constructor) {
        if (constructor.getName().equals(name)) {
            if (this.constructor == null) {
                this.constructor = constructor;
            } else {
                symbolTable.occurred(new DuplicateConstructorException(constructor));
            }
        } else {
            symbolTable.occurred(new InvalidConstructorException(constructor));
        }

        currentCallable = constructor;
    }

    public void add(Method method) {
        String name = method.getName();

        if (!methods.containsKey(name)) {
            methods.put(name, method);
        } else {
            symbolTable.occurred(new DuplicateMethodException(method));
        }

        currentCallable = method;
    }

    public Callable getCurrentCallable() {
        return currentCallable;
    }

    public void checkDeclaration() {
        // my checks
        if (!symbolTable.contains(parent)) {
            symbolTable.occurred(new ParentNotFoundException(this, parent)); // TODO: delete?
        }

        // check children
        for (Attribute attribute : attributes.values()) {
            attribute.checkDeclaration();
        }

        if (constructor != null) {
            constructor.checkDeclaration();
        }

        for (Method method : methods.values()) {
            method.checkDeclaration();
        }
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
