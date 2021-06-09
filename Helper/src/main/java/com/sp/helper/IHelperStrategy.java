package com.sp.helper;

import java.util.ArrayList;

import com.lib.FireModel;
import com.sp.helper.vehicle.Vehicle;

public interface IHelperStrategy {

	Vehicle applyStrategy(ArrayList<Vehicle> lVehicle, FireModel f);
	
	public default double getDist(double x1 , double y1 , double x2 , double y2) {
		return Math.sqrt( Math.pow(x1-x2 , 2) + Math.pow(y1-y2 , 2) );
	}

}
