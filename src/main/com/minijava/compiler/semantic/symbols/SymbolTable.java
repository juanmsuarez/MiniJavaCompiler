package com.minijava.compiler.semantic.symbols;

import java.util.*;

public class SymbolTable {
    private Map<String, Class> classes = new HashMap<>();
    private Set<String> duplicatedNames = new HashSet<>();
    private List<Exception> exceptions = new ArrayList<>();

    private Class currentClass;

    public void add(Class newClass) { // TODO: qué pasa con las interfaces?
        String name = newClass.getName();

        if (!duplicatedNames.contains(name)) {
            if (!classes.containsKey(name)) {
                classes.put(name, newClass);
            } else {
                // TODO: borramos también la primera o la dejamos? no mostramos error para la primera?
                duplicatedNames.add(name);
                exceptions.add(new DuplicatedClassException(newClass));
            }
        } else {
            exceptions.add(new DuplicatedClassException(newClass));
        }
    }

    public boolean contains(Class usedClass) {
        return classes.containsKey(usedClass.getName());
    }

    public Class get(String className) {
        return classes.get(className);
    }

    public void setCurrentClass(Class currentClass) {
        this.currentClass = currentClass;
    }

    public Class getCurrentClass() {
        return currentClass;
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
                ", currentClass=" + currentClass +
                '}';
    }
}
