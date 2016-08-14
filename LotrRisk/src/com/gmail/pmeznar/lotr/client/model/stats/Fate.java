package com.gmail.pmeznar.lotr.client.model.stats;

/**
 * The fate statistic of a troop.
 * @author pmeznar
 */
public class Fate {
	private int maxFate;
	private int currentFate;
	
	/**
	 * Initialize current and max fate to the given value (all units start with full fate).
	 */
	public Fate(int fate)
	{
		maxFate = fate;
		currentFate = fate;
	}
	
	public Fate(int currentFate, int maxFate)
	{
		this.currentFate = currentFate;
		this.maxFate = maxFate;
	}
	
	/**
	 * Set the current fate to a new number.
	 */
	public void update(int currentFate)
	{
		this.currentFate = currentFate;
	}
	
	/**
	 * Increase the amount of fate by <code>points</code>.  Will not increase beyond the units <code>maxFate</code>.
	 */
	public void replenish(int points)
	{
		currentFate += points;
		if (currentFate > maxFate)
		{
			currentFate = maxFate;
		}
	}
	
	/**
	 * Returns the current Fate the unit has.
	 */
	public int get()
	{
		return currentFate;
	}
	
	/**
	 * Update the maximum amount of fate available for the unit.
	 */
	public void setMax(int maxFate)
	{
		this.maxFate = maxFate;
	}
	
	public String toString()
	{
		return "CurrentFate: " + currentFate + "\nMaxFate: " + maxFate;
	}
}