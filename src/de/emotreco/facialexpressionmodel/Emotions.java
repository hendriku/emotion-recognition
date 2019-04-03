package de.emotreco.facialexpressionmodel;

import java.util.LinkedHashMap;

final class Emotions {

    /**
     * Deny attempts to instantiate this class. It is a store for (Facial Expression to Emotion) rules only.
     */
    private Emotions() {}

    /**
     *  Each emotion has to provide a rule for each possible facial element.
     *  The value has to be discrete or a set of possible discrete values ["low", "medium", "high"].
     *
     *         put("Stirnfalten", "xxx, yyy");
     *         put("Augenöffnung", "xxx");
     *         put("Brauenabstand", "zzz");
     *         put("Horizontale Nasenfalten", "xxx");
     *         put("Vertikale Nasenfalten", "xxx");
     *         put("Wangenfalten", "xxx");
     *         put("Mundöffnung", "xxx");
     */

    static final LinkedHashMap<String, String> NEUTRAL = new LinkedHashMap<String, String>(){{
        put("Stirnfalten", "low");
        put("Augenöffnung", "medium");
        put("Brauenabstand", "medium");
        put("Horizontale Nasenfalten", "medium");
        put("Vertikale Nasenfalten", "medium");
        put("Wangenfalten", "low");
        put("Mundöffnung", "");
    }};

    static final LinkedHashMap<String, String> SADNESS = new LinkedHashMap<String, String>(){{
        put("Stirnfalten", "high");
        put("Augenöffnung", "low,medium");
        put("Brauenabstand", "medium");
        put("Horizontale Nasenfalten", "medium");
        put("Vertikale Nasenfalten", "");
        put("Wangenfalten", "low,medium");
        put("Mundöffnung", "");
    }};

    static final LinkedHashMap<String, String> FEAR = new LinkedHashMap<String, String>(){{
        put("Stirnfalten", "high");
        put("Augenöffnung", "high");
        put("Brauenabstand", "high");
        put("Horizontale Nasenfalten", "high");
        put("Vertikale Nasenfalten", "");
        put("Wangenfalten", "");
        put("Mundöffnung", "high");
    }};

    static final LinkedHashMap<String, String> JOY = new LinkedHashMap<String, String>(){{
        put("Stirnfalten", "medium");
        put("Augenöffnung", "high");
        put("Brauenabstand", "high");
        put("Horizontale Nasenfalten", "low");
        put("Vertikale Nasenfalten", "");
        put("Wangenfalten", "medium,high");
        put("Mundöffnung", "");
    }};

    static final LinkedHashMap<String, String> DISGUST = new LinkedHashMap<String, String>(){{
        put("Stirnfalten", "low");
        put("Augenöffnung", "low");
        put("Brauenabstand", "low");
        put("Horizontale Nasenfalten", "medium");
        put("Vertikale Nasenfalten", "medium");
        put("Wangenfalten", "medium");
        put("Mundöffnung", "");
    }};
}