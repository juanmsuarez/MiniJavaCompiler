package com.minijava.compiler.filemanager;

import java.io.*;

public class FileManager implements AutoCloseable {
    private LineNumberReader reader;
    private boolean reachedEOF = false;

    public FileManager(String path) throws FileNotFoundException  {
        File file = new File(path);
        InputStream inputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        reader = new LineNumberReader(inputStreamReader);
    }

    public Character nextChar() throws IOException {
        int r = reader.read();

        if (r == -1) {
            reachedEOF = true;
            return null;
        } else {
            return (char) r;
        }
    }

    public int currentLineNumber() {
        return reader.getLineNumber();
    }

    public boolean reachedEOF() {
        return reachedEOF;
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
