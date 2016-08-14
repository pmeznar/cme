package com.gmail.pmeznar.lotr.client.web;

import com.gmail.pmeznar.lotr.client.model.Alliance;

/**
 * A Singleton that retains information for a logged in user.  Given values at login, cleared when logged out.
 * @author pmeznar
 */
public class PlayerData {
	private static PlayerData playerData;
	
	private String username;
	Alliance alliance;
	
	private PlayerData(String username)
	{
		this.username = username;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	protected void setAlliance(Alliance alliance)
	{
		this.alliance = alliance;
	}
	
	public Alliance getAlliance()
	{
		return alliance;
	}
	
	protected static void create(String username)
	{
		playerData = new PlayerData(username);
	}
	
	public static PlayerData get()
	{
		if (playerData == null)
		{
			throw new IllegalStateException("Can't get player data when not logged in");
		}
		
		return playerData;
	}
	
	public static void clear()
	{
		playerData = null;
	}
}