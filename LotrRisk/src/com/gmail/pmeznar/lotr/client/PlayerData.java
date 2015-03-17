package com.gmail.pmeznar.lotr.client;

import com.gmail.pmeznar.lotr.client.model.Alliance;
import com.gmail.pmeznar.lotr.client.model.Army;

public class PlayerData {
	public static Alliance alliance;
	public static String username;
	
	public static void spoof(){
		alliance = new Alliance("alliance", 1000, 1);
		Army army1 = new Army("army1", 0);
		Army army2 = new Army("army2", 0);
		alliance.addArmy(army1);
		alliance.addArmy(army2);
	}
}
