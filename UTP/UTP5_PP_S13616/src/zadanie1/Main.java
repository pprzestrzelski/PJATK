/**
 *
 *  @author Przestrzelski Paweł S13616
 *
 */

package zadanie1;

import javax.swing.SwingUtilities;

public class Main {

  public static void main(String[] args) {
	  
	  SwingUtilities.invokeLater( () -> { 
		  new TaskList();
	  });
	  
  }
}
