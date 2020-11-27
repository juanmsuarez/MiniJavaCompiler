package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.types.Type;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;
import com.minijava.compiler.semantic.sentences.exceptions.InvalidReturnTypeException;
import com.minijava.compiler.semantic.sentences.exceptions.MustReturnExpressionException;
import com.minijava.compiler.semantic.sentences.models.Context;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;
import static com.minijava.compiler.semantic.declarations.entities.types.VoidType.VOID;

public class ReturnSentence extends Sentence {
    Expression expression;

    public ReturnSentence(Lexeme lexeme, Expression expression) {
        super(lexeme);
        this.expression = expression;
    }

    @Override
    public void check(Context context) {
        Type returnType = context.getCurrentCallable().getType();

        try {
            if (expression == null) {
                if (!returnType.getName().equals(VOID)) {
                    throw new MustReturnExpressionException(lexeme);
                }
            } else {
                expression.check(context);
                if (!expression.getType().isSubtype(returnType)) {
                    throw new InvalidReturnTypeException(lexeme);
                }
            }
        } catch (SemanticException exception) {
            symbolTable.throwLater(exception);
        }
    }

    @Override
    public String toString() {
        return "\nReturnSentence{" +
                "expression=" + expression +
                '}';
    }
}
