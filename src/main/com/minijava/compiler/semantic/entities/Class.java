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
    private String parentName;
    private Constructor constructor;
    private Map<String, Attribute> hiddenAttributes = new HashMap<>();
    private Map<String, Attribute> attributes = new HashMap<>();
    private Map<String, Method> methods = new HashMap<>();

    private Callable currentCallable;
    private boolean consolidated = false;

    public Class() {
    }

    public Class(String name, String parentName) {
        this.name = name;
        this.parentName = parentName;
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

    public void setParentName(String parentName) {
        this.parentName = parentName;
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
        if (!name.equals(OBJECT.name) && !symbolTable.contains(parentName)) {
            symbolTable.throwLater(new ParentNotFoundException(this, parentName));
            parentName = OBJECT.name;
        }
    }

    private boolean validInheritanceChain() {
        Set<String> ancestors = new HashSet<>();
        Class currentClass = this;

        do { // go up in chain
            ancestors.add(currentClass.name);

            currentClass = symbolTable.get(currentClass.parentName);
        } while (currentClass != null && !currentClass.name.equals(OBJECT.name) && !ancestors.contains(currentClass.name));

        if (currentClass != null && !currentClass.name.equals(OBJECT.name)) { // cycle found
            symbolTable.throwLater(new CyclicInheritanceException(currentClass, this));
            return false;
        }

        return true;
    }

    private void checkChildren() {
        attributes.values().removeIf(attribute -> !attribute.validDeclaration());

        if (constructor == null || !constructor.validDeclaration()) {
            constructor = new Constructor(name);
        }

        methods.values().removeIf(method -> !method.validDeclaration());
    }

    public void consolidate() {
        if (consolidated || name.equals(OBJECT.getName())) {
            return;
        }

        Class parent = symbolTable.get(parentName);
        parent.consolidate();

        consolidateAttributes();
        consolidateMethods();

        consolidated = true;
    }

    private void consolidateAttributes() {
        Class parent = symbolTable.get(parentName);

        for (Attribute parentAttribute : parent.attributes.values()) { // TODO: hacemos algo con parent.hiddenAttributes?
            String parentAttributeName = parentAttribute.getName();

            if (attributes.containsKey(parentAttributeName)) {
                hiddenAttributes.put(parentAttributeName, parentAttribute);
            } else {
                attributes.put(parentAttributeName, parentAttribute);
            }
        }

    }

    private void consolidateMethods() {
        Class parent = symbolTable.get(parentName);

        for (Method parentMethod : parent.methods.values()) {
            String parentMethodName = parentMethod.getName();

            Method childMethod = methods.get(parentMethodName);
            if (childMethod != null && !childMethod.equals(parentMethod)) { // remove if it's invalidly redefined
                symbolTable.throwLater(new InvalidRedefinitionException(childMethod));
                methods.remove(childMethod.getName());
            }

            if (!methods.containsKey(parentMethodName)) { // add if not redefined
                methods.put(parentMethodName, parentMethod);
            }
        }
    }

    @Override
    public String toString() {
        return "\nClass{" +
                "name='" + name + '\'' +
                ", parent='" + parentName + '\'' +
                ", \nconstructor=" + constructor +
                ", \nhiddenAttributes=" + hiddenAttributes +
                ", \nattributes=" + attributes +
                ", \nmethods=" + methods +
                '}';
    }
}
