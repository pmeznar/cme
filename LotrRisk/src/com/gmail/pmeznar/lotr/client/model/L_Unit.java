package com.gmail.pmeznar.lotr.client.model;

import com.google.gwt.user.client.Window;

public class L_Unit {
	String name;
	int cost;
	int noise;
	int number;
	int databaseId;
	
	public L_Unit(String name, int cost, int noise, int databaseId){
		this.name = name;
		this.cost = cost;
		this.noise = noise;
		this.databaseId = databaseId;
		number = 0;
	}
	
	public L_Unit(String name, int cost, int noise, int databaseId, int number){
		this.name = name;
		this.cost = cost;
		this.noise = noise;
		this.databaseId = databaseId;
		this.number = number;
	}
	
	public static L_Unit load(String[] details){
		int id = Integer.parseInt(details[0]);
		String name = details[1];
		int noise = Integer.parseInt(details[2]);
		int cost = Integer.parseInt(details[3]);
		int num = Integer.parseInt(details[4]);
		
		L_Unit unit = new L_Unit(name, cost, noise, id, num);
		
		return unit;
	}
	
	public void showInfo(){
		Window.alert("Name: " + name +
				"\nCost: " + cost +
				"\nNoise: " + noise +
				"\nNumber: " + number);
	}
	
	public int getTotalCost(){
		return number * cost;
	}
	
	public String getName(){
		return name;
	}
	
	public int getCost(){
		return cost;
	}
	
	public int getNoise(){
		return noise;
	}
	
	public int getNumber(){
		return number;
	}
}
