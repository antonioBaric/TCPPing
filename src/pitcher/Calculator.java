package pitcher;

import java.text.SimpleDateFormat;
import java.util.Date;

//Class for calculating all required times and printing them through the console
public class Calculator {
	
	private int numMessagesPerSecond;
	private int numMessages;
	
	private long sumTimeAtoB;
	private long sumTimeBtoA;
	private long sumTimeAtoBtoA;
	
	private long maxTimeAtoB;
	private long maxTimeBtoA;
	private long maxTimeAtoBtoA;
	
	//Calculating times
	public void newRecord(long startTimeOnClient, long endTimeOnClient, long serverTimeRecievingMessage, long serverTimeSendMessage){
		
		numMessagesPerSecond++;
		numMessages++;
		
		long timeAtoB = serverTimeRecievingMessage - startTimeOnClient;
		long timeBtoA = endTimeOnClient - serverTimeSendMessage;
		long timeAtoBtoA = endTimeOnClient - startTimeOnClient - (serverTimeSendMessage - serverTimeRecievingMessage);
		
		sumTimeAtoB += timeAtoB;
		sumTimeBtoA += timeBtoA;
		sumTimeAtoBtoA += timeAtoBtoA;
		
		if (timeAtoB > maxTimeAtoB) {
			maxTimeAtoB = timeAtoB;
		}
		if (timeBtoA > maxTimeBtoA) {
			maxTimeBtoA = timeBtoA;
		}
		if (timeAtoBtoA > maxTimeAtoBtoA) {
			maxTimeAtoBtoA = timeAtoBtoA;
		}
	}
	
	public int getNumMessages() {
		return numMessages;
	}
	
	//Calculating times and printing them through the console
	public void printAndRestart(){
		
		double averageTimeAtoB = (double)sumTimeAtoB / numMessagesPerSecond;
		double averageTimeBtoA = (double)sumTimeBtoA / numMessagesPerSecond;
		double averageTimeAtoBtoA = (double)sumTimeAtoBtoA / numMessagesPerSecond;
		
		printCalculations(averageTimeAtoB, averageTimeBtoA, averageTimeAtoBtoA);
		
		numMessagesPerSecond = 0;
		
		sumTimeAtoB = 0;
		sumTimeBtoA = 0;
		sumTimeAtoBtoA = 0;
		
		maxTimeAtoB = 0;
		maxTimeBtoA = 0;
		maxTimeAtoBtoA = 0;
	}
	
	//Message that will be shown when calculations are finished
	private void printCalculations(double averageTimeAtoB, double averageTimeBtoA, double averageTimeAtoBtoA){		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		
		String title = String.format("%1$-15s %2$-17s %3$-25s %4$-12s %5$-12s %6$-12s %7$-12s %8$-12s %9$-12s", "Current Time", "Messages sent", "Messages prev.second", "Time A->B", "Time B->A", "Time RTT", "Max A->B", "Max B->A", "Max RTT");

		String numbers = String.format("%1$-15s %2$-17d %3$-25d %4$-12.3f %5$-12.3f %6$-12.3f %7$-12.3f %8$-12.3f %9$-12.3f", dateFormat.format(date),numMessages, numMessagesPerSecond, averageTimeAtoB /1e6, averageTimeBtoA / 1e6, averageTimeAtoBtoA /1e6, (double)maxTimeAtoB/1e6, (double)maxTimeBtoA/1e6, (double)maxTimeAtoBtoA/1e6);
				
		System.out.println(title);
		System.out.println(numbers + "\n");
	}
}