package com.minijava.compiler.lexicalanalyzer;

import com.minijava.compiler.filemanager.FileManager;
import com.minijava.compiler.lexicalanalyzer.exceptions.InvalidSymbolException;
import com.minijava.compiler.lexicalanalyzer.exceptions.LexicalException;
import com.minijava.compiler.lexicalanalyzer.exceptions.MalformedCharException;
import com.minijava.compiler.lexicalanalyzer.exceptions.MalformedStringException;

import java.io.IOException;

import static com.minijava.compiler.lexicalanalyzer.TokenNames.*;

public class LexicalAnalyzer {
    private FileManager fileManager;
    private Character currentChar;
    private String currentLexeme;

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
        return new Token(name, currentLexeme, fileManager.getLineNumber());
    }

    // Updates current lexeme, advances current char and executes State
    private Token transition(State state) throws IOException, LexicalException {
        updateLexeme();
        advanceCurrentChar();
        return state.execute();
    }

    public Token nextToken() throws IOException, LexicalException {
        currentLexeme = "";
        return initialState();
    }

    private Token initialState() throws IOException, LexicalException {
        if (CharacterUtils.isWhiteSpace(currentChar)) {
            advanceCurrentChar();
            return initialState();
        } else if (CharacterUtils.isUpperCase(currentChar)) {
            return transition(this::classIdState);
        } else if (CharacterUtils.isLowerCase(currentChar)) {
            return transition(this::varMetIdState);
        } else if (CharacterUtils.isDigit(currentChar)) {
            return transition(this::intLitState);
        } else if (CharacterUtils.isSingleQuote(currentChar)) {
            return transition(this::charLitOpenedState);
        } else if (CharacterUtils.isDoubleQuote(currentChar)) {
            return transition(this::stringLitOpenedState);
        } else if (CharacterUtils.isPunctuation(currentChar)) {
            return transition(this::punctuationState);
        } else if (CharacterUtils.isEOF(currentChar)) {
            return eofState();
        } else {
            updateLexeme();
            advanceCurrentChar();
            throw new InvalidSymbolException(currentLexeme, fileManager.getLineNumber());
        }
    }

    private Token classIdState() throws IOException, LexicalException {
        if (CharacterUtils.isLetter(currentChar) || CharacterUtils.isDigit(currentChar)
                || CharacterUtils.isUnderscore(currentChar)) {
            return transition(this::classIdState);
        } else {
            return buildToken(KEYWORDS.getOrDefault(currentLexeme, CLASS_ID));
        }
    }

    private Token varMetIdState() throws IOException, LexicalException {
        if (CharacterUtils.isLetter(currentChar) || CharacterUtils.isDigit(currentChar)
                || CharacterUtils.isUnderscore(currentChar)) {
            return transition(this::varMetIdState);
        } else {
            return buildToken(KEYWORDS.getOrDefault(currentLexeme, VAR_MET_ID));
        }
    }

    private Token intLitState() throws IOException, LexicalException {
        if (CharacterUtils.isDigit(currentChar)) {
            return transition(this::intLitState);
        } else {
            return buildToken(INT_LIT);
        }
    }

    private Token charLitOpenedState() throws IOException, LexicalException {
        if (CharacterUtils.isEOF(currentChar) || CharacterUtils.isEOL(currentChar)
                || CharacterUtils.isSingleQuote(currentChar)) {
            throw new MalformedCharException(currentLexeme, fileManager.getLineNumber());
        } else if (CharacterUtils.isBackslash(currentChar)) {
            return transition(this::escapedCharLitOpenedState);
        } else {
            return transition(this::charLitPendingCloseState);
        }
    }

    private Token escapedCharLitOpenedState() throws IOException, LexicalException {
        if (CharacterUtils.isEOF(currentChar) || CharacterUtils.isEOL(currentChar)) {
            throw new MalformedCharException(currentLexeme, fileManager.getLineNumber());
        } else {
            return transition(this::charLitPendingCloseState);
        }
    }

    private Token charLitPendingCloseState() throws IOException, LexicalException {
        if (!CharacterUtils.isSingleQuote(currentChar)) { // TODO: no mostramos este caracter que apareció en lugar de la comilla ('xy), cierto? Y si son solo 2 comillas ('')? Estos dos serían diferentes errores o puede ser todo literal malformado?
            throw new MalformedCharException(currentLexeme, fileManager.getLineNumber());
        } else {
            return transition(this::charLitClosedState);
        }
    }

    private Token charLitClosedState() {
        return buildToken(CHAR_LIT);
    }

    private Token stringLitOpenedState() throws IOException, LexicalException {
        if (CharacterUtils.isEOF(currentChar) || CharacterUtils.isEOL(currentChar)) {
            throw new MalformedStringException(currentLexeme, fileManager.getLineNumber());
        } else if (!CharacterUtils.isDoubleQuote(currentChar)) {
            return transition(this::stringLitOpenedState);
        } else {
            return transition(this::stringLitClosedState);
        }
    }

    private Token stringLitClosedState() {
        return buildToken(STRING_LIT);
    }

    private Token punctuationState() {
        return buildToken(PUNCTUATION.get(currentLexeme));
    }

    private Token eofState() {
        return buildToken(EOF);
    }
}
