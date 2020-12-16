package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.models.Token;
import com.minijava.compiler.semantic.declarations.entities.types.BooleanType;
import com.minijava.compiler.semantic.declarations.entities.types.IntType;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;
import com.minijava.compiler.semantic.sentences.exceptions.OperatorExpectedBooleanException;
import com.minijava.compiler.semantic.sentences.exceptions.OperatorExpectedConformingTypesException;
import com.minijava.compiler.semantic.sentences.exceptions.OperatorExpectedIntegerException;
import com.minijava.compiler.semantic.sentences.models.Context;

import java.io.IOException;

import static com.minijava.compiler.semantic.declarations.entities.types.BooleanType.BOOLEAN;
import static com.minijava.compiler.semantic.declarations.entities.types.IntType.INT;
import static com.minijava.compiler.semantic.sentences.models.TokenGroups.*;

public class BinaryExpression extends Expression {
    private Expression left;
    private Token operator;
    private Expression right;

    public BinaryExpression(Expression left, Token operator, Expression right) {
        super(left.leftLexeme, operator.getLexeme());
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public void check(Context context) throws SemanticException {
        left.check(context);
        right.check(context);

        String operatorName = operator.getName();
        String leftTypeName = left.type.getName();
        String rightTypeName = right.type.getName();

        if (INT_OPERATORS.contains(operatorName)) {
            if (!leftTypeName.equals(INT) || !rightTypeName.equals(INT)) {
                throw new OperatorExpectedIntegerException(lexeme);
            }
            type = new IntType();
        } else if (BOOLEAN_OPERATORS.contains(operatorName)) {
            if (!leftTypeName.equals(BOOLEAN) || !rightTypeName.equals(BOOLEAN)) {
                throw new OperatorExpectedBooleanException(lexeme);
            }
            type = new BooleanType();
        } else if (EQUALITY_OPERATORS.contains(operatorName)) {
            if (!left.type.isSubtype(right.type) && !right.type.isSubtype(left.type)) {
                throw new OperatorExpectedConformingTypesException(lexeme);
            }
            type = new BooleanType();
        } else if (RELATIONAL_OPERATORS.contains(operatorName)) {
            if (!leftTypeName.equals(INT) || !rightTypeName.equals(INT)) {
                throw new OperatorExpectedIntegerException(lexeme);
            }
            type = new BooleanType();
        }
    }

    @Override
    public void translate() throws IOException { // TODO: CONSULTA qué pasa si no implementabas logro de precedencia?
        // TODO pending
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
