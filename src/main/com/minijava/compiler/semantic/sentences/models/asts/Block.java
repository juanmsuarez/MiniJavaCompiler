package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.semantic.sentences.models.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Block extends Sentence {
    private List<Sentence> sentences = new ArrayList<>();

    private Context context;

    public Block() {
        super(null);
    }

    public void add(Sentence sentence) {
        sentences.add(sentence);
    }

    @Override
    public void check(Context previousContext) {
        context = new Context(previousContext);
        for (Sentence sentence : sentences) {
            sentence.check(context);
        }
    }

    @Override
    public void translate() throws IOException {
        for (Sentence sentence : sentences) {
            sentence.translate();
        }
    }

    @Override
    public String toString() {
        return "Block{" +
                "sentences=" + sentences +
                '}';
    }
}
