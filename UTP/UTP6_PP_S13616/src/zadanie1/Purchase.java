/**
 *
 *  @author Przestrzelski Paweł S13616
 *
 */

package zadanie1;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

// http://docs.oracle.com/javase/tutorial/javabeans/writing/properties.html

public class Purchase {
	
	private String prod;
	private String data;	// zwiazana (bounded)
	private Double price;	// 		-//-			+ dodatkowo ograniczona (constrained)
	
	private PropertyChangeSupport mPcs = 
			new PropertyChangeSupport(this);
	private VetoableChangeSupport mVcs =
			new VetoableChangeSupport(this);
	
	Purchase() {};
	
	Purchase(String prod, String data, Double price) {
		this.prod = prod;
		this.data = data;
		this.price = price;
	}
	
	public void setData(String newData) {
		String oldData = this.data;
		this.data = newData;
		mPcs.firePropertyChange("data", oldData, newData);
	}
	
	public void setPrice(Double newPrice) throws PropertyVetoException {
		Double oldPrice = this.price;
		mVcs.fireVetoableChange("Price", oldPrice, newPrice);
		this.price = newPrice;
		mPcs.firePropertyChange("price", oldPrice, newPrice);
	}
	
	public String getProd() {
		return this.prod;
	}
	
	public String getData() {
		return this.data;
	}
	
	public Double getPrice() {
		return this.price;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		mPcs.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		mPcs.removePropertyChangeListener(listener);
	}
	
	public void addVetoableChangeListener(VetoableChangeListener listener) {
		mVcs.addVetoableChangeListener(listener);
	}
	
	public void removeVetoableChangeListener(VetoableChangeListener listener) {
		mVcs.removeVetoableChangeListener(listener);
	}
	
	@Override
	public String toString() {
		return "Purchase [prod=" + this.prod + ", data=" + this.data + ", price=" + this.price + "]";		
	}
	
}  
