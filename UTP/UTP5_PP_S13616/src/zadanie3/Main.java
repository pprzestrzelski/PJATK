/**
 *
 *  @author Przestrzelski Paweł S13616
 *
 */

package zadanie3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
	
	public static long COUNT_NUMBER = 10000000;
	
	public static int THREADS = 4;	// Inne wyniki uzyskamy dla 8 watkow...
	public static int REPEATS = 5;
	
	private static ExecutorService exec;
	  
	private static long timeStart;
	private static int round;
	private static Boolean[] rounds;
	
	private static List<Long> results;
	  
	public static void main(String[] args) {
	  	  
	  results = new ArrayList<>(REPEATS*2);
	  
	  System.out.println("Czas wykonania zadania dla ReadWriteLock'ow:");
	  
	  rounds = new Boolean[REPEATS];
	  for(round = 0; round < REPEATS; ++round) {
		  
		  rounds[round] = Boolean.FALSE;
		  
		  Counter counter = new RWLock();
		  exec = Executors.newFixedThreadPool(THREADS);
		  
		  timeStart = System.currentTimeMillis();
		  
		  for(int i=0; i<THREADS; i+=2) {
			  exec.execute(new Reader(counter));
			  exec.execute(new Writer(counter));
		  }
		  
		  try {
			  exec.awaitTermination(4, TimeUnit.MINUTES);
		  } catch (InterruptedException e) {
			  e.printStackTrace();
		  }
		  
	  }
	  
	  long avg = 0;
	  for(int j=0; j<REPEATS; ++j) {
		avg += results.get(j);  
	  }
	  avg /= REPEATS;
	  
	  System.out.println("Sredni czas: " + avg + "ms.");
	  
	  System.out.println("Czas wykonania tego samego zadania z zastosowaniem sekcji Synchronized:");
	  
	  rounds = new Boolean[REPEATS];
	  for(round = 0; round < REPEATS; ++round) {
		  
		  rounds[round] = Boolean.FALSE;
		  
		  Counter counter = new Sync();
		  exec = Executors.newFixedThreadPool(THREADS);
		  
		  timeStart = System.currentTimeMillis();
		  
		  for(int i=0; i<THREADS; i+=2) {
			  exec.execute(new Reader(counter));
			  exec.execute(new Writer(counter));
		  }
		  
		  try {
			  exec.awaitTermination(4, TimeUnit.MINUTES);
		  } catch (InterruptedException e) {
			  e.printStackTrace();
		  }  
	  }
	  
	  avg = 0;
	  for(int j=REPEATS; j<REPEATS*2; ++j) {
		avg += results.get(j);  
	  }
	  avg /= REPEATS;
	  
	  System.out.println("Sredni czas: " + avg + "ms.");
	}
  
	public static void printExecutionTime(long timeEnd) {
	  synchronized(rounds[round]) {
		  if(rounds[round] == Boolean.FALSE) {
			  long execTime = timeEnd - timeStart;
			  results.add(execTime);
			  System.out.println("       ----> " + execTime);
			  rounds[round] = Boolean.TRUE;
			  exec.shutdownNow();
		  }
	  }
	}
}
