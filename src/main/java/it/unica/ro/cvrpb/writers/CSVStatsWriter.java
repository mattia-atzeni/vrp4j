package it.unica.ro.cvrpb.writers;

import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CSVStatsWriter implements AutoCloseable {

    private final PrintWriter writer;
    private final File file;

    /**
     * Creates a new writer
     * @param file the file to be written
     */
    public CSVStatsWriter(File file) throws IOException {
        this.file = file;
        this.writer = new PrintWriter(new FileWriter(file, true));
    }

    public CSVStatsWriter(String path) throws IOException {
        this(new File(path));
    }

    @Override
    public void close() {
        writer.close();
    }

    public void appendLine(Object... args) {
        String line = Arrays.stream(args)
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        writer.println(line);
    }

    public void clear() {
        try (PrintWriter w = new PrintWriter(file)) {
            w.print("");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
