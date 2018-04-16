/**
 *
 *  @author Przestrzelski Paweł S13616
 *
 */

package zadanie4;

import java.util.concurrent.LinkedBlockingQueue;


public class Author implements Runnable {
	
	private String[] text;
	private boolean isRunning;
	private LinkedBlockingQueue<String> lbq;

	
	public Author(String[] text){
		this.text = text;
		isRunning = true;
		lbq = new LinkedBlockingQueue<>();
	}
	
	
	public boolean isRunning(){
		return isRunning;
	}
	
	
	public String get() throws InterruptedException{
		return lbq.take();
	}
	

	@Override
	public void run() {
		for(String s : text){
			try{
				Thread.sleep(1000);
				lbq.put(s);
			}catch(InterruptedException e){
				return;
			}
		}
		isRunning=false;
	}
	
}  
