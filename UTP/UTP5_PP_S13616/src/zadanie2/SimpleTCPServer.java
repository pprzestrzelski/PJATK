package zadanie2;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SimpleTCPServer {
	
	private ServerSocket ss = null;
	private ExecutorService es = null;
	private List<Future<String>> results = null;
	
	public SimpleTCPServer(ServerSocket ss) {
		this.ss = ss;
		this.es = Executors.newCachedThreadPool();
		this.results = new ArrayList<>();
		System.out.println("Serwer rozpoczal dzialanie.");
		serviceIncomingConnections();
	}

	private void serviceIncomingConnections() {
		boolean serverIsRunning = true;
		while(serverIsRunning) {
			try {
				System.out.println("Oczekuje na nowe polaczenia...");
				Socket newConnection = ss.accept();
				System.out.println("Nawiazano nowe polaczenie z hostem:" + 
									newConnection.getRemoteSocketAddress());
				
				// Start nowego watku obslugi zlecen
				RequestHandler tmp = new RequestHandler(newConnection);
				Future<String> fut = es.submit(tmp);
				results.add(fut);
			} catch (Exception exc1) {
				exc1.printStackTrace();
			}
		}
		// Jesli wywolano zakonczenie pracy serwera to zamknij socket.
		try {
			ss.close();
		} catch (Exception exc2) {
			exc2.printStackTrace();
		}
	}

	public String cancelRequest(int i) {
		String msg = "";
		if(i > results.size() || i < 0)
			return msg = "Niewlasciwa wartosc argumentu funkcji.";
		
		Future<String> tmp = results.get(i);
		if(tmp.isCancelled())
			msg = "Zlecenie '" + i + "' przerwano wczesniej...";
		
		tmp.cancel(true);
		msg = "Wykonywanie zlecenia zostalo przerwane!";
		
		return msg;
	}
}
