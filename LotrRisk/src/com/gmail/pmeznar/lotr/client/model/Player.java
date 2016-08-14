package com.gmail.pmeznar.lotr.client.model;

import java.awt.Color;

public class Player {
	final Army army;
	final Color color;
	final String playerName;
	
	public Player(Army army, Color color, String playerName){
		this.army = army;
		this.color = color;
		this.playerName = playerName;
	}
}
