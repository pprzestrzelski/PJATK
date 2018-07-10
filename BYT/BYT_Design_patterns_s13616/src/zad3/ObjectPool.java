package zad3;

import java.util.LinkedList;

// Class is not thread safe in the current form!
// Some methods require synchronization.
public abstract class ObjectPool<T> {
	
	private LinkedList<T> locked, unlocked;
	private int maxSize;
	
	public ObjectPool(int size)
	{
		maxSize = size == 0 ? 0 : size - 1;
		locked = new LinkedList<T>();
		unlocked = new LinkedList<T>();
	}
	
	public int howManyAvailable()
	{
		return unlocked.size();
	}
	
	public int howManyLocked()
	{
		return locked.size();
	}
	
	public int currentPoolSize()
	{
		return howManyAvailable() + howManyLocked();
	}
	
	public T getFromPool()
	{
		T obj = null;
		if (!unlocked.isEmpty())
		{
			obj = unlocked.poll();
			locked.add(obj);
			System.out.println("Have an object in the pool: " + obj);
			
			return obj;
		}
		
		if (currentPoolSize() <= maxSize && maxSize > 0)
		{
			obj = create();
			locked.add(obj);
			System.out.println("Did't have an object in the pool, but created one: " + obj);
			
			return obj;
		}
		
		System.out.println("Could't create object. Pool is full. Please, try later..." + this);
		return obj;
	}
	
	public void returnToPool(T obj)
	{
		// Pool has to be able to hold sth
		if (maxSize > 0)
		{
			locked.remove(obj);
			unlocked.add(obj);
		}
	}
	
	// May contain additional parameters
	public abstract T create();

}
