package com.minijava.compiler.semantic.sentences.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public class StaticMethodAccess extends CallableAccess {
    private Lexeme classLexeme;
    private Lexeme methodLexeme;

    public StaticMethodAccess(Lexeme classLexeme, Lexeme methodLexeme) {
        super(methodLexeme);
        this.classLexeme = classLexeme;
        this.methodLexeme = methodLexeme;
    }

    @Override
    public String toString() {
        return "StaticMethodAccess{" +
                "classLexeme=" + classLexeme +
                ", methodLexeme=" + methodLexeme +
                ", arguments=" + arguments +
                ", chainedAccess=" + chainedAccess +
                '}';
    }
}
