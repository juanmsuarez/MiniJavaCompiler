package com.minijava.compiler.semantic.symbols;

public class Class {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Class{" +
                "name='" + name + '\'' +
                '}';
    }
}
