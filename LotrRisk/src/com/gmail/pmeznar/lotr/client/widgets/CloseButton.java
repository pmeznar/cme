package com.gmail.pmeznar.lotr.client.widgets;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;

public class CloseButton extends Button {
	public CloseButton(DialogBox box){
		super("Close");
		this.addClickHandler(new CloseBoxClick(box));
	}
}
