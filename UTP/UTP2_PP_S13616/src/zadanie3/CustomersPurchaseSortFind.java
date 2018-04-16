/**
 *
 *  @author Przestrzelski Paweł S13616
 *
 */

package zadanie3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import static java.util.stream.Collectors.toList;

public class CustomersPurchaseSortFind {
	
	List<Purchase> pList;
	// Create comparators...
	Comparator<Purchase> byID;
	Comparator<Purchase> byName;
	Comparator<Purchase> byCost;
	
	public CustomersPurchaseSortFind() {
		pList = new ArrayList<>(); 
		
		// Definiowanie komparatorów
		byID = Comparator.comparing(Purchase::getClientID);
		byName = Comparator.comparing(Purchase::getName);
		byCost = Comparator.comparing(Purchase::getCost);
	}
	
	public void readFile(String fileName) {
		Scanner scan = null;
		try {
			scan = new Scanner(new File(fileName));
			scan.useDelimiter(";|\n");
			while(scan.hasNext()) {
				pList.add(new Purchase(scan.next(),			// ID
									   scan.next(),			// name
									   scan.next(),			// product
									   Double.valueOf(scan.next()),		// quantity
									   Double.valueOf(scan.next())));	// price
			}
		} catch(FileNotFoundException e) {
			// do nothing
		} catch(Exception exc) {
			// for emergency :)
		} finally {
			scan.close();
		}
		
	}
	
	public void showSortedBy(String key) {
		// First select comparator...
		Comparator<Purchase> by = null;
		if(key.equals("Nazwiska"))
			by = byName.thenComparing(byID);
		else if(key.equals("Koszty"))
			by = byCost.reversed().thenComparing(byID);		// reversed/decreasing order
		
		// then compare :)
		List<Purchase> outList = pList.stream()
									  .sorted(by)
									  .collect(toList());
		
		// and finally spit it out to the screen.
		String out = key + "\n";
		for(Purchase p : outList)
			if(key.equals("Nazwiska"))
				out += p.print() + "\n";
			else if(key.equals("Koszty"))
				out += p.printWithCosts() + "\n";
		System.out.println(out);
	}
	
	public void showPurchaseFor(String clientID) {
		String outStr = "Klient " + clientID + "\n";
		List<Purchase> outList = pList.stream()
					   				  .filter(e -> e.getClientID()
					   				  .equals(clientID))
					   				  .collect(toList());
		for(Purchase p : outList)
			outStr += p.print() + "\n";
		System.out.println(outStr);
	}
	
}
