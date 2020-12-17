package com.minijava.compiler.semantic.declarations.entities;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.modifiers.Form;
import com.minijava.compiler.semantic.declarations.entities.types.Type;
import com.minijava.compiler.semantic.declarations.exceptions.DuplicateParameterException;
import com.minijava.compiler.semantic.sentences.models.Context;
import com.minijava.compiler.semantic.sentences.models.asts.Block;
import com.minijava.compiler.semantic.sentences.models.entities.LocalVariable;

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

    protected List<LocalVariable> localVariables = new ArrayList<>();
    protected String label;

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

        generateParameterOffsets();
        generateLabel();

        return true;
    }

    private void generateParameterOffsets() {
        int parametersBase = (form == Form.DYNAMIC ? 3 : 2) + parameters.size();

        for (int i = 0; i < parameters.size(); i++) {
            parameters.get(i).setOffset(parametersBase - i);
        }
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public void checkSentences(Class currentClass) {
        block.check(new Context(currentClass, this));

        generateVariableOffsets();
    }

    public void add(LocalVariable localVariable) {
        localVariables.add(localVariable);
    }

    private void generateVariableOffsets() {
        for (int i = 0; i < localVariables.size(); i++) {
            localVariables.get(i).setOffset(-i);
        }
    }

    public void translate() throws IOException {
        translateEntry();

        block.translate();

        translateReturn();
    }

    private void translateEntry() throws IOException {
        codeGenerator.generate(
                ".CODE",
                getLabel() + ": LOADFP",
                "LOADSP",
                "STOREFP",
                "RMEM " + localVariables.size()
        );
    }

    public void translateReturn() throws IOException {
        int upperFrameSize = (form == Form.DYNAMIC ? 1 : 0) + parameters.size();

        codeGenerator.generate(
                ".CODE",
                "FMEM " + localVariables.size(),
                "STOREFP",
                "RET " + upperFrameSize
        );
    }

    public String getLabel() {
        return label;
    }

    protected abstract void generateLabel();

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
