package com.gmail.pmeznar.lotr.client;

import com.gmail.pmeznar.lotr.client.model.Hero;
import com.gmail.pmeznar.lotr.client.widgets.CloseButton;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Window_ChooseHero extends DialogBox{
	Window_ConstructWarband window;
	VerticalPanel pnlBase = new VerticalPanel();
	TextBox txtName = new TextBox();
	TextBox txtMight = new TextBox();
	TextBox txtWill = new TextBox();
	TextBox txtFate = new TextBox();
	TextBox txtWounds = new TextBox();
	TextBox txtNoise = new TextBox();
	TextBox txtCost = new TextBox();
	CloseButton closeButton = new CloseButton(this);
	CheckBox chkLeader = new CheckBox();
	CheckBox chkKing = new CheckBox();
	
	public Window_ChooseHero(Window_ConstructWarband window){
		this.window = window;
		this.add(pnlBase);
		
		initialSetup();
		
		this.center();
	}
	
	public void initialSetup(){
		pnlBase.add(new Label("HERO:"));
		
		HorizontalPanel pnlDetails = new HorizontalPanel();
		VerticalPanel vpnlName = new VerticalPanel();
		VerticalPanel vpnlMight = new VerticalPanel();
		VerticalPanel vpnlWill = new VerticalPanel();
		VerticalPanel vpnlFate = new VerticalPanel();
		VerticalPanel vpnlWounds = new VerticalPanel();
		VerticalPanel vpnlCost = new VerticalPanel();
		VerticalPanel vpnlNoise = new VerticalPanel();
		
		pnlDetails.add(vpnlName);
		pnlDetails.add(vpnlMight);
		pnlDetails.add(vpnlWill);
		pnlDetails.add(vpnlFate);
		pnlDetails.add(vpnlWounds);
		pnlDetails.add(vpnlNoise);
		pnlDetails.add(vpnlCost);
		
		vpnlName.add(new Label("Name"));
		vpnlMight.add(new Label("Might"));
		vpnlWill.add(new Label("Will"));
		vpnlFate.add(new Label("Fate"));
		vpnlWounds.add(new Label("Wounds"));
		vpnlNoise.add(new Label("Noise"));
		vpnlCost.add(new Label("Cost"));
		
		txtMight.setStyleName("heroTxt");
		txtWill.setStyleName("heroTxt");
		txtFate.setStyleName("heroTxt");
		txtWounds.getElement().getStyle().setWidth(50.0, Unit.PX);
		txtNoise.setStyleName("heroTxt");
		txtCost.setStyleName("heroTxt");
		
		vpnlName.add(txtName);
		vpnlMight.add(txtMight);
		vpnlWill.add(txtWill);
		vpnlFate.add(txtFate);
		vpnlWounds.add(txtWounds);
		vpnlNoise.add(txtNoise);
		vpnlCost.add(txtCost);
		
		pnlBase.add(pnlDetails);
		
		HorizontalPanel pnlLeader = new HorizontalPanel();
		pnlLeader.add(new Label("Leader: "));
		pnlLeader.add(chkLeader);
		Label lblKing = new Label("King: ");
		lblKing.setStyleName("bumpRight");
		pnlLeader.add(lblKing);
		pnlLeader.add(chkKing);
		
		pnlBase.add(pnlLeader);
		
		HorizontalPanel pnlButtons = new HorizontalPanel();
		Button btnAddHero = new Button("Add");
		btnAddHero.addClickHandler(new AddHeroClickHandler());
		pnlButtons.add(btnAddHero);
		pnlButtons.add(closeButton);
		
		pnlBase.add(pnlButtons);
	}
	
	private class AddHeroClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			if(verify()){
				closeButton.click();
			}
		}
		
	}
	
	public boolean verify(){
		String name, might, will, fate, wounds, cost, noise;
		name = txtName.getText();
		might = txtMight.getText();
		will = txtWill.getText();
		fate = txtFate.getText();
		wounds = txtWounds.getText();
		cost = txtCost.getText();
		noise = txtNoise.getText();
		
		// Check Empty
		if(	name.equals("")		||
			might.equals("")	||
			will.equals("")		||
			fate.equals("")		||
			wounds.equals("")	||
			cost.equals("")		||
			noise.equals("")){
				Window.alert("Please enter all fields");
				return false;
		}
		
		int m, wi, f, wo, c, n;
		
		// Check Numbers
		try{
			m = Integer.parseInt(might);
			wi = Integer.parseInt(will);
			f = Integer.parseInt(fate);
			wo = Integer.parseInt(wounds);
			c  = Integer.parseInt(cost);
			n = Integer.parseInt(noise);
		} catch (NumberFormatException e){
			Window.alert("Other than name, all fields must be numeric");
			return false;
		}
		
		// Check only one leader per warband
		if(chkLeader.getValue()){
			for(Hero hero: window.warband.getHeroes()){
				if(hero.isLeader()){
					Window.alert("Warband already has leader.\n" + 
							"Either uncheck leader box, or start warband over");
					return false;
				}
			}
		}
		
		// Check only one king per alliance
		if(chkKing.getValue()){
			if(window.allianceWindow.alliance.getKing() != null){
				Window.alert("Alliance already has a king.\n" + 
						"Either uncheck king box, or start alliance over");
			}
		}
		
		if(window.allianceWindow.alliance.getCurrentPoints() + c > 
			window.allianceWindow.alliance.getPointCapacity()){
				int space = window.allianceWindow.alliance.getPointCapacity() -
						window.allianceWindow.alliance.getCurrentPoints();
				Window.alert("Cannot add units - exceeds alliance point limit." +
					"\nAlliance can accept " + space + " points.");
				return false;
		}
		
		// Add Hero to warband
		Hero hero = new Hero(name, c, n, m, wi, f, wo);
		if(window.warband.addHero(hero)){
			if(chkLeader.getValue()) hero.setLeader(true);
			
			// Add hero to chart
			Label lblName = new Label(name);
			Label lblCost = new Label(cost);
			Label lblNum = new Label("1");
			Label lblNoise = new Label(noise);
			
			lblName.setStyleName("floatRight");
			lblCost.setStyleName("floatRight");
			lblNum.setStyleName("floatRight");
			lblNoise.setStyleName("floatRight");
			
			window.chart.unitType.add(lblName);
			window.chart.unitCost.add(lblCost);
			window.chart.unitNumber.add(lblNum);
			window.chart.unitNoise.add(lblNoise);
			
			window.chart.totalCost += c;
			window.chart.totalNoise += n;
			window.updateTotals();
			window.allianceWindow.alliance.addPoints(c);
			
			if(chkKing.getValue()){
				window.allianceWindow.alliance.setKing(hero);
			}
			
			return true;
		}
		return false;
	}

}
