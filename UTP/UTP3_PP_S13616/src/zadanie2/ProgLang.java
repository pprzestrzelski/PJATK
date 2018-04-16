package zadanie2;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;
import java.util.*;


public class ProgLang {

	
	private Map<Lang, Set<Prog>> lpmap;
	private Map<Prog, Set<Lang>> plmap;
	
	
	ProgLang(String nazwaPliku) throws IOException {
		
		// TreeMap instead of LinkedHashMap?
		lpmap = new LinkedHashMap<>();
		plmap = new LinkedHashMap<>();
		
		Scanner sc = new Scanner(new File(nazwaPliku));
		
		// Read line by line next languages + programmers
		while(sc.hasNextLine()) {
			
			// Split (or scan) line, delimit it with '\t' and read language name
			// and its programmers
			String txt = sc.nextLine();
			Scanner spliter = new Scanner(txt).useDelimiter("[\t]");
			
			Lang l = new Lang(spliter.next());
			Set<Prog> p = new LinkedHashSet<Prog>();
			while(spliter.hasNext()) {
				Prog prog = new Prog(spliter.next());
				plmap.putIfAbsent(prog, new LinkedHashSet<Lang>());
				plmap.get(prog).add(l);
				p.add(prog);
			}
			lpmap.put(l, p);
			spliter.close();
		}
		sc.close();
	}
	
	
	public Map<Lang, Set<Prog>> getLangsMap() {
		return lpmap;
	}
	
	
	public Map<Prog, Set<Lang>> getProgsMap() {
		return plmap;
	}
	
	
	public Map<Lang, Set<Prog>> getLangsMapSortedByNumOfProgs() {
		return sorted(lpmap, (o1, o2) -> {
			return o2.getValue().size() - o1.getValue().size();
		});
	}
	
	
	public Map<Prog, Set<Lang>> getProgsMapSortedByNumOfLangs() {
		return sorted(plmap, (o1, o2) -> {
				int res = o2.getValue().size() - o1.getValue().size();
				return res != 0 ? res : o1.getKey().getSurname().compareTo(o2.getKey().getSurname());			
		});
	}
	
	
	public Map<Prog, Set<Lang>> getProgsMapForNumOfLangsGreaterThan(int n) {
		return filtered(plmap, e -> e.getValue().size() > n);
	}
	
	
	public <K, V> Map<K, V> sorted(Map<K, V> map, Comparator<Map.Entry<K, V>> comp) {
		
		// Convert map to a list of map.entries (map -> list)
		List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
		
		// Sort
		Collections.sort(list, comp);
		
		// Backward conversion (list -> map)
		Map<K, V> sortedMap = new LinkedHashMap<>();
		for(Map.Entry<K, V> e : list)
			sortedMap.put(e.getKey(), e.getValue());
		
		return sortedMap;
	}
	
	
	public <K, V> Map<K, V> filtered(Map<K, V> map, Predicate<Map.Entry<K, V>> filter) {
		
		// Conversion from map to a list of map.entries
		List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
		
		// Create an output map, filter and add filtered elements to the output map.
		Map<K, V> filteredMap = new LinkedHashMap<>();
		for(Map.Entry<K, V> e : list) {
			if(filter.test(e))
				filteredMap.put(e.getKey(), e.getValue());
		}
		
		return filteredMap;
	}
	
}
