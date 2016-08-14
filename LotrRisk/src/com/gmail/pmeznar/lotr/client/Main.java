package com.gmail.pmeznar.lotr.client;

import com.gmail.pmeznar.lotr.client.web.LotrProxy;
import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point to the application.
 * @author pmeznar
 */
public class Main implements EntryPoint {

	public void onModuleLoad() {
		Resources.INSTANCE.css().ensureInjected();
		LotrProxy.isLoggedIn();
	}
}
