package zadanie1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Futil {
	
	public static void processDir(String dirName, String resultFileName) {
		try {
			// Initialize class with a result file name
			MyFileVisitor visit = new MyFileVisitor(resultFileName);
			
			// Walk through dir and subdirs
			Files.walkFileTree(Paths.get(dirName), visit);
			
			// Save content of found files into a single file
			visit.copyData();
			
		} catch (IOException exc) {
			// System.out.println(...)
		}
	}
}
