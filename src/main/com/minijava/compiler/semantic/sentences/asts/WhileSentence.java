package com.minijava.compiler.semantic.sentences.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;

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
    public String toString() {
        return "\nWhileSentence{" +
                "condition=" + condition +
                ", body=" + body +
                '}';
    }
}
