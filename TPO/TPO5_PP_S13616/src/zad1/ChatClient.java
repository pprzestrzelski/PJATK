package zad1;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ChatClient extends JFrame implements MessageListener
{
	private static boolean DEBUG = true;
	
	private String clientName;
	private String destinationName;		// topic or queue's name
	
	private JTextArea textArea = null;
	private JTextField textInput = null;
	
	private String factoryName = "ConnectionFactory";	
	private Connection connection = null;
	private Session session = null;
	private MessageConsumer consumer = null;
	private MessageProducer producer = null;

	public ChatClient(String clientName, String destinationName) 
	{
		this.clientName = clientName;
		this.destinationName = destinationName;
		setupConnection(destinationName);
		setupGui();
	}
	
	private void setupConnection(String destinationName)
	{
		Context context = null;
		try 
		{
			context = new InitialContext();		// looks for jndi.config in "JAVA_HOME/lib/"
			//// Otherwise create local properties as follows
			//Properties props = new Properties();
			//props.put(Context.INITIAL_CONTEXT_FACTORY, "org.exolab.jms.jndi.InitialContextFactory");
			//props.put(Context.PROVIDER_URL, "tcp://localhost:3035/");
			// context = new InitialContext(props); 
			
			ConnectionFactory cf = (ConnectionFactory) context.lookup(factoryName);
			Destination dst = (Destination) context.lookup(destinationName);
			connection = cf.createConnection();
			
			// connection.createSession(param1, param2)
			// param1 => false - brak transakcyjnosci
			// param2 => Session.AUTO_ACKNOWLEDGE - automatyczne potwierdzenie
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			consumer = session.createConsumer(dst);
			consumer.setMessageListener(this);
			connection.start();
			producer = session.createProducer(dst);
		} 
		catch (NamingException e) 
		{
			if (DEBUG) System.out.println("ERROR: " + e.getMessage() + " does not exist.");
			System.exit(1);
		}
		catch (JMSException jmse) 
		{
			if (DEBUG) System.out.println("ERROR: " + jmse.getMessage());
			System.exit(2);
		}
	}
	
	private void setupGui()
	{	
		// add all gui components
		textArea = new JTextArea(20, 40);
		textArea.setEditable(false);
		textInput = new JTextField(40);
		textInput.addActionListener(new AbstractAction()
		{
		    @Override
		    public void actionPerformed(ActionEvent e)
		    {
		    	// SEND MESSAGE
		    	TextMessage msg;
				try 
				{
					msg = session.createTextMessage(clientName + ": " + textInput.getText() + "\n");
			    	producer.send(msg);
			        textInput.setText("");
				} 
				catch (JMSException e1) 
				{
					e1.printStackTrace();
				}
		    }
		});
		
		
		this.add(new JScrollPane(textArea), BorderLayout.CENTER);
		this.add(textInput, BorderLayout.SOUTH);
		
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				try
				{
					connection.close();
				}
				catch (JMSException jmse)
				{
					if (DEBUG) System.out.println(jmse.getMessage());
				}
				dispose();
				System.exit(0);
			}
		});
		
		this.setTitle("OpenJMS chat. Client: " + this.clientName + " in the topic: " + destinationName);
		pack();
		this.setVisible(true);
	}

	@Override
	public void onMessage(Message msg) 
	{
		try
		{
			textArea.append(((TextMessage) msg).getText());
		}
		catch (JMSException e)
		{
			if (DEBUG) System.out.println(e.getMessage());
		}
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

}
