package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public abstract class Access extends Operand {
    protected ChainedAccess chainedAccess;

    public Access(Lexeme leftLexeme, Lexeme lexeme) {
        super(leftLexeme, lexeme);
    }

    public void setChainedAccess(ChainedAccess chainedAccess) {
        this.chainedAccess = chainedAccess;
    }

    public Access getLastInChain() {
        if (chainedAccess == null) {
            return this;
        }

        return chainedAccess.getLastInChain();
    }

    public abstract boolean isAssignable(); // específico a este acceso, mientras que tipo incluye encadenados

    public abstract boolean isCallable();
}
