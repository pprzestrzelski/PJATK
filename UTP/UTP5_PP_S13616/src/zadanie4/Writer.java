/**
 *
 *  @author Przestrzelski Paweł S13616
 *
 */

package zadanie4;


public class Writer implements Runnable {
	
	private Author author;
	
	public Writer(Author author){
		this.author = author;
	}

	@Override
	public void run() {
		while(author.isRunning()){
			try{
				String str = author.get();
				System.out.println(str);
			}catch(InterruptedException e){
				return;
			}
		}
		
	}
	
}
