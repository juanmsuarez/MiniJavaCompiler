package com.minijava.compiler.semantic.sentences.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public class ChainedVariableAccess extends Access implements ChainedAccess {
    public ChainedVariableAccess(Lexeme lexeme) {
        super(lexeme);
    }

    @Override
    public String toString() {
        return "ChainedVariableAccess{" +
                "lexeme=" + lexeme +
                ", chainedAccess=" + chainedAccess +
                '}';
    }
}
