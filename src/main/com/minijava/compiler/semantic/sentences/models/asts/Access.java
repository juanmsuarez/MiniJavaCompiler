package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public abstract class Access extends Operand {
    protected ChainedAccess chainedAccess;
    protected boolean leftSide = false;

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

    public void setLeftSide(boolean leftSide) { // includes chained accesses
        this.leftSide = leftSide;

        if (chainedAccess != null) {
            chainedAccess.setLeftSide(leftSide);
        }
    }

    public abstract boolean isAssignable(); // specific to this access

    public abstract boolean isCallable(); // specific to this access
}
