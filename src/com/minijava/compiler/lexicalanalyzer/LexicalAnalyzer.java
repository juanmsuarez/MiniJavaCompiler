package com.minijava.compiler.lexicalanalyzer;

import com.minijava.compiler.filemanager.FileManager;
import com.minijava.compiler.lexicalanalyzer.exceptions.*;

import java.io.IOException;

import static com.minijava.compiler.lexicalanalyzer.CharacterUtils.*;
import static com.minijava.compiler.lexicalanalyzer.TokenNames.*;

public class LexicalAnalyzer {
    private FileManager fileManager;
    private Character currentChar;
    private String currentLexeme;
    private int lexemeStartLine;

    public LexicalAnalyzer(FileManager fileManager) throws IOException {
        this.fileManager = fileManager;
        advanceCurrentChar();
    }

    private void advanceCurrentChar() throws IOException {
        currentChar = fileManager.nextChar();
    }

    private void updateLexeme() {
        currentLexeme = currentLexeme + currentChar;
    }

    private Token buildToken(String name) {
        return new Token(name, currentLexeme, lexemeStartLine);
    }

    // Updates current lexeme, advances current char and executes State
    private Token transition(State state) throws IOException, LexicalException { // TODO: no pasa nada si tira lexical en todos?
        updateLexeme();
        advanceCurrentChar();
        return state.execute();
    }

    private void resetLexeme() {
        currentLexeme = "";
        lexemeStartLine = fileManager.getLineNumber();
    }

    public Token nextToken() throws IOException, LexicalException {
        return initialState();
    }

    private Token initialState() throws IOException, LexicalException {
        resetLexeme();

        if (isWhiteSpace(currentChar)) {
            return transition(this::initialState);
        } else if (isUpperCase(currentChar)) {
            return transition(this::classIdState);
        } else if (isLowerCase(currentChar)) {
            return transition(this::varMetIdState);
        } else if (isDigit(currentChar)) {
            return transition(this::intLitState);
        } else if (isSingleQuote(currentChar)) {
            return transition(this::charLitOpenedState);
        } else if (isDoubleQuote(currentChar)) {
            return transition(this::stringLitOpenedState);
        } else if (isPunctuation(currentChar)) {
            return transition(this::punctuationState);
        } else if (isSlash(currentChar)) {
            return transition(this::divOpOrCommentState);
        } else if (isOperator(currentChar)) {
            return transition(this::operatorState);
        } else if (isEOF(currentChar)) {
            return eofState();
        } else {
            updateLexeme();
            advanceCurrentChar();
            throw new InvalidSymbolException(currentLexeme, lexemeStartLine);
        }
    }

    private Token classIdState() throws IOException, LexicalException {
        if (isLetter(currentChar) || isDigit(currentChar) || isUnderscore(currentChar)) {
            return transition(this::classIdState);
        } else {
            return buildToken(KEYWORDS.getOrDefault(currentLexeme, CLASS_ID));
        }
    }

    private Token varMetIdState() throws IOException, LexicalException {
        if (isLetter(currentChar) || isDigit(currentChar) || isUnderscore(currentChar)) {
            return transition(this::varMetIdState);
        } else {
            return buildToken(KEYWORDS.getOrDefault(currentLexeme, VAR_MET_ID));
        }
    }

    private Token intLitState() throws IOException, LexicalException {
        if (isDigit(currentChar)) {
            return transition(this::intLitState);
        } else {
            return buildToken(INT_LITERAL);
        }
    }

    private Token charLitOpenedState() throws IOException, LexicalException {
        if (isEOF(currentChar) || isEOL(currentChar) || isSingleQuote(currentChar)) {
            throw new MalformedCharException(currentLexeme, lexemeStartLine);
        } else if (isBackslash(currentChar)) {
            return transition(this::escapedCharLitOpenedState);
        } else {
            return transition(this::charLitPendingCloseState);
        }
    }

    private Token escapedCharLitOpenedState() throws IOException, LexicalException {
        if (isEOF(currentChar) || isEOL(currentChar)) {
            throw new MalformedCharException(currentLexeme, lexemeStartLine);
        } else {
            return transition(this::charLitPendingCloseState);
        }
    }

    private Token charLitPendingCloseState() throws IOException, LexicalException {
        if (!isSingleQuote(currentChar)) { // TODO: no mostramos este caracter que apareció en lugar de la comilla ('xy), cierto? Y si son solo 2 comillas (''), consumimos? Estos dos serían diferentes errores o puede ser todo literal malformado?
            throw new UnclosedCharException(currentLexeme, lexemeStartLine);
        } else {
            return transition(this::charLitClosedState);
        }
    }

    private Token charLitClosedState() {
        return buildToken(CHAR_LITERAL);
    }

    private Token stringLitOpenedState() throws IOException, LexicalException {
        if (isEOF(currentChar) || isEOL(currentChar)) {
            throw new UnclosedStringException(currentLexeme, lexemeStartLine);
        } else if (!isDoubleQuote(currentChar)) {
            return transition(this::stringLitOpenedState);
        } else {
            return transition(this::stringLitClosedState);
        }
    }

    private Token stringLitClosedState() {
        return buildToken(STRING_LITERAL);
    }

    private Token punctuationState() {
        return buildToken(PUNCTUATION.get(currentLexeme));
    }

    private Token divOpOrCommentState() throws IOException, LexicalException {
        if (isSlash(currentChar)) {
            return transition(this::lineCommentState);
        } else if (isAsterisk(currentChar)) {
            return transition(this::blockCommentOpenedState);
        } else {
            return buildToken(DIV);
        }
    }

    private Token lineCommentState() throws IOException, LexicalException {
        if (!isEOF(currentChar) && !isEOL(currentChar)) {
            return transition(this::lineCommentState);
        } else {
            return initialState();
        }
    }

    // TODO: los errores de comment se muestran con enters en el medio?
    private Token blockCommentOpenedState() throws IOException, LexicalException {
        if (isAsterisk(currentChar)) {
            return blockCommentToCloseState();
        } else if (!isEOF(currentChar)) {
            return transition(this::blockCommentOpenedState);
        } else {
            throw new UnclosedCommentException(currentLexeme, lexemeStartLine);
        }
    }

    private Token blockCommentToCloseState() throws IOException, LexicalException {
        if (isSlash(currentChar)) {
            return transition(this::initialState);
        } else if (isAsterisk(currentChar)) {
            return transition(this::blockCommentToCloseState);
        } else if (!isEOF(currentChar)) {
            return transition(this::blockCommentOpenedState);
        } else {
            throw new UnclosedCommentException(currentLexeme, lexemeStartLine);
        }
    }

    private Token operatorState() throws IOException, LexicalException {
        if (!isEOF(currentChar) && OPERATORS.containsKey(currentLexeme + currentChar)) {
            return transition(this::operatorState);
        } else if (OPERATORS.containsKey(currentLexeme)) {
            return buildToken(OPERATORS.get(currentLexeme));
        } else {
            throw new MalformedOperatorException(currentLexeme, lexemeStartLine);
        }
    }

    private Token eofState() {
        return buildToken(EOF);
    }
}
