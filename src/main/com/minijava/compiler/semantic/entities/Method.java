package com.minijava.compiler.semantic.entities;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.entities.modifiers.Form;
import com.minijava.compiler.semantic.entities.types.Type;
import com.minijava.compiler.semantic.exceptions.DuplicateParameterException;
import com.minijava.compiler.semantic.exceptions.MethodTypeNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

public class Method implements Callable {
    private Form form;
    private Type type;
    private Lexeme lexeme;
    private String name;
    private List<Parameter> parameters = new ArrayList<>();
    private Set<String> parameterNames = new HashSet<>();

    public Method(Form form, Type type, Lexeme lexeme) {
        this.form = form;
        this.type = type;
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
        String parameterName = parameter.getName();

        if (!parameterNames.contains(parameterName)) {
            parameterNames.add(parameterName);
            parameters.add(parameter);
        } else {
            symbolTable.occurred(new DuplicateParameterException(parameter));
        }
    }

    public void checkDeclaration() {
        if (!type.isDefined()) {
            symbolTable.occurred(new MethodTypeNotFoundException(this, type)); // TODO: delete?
        }

        for (Parameter parameter : parameters) {
            parameter.checkDeclaration();
        }
    }

    @Override
    public String toString() {
        return "\nMethod{" +
                "form=" + form +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
