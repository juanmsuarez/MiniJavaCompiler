package com.minijava.compiler.semantic.sentences.asts;

import com.minijava.compiler.lexical.models.Token;

public class UnaryExpression extends Expression {
    private Token operator;
    private Operand operand;

    public UnaryExpression(Token operator, Operand operand) {
        super(operator.getLexeme());
        this.operator = operator;
        this.operand = operand;
    }

    @Override
    public String toString() {
        return "UnaryExpression{" +
                "operator=" + operator +
                ", operand=" + operand +
                '}';
    }
}
