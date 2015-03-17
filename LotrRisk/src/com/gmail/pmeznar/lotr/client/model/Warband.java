package com.gmail.pmeznar.lotr.client.model;

import java.util.ArrayList;

import com.gmail.pmeznar.lotr.client.LotrProxy;
import com.gmail.pmeznar.lotr.client.ProxyReceiver;
import com.google.gwt.user.client.Window;

public class Warband implements ProxyReceiver{
	String name, armyName, allianceName;
	int databaseId, armyId, allianceId;
	boolean hidden;
	
	ArrayList<L_Unit> units;
	ArrayList<Hero> heroes;
	
	
	public static Warband load(String[] details, int armyId, int allianceId){
		Warband warband = new Warband(details[2]);
		warband.databaseId = Integer.parseInt(details[0]);
		int hidden = Integer.parseInt(details[1]);
		if(hidden == 1) warband.hidden = true;
		else 			warband.hidden = false;
		warband.armyId = armyId;
		warband.allianceId = allianceId;
		
		return warband;
	}
	
	public Warband(String name){
		this.name = name;
		
		hidden = false;
		units = new ArrayList<L_Unit>();
		heroes = new ArrayList<Hero>();
		databaseId = 0;
	}
	
	public void loadUnits(){
		LotrProxy.getWarbandUnits(databaseId, this);
	}

	@Override
	public void receive(Object[] objects) {
				
	}
	
	public boolean addUnit(L_Unit unit){
		if(getNumberUnits() + unit.number > 13){
			int space = 13 - getNumberUnits();
			Window.alert("Tried to add " + unit.number + " units.\n" +
					"Only have space for " + space);
			return false;
		}
		
		units.add(unit);
		return true;
	}
	
	public boolean addHero(Hero hero){
		if(getNumberUnits() + 1 > 13){
			Window.alert("Warband has 13 units - cannot add more!");
			return false;
		}
		
		heroes.add(hero);
		return true;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public ArrayList<Hero> getHeroes(){
		return heroes;
	}
	
	public void showInfo(){
		String hide;
		if(hidden)  hide = "Hidden";
		else hide = "Not hidden";
		
		Window.alert("Warband Name: " + name + "\nHidden: " + hide);
		for(Hero hero: heroes){
			hero.showInfo();
		}
		for(L_Unit unit: units){
			unit.showInfo();
		}
	}
	
	private int getNumberUnits(){
		int num = 0;
		for(L_Unit unit: units){
			num += unit.number;
		}
	
		num += heroes.size();
		
		return num;
	}
	
	public String getName(){
		return name;
	}
	
	public ArrayList<L_Unit> getUnits(){
		return units;
	}
	
	public int getDatabaseId(){
		return this.databaseId;
	}

}
