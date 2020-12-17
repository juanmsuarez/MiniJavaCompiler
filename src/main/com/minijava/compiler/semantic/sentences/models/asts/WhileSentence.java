package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;
import com.minijava.compiler.semantic.sentences.exceptions.ExpectedBooleanConditionException;
import com.minijava.compiler.semantic.sentences.models.Context;

import java.io.IOException;

import static com.minijava.compiler.MiniJavaCompiler.codeGenerator;
import static com.minijava.compiler.MiniJavaCompiler.symbolTable;
import static com.minijava.compiler.semantic.declarations.entities.types.BooleanType.BOOLEAN;

public class WhileSentence extends Sentence {
    private Expression condition;
    private Sentence body;

    public WhileSentence(Lexeme lexeme) {
        super(lexeme);
    }

    public void setCondition(Expression condition) {
        this.condition = condition;
    }

    public void setBody(Sentence body) {
        this.body = body;
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

        Context bodyContext = new Context(previousContext);
        body.check(bodyContext);
    }

    @Override
    public void translate() throws IOException {
        String startLabel = "WHILE_START_" + codeGenerator.newLabelId();
        String endLabel = "WHILE_END_" + codeGenerator.newLabelId();

        codeGenerator.generate(".CODE", startLabel + ": NOP");
        condition.translate();
        codeGenerator.generate(".CODE", "BF " + endLabel);

        body.translate();

        codeGenerator.generate(
                ".CODE",
                "JUMP " + startLabel,
                endLabel + ": NOP"
        );
    }

    @Override
    public String toString() {
        return "\nWhileSentence{" +
                "condition=" + condition +
                ", body=" + body +
                '}';
    }
}
