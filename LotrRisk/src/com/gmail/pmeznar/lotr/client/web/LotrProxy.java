package com.gmail.pmeznar.lotr.client.web;

import com.gmail.pmeznar.lotr.client.ProxyReceiver;
import com.gmail.pmeznar.lotr.client.model.Alliance;
import com.gmail.pmeznar.lotr.client.model.Army;
import com.gmail.pmeznar.lotr.client.model.Hero;
import com.gmail.pmeznar.lotr.client.model.Troop;
import com.gmail.pmeznar.lotr.client.model.Warband;
import com.gmail.pmeznar.lotr.client.widgets.StartGameClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LotrProxy {
	public static String BASE = "lotrphp/";
    static boolean debug = false;
	
	public static void isLoggedIn(){
		new LotrRequest("IsLoggedIn.php"){
			@Override
			public void receive(LotrResponse response) {
				if(response.status == LotrResponse.SUCCESS){
					LotrProxy.getGames();
				} else {
					RootPanel.get().add(new LoginPage());
				}
			}
		};
	}
	
	public static void login(final String username, String password){
		new LotrRequest("Login.php?username="+username+"&password="+password) {
			
			@Override
			public void receive(LotrResponse response) {
				if(response.status == LotrResponse.SUCCESS){
					PlayerData.create(username);
					getGames();
				} else {
					Window.alert("User/password combination doesn't exist.\n" +
						"Please try again.");
				}
			}
		};
	}
	
	public static void getGames(){
		new LotrRequest("CurrentGames.php"){
	
			@Override
			public void receive(LotrResponse response) {
				new GameSelectPage().loadGames(response);				
			}
			
		};
	}

	public static void getAllianceDetails(String gameName, final ProxyReceiver receiver){
		new LotrRequest("GetAllianceDetails?game="+gameName) {
			
			@Override
			public void receive(LotrResponse response) {
				receiver.receive(response.messageArray);
			}
		};
	}
	
	public static void getUsers(final ProxyReceiver receiver){
		new LotrRequest("GetPlayers.php"){

			@Override
			public void receive(LotrResponse response) {
				receiver.receive(response.messageArray);
			}
			
		};
	}
	
	public static void getWarbandUnits(int databaseId, ProxyReceiver receiver){
		new LotrRequest("GetWarbandUnits.php?warband="+databaseId){

			@Override
			public void receive(LotrResponse response) {
				
			}
			
		};
	}
	
	public static void downloadGameData(){
		new LotrRequest("DownloadAlliance.php"){
			@Override
			public void receive(LotrResponse response) {
				String allianceName = response.messageArray[0];
				Integer allianceId = Integer.parseInt(response.messageArray[1]);
				if(debug) Window.alert("Alliance: " + allianceName);
				
				Alliance alliance = new Alliance(allianceName, allianceId);
				LotrProxy.downloadArmies(alliance);
				PlayerData.get().setAlliance(alliance);
			}
		};
	}
	
	public static void downloadArmies(final Alliance alliance){
		new LotrRequest("DownloadArmy.php?allianceId=" + alliance.getDatabaseId()){
			@Override
			public void receive(LotrResponse response) {
				String[] list = response.messageArray;
				for(int i = 0; i < list.length; i += 2){
					String armyName = list[i];
					Integer armyId = Integer.parseInt(list[i + 1]);

					Army army = new Army(armyName, armyId);
					LotrProxy.downloadWarbands(army, alliance.getDatabaseId());

					alliance.addArmy(army);
				}
			}
			
		};
	}
	
	public static void downloadWarbands(final Army army, final int allianceId){
		new LotrRequest("DownloadWarband.php?armyId=" + army.getDatabaseId()){
			@Override
			public void receive(LotrResponse response) {
				String[] list = response.messageArray;
				for(int i = 0; i < list.length; i += 3){
					String databaseId = list[i];
					String hiddenIndicator = list[i + 1];
					String warbandName = list[i + 2];

					String[] details = {databaseId, hiddenIndicator, warbandName};
					Warband warband = Warband.load(details, army.getDatabaseId(), allianceId);

					if(debug) Window.alert("Warband: " + warband.getName());
					LotrProxy.downloadWarbandContents(warband);
					army.addWarband(warband);
				}
			}
		};
	}
	
	public static void downloadWarbandContents(final Warband warband){
		new LotrRequest("DownloadWarbandUnits.php?warbandId=" + warband.getDatabaseId()){
			@Override
			public void receive(LotrResponse response) {
				String[] list = response.messageArray;
				for(int i = 0; i < list.length; i += 5){
		            String id = list[i];
 		            String name = list[i + 1];
            		String noise = list[i + 2];
            		String cost = list[i + 3];
            		String num = list[i + 4];
					String[] details = {id, name, noise, cost, num};

					warband.addTroop(Troop.load(details));
					if(debug) Window.alert("Adding " + list[i+1] + " to " + warband.getName());
				}
			}
		};
		
		new LotrRequest("DownloadWarbandHeros?warbandId=" + warband.getDatabaseId()){
			@Override
			public void receive(LotrResponse response) {
				String[] list = response.messageArray;
				for(int i = 0; i < list.length; i+=13){
					String[] details = {list[i], list[i+1], list[i+2], list[i+3],
										list[i+4], list[i+5], list[i+6], list[i+7],
										list[i+8], list[i+9], list[i+10], list[i+11], list[i+12]};
					warband.addHero(Hero.load(details));							
					if(debug) Window.alert("Adding " + list[i+12] + " to " + warband.getName());
					}
				}
			};
	}
	
	public static void startGame(final String gameName){
		new LotrRequest("GetGameStarted.php?game="+gameName){

			@Override
			public void receive(LotrResponse response) {
				if(response.status == LotrResponse.SUCCESS){
					if(response.message.equals("started")){
						//go to map page, I guess
					} else if(response.message.equals("armies_loaded")){
						LotrProxy.downloadGameData();
						new MapPage().load();
					} else {
						new Window_ConstructAlliance(gameName);
					}
				}
				
			}
			
		};
		
	}
	
	public static void uploadGameData(final String gameName, String alliNames, String
			alliPoints, String players, String playerAlliances, final VerticalPanel pnlGamesList){
		String str = "UploadGameData.php?g="+gameName+"&an="+alliNames+
				"&ap="+alliPoints+"&p="+players+"&pa="+playerAlliances;
		Window.alert(str);
		new LotrRequest("UploadGameData.php?g="+gameName+"&an="+alliNames+
				"&ap="+alliPoints+"&p="+players+"&pa="+playerAlliances){

			@Override
			public void receive(LotrResponse response) {
				if(!(response.status == LotrResponse.SUCCESS)){
					Window.alert("Error saving game data");
				} else {
					Button newGame = new Button(gameName);
					newGame.setWidth("100%");
					newGame.addClickHandler(new StartGameClickHandler(gameName));
					pnlGamesList.add(newGame);
				}
			}
			
		};
	}
	
	public static void isMyTurn(int allianceId, final ProxyReceiver receiver){
		new LotrRequest("SetupTurn.php?allianceId=" + allianceId) {
			
			@Override
			public void receive(LotrResponse response) {
				String[] str = {"no"};
				if(response.status == LotrResponse.SUCCESS){
					str[0] = "yes";
				}
				receiver.receive(str);
			}
		};
	}
	
	public static void uploadAlliance(final Alliance alliance){
		for(final Army army: alliance.getArmies()){
			new LotrRequest("UploadArmy.php?allianceName="+alliance.getName()+"&armyName="+army.getName()) {
				
				@Override
				public void receive(LotrResponse response) {
					for(final Warband warband: army.getWarbands()){
						new LotrRequest("UploadWarband.php?allianceName="+alliance.getName()+"&armyName="+army.getName()+"&warbandName="+warband.getName()){

							@Override
							public void receive(LotrResponse response) {
								for(final Troop troop: warband.getUnits()){
									new LotrRequest("UploadUnit.php?unitName="+troop.getName()+"&unitCost="+troop.getCost()+"&noise="+troop.getNoise()){

										@Override
										public void receive(
												LotrResponse response) {
											String str = "UploadWarbandUnits.php?allianceName="+alliance.getName()+
													"&armyName="+army.getName()+"&warbandName="+warband.getName()+
													"&unitName="+troop.getName()+"&unitCost="+troop.getCost()+
													"&unitNum="+troop.getNumber()+"&noise="+troop.getNoise();

											new LotrRequest(str){
														@Override
														public void receive(
																LotrResponse response) {
														}
											};
										}
									};
								}

								for(final Hero hero: warband.getHeroes()){
									new LotrRequest("UploadHero.php?allianceName="+alliance.getName()+
											"&armyName="+army.getName()+"&warbandName="+warband.getName()+
											"&might="+hero.getMight()+"&will="+hero.getWill()+"&fate="+hero.getFate()+
											"&wound="+hero.getWounds()+"&leader="+hero.getLeader()+"&cost="+hero.getCost()+
											"&noise="+hero.getNoise()+"&name="+hero.getName()) {
										
										@Override
										public void receive(LotrResponse response) {
										}
									};
								}
								// At this point, all requests are generated, although some may still be being processed...
							}
						};
					}
				}
			};
		}
	}
	
	

}
