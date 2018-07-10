package zad3;

public class Main {

	public static void main(String[] args)
	{
		@SuppressWarnings("unused")
		Car c1, c2, c3, c4;
		int poolSize = 3;
		CarLeasePool carPool = new CarLeasePool("The special one car company", poolSize);
		
		System.out.println("==> Let's try to lease more cars than the current limit allows.");
		c1 = carPool.getFromPool();
		c2 = carPool.getFromPool();
		c3 = carPool.getFromPool();
		c4 = carPool.getFromPool();
		
		if (c4 == null)
		{
			System.out.println("Am I a real car?!");
			System.out.println("==> Ups! First we have to return one.");
		}
		carPool.returnToPool(c1);
		
		System.out.println(carPool);
		
		System.out.println("==> Now we can get a new... old one :)");
		c4 = carPool.getFromPool();
		System.out.println(carPool);
		
	}
	
}
