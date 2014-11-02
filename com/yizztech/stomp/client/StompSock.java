package com.yizztech.stomp.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.yizztech.stomp.client.listener.StompListener;
import com.yizztech.stomp.exception.StompException;
import com.yizztech.stomp.message.Message;
import com.yizztech.stomp.message.handler.MessageHandler;
import com.yizztech.util.MD5;

/**
 * 
 * @author Daniel Pardo Ligorred @ http://www.programacionj2ee.com/
 * @version 0.1
 * 
 */
public class StompSock {

	private String host;
	private int port;

	private boolean connected = false;

	private Socket socket;
	private int heartBeat;

	private String login;
	private String passcode;

	private MessageHandler messageHandler;
	private StompListener stompListener;
	private PingSender pingSender;
	private Map<String, String> topics = new HashMap<String, String>();

	public StompSock(String host, int port, int heartBeat, String login,
			String passcode, MessageHandler messageHandler)
			throws StompException {

		if (messageHandler == null) {

			throw new StompException("MessageHandler can´t be null.");
		}

		this.host = host;
		this.port = port;
		this.heartBeat = heartBeat;
		this.login = login;
		this.passcode = passcode;

		this.connect();

		if (this.socket.isConnected()) {

			this.messageHandler = messageHandler;

			try {

				this.setUpListener();
			} catch (StompException e) {

				this.disconnect();
				System.err.println(e.getMessage());
			}
		} else {

			System.err.println("Unable to connect with " + this.host + ":"
					+ this.port);
			this.disconnect();
		}
	}

	public Socket getSocket() {

		return this.socket;
	}
	
	public Map<String, String> getTopics() {

		return this.topics;
	}

	private void connect() {

		try {

			if (this.socket == null) {

				this.socket = new Socket(this.host, this.port);
			}

			this.sendFrame(StompParse.connect(this.login, this.passcode));

			this.connected = true;
			System.out.println("Connection established.");

			if (this.pingSender == null) {

				this.pingSender = new PingSender(this, this.heartBeat);
			}
		} catch (Exception e) {

			System.err.println("Unable to connect with server");
		}
	}

	public synchronized void reconnect() {

		this.connected = false;

		try {

			if (this.socket != null) {

				this.socket.close();
				this.socket = null;
			}
		} catch (IOException ioe) {

			System.err.println(ioe.getMessage());
		}

		this.connect();

		if (!this.connected) {

			return;
		}

		for (Map.Entry<String, String> entry : this.topics.entrySet()) {

			try {

				this.subscribe(entry.getValue());
			} catch (StompException e) {
				System.err
						.println("Unable to subscribe with the server on reconnection:\n"
								+ e.getMessage());
			}
		}

		try {

			this.setUpListener();
		} catch (StompException se) {

			System.err.println(se.getMessage());
		}
	}

	private void setUpListener() throws StompException {

		if (this.messageHandler == null) {

			throw new StompException("MessageHandler can´t be null.");
		}

		if (this.stompListener != null) {

			this.stompListener.stopListener();
			this.stompListener = null;
		}

		this.stompListener = new StompListener(this, this.messageHandler);
		this.stompListener.start();
	}

	private synchronized void sendFrame(String data) throws Exception {

		byte[] bytes = data.getBytes("UTF-8");
		OutputStream outputStream = this.socket.getOutputStream();
		for (int i = 0; i < bytes.length; i++) {
			outputStream.write(bytes[i]);
		}
		outputStream.flush();
	}

	/** CAMBIAR PARA NO PERMITIR EL ENVIO DE CUALQUIER COSA */
	public void sendMessage(String data) throws StompException {

		try {

			this.sendFrame(data);
		} catch (Exception e) {

			throw new StompException("Unable to send the message:\n"
					+ e.getMessage());
		}
	}

	public void subscribe(String topic) throws StompException {

		if (topic == null) {

			throw new StompException("Topic can´t be null");
		}

		System.out.println("Subscribing to " + topic);
		this.sendMessage(StompParse.subscribe(topic));

		synchronized (this.topics) {

			try {

				if (!this.topics.containsKey(MD5.toHashCode(topic))) {

					this.topics.put(MD5.toHashCode(topic), topic);
				}
			} catch (Exception e) {

				throw new StompException("Error adding topic in topic list.");
			}
		}
	}

	public void unsubscribe(String topic) {

		try {

			if (this.topics.containsKey(MD5.toHashCode(topic))) {

				this.topics.remove(MD5.toHashCode(topic));
				this.sendFrame(StompParse.unsubscribe(topic));
			}
		} catch (Exception e) {

			System.err.println("Error unsubscribing:\n" + e.getMessage());
		}
	}

	public void send(Message message) {

		try {
			this.sendFrame(StompParse.send(message));
		} catch (Exception e) {

			System.err.println("Unable to send message to server:\n"
					+ e.getMessage());
		}
	}

	public void disconnect() {

		try {

			this.sendFrame(StompParse.disconnect());

			if (this.socket != null) {

				this.socket.close();
			}

			if (this.stompListener != null) {

				this.stompListener.stopListener();
			}

			if (this.passcode != null) {

				this.pingSender.stop();
			}

			this.sendMessage(StompParse.disconnect());
		} catch (Exception e) {

			System.err.println("Some error occurs on disconnect process.");
		} finally {

			this.stompListener = null;
			this.pingSender = null;
			this.socket = null;
		}
	}
}
