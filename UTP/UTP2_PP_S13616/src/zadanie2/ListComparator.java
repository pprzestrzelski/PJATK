/**
 *
 *  @author Przestrzelski Paweł S13616
 *
 */

package zadanie2;

import java.util.Comparator;
import java.util.List;

public class ListComparator implements Comparator<List<String>> {

	@Override
	public int compare(List<String> v1, List<String> v2) {
		if(v1.size() > v2.size()) {
			return -1;
		}
		else if(v1.size() < v2.size()) {
			return 1;
		}
		else{
			return v1.get(0).compareTo(v2.get(0));
		}
	}

}

