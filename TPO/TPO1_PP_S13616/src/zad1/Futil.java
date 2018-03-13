package zad1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.function.Predicate;
import static java.nio.file.StandardOpenOption.*;

public class Futil 
{
	
	static void processDir(String dirName, String outputFile)
	{
		Predicate<Path> isFile = Files::isRegularFile;
		Predicate<Path> isTxt = p -> p.toString().endsWith(".txt");
		
		try
		{
			Files.walk(Paths.get(dirName))
				 .filter(isFile.and(isTxt))
				 .forEach(f -> { Futil.copyData(f, outputFile); });
		}
		catch (IOException e)
		{
			//System.out.println("ERROR:" + e.toString());
		}
	}
	
	static void copyData(Path fromFile, String outputFile)
	{
		EnumSet<StandardOpenOption> outOpts = EnumSet.of(CREATE, APPEND, WRITE);
		FileChannel inpch = null, outch = null;
		Charset inpcs = Charset.forName("Cp1250"),	// wejsciowa strona kodowa
				outcs = Charset.forName("UTF-8");	// wyjsciowa strona kodowa
		
		try
		{
			inpch = FileChannel.open(fromFile, READ);
			outch = FileChannel.open(Paths.get(outputFile), outOpts);

			String sep = System.getProperty("line.separator");
			String os = System.getProperty("os.name");
			int sepSize = os.toLowerCase().contains("win") ? 2 : 1;
			ByteBuffer buffer = ByteBuffer.allocate((int) inpch.size() + sepSize);
			
			// Przyzwoicie powinno byc:
			// 	int n = inChannel.read(buffer);
			// 	if (n != (int) inChannel.size()) { System.out.println("ERROR"); return; }
			// a jest...
			inpch.read(buffer);
			buffer.put(sep.getBytes());
			buffer.flip();
			
			CharBuffer cb = inpcs.decode(buffer);
			buffer = outcs.encode(cb);
			outch.write(buffer);
		}
		catch (IOException e)
		{
			//System.out.println("ERROR:" + e.toString());
		}
		finally
		{
			try
			{
				inpch.close();
				outch.close();
			}
			catch (IOException e)
			{
				//System.out.println("ERROR:" + e.toString());
			}
		}
	}
	
}
