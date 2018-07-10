import java.util.*;

// TODO: bad smell: duplicate code, data class
//		 solution:  extract duplicated methods, extract setter/getter
public class Configuration {
	
	// TODO: interval, duration and departure were public!
	private Integer interval = null;
	private Integer duration = null;
	private Integer departure = null;

	public void load(Properties props) throws ConfigurationException 
	{
		int value;
		
		value = getProperty(props, "interval");
		setInterval(value);

		value = getProperty(props, "duration");
		setDuration(value);

		value = getProperty(props, "departure");
		setDeparture(value);
	}
	
	private int getProperty(Properties props, String prop) throws ConfigurationException
	{
		String valueString = props.getProperty(prop);
		if (valueString == null) {
			throw new ConfigurationException(prop);
		}
		
		int value = Integer.parseInt(valueString);
		if (value <= 0) {
			throw new ConfigurationException(prop + " > 0");
		}
		
		if (!prop.equals("interval"))
		{
			// TODO: added interval's verification
			if (getInterval() == null)
			{
				throw new ConfigurationException("interval is not setup");
			}
			
			if ((value % getInterval()) != 0) 
			{
				throw new ConfigurationException(props + " % interval");
			}
		}
		
		return value;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getDeparture() {
		return departure;
	}

	public void setDeparture(Integer departure) {
		this.departure = departure;
	}
}
