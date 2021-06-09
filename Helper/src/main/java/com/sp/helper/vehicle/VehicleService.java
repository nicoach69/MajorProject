package com.sp.helper.vehicle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {
	@Autowired
	VehicleRepository vRepository;
	
	public void addVehicle(Vehicle v) {
		vRepository.save(v);
	}

	public Vehicle getVehicle(int id) {
		Optional<Vehicle> vOpt = vRepository.findById(id);
		return vOpt.isPresent() ? vOpt.get() :  null;
	}
	
	public Iterable<Vehicle> getAll() {
		return vRepository.findAll();
	}
	
	public String getInfoVehicle(int id) {
		Optional<Vehicle> vOpt = vRepository.findById(id);
		return vOpt.isPresent() ? vOpt.get().toString() :  null;
	}
	
	public boolean changeStateOccuped(int id) {
		Optional<Vehicle> vOpt = vRepository.findById(id);
		if(vOpt.isPresent()) {
			vOpt.get().setOccuped(!vOpt.get().isOccuped());
			vRepository.save(vOpt.get());
			return true;
		}
		return false;
	}
}
