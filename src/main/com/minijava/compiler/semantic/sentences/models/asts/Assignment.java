package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.models.Token;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;
import com.minijava.compiler.semantic.sentences.exceptions.InvalidAssignmentException;
import com.minijava.compiler.semantic.sentences.exceptions.InvalidCompositeAssignmentException;
import com.minijava.compiler.semantic.sentences.models.Context;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;
import static com.minijava.compiler.semantic.declarations.entities.types.IntType.INT;
import static com.minijava.compiler.semantic.sentences.models.TokenGroups.COMPOSITE_ASSIGNMENT;

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
    public void check(Context context) {
        try {
            access.check(context);
            expression.check(context);

            if (COMPOSITE_ASSIGNMENT.contains(type.getName())) {
                if (!access.type.getName().equals(INT) || !expression.type.getName().equals(INT)) {
                    throw new InvalidCompositeAssignmentException(lexeme);
                }
            }

            if (!access.getLastInChain().isAssignable() || !expression.type.isSubtype(access.type)) {
                throw new InvalidAssignmentException(lexeme);
            }
        } catch (SemanticException exception) {
            symbolTable.throwLater(exception);
        }
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
