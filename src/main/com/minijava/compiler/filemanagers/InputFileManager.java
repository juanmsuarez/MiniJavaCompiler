package com.minijava.compiler.filemanagers;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class InputFileManager implements AutoCloseable {
    private BufferedReader reader;
    private String line = "";
    private int lineNumber = -1;
    private int charPosition = 0;

    public InputFileManager(String path) throws FileNotFoundException {
        File file = new File(path);
        InputStream inputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        reader = new BufferedReader(inputStreamReader);
    }

    private void readLine() throws IOException {
        line = reader.readLine();
        lineNumber++;
        charPosition = -1;
    }

    public Character nextChar() throws IOException {
        if (line != null && charPosition == line.length()) {
            readLine();
        }
        if (line == null) {
            return null;
        }

        charPosition++;
        return charPosition < line.length() ? line.charAt(charPosition) : '\n';
    }

    public String getLine() {
        return line == null ? "" : line;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getCharPosition() {
        return charPosition == -1 ? 0 : charPosition;
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
