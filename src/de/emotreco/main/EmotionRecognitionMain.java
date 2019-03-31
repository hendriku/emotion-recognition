package de.emotreco.main;

import java.io.File;
import java.io.FilenameFilter;

/**
 * The main class of the emotion-recognition application.
 */
public class EmotionRecognitionMain {

    private static final String dataDirectoryPath = "data/";

    /**
     * Main entry point of the application. Handles program arguments and calls EmotionClassifier.java.
     *
     * @param args either the path to one csv file or "--all" to check all files in ./data/*.csv
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }

        EmotionClassifier classifier = new EmotionClassifier();
        try {
            if (args[0].equals("--all")) {
                File dataFolder = new File(dataDirectoryPath);
                // data/ directory has to be in the same folder as the jar application
                if (!dataFolder.exists() || !dataFolder.isDirectory()) {
                    System.out.println("Can't find directory " + dataDirectoryPath );
                    return;
                }

                // Get all files with .csv
                File[] patternFiles = dataFolder.listFiles(new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        return name.toLowerCase().endsWith(".csv");
                    }
                });

                System.out.println("Testing all csv files from " + dataDirectoryPath);
                for (File file : patternFiles) {
                    classifier.classifyFile(file.getAbsolutePath());
                }
            } else {
                String fileName = args[0];
                classifier.classifyFile(fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            printUsage();
        }
    }

    private static void printUsage() {
        System.out.println("Usage: \n\n" +
                "java -jar emotion-recognition.jar data/emo_muster_1_1.csv\n\tScans the file at the specified path.\n\n" +
                "java -jar emotion-recognition.jar --all\n\tScans all .csv files which are in the directory data.");
    }
}
