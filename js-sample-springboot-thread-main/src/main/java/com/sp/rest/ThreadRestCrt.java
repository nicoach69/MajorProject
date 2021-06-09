package com.sp.rest;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.sp.service.ThreadService;

@RestController
public class ThreadRestCrt {
	@Autowired
	ThreadService tService;
	private RestTemplate rTemplate;
	private String URL_SIMULATOR = "http://localhost:8081";
	private String URL_GATEWAY = "http://localhost:8080";
	
	ThreadRestCrt() {
		this.rTemplate = new RestTemplate();
	}
	
	public void getFire() throws RestClientException, URISyntaxException {
		ResponseEntity<String> responseS = rTemplate.getForEntity(URL_SIMULATOR + "/fire", String.class);
		System.out.println(responseS.getBody());
		rTemplate.postForEntity(new URI(URL_GATEWAY + "/Manager/Fires"), responseS.getBody(), String.class);
	}
	
	
	public void getVehicle() throws RestClientException, URISyntaxException {
		ResponseEntity<String> responseS = rTemplate.getForEntity(URL_SIMULATOR + "/vehicle", String.class);
		System.out.println(responseS.getBody());
		rTemplate.postForEntity(new URI(URL_GATEWAY + "/Manager/Vehicles"), responseS.getBody(), String.class);
	}
	
	public void calcul() {
		rTemplate.put(URL_GATEWAY + "/Manager/Calcul", null);
	}
	
	
	

	
	
	@RequestMapping(method=RequestMethod.GET,value="/stopdisplay")
	public void stopDisplay() {
		tService.stopDisplay();
	}
}
