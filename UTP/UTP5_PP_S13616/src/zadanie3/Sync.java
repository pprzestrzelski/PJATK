package zadanie3;

public class Sync implements Counter {

	private Object locker = new Object();
	
	private long counter;
	
	@Override
	public long getCounter() {
		synchronized(locker) {
			return counter;
		}
	}

	@Override
	public void increment() {
		synchronized(locker) {
			++counter;
		}
	}

}
