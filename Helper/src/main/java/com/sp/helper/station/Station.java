package com.sp.helper.station;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Station {
	@Id
	private Integer id;
	private double lon;
	private double lat;
	
	Station () {
	}
	Station (Integer id , double lon , double lat) {
		this.id = id;
		this.lon = lon;
		this.lat = lat;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	
}
