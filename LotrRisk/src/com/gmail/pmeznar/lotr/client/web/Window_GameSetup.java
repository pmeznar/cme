package com.gmail.pmeznar.lotr.client.web;

import java.util.ArrayList;

import com.gmail.pmeznar.lotr.client.ProxyReceiver;
import com.gmail.pmeznar.lotr.client.widgets.CloseButton;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Window_GameSetup extends DialogBox implements ProxyReceiver{
	private String[] players;
	private ArrayList<ListBox> selectedPlayers = new ArrayList<ListBox>();
	private ArrayList<ListBox> selectedAlliances = new ArrayList<ListBox>();
	private ArrayList<TextBox> allianceNames = new ArrayList<TextBox>();
	private ArrayList<TextBox> alliancePoints = new ArrayList<TextBox>();
	private TextBox txtGameName;
	private ListBox lstPlayers;
	private ListBox lstAlliance;
	private VerticalPanel pnlGamesList;
	
	public Window_GameSetup(VerticalPanel pnlGamesList){
		super();
		this.setTitle("Game Setup");
		this.pnlGamesList = pnlGamesList;
		
		LotrProxy.getUsers(this); // populates players
		fillContents();
	}
	
	public void fillContents(){
		VerticalPanel pnlBase = new VerticalPanel();
		
		HorizontalPanel pnlName = new HorizontalPanel();
		Label lblName = new Label("Game name: ");
		txtGameName = new TextBox();
		txtGameName.setWidth("200px");
		pnlName.add(lblName);
		pnlName.add(txtGameName);
		
		pnlBase.add(pnlName);
		
		HorizontalPanel pnlNumPlayers = new HorizontalPanel();
		Label lblNumPlayers = new Label("Number of players:");
		ListBox lstNumPlayers = new ListBox();
		lstNumPlayers.addItem("2", "2");
		lstNumPlayers.addItem("3", "3");
		lstNumPlayers.addItem("4", "4");
		
		VerticalPanel pnlPlayers = new VerticalPanel();
		NumPlayerChangeHandler lstChanger = new NumPlayerChangeHandler(pnlPlayers, lstNumPlayers);
		lstNumPlayers.addChangeHandler(lstChanger);
		
		pnlNumPlayers.add(lblNumPlayers);
		pnlNumPlayers.add(lstNumPlayers);
		pnlBase.add(pnlNumPlayers);
		pnlBase.add(pnlPlayers);
		
		HorizontalPanel pnlButtons = new HorizontalPanel();
		Button btnCreate = new Button("Create Game");
		btnCreate.addClickHandler(new CreateGameClick(this));
		pnlButtons.add(btnCreate);
		
		pnlButtons.add(new CloseButton(this));
		pnlBase.add(pnlButtons);
		
		this.add(pnlBase);
		
		this.center();
	}
	
	private class CreateGameClick implements ClickHandler{
		DialogBox box;
		
		public CreateGameClick(DialogBox box){
			this.box = box;
		}
		@Override
		public void onClick(ClickEvent event) {
			if(validate()){
				uploadGameData();
				//database
				box.hide(true);
			}
			
		}	
	}
	
	private void uploadGameData(){
		// Get game name
		String gameName = txtGameName.getText();
		String alliNames = concatString(allianceNames);
		String alliPoints = concatString(alliancePoints);
		String players = concatString(selectedPlayers);
		String playerAlliances = concatString(selectedAlliances);
		
		LotrProxy.uploadGameData(gameName, alliNames, alliPoints, players, playerAlliances, pnlGamesList);
	}
	
	@SuppressWarnings("rawtypes")
	private String concatString(ArrayList list){
		String concat = "";
		ListBox tmpList;
		TextBox tmpText;
		
		for(Object entry: list){
			if(entry instanceof ListBox){
				tmpList = (ListBox) entry;
				concat += tmpList.getItemText(tmpList.getSelectedIndex()) + ",";
			} else if (entry instanceof TextBox){
				tmpText = (TextBox) entry;
				concat += tmpText.getText() + ",";
			}
		}
		
		concat = concat.substring(0, concat.length() - 1);
		
		return concat;
	}
	
	/**
	 * Handles
	 * @author pmeznar
	 *
	 */
	private class NumPlayerChangeHandler implements ChangeHandler{
		VerticalPanel base;
		ListBox lstNumberOfPlayers;
		
		public NumPlayerChangeHandler(VerticalPanel base, ListBox lstNP){
			this.base = base;
			this.lstNumberOfPlayers = lstNP;
		}
		
		@Override
		public void onChange(ChangeEvent event) {
			allianceNames.clear();
			alliancePoints.clear();
			selectedPlayers.clear();
			selectedAlliances.clear();
			base.clear();
			
			int playerCount = Integer.parseInt(lstNumberOfPlayers.getItemText(lstNumberOfPlayers.getSelectedIndex()));
			int allianceCount = 0;
			// 4 players still just get 2 alliances
			if(playerCount % 2 == 0) {
				allianceCount = 2;
			} else {
				allianceCount = 3;
			}
			
			// Creates multiple lists of all potential players
			for(int i = 0; i < playerCount; i++){
				HorizontalPanel pnlPlayer = new HorizontalPanel();
				
				lstPlayers = new ListBox();
				for(String player: players){
					lstPlayers.addItem(player);
				}
				
				lstAlliance = new ListBox();
				for(int j = 1; j <= allianceCount; j++){
					lstAlliance.addItem(j + "");
				}
				
				pnlPlayer.add(lstPlayers);
				pnlPlayer.add(lstAlliance);
				selectedPlayers.add(lstPlayers);
				selectedAlliances.add(lstAlliance);
				base.add(pnlPlayer);
			}
			
			for(int i = 1; i <= allianceCount; i++){
				HorizontalPanel pnlAlliance = new HorizontalPanel();
				Label numLabel = new Label("Alliance " + i + " name: ");
				pnlAlliance.add(numLabel);
				TextBox txtAlliance = new TextBox();
				TextBox txtAlliancePoints = new TextBox();
				pnlAlliance.add(txtAlliance);
				pnlAlliance.add(txtAlliancePoints);
				allianceNames.add(txtAlliance);
				alliancePoints.add(txtAlliancePoints);
				
				base.add(pnlAlliance);
			}
		}
		
	}

	/**
	 * Basic form validation
	 * @return whether or not Game init is filled out correctly
	 */
	public boolean validate(){
		ArrayList<String> playerNames = new ArrayList<String>();
		ArrayList<String> playerAlliances = new ArrayList<String>();
		ArrayList<String> allNames = new ArrayList<String>();
		
		// Make sure game name is there
		if(txtGameName.getText().equals("")){
			Window.alert("Please enter game name");
			return false;
		}
		
		// Make sure alliance names are there, no duplicate
		for(TextBox txtAlliance: allianceNames){
			if(allNames.contains(txtAlliance.getText())){
				Window.alert("Cannot have duplicate alliance names");
				return false;
			} else {
				allNames.add(txtAlliance.getText());
			}
			
			if(txtAlliance.getText().equals("")){
				Window.alert("Cannot have empty alliance name");
				return false;
			}
		}
		
		ListBox curBox, curAllianceBox;
		// Make sure no duplicate players, multiple alliances
		for(int i = 0; i < selectedPlayers.size(); i++){
			curBox = selectedPlayers.get(i);
			curAllianceBox = selectedAlliances.get(i);
			
			if(playerNames.contains(curBox.getItemText(curBox.getSelectedIndex()))){
				Window.alert("Duplicate player entered");
				return false;
			} else {
				playerNames.add(curBox.getItemText(curBox.getSelectedIndex()));
			}
			
			// look at this monster
			if(!playerAlliances.contains(curAllianceBox.getItemText(curAllianceBox.getSelectedIndex()))){
				playerAlliances.add(curAllianceBox.getItemText(curAllianceBox.getSelectedIndex()));
			}
		}
		
		if(playerAlliances.size() < 2){
			Window.alert("Must have multiple alliances!");
			return false;
		}
		
		for(TextBox alliancePoint: alliancePoints){
			int points = 0;
			try{
				points = Integer.parseInt(alliancePoint.getText());
			} catch (NumberFormatException e){
				Window.alert("Please enter a number");
				alliancePoint.setFocus(true);
				return false;
			}
			
			if(points < 100){
				Window.alert("Alliance must have at least 100 points");
				alliancePoint.setFocus(true);
			}
		}
		
		return true;
	}
	
	/**
	 * Stores all into the players array.
	 */
	@Override
	public void receive(Object[] objects) {
		players = new String[objects.length];
		for(int i = 0; i < objects.length; i++){
			players[i] = (String) objects[i];
		}
	}

}
