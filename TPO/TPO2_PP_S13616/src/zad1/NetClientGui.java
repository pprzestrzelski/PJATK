package zad1;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import org.json.JSONException;
import org.json.JSONObject;

public class NetClientGui extends JFrame 
{
	private static final int MAX_CURRENCY_LENGTH = 3;
	private static final long serialVersionUID = -4802716159375012064L;
	private Service netService = null;
	private JPanel topPanel, bottomPanel;
	private JFXPanel middlePanel;
	private JTextField countryTextField, cityTextField, foreignCurrencyTextField;
	private JLabel countryLabel, cityLabel, foreignCurrencyLabel;
	private JLabel weatherInfoLabel, currencyInfoLabel;
	private final String weatherFormat, currencyFormat;
	private WebEngine webEngine;
	private final int FIELDS_LENGTH = 20;
	private JButton searchBtn = null;

	public NetClientGui(String name)
	{
		super(name);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setupTopArea();		// Search area
		setupMiddleArea();	// WebBrowser
		setupBottomArea();	// Weather, currency et al.
		
		getContentPane().setPreferredSize(new Dimension(800, 600));
		pack();
		setVisible(true);
		
		weatherFormat = new String("<html>Weather information<i><br>Date: %s<br>Temperature: %.1f C<br>Pressure: %.0f hPa<br>Humidity: %.0f %%<br>" +
		   		   				   "Wind speed: %.1f m/s<br>Wind direction: %.1f deg</i></html>");
		currencyFormat = new String("<html>Currency rates<br><i>%s/%s: %.4f<br>" +
		   		   					"PLN from NBP: %.4f</i></html>");
	}
	
	private void setupTopArea()
	{
		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(2, 4, 3, 3));
		
		searchBtn = new JButton("Search");
		searchBtn.addActionListener(runService);
		
		// Top labels
		countryLabel = new JLabel("Country:", JLabel.LEFT);
		cityLabel = new JLabel("City:", JLabel.LEFT);
		foreignCurrencyLabel = new JLabel("Foreign currency to compare:", JLabel.LEFT);
		topPanel.add(new JPanel());	// Blank space
		topPanel.add(countryLabel);
		topPanel.add(cityLabel);
		topPanel.add(foreignCurrencyLabel);
		
		// Bottom control fields and button
		countryTextField = new JTextField(FIELDS_LENGTH);
		countryTextField.setText("Poland");
		cityTextField = new JTextField(FIELDS_LENGTH);
		cityTextField.setText("Warsaw");
		
		foreignCurrencyTextField = new JTextField(FIELDS_LENGTH);
		foreignCurrencyTextField.setText("USD");
		// Limit a text filed with foreign currency to MAX_CURRENCY_LENGTH
		foreignCurrencyTextField.addKeyListener(new KeyAdapter() 
		{
		    public void keyTyped(KeyEvent e) 
		    { 
		        if (foreignCurrencyTextField.getText().length() >= MAX_CURRENCY_LENGTH)
		            e.consume(); 
		    }  
		});
		
		topPanel.add(searchBtn);
		topPanel.add(countryTextField);
		topPanel.add(cityTextField);
		topPanel.add(foreignCurrencyTextField);
		
		add(topPanel, BorderLayout.PAGE_START);
	}
	
	
	private void setupMiddleArea()
	{
		middlePanel = new JFXPanel();
		add(middlePanel, BorderLayout.CENTER);
		
		Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{
				initWebEngine();
			}
		});
	}
	
	// https://stackoverflow.com/questions/20413935/make-javafx-scene-stretch-with-container-frame
	private void initWebEngine()
	{
		AnchorPane anchorPane = new AnchorPane();
		WebView webView = new WebView();

		//Set Layout Constraint
		AnchorPane.setTopAnchor(webView, 0.0);
		AnchorPane.setBottomAnchor(webView, 0.0);
		AnchorPane.setLeftAnchor(webView, 0.0);
		AnchorPane.setRightAnchor(webView, 0.0);

		anchorPane.getChildren().add(webView);
		final Scene scene = new Scene(anchorPane);
		middlePanel.setScene(scene);

		webEngine = webView.getEngine();
		// Load default webpage:
		//webEngine.load("http://www.google.com");
	}
	
	private void setupBottomArea()
	{
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
		
		weatherInfoLabel = new JLabel();
		weatherInfoLabel.setHorizontalAlignment(JLabel.CENTER);
		weatherInfoLabel.setVerticalAlignment(JLabel.TOP);
		
		currencyInfoLabel = new JLabel();
		currencyInfoLabel.setHorizontalAlignment(JLabel.CENTER);
		currencyInfoLabel.setVerticalAlignment(JLabel.TOP);
		
		bottomPanel.add(weatherInfoLabel);
		bottomPanel.add(currencyInfoLabel);
		add(bottomPanel, BorderLayout.PAGE_END);
	}
	
	private ActionListener runService = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			String country = countryTextField.getText();
			String city = cityTextField.getText();
			
			netService = new Service(country);
			String weatherJson = netService.getWeather(city);
			
			showWeatherParameters(weatherJson);
			showCurrencyRates();
						
			Platform.runLater(new Runnable()
			{
				@Override
				public void run()
				{
					webEngine.load("https://en.wikipedia.org/wiki/" + city);
				}
			});
		}
	};
	
	private void showWeatherParameters(String weatherJson)
	{
		JSONObject json = new JSONObject(weatherJson);
		
		try
		{
			// Get date from dt - time in ms since January 1st, 1970 GMT
			double dt = json.getDouble("dt");
			Date weatherDate = new Date((long) dt * 1000);
			
			JSONObject main = json.getJSONObject("main");
			double temp = main.getDouble("temp");
			double pressure = main.getDouble("pressure");
			double humidity = main.getDouble("humidity");
			
			JSONObject wind = json.getJSONObject("wind");
			double windSpeed = wind.getDouble("speed");
			double windDir = wind.getDouble("deg");
			weatherInfoLabel.setText(String.format(weatherFormat, weatherDate.toString(), temp, pressure, 
												   				  humidity, windSpeed, windDir));
		}
		catch (JSONException je)
		{
			//System.out.println(weatherJson);
			weatherInfoLabel.setText("At least one of the weather parameters is missing.");
		}
	}
	
	private void showCurrencyRates()
	{
		double countryToForeignCurrRate = netService.getRateFor(foreignCurrencyTextField.getText());
		double plnToForeignCurrRate = netService.getNBPRate();
		currencyInfoLabel.setText(String.format(currencyFormat, netService.getCountryCurrency().toUpperCase(), 
																netService.getCurrencyToCompareWith().toUpperCase(), 
																countryToForeignCurrRate, 
																plnToForeignCurrRate));
	}
}
