package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.semantic.sentences.models.Context;

public class EmptySentence extends Sentence {
    public EmptySentence() {
        super(null);
    }

    @Override
    public void check(Context context) {
    }

    @Override
    public String toString() {
        return "\nEmptySentence{}";
    }
}
