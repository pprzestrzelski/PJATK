package zadanie2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class Database {
	
	private final static String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private final static Integer columnNumber = 7;
	private String usr = "s13616";
	private String pass = "oracle12";

	private TravelData td;
	private Connection conn;
	private Statement st; 
	private JFrame frame;
	private JTable table;
	private DefaultTableModel tmodel;
	private ButtonGroup bgroup;
	
	public Database(String url, TravelData travelData) {
		
		 this.td = travelData;
		 try {
			 
			//Locale backup = Locale.getDefault();
			Locale.setDefault(Locale.US);
			Class.forName(ORACLE_DRIVER);
			conn = DriverManager.getConnection(url, usr, pass);
			this.st = conn.createStatement(); 
			//Locale.setDefault(backup);
			
		} catch (ClassNotFoundException | SQLException e) {
			
			// =====================================================================================
			// W przypadku BD Oracle PJATK aplikacja wyrzuci w tym momencie blad/zakonczy dzialanie;
			// nalezy polaczyc sie z siecia PJATK poprzez VPN.
			// =====================================================================================
			
			System.exit(1);
		}
	}
	
	public void create() {
		
		 try {
			 
			 String createTable = "CREATE TABLE Travel ( " +
	                 "    IdTravel  INT,      " +
	                 "    Country VARCHAR(50), " +
	                 "    DateFrom DATE, " +
	                 "    DateTo DATE, " +
	                 "    Place VARCHAR(20), " +
	                 "    Price BINARY_DOUBLE, " +
	                 "    Currency VARCHAR(3)" +
	                 "	  )";

			 st.executeUpdate(createTable);
			 String insert = "INSERT INTO Travel (IdTravel, Country, DateFrom, DateTo, Place, Price, Currency) VALUES (?,?,?,?,?,?,?)";
			 PreparedStatement ps = conn.prepareStatement(insert);
			 
			 List<Travel> data = new LinkedList<>();
			 data.addAll(td.getOffersList());
			 
			 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			 int i = 1;
			 for (Travel offer : data) {
				 
				 ps.setInt(1, i);
				 ps.setString(2, offer.getCountry());
				 ps.setDate(3, Date.valueOf( formatter.format(offer.getFrom())));
				 ps.setDate(4, Date.valueOf( formatter.format(offer.getTo())));
				 ps.setString(5, offer.getPlace());
				 ps.setDouble(6, offer.getPrice());
				 ps.setString(7, offer.getCurrency());
				 ps.executeUpdate();
				 ++i;
				 
			 }
		
		} catch(SQLException e) {
			
			//System.out.println(e.getCause());
			
		}
		
	}
	
	
	public void showGui() {
		
		frame = new JFrame("Travel agency data base");
		tmodel = new DefaultTableModel();
		table = new JTable(tmodel);
		JScrollPane scrollPanel = new JScrollPane(table);
		frame.add(scrollPanel, BorderLayout.CENTER);
		bgroup = new ButtonGroup();
	
		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JRadioButton rb_en = new JRadioButton("en_GB");
		rb_en.setActionCommand("en_GB");
		JRadioButton rb_pl = new JRadioButton("pl_PL");
		rb_pl.setActionCommand("pl_PL");
		JRadioButton rb_de = new JRadioButton("de_DE");
		rb_de.setActionCommand("de_DE");

		rb_en.setSelected(true);
		
		ActionListener language = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String loc = bgroup.getSelection().getActionCommand();
				updateTable(loc, true);
			}
			
		};
		
		rb_en.addActionListener(language);
		rb_pl.addActionListener(language);
		rb_de.addActionListener(language);
		
		bgroup.add(rb_pl);
		bgroup.add(rb_en);
		bgroup.add(rb_de);

	    radioPanel.add(rb_pl);
	    radioPanel.add(rb_en);
	    radioPanel.add(rb_de);
	    
	    frame.add(radioPanel, BorderLayout.SOUTH);

	    updateTable("en_GB", false);
		
	    frame.setPreferredSize(new Dimension(700, 200));
	    frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	
	
	public void updateTable(String loc, boolean exists) {
		
		String[] de_labels = {"ID", "Staat", "Von", "Zu", "Aufenthaltsort", "Preis", "Wahrung"}; 
		String[] pl_labels = {"ID", "Państwo", "Od", "Do", "Miejsce", "Cena", "Waluta"};
		String[] default_labels = {"ID", "Country", "From", "To", "Place", "Price", "Currency"};
		
		try {
			
			String sql = "SELECT * FROM TRAVEL";
			ResultSet rs = st.executeQuery(sql);

			String cNames[] = new String[columnNumber];
			switch (loc.substring(0, 2)) {
				case "de":
					for(int i = 0; i < de_labels.length; ++i) 
						cNames[i] = de_labels[i];
					break;
				case "pl":					
					for(int i = 0; i < pl_labels.length; ++i) 
						cNames[i] = pl_labels[i];
					break;
				default:
					for(int i = 0; i < default_labels.length; ++i) 
						cNames[i] = default_labels[i];
					break;
			}

			if (!exists) {
				
				for (String columnName : cNames) {
					tmodel.addColumn(columnName);
				}
				
				table.setModel(tmodel);
				frame.add(table.getTableHeader(), BorderLayout.NORTH);
				
			} else {
				
				Enumeration<TableColumn> enumer = table.getColumnModel().getColumns();
                for(int i = 0; enumer.hasMoreElements(); ++i){
                    enumer.nextElement().setHeaderValue(cNames[i]);
                }
                
                table.getTableHeader().repaint();
                
			}
			
			String[] rowValues = new String[columnNumber];

			while (rs.next()) {

				Integer id = rs.getInt(1);
				String country = rs.getString(2);
				Date dFrom = rs.getDate(3);
				Date dTo = rs.getDate(4);
				String place = rs.getString(5);
				Double price = rs.getDouble(6);
				String curr = rs.getString(7);
				
				Travel off = new Travel(country, dFrom, dTo, place, price, curr);
				rowValues = TravelData.getOffer(id, off, loc, "yyyy-MM-dd");
				
				if (!exists) {
					
					tmodel.addRow(rowValues);
					table.setModel(tmodel);
					
				} else {
					
					for(int i = 0; i < columnNumber; ++i)
						tmodel.setValueAt(rowValues[i], id-1, i);
					
				}
			}
			
			table.repaint();
			frame.repaint();
			
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}	
	
}
