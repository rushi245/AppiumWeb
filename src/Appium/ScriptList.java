package Appium;
import java.sql.*;
import java.sql.Connection;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ScriptList
 */
public class ScriptList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScriptList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		java.io.PrintWriter pw = response.getWriter();
		
	    Connection c = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Scripts.db");
	      
		  Statement stmt = c.createStatement();
		  ResultSet rs = stmt.executeQuery( "SELECT * FROM Scripts order by ScriptId desc" );
		  
	      while ( rs.next() ) {
	    	  pw.write("<tr>");
	    	  pw.write("<td>"+rs.getString(1)+"</td>");
	    	  pw.write("<td>"+rs.getString(2)+"</td>");
	    	  pw.write("<td>"+rs.getString(3)+"</td>");
	    	  pw.write("<td>"+rs.getString(4)+"</td>");
	    	  pw.write("<td>"+rs.getString(5)+"</td>");
	    	  pw.write("<td><a href='#' onclick='modify("+rs.getString(1)+")' class='btn btn-info btn-lg'>");
	    	  pw.write("<span class='glyphicon glyphicon-pencil'></span></a></td>");
	    	  pw.write("<td><a href='#' onclick='delete("+rs.getString(1)+")' class='btn btn-info btn-lg'>");
	    	  pw.write("<span class='glyphicon glyphicon-remove'></span></a></td>");
	    	  pw.write("</tr>");
	    	  
	    	  
	       }	
	      rs.close();
	      c.close();
	      
	    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		      try {
				c.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
