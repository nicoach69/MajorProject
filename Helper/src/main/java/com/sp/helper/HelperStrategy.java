package com.sp.helper;

import java.util.ArrayList;

import com.lib.FireModel;
import com.sp.helper.vehicle.Vehicle;

public class HelperStrategy implements IHelperStrategy {

	public HelperStrategy(){
	}
	
	//Only coord calcul
	public Vehicle applyStrategy(ArrayList<Vehicle> lVehicle , FireModel f) {
		if(lVehicle.size() == 0) return null;
		
		int beginIndex = 0;
		Vehicle bestChoice = null;
		for(;lVehicle.get(beginIndex).isOccuped()&& beginIndex<lVehicle.size()-1;beginIndex++ ) {}
		if(lVehicle.get(beginIndex).isOccuped()) return null;
		else  bestChoice = lVehicle.get(beginIndex);
		for (int i=beginIndex+1 ; i < lVehicle.size() ; i++) {
			if (!lVehicle.get(i).isOccuped()) {
				bestChoice = _nearestVehicle( lVehicle.get(i) , bestChoice , f);
			}
		}
		return bestChoice;
	}

	
	private Vehicle _nearestVehicle(Vehicle v1 , Vehicle v2 , FireModel f) {
		double v1x = v1.getLat();
		double v2x = v2.getLat();
		double v1y = v1.getLon();
		double v2y = v2.getLon();
		double fx = f.getLat();
		double fy = f.getLon();
		if ( getDist(v1x , v1y , fx , fy) < getDist(v2x , v2y , fx , fy) ) {return v1;}
		else {return v2;}
	}

}
