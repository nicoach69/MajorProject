package com.sp.helper;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

import com.sp.helper.vehicle.Vehicle;

public class HelperReturnStationStrategyNearest implements IHelperReturnStationStrategy{
	
	private ArrayList<Point2D> stations_pos;
	
	public HelperReturnStationStrategyNearest() {
		this.stations_pos = new ArrayList<Point2D>();
		this.stations_pos.add(new Point2D.Double(45.747389,4.828066));
		this.stations_pos.add(new Point2D.Double(45.77900662392688,4.8782091997044645));
		this.stations_pos.add(new Point2D.Double(45.76074861168169,4.924973158699562));
		this.stations_pos.add(new Point2D.Double(45.7872386,4.7137926));
	}
	
	@Override
	public HashMap<Integer, Point2D> applyStrategy(ArrayList<Vehicle> lVehicles) {
		HashMap<Integer, Point2D> returnMap = new HashMap<Integer, Point2D>();
		lVehicles.forEach(item -> returnMap.put(item.getId() , nearestStation(item)));
		return returnMap;
	}
	
	public Point2D nearestStation(Vehicle v) {
		Point2D neareset = null;
		double min_distance = Double.POSITIVE_INFINITY;
		for (Point2D it : stations_pos) {
			double distance = getDist(it.getX(), it.getY(), v.getLon(), v.getLat());
			if(distance < min_distance) {
				min_distance = distance;
				neareset = it;
			}
		}
		return neareset;
	}
	
	public double getDist(double x1 , double y1 , double x2 , double y2) {
		return Math.sqrt( Math.pow(x1-x2 , 2) + Math.pow(y1-y2 , 2) );
	}

}