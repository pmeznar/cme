package com.gmail.pmeznar.lotr.client.model;

import java.util.ArrayList;

import com.gmail.pmeznar.lotr.client.TerritoryBox;

public class Territory {
	private TerritoryBox box;
	String name;
	ArrayList<Warband> warbands;
	int allegiance;
	
	public Territory(TerritoryBox box, String name)
	{
		this.name = name;
		this.box = box;
		this.warbands = new ArrayList<Warband>();
		this.allegiance = 0;
	}
	
}
