package com.minijava.compiler.semantic.entities;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.exceptions.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;
import static com.minijava.compiler.semantic.entities.PredefinedEntities.OBJECT;

public class Class {
    private Lexeme lexeme;
    private String name;
    private String parent;
    private Constructor constructor;
    private Map<String, Attribute> attributes = new HashMap<>();
    private Map<String, Method> methods = new HashMap<>();

    private Callable currentCallable;

    public Class() {
    }

    public Class(String name, String parent) {
        this.name = name;
        this.parent = parent;
    }

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

    public Method getMethod(String name) {
        return methods.get(name);
    }

    public void add(Attribute attribute) {
        String name = attribute.getName();

        if (!attributes.containsKey(name)) {
            attributes.put(name, attribute);
        } else {
            symbolTable.throwLater(new DuplicateAttributeException(attribute));
        }
    }

    public void add(Constructor constructor) {
        if (constructor.getName().equals(name)) {
            if (this.constructor == null) {
                this.constructor = constructor;
            } else {
                symbolTable.throwLater(new DuplicateConstructorException(constructor));
            }
        } else {
            symbolTable.throwLater(new InvalidConstructorException(constructor));
        }

        currentCallable = constructor;
    }

    public void add(Method method) {
        String name = method.getName();

        if (!methods.containsKey(name)) {
            methods.put(name, method);
        } else {
            symbolTable.throwLater(new DuplicateMethodException(method));
        }

        currentCallable = method;
    }

    public Callable getCurrentCallable() {
        return currentCallable;
    }

    public boolean validDeclaration() {
        checkParentExists();
        if (!validInheritanceChain()) {
            return false;
        }

        checkChildren();

        return true;
    }

    private void checkParentExists() {
        if (!name.equals(OBJECT.name) && !symbolTable.contains(parent)) {
            symbolTable.throwLater(new ParentNotFoundException(this, parent));
            parent = OBJECT.name;
        }
    }

    private boolean validInheritanceChain() {
        Set<String> ancestors = new HashSet<>();
        Class currentClass = this;

        do { // go up in chain
            ancestors.add(currentClass.name);

            currentClass = symbolTable.get(currentClass.parent);
        } while (currentClass != null && !currentClass.name.equals(OBJECT.name) && !ancestors.contains(currentClass.name));

        if (currentClass != null && !currentClass.name.equals(OBJECT.name)) { // cycle found
            symbolTable.throwLater(new CyclicInheritanceException(currentClass, this));
            return false;
        }

        return true;
    }

    private void checkChildren() {
        attributes.values().removeIf(attribute -> !attribute.validDeclaration());

        if (constructor != null && !constructor.validDeclaration()) {
            constructor = null;
        }

        methods.values().removeIf(method -> !method.validDeclaration());
    }

    private void consolidate() {
        // TODO: generate constructor
        // TODO: bring methods from above (redefine)
        // TODO: bring attributes from above (hide)
    }

    @Override
    public String toString() {
        return "\nClass{" +
                "name='" + name + '\'' +
                ", parent='" + parent + '\'' +
                ", \nconstructor=" + constructor +
                ", \nattributes=" + attributes +
                ", \nmethods=" + methods +
                '}';
    }
}
