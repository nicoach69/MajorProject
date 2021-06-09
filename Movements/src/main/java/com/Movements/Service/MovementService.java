package com.Movements.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

import com.Movements.Model.Movement;
import com.Movements.Repository.MovementRepository;

@Service
public class MovementService {
	@Autowired
	MovementRepository repository;

    public void addMovement(Movement movement) {
    	repository.save(movement);
    }
    
    public Movement getMovement(int id) {
    	Optional<Movement> mOpt = repository.findById(id);
    	return mOpt.isPresent() ? mOpt.get() : null;
    }
    
    public ArrayList<Movement> getAllMovements() {
    	return (ArrayList<Movement>) repository.findAll();
    }
    
    public void updateMouvements(ArrayList<Movement> mouvements) {
    	mouvements.forEach(mvmt -> repository.save(mvmt));
    }

}
