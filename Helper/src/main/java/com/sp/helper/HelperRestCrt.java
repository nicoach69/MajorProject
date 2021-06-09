package com.sp.helper;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lib.FireModel;
import com.lib.VehicleModel;
import com.sp.helper.station.StationService;
import com.sp.helper.vehicle.Vehicle;

@RestController
public class HelperRestCrt {
	@Autowired
	HelperService hService;
	@Autowired
	StationService sService;
	@Autowired
	IHelperStrategy strategy;
	


	@RequestMapping(method=RequestMethod.PUT, value = "/Helper/Vehicle")
	public VehicleModel addVehicle(@RequestBody VehicleModel vehicleM) {
		Vehicle vehicle = mapperVehicle(vehicleM);
		hService.addVehicle(vehicle);
		//System.out.println(vehicle);
		return vehicleM;
	}

	@RequestMapping(method=RequestMethod.POST, value = "/Helper/End")
	public void endOfHelp(@RequestBody ArrayList<Integer> finished) {
		finished.forEach(id -> hService.changeintervening(id));
		
		/*ArrayList<Vehicle> finishedListVehicles = new ArrayList<Vehicle>();
		finished.forEach(i -> finishedListVehicles.add(hService.getVehicle(i)));
		HashMap<Integer, Point2D> result = returnStrategy.applyStrategy(finishedListVehicles);
		System.out.println(result);
		return result;*/
	}

	@RequestMapping(method=RequestMethod.PUT, value = "/Helper/Vehicles")
	public void addVehicules(@RequestBody ArrayList<VehicleModel> vehicles) {
		for (VehicleModel v : vehicles) {
			hService.addVehicle(mapperVehicle(v));
		}
	}

	@RequestMapping(method=RequestMethod.POST, value = "/Helper/HelpIfPossible")
	public HashMap<Integer,ArrayList<Integer>> addHelpIfPossible(@RequestBody ArrayList<com.lib.FireModel> currentFires) {
		System.out.println(currentFires);
		HashMap<Integer, ArrayList<Integer>> returnMap =new HashMap<Integer, ArrayList<Integer>>();
		for (FireModel fire : currentFires) {
			ArrayList<Integer> listVehicles = new ArrayList<Integer>();
			Vehicle vehicle = strategy.applyStrategy((ArrayList<Vehicle>) hService.getAllVehicle() , fire);
			if(vehicle != null) {
				hService.changeintervening(vehicle.getId());
				listVehicles.add(vehicle.getId());
			}
			returnMap.put(fire.getId(), listVehicles);
		}
		return returnMap;
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/Helper/Vehicles")
	public Iterable<Vehicle> getAllVehicles() {
		return hService.getAllVehicle();
	}


	@RequestMapping(method=RequestMethod.GET, value = "/helper/vehicle/{id}")
	public VehicleModel getVehicleById(@PathVariable Integer id) {
		Vehicle vehicle = hService.getVehicle(id);
		VehicleModel vehicleM = unmapperVehicle(vehicle);
		return vehicleM;
	}
	@RequestMapping(method=RequestMethod.GET, value = "/helper/vehicle/all")
	public ArrayList<VehicleModel> getAllVehicleModel() {
		ArrayList<VehicleModel> lVehicleM = new ArrayList<VehicleModel>();
		ArrayList<Vehicle> lVehicle = (ArrayList<Vehicle>) hService.getAllVehicle();
		for (Vehicle vehicle : lVehicle) {
			lVehicleM.add(unmapperVehicle(vehicle));
		}
		return lVehicleM;
	}
	public ArrayList<Vehicle> getAllVehicle() {
		return (ArrayList<Vehicle>) hService.getAllVehicle();
	}
	@RequestMapping(method=RequestMethod.GET, value = "/helper/vehicle")
	public VehicleModel mobilize(@RequestBody FireModel fire) {
		ArrayList<Vehicle> lVehicle = this.getAllVehicle();
		Vehicle vehicle = strategy.applyStrategy(lVehicle , fire);
		vehicle.setOccuped(true);
		return unmapperVehicle(vehicle);
	}





	public Vehicle mapperVehicle(VehicleModel vM) {
		Vehicle v = new Vehicle(vM.getId() , vM.getLon() , vM.getLat() , vM.getType() , vM.getEfficiency() , vM.getLiquidType() , 
				vM.getLiquidQuantity() , vM.getFuelConsumption() , vM.getFuel() , vM.getFuelConsumption() , vM.getCrewMember() , vM.getCrewMemberCapacity()
				, vM.getFacilityRefID() , false);
		return v;
	}
	public VehicleModel unmapperVehicle(Vehicle v) {
		VehicleModel vM = new VehicleModel(v.getId() , v.getLon() , v.getLat() , v.getType() , v.getEfficiency() , v.getLiquidType() , 
				v.getLiquidQuantity() , v.getFuelConsumption() , v.getFuel() , v.getFuelConsumption() , v.getCrewMember() , v.getCrewMemberCapacity()
				, v.getFacilityRefID());
		return vM;
	}



	/*
	@RequestMapping(method=RequestMethod.PUT, value = "/helper/station")
	public StationModel addStation(@RequestBody StationModel stationM) {
		Station station = mapperStation(stationM);
		sService.addStation(station);
		return stationM;
	}
	@RequestMapping(method=RequestMethod.GET, value = "/helper/station/{id}")
	public StationModel getStationById(@PathVariable Integer id) {
		Station station = sService.getStation(id);
		StationModel stationM = unmapperStation(station);
		return stationM;
	}
	@RequestMapping(method=RequestMethod.GET, value = "/helper/station/all")
	public ArrayList<StationModel> getAllStationModel() {
		ArrayList<StationModel> lStationM = new ArrayList<StationModel>();
		ArrayList<Station> lStation= (ArrayList<Station>) sService.getAllStation();
		for (Station station : lStation) {
			lStationM.add(unmapperStation(station));
		}
		return lStationM;
	}
	public ArrayList<Station> getAllStation() {
		return (ArrayList<Station>) sService.getAllStation();
	}
	 */
}