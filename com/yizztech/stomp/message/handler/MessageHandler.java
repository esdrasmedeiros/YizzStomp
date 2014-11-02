package com.yizztech.stomp.message.handler;

import com.yizztech.stomp.message.ConnectMessage;
import com.yizztech.stomp.message.SubscribeMessage;

/**
 * 
 * @author Daniel Pardo Ligorred @ http://www.programacionj2ee.com/
 * @version 0.1
 *
 */
public interface MessageHandler {
	
	public void onConnected(ConnectMessage msg);
	
	public void onMessage(SubscribeMessage msg);	
	
}
