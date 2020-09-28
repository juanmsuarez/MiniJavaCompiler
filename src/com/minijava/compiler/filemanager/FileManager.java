package com.minijava.compiler.filemanager;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileManager implements AutoCloseable {
    private LineNumberReader reader;
    private String line = "";
    private int charPosition = 0;

    public FileManager(String path) throws FileNotFoundException {
        File file = new File(path);
        InputStream inputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        reader = new LineNumberReader(inputStreamReader);
    }

    private void readLine() throws IOException {
        line = reader.readLine();
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
        return line;
    }

    public int getLineNumber() {
        return reader.getLineNumber() - 1;
    }

    public int getCharPosition() {
        return charPosition;
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
