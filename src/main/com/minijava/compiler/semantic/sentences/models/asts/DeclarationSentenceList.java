package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.semantic.declarations.entities.types.ReferenceType;
import com.minijava.compiler.semantic.declarations.entities.types.Type;
import com.minijava.compiler.semantic.sentences.exceptions.VariableTypeNotFoundException;
import com.minijava.compiler.semantic.sentences.models.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

public class DeclarationSentenceList extends Sentence {
    private Type type;
    private List<DeclarationSentence> declarationSentences = new ArrayList<>();

    public DeclarationSentenceList(Type type) {
        super(null); // caso especial porque no todos los tipos tienen lexema, podría fixearse en la clase tipo
        this.type = type;
    }

    public void addDeclaration(DeclarationSentence declarationSentence) {
        declarationSentences.add(declarationSentence);
    }

    public Type getType() {
        return type;
    }

    @Override
    public void check(Context context) {
        if (!type.isValid()) {
            symbolTable.throwLater(new VariableTypeNotFoundException((ReferenceType) type));
        } else {
            for (DeclarationSentence declarationSentence : declarationSentences) {
                declarationSentence.check(context);
            }
        }
    }

    @Override
    public void translate() throws IOException {
        // TODO pending
    }

    @Override
    public String toString() {
        return "\nDeclarationSentenceList{" +
                "type=" + type +
                ", declarationSentences=" + declarationSentences +
                '}';
    }
}
