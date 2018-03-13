package zad1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Futil 
{
	
	static void processDir(String dirName, String outputFile)
	{
		try
		{
			// Create and initialize FileVisitor object
			MyFileVisitor visitor = new MyFileVisitor(outputFile);
			visitor.init();
			
			// Walk through dir and subdirs - populate list of found files
			Files.walkFileTree(Paths.get(dirName), visitor);
			
			// Save content of found files into a single file
			visitor.copyData();
			visitor.cleanup();
		}
		catch (IOException ioe)
		{
			//System.out.println("ERROR: " + e.toString());
		}
		catch (Exception e)
		{
			//System.out.println("Different ERROR: " + e.toString());
		}
	}
	
}
