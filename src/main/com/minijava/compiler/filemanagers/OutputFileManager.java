package com.minijava.compiler.filemanagers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OutputFileManager implements AutoCloseable {
    private BufferedWriter writer;

    public OutputFileManager(String path) throws IOException {
        File file = new File(path);
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file);
        writer = new BufferedWriter(fileWriter);
    }

    public void writeLine(String line) throws IOException {
        writer.write(line);
        writer.newLine();
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
