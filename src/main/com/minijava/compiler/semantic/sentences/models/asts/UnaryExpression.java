package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.models.Token;
import com.minijava.compiler.semantic.declarations.entities.types.BooleanType;
import com.minijava.compiler.semantic.declarations.entities.types.IntType;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;
import com.minijava.compiler.semantic.sentences.exceptions.OperatorExpectedBooleanException;
import com.minijava.compiler.semantic.sentences.exceptions.OperatorExpectedIntegerException;
import com.minijava.compiler.semantic.sentences.models.Context;

import java.io.IOException;

import static com.minijava.compiler.lexical.models.TokenNames.*;
import static com.minijava.compiler.semantic.declarations.entities.types.BooleanType.BOOLEAN;
import static com.minijava.compiler.semantic.declarations.entities.types.IntType.INT;

public class UnaryExpression extends Expression {
    private Token operator;
    private Operand operand;

    public UnaryExpression(Token operator, Operand operand) {
        super(operator.getLexeme(), operator.getLexeme());
        this.operator = operator;
        this.operand = operand;
    }

    @Override
    public void check(Context context) throws SemanticException {
        operand.check(context);

        String operatorName = operator.getName();
        String operandTypeName = operand.type.getName();

        if (operatorName.equals(ADD) || operatorName.equals(SUB)) {
            if (!operandTypeName.equals(INT)) {
                throw new OperatorExpectedIntegerException(lexeme);
            }

            type = new IntType();
        } else if (operatorName.equals(NOT)) {
            if (!operandTypeName.equals(BOOLEAN)) {
                throw new OperatorExpectedBooleanException(lexeme);
            }

            type = new BooleanType();
        }
    }

    @Override
    public void translate() throws IOException {
        // TODO: pending
    }

    @Override
    public String toString() {
        return "UnaryExpression{" +
                "operator=" + operator +
                ", operand=" + operand +
                '}';
    }
}
