/**
 *
 *  @author Przestrzelski Paweł S13616
 *
 */

package zadanie1;

import java.beans.PropertyVetoException;

public class Main {
  public static void main(String[] args) {

    Purchase purch = new Purchase("komputer", "nie ma promocji", 3000.00);
    System.out.println(purch);

    purch.addPropertyChangeListener(e -> {
    	String pname = e.getPropertyName();
    	Object oldv = e.getOldValue();
    	Object newv = e.getNewValue();
    	System.out.println("Change value of: " + pname +
    					   " from: " + oldv +
    					   " to: " + newv);
    });
    
    purch.addVetoableChangeListener(e -> {
    	String pname = e.getPropertyName();
    	Double newv = (Double) e.getNewValue();
    	if(newv < 1000.0)
    		throw new PropertyVetoException(pname + " change to: " + 
    										newv + " not allowed", e);
    });

    try {
      purch.setData("w promocji");
      purch.setPrice(2000.00);
      System.out.println(purch);

      purch.setPrice(500.00);

    } catch (PropertyVetoException exc) {
      System.out.println(exc.getMessage());
    }
    System.out.println(purch);

  }
}
