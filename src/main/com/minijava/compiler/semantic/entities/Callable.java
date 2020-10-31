package com.minijava.compiler.semantic.entities;

import com.minijava.compiler.semantic.exceptions.DuplicateParameterException;

import java.util.*;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

public abstract class Callable {
    protected List<Parameter> parameters = new ArrayList<>();
    private Set<String> parameterNames = new HashSet<>();

    public void add(Parameter parameter) {
        String parameterName = parameter.getName();

        if (!parameterNames.contains(parameterName)) {
            parameterNames.add(parameterName);
            parameters.add(parameter);
        } else {
            symbolTable.throwLater(new DuplicateParameterException(parameter));
        }
    }

    public boolean validDeclaration() {
        parameters.removeIf(parameter -> !parameter.validDeclaration());

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Callable callable = (Callable) o;
        return parameters.equals(callable.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameters);
    }
}
