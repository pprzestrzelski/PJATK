/**
 *
 *  @author Przestrzelski Paweł S13616
 *
 */

package zadanie2;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

  public static void main(String[] args) {
	
	// Dodatkowa obsługa np. przerwania zlecen (poleceni poniżej) 
	// wymagałaby uruchomienia serwera w oddzielnym wątku.
	SimpleTCPServer server = null;
	try {
		server = new SimpleTCPServer(new ServerSocket());
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	// Metoda przerywajaca dzialanie zlecenia o numerze i-tym
	// String odp = server.cancelRequest(1);
	// System.out.println(odp);
	
  }
}
