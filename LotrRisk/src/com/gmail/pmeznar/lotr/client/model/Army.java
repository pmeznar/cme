package com.gmail.pmeznar.lotr.client.model;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;


public class Army {
	int databaseId;
	String name;
	ArrayList<Warband> warbands = new ArrayList<Warband>();
	
	public Army(String name, int databaseId){
		this.name = name;
		this.databaseId = databaseId;
	}
	
	public String getName(){
		return name;
	}
	
	public void addWarband(Warband w){
		warbands.add(w);
	}
	
	public void showInfo(){
		Window.alert("Army name: " + name);
		for(Warband warband: warbands){
			warband.showInfo();
		}
	}
	
	public ArrayList<Warband> getWarbands(){
		return warbands;
	}
	
	public int getDatabaseId(){
		return databaseId;
	}
}
