package com.minijava.compiler.semantic.declarations.entities;

import com.minijava.compiler.lexical.analyzer.Lexeme;
import com.minijava.compiler.semantic.declarations.entities.modifiers.Form;
import com.minijava.compiler.semantic.declarations.entities.types.VoidType;

import static com.minijava.compiler.MiniJavaCompiler.codeGenerator;

public class Constructor extends Callable {
    public Constructor(String name) {
        super(Form.DYNAMIC, new VoidType(), name);
    }

    public Constructor(Lexeme lexeme) {
        super(Form.DYNAMIC, new VoidType(), lexeme);
    }

    @Override
    public String getLabel() {
        if (label == null) {
            label = "CONSTRUCTOR_" + name + '_' + codeGenerator.newLabelId();
        }
        return label;
    }

    @Override
    public String toString() {
        return "Constructor{" +
                "name='" + name + '\'' +
                ", parameters=" + parameters +
                ", block=" + block +
                '}';
    }
}
