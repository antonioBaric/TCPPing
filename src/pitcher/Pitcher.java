package pitcher;

public class Pitcher {
	
	@SuppressWarnings("unused")
	public Pitcher(int port, int mps, int size, String hostname){
		
		//Note user that he successfully started client side of program (pitcher) with a few useful informations
		printInitialization(port, mps, size, hostname);
		
		//Creating new classes and objects for better optimizing and understanding the written code.
		Calculator calculator = new Calculator();		
		Messenger messenger = new Messenger(hostname, port, mps, size, calculator);		
		Printer printer = new Printer(calculator);
		
	}
	
	private void printInitialization(int port, int mps, int size, String hostname) {

		System.out.println("Client program started\n");

		System.out.println("type: Pitcher");
		System.out.println("port number: " + port);
		System.out.println("messages per second: " + mps);
		System.out.println("size of message: " + size);
		System.out.println("hostname: " + hostname + "\n\n");
	}
}