package com.minijava.compiler.semantic.declarations.entities;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.modifiers.Form;
import com.minijava.compiler.semantic.declarations.entities.types.VoidType;
import com.minijava.compiler.semantic.sentences.models.Context;

public class Constructor extends Callable {
    public Constructor(String name) {
        super(Form.DYNAMIC, new VoidType(), name);
    }

    public Constructor(Lexeme lexeme) {
        super(Form.DYNAMIC, new VoidType(), lexeme);
    }

    @Override
    public void checkSentences(Class currentClass) {
        block.check(new Context(currentClass, this));
    }

    @Override
    public String toString() {
        return "Constructor{" +
                "name='" + name + '\'' +
                ", parameters=" + parameters +
                ", block=" + block +
                '}';
    }

}
