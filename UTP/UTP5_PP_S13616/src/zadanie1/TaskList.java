package zadanie1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TaskList extends JFrame implements ActionListener, ListSelectionListener {
	
	private static final long serialVersionUID = 1321720111149523228L;
	
	private ExecutorService exec;
	private List<Future<Long>> results;
	private int index;
	
	private DefaultListModel<String> dataModel;
	private JList<String> list;
	private JPopupMenu popupMenu;
	private JMenuItem miState, miCancel, miShowResult;
	private JButton buttonAdd;

	TaskList() {
		exec = Executors.newCachedThreadPool();
		results = new ArrayList<>();
		
		dataModel = new DefaultListModel<>();
		list = new JList<>(dataModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(this);
		add(new JScrollPane(list));
		
		popupMenu = new JPopupMenu();
		popupMenu.add(miState = new JMenuItem("Get state"));
		popupMenu.add(miCancel = new JMenuItem("Cancel task"));
		popupMenu.add(miShowResult = new JMenuItem("Show result"));
		
		miState.addActionListener(this);
		miCancel.addActionListener(this);
		miShowResult.addActionListener(this);
		
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)
				   && list.locationToIndex(e.getPoint()) == list.getSelectedIndex()
				   ) {
						popupMenu.show(list, e.getX(), e.getY());
				}
			}
		});
		
		buttonAdd = new JButton("Add new task");
		buttonAdd.addActionListener(this);
		add(buttonAdd, "South");
		
		setSize(300, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		String welcomeMsg = "<html>Zadania pojawiaja sie kolejno na liscie.<br>" + 
							"Nowe zadanie dodajemy przyciskiem <i>'Add task'</i> " + 
							"podajac szukana wartosc ciagu Fibonacciego.<br><br>" +
							"Zapytania wywolujemy <font color=red>Prawym Przyciskiem Myszy (PPM)</font>.<br>" +
							"(przed wykonaniem zapytania nalezy zadanie zaznaczyc przy uzyciu LPM)</html>";
		JOptionPane.showMessageDialog(list, welcomeMsg, "Zadanie 1 - Task List", 1);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String msg = "";
		if(e.getSource() == buttonAdd) {
			try {
				// Mozna zamienic int na String i pozniej parsowac, dzieki czemu
				// bedziemy miec info o wprowadzonej, blednej wartosci.
				int n = Integer.parseInt(
						JOptionPane.showInputDialog("Wprowadz wartosc n dla ciagu Fibonacciego."));
				Fibonacci tmp = new Fibonacci(n);
				Future<Long> fut = exec.submit(tmp);
				results.add(fut);
				dataModel.addElement(tmp.toString());
				msg = "Dodano nowy element do listy.";
			} catch(IllegalArgumentException ae) {
				msg = "Podano niewłaściwa wartosc...";
			}

		} else if (e.getSource() == miState) {
			Future<Long> tmp = results.get(index);
			if(tmp.isCancelled()) {
				msg = "Zadanie anulowano.";
			} else if(tmp.isDone()) {
				msg = "Zadanie policzono.";
			} else if(!tmp.isDone()) {
				msg = "Nie ma jeszcze wyniku...";
			}

		} else if (e.getSource() == miCancel) {
			Future<Long> tmp = results.get(index);
			//String msg = "";
			if(!tmp.isDone() && !tmp.isCancelled()) {
				tmp.cancel(true);
				msg = "Zadanie anulowano.";
			} else if (tmp.isCancelled()){
				msg = "Zadanie anulowano wczesniej...";
			}
			
		} else if (e.getSource() == miShowResult) {
			Future<Long> tmp = results.get(index);
			try {
				if(tmp.isDone()) {
					msg = "Wynik zadania wynosi: " + tmp.get();
				} else if(!tmp.isDone()) {
					msg = "Nie ma jeszcze wyniku...";
				}
			} catch(Exception exc) {
				msg = "Wystapil blad dla zapytania!";
			}

		}
		// Pokaz odpowiedz dowolnego z eventow...
		JOptionPane.showMessageDialog(list, msg);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void valueChanged(ListSelectionEvent e) {
		index = ((JList<String>)e.getSource()).getSelectedIndex();
	}
	
}
