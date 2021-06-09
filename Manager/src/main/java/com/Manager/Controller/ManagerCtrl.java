package com.Manager.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.lib.VehicleModel;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lib.FireModel;

@RestController
public class ManagerCtrl {

	private String URL_MOVEMENT = "http://localhost:8086/Movement";
	private String URL_INTERVENTION = "http://localhost:8084/Intervention";
	private String URL_HELPER = "http://localhost:8085/Helper";
	
	private RestTemplate rTemplate;

	ManagerCtrl() {
		this.rTemplate = new RestTemplate();
	}
	@RequestMapping(method=RequestMethod.POST,value="/Fires")
	public void updateFires(@RequestBody String json_fires) throws URISyntaxException, JsonParseException, JsonMappingException, IOException {
		//Recupere les feu du simulator
		ArrayList<FireModel> fires = new ArrayList<FireModel>(Arrays.asList(new ObjectMapper().readValue(json_fires, FireModel[].class)));
		//Met a jour les feus du repo Intervention, recupere ID Helper a nouveaux disponibles
		ResponseEntity<String> result = rTemplate.postForEntity(new URI(URL_INTERVENTION + "/Fires"), fires, String.class);
		//Conversion ID Helper en list d'entier
		Integer[] returnedValue = new ObjectMapper().readValue(result.getBody(), Integer[].class);
		//if(returnedValue.length != 0) {
			List<Integer> list =  new ArrayList<Integer>(Arrays.asList(returnedValue));
			//envoi des ID Helper disponible au service helper
			rTemplate.postForEntity(new URI(URL_HELPER + "/End"), list, String.class);
			/*@SuppressWarnings("unchecked")
			HashMap<Integer , Point2D> helpersObjectives = new ObjectMapper().readValue(result.getBody(), HashMap.class);
			rTemplate.postForEntity(URL_MOVEMENT + "/VehiclesByPos", helpersObjectives, String.class);*/
			throw new ResponseStatusException(HttpStatus.OK, "OK");
		//}
	}

	@RequestMapping(method=RequestMethod.POST,value="/Vehicles")
	public void updateVehicles(@RequestBody String json_vehicles) throws URISyntaxException, JsonParseException, JsonMappingException, IOException {
		//recupere la liste des vehicle
		ArrayList<VehicleModel> vehicles = new ArrayList<VehicleModel>(Arrays.asList(new ObjectMapper().readValue(json_vehicles, VehicleModel[].class)));
		//envoi liste vehicle a Helper
		rTemplate.put(new URI(URL_HELPER + "/Vehicles"), vehicles);
		//envoi liste vehicle a Movement
		rTemplate.put(new URI(URL_MOVEMENT + "/Vehicles"), vehicles);
	}

	
	@RequestMapping(method=RequestMethod.PUT,value="/Calcul")
	public void startCalcul() throws JsonParseException, JsonMappingException, IOException {
		//Liste des feux à gérer
		ResponseEntity<String> fires = rTemplate.getForEntity(URL_INTERVENTION + "/FiresWithoutHelpers", (String.class));
		//conversion en arraylist
		ArrayList<FireModel> firesList = new ArrayList<FireModel>(Arrays.asList(new ObjectMapper().readValue(fires.getBody(), FireModel[].class)));
		System.out.println(firesList);
		//Recupere les secours disponibles adaptés
		ResponseEntity<String> helpers = rTemplate.postForEntity(URL_HELPER + "/HelpIfPossible", fires, String.class);
		//Conversion en HashMap
		@SuppressWarnings("unchecked")
		HashMap<Integer , ArrayList<Integer>> mapHelpers = new ObjectMapper().readValue(helpers.getBody(), HashMap.class);
		System.out.println(mapHelpers);
		System.out.println(mapHelpers.getClass());
		//Recupere les interventions des feux a gerer, avec helpers associé
		ResponseEntity<String> interventions = rTemplate.postForEntity(URL_INTERVENTION + "/Resolvers", mapHelpers, String.class);
		//conversion en hashMap
		@SuppressWarnings("unchecked")
		HashMap<Integer , FireModel> mapInterventions = new ObjectMapper().readValue(interventions.getBody(), HashMap.class);
		System.out.println(mapInterventions);
		System.out.println(mapInterventions.getClass());
		//envoi a movment de HashMap<IdHelper , IdFeu>
		rTemplate.postForEntity(URL_MOVEMENT + "/Vehicles", mapInterventions, String.class);
	}
}