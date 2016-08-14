package com.gmail.pmeznar.lotr.client.web;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;

public abstract class LotrRequest extends RequestBuilder {
	public LotrRequest(String url){
		super(RequestBuilder.GET, LotrProxy.BASE + url);
		send(url);
	}
	
	public LotrRequest(Method httpMethod, String url) {
		super(httpMethod, LotrProxy.BASE + url);
		send(url);
	}
	
	private void send(String url){
		try{
			this.sendRequest(null, new RequestCallback() {
				
				@Override
				public void onResponseReceived(Request request, Response response) {
					Window.alert(response.getStatusText() + "\n" + response.getText());
					receive(new LotrResponse(response));
				}
				
				@Override
				public void onError(Request request, Throwable exception) {
					exception.printStackTrace();						
				}
			});
		} catch (Exception e) {
			Window.alert(e.getMessage() + "");
		}
	}
	
	public abstract void receive(LotrResponse response);
}
