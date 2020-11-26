package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.Class;
import com.minijava.compiler.semantic.declarations.entities.types.Type;
import com.minijava.compiler.semantic.declarations.exceptions.SemanticException;
import com.minijava.compiler.semantic.sentences.models.Context;

import static com.minijava.compiler.MiniJavaCompiler.symbolTable;

public abstract class ChainedAccess extends Access {
    protected Class accessedClass;
    protected String name;

    public ChainedAccess(Lexeme lexeme) {
        super(lexeme, lexeme);
        this.name = lexeme.getString();
    }

    public void check(Context context, Type accessedType) throws SemanticException {
        this.accessedClass = symbolTable.getClass(accessedType.getName());
        check(context);
    }
}
