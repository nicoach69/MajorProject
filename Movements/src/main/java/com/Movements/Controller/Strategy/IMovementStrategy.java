package com.Movements.Controller.Strategy;

import java.io.IOException;
import java.util.ArrayList;

import com.Movements.Model.Movement;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface IMovementStrategy {
	public Movement calculMovement(Movement mouvement) throws JsonParseException, JsonMappingException, IOException;
	
	default public ArrayList<Movement> calculMovement(ArrayList<Movement> mouvements) {
		ArrayList<Movement> returnList = new ArrayList<Movement>();
		mouvements.forEach(it -> {
			try {
				returnList.add(this.calculMovement(it));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		return returnList;
	}
}