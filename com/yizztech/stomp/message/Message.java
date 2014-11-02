package com.yizztech.stomp.message;

public class Message {

	public static final MessageType MESSAGETY_TYPE = MessageType.MESSAGE;

	private String destination = "";
	private String expires;
	private String redelivered;
	private int priority = 4;
	private String timestamp;
	private String body = "";
	
	public Message(String destination, String body){
		
		this.destination = destination;
		this.body = body;
	}
	
	public Message(String destination, String body, int priority){
		
		this.destination = destination;
		this.body = body;
		this.priority = priority;
	}

	public static MessageType getMessagetyType() {
		return MESSAGETY_TYPE;
	}

	public String getDestination() {
		return destination;
	}

	public String getExpires() {
		return expires;
	}

	public String getRedelivered() {
		return redelivered;
	}

	public int getPriority() {
		return priority;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getBody() {
		return body;
	}
	
}
