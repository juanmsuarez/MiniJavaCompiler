package com.minijava.compiler.semantic.declarations.entities;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.modifiers.Form;
import com.minijava.compiler.semantic.declarations.entities.types.Type;
import com.minijava.compiler.semantic.declarations.exceptions.DuplicateParameterException;
import com.minijava.compiler.semantic.sentences.models.asts.Block;

import java.io.IOException;
import java.util.*;

import static com.minijava.compiler.MiniJavaCompiler.codeGenerator;
import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

public abstract class Callable {
    protected Form form;
    protected Type type;
    protected Lexeme lexeme;
    protected String name;

    protected List<Parameter> parameters = new ArrayList<>();
    private Set<String> parameterNames = new HashSet<>();

    protected Block block = new Block();

    public Callable(Form form, Type type, String name, Parameter... parameters) {
        this.form = form;
        this.type = type;
        this.name = name;

        for (Parameter parameter : parameters) {
            this.parameterNames.add(parameter.getName());
            this.parameters.add(parameter);
        }
    }

    public Callable(Form form, Type type, Lexeme lexeme, Parameter... parameters) {
        this(form, type, lexeme.getString(), parameters);
        this.lexeme = lexeme;
    }

    public Form getForm() {
        return form;
    }

    public Type getType() {
        return type;
    }

    public Lexeme getLexeme() {
        return lexeme;
    }

    public String getName() {
        return name;
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

    public boolean containsParameter(String parameterName) {
        return parameterNames.contains(parameterName);
    }

    public Parameter getParameter(String parameterName) {
        for (Parameter parameter : parameters) {
            if (parameter.getName().equals(parameterName)) {
                return parameter;
            }
        }
        return null;
    }

    public Parameter getParameter(int position) {
        return parameters.get(position);
    }

    public int parametersSize() {
        return parameters.size();
    }

    public boolean validDeclaration() {
        parameters.removeIf(parameter -> !parameter.validDeclaration());

        return true;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public abstract void checkSentences(Class currentClass);

    public void translate() throws IOException {
        codeGenerator.generate(
                ".CODE",
                getLabel() + ": LOADFP",
                "LOADSP",
                "STOREFP"
        );

        block.translate();

        codeGenerator.generate(
                ".CODE",
                "STOREFP",
                "RET " + parameters.size()
        );
    }

    public abstract String getLabel();

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
