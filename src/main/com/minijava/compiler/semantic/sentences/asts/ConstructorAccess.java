package com.minijava.compiler.semantic.sentences.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;

public class ConstructorAccess extends CallableAccess {
    private String classId;

    public ConstructorAccess(Lexeme lexeme) {
        super(lexeme);
        classId = lexeme.getString();
    }

    @Override
    public String toString() {
        return "ConstructorAccess{" +
                "arguments=" + arguments +
                ", chainedAccess=" + chainedAccess +
                ", lexeme=" + lexeme +
                '}';
    }
}
