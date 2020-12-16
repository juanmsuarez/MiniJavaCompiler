package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.Method;
import com.minijava.compiler.semantic.declarations.entities.modifiers.Form;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;
import com.minijava.compiler.semantic.sentences.exceptions.InvalidChainedMethodAccessException;
import com.minijava.compiler.semantic.sentences.models.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.minijava.compiler.MiniJavaCompiler.codeGenerator;

public class ChainedMethodAccess extends ChainedAccess implements CallableAccess {
    private List<Expression> arguments = new ArrayList<>();

    private Method method;

    public ChainedMethodAccess(Lexeme lexeme) {
        super(lexeme);
    }

    @Override
    public void add(Expression argument) {
        arguments.add(argument);
    }

    @Override
    public void check(Context context) throws SemanticException {
        if (accessedClass == null || !accessedClass.containsMethod(name)) {
            throw new InvalidChainedMethodAccessException(lexeme);
        }

        method = accessedClass.getMethod(name);
        type = method.getType();

        CallableAccess.checkArguments(method, lexeme, arguments, context);
        if (chainedAccess != null) {
            chainedAccess.check(context, type);
            type = chainedAccess.type;
        }
    }

    @Override
    public boolean isAssignable() {
        return false;
    }

    @Override
    public boolean isCallable() {
        return true;
    }

    @Override
    public void translate() throws IOException {
        if (method.getForm() == Form.STATIC) {
            codeGenerator.generate(
                    ".CODE",
                    "POP"
            );
        }
        CallableAccess.reserveMemoryForReturnTypeIfNeeded(method.getType(), method.getForm());

        CallableAccess.translateArguments(arguments, method.getForm());

        CallableAccess.call(method);

        if (chainedAccess != null) {
            chainedAccess.translate();
        }
    }

    @Override
    public String toString() {
        return "ChainedMethodAccess{" +
                "lexeme=" + lexeme +
                ", arguments=" + arguments +
                ", chainedAccess=" + chainedAccess +
                '}';
    }
}
