package zad1;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.json.JSONObject;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class NetClientGui extends JFrame 
{
	private static final long serialVersionUID = -4802716159375012064L;
	private Service netService = null;
	private JPanel topPanel, bottomPanel;
	private JFXPanel middlePanel;
	private JTextField countryTextField, cityTextField;
	private JLabel countryLabel, cityLabel;
	
	private WebEngine webEngine;
	private final int FIELDS_LENGTH = 20;
	private final String ISO8601_FORMAT = "yyyy-MM-dd HH:mm:ss";
	JButton searchBtn = null;

	public NetClientGui(String name)
	{
		super(name);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setupTopArea();		// Search area
		setupMiddleArea();	// WebBrowser
		setupBottomArea();	// Weather, currency et al.
		
		getContentPane().setPreferredSize(new Dimension(800, 665));
		pack();
		setVisible(true);
	}
	
	private void setupTopArea()
	{
		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(2, 3, 3, 3));
		
		searchBtn = new JButton("Search");
		searchBtn.addActionListener(runService);
		topPanel.add(new JPanel());
		
		countryLabel = new JLabel("Country:", JLabel.LEFT);
		cityLabel = new JLabel("City:", JLabel.LEFT);
		topPanel.add(countryLabel);
		topPanel.add(cityLabel);
		
		countryTextField = new JTextField(FIELDS_LENGTH);
		countryTextField.setText("Poland");
		cityTextField = new JTextField(FIELDS_LENGTH);
		cityTextField.setText("Warsaw");
		topPanel.add(searchBtn);
		topPanel.add(countryTextField);
		topPanel.add(cityTextField);
		
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
	
	private void initWebEngine()
	{
		Group group = new Group();
		Scene scene = new Scene(group);
		middlePanel.setScene(scene);

		WebView browser = new WebView();
		group.getChildren().add(browser);
		
		webEngine = browser.getEngine();
		//webEngine.load("http://www.google.com");
	}
	
	private void setupBottomArea()
	{
		bottomPanel = new JPanel();
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
			
			// https://openweathermap.org/current
			//viewBasicWeatherParameters(weatherJson);
			JSONObject json = new JSONObject(weatherJson);
			double dt = json.getDouble("dt");	// dt - time in ms since January 1st, 1970 GMT
			Date weatherDate = new Date((long) dt * 1000);
			double temp = json.getJSONObject("main").getDouble("temp");
			double pressure = json.getJSONObject("main").getDouble("pressure");
			double humidity = json.getJSONObject("main").getDouble("humidity");
			double windSpeed = json.getJSONObject("wind").getDouble("speed");
			double windDir = json.getJSONObject("wind").getDouble("deg");
				
			//System.out.println(weatherDate + " " + temp + " " + pressure + " " 
			//					 + humidity + " " + windSpeed + " " + windDir);
			//System.out.println(netService.getRateFor("USD"));
			//System.out.println(weatherJson);
						
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
}
