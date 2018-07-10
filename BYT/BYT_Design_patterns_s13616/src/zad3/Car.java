package zad3;

public class Car {

	private static int id = 1;
	private int carId;
	private String model;
	private String color;
	
	public Car()
	{
		carId = id;
		model = "Ford";
		color = "Black";
		++id;
	}
	
	public int getId() { return carId; }
	public String getModel() { return model; }
	public String getColor() { return color; }
	
	public String toString()
	{
		return new String("Car #" + carId + " model: " + model + " color: " + color);
	}
	
}
