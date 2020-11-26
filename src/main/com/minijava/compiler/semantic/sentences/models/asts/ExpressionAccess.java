package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;
import com.minijava.compiler.semantic.sentences.models.Context;

public class ExpressionAccess extends Access {
    private Expression expression;

    public ExpressionAccess(Lexeme lexeme, Expression expression) {
        super(lexeme, lexeme);
        this.expression = expression;
    }

    @Override
    public void check(Context context) throws SemanticException {
        expression.check(context);

        type = expression.type;

        if (chainedAccess != null) {
            chainedAccess.check(context, type);
            type = chainedAccess.type;
        }
    }

    @Override
    public boolean isAssignable() {
        return false;
    }

    @Override
    public boolean isCallable() {
        return false;
    }

    @Override
    public String toString() {
        return "ExpressionAccess{" +
                "expression=" + expression +
                ", chainedAccess=" + chainedAccess +
                '}';
    }
}
