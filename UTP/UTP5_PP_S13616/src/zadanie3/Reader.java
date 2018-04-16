package zadanie3;

public class Reader implements Runnable {

	private final Counter counter;
	
	Reader(Counter counter) {
		this.counter = counter;
	}
	
	@Override
	public void run() {
		while(true) {
			if(Thread.interrupted())
				break;
			
			long count = counter.getCounter();
			
			if(count >= Main.COUNT_NUMBER) {
				Main.printExecutionTime(System.currentTimeMillis());
				break;
			}
		}
	}

}
