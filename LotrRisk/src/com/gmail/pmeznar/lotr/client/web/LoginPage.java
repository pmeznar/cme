package com.gmail.pmeznar.lotr.client.web;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LoginPage extends Composite {
	@UiField TextBox txtUsername;
	@UiField PasswordTextBox txtPassword;
	@UiField Button btnLogin;
	
	private static LoginPageUiBinder uiBinder = GWT
			.create(LoginPageUiBinder.class);

	interface LoginPageUiBinder extends UiBinder<Widget, LoginPage> {
	}

	public LoginPage() {
		initWidget(uiBinder.createAndBindUi(this));
		
		btnLogin.addClickHandler(new loginClickHandler(txtUsername, txtPassword));
	}
	
	private class loginClickHandler implements ClickHandler{
		TextBox name;
		PasswordTextBox password;
		
		public loginClickHandler(TextBox name, PasswordTextBox password){
			this.name = name;
			this.password = password;
		}
		@Override
		public void onClick(ClickEvent event) {
			LotrProxy.login(name.getText(), password.getText());
		}
	}
}
