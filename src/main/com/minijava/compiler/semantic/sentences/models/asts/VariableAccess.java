package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.Variable;
import com.minijava.compiler.semantic.declarations.entities.modifiers.Form;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;
import com.minijava.compiler.semantic.sentences.exceptions.DynamicAccessInStaticContextException;
import com.minijava.compiler.semantic.sentences.exceptions.VariableNotFoundException;
import com.minijava.compiler.semantic.sentences.models.Context;

import java.io.IOException;

import static com.minijava.compiler.MiniJavaCompiler.codeGenerator;

public class VariableAccess extends Access { // variables, parameters or attributes
    private String name;

    private Variable variable;

    public VariableAccess(Lexeme lexeme) {
        super(lexeme, lexeme);
        this.name = lexeme.getString();
    }

    @Override
    public void check(Context context) throws SemanticException {
        if (!context.isVariableDefined(name)) {
            throw new VariableNotFoundException(lexeme);
        }

        Form form = context.getFormOfVariable(name);
        if (context.isStatic() && form == Form.DYNAMIC) {
            throw new DynamicAccessInStaticContextException(lexeme);
        }

        variable = context.getVariable(name);
        type = variable.getType();

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
    public void translate() throws IOException { // TODO: CONTROLAR en output
        codeGenerator.generate(".CODE");
        if (variable.isInstanceMember()) {
            codeGenerator.generate("LOAD 3");
            if (leftSide && chainedAccess == null) {
                codeGenerator.generate(
                        "SWAP",
                        "STOREREF " + variable.getOffset()
                );
            } else {
                codeGenerator.generate("LOADREF " + variable.getOffset());
            }
        } else {
            if (leftSide && chainedAccess == null) {
                codeGenerator.generate("STORE " + variable.getOffset());
            } else {
                codeGenerator.generate("LOAD " + variable.getOffset());
            }
        }

        if (chainedAccess != null) {
            chainedAccess.translate();
        }
    }

    @Override
    public String toString() {
        return "VariableAccess{" +
                "lexeme=" + lexeme +
                ", chainedAccess=" + chainedAccess +
                '}';
    }
}