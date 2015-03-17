package com.gmail.pmeznar.lotr.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class StartGameClickHandler implements ClickHandler{
	private String gameName;
	
	public StartGameClickHandler(String gameName){
		this.gameName = gameName;
	}

	@Override
	public void onClick(ClickEvent event) {
		LotrProxy.startGame(gameName);
	}
	
}
