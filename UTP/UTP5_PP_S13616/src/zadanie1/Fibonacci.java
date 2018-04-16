package zadanie1;

import java.util.concurrent.Callable;

public class Fibonacci implements Callable<Long> {

	private static int count = 0;
	private int n,
				id;
	
	Fibonacci(int n) {
		this.n = n;
		this.id = ++count;
	}
	
	public Long fibo(int n) {
		return n < 2 ? n : fibo(n-1) + fibo(n-2);
	}

	@Override
	public Long call() throws Exception {
		return fibo(n);
	}
	
	@Override
	public String toString() {
		return "Fibonacci (" + n + "), id: " + id;
	}

}
