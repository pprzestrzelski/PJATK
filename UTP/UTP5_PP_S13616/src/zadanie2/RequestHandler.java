package zadanie2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Callable;

public class RequestHandler implements Callable<String> {

	private Socket connection = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	
	public RequestHandler(Socket connection) {
		this.connection = connection;
		try {
			// Odpowiednia obsluga danych przychodzacych i wychodzacych
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			out = new PrintWriter(connection.getOutputStream(), true);
		} catch (Exception exc) {
			exc.printStackTrace();
			try {
				connection.close();
				in.close();
				out.close();
			} catch (Exception exc2) {
				exc2.printStackTrace();
			}
		}
	}

	@Override
	public String call() throws Exception {
		String odp = "";
		try {
			// Obsluga zapytan dla zlecenia
			String line = in.readLine(); // lub w petli for(..)
			
			odp = "ODP: " + line.toUpperCase();
			out.println(odp);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				connection = null;
				in.close();
				out.close();
			} catch (Exception e) {}
		}
		return "Do hosta -> " + odp;
	}

}
