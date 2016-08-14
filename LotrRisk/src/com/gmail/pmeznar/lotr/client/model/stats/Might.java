package com.gmail.pmeznar.lotr.client.model.stats;

/**
 * The might statistic of a troop.
 * @author pmeznar
 */
public class Might {
	private int maxMight;
	private int currentMight;
	
	/**
	 * Initialize current and max might to the given value (all units start with full might).
	 */
	public Might(int might)
	{
		maxMight = might;
		currentMight = might;
	}
	
	public Might(int currentMight, int maxMight)
	{
		this.currentMight = currentMight;
		this.maxMight = maxMight;
	}
	
	/**
	 * Set the current might to a new number.
	 */
	public void update(int currentMight)
	{
		this.currentMight = currentMight;
	}
	
	/**
	 * Increase the amount of might by <code>points</code>.  Will not increase beyond the units <code>maxMight</code>.
	 */
	public void replenish(int points)
	{
		currentMight += points;
		if (currentMight > maxMight)
		{
			currentMight = maxMight;
		}
	}
	
	/**
	 * Returns the current Might the unit has.
	 */
	public int get()
	{
		return currentMight;
	}
	
	/**
	 * Update the maximum amount of might available for the unit.
	 */
	public void setMax(int maxMight)
	{
		this.maxMight = maxMight;
	}

	public String toString()
	{
		return "CurrentMight: " + currentMight + "\nMaxMight: " + maxMight;
	}
}
