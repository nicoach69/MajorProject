package com.sp.intervention.fire;

import java.util.ArrayList;

import com.lib.FireModel;

public final class Mapper {
	static public final Fire mapperFire(FireModel fM) {
		Fire f = new Fire(fM.getId() , fM.getType(),  fM.getIntensity() , fM.getRange() , fM.getLon() , fM.getLat() , null);
		return f;
	}
	static public final ArrayList<Fire> mapperFires(ArrayList<FireModel> fM) {
		ArrayList<Fire> fireList = new ArrayList<Fire>();
		fM.forEach(f -> fireList.add(mapperFire(f)));
		return fireList;
	}
	static public final ArrayList<FireModel> unmapperFires(ArrayList<Fire> fM) {
		ArrayList<FireModel> fireList = new ArrayList<FireModel>();
		fM.forEach(f -> fireList.add(unmapperFire(f)));
		return fireList;
	}
	static public final FireModel unmapperFire(Fire f) {
		FireModel fM = new FireModel(f.getId() , f.getType(),  f.getIntensity() , f.getRange() , f.getLon() , f.getLat());
		return fM;
	}
}
