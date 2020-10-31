package com.minijava.compiler.semantic;

import com.minijava.compiler.semantic.entities.Class;
import com.minijava.compiler.semantic.exceptions.DuplicateClassException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable {
    private Map<String, Class> classes = new HashMap<>();

    private Class currentClass;

    private List<Exception> exceptions = new ArrayList<>();

    public SymbolTable() {
        initialize();
    }

    private void initialize() {
        // TODO: initialize Object y System
    }

    public Class get(String className) {
        return classes.get(className);
    }

    public boolean contains(String className) {
        return classes.containsKey(className);
    }

    public void add(Class newClass) { // TODO: interfaces deberían estar acá también
        String name = newClass.getName();

        if (!classes.containsKey(name)) {
            classes.put(name, newClass);
        } else {
            exceptions.add(new DuplicateClassException(newClass));
        }
    }

    public Class getCurrentClass() {
        return currentClass;
    }

    public void setCurrentClass(Class currentClass) {
        this.currentClass = currentClass;
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }

    public void throwLater(Exception exception) { // TODO: improve name
        exceptions.add(exception);
    }

    public void checkDeclarations() {
        for (Class aClass : classes.values()) {
            aClass.checkDeclaration();
        }

        // TODO: main check
    }

    public void consolidate() {

    }

    @Override
    public String toString() {
        return "SymbolTable{" +
                "classes=" + classes +
                '}';
    }
}
