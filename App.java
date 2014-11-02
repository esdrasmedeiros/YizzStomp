import com.yizztech.stomp.client.StompClient;
import com.yizztech.stomp.message.ConnectMessage;
import com.yizztech.stomp.message.SubscribeMessage;
import com.yizztech.stomp.message.handler.MessageHandler;

public class App implements MessageHandler{

	public App(){

		StompClient stompClient = null;
		
		try {
			// Time to Live: 2 minutes.
			stompClient = new StompClient("localhost", 61614, 120);
			
			stompClient.connect("guest", "guest", this);
			
			stompClient.subscribe("jms.topic.chat");
		} catch (Exception ex) {

			ex.printStackTrace();
		}
	}

	@Override
	public void onConnected(ConnectMessage msg) {
		
		System.out.println("La conexion ha sido establecida con el id de session " + msg.getSession() + "\n");		
	}

	@Override
	public void onMessage(SubscribeMessage msg) {

		System.out.println("priority: " + msg.getPriority());
		System.out.println("body: " + msg.getBody());
		
		System.out.println("Un mensaje ha llegado\n");
	}
}