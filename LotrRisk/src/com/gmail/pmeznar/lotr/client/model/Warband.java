package com.gmail.pmeznar.lotr.client.model;

import java.util.ArrayList;

import com.gmail.pmeznar.lotr.client.ProxyReceiver;
import com.gmail.pmeznar.lotr.client.web.LotrProxy;
import com.google.gwt.user.client.Window;

public class Warband implements ProxyReceiver{
	String name;
	final int databaseId, armyId, allianceId;
	
	final ArrayList<Troop> units;
	final ArrayList<Hero> heroes;

	boolean hidden;
	boolean toTheKing;
	Territory territory;
	int moveRate;
	
	public static Warband load(String[] details, int armyId, int allianceId){
		int databaseId = Integer.parseInt(details[0]);
		int hidden = Integer.parseInt(details[1]);
		
		boolean isHidden = hidden > 0;
		boolean isToTheKing = false; // TODO read 'to the king' from DB

		Warband warband = new Warband(details[2], databaseId, armyId, allianceId, isHidden, isToTheKing);

		return warband;
	}
	
	public Warband(String name, int databaseId, int armyId, int allianceId, boolean hidden, boolean toTheKing){
		this.name = name;

		this.hidden = hidden;
		this.toTheKing = toTheKing;

		this.databaseId = databaseId;
		this.armyId = armyId;
		this.allianceId = allianceId;

		units = new ArrayList<Troop>();
		heroes = new ArrayList<Hero>();
	}
	
	public void loadUnits(){
		LotrProxy.getWarbandUnits(databaseId, this);
	}

	@Override
	public void receive(Object[] objects) {
				
	}
	
	public boolean addTroop(Troop unit){
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
		for(Troop unit: units){
			unit.showInfo();
		}
	}
	
	private int getNumberUnits(){
		int num = 0;
		for(Troop unit: units){
			num += unit.number;
		}
	
		num += heroes.size();
		
		return num;
	}
	
	public String getName(){
		return name;
	}
	
	public ArrayList<Troop> getUnits(){
		return units;
	}
	
	public int getDatabaseId(){
		return this.databaseId;
	}

}
