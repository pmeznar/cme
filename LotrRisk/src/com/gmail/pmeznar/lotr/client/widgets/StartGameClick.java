package com.gmail.pmeznar.lotr.client.widgets;

import com.gmail.pmeznar.lotr.client.web.LotrProxy;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class StartGameClick implements ClickHandler{
	private String gameName;
	
	public StartGameClick(String gameName){
		this.gameName = gameName;
	}

	@Override
	public void onClick(ClickEvent event) {
		LotrProxy.startGame(gameName);
	}
	
}
