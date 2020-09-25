package com.minijava.compiler.lexicalanalyzer;

import com.minijava.compiler.filemanager.FileManager;
import com.minijava.compiler.lexicalanalyzer.exceptions.GenericLexicalException;
import com.minijava.compiler.lexicalanalyzer.exceptions.LexicalException;

import java.io.IOException;

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
            return new Token("classId", currentLexeme, fileManager.getLineNumber());
        }
    }

    private Token varMetIdState() throws IOException {
        if (CharacterUtils.isLetter(currentChar) || CharacterUtils.isDigit(currentChar)
                || CharacterUtils.isUnderscore(currentChar)) {
            updateLexeme();
            advanceCurrentChar();
            return varMetIdState();
        } else {
            return new Token("varMetId", currentLexeme, fileManager.getLineNumber());
        }
    }

    private Token eofState() {
        return new Token("EOF", currentLexeme, fileManager.getLineNumber());
    }
}
