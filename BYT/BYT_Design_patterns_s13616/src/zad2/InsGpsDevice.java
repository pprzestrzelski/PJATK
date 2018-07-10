package zad2;

// State design pattern
public class InsGpsDevice {

	public static void main(String[] args) {
		InsState insState = new InsState();
		GpsState gpsState = new GpsState();
		InsGpsState insGpsState = new InsGpsState();
		
		// Turn on INS only
		double latitude = 52.0;
		double longitude = 21.0;
		double height = 130.0;
		InsGpsContext igc = new InsGpsContext(insState);
		igc.setInitialCoordinates(latitude, longitude, height);
		igc.estimatePosition();
		
		// Turn off INS, turn on GPS
		igc.setState(gpsState);
		igc.estimatePosition();
		
		// Turn on both
		igc.setState(insGpsState);
		igc.estimatePosition();
	}

}
