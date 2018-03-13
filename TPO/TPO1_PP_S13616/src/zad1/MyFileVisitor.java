package zad1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static java.nio.file.FileVisitResult.*;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class MyFileVisitor implements FileVisitor<Path> {
	
	private PathMatcher pmatcher;
	private List<Path> flist;
	private String resultFileName, sep, os;
	private int sepSize;
	private Charset inpcs, outcs;
	private FileChannel inpch, outch;
	EnumSet<StandardOpenOption> outOpts;
	
	public MyFileVisitor(String fname)
	{
		this.resultFileName = fname;
	}
	
	public void init() throws IOException
	{
		this.flist = new ArrayList<>();
		this.pmatcher = FileSystems.getDefault().getPathMatcher("glob:*.txt");
		
		inpcs = Charset.forName("Cp1250");	// wejsciowa strona kodowa
		outcs = Charset.forName("UTF-8");	// wyjsciowa strona kodowa
		outOpts = EnumSet.of(CREATE, WRITE);
		outch = FileChannel.open(Paths.get(resultFileName), outOpts);
		
		sep = System.getProperty("line.separator");
		os = System.getProperty("os.name");
		sepSize = os.toLowerCase().contains("win") ? 2 : 1;
	}

	public void copyData() throws IOException
	{
		for (Path filePath : flist)
		{
			inpch = FileChannel.open(filePath, READ);
			ByteBuffer buffer = ByteBuffer.allocate((int) inpch.size() + sepSize);
			
			// Should be:
			// 	int n = inChannel.read(buffer);
			// 	if (n != (int) inChannel.size()) { System.out.println("ERROR"); return; }
			// and here we go just with...
			inpch.read(buffer);
			buffer.put(sep.getBytes());
			buffer.flip();
			
			CharBuffer cb = inpcs.decode(buffer);
			buffer = outcs.encode(cb);
			outch.write(buffer);
			
			inpch.close();
		}
	}
	
	public void cleanup() throws IOException
	{
		outch.close();
	}
	
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) 
	{
		// Add found file
		if(attrs.isRegularFile() && pmatcher.matches(file.getFileName()))
			flist.add(file);		
		return CONTINUE;
	}
	
	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) 
	{
		//System.out.println("Leaving dir: " + dir);
		return CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) 
	{
		//System.out.println("Entering dir: " + dir);
		return CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) 
	{
		// System.err.pritnln("Failed to visit file: " + file + " - " + exc);
		return CONTINUE;
	}
}
