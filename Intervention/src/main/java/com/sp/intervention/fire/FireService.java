package com.sp.intervention.fire;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FireService {
	@Autowired
	FireRepository fRepository;


	public Fire getFire(int id) {
		Optional<Fire> fOpt = fRepository.findById(id);
		return fOpt.isPresent() ? fOpt.get() :  null;
	}

	public ArrayList<Fire> getAll() {
		return (ArrayList<Fire>) fRepository.findAll();
	}

	public void addFire(Fire f) {
		fRepository.save(f);
	}
	
	public boolean removeFire(int id) {
		Optional<Fire> fOpt = fRepository.findById(id);
		if(fOpt.isPresent()) {
			fRepository.delete(fOpt.get());
			return true;
		}
		return false;
	}

	public boolean addResolver(int fireID, int resolverID) {
		Optional<Fire> fOpt = fRepository.findById(fireID);
		if(fOpt.isPresent()) {
			fOpt.get().setResolvers(resolverID);
			fRepository.save(fOpt.get());
			return true;
		}
		return false;	
	}


}