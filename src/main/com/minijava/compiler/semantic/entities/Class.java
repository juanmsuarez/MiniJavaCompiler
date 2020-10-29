package com.minijava.compiler.semantic.entities;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.exceptions.DuplicatedAttributeException;

import java.util.HashMap;
import java.util.Map;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

public class Class { // TODO: extends Tipo Referencia, cómo chequear igualdad?
    private String name;
    private String parent;
    private Map<String, Attribute> attributes = new HashMap<>();

    // detailed error
    private Lexeme lexeme;

    public String getName() {
        return name;
    }

    public Lexeme getLexeme() {
        return lexeme;
    }

    public void setLexeme(Lexeme lexeme) {
        this.lexeme = lexeme;
        this.name = lexeme.getString();
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void add(Attribute attribute) {
        String name = attribute.getName();

        if (!attributes.containsKey(name)) {
            attributes.put(name, attribute);
        } else {
            symbolTable.getExceptions().add(new DuplicatedAttributeException(attribute)); // TODO: addException?
        }
    }

    @Override
    public String toString() {
        return "\nClass{" +
                "name='" + name + '\'' +
                ", parent='" + parent + '\'' +
                ", attributes=" + attributes +
                "}";
    }
}
