package zad1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class BooksDbServlet
 */
@WebServlet("/BooksDbServlet")
public class BooksDbServlet extends HttpServlet 
{
	
	private static final long serialVersionUID = 1L;
	public static final String SQL_GET_ALL = "SELECT * FROM Books";
	DataSource dataSource = null;

    public BooksDbServlet() 
    {
        super();
    }
        
    public void init()
    {
    	try
    	{
    		Context context = (Context) new InitialContext().lookup("java:comp/env");
    		dataSource = (DataSource) context.lookup("jdbc/s13616");
    		if (dataSource == null)
    		{
    			throw new ServletException("Unable to reach data source.");
    		}
    	}
    	catch (Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Connection conn = null;
		Statement statement = null;
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>Book search</title></head>");
		out.println("<body>");
		out.println("<h2>The place where all your books are stored.</h2>");
		out.println("<hr>");
		out.println("<form action=\"http://localhost:8080/TPO4_PP_S13616/BooksDbServlet\">" +
					"Title's key word: " +
					"<input type=\"text\" size=\"20\" name=\"word\"> " +
					"<input type=\"submit\" value=\"Find\">" +
					"</form>");
		
		out.println("<table>");
		try 
		{
			conn = dataSource.getConnection();
			statement = conn.createStatement();
			String word = request.getParameter("word");
			String query = SQL_GET_ALL;
			if (word != null && !word.equals(""))
			{
				query += " WHERE Name LIKE '%" + word + "%'";
			}
			
			ResultSet rs = statement.executeQuery(query);
			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			out.println("<tr>");
			for (int i = 1; i <= columns; ++i)
			{
				out.println("<td bgcolor=lightblue>" + metaData.getColumnName(i) + "</td>");
			}
			out.println("</tr>");
			
			while (rs.next())
			{
				out.println("<tr>");
				for (int i = 1; i <= columns; ++i)
				{
					out.println("<td bgcolor=lightgrey>" + rs.getString(i) + "</td>");
				}
				out.println("</tr>");
			}
		}
		catch (SQLException e) { System.out.println(e.getMessage()); }
		finally 
		{
			try 
			{
				if (conn != null) conn.close();				
				if (statement != null) statement.close();
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		out.println("</table>");
		out.println("</body>");
		out.println("</html>");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
