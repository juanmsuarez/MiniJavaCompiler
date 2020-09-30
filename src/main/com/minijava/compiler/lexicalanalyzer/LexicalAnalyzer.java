package com.minijava.compiler.lexicalanalyzer;

import com.minijava.compiler.filemanager.FileManager;
import com.minijava.compiler.lexicalanalyzer.exceptions.*;

import java.io.IOException;

import static com.minijava.compiler.lexicalanalyzer.CharacterUtils.*;
import static com.minijava.compiler.lexicalanalyzer.Lexemes.*;
import static com.minijava.compiler.lexicalanalyzer.TokenNames.*;

public class LexicalAnalyzer {
    private FileManager fileManager;

    private Character currentChar;

    private String currentLexeme;
    private int lexemeStartLineNumber;
    private String lexemeStartLine;
    private int lexemeStartPosition;

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
        return new Token(name, currentLexeme, lexemeStartLineNumber);
    }

    // Updates current lexeme, advances current char and executes State
    private Token transition(State state) throws IOException, LexicalException {
        updateLexeme();
        advanceCurrentChar();
        return state.execute();
    }

    private void resetLexeme() {
        currentLexeme = "";
        lexemeStartLineNumber = fileManager.getLineNumber();
        lexemeStartLine = fileManager.getLine();
        lexemeStartPosition = fileManager.getCharPosition();
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
            return transition(this::stringLitOpeningState);
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
            throw new InvalidSymbolException(lexemeStartLineNumber, currentLexeme, lexemeStartLine, lexemeStartPosition);
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
            throw new MalformedCharException(lexemeStartLineNumber, currentLexeme, lexemeStartLine, lexemeStartPosition);
        } else if (isBackslash(currentChar)) {
            return transition(this::escapedCharLitOpenedState);
        } else {
            return transition(this::charLitPendingCloseState);
        }
    }

    private Token escapedCharLitOpenedState() throws IOException, LexicalException {
        if (isEOF(currentChar) || isEOL(currentChar)) {
            throw new MalformedCharException(lexemeStartLineNumber, currentLexeme, lexemeStartLine, lexemeStartPosition);
        } else {
            return transition(this::charLitPendingCloseState);
        }
    }

    private Token charLitPendingCloseState() throws IOException, LexicalException {
        if (!isSingleQuote(currentChar)) {
            throw new UnclosedCharException(lexemeStartLineNumber, currentLexeme, lexemeStartLine, lexemeStartPosition);
        } else {
            return transition(this::charLitClosedState);
        }
    }

    private Token charLitClosedState() {
        int border = CHAR_DELIMITER.length();
        currentLexeme = currentLexeme.substring(border, currentLexeme.length() - border);

        return buildToken(CHAR_LITERAL);
    }

    private Token stringLitOpeningState() throws IOException, LexicalException {
        if (!isEOF(currentChar) && TEXT_BLOCK_OPEN.startsWith(currentLexeme + currentChar)) {
            return transition(this::stringLitOpeningState);
        } else if (currentLexeme.equals(STRING_DELIMITER)) {
            return stringLitOpenedState();
        } else if (currentLexeme.equals(EMPTY_STRING_LITERAL)) {
            return stringLitClosedState();
        } else if (currentLexeme.equals(TEXT_BLOCK_OPEN)) {
            return textBlockOpenedState();
        } else {
            throw new MalformedTextBlockException(lexemeStartLineNumber, currentLexeme, lexemeStartLine, lexemeStartPosition);
        }
    }

    private Token textBlockOpenedState() throws IOException, LexicalException {
        if (currentLexeme.length() >= TEXT_BLOCK_OPEN.length() + TEXT_BLOCK_CLOSE.length()
                && currentLexeme.endsWith(TEXT_BLOCK_CLOSE)) {
            return stringLitClosedState();
        } else if (isEOF(currentChar)) {
            throw new UnclosedTextBlockException(lexemeStartLineNumber, currentLexeme, lexemeStartLine, lexemeStartPosition);
        } else {
            return transition(this::textBlockOpenedState);
        }
    }

    private Token stringLitOpenedState() throws IOException, LexicalException {
        if (isEOF(currentChar) || isEOL(currentChar)) {
            throw new UnclosedStringException(lexemeStartLineNumber, currentLexeme, lexemeStartLine, lexemeStartPosition);
        } else if (!isDoubleQuote(currentChar)) {
            return transition(this::stringLitOpenedState);
        } else {
            return transition(this::stringLitClosedState);
        }
    }

    private Token stringLitClosedState() {
        int prefix = currentLexeme.startsWith(TEXT_BLOCK_OPEN) ? TEXT_BLOCK_OPEN.length() : STRING_DELIMITER.length();
        int suffix = currentLexeme.startsWith(TEXT_BLOCK_OPEN) ? TEXT_BLOCK_CLOSE.length() : STRING_DELIMITER.length();
        currentLexeme = currentLexeme.substring(prefix, currentLexeme.length() - suffix);

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

    private Token blockCommentOpenedState() throws IOException, LexicalException {
        if (isAsterisk(currentChar)) {
            return blockCommentToCloseState();
        } else if (!isEOF(currentChar)) {
            return transition(this::blockCommentOpenedState);
        } else {
            throw new UnclosedCommentException(lexemeStartLineNumber, currentLexeme, lexemeStartLine, lexemeStartPosition);
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
            throw new UnclosedCommentException(lexemeStartLineNumber, currentLexeme, lexemeStartLine, lexemeStartPosition);
        }
    }

    private Token operatorState() throws IOException, LexicalException {
        if (!isEOF(currentChar) && OPERATORS.containsKey(currentLexeme + currentChar)) {
            return transition(this::operatorState);
        } else if (OPERATORS.containsKey(currentLexeme)) {
            return buildToken(OPERATORS.get(currentLexeme));
        } else {
            throw new MalformedOperatorException(lexemeStartLineNumber, currentLexeme, lexemeStartLine, lexemeStartPosition);
        }
    }

    private Token eofState() {
        return buildToken(EOF);
    }

    private interface State {
        Token execute() throws IOException, LexicalException;
    }
}

