package com.Movements.Controller.Strategy;

import java.awt.geom.Point2D;
import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.Movements.Model.Movement;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ItineraryStrategy implements IMovementStrategy {
	static private String MAP_BOX_URI = "https://api.mapbox.com/directions/v5/mapbox/driving/";
	static private String _token = "pk.eyJ1IjoiY2hhcmxlc2NvbCIsImEiOiJja3BuejA4M240amRtMm5ueHFkMTVkOXlrIn0.7uwXqMp_ueXOLF0-Wu_H_g";
	static private String geometries = "geojson";
	static private String OTHER_PARAM_URL= "?geometries="+ geometries +  "&access_token="  + _token;

	@Override
	public Movement calculMovement(Movement mouvement) throws JsonParseException, JsonMappingException, IOException {
		String paramURLDeparture = String.valueOf(mouvement.getCurrentPosX()) + "," + String.valueOf(mouvement.getCurrentPosY()) + ";";
		String paramURLArrival = String.valueOf(mouvement.getObjectivePosX()) + "," + String.valueOf(mouvement.getObjectivePosY());
		ResponseEntity<String> result = new RestTemplate().getForEntity(MAP_BOX_URI + paramURLDeparture + paramURLArrival + OTHER_PARAM_URL, String.class);
		JsonNode jsonNode = new ObjectMapper().readTree(result.getBody());
		JsonNode json = jsonNode.withArray("routes").get(0).get("geometry").withArray("coordinates");
		
		if(json.size() > 3) 
			mouvement.setCurentPos(new Point2D.Double(json.get(1).get(0).asDouble(), json.get(1).get(1).asDouble()));
		else 
			mouvement.setCurentPos(mouvement.getObjectivePos());
		System.out.print(mouvement);
		
		return mouvement;
	}

}