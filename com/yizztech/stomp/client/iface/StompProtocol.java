package com.yizztech.stomp.client.iface;

/**
 * 
 * @author Daniel Pardo Ligorred @ www.programacionj2ee.com
 * 
 */
public interface StompProtocol extends PublicStompProtocol {

	/**
	 * ABORT is used to roll back a transaction in progress.
	 * 
	 * @param transactionId
	 */
	void abort(String transactionId);

	/**
	 * ACK is used to acknowledge consumption of a message from a subscription
	 * using client acknowledgment. When a client has issued a SUBSCRIBE frame
	 * with the ack header set to client any messages received from that
	 * destination will not be considered to have been consumed (by the server)
	 * until the message has been acknowledged via an ACK. ACK has one required
	 * header, message-id, which must contain a value matching the message-id
	 * for the MESSAGE being acknowledged. Additionally, a transaction header
	 * may be specified, indicating that the message acknowledgment should be
	 * part of the named transaction.
	 * 
	 * @param messageId
	 * @param transactionId
	 */
	void ack(String messageId, String transactionId);

	/**
	 * BEGIN is used to start a transaction. Transactions in this case apply to
	 * sending and acknowledging - any messages sent or acknowledged during a
	 * transaction will be handled atomically based on the transaction.
	 * 
	 * @param transactionId
	 */
	void begin(String transactionId);

	/**
	 * COMMIT is used to commit a transaction in progress.
	 * 
	 * @param transactionId
	 */
	void commit(String transactionId);
}