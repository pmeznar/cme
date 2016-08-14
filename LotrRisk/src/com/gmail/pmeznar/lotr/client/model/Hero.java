package com.gmail.pmeznar.lotr.client.model;

import com.gmail.pmeznar.lotr.client.model.stats.Fate;
import com.gmail.pmeznar.lotr.client.model.stats.Might;
import com.gmail.pmeznar.lotr.client.model.stats.Will;
import com.gmail.pmeznar.lotr.client.model.stats.Wounds;
import com.google.gwt.user.client.Window;

public class Hero extends Troop {
	private int moveRate;
	private Fate fate;
	private Might might;
	private Will will;
	private Wounds wounds;
	final private boolean leader;

	public Hero(String name, int cost, int noise, Might might, Will will, Fate fate, Wounds wound, boolean leader, int id){
		super(name, cost, noise, 0/* TODO: Moverate */, true, id, 1);
		this.might = might;
		this.will = will;
		this.fate = fate;
		this.wounds = wound;
		this.leader = leader;
	}

	public static Hero load(String[] details){
		int id = Integer.parseInt(details[0]);
		int currentMight = Integer.parseInt(details[1]);
		int currentWill = Integer.parseInt(details[2]);
		int currentFate = Integer.parseInt(details[3]);
		int currentWound = Integer.parseInt(details[4]);
		int maxMight = Integer.parseInt(details[5]);
		int maxWill = Integer.parseInt(details[6]);
		int maxFate = Integer.parseInt(details[7]);
		int maxWound = Integer.parseInt(details[8]);
		int isLeader = Integer.parseInt(details[9]);
		// TODO add isAlive?
		boolean leader;

		if(isLeader == 1)
			leader = true;
		else
			leader = false;

		int noise = Integer.parseInt(details[10]);
		int cost = Integer.parseInt(details[11]);
		String name = details[12];
		
		Might might = new Might(currentMight, maxMight);
		Wounds wound = new Wounds(currentWound, maxWound);
		Will will = new Will(currentWill, maxWill);
		Fate fate = new Fate(currentFate, maxFate);

		Hero hero = new Hero(name, cost, noise, might, will, fate, wound, leader, id);
		
		return hero;
	}
	
	
	public boolean isLeader(){
		return leader;
	}
	
	public void showInfo(){
		String leaderS;
		if(leader) leaderS = "true";
		else leaderS = "false";
		
		Window.alert("Name: " + name +
				"\nMight: " + might.toString() + 
				"\nWill: " + will.toString() +
				"\nFate: " + fate.toString() + 
				"\nWounds: " + wounds.toString() + 
				"\nCost: " + cost + 
				"\nLeader: " + leaderS);
	}
	
	public int getCost(){
		return cost;
	}
	
	public Might getMight(){
		return might;
	}
	
	public Will getWill(){
		return will;
	}
	
	public Fate getFate(){
		return fate;
	}
	
	public Wounds getWounds(){
		return wounds;
	}
	
	public int getLeader(){
		if(leader) return 1;
		else return 0;
	}
}