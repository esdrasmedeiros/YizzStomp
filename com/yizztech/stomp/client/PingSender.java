package com.yizztech.stomp.client;

import java.util.Timer;
import java.util.TimerTask;

public class PingSender {

	private StompSock stompSock;

	private Timer timer;

	public PingSender(StompSock stompSock, int secs) {

		this.stompSock = stompSock;

		this.timer = new Timer();

		this.timer.schedule(new SendPingTask(), secs * 1000, secs * 1000);
	}

	private class SendPingTask extends TimerTask {

		public void run() {

			try {

				System.out.println("Send ping.");
				PingSender.this.stompSock.sendMessage("\n");
			} catch (Exception e) {

				System.err.println("Sending ping error.");

				System.out.println("Retrying to connect with server.");
				PingSender.this.stompSock.reconnect();
			}
		}
	}

	public void stop() {

		this.timer.cancel();
		this.timer = null;
	}
}