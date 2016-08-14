package com.gmail.pmeznar.lotr.client.model;

import com.google.gwt.user.client.Window;

public class Troop {
	final String name;
	final int cost;
	final int noise;
	final int databaseId;

	int number;
	int moveRate;
	boolean alive;
	
	public Troop(String name, int cost, int noise, int moveRate, boolean alive, int databaseId, int number){
		this.name = name;
		this.cost = cost;
		this.noise = noise;
		this.databaseId = databaseId;
		this.number = number;
		this.moveRate = moveRate;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getCost()
	{
		return cost;
	}

	public int getNumber()
	{
		return number;
	}

	public int getNoise()
	{
		return noise;
	}
	
	public static Troop load(String[] details){
		int id = Integer.parseInt(details[0]);
		String name = details[1];
		int noise = Integer.parseInt(details[2]);
		int cost = Integer.parseInt(details[3]);
		int num = Integer.parseInt(details[4]);
		int moveRate = Integer.parseInt(details[5]); // TODO Add move rate to DB
		int alive = Integer.parseInt(details[6]); // TODO Add alive to DB
	
		boolean isAlive = alive > 0;
		Troop unit = new Troop(name, cost, noise, moveRate, isAlive, id, num);
		
		return unit;
	}
	
	// TODO Eh something about marshalling for the database.  Will need changing, and implementation elsewhere.
	public boolean store()
	{
		return false;
	}
	
	// EVENTUALLY Update
	public void showInfo(){
		Window.alert("Name: " + name +
				"\nCost: " + cost +
				"\nNoise: " + noise +
				"\nNumber: " + number);
	}
	
	public int getTotalCost(){
		return number * cost;
	}
}
