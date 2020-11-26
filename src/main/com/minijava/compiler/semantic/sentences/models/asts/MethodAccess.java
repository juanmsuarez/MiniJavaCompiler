package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.Class;
import com.minijava.compiler.semantic.declarations.entities.Method;
import com.minijava.compiler.semantic.declarations.entities.modifiers.Form;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;
import com.minijava.compiler.semantic.sentences.exceptions.DynamicAccessInStaticContextException;
import com.minijava.compiler.semantic.sentences.exceptions.MethodNotFoundException;
import com.minijava.compiler.semantic.sentences.models.Context;

import java.util.ArrayList;
import java.util.List;

public class MethodAccess extends Access implements CallableAccess {
    private String name;
    private List<Expression> arguments = new ArrayList<>();

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

        Method method = currentClass.getMethod(name);
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
    public String toString() {
        return "MethodAccess{" +
                "lexeme=" + lexeme +
                ",arguments=" + arguments +
                ", chainedAccess=" + chainedAccess +
                '}';
    }
}
