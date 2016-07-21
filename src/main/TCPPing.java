package main;

import catcher.Catcher;
import pitcher.Pitcher;

public class TCPPing {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		try {
			// Initializing arrays, and setting some default values on "mps" and "size"
			String type;
			String bindAddress;
			int port = -1;
			int mps = 1;
			int size = 300;
			String hostname = null;
			
			type = args[0];
			int arguments = args.length;
			
			//Forwarding values from command line if user input is correct for catcher mode
			if(arguments < 4 || arguments > 8) wrongInput();
			if (!type.equals("-p") && !type.equals("-c")) wrongInput();			
			
			else if (type.equals("-c")) {
				if (arguments != 5 || !args[1].equals("-bind") || !args[3].equals("-port")) {
					wrongInput();
				}													
				bindAddress = args[2];
				port = Integer.parseInt(args[4]);
				Catcher catcher = new Catcher(bindAddress, port);				
			}
			
			//Forwarding values from command line if user input is correct for pitcher mode. More possibilities for input since "mps" and "size" have their own default properties.
			else if (type.equals("-p")) {
				
				if(arguments == 4 || arguments == 6 || arguments == 8){
					
					port = Integer.parseInt(args[2]);
					
					if(arguments == 4){
						
						if(!args[1].equals("-port")) wrongInput();
						if(args[3].equals("-mps") || args[3].equals("-size")) wrongInput();
						hostname = args[3];									
					}
					else if (arguments == 8) {
						
						if(!args[1].equals("-port") ||  !args[3].equals("-mps") || !args[5].equals("-size")) wrongInput();
						mps = Integer.parseInt(args[4]);
						size = Integer.parseInt(args[6]);
						hostname = args[7];						
					}
					else if (arguments == 6) {
						hostname = args[5];
						if(!args[1].equals("-port")) wrongInput();
						if (args[3].equals("-mps")) {
							mps = Integer.parseInt(args[4]);
						}
						else if (args[3].equals("-size")) {
							size = Integer.parseInt(args[4]);
						}
						else wrongInput();						
					}					
				}else wrongInput();		
								
				if (size < 50 || size > 3000 ) {
					System.err.println("Message size out of range! It must be between 50 and 3000.");
					wrongInput();
				}
				
				Pitcher pitcher = new Pitcher(port, mps, size, hostname);
			}
			
		} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
			wrongInput();
		}
	}	
	
	//Message that will be shown if user input is not valid
	public static void wrongInput(){
		System.err.println("Wrong input format!\nUsage for Pitcher: java main.TCPPing -p -port <port number> -mps <message per second> -size <message size> <hostname>"
				+ "\nUsage for Catcher: java main.TCPPing -c -bind <socket bind address> -port <socket port>"
				+ "\nNote that in Pitcher mode you can ignore 'messages per second(mps)' or 'size' because they are optional. ");
		System.exit(1);
	}
}