package com.Movements.Controller;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.Movements.Controller.Strategy.IMovementStrategy;
import com.Movements.Model.Movement;
import com.Movements.Service.MovementService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lib.VehicleModel;


@RestController
public class MovementRestController {
    @Autowired
    MovementService service;
    @Autowired
    IMovementStrategy strategy;
    
    private String URL_SIMULATOR = "http://localhost:8081";
    private RestTemplate rTemplate;
    
    MovementRestController() {
    	this.rTemplate = new RestTemplate();
    }
    // FONCTIONS METIER
    
    @RequestMapping(method=RequestMethod.PUT,value="/Movement/Vehicles")
    public void addVehicules(@RequestBody ArrayList<com.lib.VehicleModel> vehicules) {
    	vehicules.forEach(v -> service.addMovement(new Movement(v.getId(), v.getLon(), v.getLat(),v.getLon(), v.getLat())));
    }
    
    @RequestMapping(method=RequestMethod.POST,value="/Movement/Vehicles")
    public void updateVehicules(@RequestBody Map<Integer, com.lib.FireModel> movements) throws JsonParseException, JsonMappingException, IOException {
    	
    	for (Map.Entry<Integer, com.lib.FireModel> movement : movements.entrySet()) {
    		Movement mvmt = service.getMovement(movement.getKey());
    		if(mvmt != null) {
    			if(mvmt.getObjectivePosX() != movement.getValue().getLon() ||
    							mvmt.getCurrentPosY() != movement.getValue().getLat()) {
    				mvmt.setObjectivePos(new Point2D.Double(movement.getValue().getLon(), movement.getValue().getLat()));
    				service.addMovement(mvmt);
    			}
    		}
    	}
    	
    	ArrayList<Movement> listMovement = strategy.calculMovement(service.getAllMovements());
    	for (Movement movement : listMovement) {
			ResponseEntity<String> vehicle = rTemplate.getForEntity(URL_SIMULATOR + "/vehicle/" + movement.getIDHelper()  , String.class);
			System.out.println(vehicle.getBody());
			System.out.println(movement);
			System.out.println(movement.getCurrentPosX());
			System.out.println(movement.getCurrentPosY());
			VehicleModel v = new ObjectMapper().readValue(vehicle.getBody(), VehicleModel.class);
			v.setLon(movement.getCurrentPosX());
			v.setLat(movement.getCurrentPosY());
			System.out.println(v.getLon());
			System.out.println(v.getLat());
			HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<VehicleModel> request = new HttpEntity<>(v,headers);
	        rTemplate.exchange(URL_SIMULATOR + "/vehicle/" + movement.getIDHelper() , HttpMethod.PUT, request, Void.class);
		}
    }

    
    // FONCTIONS TEST
    
    @RequestMapping(method=RequestMethod.GET,value="/Movement/{id}")
    public Movement getMouvement(@PathVariable int id) {
    	return service.getMovement(id);
    }
    
    @RequestMapping(method=RequestMethod.GET,value="/Movements")
    public ArrayList<Movement> getAll() {
    	return service.getAllMovements();
    }
}
