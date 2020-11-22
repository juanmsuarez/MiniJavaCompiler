package com.minijava.compiler.semantic.sentences.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public class ExpressionAccess extends Access {
    private Expression expression;

    public ExpressionAccess(Lexeme lexeme, Expression expression) {
        super(lexeme);
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "ExpressionAccess{" +
                "expression=" + expression +
                ", chainedAccess=" + chainedAccess +
                '}';
    }
}
