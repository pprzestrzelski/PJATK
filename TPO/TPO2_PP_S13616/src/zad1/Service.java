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
	private Locale countryLocale;
	private String countryCurrency;
	private String currencyToCompareWith;
	private static final String API_FIXER_BASE = "https://api.fixer.io";
	private static final String OPEN_WEATHER_BASE = "https://api.openweathermap.org";
	private static final String OPEN_WEATHER_APPID = "1f49695d4a9aed2b6c93a8ba1552e935";
	private static final String API_NBP_BASE = "http://api.nbp.pl";
	
	public Service(String country)
	{
		this.country = country;
		Locale.setDefault(new Locale("US"));
		currencyToCompareWith = "PLN";	// Default value, just in case
		findCountryInLocale();
	}
	
	private void findCountryInLocale()
	{
		// TODO: find more efficient solution of Country --> Locale for country problem
		//		 Maybe Map<String, Locale> to find locale easier.
		Locale []availableLocales = Locale.getAvailableLocales();
		for (Locale l : availableLocales)
		{
			//System.out.println(l.getDisplayName());
			if (l.getDisplayCountry().equals(country))
			{
				countryLocale = l;
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
	
	/*
	 * Sample Fixer.io response:
	 * {
	 * 	"base":"PLN",
	 * 	"date":"2018-03-28",
	 * 	"rates":
	 * 		{
	 * 			"AUD":0.38344,
	 * 			"BGN":0.46487,
	 * 			"BRL":0.98079,
	 * 				  ...
	 * 			"USD":0.29469,
	 * 			"ZAR":3.4472
	 * 		}
	 * }
	*/
	// Nice example using Gson: http://www.javatraineronline.com/java/simple-currency-converter-using-java/
	public Double getRateFor(String currency)
	{
		currencyToCompareWith = currency;
		String url = API_FIXER_BASE + "/latest?base=" + countryCurrency;
		String response = getResponse(url);
		return getCurrencyRateFromResponse(currencyToCompareWith, response);
	}
	
	// http://www.oracle.com/technetwork/articles/java/json-1973242.html
	private double getCurrencyRateFromResponse(String forCurrency, String serviceResponse)
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
	
	/*
	 * Sample response from NBP:
	 * {
	 * 	"table":"A",
	 * 	"currency":"frank szwajcarski",
	 * 	"code":"CHF",
	 * 	"rates":
	 * 		[{
	 * 			"no":"062/A/NBP/2018",
	 * 			"effectiveDate":"2018-03-28",
	 * 			"mid":3.5731
	 * 		}]
	 * 	} 
	 * 
	*/
	public Double getNBPRate()
	{
		if (countryCurrency.equals("PLN"))
			return 1.0;		// PLN ==> PLN rate = 1.0

		double rate = 0.0;
		String url = API_NBP_BASE + "/api/exchangerates/rates/a/" + countryCurrency + "/";
		String response = getResponse(url);
		
		if (response.isEmpty())
		{
			// try to find in a b table
			url = API_NBP_BASE + "/api/exchangerates/rates/b/" + countryCurrency + "/";
			response = getResponse(url);
			if (response.isEmpty())
			{
				// TODO: Include some kind of notification?!
				return rate;
			}
		}
		
		try
		{
			JSONObject json = new JSONObject(response);
			JSONObject jsonRates = (JSONObject) json.getJSONArray("rates").get(0);
			rate = jsonRates.getDouble("mid");
		}
		catch (Exception e)
		{
			// TODO: Catch JSONExceptions instead of the generic Exception
		}

		return rate;
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
			// TODO: Handle MalformedURLException and IOException!
			//System.out.println(e.toString());
		}
		
		return sb.toString();
	}
	
	public String getCountryCurrency()
	{
		return countryCurrency;
	}
	
	public String getCurrencyToCompareWith()
	{
		return currencyToCompareWith;
	}
}  
