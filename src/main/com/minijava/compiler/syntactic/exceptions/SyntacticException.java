package com.minijava.compiler.syntactic.exceptions;

import com.minijava.compiler.CompilerException;
import com.minijava.compiler.lexical.models.Token;

public class SyntacticException extends CompilerException {
    private static final String ERROR_MESSAGE = "se esperaba un %s pero se encontró \"%s\"";
    private static final String ERROR_TYPE = "sintáctico";

    public SyntacticException(Token foundToken, String expectedTokenName) {
        super(ERROR_TYPE, foundToken.getLineNumber(),
                String.format(ERROR_MESSAGE, expectedTokenName, foundToken.getLexeme()), foundToken.getLexeme()); // TODO: el error "se espera..." muestra el nombre de token propio de nuestro compi? un nombre más amigable (hace falta codificarlo?)? lista las posibilidades? y el lexema erróneo que se encontró muestra el original? si EOF vacío nomás?
    }

    @Override
    protected String detailString() { // TODO: cuál es la dificultad en la detail string del sintáctico respecto al léxico?
        return "";
    }
}
