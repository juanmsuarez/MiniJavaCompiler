package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.Class;
import com.minijava.compiler.semantic.declarations.entities.Method;
import com.minijava.compiler.semantic.declarations.entities.modifiers.Form;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;
import com.minijava.compiler.semantic.sentences.exceptions.DynamicAccessInStaticContextException;
import com.minijava.compiler.semantic.sentences.exceptions.MethodNotFoundException;
import com.minijava.compiler.semantic.sentences.models.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.minijava.compiler.MiniJavaCompiler.codeGenerator;

public class MethodAccess extends Access implements CallableAccess {
    private String name;
    private List<Expression> arguments = new ArrayList<>();

    private Method method;

    public MethodAccess(Lexeme lexeme) {
        super(lexeme, lexeme);
        name = lexeme.getString();
    }

    @Override
    public void add(Expression argument) {
        arguments.add(argument);
    }

    @Override
    public void check(Context context) throws SemanticException {
        Class currentClass = context.getCurrentClass();
        if (!currentClass.containsMethod(name)) {
            throw new MethodNotFoundException(lexeme);
        }

        method = currentClass.getMethod(name);
        if (context.isStatic() && method.getForm() == Form.DYNAMIC) {
            throw new DynamicAccessInStaticContextException(lexeme);
        }

        CallableAccess.checkArguments(method, lexeme, arguments, context);

        type = method.getType();

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
        if (method.getForm() == Form.DYNAMIC) {
            codeGenerator.generate(
                    ".CODE",
                    "LOAD 3"
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
        return "MethodAccess{" +
                "lexeme=" + lexeme +
                ",arguments=" + arguments +
                ", chainedAccess=" + chainedAccess +
                '}';
    }
}
