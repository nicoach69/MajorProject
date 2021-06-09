package com.Movements.Repository;

import org.springframework.data.repository.CrudRepository;

import com.Movements.Model.Movement;

public interface MovementRepository extends CrudRepository<Movement, Integer> {

}
