package com.gmail.pmeznar.lotr.client.model.stats;

/**
 * The will statistic of a troop.
 * @author pmeznar
 */
public class Will {
	private int maxWill;
	private int currentWill;
	
	/**
	 * Initialize current and max will to the given value (all units start with full will).
	 */
	public Will(int will)
	{
		maxWill = will;
		currentWill = will;
	}
	
	public Will(int currentWill, int maxWill)
	{
		this.currentWill = currentWill;
		this.maxWill = maxWill;
	}
	
	/**
	 * Set the current will to a new number.
	 */
	public void update(int currentWill)
	{
		this.currentWill = currentWill;
	}
	
	/**
	 * Increase the amount of will by <code>points</code>.  Will not increase beyond the units <code>maxWill</code>.
	 */
	public void replenish(int points)
	{
		currentWill += points;
		if (currentWill > maxWill)
		{
			currentWill = maxWill;
		}
	}
	
	/**
	 * Returns the current Will the unit has.
	 */
	public int get()
	{
		return currentWill;
	}
	
	/**
	 * Update the maximum amount of will available for the unit.
	 */
	public void setMax(int maxWill)
	{
		this.maxWill = maxWill;
	}

	public String toString()
	{
		return "CurrentWill: " + currentWill + "\nMaxWill: " + maxWill;
	}
}
