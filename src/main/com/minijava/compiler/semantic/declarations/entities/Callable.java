package com.minijava.compiler.semantic.declarations.entities;

import com.minijava.compiler.semantic.declarations.exceptions.DuplicateParameterException;
import com.minijava.compiler.semantic.sentences.asts.Block;

import java.util.*;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

public abstract class Callable {
    protected List<Parameter> parameters = new ArrayList<>();
    private Set<String> parameterNames = new HashSet<>();
    protected Block block = new Block();

    public Callable(Parameter... parameters) {
        for (Parameter parameter : parameters) {
            this.parameterNames.add(parameter.getName());
            this.parameters.add(parameter);
        }
    }

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

    public void setBlock(Block block) {
        this.block = block;
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
