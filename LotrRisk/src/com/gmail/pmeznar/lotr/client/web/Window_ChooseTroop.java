package com.gmail.pmeznar.lotr.client.web;

import com.gmail.pmeznar.lotr.client.model.Troop;
import com.gmail.pmeznar.lotr.client.widgets.CloseButton;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sun.java.swing.plaf.windows.resources.windows;

public class Window_ChooseTroop extends DialogBox{
	private Window_ConstructWarband parentWindow;
	private VerticalPanel pnlBase = new VerticalPanel();
	private TextBox txtName, txtCost, txtNoise, txtNumber;
	private Label lblTotalCost;
	private ListBox lstTypes;
	private CloseButton closeButton = new CloseButton(this);
	
	public Window_ChooseTroop(Window_ConstructWarband window){
		this.parentWindow = window;
		this.add(pnlBase);
		
		txtName = new TextBox();
		txtCost = new TextBox();
		txtCost.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				calcTotalCost(txtCost);	
			}
		});
		txtNoise = new TextBox();
		txtNumber = new TextBox();
		txtNumber.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				calcTotalCost(txtNumber);
			}
		});
		lblTotalCost = new Label();
		
		lstTypes = new ListBox();
		setupWindow();
	}
	
	private void setupWindow(){
		pnlBase.add(new Label("Add Troop"));
		
		HorizontalPanel pnlLoad = new HorizontalPanel();
		pnlLoad.getElement().getStyle().setPaddingBottom(3.0, Unit.PX);
		VerticalPanel vpnlLabels = new VerticalPanel();
		VerticalPanel vpnlType = new VerticalPanel();
		VerticalPanel vpnlCost = new VerticalPanel();
		vpnlCost.setStyleName("cheapAlign");
		VerticalPanel vpnlNoise = new VerticalPanel();
		vpnlNoise.setStyleName("cheapAlign");
		
		pnlLoad.add(vpnlLabels);
		pnlLoad.add(vpnlType);
		pnlLoad.add(vpnlCost);
		pnlLoad.add(vpnlNoise);
		
		vpnlLabels.add(new Label("Load:"));
		vpnlLabels.add(new Label("Create:"));
		vpnlLabels.setStyleName("chooseTroopLabel");
		
		vpnlType.add(lstTypes);
		vpnlType.add(txtName);
		
		vpnlCost.add(new Label("Cost:"));
		vpnlCost.add(txtCost);
		
		vpnlNoise.add(new Label("Noise:"));
		vpnlNoise.add(txtNoise);
		
		HorizontalPanel pnlTotals = new HorizontalPanel();
		Label lblMany = new Label("How Many:");
		lblMany.setStyleName("chooseTroopLabel");
		pnlTotals.add(lblMany);
		pnlTotals.add(txtNumber);
		pnlTotals.add(new Label("Total Cost:"));
		pnlTotals.add(lblTotalCost);
		
		HorizontalPanel pnlButtons = new HorizontalPanel();
		Button btnSet = new Button("Set");
		btnSet.addClickHandler(new SetClickHandler());
		pnlButtons.add(btnSet);
		pnlButtons.add(closeButton);
		
		pnlBase.add(pnlLoad);
		pnlBase.add(pnlTotals);
		pnlBase.add(pnlButtons);
		
		this.center();
	}
	
	// Calculate and verifies total cost
	private void calcTotalCost(TextBox box){
		int cost, num;
		
		// Only verify when both have something in them
		if(txtCost.getText().equals("") || txtNumber.getText().equals("")){
			return;
		}
		
		try{
			cost = Integer.parseInt(txtCost.getText());
			num = Integer.parseInt(txtNumber.getText());
		} catch (NumberFormatException e){
			Window.alert("Please make sure entries in Cost and How Many fields" +
					" are valid numbers");
			box.setFocus(true);
			return;
		}
		
		int totalCost = cost * num;
		lblTotalCost.setText(totalCost + "");
	}
	
	public boolean verify(){
		// Make sure they added something everywhere
		if(txtName.getText().equals("") || txtCost.getText().equals("") ||
				txtNoise.getText().equals("") || txtNumber.getText().equals("")){
			Window.alert("Please fill in all fields");
			return false;
		}
		
		// Verify number and cost fields
		calcTotalCost(txtCost);

		// Verify that the noise value is a number
		try{
			Integer.parseInt(txtNoise.getText());
		} catch (NumberFormatException e){
			Window.alert("Please enter a number in the noise field");
			return false;
		}
		
		// Make sure they aren't re-inventing the wheel
		String typeText = txtName.getText();
		for(int i = 0; i < lstTypes.getItemCount(); i++){
			if(typeText.equals(lstTypes.getItemText(i))){
				Window.alert("Please load box for existing units");
				return false;
			}
		}
		
		// Make sure alliance can hold units
		int totalCost = Integer.parseInt(txtCost.getText()) * Integer.parseInt(txtNumber.getText());
		if(parentWindow.allianceWindow.alliance.getCurrentPoints() + totalCost > 
				parentWindow.allianceWindow.alliance.getPointCapacity()){
			int space = parentWindow.allianceWindow.alliance.getPointCapacity() -
					parentWindow.allianceWindow.alliance.getCurrentPoints();
			Window.alert("Cannot add units - exceeds alliance point limit." +
				"\nAlliance can accept " + space + " points.");
			return false;
		}

		return true;
	}
	
	public boolean addTroops(){
		String name = txtName.getText();
		int cost = Integer.parseInt(txtCost.getText());
		int number = Integer.parseInt(txtNumber.getText());
		int noise = Integer.parseInt(txtNoise.getText());
		
		Label lblName = new Label(name);
		lblName.setStyleName("floatRight");
		Label lblCost = new Label(cost + "");
		lblCost.setStyleName("floatRight");
		Label lblNumber = new Label(number + "");
		lblNumber.setStyleName("floatRight");
		Label lblNoise = new Label(noise + "");
		lblNoise.setStyleName("floatRight");
		
		final int temporaryFakeId = 0;
		Troop unit = new Troop(name, cost, noise, /* TODO moveRate */ 0, true, temporaryFakeId, number);
		if(parentWindow.warband.addTroop(unit)){
			parentWindow.chart.unitType.add(lblName);
			parentWindow.chart.unitCost.add(lblCost);
			parentWindow.chart.unitNumber.add(lblNumber);
			parentWindow.chart.unitNoise.add(lblNoise);
			
			parentWindow.chart.totalCost += (number * cost);
			parentWindow.chart.totalNoise += (number * noise);
			parentWindow.updateTotals();
			
			parentWindow.allianceWindow.alliance.addPoints(number * cost);
			
			return true;
		}
		
		return false;
	}
	
	private class SetClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			if(verify()){
				if(addTroops()){
					closeButton.click();
				}
			}
		}
		
	}
}
