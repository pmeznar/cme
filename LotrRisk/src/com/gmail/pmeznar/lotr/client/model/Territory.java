package com.gmail.pmeznar.lotr.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.gmail.pmeznar.lotr.client.TerritoryBox;

// TODO Create factory that returns territory types differentiated just by the different capitalized field values...
// TGBABW!
public class Territory {
	final String name;
	final private TerritoryBox box;
	final private int MAX_RESURRECT_PER_WARBAND;
	final private int REQUIRED_STORED_REST_TURN;

	final private ArrayList<Warband> warbands;
	final private Map<Integer, Integer> storedRestsByWarbandId;
	final private Map<Integer, Integer> replenishesLeftByWarbandId;

	private int resurrectsRemaining;
	private int allegiance;
	
	// TODO Add city/fortress stuff to excel sheet...
	public Territory(TerritoryBox box, String name, int maxResurrects, int requiredRest)
	{
		this.name = name;
		this.box = box;
		this.MAX_RESURRECT_PER_WARBAND = maxResurrects;
		this.REQUIRED_STORED_REST_TURN = requiredRest;

		allegiance = 0;
		warbands = new ArrayList<Warband>();
		storedRestsByWarbandId = new HashMap<Integer, Integer>();
		replenishesLeftByWarbandId = new HashMap<Integer, Integer>();
	}
	
}
