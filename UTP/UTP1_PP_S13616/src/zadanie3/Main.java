/**
 *
 *  @author Przestrzelski Paweł S13616
 *
 */

package zadanie3;



import java.util.*;

public class Main {
  public Main() {
    List<Integer> src1 = Arrays.asList(1, 7, 9, 11, 12);
    System.out.println(test1(src1));

    List<String> src2 = Arrays.asList("a", "zzzz", "vvvvvvv" );
    System.out.println(test2(src2));
  }

  public List<Integer> test1(List<Integer> src) {
	  
    Selector<Integer> sel = new Selector<Integer>() {
    	
    	@Override
    	public boolean select(Integer e) {
    		return (e < 10);
    	}
    };
    
    Mapper<Integer, Integer> map = new Mapper<Integer, Integer>() {
    	
    	@Override
    	public Integer map(Integer e) {
    		return e + 10;
    	}
    };
    
    return ListCreator.collectFrom(src).when(sel).mapEvery(map);
    
  }

  public List<Integer> test2(List<String> src) {
	  
    Selector<String> sel = new Selector<String>() {
    	
    	@Override
    	public boolean select(String e) {
    		return (e.length() > 3);
    	}
    };
    
    Mapper<String, Integer> map = new Mapper<String, Integer>() {
    	
    	@Override
    	public Integer map(String e) {
    		return e.length() + 10;
    	}
    };
    
    return ListCreator.collectFrom(src).when(sel).mapEvery(map);
    
  }

  public static void main(String[] args) {
    new Main();
  }
}
