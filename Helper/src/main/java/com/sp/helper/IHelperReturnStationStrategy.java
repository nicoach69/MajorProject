package com.sp.helper;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

import com.sp.helper.vehicle.Vehicle;

public interface IHelperReturnStationStrategy {

	HashMap<Integer, Point2D> applyStrategy(ArrayList<Vehicle> lVehicles);
}