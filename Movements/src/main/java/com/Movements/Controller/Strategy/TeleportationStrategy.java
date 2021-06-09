package com.Movements.Controller.Strategy;

import java.util.ArrayList;

import com.Movements.Model.Movement;

public class TeleportationStrategy implements IMovementStrategy {
	
	public Movement calculMovement(Movement mouvement) {
		mouvement.setCurentPos(mouvement.getObjectivePos());
		return mouvement;
	}

}
