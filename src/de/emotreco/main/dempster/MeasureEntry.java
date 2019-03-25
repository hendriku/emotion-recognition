package de.emotreco.main.dempster;

import java.util.List;

/**
 * Class to represent an entry within a measure. Contains a List of values (0 or 1), a fixed size and the probability of the entry.
 * @author Ben Förnrohr, edited: Hendrik Ulbrich, Malte Bartels
 */
public class MeasureEntry{
	
	/** size of the entry, should match that of the holding {@link Measure} */
	private int size;
	
	/** List of values, containing 0s and 1s */
	private List<Integer> values;
	
	/** probability of a single measure */
	private double probability;
	
	MeasureEntry(int size, List<Integer> values, double probability) {
		this.size = size;
		this.values = values;
		this.probability = probability;
	}

	/**
	 * Returns the List of values in the entry.
	 *
	 * @return List of all values e.g [0,0,0,0,1]
	 */
	List<Integer> getValues() {
		return values;
	}

	/**
	 * Get the emotion name for a believable list of values.
	 *
	 * @return String
	 *  [1,0,0,0,0]: NEUTRAL
	 *  [0,1,0,0,0]: SADNESS
	 *  [0,0,1,0,0]: FEAR
	 *  [0,0,0,1,0]: JOY
	 *  [0,0,0,0,1]: DISGUST
	 *  Other: null
	 */
	public String getEmotion() {
		int i = 1;
		for (Integer value : values) {
			if (value == 1) {
				switch (i) {
					case 1:
						return "NEUTRAL";
					case 2:
						return "SADNESS";
					case 3:
						return "FEAR";
					case 4:
						return "JOY";
					case 5:
						return "DISGUST";
				}
			}
			i++;
		}
		return null;
	}

	/**
	 * Return the probability of this entry.
	 * @return double propability
	 */
	public double getProbability() {
		return probability;
	}

	/**
	 * Sets the List of values in the entry
	 * @param values new values
	 */
	public void setValues(List<Integer> values) {
		this.values = values;
	}

	/**
	 * Sets the probability of the entry
	 *
	 * @param probability new propability
	 */
	void setProbability(double probability) {
		this.probability = probability;
	}

	public String toString() {
		String retString = "Set: ";
		for (int i = 0; i < size; i++) {
			retString = retString + (values.get(i)+ " ");
		}
		retString = retString + ("probability: " + probability + "\n");	
		return retString;
	}
}