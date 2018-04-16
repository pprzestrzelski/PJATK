package zadanie1;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class MyFileVisitor implements FileVisitor<Path> {

	private PathMatcher pmatcher;
	
	private BufferedWriter out = null;
	private FileOutputStream fos = null;
	
	private BufferedReader in = null;
	private FileInputStream fis = null;
	
	private List<Path> flist;
	private Charset in_encode = Charset.forName("Cp1250"),
				    out_encode = Charset.forName("UTF8");
	
	
	public MyFileVisitor(String resultFileName) throws IOException {
		this.fos = new FileOutputStream(resultFileName);
		this.flist = new ArrayList<>();
		this.pmatcher = FileSystems.getDefault().getPathMatcher("glob:*.txt");
	}
	
	
	public void copyData() throws IOException {
		out = new BufferedWriter(new OutputStreamWriter(fos, out_encode));
		for(Path f : flist) {
			fis = new FileInputStream(f.toString());		
			in = new BufferedReader(new InputStreamReader(fis, in_encode));
			
			String line;
			while((line = in.readLine()) != null) {
				out.write(line);
				out.newLine();
				out.flush();
			}
			in.close();
		}
		out.close();
	}
	
	
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
		
		// Add found file
		if(attrs.isRegularFile() && pmatcher.matches(file.getFileName()))
			flist.add(file);		
		return CONTINUE;
		
	}
	
	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
		//System.out.println("Wychodze z " + dir);
		return CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
		//System.out.println("Wchodze do " + dir);
		return CONTINUE;
	}


	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) {
		// System.err.pritnln("Nieudana wizytacja pliku: " + file + " - " + exc);
		return CONTINUE;
	}
}
