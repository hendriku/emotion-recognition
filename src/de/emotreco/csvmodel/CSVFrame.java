package de.emotreco.csvmodel;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Represents a row in the csv file format.
 */
public class CSVFrame {

    // Possible keys: x, y, xright, ylow, fob, lea, lbd, rea, rbd, hnc, vnc, lcw, rcw, ma;
    private HashMap<String, Integer> columns = new LinkedHashMap<>();
    private String[] columnArray;

    public CSVFrame(String csvRow) {
        columnArray = csvRow.split(";");

        // Validate amount of columns
        if (columnArray.length != CSVImporter.COLUMN_NAMES.length) {
            throw new RuntimeException("Found a row that does not match expected format.\nRow: "
                    + csvRow+ " has " + columnArray.length + ".\nA row should have " + CSVImporter.COLUMN_NAMES.length + " columns. Columns can be empty but have to append separation token anyway (;).");
        }

        for (int i = 0; i < CSVImporter.COLUMN_NAMES.length; i++) {
            // Only put the key if there was data measured
            if (columnArray[i].length() > 0) {
                columns.put(CSVImporter.COLUMN_NAMES[i],Integer.parseInt(columnArray[i]));
            }
        }
    }

    public HashMap<String, Integer> getColumns() {
        return columns;
    }

    /**
     * The source string which created this CSVFrame.
     *
     * @return The joined columns
     */
    public String getCsvRow() {
        return String.join(";", columnArray);
    }

    /**
     * Get the row number of the frames from the csv data
     * @return Content of Nr.
     */
    public int getFrameNumber() {
        return columns.get("Nr.");
    }

    /**
     * Get the x coordinate of the upper left edge of the region of interest (RoI)
     * @return Content of x
     */
    public int getX() {
        return columns.get("x");
    }

    /**
     * Get the y coordinate of the upper left edge of the region of interest (RoI)
     * @return Content of y
     */
    public int getY() {
        return columns.get("y");
    }

    /**
     * Get the width of the region of interest (RoI)
     * @return Content of xright
     */
    public int getWidth() {
        return columns.get("xright");
    }

    /**
     * Get the height of the region of interest (RoI)
     * @return Content of ylow
     */
    public int getHeight() {
        return columns.get("ylow");
    }

    /**
     * Get the pixel-based value for measured forehead wrinkles via the furrowing of the browe
     * @return Content of fob
     */
    public int getFurrowingOfBrowe() {
        return columns.get("fob");
    }

    /**
     * Get the pixel indicator for detected points on an oval of the left eye
     * @return Content of lea
     */
    public int getLeftEyeAperture() {
        return columns.get("lea");
    }

    /**
     * Get the pixel indicator for detected points on an oval of the right eye
     * @return Content of lea
     */
    public int getRightEyeAperture() {
        return columns.get("rea");
    }

    /**
     * Get the pixel-based value for measured distance of the left browe
     * @return Content of lbd
     */
    public int getLeftBroweDistance() {
        return columns.get("lbd");
    }

    /**
     * Get the pixel-based value for measured distance of the right browe
     * @return Content of rbd
     */
    public int getRightBroweDistance() {
        return columns.get("rbd");
    }

    /**
     * Get the pixel-based value for measured horizontal nose crinkles
     * @return Content of hnc
     */
    public int getHorizontalNoseCrinkles() {
        return columns.get("hnc");
    }

    /**
     * Get the pixel-based value for measured vertical nose crinkles
     * @return Content of vnc
     */
    public int getVerticalNoseCrinkles() {
        return columns.get("vnc");
    }

    /**
     * Get the pixel-based value for measured left cheek wrinkle
     * @return Content of lcw
     */
    public int getLeftCheekWrinkle() {
        return columns.get("lcw");
    }

    /**
     * Get the pixel-based value for measured right cheek wrinkle
     * @return Content of rcw
     */
    public int getRightCheekWrinkle() {
        return columns.get("rcw");
    }

    /**
     * Get the pixel-based value for measured mouth aperture
     * @return Content of ma
     */
    public int getMouthAperture() {
        return columns.get("ma");
    }

    /**
     * For debugging purposes.
     *
     * @return Example: { Nr: 1, x: 92, y: 145, xright: 344, ylow: 532, fob: 25361, lea: 21, lbd: 21, rea: 21, rbd: 19, hnc: 5, vnc: 3, lcw: 8, rcw: 8, ma: 80  }
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ ");
        columns.forEach((k, v) -> stringBuilder.append(k).append(": ").append(v).append(", "));
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length() - 1);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
