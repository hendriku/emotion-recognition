package de.emotreco.facialexpressionmodel;

import de.emotreco.featuremodel.FeatureFrame;
import de.emotreco.featuremodel.FeatureImporter;

import java.util.ArrayList;
import java.util.Collections;


/**
 * This is a class to interprete the pixel values from a CSVFrame object into FacialExpression objects (see the .java files).
 */
public class FacialExpressionDescriptor {

    public static final int TRAINING_FRAME_AMOUNT = 35;
    private static final float[] featureFactor = new float[]{1.9f, 1f, 2f, 2f, 3f, 1.5f, 4f};
    private float[] average, firstMinimum, secondMinimum;

    /**
     * Create a descriptor containing the definitions for when an expression is categorised as one of low, medium or high.
     *
     * @param featureFrames The first known frames from the csv file. For the WBS task this means the first 35 frames. Others are randomly.
     */
    public FacialExpressionDescriptor(FeatureFrame[] featureFrames) {
        average = new float[FeatureImporter.FEATURE_COLUMN_NAMES.length];
        firstMinimum = new float[FeatureImporter.FEATURE_COLUMN_NAMES.length];
        secondMinimum = new float[FeatureImporter.FEATURE_COLUMN_NAMES.length];

        // Sum up all the values
        for (int i = 0; i < FeatureImporter.FEATURE_COLUMN_NAMES.length; i++) {
            for (int y = 0; y < TRAINING_FRAME_AMOUNT; y++) {
                Float featureValue = featureFrames[y].getFeatures()[i];
                if (featureValue != null) {
                    average[i] += featureValue;
                }
            }
        }

        // And calculate the average and the minima
        for (int i = 0; i < FeatureImporter.FEATURE_COLUMN_NAMES.length; i++) {
            average[i] = average[i] / TRAINING_FRAME_AMOUNT;
            firstMinimum[i] = average[i] - average[i] / (2f * featureFactor[i]);
            secondMinimum[i] = average[i] + average[i] / (2f * featureFactor[i]);
        }
    }

    /**
     * Describe a frame with facial expressions, used as key for an emotion in Emotions.java.
     *
     * @param frame the FeatureFrame to get all the FacialExpressions for
     * @return An array of the winning intensities for the different expressions
     */
    public FacialExpression[] describeFrame(FeatureFrame frame) {
        FacialExpression[] winners = new FacialExpression[frame.getFeatures().length];
        Float[] frameFeatures = frame.getFeatures();
        for (int i = 0; i < frameFeatures.length; i++) {
            if (frameFeatures[i] != null) {
                String key = FacialExpressions.EXPRESSIONS.get(FeatureImporter.FEATURE_COLUMN_NAMES[i]);

                // calculate all values and sort
                ArrayList<FacialExpression> currentExpressions = new ArrayList<>();
                currentExpressions.add(calculateLow(key, i, frameFeatures[i]));
                currentExpressions.add(calculateMedium(key, i, frameFeatures[i]));
                currentExpressions.add(calculateHigh(key, i, frameFeatures[i]));
                Collections.sort(currentExpressions);

                // get the most confident facial expression, winner takes all
                winners[i] = currentExpressions.get(0);
            }
        }

        return winners;
    }

    /**
     * Calculate the confidence for low.
     *
     * @param key key of the facial expression e.g. "Stirnfalten"
     * @param i index of the feature
     * @param x the measured pixel value
     * @return A facial expression which could be the winner
     */
    private FacialExpression calculateLow(String key, int i, float x) {
        return new FacialExpression(
                key,
                "low",
                - (1 / (firstMinimum[i] * firstMinimum[i] * 2)) * x * x + 1
                // (0.5f / (firstMinimum[i] - average[i])) * x + 1f
        );
    }

    /**
     * Calculate the confidence for medium.
     *
     * @param key key of the facial expression e.g. "Stirnfalten"
     * @param i index of the feature
     * @param x the measured pixel value
     * @return A facial expression which could be the winner
     */
    private FacialExpression calculateMedium(String key, int i, float x) {
        return new FacialExpression(
                key,
                "medium",
                -((featureFactor[i] / average[i]) * Math.abs(x - average[i])) + 1f
        );
    }

    /**
     * Calculate the confidence for high.
     *
     * @param key key of the facial expression e.g. "Stirnfalten"
     * @param i index of the feature
     * @param x the measured pixel value
     * @return A facial expression which could be the winner
     */
    private FacialExpression calculateHigh(String key, int i, float x) {
        return new FacialExpression(
                key,
                "high",
                Math.min((0.5f / secondMinimum[i]) * x, 1)
        );
    }

}
