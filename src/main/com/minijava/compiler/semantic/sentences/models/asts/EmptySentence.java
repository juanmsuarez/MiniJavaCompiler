package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.semantic.sentences.models.Context;

import java.io.IOException;

public class EmptySentence extends Sentence {
    public EmptySentence() {
        super(null);
    }

    @Override
    public void check(Context context) {
    }

    @Override
    public void translate() throws IOException {
    }

    @Override
    public String toString() {
        return "\nEmptySentence{}";
    }
}
