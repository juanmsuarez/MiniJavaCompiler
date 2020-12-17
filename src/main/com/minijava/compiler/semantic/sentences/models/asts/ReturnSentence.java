package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.Callable;
import com.minijava.compiler.semantic.declarations.entities.modifiers.Form;
import com.minijava.compiler.semantic.declarations.entities.types.Type;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;
import com.minijava.compiler.semantic.sentences.exceptions.InvalidReturnTypeException;
import com.minijava.compiler.semantic.sentences.exceptions.MustReturnExpressionException;
import com.minijava.compiler.semantic.sentences.models.Context;

import java.io.IOException;

import static com.minijava.compiler.MiniJavaCompiler.codeGenerator;
import static com.minijava.compiler.MiniJavaCompiler.symbolTable;
import static com.minijava.compiler.semantic.declarations.entities.types.VoidType.VOID;

public class ReturnSentence extends Sentence {
    Expression expression;

    private Callable callable;

    public ReturnSentence(Lexeme lexeme, Expression expression) {
        super(lexeme);
        this.expression = expression;
    }

    @Override
    public void check(Context context) {
        callable = context.getCurrentCallable();
        Type returnType = callable.getType();

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
    public void translate() throws IOException {
        if (expression != null) {
            expression.translate();

            int returnOffset = 3 + (callable.getForm() == Form.DYNAMIC ? 1 : 0) + callable.parametersSize();
            codeGenerator.generate(
                    ".CODE",
                    "STORE " + returnOffset
            );
        }

        callable.translateReturn();
    }

    @Override
    public String toString() {
        return "\nReturnSentence{" +
                "expression=" + expression +
                '}';
    }
}
