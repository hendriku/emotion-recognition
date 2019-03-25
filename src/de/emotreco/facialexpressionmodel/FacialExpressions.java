package de.emotreco.facialexpressionmodel;

import java.util.LinkedHashMap;

final class FacialExpressions {

    /**
     * Deny attempts to instantiate this class. It is a store for Feature to Facial Expression associations only.
     * Each facial expression has an acronym in the featuremodel.
     */
    private FacialExpressions() {}

    static final LinkedHashMap<String, String> EXPRESSIONS = new LinkedHashMap<String, String>(){{
        put("fob", "Stirnfalten");
        put("ea", "Augenöffnung");
        put("bd", "Brauenabstand");
        put("hnc", "Horizontale Nasenfalten");
        put("vnc", "Vertikale Nasenfalten");
        put("cw", "Wangenfalten");
        put("ma", "Mundöffnung");
    }};

}