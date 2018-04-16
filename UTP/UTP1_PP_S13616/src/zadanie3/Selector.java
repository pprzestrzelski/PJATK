/**
 *
 *  @author Przestrzelski Paweł S13616
 *
 */

package zadanie3;

@FunctionalInterface
public interface Selector<T> {
	public boolean select(T e);
}  
