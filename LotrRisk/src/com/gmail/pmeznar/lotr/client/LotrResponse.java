package com.gmail.pmeznar.lotr.client;

import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class LotrResponse {
	public final static int ERROR = 0;
	public final static int SUCCESS = 1;
	public final static int WARNING = 2;
	
	int status;
	String message;
	String[] messageArray;
	
	public LotrResponse(Response response){
		JSONValue vals = JSONParser.parseStrict(response.getText());
		JSONObject obj = vals.isObject();
		if(obj != null){
			JSONNumber stat   = obj.get("stat").isNumber();
			JSONString string = obj.get("msg").isString();
			JSONArray  array  = obj.get("msg").isArray();

			// JSON Number
			if (stat != null)
				this.status = Integer.parseInt(stat.toString());
			// JSON String
			if (string != null) {
				String msg = string.toString();
				msg = msg.trim();
				// Remove the quotations at beginning and end of string.
				this.message = msg.substring(1, msg.length() - 1);
			}
			// JSON Array
			if (array != null) {
				messageArray = new String[array.size()];
				for (int i = 0; i < array.size(); i++) {
					String msg = ((JSONValue) array.get(i)).toString();
					messageArray[i] = msg.substring(1, msg.length() - 1);// Remove quotes
				}
				
				//Set message to concatenated contents of array
				for (String msg:messageArray){
					if(msg.length() > 0)
						this.message += msg + " | ";
				}
				
				this.message = this.message.substring(0, message.length()-3); //remove last " | "
			}
		}
	}
}
