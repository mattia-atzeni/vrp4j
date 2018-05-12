package it.unica.ro.cvrpb.writers;

import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Logs data into a csv file
 */
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

    /**
     * Creates a new CSV Writer
     * @param path the path to the file to be written
     */
    public CSVStatsWriter(String path) throws IOException {
        this(new File(path));
    }

    @Override
    public void close() {
        writer.close();
    }

    /**
     * Adds a new line to the csv file
     * @param args the field values of the line
     */
    public void appendLine(Object... args) {
        String line = Arrays.stream(args)
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        writer.println(line);
    }

    /**
     * Removes all the lines from the file
     */
    public void clear() {
        try (PrintWriter w = new PrintWriter(file)) {
            w.print("");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
