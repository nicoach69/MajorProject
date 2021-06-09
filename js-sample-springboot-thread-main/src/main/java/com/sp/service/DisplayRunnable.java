package com.sp.service;

import java.net.URISyntaxException;

import org.springframework.web.client.RestClientException;

import com.sp.rest.ThreadRestCrt;

public class DisplayRunnable implements Runnable {

	boolean isEnd = false;
	private ThreadRestCrt threadRestCrt;

	public DisplayRunnable(ThreadRestCrt threadRestCrt) {
		this.threadRestCrt = threadRestCrt;
	}

	@Override
	public void run() {
		while (!this.isEnd) {
			try {
				Thread.sleep(1000);
				threadRestCrt.getFire();
				threadRestCrt.getVehicle();
				threadRestCrt.calcul();
			} catch (InterruptedException | RestClientException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Runnable DisplayRunnable ends.... ");
	}

	public void stop() {
		this.isEnd = true;
	}

}
