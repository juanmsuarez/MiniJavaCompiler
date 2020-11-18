package com.minijava.compiler.semantic.declarations.entities;

import com.minijava.compiler.semantic.declarations.entities.modifiers.Form;
import com.minijava.compiler.semantic.declarations.entities.types.*;

public class PredefinedEntities {
    public static final Class OBJECT = new Class("Object") {{
        add(new Constructor("Object"));
    }};

    public static final Class SYSTEM = new Class("System", "Object") {{
        add(new Constructor("System"));

        add(new Method(Form.STATIC, new IntType(), "read"));

        add(new Method(Form.STATIC, new VoidType(), "printB", new Parameter(new BooleanType(), "b")));
        add(new Method(Form.STATIC, new VoidType(), "printC", new Parameter(new CharType(), "c")));
        add(new Method(Form.STATIC, new VoidType(), "printI", new Parameter(new IntType(), "i")));
        add(new Method(Form.STATIC, new VoidType(), "printS", new Parameter(new StringType(), "s")));

        add(new Method(Form.STATIC, new VoidType(), "println"));

        add(new Method(Form.STATIC, new VoidType(), "printBln", new Parameter(new BooleanType(), "b")));
        add(new Method(Form.STATIC, new VoidType(), "printCln", new Parameter(new CharType(), "c")));
        add(new Method(Form.STATIC, new VoidType(), "printIln", new Parameter(new IntType(), "i")));
        add(new Method(Form.STATIC, new VoidType(), "printSln", new Parameter(new StringType(), "s")));
    }};

    public static final Method MAIN = new Method(Form.STATIC, new VoidType(), "main");
}
