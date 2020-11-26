package com.minijava.compiler.semantic.sentences.models.asts;

import com.minijava.compiler.lexical.models.Token;
import com.minijava.compiler.semantic.declarations.entities.types.*;
import com.minijava.compiler.semantic.sentences.models.Context;

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
    public String toString() {
        return "Literal{" +
                "literal=" + literal +
                '}';
    }
}
