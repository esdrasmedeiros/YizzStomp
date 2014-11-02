package com.yizztech.stomp.client.iface;

import com.yizztech.stomp.message.Message;
import com.yizztech.stomp.message.handler.MessageHandler;

public interface PublicStompProtocol {

	/**
	 * Initially the client must open a socket with CONNECT.
	 * 
	 * @param login
	 * @param passCode
	 * @param messageHandler
	 */
	void connect(String login, String passCode, MessageHandler messageHandler);

	/**
	 * DISCONNECT does a graceful disconnect from the server. It is quite polite
	 * to use this before closing the socket.
	 */
	void disconnect();

	/**
	 * The SEND command sends a message to a destination in the messaging
	 * system. It has one required header, destination, which indicates where to
	 * send the message. The body of the SEND command is the message to be sent.
	 * For example:
	 * 
	 * @param message
	 */
	void send(Message message);

	/**
	 * The SUBSCRIBE command is used to register to listen to a given
	 * destination. Like the SEND command, the SUBSCRIBE command requires a
	 * destination header indicating which destination to subscribe to. Any
	 * messages received on the subscription will henceforth be delivered as
	 * MESSAGE frames from the server to the client. The ack header is optional,
	 * and defaults to auto.
	 * 
	 * @param topic
	 */
	void subscribe(String topic);

	/**
	 * The UNSUBSCRIBE command is used to remove an existing subscription - to
	 * no longer receive messages from that destination. It requires either a
	 * destination header or an id header (if the previous SUBSCRIBE operation
	 * passed an id value). Example:
	 * 
	 * @param topic
	 */
	void unsubscribe(String topic);
}
