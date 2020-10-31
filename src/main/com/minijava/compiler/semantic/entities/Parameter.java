package com.minijava.compiler.semantic.entities;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.entities.types.Type;
import com.minijava.compiler.semantic.exceptions.ParameterTypeNotFoundException;

import java.util.Objects;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

public class Parameter {
    private Type type;
    private Lexeme lexeme;
    private String name;

    public Parameter(Type type, Lexeme lexeme) {
        this.type = type;
        this.lexeme = lexeme;
        this.name = lexeme.getString();
    }

    public Lexeme getLexeme() {
        return lexeme;
    }

    public String getName() {
        return name;
    }

    public boolean validDeclaration() {
        if (!type.isDefined()) {
            symbolTable.throwLater(new ParameterTypeNotFoundException(this, type));
            return false;
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameter parameter = (Parameter) o;
        return type.equals(parameter.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
