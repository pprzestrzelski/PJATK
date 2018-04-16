/**
 *
 *  @author Przestrzelski Paweł S13616
 *
 */

package zadanie3;

@FunctionalInterface
public interface Mapper<T, S> {
	public S map(T e);
}  
