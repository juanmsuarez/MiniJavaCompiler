package com.minijava.compiler.semantic.sentences.asts;

import com.minijava.compiler.semantic.declarations.entities.types.Type;

import java.util.ArrayList;
import java.util.List;

public class DeclarationSentenceList extends Sentence {
    private Type type;
    private List<DeclarationSentence> declarationSentences = new ArrayList<>();

    public DeclarationSentenceList(Type type) { // TODO: dónde marcar el error de tipo, en la variable o en el tipo?
        super(null);
        this.type = type;
    }

    public void addDeclaration(DeclarationSentence declarationSentence) {
        declarationSentences.add(declarationSentence);
    }

    @Override
    public String toString() {
        return "\nDeclarationSentenceList{" +
                "type=" + type +
                ", declarationSentences=" + declarationSentences +
                '}';
    }
}
