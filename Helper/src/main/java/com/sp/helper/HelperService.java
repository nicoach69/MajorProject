package com.sp.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.helper.station.Station;
import com.sp.helper.station.StationService;
import com.sp.helper.vehicle.Vehicle;
import com.sp.helper.vehicle.VehicleService;

@Service
public class HelperService { 
	@Autowired
	VehicleService vService;
	@Autowired
	StationService sService;
	
	public void addVehicle(Vehicle v) {
		vService.addVehicle(v);
	}
	public Vehicle getVehicle(int id) {
		return vService.getVehicle(id);
	}
	public String getInfoVehicle(int id) {
		return vService.getInfoVehicle(id);
	}
	public Iterable<Vehicle> getAllVehicle() {
		return vService.getAll();
	}
	
	public boolean changeintervening(int id) {
		return vService.changeStateOccuped(id);
	}
	
	
	public void addStation(Station s) {
		sService.addStation(s);
	}
	public Station getStation(int id) {
		return sService.getStation(id);
	}
	public String getInfoStation(int id) {
		return sService.getInfoStation(id);
	}
	public Iterable<Station> getAllStation() {
		return sService.getAll();
	}
}