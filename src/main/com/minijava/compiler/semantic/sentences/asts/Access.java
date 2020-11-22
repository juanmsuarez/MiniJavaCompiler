package com.minijava.compiler.semantic.sentences.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public abstract class Access extends Operand {
    protected ChainedAccess chainedAccess;

    public Access(Lexeme lexeme) {
        super(lexeme);
    }

    public void setChainedAccess(ChainedAccess chainedAccess) {
        this.chainedAccess = chainedAccess;
    }
}
