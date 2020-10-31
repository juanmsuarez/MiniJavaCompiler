package com.minijava.compiler.semantic.entities;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.entities.modifiers.Form;
import com.minijava.compiler.semantic.entities.types.Type;
import com.minijava.compiler.semantic.exceptions.MethodTypeNotFoundException;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

public class Method extends Callable {
    private Form form;
    private Type type;
    private Lexeme lexeme;
    private String name;

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

    public void checkDeclaration() {
        if (!type.isDefined()) {
            symbolTable.throwLater(new MethodTypeNotFoundException(this, type)); // TODO: delete?
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
