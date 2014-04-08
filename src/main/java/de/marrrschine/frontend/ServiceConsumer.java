package de.marrrschine.frontend;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ServiceConsumer {

	public List<Prospect> consumeServiceGet() throws ParseException {
		Client client = Client.create();

		WebResource webResource = client.resource("http://marrrschine.de:8080/rest/hello/newlive");
		ClientResponse response = webResource.accept("application/json")
                .type("application/json").get(ClientResponse.class);
		String s = response.getEntity(String.class);
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(s);
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray jsonArray = (JSONArray) jsonObject.get("prospectList");
		List<Prospect> prospectList = new ArrayList<Prospect>();
		for (Object object : jsonArray) {
			JSONObject jsonObj = (JSONObject) object;
			Prospect prospect = new Prospect();
			String firstname = (String) jsonObj.get("firstname");
			prospect.setFirstname(firstname);
			String lastname = (String) jsonObj.get("lastname");
			prospect.setLastname(lastname);
			String school = (String) jsonObj.get("school");
			prospect.setSchool(school);
			String weight = (String) (jsonObj.get("weight"));
			prospect.setWeight(weight);
			String classYear = (String) jsonObj.get("classYear");
			prospect.setClassYear(classYear);
			long posRank = (Long) jsonObj.get("posRank");
			prospect.setPosRank((int)posRank);
			String pos = (String) jsonObj.get("pos");
			prospect.setPos(pos);
			long rank = (Long) jsonObj.get("rank");
			prospect.setRank((int)rank);
			String height = (String) jsonObj.get("height");
			prospect.setHeight(height);
			prospectList.add(prospect);
			System.out.println(prospect);
		}
////		JSONObject jsonObject = (JSONObject) obj;
//		JSONParser parser = new JSONParser();
//		Object jsonObject = parser.parse(obj);
//		JSONArray jsonArray = (JSONArray) jsonObject.get("prospectList");
		return prospectList;
	}

	public String consumeOtcService() throws ParseException {
		Client client = Client.create();
		WebResource webResource = client.resource("http://marrrschine.de:8080/rest/hello/otc");
		ClientResponse response = webResource.accept("application/json")
                .type("application/json").get(ClientResponse.class);
		String s = response.getEntity(String.class);
		return s;
	}
	
}