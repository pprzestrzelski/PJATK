package zadanie1;


import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Maybe<T> {
	
	private T content;
	
	public Maybe(){}
	
	public Maybe(T value) {
		content = value;
	}
	
	public static <T> Maybe<T> of(T value) {
		return new Maybe<T>(value);
	}
	
	public void ifPresent(Consumer<? super T> cons) {
		if(isPresent()) {
			cons.accept(content);
		}
	}
	
	public <U> Maybe<U> map(Function<? super T, ? extends U> func) {
		if(isPresent())
			return new Maybe<U>(func.apply(content));
		else
			return new Maybe<U>();
	}
	
	public T get() {
		if(isPresent())
			return content;
		else
			throw new NoSuchElementException("maybe is empty");
	}
	
	public boolean isPresent() {
		if(content != null)
			return true;
		else
			return false;
	}
	
	public T orElse(T defVal) {
		T retVal;
		if(this.isPresent())
			retVal = content;
		else
			retVal = defVal;
		return retVal;
	}
	
	public Maybe<T> filter(Predicate<? super T> pred) {
		if(isPresent() && pred.test(content))
			return this;
		else
			return new Maybe<T>();
	}
	
	@Override
	public String toString() {
		if(isPresent())
			return "Maybe has value " + content.toString();
		else
			return "Maybe is empty";
	}

}
