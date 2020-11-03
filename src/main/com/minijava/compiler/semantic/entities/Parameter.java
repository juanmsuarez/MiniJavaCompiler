package com.minijava.compiler.semantic.entities;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.entities.types.ReferenceType;
import com.minijava.compiler.semantic.entities.types.Type;
import com.minijava.compiler.semantic.exceptions.InvalidParameterTypeException;

import java.util.Objects;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

public class Parameter {
    private Type type;
    private Lexeme lexeme;
    private String name;

    public Parameter(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public Parameter(Type type, Lexeme lexeme) {
        this(type, lexeme.getString());
        this.lexeme = lexeme;
    }

    public Lexeme getLexeme() {
        return lexeme;
    }

    public String getName() {
        return name;
    }

    public boolean validDeclaration() {
        if (!type.isValid()) {
            symbolTable.throwLater(new InvalidParameterTypeException(this, (ReferenceType) type));
            return false;
        }

        return true;
    }

    public Parameter instantiate(String newType) {
        return new Parameter(type.instantiate(newType), lexeme);
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
