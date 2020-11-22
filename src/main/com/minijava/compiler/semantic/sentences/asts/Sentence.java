package com.minijava.compiler.semantic.sentences.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public abstract class Sentence {
    protected Lexeme lexeme;

    public Sentence(Lexeme lexeme) {
        this.lexeme = lexeme;
    }

    @Override
    public abstract String toString();
}
