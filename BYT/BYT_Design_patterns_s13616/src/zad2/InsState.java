package zad2;

import java.util.Random;

public class InsState implements State {

	double lat;
	double lon;
	double h;
	
	@Override
	public double[] estimatePosition() 
	{
		System.out.println("Position will be estimated using INS only...");
		
		Random rnd = new Random();
		rnd.setSeed(1000);
		double inc = 0.0;
		
		try
		{
			for (int i = 0; i < 3; ++i)
			{
				inc = rnd.nextDouble() * 2 - 1.0;
				lat += inc/100;
				lon += inc/100;
				h += inc/10;
				showPosition();
				Thread.sleep(600);
			}
		}
		catch (Exception e)
		{
			//
		}
		double[] pos = {lat, lon, h};
		return pos;
	}
	
	@Override
	public void showPosition()
	{
		System.out.printf("Lat: %.6f, lon: %.6f, h: %.3f\n", lat, lon , h);
	}
	
	@Override
	public void setInitialCoordinates(double lat, double lon, double h)
	{
		this.lat = lat;
		this.lon = lon;
		this.h = h;
	}
	
	@Override
	public String toString()
	{
		return new String("INS - on, GPS - off");
	}
}
