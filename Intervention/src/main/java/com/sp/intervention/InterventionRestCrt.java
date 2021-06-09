package com.sp.intervention;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lib.FireModel;
import com.sp.intervention.fire.Fire;
import com.sp.intervention.fire.Mapper;

@RestController
public class InterventionRestCrt {
	@Autowired
	InterventionService iService;

	// FONCTIONS METIER
	
	@RequestMapping(method=RequestMethod.POST, value = "/Intervention/Fires")
	public ArrayList<Integer> updateFires(@RequestBody ArrayList<FireModel> fireM) {
		ArrayList<Fire> oldFires = new ArrayList<Fire>(iService.getAllFire());
		ArrayList<Integer> returnValues = new ArrayList<Integer>();
		// On recupere tous les feux qui ont été éteints
		for (FireModel newIt : fireM) {
			for (int i = 0; i <  oldFires.size(); i++) {
				if(newIt.getId() == oldFires.get(i).getId())
					oldFires.remove(i);
			}
		}
		// On recupere l'id des helpers qui s'occupaient du feu et qui sont maintenant libres
		for (Fire it : oldFires) {	
			iService.removeFire(it.getId());
			if (it.getResolvers().size() != 0)
				it.getResolvers().forEach(item -> returnValues.add(item));
		}
		// On remplace les anciens feux par les nouveaux
		Mapper.mapperFires(fireM).forEach(i -> iService.addFire(i));
		//iService.addFires(Mapper.mapperFires(fireM));
		return returnValues;
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/Intervention/Resolvers")
	public Map<Integer, FireModel> addResolvers(@RequestBody Map<Integer, ArrayList<Integer>> mapFireResolver) {
		// On ajoute les resolvers pour chaque feu
		for (Map.Entry<Integer, ArrayList<Integer>> entry : mapFireResolver.entrySet()) {
			entry.getValue().forEach(resolver -> iService.addResolver(entry.getKey(), resolver));
		}
		// On retourne les feux actualisés sous forme de Map<IDResolver,objet feu associé>)
		ArrayList<Fire> allFires = (ArrayList<Fire>) iService.getAllFire();
		Map<Integer, FireModel> returnMap = new HashMap<Integer, FireModel>();
		for (Fire it : allFires) {
			it.getResolvers().forEach(item -> returnMap.put(item, Mapper.unmapperFire(it)));
		}
		System.out.print(returnMap);
		return returnMap;
	}
	
	
	@RequestMapping(method=RequestMethod.GET, value = "/Intervention/FiresWithoutHelpers")
	public ArrayList<FireModel> getFiresWithoutHelpers() {
		ArrayList<Fire> fires = new ArrayList<Fire>();
		// Pour chaque feu qui n'a pas d'helpers, on l'ajoute à la liste de retour
		for (Fire fire : (ArrayList<Fire>) iService.getAllFire()) {
			if(fire.getResolvers().size() == 0) fires.add(fire);
		}
		return Mapper.unmapperFires(fires);
	}
	
	
	// FONCTIONS TEST
	
	@RequestMapping(method=RequestMethod.PUT, value = "/Intervention/Fire")
	public FireModel addFire(@RequestBody FireModel fireM) {
		iService.addFire(Mapper.mapperFire(fireM));
		return fireM;
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/Intervention/Fire/{id}")
	public FireModel getById(@PathVariable Integer id) {
		Fire fire = iService.getFire(id);
		FireModel fireM = Mapper.unmapperFire(fire);
		return fireM;
	}
	@RequestMapping(method=RequestMethod.GET, value = "/Intervention/Fires")
	public ArrayList<FireModel> getAllFireModel() {
		ArrayList<FireModel> lFireM = new ArrayList<FireModel>();
		ArrayList<Fire> lFire = (ArrayList<Fire>) iService.getAllFire();
		for (Fire fire : lFire) {
			lFireM.add(Mapper.unmapperFire(fire));
		}
		return lFireM;
	}
	@RequestMapping(method=RequestMethod.GET, value = "/Intervention/Fires2")
	public ArrayList<Fire> getAllFire() {
		return (ArrayList<Fire>) iService.getAllFire();
	}
	

}