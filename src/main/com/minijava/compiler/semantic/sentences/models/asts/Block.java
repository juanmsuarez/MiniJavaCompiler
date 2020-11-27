package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.semantic.sentences.models.Context;

import java.util.ArrayList;
import java.util.List;

public class Block extends Sentence {
    private List<Sentence> sentences = new ArrayList<>();

    public Block() {
        super(null);
    }

    public void add(Sentence sentence) {
        sentences.add(sentence);
    }

    @Override
    public void check(Context previousContext) {
        Context context = new Context(previousContext);
        for (Sentence sentence : sentences) {
            sentence.check(context);
        }
    }

    @Override
    public String toString() {
        return "Block{" +
                "sentences=" + sentences +
                '}';
    }
}
