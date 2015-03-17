package com.gmail.pmeznar.lotr.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DialogBox;

public class CloseBoxClick implements ClickHandler {
	DialogBox box;
		
	public CloseBoxClick(DialogBox box){
		this.box = box;
	}

	@Override
	public void onClick(ClickEvent event) {
			box.hide(true);
	}		

}
