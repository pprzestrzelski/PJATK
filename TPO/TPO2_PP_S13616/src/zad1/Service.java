/**
 *
 *  @author Przestrzelski Paweł S13616
 *
 */

package zad1;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Currency;
import java.util.Locale;

import org.json.*;

public class Service 
{
	private String country;
	private Locale countryLocale = null;
	private String countryCurrency;
	private static final String API_FIXER_BASE = "https://api.fixer.io";
	private static final String OPEN_WEATHER_BASE = "https://api.openweathermap.org";
	private static final String OPEN_WEATHER_APPID = "1f49695d4a9aed2b6c93a8ba1552e935";
	
	public Service(String country)
	{
		this.country = country;
		findCountryInLocale();
	}
	
	private void findCountryInLocale()
	{
		// TODO: find more efficient solution of Country --> Locale for country problem
		Locale []availableLocales = Locale.getAvailableLocales();
		for (Locale l : availableLocales)
		{
			if (l.getDisplayCountry().equals(country))
			{
				Locale countryLocale = l;
				countryCurrency = Currency.getInstance(countryLocale).getCurrencyCode();
				return;
			}
		}	
	}
	
	public String getWeather(String city)
	{
		String url = OPEN_WEATHER_BASE + "/data/2.5/weather?q=" + city +
					 "&units=metric" +
					 "&APPID=" + OPEN_WEATHER_APPID;
		return getResponse(url);
	}
	
	// Nice example using Gson: http://www.javatraineronline.com/java/simple-currency-converter-using-java/
	public Double getRateFor(String currency)
	{
		String url = API_FIXER_BASE + "/latest?base=" + countryCurrency;
		String response = getResponse(url);
		//System.out.println(response);
		return getCurrencyRate(currency, response);
	}
	
	private String getResponse(String strUrl)
	{
		StringBuffer sb = new StringBuffer();
		
		if (strUrl == null || strUrl.isEmpty())
		{
			return null;
		}
		
		try 
		{
			URL url = new URL(strUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			InputStream stream = connection.getInputStream();
			
			int data;
			while ((data = stream.read()) != -1)
			{
				sb.append((char) data);
			}
			
			stream.close();
		}
		catch (Exception e)
		{
			// ERROR!
			// TODO: Handle MalformedURLException and IOException!
			System.out.println(e.toString());
		}
		
		return sb.toString();
	}
	
	// http://www.oracle.com/technetwork/articles/java/json-1973242.html
	private double getCurrencyRate(String forCurrency, String serviceResponse)
	{
		JSONObject json = new JSONObject(serviceResponse);
		double rate = 0.0; 
		try
		{
			rate = json.getJSONObject("rates").getDouble(forCurrency);
		
		}
		catch (JSONException je)
		{
			// Error while parsing JSON
		}
		catch (Exception e)
		{
			// Unknown exception
		}
		
		return rate;
	}
	
	public Double getNBPRate()
	{
		// TODO: Implemntation!
		return new Double(0.0);
	}
	
}  
