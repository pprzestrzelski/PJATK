/**
 *
 *  @author Przestrzelski Paweł S13616
 *
 */

package zadanie3;

import java.util.*;

public class ListCreator <T> {
	
	List<T> list;
	
	private ListCreator(final List<T> list) {
		this.list = list;
	}
	
	public static <T> ListCreator<T> collectFrom(final List<T> list) {
		return new ListCreator<>(list);
	}
	
	public ListCreator<T> when(Selector<T> selFun) {
		List<T> out = new ArrayList<>();
		for(T el : this.list) {
			if(selFun.select(el))
				out.add(el);
		}
		list = out;
		return this;
	}
	
	public <S> List<S> mapEvery(Mapper<T, S> mapFun) {
		List<S> out = new ArrayList<>();
		for(T t : this.list) {
			out.add(mapFun.map(t));
		}
		return out;
	}
	
}  
