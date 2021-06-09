package com.sp.intervention;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.intervention.fire.Fire;
import com.sp.intervention.fire.FireService;

@Service
public class InterventionService {
	@Autowired
	FireService fService;
	
	public void addFire(Fire f) {
		fService.addFire(f);
	}
	
	public void updateFires(ArrayList<Fire> fires) {
		for (Fire f : fires) {
			//fService.getFire(f.getId()).getResolvers().forEach(x -> f.setResolvers(x));
			fService.addFire(f);
		}
		fires.forEach(f -> addFire(f));
	}

	public Fire getFire(int id) {
		return fService.getFire(id);
	}
	
	public ArrayList<Fire> getAllFire() {
		return fService.getAll();
	}
	
	public boolean removeFire(int id) {
		return fService.removeFire(id);
	}
	
	public boolean addResolver(int fireID, int resolverID) {
		return fService.addResolver(fireID, resolverID);
	}
}