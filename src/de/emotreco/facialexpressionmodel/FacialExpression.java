package de.emotreco.facialexpressionmodel;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static de.emotreco.facialexpressionmodel.Emotions.*;

/**
 * A class representing the discrete values of an facial expression. An expression (<code>expressionKey</code>) can be "Stirnfalten"
 * while its intensity (<code>value</code>) could be "high". This association is made with a certain confidence(<code>confidence</code>).
 */
public class FacialExpression implements Comparable<FacialExpression> {

    private String expressionKey, value;
    private float confidence;

    /**
     * See keys from Emotions.java. Value kann be *one* of low, medium or high.
     * Paste in the value resulting from the winner takes all principle.
     *
     * @param expressionKey e.g. "Stirnfalten"
     * @param value one of low, medium or high
     * @param confidence the confidence of this expression within the frame
     */
    FacialExpression(String expressionKey, String value, float confidence) {
        this.expressionKey = expressionKey;
        this.value = value;
        this.confidence = confidence;
    }

    /**
     * Returns the name of the expression, also used in Emotions.java.
     *
     * @return for example "Stirnfalten"
     */
    public String getExpressionKey() {
        return expressionKey;
    }

    /**
     * Returns the intensity of the expression.
     *
     * @return for example "high"
     */
    public String getValue() {
        return value;
    }

    /**
     * Get a list representing the emotions which would match expressionKey and value. Independant from confidence.
     *
     * Order is [NEUTRAL, SADNESS, FEAR, JOY, DISGUST].
     *
     * @return for example [0, 0, 0, 1, 0]
     */
    public List<Integer> getMatchingBinaries() {
        LinkedHashMap[] emotions = new LinkedHashMap[] { NEUTRAL, SADNESS, FEAR, JOY, DISGUST };
        Integer[] matchingEmotions = new Integer[emotions.length];
        Arrays.fill(matchingEmotions, 0);
        for (int i = 0; i < emotions.length; i++) {
            String[] possibleValues = ((String) emotions[i].get(expressionKey)).split(",");
            if (Arrays.stream(possibleValues).anyMatch(pV -> pV.equalsIgnoreCase(value))) {
                matchingEmotions[i] = 1;
            }
        }
        return Arrays.asList(matchingEmotions);
    }

    public float getConfidence() {
        return confidence;
    }

    /**
     * Used for native sorting algorithm. Sorts by confidence.
     */
    @Override
    public int compareTo(FacialExpression that) {
        return Float.compare(that.confidence, this.confidence);
    }
}
