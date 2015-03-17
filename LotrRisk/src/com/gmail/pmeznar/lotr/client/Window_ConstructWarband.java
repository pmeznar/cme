package com.gmail.pmeznar.lotr.client;

import com.gmail.pmeznar.lotr.client.model.Army;
import com.gmail.pmeznar.lotr.client.model.Hero;
import com.gmail.pmeznar.lotr.client.model.L_Unit;
import com.gmail.pmeznar.lotr.client.model.Warband;
import com.gmail.pmeznar.lotr.client.widgets.CloseButton;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Window_ConstructWarband extends DialogBox {
	private TextBox txtArmyName, txtWarbandName = new TextBox();
	private VerticalPanel basePanel = new VerticalPanel();
	private CloseButton btnClose = new CloseButton(this);
	Window_ConstructAlliance allianceWindow;
	public CompChart chart;
	private Label lblTotalNoise, lblTotalCost;
	public Warband warband;
	
	public Window_ConstructWarband(Window_ConstructAlliance allianceWindow, TextBox txtArmyName){
		this.allianceWindow = allianceWindow;
		this.txtArmyName = txtArmyName;
		warband = new Warband("tmpName");
		this.add(basePanel);
		
		setupWindow();
		
		
		this.center();
	} 
	
	private void setupWindow(){
		HorizontalPanel pnlName = new HorizontalPanel();
		pnlName.add(new Label("Warband Name: "));
		pnlName.add(txtWarbandName);
		
		HorizontalPanel pnlAddUnits = new HorizontalPanel();
		pnlAddUnits.setStyleName("spanningRow");
		Button btnAddUnit = new Button("Add Units");
		btnAddUnit.setStyleName("addUnitButton");
		btnAddUnit.addClickHandler(new AddUnitClickHandler(this));
		Button btnAddHero = new Button("Add Hero");
		btnAddHero.setStyleName("addUnitButton");
		btnAddHero.addClickHandler(new AddHeroClickHandler(this));
		pnlAddUnits.add(btnAddUnit);
		pnlAddUnits.add(btnAddHero);
		
		Label lblComposition = new Label("Warband Composition:");
		
		chart = new CompChart();
		chart.setStyleName("chart");
		
		HorizontalPanel pnlTotals = new HorizontalPanel();
		pnlTotals.add(new Label("Noise: "));
		lblTotalNoise = new Label("0");
		lblTotalNoise.setStyleName("bumpRight");
		pnlTotals.add(lblTotalNoise);
		Label costLabel = new Label("Cost:");
		costLabel.getElement().getStyle().setPaddingLeft(30.0, Unit.PX);
		pnlTotals.add(costLabel);
		lblTotalCost = new Label("0");
		lblTotalCost.setStyleName("bumpRight");
		pnlTotals.add(lblTotalCost);
		
		
		HorizontalPanel pnlLeader = new HorizontalPanel();
		pnlLeader.add(new Label("Leader: "));
		Label lblLeader = new Label("No leader");
		lblLeader.setStyleName("bumpRight");
		pnlLeader.add(lblLeader);
		
		HorizontalPanel pnlButtons = new HorizontalPanel();
		pnlButtons.setStyleName("spanningRow");
		Button btnSet = new Button("Set Warband");
		btnSet.addClickHandler(new SetWarbandClickHandler());
		
		btnClose.setStyleName("floatRight");
		pnlButtons.add(btnSet);
		btnClose.addClickHandler(new NoGoodClickHandler());
		pnlButtons.add(btnClose);
		
		basePanel.add(new Label(txtArmyName.getText() + " Warband"));
		basePanel.add(pnlName);
		basePanel.add(pnlAddUnits);
		basePanel.add(lblComposition);
		basePanel.add(chart);
		basePanel.add(pnlTotals);
		basePanel.add(pnlLeader);
		basePanel.add(pnlButtons);
	}
	
	private class SetWarbandClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {	
			if(!verify()) return;
		
			txtArmyName.setReadOnly(true);
			allianceWindow.armyNames.add(txtArmyName.getText());

			Army army = null;
			for(int i = 0; i < allianceWindow.armies.size(); i++){
				if(allianceWindow.armies.get(i).getName().equals(txtArmyName.getText())){
					army = allianceWindow.armies.get(i);
				}
			}

			warband.setName(txtWarbandName.getText());
			if(army == null){
				army = new Army(txtArmyName.getText(), 0);
				allianceWindow.armies.add(army);
			}

			army.addWarband(warband);
			
			// Add to alliance window
			Label wName = new Label(warband.getName());
			wName.setStyleName("floatRight");
			Label wPoint = new Label(chart.totalCost + "");
			wPoint.setStyleName("floatRight");
			Label wArmy = new Label(txtArmyName.getText());
			wArmy.setStyleName("floatRight");
			allianceWindow.vpnlWarbandName.add(wName);
			allianceWindow.vpnlWarbandPoint.add(wPoint);
			allianceWindow.vpnlWarbandArmy.add(wArmy);
			
			btnClose.click();
		}
		
	}
	
	public boolean verify(){
		boolean hasLeader = false;
		for(Hero hero: warband.getHeroes()){
			if(hero.isLeader()) hasLeader = true;
		}
		if(!hasLeader){
			Window.alert("Warband is leaderless!  Please add leader.");
			return false;
		}
		
		return true;
	}
	
	public class CompChart extends HorizontalPanel{
		VerticalPanel unitType, unitCost, unitNumber, unitNoise;
		int totalCost, totalNoise;
		
		public CompChart(){
			this.setStyleName("spanningRow");
			setupColumns();
			
			totalCost = 0;
			totalNoise = 0;
			
			this.add(unitType);
			this.add(unitCost);
			this.add(unitNumber);
			this.add(unitNoise);
		}
		
		private void setupColumns(){
			unitType = new VerticalPanel();
			Label lblType = new Label("TYPE");
			lblType.setStyleName("warbandColumn");
			unitType.add(lblType);
			
			unitCost = new VerticalPanel();
			Label lblCost = new Label("COST");
			lblCost.setStyleName("warbandColumn");
			unitCost.add(lblCost);
			
			unitNumber = new VerticalPanel();
			Label lblNumber = new Label("NUMBER");
			lblNumber.setStyleName("warbandColumn");
			unitNumber.add(lblNumber);
			
			unitNoise = new VerticalPanel();
			Label lblTotalCost = new Label("NOISE");
			lblTotalCost.setStyleName("warbandColumn");
			unitNoise.add(lblTotalCost);
		}
	}

	private class AddUnitClickHandler implements ClickHandler{
		private Window_ConstructWarband warbandWindow;
		
		public AddUnitClickHandler(Window_ConstructWarband warbandWindow){
			this.warbandWindow = warbandWindow;
		}
		@Override
		public void onClick(ClickEvent event) {
			new Window_ChooseUnit(warbandWindow);
		}
		
	}
	
	private class AddHeroClickHandler implements ClickHandler{
		private Window_ConstructWarband warbandWindow;
		
		public AddHeroClickHandler(Window_ConstructWarband warbandWindow){
			this.warbandWindow = warbandWindow;
		}
		@Override
		public void onClick(ClickEvent event) {
			new Window_ChooseHero(warbandWindow);
		}
		
	}
	
	public void updateTotals(){
		lblTotalCost.setText(chart.totalCost + "");
		lblTotalNoise.setText(chart.totalNoise + "");
	}
	
	private class NoGoodClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			int num = 0;
			
			for(Hero hero: warband.getHeroes()){
				num += hero.getCost();
			}
			
			for(L_Unit unit: warband.getUnits()){
				num += unit.getTotalCost();
			}
			
			allianceWindow.alliance.removePoints(num);
		}
		
	}
}
