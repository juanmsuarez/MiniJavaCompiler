package com.minijava.compiler.semantic.sentences.asts;

import com.minijava.compiler.lexical.models.Token;

public class Assignment extends Sentence {
    private Access access;
    private Token type;
    private Expression expression;

    public Assignment(Access access, Token type, Expression expression) {
        super(type.getLexeme());
        this.access = access;
        this.type = type;
        this.expression = expression;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "\nAssignment{" +
                "access=" + access +
                ", type=" + type +
                ", expression=" + expression +
                '}';
    }
}
