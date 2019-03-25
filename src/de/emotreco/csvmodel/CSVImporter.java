package de.emotreco.csvmodel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Imports a file from the file system into the CSVFrame format.
 */
public class CSVImporter {

    public static final String[] COLUMN_NAMES = { "Nr", "x", "y", "xright", "ylow", "fob", "lea", "lbd", "rea", "rbd", "hnc", "vnc", "lcw", "rcw", "ma" };

    private String filePath;

    public CSVImporter(String filePath) {
        this.filePath= filePath;
    }

    /**
     * Read file from file system. The file has to be in csv format and the column names have to match the COLUMN_NAMES spec.
     *
     * @return CSVFrame[] - Representing all frames/lines from the csv
     * @throws IOException When the file could not be accessed.
     */
    public CSVFrame[] readFile() throws IOException {
        ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(filePath));

        // Validate order of columns
        String headerRowReference = String.join(";", COLUMN_NAMES);
        if (!lines.get(0).equalsIgnoreCase(headerRowReference)) {
            throw new RuntimeException("Header row does not match expected format.\nIs: "
                    + lines.get(0) + "\nShould be: " + headerRowReference);
        }

        // Create a csv frame for all rows except the header
        CSVFrame[] csvFrames = new CSVFrame[lines.size() - 1];
        for (int i = 0; i < lines.size() - 1; i++) {
            csvFrames[i] = new CSVFrame(lines.get(i + 1));
        }

        return csvFrames;
    }

}
