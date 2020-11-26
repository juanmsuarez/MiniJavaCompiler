package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.modifiers.Visibility;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;
import com.minijava.compiler.semantic.sentences.exceptions.InvalidChainedVariableAccessException;
import com.minijava.compiler.semantic.sentences.models.Context;

public class ChainedVariableAccess extends ChainedAccess {
    public ChainedVariableAccess(Lexeme lexeme) {
        super(lexeme);
    }

    @Override
    public void check(Context context) throws SemanticException {
        if (accessedClass == null || !accessedClass.containsAttribute(name) || accessedClass.getAttribute(name).getVisibility() == Visibility.PRIVATE) {
            throw new InvalidChainedVariableAccessException(lexeme);
        }

        type = accessedClass.getAttribute(name).getType();

        if (chainedAccess != null) {
            chainedAccess.check(context, type);
            type = chainedAccess.type;
        }
    }

    @Override
    public boolean isAssignable() {
        return true;
    }

    @Override
    public boolean isCallable() {
        return false;
    }

    @Override
    public String toString() {
        return "ChainedVariableAccess{" +
                "lexeme=" + lexeme +
                ", chainedAccess=" + chainedAccess +
                '}';
    }
}
