/**
 *
 *  @author Przestrzelski Paweł S13616
 *
 */

package zad1;

import javax.swing.SwingUtilities;

public class Main {
  public static void main(String[] args) {
    Service s = new Service("Poland");
    String weatherJson = s.getWeather("Warsaw");
    Double rate1 = s.getRateFor("USD");
    Double rate2 = s.getNBPRate();
    // ...
    // część uruchamiająca GUI
    SwingUtilities.invokeLater(
    		() -> new NetClientGui("Net service's client")
    );
  }
}
