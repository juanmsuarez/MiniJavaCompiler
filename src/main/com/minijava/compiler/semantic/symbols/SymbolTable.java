package com.minijava.compiler.semantic.symbols;

import com.minijava.compiler.semantic.entities.Class;
import com.minijava.compiler.semantic.exceptions.DuplicatedClassException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable {
    public static final String OBJECT = "Object";

    private Map<String, Class> classes = new HashMap<>();
    private List<Exception> exceptions = new ArrayList<>();

    private Class currentClass;

    public SymbolTable() {
        initialize();
    }

    private void initialize() {
        // TODO: initialize Object y System
    }

    public void add(Class newClass) { // TODO: interfaces deberían estar acá también
        String name = newClass.getName();

        if (!classes.containsKey(name)) {
            classes.put(name, newClass);
        } else {
            exceptions.add(new DuplicatedClassException(newClass));
        }
    }

    public void setCurrentClass(Class currentClass) {
        this.currentClass = currentClass;
    }

    public Class getCurrentClass() {
        return currentClass;
    }

    public void checkDeclarations() { // mejor nombre?

    }

    public void consolidate() {

    }

    public List<Exception> getExceptions() {
        return exceptions;
    }

    @Override
    public String toString() {
        return "SymbolTable{" +
                "classes=" + classes +
                '}';
    }
}
