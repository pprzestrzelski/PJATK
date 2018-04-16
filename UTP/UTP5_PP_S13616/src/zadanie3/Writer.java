package zadanie3;

public class Writer implements Runnable {

	private final Counter counter;
	
	Writer(Counter counter) {
		this.counter = counter;
	}
	
	@Override
	public void run() {
		while(true) {
			if(Thread.interrupted())
				break;
			counter.increment();
		}
	}
	
}
