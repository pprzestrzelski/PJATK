package zadanie2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;

public class Futil {

	public static void processDir(String dirName, String resultFileName) {
		
		Predicate<Path> isFile = Files::isRegularFile;
		Predicate<Path> isTxt = p -> p.toString().endsWith(".txt");
		
		try {
			Files.walk(Paths.get(dirName))		// 'walk' zwraca strumien reprezentujacy drzewo katalogowe
				 .filter(isFile.and(isTxt))
				 .forEach(f -> {
					 Futil.copyData(f, resultFileName);
				 });
		} catch (IOException e) {
			// System.out.println();
		}
		
	}
	
	public static void copyData(Path file, String outputFile) {
		BufferedWriter out = null;
		BufferedReader in = null;
		try {
			
			FileOutputStream fos = new FileOutputStream(outputFile, true);
			out = new BufferedWriter(new OutputStreamWriter(fos, Charset.forName("UTF8")));
			FileInputStream fis = new FileInputStream(file.toString());		
			in = new BufferedReader(new InputStreamReader(fis, Charset.forName("Cp1250")));
				
			String line;
			while((line = in.readLine()) != null) {
				out.write(line);
				out.newLine();
				out.flush();
			}
			
		} catch (FileNotFoundException ffe) {} 
		catch (IOException ioe) {}
		finally {
			try {
				in.close();
				out.close();
			} catch (IOException exc) {
				
			}
		}
	}

}
