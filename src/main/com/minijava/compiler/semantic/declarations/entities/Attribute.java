package com.minijava.compiler.semantic.declarations.entities;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.modifiers.Form;
import com.minijava.compiler.semantic.declarations.entities.modifiers.Visibility;
import com.minijava.compiler.semantic.declarations.entities.types.ReferenceType;
import com.minijava.compiler.semantic.declarations.entities.types.Type;
import com.minijava.compiler.semantic.declarations.exceptions.InvalidAttributeTypeException;

import java.util.Objects;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

public class Attribute extends Variable {
    private Visibility visibility;
    private Form form;
    private Lexeme lexeme;

    private Class classUnit;

    private int offset = -1;

    public Attribute(Visibility visibility, Form form, Type type, Lexeme lexeme) {
        super(type, lexeme.getString());
        this.visibility = visibility;
        this.form = form;
        this.lexeme = lexeme;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public Lexeme getLexeme() {
        return lexeme;
    }

    public Form getForm() {
        return form;
    }

    public Class getClassUnit() {
        return classUnit;
    }

    public void setClassUnit(Class classUnit) {
        this.classUnit = classUnit;
    }

    public boolean validDeclaration() {
        if (!type.isValid()) {
            symbolTable.throwLater(new InvalidAttributeTypeException(this, (ReferenceType) type));
            return false;
        }

        return true;
    }

    @Override
    public boolean isInstanceAttribute() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attribute attribute = (Attribute) o;
        return name.equals(attribute.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "\nAttribute{" +
                "name='" + name + '\'' +
                ", visibility=" + visibility +
                ", form=" + form +
                ", type=" + type +
                "}";
    }
}
