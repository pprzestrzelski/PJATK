package zadanie1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

public class ListCreator<T> {
	
	List<T> list;
	
	private ListCreator(final List<T> list) {
		this.list = list;
	}
	
	public static <T> ListCreator<T> collectFrom(final List<T> list) {
		return new ListCreator<>(list);
	}
	
	public ListCreator<T> when(Predicate<T> filter) {
		List<T> out = new ArrayList<>();
		for(T el : this.list) {
			if(filter.test(el))
				out.add(el);
		}
		list = out;
		return this;
	}
	
	public <S> List<S> mapEvery(Function<T, S> func) {
		List<S> out = new ArrayList<>();
		for(T el : this.list) {
			out.add(func.apply(el));
		}
		return out;
	}
	
}
