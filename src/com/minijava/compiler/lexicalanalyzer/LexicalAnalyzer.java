package com.minijava.compiler.lexicalanalyzer;

import com.minijava.compiler.filemanager.FileManager;
import com.minijava.compiler.lexicalanalyzer.exceptions.InvalidSymbolException;
import com.minijava.compiler.lexicalanalyzer.exceptions.LexicalException;
import com.minijava.compiler.lexicalanalyzer.exceptions.MalformedCharException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LexicalAnalyzer {
    private static final String[] KEYWORDS = new String[] {
            "class", "extends", "static", "dynamic", "public", "private", "this", "new", "null",
            "void", "boolean", "char", "int", "String", "true", "false",
            "if", "else", "while", "return"
    };

    private static final Set<String> KEYWORDS_SET = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(KEYWORDS)));

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
        if (!CharacterUtils.isEOF(currentChar)) { // TODO: el EOF no se muestra como parte del lexema no?
            currentLexeme = currentLexeme + currentChar;
        }
    }

    private Token buildToken(String name) {
        return new Token(name, currentLexeme, fileManager.getLineNumber());
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
            updateLexeme();
            advanceCurrentChar();
            return classIdState();
        } else if (CharacterUtils.isLowerCase(currentChar)) {
            updateLexeme();
            advanceCurrentChar();
            return varMetIdState();
        } else if (CharacterUtils.isDigit(currentChar)) {
            updateLexeme();
            advanceCurrentChar();
            return intLitState();
        } else if (CharacterUtils.isQuote(currentChar)) {
            updateLexeme();
            advanceCurrentChar();
            return charLitOpenedState();
        } else if (CharacterUtils.isEOF(currentChar)) {
            return eofState();
        } else {
            updateLexeme();
            throw new InvalidSymbolException(currentLexeme, fileManager.getLineNumber());
        }
    }

    private Token classIdState() throws IOException {
        if (CharacterUtils.isLetter(currentChar) || CharacterUtils.isDigit(currentChar)
                || CharacterUtils.isUnderscore(currentChar)) {
            updateLexeme();
            advanceCurrentChar();
            return classIdState();
        } else {
            if (KEYWORDS_SET.contains(currentLexeme)) {
                return buildToken("kw" + StringUtils.capitalize(currentLexeme));
            } else {
                return buildToken("classId");
            }
        }
    }

    private Token varMetIdState() throws IOException {
        if (CharacterUtils.isLetter(currentChar) || CharacterUtils.isDigit(currentChar)
                || CharacterUtils.isUnderscore(currentChar)) {
            updateLexeme();
            advanceCurrentChar();
            return varMetIdState();
        } else {
            if (KEYWORDS_SET.contains(currentLexeme)) {
                return buildToken("kw" + StringUtils.capitalize(currentLexeme));
            } else {
                return buildToken("varMetId");
            }
        }
    }

    private Token intLitState() throws IOException {
        if (CharacterUtils.isDigit(currentChar)) {
            updateLexeme();
            advanceCurrentChar();
            return intLitState();
        } else {
            return buildToken("intLit");
        }
    }

    private Token charLitOpenedState() throws IOException, MalformedCharException {
        if (CharacterUtils.isEOF(currentChar) || CharacterUtils.isEOL(currentChar)
                || CharacterUtils.isQuote(currentChar)) {
            throw new MalformedCharException(currentLexeme, fileManager.getLineNumber());
        } else if (CharacterUtils.isBackslash(currentChar)) {
            updateLexeme();
            advanceCurrentChar();
            return escapedCharLitState();
        } else {
            updateLexeme();
            advanceCurrentChar();
            return charLitToCloseState();
        }
    }

    private Token escapedCharLitState() throws IOException, MalformedCharException {
        if (CharacterUtils.isEOF(currentChar) || CharacterUtils.isEOL(currentChar)) {
            throw new MalformedCharException(currentLexeme, fileManager.getLineNumber());
        } else {
            updateLexeme();
            advanceCurrentChar();
            return charLitToCloseState();
        }
    }

    private Token charLitToCloseState() throws IOException, MalformedCharException {
        if (!CharacterUtils.isQuote(currentChar)) { // TODO: no mostramos este caracter que apareció en lugar de la comilla ('xy), cierto? Y si son solo 2 comillas ('')?
            throw new MalformedCharException(currentLexeme, fileManager.getLineNumber());
        } else {
            updateLexeme();
            advanceCurrentChar();
            return buildToken("charLit");
        }
    }

    private Token eofState() {
        return buildToken("EOF");
    }
}
