package com.gmail.pmeznar.lotr.client;

import com.google.gwt.core.client.EntryPoint;

public class Main implements EntryPoint {

	public void onModuleLoad() {
		Resources.INSTANCE.css().ensureInjected();
		LotrProxy.isLoggedIn();
	}
}
