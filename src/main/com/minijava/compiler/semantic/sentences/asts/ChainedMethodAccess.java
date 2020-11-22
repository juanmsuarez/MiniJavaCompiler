package com.minijava.compiler.semantic.sentences.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public class ChainedMethodAccess extends CallableAccess implements ChainedAccess {
    public ChainedMethodAccess(Lexeme lexeme) {
        super(lexeme);
    }

    @Override
    public String toString() {
        return "ChainedMethodAccess{" +
                "lexeme=" + lexeme +
                ", arguments=" + arguments +
                ", chainedAccess=" + chainedAccess +
                '}';
    }
}
