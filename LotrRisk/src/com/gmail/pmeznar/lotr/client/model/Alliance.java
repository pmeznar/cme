package com.gmail.pmeznar.lotr.client.model;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;

public class Alliance {
	int databaseId;
	int points;
	Hero king = null;
	String name;
	ArrayList<Army> armies = new ArrayList<Army>();
	int curPoints;
	
	public Alliance(String name, int points, int databaseId){
		this.name = name;
		this.points = points;
		this.databaseId = databaseId;
		curPoints = 0;
	}
	
	public Alliance(String name, int databaseId){
		this.name = name;
		this.databaseId = databaseId;
	}
	
	public void addArmy(Army army){
		this.armies.add(army);
	}
	
	public void showInfo(){
		Window.alert("Name: " + name + "\nPoints: " + points);
		for(Army army: armies){
			army.showInfo();
		}
	}
	
	public void setKing(Hero hero){
		this.king = hero;
	}
	
	public Hero getKing(){
		return king;
	}
	
	public int getCurrentPoints(){
		return curPoints;
	}
	
	public int getPointCapacity(){
		return points;
	}
	
	public void addPoints(int points){
		curPoints += points;
	}
	
	public void removePoints(int points){
		curPoints -= points;
	}
	
	public ArrayList<Army> getArmies(){
		return this.armies;
	}
	
	public String getName(){
		return name;
	}
	
	public int getDatabaseId(){
		return databaseId;
	}
}
