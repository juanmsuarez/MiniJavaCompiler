package com.minijava.compiler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class MainTests extends ResourceReader {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    void lexicalMultiErrors() {
        Main.main(new String[]{getPath("additional_tests/multi_error.java")});

        Pattern pattern = Pattern.compile(".*\\[Error.*\\[Error.*", Pattern.DOTALL); // Verifica que hayan ocurrido múltiples errores
        Matcher matcher = pattern.matcher(errContent.toString());
        boolean matchFound = matcher.matches();
        Assertions.assertTrue(matchFound);
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.out.println(outContent.toString());
        System.setErr(originalErr);
        System.err.println(errContent.toString());
    }
}
