package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;
import com.minijava.compiler.semantic.sentences.exceptions.ExpectedBooleanConditionException;
import com.minijava.compiler.semantic.sentences.models.Context;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;
import static com.minijava.compiler.semantic.declarations.entities.types.BooleanType.BOOLEAN;

public class IfElseSentence extends Sentence {
    private Expression condition;
    private Sentence mainBody;
    private Sentence elseBody;

    public IfElseSentence(Lexeme lexeme) {
        super(lexeme);
    }

    public void setCondition(Expression condition) {
        this.condition = condition;
    }

    public void setMainBody(Sentence mainBody) {
        this.mainBody = mainBody;
    }

    public void setElseBody(Sentence elseBody) {
        this.elseBody = elseBody;
    }

    @Override
    public void check(Context previousContext) {
        try {
            condition.check(previousContext);
            if (!condition.type.getName().equals(BOOLEAN)) {
                throw new ExpectedBooleanConditionException(lexeme);
            }
        } catch (SemanticException exception) {
            symbolTable.throwLater(exception);
        }

        Context mainBodyContext = new Context(previousContext);
        mainBody.check(mainBodyContext);

        if (elseBody != null) {
            Context elseBodyContext = new Context(previousContext);
            elseBody.check(elseBodyContext);
        }
    }

    @Override
    public String toString() {
        return "\nIfElseSentence{" +
                "condition=" + condition +
                ", mainBody=" + mainBody +
                ", elseBody=" + elseBody +
                '}';
    }
}
