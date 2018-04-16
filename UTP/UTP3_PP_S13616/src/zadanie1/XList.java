package zadanie1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class XList<T> extends ArrayList<T> {
	
	
	private static final long serialVersionUID = 1L;


	public XList() {
		super();
	}
	
	public XList(Collection<T> c) {
		super(c);
	}
	
	
	@SafeVarargs
    public XList(T...args){
        this();
        for(int i=0; i<args.length; i++){
            this.add(args[i]);
        }
    }
    
	
	@SafeVarargs
	public static<T> XList<T> of(T...args) {
		return new XList<T>(args);
	}
	
	
	public static<T> XList<T> of(Collection<T> c) {
		return new XList<T>(c);
	}

	
	public static XList<String> charsOf(String word) {
        char[] tab = word.toCharArray();
        List<String> list = new ArrayList<>();
        for(int i=0; i<tab.length; i++){
            list.add(Character.toString(tab[i]));
        }
        return new XList<String>(list);
	}
	
	
	public static XList<String> tokensOf(String word, String sep) {
		String[] tab = word.split(sep);
		return new XList<String>(tab);
	}
	
	
	public static XList<String> tokensOf(String word) {
		return tokensOf(word, "\\s+");
	}
	
	
	@SuppressWarnings("unchecked")
	public XList<T> union(T...elts) {
		List<T> tmp = new ArrayList<>(this);
	    Collections.addAll(tmp, elts);
		return new XList<T>(tmp);
	}
	
	
	public XList<T> union(Collection<T> c) {
		List<T> tmp = new ArrayList<>(this);
	    tmp.addAll(c);
		return new XList<T>(tmp);
	}
	
	
	public XList<T> diff(Collection<T> c) {
		XList<T> tmp = new XList<>(this);
		tmp.removeAll(c);
		return tmp;
	}
	
	
	public XList<T> unique() {
		Set<T> tmp = new LinkedHashSet<>(this);
		return new XList<T>(tmp);
	}
	
	
	public XList<XList<T>> combine(){
		XList<XList<T>> resultList = new XList<>();
	
		int factor = 1;
		for(T e : this)
			factor *= ((Collection<T>) e).size();
		
		for(int i = 0; i < factor; i++)
			resultList.add(new XList<T>(((List<T>) this.get(0)).get(i%((List<T>) this.get(0)).size())));
		
		int cycle = ((List<T>) this.get(0)).size();
		int len = this.size(); 
		
		for(int i = 1; i < len; i++) {
			int index = -1;
			for(int j = 0; j < factor; j++) {
				if(j % cycle == 0) {
					index++;
				}
				resultList.get(j).add(((List<T>) this.get(i)).get(index % ((List<T>) this.get(i)).size()));
			}
			cycle *= ((List<T>) this.get(i)).size();
		}
		
		return resultList;
	}
	
	
	public String join(){
		return join("");
	}
	
	
	public String join(String sep){
		List<T> list = new XList<T>(this);
		String resultLine = "";
		int i = 0;
		
		for(T a : list){
			if(this.size()-1==i)
				resultLine+= a;
			else
				resultLine+= a + sep;
			i++;
		}
		return resultLine;
	}
	
	
	public <S> XList<S> collect(Function<T, S> func){
		XList<S> result = new XList<S>();
		for(T e : this){
			S newE = func.apply(e);
			result.add(newE);
		}
		return result;
	}	
	
	
	public void forEachWithIndex(BiConsumer<T, Integer> bc){
		for(int i=0; i<size(); i++){
			bc.accept(get(i), i);
		}
	}
	
}
