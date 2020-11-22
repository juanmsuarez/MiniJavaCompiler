package com.minijava.compiler.semantic.sentences.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public class VariableAccess extends Access {
    public VariableAccess(Lexeme lexeme) {
        super(lexeme);
    }

    @Override
    public String toString() {
        return "VariableAccess{" +
                "lexeme=" + lexeme +
                ", chainedAccess=" + chainedAccess +
                '}';
    }
}