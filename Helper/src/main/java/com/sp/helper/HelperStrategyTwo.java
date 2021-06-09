package com.sp.helper;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.lib.FireModel;
import com.sp.helper.vehicle.Vehicle;

public class HelperStrategyTwo implements IHelperStrategy {
	@Autowired
	IHelperStrategy strategyOne;


	public HelperStrategyTwo(){
	}
	
	
	public Vehicle applyStrategy(ArrayList<Vehicle> lVehicle , FireModel f) {
		if(lVehicle.size() == 0) return null;
		Vehicle nearest = strategyOne.applyStrategy(lVehicle , f);
		double seuil = 0.1;//if vehicle is more specialized and if it's only seuil + far
		Vehicle best = nearest;
		for (Vehicle vehicle : lVehicle) {
			if (! vehicle.isOccuped() 
					&& moreAdapted(best , vehicle , f) == vehicle 
					&& getDist(vehicle.getLon(),vehicle.getLat(),f.getLon(),f.getLat()) - getDist(best.getLon(),best.getLat(),f.getLon(),f.getLat()) < seuil) {
				best = vehicle;
				System.out.println(best);
			}
		}
		return best;
	}

	private Vehicle moreAdapted(Vehicle v1 , Vehicle v2 , FireModel f) {
		if( v1.getLiquidType().getEfficiency(f.getType().toString()) > v2.getLiquidType().getEfficiency(f.getType().toString())) {return v1;}
		else if( v1.getLiquidType().getEfficiency(f.getType().toString()) < v2.getLiquidType().getEfficiency(f.getType().toString())) {return v2;}
		return null;
	}

}
