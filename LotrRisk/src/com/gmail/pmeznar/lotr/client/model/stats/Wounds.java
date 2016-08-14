package com.gmail.pmeznar.lotr.client.model.stats;

/**
 * The wounds statistic of a troop.
 * @author pmeznar
 */
public class Wounds {
	private int maxWounds;
	private int currentWounds;
	
	/**
	 * Initialize current and max wounds to the given value (all units start with full wounds).
	 */
	public Wounds(int wounds)
	{
		maxWounds = wounds;
		currentWounds = wounds;
	}
	
	public Wounds(int currentWounds, int maxWounds)
	{
		this.currentWounds = currentWounds;
		this.maxWounds = maxWounds;
	}
	
	/**
	 * Set the current wounds to a new number.
	 */
	public void update(int currentWounds)
	{
		this.currentWounds = currentWounds;
	}
	
	/**
	 * Increase the amount of wounds by <code>points</code>.  Will not increase beyond the units <code>maxWounds</code>.
	 */
	public void replenish(int points)
	{
		currentWounds += points;
		if (currentWounds > maxWounds)
		{
			currentWounds = maxWounds;
		}
	}
	
	/**
	 * Returns the current Wounds the unit has.
	 */
	public int get()
	{
		return currentWounds;
	}
	
	/**
	 * Update the maximum amount of wounds available for the unit.
	 */
	public void setMax(int maxWounds)
	{
		this.maxWounds = maxWounds;
	}

	public String toString()
	{
		return "CurrentWounds: " + currentWounds + "\nMaxWounds: " + maxWounds;
	}
}