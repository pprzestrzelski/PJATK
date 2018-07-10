package zad3;

public class CarLeasePool extends ObjectPool<Car> {

	private String companyName;
	
	public CarLeasePool(String name, int size) {
		super(size);
		this.companyName = name;
	}
	
	public String getName() { return companyName; }

	@Override
	public Car create() {
		return new Car();
	}
	
	@Override
	public String toString()
	{
		return new String(companyName + " has " + howManyAvailable() + " car(s) available and " + 
						  howManyLocked() + " car(s) leased.");
	}

}
