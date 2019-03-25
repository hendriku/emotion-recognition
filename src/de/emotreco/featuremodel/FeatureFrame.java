package de.emotreco.featuremodel;

import de.emotreco.csvmodel.CSVFrame;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * A class to filter, normalize and merge the columns of a CSVFrame.
 * It is representing an abstract boundary between FacialExpression and CSVFrame.
 */
public class FeatureFrame {

    private HashMap<String, Float> features = new LinkedHashMap<>();

    /**
     * Create a featuremodel element from a csvmodel element.
     *
     * @param csvFrame source CSVFrame
     */
    FeatureFrame(CSVFrame csvFrame) {
        // Normalize all the values relatively to the face size
        float faceFactor = csvFrame.getWidth() * csvFrame.getHeight() / 10000f;

        // Set (and merge some) feature values
        features.put("fob", (float) csvFrame.getFurrowingOfBrowe() / faceFactor);
        features.put("ea", ((csvFrame.getLeftEyeAperture() + csvFrame.getRightEyeAperture()) / 2f) / faceFactor);
        features.put("bd", ((csvFrame.getLeftBroweDistance() + csvFrame.getRightBroweDistance()) / 2f) / faceFactor);
        features.put("hnc", (float) csvFrame.getHorizontalNoseCrinkles() / faceFactor);
        features.put("vnc", (float) csvFrame.getVerticalNoseCrinkles() / faceFactor);
        features.put("cw", ((csvFrame.getLeftCheekWrinkle() + csvFrame.getRightCheekWrinkle()) / 2f) / faceFactor);
        features.put("ma", (float) csvFrame.getMouthAperture() / faceFactor);
    }

    /**
     * Generate a list of all feature values in consistent order.
     *
     * @return Array of boxed float feature values
     */
    public Float[] getFeatures() {
        return features.values().toArray(new Float[0]);
    }

    /**
     * Return all the features
     * @return All contents of the map
     */
    public HashMap<String, Float> getFeatureMap() {
        return features;
    }

    /**
     * Get the normalized value for measured forehead wrinkles via the furrowing of the browe
     * @return Content of fob
     */
    public float getFurrowingOfBrowe() {
        return features.get("fob");
    }

    /**
     * Get the normalized value for measured eye size
     * @return Content of ea
     */
    public float getEyeAperture() {
        return features.get("ea");
    }

    /**
     * Get the normalized value for measured brow distance
     * @return Content of bd
     */
    public float getBroweDistance() {
        return features.get("bd");
    }

    /**
     * Get the normalized value for measured horizontal nose crinkles
     * @return Content of hnc
     */
    public float getHorizontalNoseCrinkles() {
        return features.get("hnc");
    }

    /**
     * Get the normalized value for vertical nose crinkles
     * @return Content of vnc
     */
    public float getVerticalNoseCrinkles() {
        return features.get("vnc");
    }

    /**
     * Get the normalized value for measured cheek wrinkle
     * @return Content of cw
     */
    public float getCheekWrinkle() {
        return features.get("cw");
    }

    /**
     * Get the normalized value for measured mouth aperture
     * @return Content of ma
     */
    public float getMouthAperture() {
        return features.get("ma");
    }

    /**
     * For debugging purposes.
     *
     * @return Example: { fob: 25361, ea: 21, bd: 22, hnc: 5, vnc: 3, cw: 8, ma: 80  }
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ ");
        features.forEach((k, v) -> stringBuilder.append(k).append(": ").append(v).append(", "));
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length() - 1);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

}
