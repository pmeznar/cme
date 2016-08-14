package com.gmail.pmeznar.lotr.client;

import org.vaadin.gwtgraphics.client.shape.Rectangle;

import com.gmail.pmeznar.lotr.client.model.Army;
import com.gmail.pmeznar.lotr.client.web.LotrProxy;
import com.gmail.pmeznar.lotr.client.web.PlayerData;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;

public class TerritoryBox extends Rectangle implements ProxyReceiver {
	private HandlerRegistration initialLocs;

	public TerritoryBox(int x, int y, String name) {
		super(x, y, 50, 50);
	}
	
	// Only works when called after this has been added to DOM
	public void setStyle(){
		this.getElement().getStyle().setProperty("fill-opacity", ".3");
		this.getElement().getStyle().setProperty("fill", "gray");
		this.getElement().getStyle().setProperty("stroke-width", "3px");
		this.getElement().getStyle().setProperty("stroke-opacity", ".5");	
	}
	
	public void setChooseArmyLocation(){
		initialLocs = this.addClickHandler(new initialLocationClickHandler(this));
	}
	
	public void removeChooseArmyLocation(){
		initialLocs.removeHandler();
	}
	
	private class initialLocationClickHandler implements ClickHandler{
		TerritoryBox box;
		
		public initialLocationClickHandler(TerritoryBox box){
			this.box = box;
		}
		@Override
		public void onClick(ClickEvent event) {
			LotrProxy.isMyTurn(PlayerData.get().getAlliance().getDatabaseId(), box);
		}
	}

	@Override
	public void receive(Object[] objects) {
		String myTurn = (String) objects[0];
		if(myTurn.equals("yes")){
			String armies = "";
			for(Army army: PlayerData.get().getAlliance().getArmies()){
				armies = armies + army.getName() + "\n";
			}
			Window.alert(armies);
		} else {
			Window.alert("Not your turn to place alliance");
		}
	}
}
