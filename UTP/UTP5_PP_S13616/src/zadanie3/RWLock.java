package zadanie3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLock implements Counter{

	private ReadWriteLock lock = new ReentrantReadWriteLock();;
	private Lock readLock = lock.readLock();
	private Lock writeLock = lock.writeLock();
	
	private long counter;
	
	@Override
	public long getCounter() {
		try {
			readLock.lock();
			return counter;
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public void increment() {
		try {
			writeLock.lock();
			++counter;
		} finally {
			writeLock.unlock();
		}
	}

}
