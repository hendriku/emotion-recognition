package de.emotreco.main.dempster;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a single measure within a Dempster-Schaefer calculation.
 * Contains multiple entries and allows the calculation of plausability, believe and doubt for alternatives
 * @author Ben Förnrohr, edited: Hendrik Ulbrich, Malte Bartels
 */
public class Measure{
	
	/** size of the measure-entries */
	private int size;
	
	/** List holding the various measure-entries */
	private List<MeasureEntry> entries = new ArrayList<MeasureEntry>();
	
	/** reference to the entry in the entry-list, thast represents the omega-remainder */
	private MeasureEntry omegaEntry;
	
	public Measure(int size) {
		this.size = size;
		//set the omega-Entry to all 1
		List<Integer> omegaEntryList = new ArrayList<Integer>();
		for (int i = 0 ; i < size; i++) {
			omegaEntryList.add(1);			
		}
		
		this.omegaEntry = new MeasureEntry(this.size, omegaEntryList, 1.0);
		this.entries.add(omegaEntry);
	}
	
	/**
	 * Add an entry for the Measure by submitting the entry-values and the probability of the entry
	 * @param entries of entrys. Should be the same size of the Measure
	 * @param probability probability of the entry
	 */
	public void addEntry(List<Integer> entries, double probability) {
		if (entries.size() == this.size) {
			//check if entry already exists
			for (MeasureEntry me : this.entries) {
				if (isSameEntry(me.getValues(), entries)) {
					me.setProbability(me.getProbability() + probability);
					this.omegaEntry.setProbability(this.omegaEntry.getProbability()-probability);
					return;
				}			
			}
			//entry does not exist yet?
			this.entries.add(new MeasureEntry(size, entries, probability));
			this.omegaEntry.setProbability(this.omegaEntry.getProbability()-probability);
		}
	}

	/**
	 * Sorts a copy of the entry list and return the MeasureEntry with the highest probability.
	 *
	 * @return the top of the ordered measure entries. Exception if empty.
	 */
	public MeasureEntry getWinner() {
		List<MeasureEntry> prioList = new ArrayList<>(entries);
		prioList.sort((o1, o2) -> Double.compare(o2.getProbability(), o1.getProbability()));
		return prioList.get(0);
	}

	/**
	 * Returns all Measure-Entries
	 * 
	 * @return a List of all Measure-Entries
	 */
	public List<MeasureEntry> getMeasureEntrys() {
		return entries;
	}
	
	/**
	 * Calculates the belief for a given index	
	 * @param index the index 
	 * @return the belief for the index or 0.0 if the index is too high for the size
	 */
	public double calculateBelief(int index) {
		double belief = 0.0;
		if(index < this.size) {
			for (MeasureEntry entry : this.entries) {
				List<Integer> valueList = entry.getValues();
				if (valueList.get(index) == 1) {
					//for belief of a single entry, all other positions must be "0"
					boolean isBeliefMeasureEntry = true;
					for (int i=0; i<valueList.size(); i++) {
						if (i != index && valueList.get(i) != 0) {
							isBeliefMeasureEntry = false;
						}
					}
					if (isBeliefMeasureEntry)
						belief = belief + entry.getProbability();
				}
			}
		}
		return belief;
	}
	
	/**
	 * Calculates the plausability for a given index	
	 * @param index the index 
	 * @return the plausability for the index or 0.0 if the index is too high for the size
	 */
	public double calculatePlausability(int index) {
		double plausability = 0.0;
		if(index < this.size) {
			for (MeasureEntry entry : this.entries) {
				// sum up all the MeasuerEntrys that have a "1" at the index's position
				if (entry.getValues().get(index) == 1) {
					plausability = plausability + entry.getProbability();
				}
			}
		}
		return plausability;
	}
	/**
	 * Calculates the doubt for a given index	
	 * @param index the index 
	 * @return the doubt for the index or 1.0 of the index is too high for the size
	 */
	public double calculateDoubt(int index) {
		return 1 - this.calculatePlausability(index);
	}
	
	public String toString() {
		String retString = "";
		for (MeasureEntry entry: entries) {
			retString = retString + entry.toString();
		}
		return retString;
	}	

	/**
	 * Checks whether 2 Lists contain the same entries.
	 * @param list1 first list
	 * @param list2 second list
	 * @return true if lists contain same entries
	 */
	private boolean isSameEntry(List<Integer> list1, List<Integer> list2) {
		for (int i = 0; i < this.size; i++) {
			if (list1.get(i) != list2.get(i)) {
				return false;				
			}
		}
		return true;
	}
}