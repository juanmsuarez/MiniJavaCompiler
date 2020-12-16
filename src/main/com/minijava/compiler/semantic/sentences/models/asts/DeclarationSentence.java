package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.types.Type;
import com.minijava.compiler.semantic.sentences.exceptions.DuplicateVariableNameException;
import com.minijava.compiler.semantic.sentences.models.Context;
import com.minijava.compiler.semantic.sentences.models.entities.LocalVariable;

import java.io.IOException;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

public class DeclarationSentence extends Sentence {
    private Type type;
    private String name;

    public DeclarationSentence(Type type, Lexeme lexeme) {
        super(lexeme);
        this.type = type;
        this.name = lexeme.getString();
    }

    @Override
    public void check(Context context) {
        if (context.isMethodVariable(name)) {
            symbolTable.throwLater(new DuplicateVariableNameException(lexeme));
        } else {
            context.add(new LocalVariable(type, name));
        }
    }

    @Override
    public void translate() throws IOException {
    }

    @Override
    public String toString() {
        return "DeclarationSentence{" +
                "id=" + name +
                '}';
    }
}
