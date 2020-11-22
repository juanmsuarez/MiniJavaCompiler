package com.minijava.compiler.semantic.declarations.entities;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.modifiers.Form;
import com.minijava.compiler.semantic.declarations.entities.types.ReferenceType;
import com.minijava.compiler.semantic.declarations.entities.types.Type;
import com.minijava.compiler.semantic.declarations.exceptions.InvalidMethodTypeException;

import java.util.Objects;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

public class Method extends Callable {
    private Form form;
    private Type type;
    private Lexeme lexeme;
    private String name;

    public Method(Form form, Type type, String name, Parameter... parameters) {
        super(parameters);
        this.form = form;
        this.type = type;
        this.name = name;
    }

    public Method(Form form, Type type, Lexeme lexeme) {
        this(form, type, lexeme.getString());
        this.lexeme = lexeme;
    }

    public String getName() {
        return name;
    }

    public Lexeme getLexeme() {
        return lexeme;
    }

    @Override
    public boolean validDeclaration() {
        if (!type.isValid()) {
            symbolTable.throwLater(new InvalidMethodTypeException(this, (ReferenceType) type));
            return false;
        }

        return super.validDeclaration();
    }

    public Method instantiate(String newType) {
        Method instantiatedMethod = new Method(form, type.instantiate(newType), name);

        for (Parameter parameter : parameters) {
            instantiatedMethod.add(parameter.instantiate(newType));
        }

        return instantiatedMethod;
    }

    public void checkSentences() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Method method = (Method) o;
        return form == method.form &&
                type.equals(method.type) &&
                name.equals(method.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), form, type, name);
    }

    @Override
    public String toString() {
        return "\nMethod{" +
                "form=" + form +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", parameters=" + parameters +
                ", block=" + block +
                '}';
    }
}
