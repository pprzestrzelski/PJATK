/**
 *
 *  @author Przestrzelski Paweł S13616
 *
 */

package zadanie2;

import java.util.List;
import java.util.Arrays;

import static java.util.stream.Collectors.toList;


public class Main {

  public static void main(String[] args) {
    // Lista destynacji: port_wylotu port_przylotu cena_EUR 
    List<String> dest = Arrays.asList(
      "bleble bleble 2000",
      "WAW HAV 1200",
      "xxx yyy 789",
      "WAW DPS 2000",
      "WAW HKT 1000"
    );
    double ratePLNvsEUR = 4.30;
    List<String> result = dest.stream()
    						  .filter(n -> n.startsWith("WAW"))
    						  .map(n -> String.format("to %s - price in PLN: %.0f", 
                    		   						  n.split("\\s+")[1], 
                    		   						  Double.valueOf(n.split("\\s+")[2]) * ratePLNvsEUR))
    						  .collect(toList());

    for (String r : result) System.out.println(r);
  }
}
