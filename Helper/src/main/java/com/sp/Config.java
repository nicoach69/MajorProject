package com.sp;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.sp.helper.HelperReturnStationStrategyNearest;
import com.sp.helper.HelperStrategy;
import com.sp.helper.IHelperReturnStationStrategy;
import com.sp.helper.IHelperStrategy;

@Component 
public class Config {

	
	@Bean 
	public IHelperStrategy getIHelpersStrategy() {
		IHelperStrategy strategyOne = new HelperStrategy();
		return strategyOne;
	}
	
	@Bean 
	public IHelperReturnStationStrategy getIHelperReturnStationStrategy() {
		IHelperReturnStationStrategy returnStationStrategy = new HelperReturnStationStrategyNearest();
		return returnStationStrategy;
	}

}
