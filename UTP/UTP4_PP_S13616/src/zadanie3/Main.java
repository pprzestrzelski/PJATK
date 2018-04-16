/**
 *
 *  @author Przestrzelski Paweł S13616
 *
 */

package zadanie3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
	Map<String, List<String>> anagrams = null;
	  try {
		  anagrams = new BufferedReader(new InputStreamReader(new URL("http://www.puzzlers.org/pub/wordlists/unixdict.txt").openStream()))
				  	.lines().collect(Collectors.groupingBy(w -> {char[] tab = w.toCharArray();
																 Arrays.sort(tab);
																 return new String(tab);})); 
		  
		  int longest = anagrams.entrySet().stream()
				  				.max((e1, e2) -> Integer.compare(e1.getValue().size(), e2.getValue().size()))
				  				.get().getValue().size();
		  
		  anagrams.entrySet().stream()
  		  		  .filter(v -> v.getValue().size() == longest)
  		  		  .forEach(v -> System.out.println(v.getKey().join(" ", v.getValue())));
	  } catch(IOException exc) {}
	}
}