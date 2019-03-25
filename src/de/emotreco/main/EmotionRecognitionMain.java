package de.emotreco.main;

/**
 * The main class of the emotion-recognition application.
 */
public class EmotionRecognitionMain {

    /**
     * Main entry point of the application. Handles program arguments and calls EmotionClassifier.java.
     *
     * @param args either the path to one csv file or "--all" to check all files in ./data/*.csv
     */
    public static void main(String[] args) {
        EmotionClassifier classifier = new EmotionClassifier();
        if (args[0].equals("--all")) {
            System.out.println("Testing all csv files");
            classifier.classifyFile("data/emo_muster_1_1.csv");
            classifier.classifyFile("data/emo_muster_1_2.csv");
            classifier.classifyFile("data/emo_muster_1_3.csv");
        } else {
            String fileName = args[0];
            classifier.classifyFile(fileName);
        }
    }

}
