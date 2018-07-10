import java.io.IOException;

import java.io.PrintStream;

// TODO: - added flexible output stream
//		 - added separator's specification

// STILL TODO: find repeating code?
public class CsvWriter 
{
	private PrintStream os;
	private char separator;
	
	public CsvWriter(char separator, PrintStream os) 
	{
		this.separator = separator;
		this.os = os;
	}
	
	public void write(String[][] lines) 
	{
		try
		{
			for (int i = 0; i < lines.length; i++) writeLine(lines[i]);
		}
		catch (IOException ioe)
		{
			//
		}
	}

	private void writeLine(String[] fields) throws IOException 
	{
		if (fields.length == 0)
		{
			// TODO: on the other hand one can use OutputStrem and use it as follows:
			//		 os.write(newLineSign);
			os.println();
		}
		else 
		{
			writeField(fields[0]);
			for (int i = 1; i < fields.length; i++) 
			{
				os.print(separator);
				writeField(fields[i]);
			}
			os.println();
		}
	}

	private void writeField(String field) throws IOException 
	{
		if (field.indexOf(separator) != -1 || field.indexOf('\"') != -1)
			writeQuoted(field);
		else
		{
			os.print(field);
		}
	}

	private void writeQuoted(String field) throws IOException 
	{
		os.print('\"');
		for (int i = 0; i < field.length(); i++) {
			char c = field.charAt(i);
			if (c == '\"')
			{
				os.print("\"\"");
			}
			else
				os.print(c);
		}
		os.print('\"');
	}
}