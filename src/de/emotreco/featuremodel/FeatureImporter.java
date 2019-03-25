package de.emotreco.featuremodel;

import de.emotreco.csvmodel.CSVFrame;

/**
 * Converts the csvmodel into the featuremodel.
 */
public class FeatureImporter {

    public static final String[] FEATURE_COLUMN_NAMES = { "fob", "ea", "bd", "hnc", "vnc", "cw", "ma" }; // 7 features

    public FeatureImporter() { }

    /**
     * Convert a CSVFrame[] into FeatureFrame[].
     * This means that the csvmodel is being transfered to the featuremodel.
     * Therefore values from the csv are getting filtered, normalized and merged.
     *
     * @param csvFrames the csvmodel
     * @return the feature model
     */
    public FeatureFrame[] convert(CSVFrame[] csvFrames) {
        FeatureFrame[] featureFrames = new FeatureFrame[csvFrames.length];
        for (int i = 0; i < featureFrames.length; i++) {
            featureFrames[i] = new FeatureFrame(csvFrames[i]);
        }
        return featureFrames;
    }

}
