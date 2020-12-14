package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.Class;
import com.minijava.compiler.semantic.declarations.entities.Constructor;
import com.minijava.compiler.semantic.declarations.entities.types.ReferenceType;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;
import com.minijava.compiler.semantic.sentences.exceptions.ClassInConstructorAccessNotFoundException;
import com.minijava.compiler.semantic.sentences.models.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

public class ConstructorAccess extends Access implements CallableAccess {
    private List<Expression> arguments = new ArrayList<>();
    private String classId;

    public ConstructorAccess(Lexeme newLexeme, Lexeme classLexeme) {
        super(newLexeme, classLexeme);
        classId = classLexeme.getString();
    }

    @Override
    public void add(Expression argument) {
        arguments.add(argument);
    }

    @Override
    public void check(Context context) throws SemanticException {
        Class accessedClass = symbolTable.getClass(classId); // modificar en caso de hacer genericidad
        if (accessedClass == null) {
            throw new ClassInConstructorAccessNotFoundException(lexeme);
        }

        Constructor constructor = accessedClass.getConstructor();
        type = new ReferenceType(classId);

        CallableAccess.checkArguments(constructor, lexeme, arguments, context);
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
        // TODO pending
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
