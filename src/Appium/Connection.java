package Appium;

import java.io.IOException;

import Appium.AppuimClient;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.StringTokenizer;

/**
 * Servlet implementation class TestConnection
 */
public class Connection extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;
	String screenout;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Connection() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		session = request.getSession();
		String ipAddress = request.getParameter("ipaddress");
		String task = request.getParameter("task");
		System.out.println("Task is : " + task);
		
		if(task.equals("testConnection")){
			System.out.println("Entered Test Connection");
			AppuimClient ac = new AppuimClient();

			
			if(ac.checkConnection(ipAddress)){
			out.println("Connection Successful");
			session.setAttribute("AppiumClient1", ac);
			}
			else{
				out.println("Connection Unsuccessful");
			}
		}
		else if(task.equals("getDevices")){
			session = request.getSession();
			AppuimClient ac = (AppuimClient)session.getAttribute("AppiumClient1");
			try {
				ac.createSocket(ipAddress);
				String output = ac.getDevices();
//				out.println(output);
				if(!output.equals("Error")){
					String[] tokens = output.split(",");
					screenout = "";
					if(Integer.parseInt(tokens[0]) != 0){
						for(int i = 0,j=1 ; i < Integer.parseInt(tokens[0]) ; i++,j=j+2){
							screenout = screenout + "<tr><td>" + tokens[j] + "</td><td>" + tokens[j+1]+"</td></tr>";
						}
						out.println(screenout);
					}
					else{
						out.println("No Devices");
					}
				}
				else
				{
					out.println("Error");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}
