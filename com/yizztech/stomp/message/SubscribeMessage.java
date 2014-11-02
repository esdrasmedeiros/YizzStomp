package com.yizztech.stomp.message;

import java.util.Map;

/**
 * 
 * @author Daniel Pardo Ligorred @ http://www.programacionj2ee.com/
 * @version 0.1
 *
 */
public class SubscribeMessage {

	public static final String MESSAGE = "MESSAGE";
	public static final String SUBSCRIPTION = "subscription";
	public static final String MESSAGE_ID = "message-id";
	public static final String DESTINATION = "destination";
	public static final String EXPIRES = "expires";
	public static final String REDELIVERED = "redelivered";
	public static final String PRIORITY = "priority";
	public static final String TIMESTAMP = "timestamp";
	public static final String HQ_CID = "__HQ_CID";
	private static final String BODY = "body";

	private MessageType messageType;
	private Map<String, String> data;

	public SubscribeMessage(Map<String, String> data, MessageType messageType) {

		this.data = data;
		this.messageType = messageType;

	}

	public MessageType getMessageType() {
		return messageType;
	}

	public String getTopic() {
		
		return this.data.get(SubscribeMessage.DESTINATION);
	}

	public String getMessageId() {

		return this.data.get(SubscribeMessage.MESSAGE_ID);
	}

	public int getExpires() {

		return Integer.parseInt(this.data.get(SubscribeMessage.EXPIRES));
	}

	public boolean isRedelivered() {

		return Boolean.valueOf(this.data.get(SubscribeMessage.REDELIVERED));
	}

	public int getPriority() {

		return Integer.parseInt(this.data.get(SubscribeMessage.PRIORITY));
	}

	public int getTimestamp() {
		
		return Integer.parseInt(this.data.get(SubscribeMessage.TIMESTAMP));
	}

	public String getHQ_CID() {

		return this.data.get(SubscribeMessage.HQ_CID);
	}

	public String getBody() {
		
		return this.data.get(SubscribeMessage.BODY);
	}
}