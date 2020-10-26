package com.minijava.compiler.semantic.symbols;

import java.util.*;

public class SymbolTable {
    private Map<String, Class> classes = new HashMap<>();
    private Set<String> duplicatedNames = new HashSet<>(); // TODO: los nombres solo molestan si se repiten en el mismo contexto?

    private Class currentClass;

    public void add(Class newClass) {
        String name = newClass.getName();

        if (!duplicatedNames.contains(name)) {
            if (!classes.containsKey(name)) {
                classes.put(name, newClass);
            } else {
                classes.remove(name);
                duplicatedNames.add(name); // TODO: mostrar error de duplicado
            }
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
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return "SymbolTable{" +
                "classes=" + classes +
                ", currentClass=" + currentClass +
                '}';
    }
}
