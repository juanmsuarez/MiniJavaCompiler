package com.minijava.compiler.semantic.sentences.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public class ThisAccess extends Access {
    public ThisAccess(Lexeme lexeme) {
        super(lexeme);
    }

    @Override
    public String toString() {
        return "ThisAccess{" +
                "lexeme=" + lexeme +
                ", chainedAccess=" + chainedAccess +
                '}';
    }
}
