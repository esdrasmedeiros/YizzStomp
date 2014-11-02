package com.yizztech.stomp.message;

import java.util.Map;

/**
 * 
 * @author Daniel Pardo Ligorred @ http://www.programacionj2ee.com/
 * @version 0.1
 *
 */
public class ConnectMessage {

	public static final String SESSION = "session";
	public static final String RESPONSE_ID = "response-id";

	private MessageType messageType;
	private Map<String, String> data;

	public ConnectMessage(Map<String, String> data, MessageType messageType) {

		this.data = data;
		this.messageType = messageType;
	}

	public MessageType getMessageType() {
		
		return this.messageType;
	}

	public Integer getSession() {

		try {

			return Integer.parseInt(this.data.get(ConnectMessage.SESSION));
		} catch (NumberFormatException ex) {

			return null;
		}
	}

	public String getMessageId() {

		if (this.data.containsKey(ConnectMessage.RESPONSE_ID)) {

			return this.data.get(ConnectMessage.RESPONSE_ID);
		}

		return null;
	}
}