package com.gmail.pmeznar.lotr.client.web;

import java.util.ArrayList;

import com.gmail.pmeznar.lotr.client.ProxyReceiver;
import com.gmail.pmeznar.lotr.client.model.Alliance;
import com.gmail.pmeznar.lotr.client.model.Army;
import com.gmail.pmeznar.lotr.client.model.Hero;
import com.gmail.pmeznar.lotr.client.widgets.CloseButton;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Window_ConstructAlliance extends DialogBox implements ProxyReceiver{
	private String gameName;
	private VerticalPanel pnlBase = new VerticalPanel();
	VerticalPanel vpnlWarbandName = new VerticalPanel();
	VerticalPanel vpnlWarbandPoint = new VerticalPanel();
	VerticalPanel vpnlWarbandArmy = new VerticalPanel();
	public ArrayList<String> armyNames = new ArrayList<String>();
	public Alliance alliance;
	public ArrayList<Army> armies = new ArrayList<Army>();
	public CloseButton closeButton = new CloseButton(this);

	public Window_ConstructAlliance(String gameName){
		super();
		this.setTitle("Construct Alliance");
		this.gameName = gameName;
		this.add(pnlBase);
		
		LotrProxy.getAllianceDetails(gameName, this);
	}

	@Override
	public void receive(Object[] objects) {
		setupInitialWindow((String) objects[0], (String) objects[1]);
	}
	
	/**
	 * Looks up the army csv's, displays file names to be chosen.
	 * @param allianceName
	 * @param alliancePoints
	 */
	private void setupInitialWindow(String allianceName, String alliancePoints){
		Window.alert("Load files....");
	}
}