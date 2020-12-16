package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.Attribute;
import com.minijava.compiler.semantic.declarations.entities.modifiers.Visibility;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;
import com.minijava.compiler.semantic.sentences.exceptions.InvalidChainedVariableAccessException;
import com.minijava.compiler.semantic.sentences.models.Context;

import java.io.IOException;

import static com.minijava.compiler.MiniJavaCompiler.codeGenerator;

public class ChainedVariableAccess extends ChainedAccess {
    private Attribute attribute;

    public ChainedVariableAccess(Lexeme lexeme) {
        super(lexeme);
    }

    @Override
    public void check(Context context) throws SemanticException {
        if (accessedClass == null || !accessedClass.containsAttribute(name) || accessedClass.getAttribute(name).getVisibility() == Visibility.PRIVATE) {
            throw new InvalidChainedVariableAccessException(lexeme);
        }

        attribute = accessedClass.getAttribute(name);
        type = attribute.getType();

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
    public void translate() throws IOException {
        // fix needed if static attributes are allowed (pop this + resolve name)
        if (leftSide && chainedAccess == null) {
            codeGenerator.generate(
                    ".CODE",
                    "SWAP",
                    "STOREREF " + attribute.getOffset()
            );
        } else {
            codeGenerator.generate(
                    ".CODE",
                    "LOADREF " + attribute.getOffset()
            );

            if (chainedAccess != null) {
                chainedAccess.translate();
            }
        }
    }

    @Override
    public String toString() {
        return "ChainedVariableAccess{" +
                "lexeme=" + lexeme +
                ", chainedAccess=" + chainedAccess +
                '}';
    }
}
