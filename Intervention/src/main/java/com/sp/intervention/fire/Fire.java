package com.sp.intervention.fire;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.cst.FireType;

import antlr.collections.List;

@Entity
public class Fire {
	@Id
	private Integer id;
	private FireType type;
	private float intensity;
	private float range;
	private double lon;
	private double lat;
	
	private String resolvers;
	
	public Fire() {
	}
	
	public Fire(Integer id , FireType type , float intensity , float range , double lon , double lat , String resolver) {
		super();
		this.id = id;
		this.type = type;
		this.intensity = intensity;
		this.range = range;
		this.lon = lon;
		this.lat = lat;
		if(resolver == null ) this.resolvers = new String();
		else this.resolvers = resolver;
	}
	
	public ArrayList<Integer> getResolvers() {
		if(this.resolvers != null &&  !this.resolvers.trim().isEmpty()) {
			String[] strArray = resolvers.split(",");
			ArrayList<Integer> intArray = new ArrayList<Integer>();		
			for(int i = 0; i < strArray.length; i++) {
				intArray.add(Integer.parseInt(strArray[i]));
			}
			return intArray;
		}
		return new ArrayList<Integer>();
	}

	public void setResolvers(Integer resolver) {
		this.resolvers += resolver + ",";
	}
	
	public FireType getType() {
		return type;
	}

	public void setType(FireType type) {
		this.type = type;
	}

	public float getIntensity() {
		return intensity;
	}

	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}

	public float getRange() {
		return range;
	}

	public void setRange(float range) {
		this.range = range;
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

	public Integer getId() {
		return id;
	}
	@Override
	public String toString() {
		return "Fire [id=" + id + "]";
	}
	public void setId(Integer id) {
		this.id = id;
	}


}
