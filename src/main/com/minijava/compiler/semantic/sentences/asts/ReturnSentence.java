package com.minijava.compiler.semantic.sentences.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public class ReturnSentence extends Sentence {
    Expression expression;

    public ReturnSentence(Lexeme lexeme, Expression expression) {
        super(lexeme);
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "\nReturnSentence{" +
                "expression=" + expression +
                '}';
    }
}
