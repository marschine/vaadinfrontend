package de.marrrschine.frontend;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ServiceConsumer {

	public JSONArray consumeServiceGet() throws ParseException {
		Client client = Client.create();

		WebResource webResource = client.resource("http://marrrschine.de:8080/rest/hello/live");
		ClientResponse response = webResource.accept("application/json")
                .type("application/json").get(ClientResponse.class);
		String s = response.getEntity(String.class);
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(s);
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray jsonArray = (JSONArray) jsonObject.get("prospectList");
////		JSONObject jsonObject = (JSONObject) obj;
//		JSONParser parser = new JSONParser();
//		Object jsonObject = parser.parse(obj);
//		JSONArray jsonArray = (JSONArray) jsonObject.get("prospectList");

		return jsonArray;
	}
}