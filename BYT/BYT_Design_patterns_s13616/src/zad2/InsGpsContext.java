package zad2;

public class InsGpsContext implements State {
	
	private State devState;
	double lat;
	double lon;
	double h;
	
	public InsGpsContext(State state)
	{
		System.out.println(" === INS/GPS device starts estimating position  === ");
		devState = state;
	}
	
	public State getState()
	{
		return devState;
	}
	
	public void setState(State state)
	{
		devState = state;
		System.out.println("INS/GPS device changed its state to " + devState);
	}

	@Override
	public double[] estimatePosition() {
		devState.setInitialCoordinates(lat, lon, h);
		double[] pos = devState.estimatePosition();
		lat = pos[0]; lon = pos[1]; h = pos[2];
		return pos;
	}
	
	@Override
	public void showPosition()
	{
		System.out.printf("Lat: %.6f, lon: %.6f, h: %.3f", lat, lon , h);
	}

	@Override
	public void setInitialCoordinates(double lat, double lon, double h) {
		this.lat = lat;
		this.lon = lon;
		this.h= h;
	}

}
