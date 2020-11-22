package com.minijava.compiler.semantic.sentences.asts;

public class EmptySentence extends Sentence {
    public EmptySentence() {
        super(null);
    }

    @Override
    public String toString() {
        return "\nEmptySentence{}";
    }
}
