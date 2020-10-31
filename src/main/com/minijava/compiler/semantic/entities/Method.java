package com.minijava.compiler.semantic.entities;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.entities.modifiers.Form;
import com.minijava.compiler.semantic.entities.types.Type;
import com.minijava.compiler.semantic.exceptions.MethodTypeNotFoundException;

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
        if (!type.isDefined()) {
            symbolTable.throwLater(new MethodTypeNotFoundException(this, type));
            return false;
        }

        return super.validDeclaration();
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
                '}';
    }
}
