package zad2;

public interface State {
	double[] estimatePosition();
	void setInitialCoordinates(double lat, double lon, double h);
	void showPosition();
}
