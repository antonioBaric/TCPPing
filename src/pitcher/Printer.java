package pitcher;

import java.util.Timer;
import java.util.TimerTask;

//Class for printing statistics every second
public class Printer {
	
	private Timer timerShowStats = new Timer();
	private TimerTask taskShowStats;
	
	public Printer(Calculator calculator){
		
		//Printing messages every second except first one which will not be displayed
		this.taskShowStats = new TimerTask() {
			
			@Override
			public void run() {

				calculator.printAndRestart();
				
			}
		};
		
		timerShowStats.scheduleAtFixedRate(taskShowStats, 1000, 1000);		
	}
}