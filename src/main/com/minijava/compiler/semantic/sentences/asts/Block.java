package com.minijava.compiler.semantic.sentences.asts;

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
    public String toString() {
        return "Block{" +
                "sentences=" + sentences +
                '}';
    }
}
