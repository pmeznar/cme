package com.gmail.pmeznar.lotr.client.model;

import com.google.gwt.user.client.Window;

public class Hero extends L_Unit {
	int might, will, fate, maxMight, maxWill, maxFate, wound, maxWound;
	boolean leader;

	public Hero(String name, int cost, int noise, int databaseId) {
		super(name, cost, noise, databaseId);
	}
	
	public static Hero load(String[] details){
		int id = Integer.parseInt(details[0]);
		int might = Integer.parseInt(details[1]);
		int will = Integer.parseInt(details[2]);
		int fate = Integer.parseInt(details[3]);
		int wound = Integer.parseInt(details[4]);
		int maxMight = Integer.parseInt(details[5]);
		int maxWill = Integer.parseInt(details[6]);
		int maxFate = Integer.parseInt(details[7]);
		int maxWound = Integer.parseInt(details[8]);
		int isLeader = Integer.parseInt(details[9]);
		boolean leader;
		if(isLeader == 1) leader = true;
		else leader = false;
		int noise = Integer.parseInt(details[10]);
		int cost = Integer.parseInt(details[11]);
		String name = details[12];
		
		Hero hero = new Hero(name, cost, noise, might, will, fate, wound);
		hero.maxMight = maxMight;
		hero.maxWill = maxWill;
		hero.maxFate = maxFate;
		hero.maxWound = maxWound;
		hero.leader = leader;
		hero.databaseId = id;
		
		return hero;
	}
	
	public Hero(String name, int cost, int noise, int might, int will, int fate, int wound){
		super(name, cost, noise, 0);
		this.might = might;
		this.will = will;
		this.fate = fate;
		this.wound = wound;
		this.maxMight = might;
		this.maxWill = will;
		this.maxFate = fate;
		this.maxWound = wound;
		leader = false;
	}
	
	public boolean isLeader(){
		return leader;
	}
	
	public void setLeader(boolean value){
		this.leader = value;
	}
	
	public void showInfo(){
		String leaderS;
		if(leader) leaderS = "true";
		else leaderS = "false";
		
		Window.alert("Name: " + name +
				"\nMight: " + might +"/" + maxMight +
				"\nWill: " + will + "/" + maxWill +
				"\nFate: " + fate + "/" + maxFate +
				"\nWounds: " + wound + "/" + maxWound +
				"\nCost: " + cost + 
				"\nLeader: " + leaderS);
	}
	
	public int getCost(){
		return cost;
	}
	
	public int getMight(){
		return might;
	}
	
	public int getWill(){
		return will;
	}
	
	public int getFate(){
		return fate;
	}
	
	public int getWound(){
		return wound;
	}
	
	public int getLeader(){
		if(leader) return 1;
		else return 0;
	}

}
