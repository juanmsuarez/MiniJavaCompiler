package com.minijava.compiler.semantic.sentences.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public class DeclarationSentence extends Sentence {
    private String id;

    public DeclarationSentence(Lexeme lexeme) {
        super(lexeme);
        this.id = lexeme.getString();
    }

    @Override
    public String toString() {
        return "DeclarationSentence{" +
                "id=" + id +
                '}';
    }
}
