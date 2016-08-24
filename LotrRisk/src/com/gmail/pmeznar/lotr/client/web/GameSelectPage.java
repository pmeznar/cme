package com.gmail.pmeznar.lotr.client.web;

import com.gmail.pmeznar.lotr.client.widgets.StartGameClick;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Page for selecting an existing game or creating a new one.
 * 
 * Exists via either a {@link NewGameClick} or {@link StartGameClick}.
 * @author pmeznar
 */
public class GameSelectPage extends Composite{
	@UiField VerticalPanel pnlGamesList;
	@UiField Button btnNewGame;
	private static GameSelectPageUiBinder uiBinder = GWT
			.create(GameSelectPageUiBinder.class);

	interface GameSelectPageUiBinder extends UiBinder<Widget, GameSelectPage> {
	}

	public GameSelectPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	/**
	 * Displays the game select page and shows buttons for all active games, along with a new game button.
	 */
	public void loadAndDisplayGames(LotrResponse response){
		RootPanel.get().clear();
		RootPanel.get().add(this);
		
		if(response.status == LotrResponse.SUCCESS){
			for(String game: response.messageArray){
				Button tmpButton = new Button(game);
				tmpButton.addClickHandler(new StartGameClick(game));
				tmpButton.setWidth("100%");
				pnlGamesList.add(tmpButton);
			}
		} 
		
		btnNewGame.addClickHandler(new NewGameClick());
	}
	
	/**
	 * ClickHandler for setting up a new game.
	 */
	private class NewGameClick implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			new Window_GameSetup(pnlGamesList);
		}
	}
}