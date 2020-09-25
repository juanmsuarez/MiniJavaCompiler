package com.minijava.compiler.lexicalanalyzer;

import com.minijava.compiler.filemanager.FileManager;
import com.minijava.compiler.lexicalanalyzer.exceptions.GenericLexicalException;
import com.minijava.compiler.lexicalanalyzer.exceptions.LexicalException;

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
        currentLexeme = currentLexeme + currentChar;
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
        } else if (fileManager.hasReachedEOF()) {
            return eofState();
        } else {
            updateLexeme();
            throw new GenericLexicalException(currentLexeme, fileManager.getLineNumber());
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
                return new Token("kw" + StringUtils.capitalize(currentLexeme), currentLexeme, fileManager.getLineNumber());
            } else {
                return new Token("classId", currentLexeme, fileManager.getLineNumber());
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
                return new Token("kw" + StringUtils.capitalize(currentLexeme), currentLexeme, fileManager.getLineNumber());
            } else {
                return new Token("varMetId", currentLexeme, fileManager.getLineNumber());
            }
        }
    }

    private Token intLitState() throws IOException {
        if (CharacterUtils.isDigit(currentChar)) {
            updateLexeme();
            advanceCurrentChar();
            return intLitState();
        } else {
            return new Token("intLit", currentLexeme, fileManager.getLineNumber());
        }
    }

    private Token eofState() {
        return new Token("EOF", currentLexeme, fileManager.getLineNumber());
    }
}
