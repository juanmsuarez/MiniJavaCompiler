package com.minijava.compiler.semantic.sentences.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public class StaticVariableAccess extends Access {
    private Lexeme classLexeme;
    private Lexeme variableLexeme;

    public StaticVariableAccess(Lexeme classLexeme, Lexeme variableLexeme) {
        super(variableLexeme);
        this.classLexeme = classLexeme;
        this.variableLexeme = variableLexeme;
    }

    @Override
    public String toString() {
        return "StaticVariableAccess{" +
                "classLexeme=" + classLexeme +
                ", variableLexeme=" + variableLexeme +
                ", chainedAccess=" + chainedAccess +
                '}';
    }
}
