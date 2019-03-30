package de.emotreco.main;

import de.emotreco.csvmodel.CSVFrame;
import de.emotreco.csvmodel.CSVImporter;
import de.emotreco.facialexpressionmodel.FacialExpression;
import de.emotreco.facialexpressionmodel.FacialExpressionDescriptor;
import de.emotreco.featuremodel.FeatureFrame;
import de.emotreco.featuremodel.FeatureImporter;
import de.emotreco.main.dempster.DempsterHandler;
import de.emotreco.main.dempster.Measure;
import de.emotreco.main.dempster.MeasureEntry;
import de.emotreco.utils.MathUtils;

import java.io.IOException;

public class EmotionClassifier {

    /**
     * Reads, processes, classifies an amount of frames defined in a .csv file which should match the CSVImporter.
     * Uses <code>printClassificationResult</code> for printing the result which is also returned for debug purposes .
     *
     * The conversion happens through three abstract layers: csvmodel to featuremodel to facialexpressionmodel
     *
     * @param fileName path to the .csv file with the frames in it.
     * @return the measured emotions, null if fail
     */
    public Measure[] classifyFile(String fileName) {
        try {
            System.out.println("Starting with import of csvmodel.....");
            CSVFrame[] csvFrames = new CSVImporter(fileName).readFile();

            System.out.println("Import of csvmodel done.");
            System.out.println("Conversion into featuremodel running...");

            FeatureFrame[] featureFrames = new FeatureImporter().convert(csvFrames);

            System.out.println("Conversion into featuremodel done.");
            System.out.println("Building a descriptor...");

            FacialExpressionDescriptor descriptor = new FacialExpressionDescriptor(featureFrames);
            Measure[] emotions = new Measure[featureFrames.length];

            System.out.println("Done. Classifying frames now...");
            for (int i = 0; i < featureFrames.length; i++) {
                emotions[i] = classifyFrame(featureFrames[i], descriptor);
                printClassificationResult(emotions[i], i);
            }
            System.out.println("Classification done!");

            return emotions;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Uses the Dempster-Shaefer implementation provided from the excerise to classify a frame into an emotion.
     *
     * @param frame The filtered, merged and normalized FeatureFrame to classify
     * @param descriptor the descriptor, trained with the first n balanced sets of frames.
     * @return The best measure as a result of the dempster implementation. This will be the classified frame.
     */
    private Measure classifyFrame(FeatureFrame frame, FacialExpressionDescriptor descriptor) {
        // We have neutral, sadness, fear, joy, disgust, (5)
        DempsterHandler dempsterHandler = new DempsterHandler(5);

        // There are 10 features per frame
        // fob, lea,lbd, rea, rbd, hnc, vnc, lcw, rcw, ma
        // each can be expressed by [small, medium, large] or a combination of them
        // The measurements (if present) are tracked within the frame
        FacialExpression[] expressions = descriptor.describeFrame(frame);

        for (FacialExpression expression : expressions) {
            // One measure example is
            // Mouth Aperture (MA) = "large"
            // which is an evidence for [joy, fear]
            // the propability has to be calculated together with discreting "large" via a function F(Large) = 0.7
            // Measure maIsLarge = dempsterHandler.addMeasure();
            // maIsLarge.addEntry(Arrays.asList(0, 0, 1, 1, 0), 0.7f);
            Measure measure = dempsterHandler.addMeasure();
            measure.addEntry(expression.getMatchingBinaries(), expression.getConfidence());
        }

        // This can be done for multiple measures
        // They have to be accumulated
        dempsterHandler.accumulateAllMeasures();

        // Return the finally accumulated measures
        return dempsterHandler.getFirstMeasure();
    }

    /**
     * Prints a classified emotion to the systems standard output stream (console).
     * Every frame is printed on a new line. The main interest of a classification is the measured emotion.
     *
     * @param emotion the result of the dempster shaefer implementation
     * @param i the index of the frame for cleaner visual appearance
     */
    private void printClassificationResult(Measure emotion, int i) {
        MeasureEntry winner = emotion.getWinner();
        System.out.println((i+1) + ". " + winner.getEmotion()
                + " = Plausability: " + MathUtils.round(emotion.calculatePlausability(winner.getEmotionIndex()))
                + ", Belief: " + MathUtils.round(emotion.calculateBelief(winner.getEmotionIndex()))
                + ", Doubt: " + MathUtils.round(emotion.calculateDoubt(winner.getEmotionIndex())));
    }

}
