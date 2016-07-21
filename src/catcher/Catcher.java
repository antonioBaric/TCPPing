package catcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class Catcher {
	
	public Catcher(String bind, int port){
		
		//Note user that he successfully started server side of program with a few useful informations
		System.out.println("Server side program started");
		System.out.println("This is a server on address " + bind + ",listening on port " + port + "\n");	
		
		while (true) {
			//Opening a new socket and streams for communication with client (pitcher). Using "try-with-resources" method for closing socket and streams automatically
			try (	
				ServerSocket server = new ServerSocket(port);
				Socket client = server.accept();
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				)
			{				
				System.out.println("Connected to " + client.getRemoteSocketAddress() + "\n");
				
				String recievedMessage;
				long recievedMessageTime;	
				
				while ((recievedMessage = in.readLine()) != null) {
					
					//Reading messages from client (pitcher) and forwarding them with useful informations of time when server recieved and sent message to client.
					recievedMessageTime = System.nanoTime();
					int messageSize = recievedMessage.getBytes().length;					
					String[] partsOfMessage = recievedMessage.split(" ");
					int idMessageFromClient = Integer.parseInt(partsOfMessage[0]);
					String messageToSend = buildMessageToSent(idMessageFromClient, messageSize - 9, recievedMessageTime);					
					long sendMessageTime = System.nanoTime();
					out.println(sendMessageTime + " " + messageToSend);	//8 bytes(long sendMessageTime) + 1 space = 9 bytes
				}
				
			}catch (SocketTimeoutException | SocketException e) {
				System.err.println("Connection timed out, or terminated by client!");
			}
			catch (IOException e) {
				System.err.println("error on I/O!");
				System.err.println(e.getMessage());
				System.exit(1);
			} 
		}		
	}
	// Formatting string how pitcher could extract useful informations for calculating the time
	private String buildMessageToSent(int idMessage, int messageSize, long recievedMessageTime) {
		
		String string = idMessage + " " + recievedMessageTime + " ";
		
		for (int i = 0; i < (messageSize-string.getBytes().length); i++) {
			string  = string + "i";
		}		
		return string;
	}
}