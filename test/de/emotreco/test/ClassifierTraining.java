package de.emotreco.test;

import de.emotreco.main.EmotionClassifier;
import de.emotreco.main.dempster.Measure;
import de.emotreco.main.dempster.MeasureEntry;
import de.emotreco.utils.MathUtils;

import static de.emotreco.facialexpressionmodel.FacialExpressionDescriptor.TRAINING_FRAME_AMOUNT;

/**
 * This class is for testing the performance of the emotion-recognition application.
 */
public class ClassifierTraining {

    /**
     * The first TRAINING_FRAME_AMOUNT frames were analysed from hand.
     * These are the solution definitions on which the classifier should be trained on.
     */
    private static final String[][] trainingEmotionSolutions = new String[][] {
            { // file 1
                "NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL",
                "FEAR","FEAR","FEAR","FEAR","FEAR",
                "SADNESS","SADNESS","SADNESS","SADNESS","SADNESS",
                "DISGUST","DISGUST","DISGUST","DISGUST","DISGUST",
                "NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL",
            },
            { // file 2
                "NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL",
                "FEAR","FEAR","FEAR","FEAR","FEAR",
                "SADNESS","SADNESS","SADNESS","SADNESS","SADNESS",
                "DISGUST","DISGUST","DISGUST","DISGUST","DISGUST",
                "NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL",
            },
            { // file 3
                "NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL",
                "FEAR","FEAR","FEAR","FEAR","FEAR",
                "SADNESS","SADNESS","SADNESS","SADNESS","SADNESS",
                "DISGUST","DISGUST","DISGUST","DISGUST","DISGUST",
                "NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL","NEUTRAL",
            },
    };

    /**
     * Alternative entry point for executing the test comparison.
     * @param args none
     */
    public static void main(String[] args) {
        EmotionClassifier classifier = new EmotionClassifier();

        System.out.println("Verifying the classifier with training sets");

        System.out.println("Using data/emo_muster_1_1.csv...");
        compareToSolution(classifier.classifyFile("data/emo_muster_1_1.csv"), trainingEmotionSolutions[0]);

        System.out.println("Using data/emo_muster_1_2.csv...");
        compareToSolution(classifier.classifyFile("data/emo_muster_1_2.csv"), trainingEmotionSolutions[1]);

        System.out.println("Using data/emo_muster_1_3.csv...");
        compareToSolution(classifier.classifyFile("data/emo_muster_1_3.csv"), trainingEmotionSolutions[2]);
    }

    /**
     * Compares the measures of the classifier to the solution emotions.
     * Only the first TRAINING_FRAME_AMOUNT frames.
     * Prints out the performance in detection rate and balance distribution metrics.
     *
     * @param measuredResult the measure which were actually classsified
     * @param solution the solution which emotion should be detected in which frame
     */
    private static void compareToSolution(Measure[] measuredResult, String[] solution) {
        // Amount of hits (emotion detected correctly)
        int hits = 0;
        // Amount of emotion counts (independant from if emotion detection was correct)
        int[] emotionCounts = new int[5];
        double plausability = 0, belief = 0, doubt = 0;
        for (int i = 0; i < TRAINING_FRAME_AMOUNT; i++) {
            MeasureEntry winner = measuredResult[i].getWinner();
            if (winner.getEmotion().equalsIgnoreCase(solution[i])) {
                hits++;
            }
            emotionCounts[winner.getEmotionIndex()]++;
            plausability += measuredResult[i].calculatePlausability(winner.getEmotionIndex());
            belief += measuredResult[i].calculateBelief(winner.getEmotionIndex());
            doubt += measuredResult[i].calculateDoubt(winner.getEmotionIndex());
        }
        System.out.println("\nTRAINING RESULT:\n"
                + "PERFORMANCE=" + MathUtils.round((hits / (float) TRAINING_FRAME_AMOUNT) * 100f) + "% "
                + "|| DISTRIBUTION: NEUTRAL=" + emotionCounts[0] + ", SADNESS=" + emotionCounts[1]
                + ", FEAR=" + emotionCounts[2] + ", JOY=" + emotionCounts[3] + ", DISGUST=" + emotionCounts[4]);
        System.out.println("AVGERAGE Plausibility=" + MathUtils.round(plausability / (double) TRAINING_FRAME_AMOUNT)
                + ", Belief=" + MathUtils.round(belief / (double) TRAINING_FRAME_AMOUNT)
                + ", Doubt=" + MathUtils.round(doubt / (double) TRAINING_FRAME_AMOUNT) );
        System.out.println("========================================================================================");
    }

}
