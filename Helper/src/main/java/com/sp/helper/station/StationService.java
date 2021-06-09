package com.sp.helper.station;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationService {
	@Autowired
	StationRepository sRepository;
	
	public void addStation(Station v) {
		sRepository.save(v);
	}

	public Station getStation(int id) {
		Optional<Station> vOpt = sRepository.findById(id);
		return vOpt.isPresent() ? vOpt.get() :  null;
	}
	
	public Iterable<Station> getAll() {
		return sRepository.findAll();
	}
	
	public String getInfoStation(int id) {
		Optional<Station> sOpt = sRepository.findById(id);
		return sOpt.isPresent() ? sOpt.get().toString() :  null;
	}
}
