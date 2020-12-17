package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.models.Token;
import com.minijava.compiler.semantic.declarations.entities.types.*;
import com.minijava.compiler.semantic.sentences.models.Context;

import java.io.IOException;

import static com.minijava.compiler.MiniJavaCompiler.codeGenerator;
import static com.minijava.compiler.lexical.models.TokenNames.*;
import static com.minijava.compiler.semantic.declarations.entities.types.ReferenceType.NULL;

public class Literal extends Operand {
    private Token literal;

    public Literal(Token literal) {
        super(literal.getLexeme(), literal.getLexeme());
        this.literal = literal;
    }

    @Override
    public void check(Context context) {
        switch (literal.getName()) {
            case STRING_LITERAL:
                type = new StringType();
                break;
            case INT_LITERAL:
                type = new IntType();
                break;
            case CHAR_LITERAL:
                type = new CharType();
                break;
            case TRUE_KW:
            case FALSE_KW:
                type = new BooleanType();
                break;
            case NULL_KW:
                type = new ReferenceType(NULL);
                break;
        }
    }

    @Override
    public void translate() throws IOException {
        switch (literal.getName()) {
            case STRING_LITERAL:
                String stringLiteral = literal.getLexeme().getString().isEmpty() ? "" : "\"" + literal.getLexeme() + "\", ";
                String label = "STRING_" + codeGenerator.newLabel();
                codeGenerator.generate(
                        ".DATA",
                        label + ": DW " + stringLiteral + "0",
                        ".CODE",
                        "PUSH " + label
                );
                break;
            case INT_LITERAL:
                codeGenerator.generate(
                        ".CODE",
                        "PUSH " + literal.getLexeme()
                );
                break;
            case CHAR_LITERAL:
                codeGenerator.generate(
                        ".CODE",
                        "PUSH " + (int) decodeChar(literal.getLexeme().getString())
                );
                break;
            case TRUE_KW:
                codeGenerator.generate(
                        ".CODE",
                        "PUSH 1"
                );
                break;
            case FALSE_KW:
            case NULL_KW:
                codeGenerator.generate(
                        ".CODE",
                        "PUSH 0"
                );
                break;
        }
    }

    private char decodeChar(String encodedChar) {
        char firstChar = encodedChar.charAt(0);
        if (firstChar == '\\') {
            char escapedChar = encodedChar.charAt(1);
            switch (escapedChar) {
                case 'n':
                    return '\n';
                case 't':
                    return '\t';
                default:
                    return escapedChar;
            }
        }

        return firstChar;
    }

    @Override
    public String toString() {
        return "Literal{" +
                "literal=" + literal +
                '}';
    }
}
