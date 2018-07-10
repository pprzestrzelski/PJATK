package zad1;

import java.net.MalformedURLException;

import javax.jms.JMSException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.exolab.jms.administration.AdminConnectionFactory;
import org.exolab.jms.administration.JmsAdminServerIfc; 

public class Main 
{
	private static boolean DEBUG 	= false;
	private static boolean IS_QUEUE = false;
	private static String DEST_NAME = "mytopic";
	private static String URL 	    = "tcp://localhost:3035";
	
	public static void main(String[] args) 
	{
		showInfo();
		initConnection();
		showChatClients();

	}
	
	private static void showChatClients()
	{
		SwingUtilities.invokeLater(() -> new ChatClient("user1", DEST_NAME));
		SwingUtilities.invokeLater(() -> new ChatClient("user2", DEST_NAME));
	}
	
	private static void showInfo()
	{
		String message = "<html>Odpalenie chatu w wykorzystaniem OpenJms wymaga kolejno:<br><br>" 
				+ "1. dodania (jednorazowo) pliku <i>jndi.properties</> z config'a do folderu <i>lib</i> katalogu z Javą<br>"
				+ "2. wystartowania serwera OpenJms "
				+ "np. <i>~\\openjms-0.7.7-beta-1\\bin\\startup.bat (.sh dla Linuxa)</i><br>"
				+ "3. wybrania nazwy domeny (tematu - ang. topic)<br>"
				+ "4. wskazania adresu URL (tcp://localhost:3035)<br><br>"
				+ "Po tej sekwencji wyświetlą się dwa, przykładowe okna dialogowe.<br>"
				+ "Wprowadzenie kolejnych okien dialogowych i wykorzystania kolejek (ang. queue)<br>"
				+ "wymaga jedynie nieznaczych zmian w kodzie zródłowym.<br><br>"
				+ "Autor: Paweł Przestrzelski (s13616)</html>";
		JOptionPane.showMessageDialog(null, message);
		
		String answer = (String) JOptionPane.showInputDialog("Podaj nazwę domeny (tematu):", DEST_NAME);
		if (answer != null) DEST_NAME = answer;
		answer = (String) JOptionPane.showInputDialog("Podaj adres URL:", URL);
		URL = answer;
	}
	
	private static void initConnection()
	{
		try 
		{
			JmsAdminServerIfc admin = AdminConnectionFactory.create(URL);
			if (admin.destinationExists(DEST_NAME))
			{
				if (DEBUG) System.out.println(DEST_NAME + " exists and will be used! ");
			}
			else
			{
				if (!admin.addDestination(DEST_NAME, IS_QUEUE))
				{
					if (DEBUG) 
						System.out.println("Failed to create: " + DEST_NAME + " at server: " + URL);
				}
				else
				{
					if (DEBUG)
						System.out.println("Succesfully added new topic (" + DEST_NAME + ")");
				}
			}
		}
		catch (MalformedURLException | JMSException e) 
		{
			if (DEBUG) System.out.println(e.getMessage());
			if (!DEBUG) JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
			System.exit(1);
		}
	}

}
