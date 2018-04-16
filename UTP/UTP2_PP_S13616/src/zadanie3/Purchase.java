/**
 *
 *  @author Przestrzelski Paweł S13616
 *
 */

package zadanie3;

import java.util.Locale;

public class Purchase {
	
	private String clientID;
	private String name;
	private String product;
	private double quantity;
	private double price;
	private double cost;
	
	public Purchase(String clientID, String name, String product, double quantity, double price) {
		this.clientID = clientID;
		this.name = name;
		this.product = product;
		this.quantity = quantity;
		this.price = price;
		this.cost = quantity * price;
	}
	
	public String print()  {
		return String.format(Locale.US, "%s;%s;%s;%.1f;%.1f", clientID, name, product, quantity, price);
	}
	
	public String printWithCosts() {
		return String.format(Locale.US, "%s (koszt: %.1f)", this.print(), cost);
	}
	
	public String getClientID() {
		return clientID;
	}
	
	public String getName() {
		return name;
	}
	
	public double getCost() {
		return cost;
	}
	
}
