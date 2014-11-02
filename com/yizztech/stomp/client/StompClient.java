package com.yizztech.stomp.client;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.yizztech.stomp.client.iface.PublicStompProtocol;
import com.yizztech.stomp.exception.StompException;
import com.yizztech.stomp.message.Message;
import com.yizztech.stomp.message.handler.MessageHandler;

public class StompClient implements PublicStompProtocol {

	private String host;
	private int port;
	private int heartBeat;

	private StompSock stompSock;

	public StompClient(String host, int port, int heartBeat) {

		this.host = host;
		this.port = port;
		this.heartBeat = heartBeat;
	}

	public List<String> getSubscribedTopics(){
		
		List<String> topicList = new Vector<String>();
		
		for (Map.Entry<String, String> entry : this.stompSock.getTopics().entrySet()) {

			topicList.add(entry.getValue());
		}
		
		return topicList;
	}
	
	@Override
	public void connect(String login, String passCode, MessageHandler messageHandler) {

		try {
			
			this.stompSock = new StompSock(this.host, this.port,
					this.heartBeat, login, passCode, messageHandler);
		} catch (StompException se) {

			System.err.println(se.getMessage());
		}
	}

	@Override
	public void disconnect() {

		if (this.stompSock == null) {

			System.err.println("Connection is not set up already.");
		} else {

			this.stompSock.disconnect();
			this.stompSock = null;
		}
	}

	@Override
	public void send(Message message) {

		this.stompSock.send(message);
	}

	@Override
	public void subscribe(String topic) {

		if (this.stompSock == null) {

			System.err.println("Connection is not set up already.");
		} else {

			try {
				
				this.stompSock.subscribe(topic);
			} catch (StompException e) {

				System.err.println(e.getMessage());
			}
		}
	}

	@Override
	public void unsubscribe(String topic) {

		this.stompSock.unsubscribe(topic);
	}
}
