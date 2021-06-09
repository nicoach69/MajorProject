package com.Movements;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.Movements.Controller.Strategy.IMovementStrategy;
import com.Movements.Controller.Strategy.ItineraryStrategy;
import com.Movements.Controller.Strategy.TeleportationStrategy;


@Component
public class Config {

    @Bean
    public IMovementStrategy getIMovementStrategy(){
    	IMovementStrategy calcService = new ItineraryStrategy();
        return calcService;
    }
}
