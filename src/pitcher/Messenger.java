package pitcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

//Class for sending and recieving messages to server (catcher) at certain speed and size of message that was specified by the client
public class Messenger {

	private Timer timerSendMessages = new Timer();
	private TimerTask taskSendMessages;

	@SuppressWarnings("resource")
	public Messenger(String hostname, int port, int mps, int size, Calculator calculator) {
		
		//Opening a new socket and streams for communication with client (pitcher)
		try {
			Socket client = new Socket(hostname, port);
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			//Sending messages continuously at fixed rate that was specified by the user
			this.taskSendMessages = new TimerTask() {

				@Override
				public void run() {
					
					int idMessage = calculator.getNumMessages();
					String message = createMessage(size, idMessage);
					long startTimeOnClient = System.nanoTime();
					out.println(message);
					String messageFromServer = null;
					long endTimeOnClient = 0;
					try {
						messageFromServer = in.readLine();
						endTimeOnClient = System.nanoTime();
					} catch(SocketException e){
						System.err.println("Lost connection with server");
						System.err.println(e.getMessage());
						System.exit(1);
					} catch (IOException e) {
						System.err.println("I/O greska, over");
						System.out.println(e.getMessage());
						System.exit(1);
					}
					
					String[] partsOfMessage = messageFromServer.split(" ");
					if (Integer.parseInt(partsOfMessage[1]) != idMessage) {
						 System.err.println("Message with ID(" + idMessage + ") has been lost");
					}
					long serverTimeSendMessage = Long.parseLong(partsOfMessage[0]);
					long serverTimeRecievingMessage = Long.parseLong(partsOfMessage[2]);
					
					calculator.newRecord(startTimeOnClient, endTimeOnClient, serverTimeRecievingMessage, serverTimeSendMessage);
				}
			};
			timerSendMessages.scheduleAtFixedRate(taskSendMessages, 0, 1000 / mps);

		} catch (UnknownHostException e) {
			System.err.println("Unknown host");
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (IOException e) {
			System.err.println("I/O error, no response from server!");
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (IllegalArgumentException e) {
			System.err.println("Invalid input for mps (messages per second)!");
			System.err.println(e.getMessage());
			System.exit(1);
		} 

	}
	
	//Creating message with specific size
	private String createMessage(int size, int idMessage) {
		String string = idMessage + " ";
		for (int i = 0; i < size - string.getBytes().length; i++) {
			string += "i";
		}		
		return string;
	}
}