package com.yizztech.stomp.client.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.yizztech.stomp.client.StompParse;
import com.yizztech.stomp.client.StompSock;
import com.yizztech.stomp.message.ConnectMessage;
import com.yizztech.stomp.message.MessageType;
import com.yizztech.stomp.message.SubscribeMessage;
import com.yizztech.stomp.message.handler.MessageHandler;

/**
 * 
 * @author Daniel Pardo Ligorred @ http://www.programacionj2ee.com/
 * @version 0.1
 * 
 */
public class StompListener extends Thread {

	private boolean stop = false;

	private StompSock stompSock;
	private MessageHandler messageHandler;

	public StompListener(StompSock stompSock, MessageHandler messageHandler) {

		this.stompSock = stompSock;
		this.messageHandler = messageHandler;
	}

	@Override
	public void run() {

		super.run();

		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(
					this.stompSock.getSocket().getInputStream()));

			String line;

			do {

				Map<String, String> data = new HashMap<String, String>();

				boolean firstLine = true;
				MessageType messageType = null;
				int emptyLines = 0;

				while ((line = in.readLine()) != null) {

					if (line.trim().equals("") && messageType == null) {

						break;
					} else if (line.equals("")) {

						emptyLines++;

						if (messageType == MessageType.CONNECTED
								&& emptyLines == 1) {

							break;
						} else if (messageType == MessageType.MESSAGE
								&& emptyLines == 1) {

							line = in.readLine();

							data.put("body", line);
							System.out.println(line);

							break;
						}
					}

					if (firstLine) {

						firstLine = false;

						switch (line.trim().toUpperCase()) {
						case "CONNECTED":

							messageType = MessageType.CONNECTED;
							break;
						case "MESSAGE":

							messageType = MessageType.MESSAGE;
							break;
						}
					} else {

						data.put(line.split(":")[0], line.split(":")[1]);
					}

					System.out.println(line);
				}
				
				if (messageType != null) {

					if (messageType == MessageType.CONNECTED) {

						this.messageHandler.onConnected(new ConnectMessage(
								data, messageType));
					} else if (messageType == MessageType.MESSAGE) {

						SubscribeMessage subscribeMessage = new SubscribeMessage(
								data, messageType);

						try {
							
							// Send automatic ACK
							this.stompSock.sendMessage(StompParse.ack(subscribeMessage
									.getMessageId()));
							
							System.out.println("ACK Sended.");
						} catch (Exception e) {

							System.err
									.println("ACK can`t been sended.");
						}

						this.messageHandler.onMessage(subscribeMessage);
					}
				}
			} while (!this.stop);
		} catch (IOException e1) {

			System.err.println("Unable to start message listener.");
			return;
		}
	}

	public void stopListener() {

		this.stop = true;
	}
}
