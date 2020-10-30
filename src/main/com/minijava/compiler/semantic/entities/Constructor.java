package com.minijava.compiler.semantic.entities;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.exceptions.DuplicateParameterException;

import java.util.HashMap;
import java.util.Map;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

public class Constructor implements Callable {
    private Lexeme lexeme;
    private String name;
    private Map<String, Parameter> parameters = new HashMap<>(); // TODO: no hace falta mantener ordenados, no?

    public Constructor(String name) {
        this.name = name;
    }

    public Constructor(Lexeme lexeme) {
        this.lexeme = lexeme;
        this.name = lexeme.getString();
    }

    public String getName() {
        return name;
    }

    public Lexeme getLexeme() {
        return lexeme;
    }

    @Override
    public void add(Parameter parameter) {
        String name = parameter.getName();

        if (!parameters.containsKey(name)) {
            parameters.put(name, parameter);
        } else {
            symbolTable.occurred(new DuplicateParameterException(parameter));
        }
    }

    public void checkDeclaration() {
        for (Parameter parameter : parameters.values()) {
            parameter.checkDeclaration();
        }
    }

    @Override
    public String toString() {
        return "\nConstructor{" +
                "name='" + name + '\'' +
                ", parameters=" + parameters +
                '}';
    }

}
