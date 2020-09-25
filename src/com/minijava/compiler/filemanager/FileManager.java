package com.minijava.compiler.filemanager;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileManager implements AutoCloseable {
    private LineNumberReader reader;
    private int lineNumber;
    private boolean reachedEOF = false;

    public FileManager(String path) throws FileNotFoundException  {
        File file = new File(path);
        InputStream inputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        reader = new LineNumberReader(inputStreamReader);
    }

    public Character nextChar() throws IOException {
        lineNumber = reader.getLineNumber() + 1;
        int r = reader.read();

        if (r == -1) {
            reachedEOF = true;
            return null;
        } else {
            return (char) r;
        }
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public boolean hasReachedEOF() {
        return reachedEOF;
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
