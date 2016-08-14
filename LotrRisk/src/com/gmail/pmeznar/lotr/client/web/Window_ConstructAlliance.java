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
		
		this.center();
	}
	
	private void setupInitialWindow(String allianceName, String alliancePoints){
		alliance = new Alliance(allianceName, Integer.parseInt(alliancePoints), 0);
		
		HorizontalPanel pnlDetails = new HorizontalPanel();
		Label aName = new Label(allianceName+":");
		aName.setWidth("350px");
		pnlDetails.add(aName);
		pnlDetails.add(new Label(alliancePoints));
		
		VerticalPanel armyPanel = new VerticalPanel();
		Button btnAddArmy = new Button("Add Army");
		btnAddArmy.addClickHandler(new AddArmyClickHandler(armyPanel, this));
		
		HorizontalPanel warbandPanel = new HorizontalPanel();
		warbandPanel.setStyleName("spanningRow");
		vpnlWarbandName.add(new Label("NAME"));
		vpnlWarbandPoint.add(new Label("POINTS"));
		vpnlWarbandArmy.add(new Label("ARMY"));
		warbandPanel.add(vpnlWarbandName);
		warbandPanel.add(vpnlWarbandPoint);
		warbandPanel.add(vpnlWarbandArmy);
		
		HorizontalPanel pnlButtons = new HorizontalPanel();
		Button setButton = new Button("Set Alliance");
		setButton.addClickHandler(new SetAllianceClickHandler());
		pnlButtons.add(setButton);
		pnlButtons.add(closeButton);
		
		pnlBase.add(pnlDetails);
		pnlBase.add(armyPanel);	
		pnlBase.add(btnAddArmy);
		pnlBase.add(warbandPanel);
		pnlBase.add(pnlButtons);		
	}
	
	private class SetAllianceClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			Hero hero = alliance.getKing();
			if(hero == null){
				Window.alert("Sorry... you don't have a king.  Unless you can add one now\n" +
						"you're going to have to start all over....  I'll make this nicer later.");
				return;
			}
			
			for(Army army: armies){
				alliance.addArmy(army);
			}
			
			//alliance.showInfo();
			
			PlayerData.get().setAlliance(alliance);
			LotrProxy.uploadAlliance(alliance);
			closeButton.click();
		}
		
	}
	
	private class AddArmyClickHandler implements ClickHandler{
		private VerticalPanel armyPanel;
		private Window_ConstructAlliance window;
		
		public AddArmyClickHandler(VerticalPanel armyPanel, Window_ConstructAlliance window){
			this.armyPanel = armyPanel;
			this.window = window;
		}

		@Override
		public void onClick(ClickEvent event) {
			Label lblArmy = new Label("Army name: ");
			TextBox txtArmy = new TextBox();
			Button addWarband = new Button("Add Warband");
			HorizontalPanel oneArmy = new HorizontalPanel();
			
			addWarband.addClickHandler(
					new AddWarbandClickHandler(txtArmy, window));
			
			oneArmy.add(lblArmy);
			oneArmy.add(txtArmy);
			oneArmy.add(addWarband);
			
			armyPanel.add(oneArmy);
		}
	}
	
	private class AddWarbandClickHandler implements ClickHandler{
		private TextBox armyName;
		private Window_ConstructAlliance window;

		public AddWarbandClickHandler(TextBox armyName, Window_ConstructAlliance window){
			this.armyName = armyName;
			this.window = window;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			// Once you make an warband for an army, you cannot change army name
			if(armyName.getText().equals("")){
				Window.alert("Please enter an army name");
				return;
			} else if ((!(armyName.isReadOnly()) && armyNames.contains(armyName.getText()))){
				Window.alert("Army names must be distinct");
				return;
			}

			new Window_ConstructWarband(window, armyName);			
		}
		
	}

	@Override
	public void receive(Object[] objects) {
		setupInitialWindow((String) objects[0], (String) objects[1]);
	}
}
