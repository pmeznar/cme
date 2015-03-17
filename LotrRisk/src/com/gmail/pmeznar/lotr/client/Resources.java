package com.gmail.pmeznar.lotr.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface Resources extends ClientBundle {

	public static final Resources INSTANCE = GWT.create(Resources.class);
	
	@Source("lotr.css")
	@CssResource.NotStrict
	CssResource css();
}
