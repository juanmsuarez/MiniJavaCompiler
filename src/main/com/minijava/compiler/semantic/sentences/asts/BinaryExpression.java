package com.minijava.compiler.semantic.sentences.asts;

import com.minijava.compiler.lexical.models.Token;

public class BinaryExpression extends Expression {
    private Expression left;
    private Token operator;
    private Expression right;

    public BinaryExpression(Expression left, Token operator, Expression right) {
        super(left.lexeme);
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public String toString() {
        return "BinaryExpression{" +
                "left=" + left +
                ", operator=" + operator +
                ", right=" + right +
                '}';
    }
}
