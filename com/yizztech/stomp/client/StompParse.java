package com.yizztech.stomp.client;

import com.yizztech.stomp.message.Message;

/**
 * 
 * @author Daniel Pardo Ligorred @ http://www.programacionj2ee.com/
 * @version 0.1
 *
 */
public class StompParse {

	public static final String END_OF_FRAME = "\u0000";
	
	public static String connect(String login, String passcode){
		
		return "CONNECT\n" +
	            "login: " + login + "\n" +
	            "passcode: " + passcode + "\n" +
	            "request-id: 1\n" +
	            "\n" +
	            END_OF_FRAME;
	}
	
	public static String send(Message message){
		
	 	return "SEND\n" +
	            "destination: " + message.getDestination() + "\n" +
	            "priority: " + message.getPriority() + "\n" +
	            "\n" +
	            message.getBody() +
	            END_OF_FRAME;
	}
	
	public static String subscribe(String topic){
		
	 	return "SUBSCRIBE\n" +
	            "destination: " + topic + "\n" +
	            "ack: client\n" +
	            "\n" +
	            END_OF_FRAME;
	}
	
	public static String unsubscribe(String topic){
		
	 	return "UNSUBSCRIBE\n" +
	            "destination: " + topic + "\n" +
	            "\n" +
	            END_OF_FRAME;
	}
	
	public static String ack(String message_id){
		
	 	return "ACK\n" +
	            "message-id: " + message_id + "\n" +
	            "\n" +
	            END_OF_FRAME;
	}
	
	public static String disconnect(){
		
	 	return "DISCONNECT\n" +
	            "\n" +
	            END_OF_FRAME;
	}
}
