package com.sp.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.sp.rest.ThreadRestCrt;

@Service
public class ThreadService {
	
	DisplayRunnable dRunnable;
	private Thread displayThread;
	private ThreadRestCrt threadRestCrt;
	
	public ThreadService(ThreadRestCrt threadRestCrt) {
		
		
		this.threadRestCrt = threadRestCrt;
		//Create a Runnable is charge of executing cyclic actions 
		this.dRunnable=new DisplayRunnable(this.threadRestCrt);
		
		// A Runnable is held by a Thread which manage lifecycle of the Runnable
		displayThread=new Thread(dRunnable);
		
		// The Thread is started and the method run() of the associated DisplayRunnable is launch
		displayThread.start();
		
	}
	

	
	public void stopDisplay() {
		//Call the user defined stop method of the runnable
		this.dRunnable.stop();
		try {
			//force the thread to stop
			this.displayThread.join(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Bean(initMethod="init")
	public void init() {
		
	}

}
