package com.minijava.compiler.semantic.sentences.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public class MethodAccess extends CallableAccess {
    public MethodAccess(Lexeme lexeme) {
        super(lexeme);
    }

    @Override
    public String toString() {
        return "MethodAccess{" +
                "lexeme=" + lexeme +
                ",arguments=" + arguments +
                ", chainedAccess=" + chainedAccess +
                '}';
    }
}
