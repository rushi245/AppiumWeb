package Appium;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;
 


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.sql.*;
import java.sql.Connection;

/**
 * A Java servlet that handles file upload from client.
 *
 * @author www.codejava.net
 */
public class UploadFiles extends HttpServlet {
    private static final long serialVersionUID = 1L;
     
    // location to store file uploaded
    private static final String UPLOAD_DIRECTORY = "upload";
 
    // upload settings
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
    
    String uploadPath;
	Statement stmt = null;
    Connection c = null; 
    
    /**
     * Upon receiving file upload submission, parses the request to read
     * upload data and saves the file on disk.
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	java.io.PrintWriter pw = response.getWriter();
    	
        // checks if the request actually contains upload file
    	ServletContext context = getServletContext();  	    	
    	
    	//File locations stored in web.xml file
    	String scriptlocation = context.getInitParameter("script-folder");
    	String apklocation = context.getInitParameter("apk-folder");      	
    	
    	//Check if request object contains 
        if (!ServletFileUpload.isMultipartContent(request)) {
            // if not, we stop here
            pw.println("Error: Form must has enctype=multipart/form-data.");
            pw.flush();
            return;
        }
        
    	//Load database objects
	    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:Scripts.db");
		      
//		      Query to create table 
//		      stmt = c.createStatement();
//		      String sql = "CREATE TABLE Scripts (" +
//		    		  " ScriptId     INTEGER PRIMARY KEY AUTOINCREMENT, " + 
//		    		  " AppName            Text     NOT NULL UNIQUE, " +
//		    		  " PkgName            Text     NOT NULL UNIQUE, " + 
//		    		  " ScriptFile        Text   NOT NULL UNIQUE, " + 
//		    		  " APKFile        Text  NOT NULL UNIQUE)"; 
//
//		      stmt.executeUpdate(sql);
//		      stmt.close();	   
		      
	    }catch (ClassNotFoundException e){
	    	pw.write("Error Loading Database : "+e.toString());
	    	return;
	    } catch (SQLException e) {
			// TODO Auto-generated catch block
	    	pw.write("Error " + e.toString());
			e.printStackTrace();
			try {
				c.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		}        
        
    	
        // configures upload settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // sets memory threshold - beyond which files are stored in disk
        factory.setSizeThreshold(MEMORY_THRESHOLD);
 
        ServletFileUpload upload = new ServletFileUpload(factory);
         
        // sets maximum size of upload file
        upload.setFileSizeMax(MAX_FILE_SIZE);
         
        // sets maximum size of request (include file + form data)
        upload.setSizeMax(MAX_REQUEST_SIZE);
        
        String scriptPath = "", apkPath = "", scriptName ="", appName="",pkgName ="";
        File storeFile = null;
        FileItem applName,packageName,classFile,apkFile;
        
        // parses the request's content to extract file data
        try{
        @SuppressWarnings("unchecked")
        List<FileItem> formItems = upload.parseRequest(request);    
        	if (formItems != null && formItems.size() > 0) {
        		Object[] fileArray = formItems.toArray();
        		if(fileArray.length == 4){
            		applName = (FileItem)fileArray[0];
            		packageName = (FileItem)fileArray[1];
            		appName = applName.getString();
            		pkgName = packageName.getString();
            		classFile= (FileItem)fileArray[2];
            		apkFile= (FileItem)fileArray[3];    
            		
            		if(classFile.getName().contains(".class") && apkFile.getName().contains(".apk")){
            			scriptPath = scriptlocation + File.separator + classFile.getName();
            			apkPath = apklocation + File.separator + apkFile.getName();
            			
            			File scriptF = new File(scriptPath);
            			File apkF = new File(apkPath);
            			String error = "";
            			stmt = c.createStatement();
            			ResultSet appCheck = stmt.executeQuery("select count(*) from Scripts where AppName = '"+appName+"';");
            			if(appCheck.next())
            			{
            				if(appCheck.getInt(1)!=1) error = error + "Application Name, ";
            			}	
            			ResultSet pkgCheck = stmt.executeQuery("select count(*) from Scripts where AppName = '"+pkgName+"';");
            			if(pkgCheck.next())
            			{
            				if(pkgCheck.getInt(1)!=1) error = error + "Package Name, ";
            			}	            			
            			ResultSet scrpCheck = stmt.executeQuery("select count(*) from Scripts where AppName = '"+scriptPath+"';");
            			if(scrpCheck.next())
            			{
            				if(scrpCheck.getInt(1)!=1)error = error + " Script, ";;
            			}	                 			
            			ResultSet apkCheck = stmt.executeQuery("select count(*) from Scripts where AppName = '"+apkPath+"';");
            			if(apkCheck.next())
            			{
            				if(appCheck.getInt(1)!=1) error = error + "APK File ";;
            			}	 
            			
            			if(error.isEmpty()){
            				pw.write(error + "Already Exists in database");
            				c.close();
            				return;
            			}
            			            			
            			try {
            				stmt = c.createStatement();
            			    String sql = "INSERT INTO Scripts (AppName,PkgName,ScriptFile,APKFile) Values('"+appName+"','"+pkgName+"','"+scriptPath+"','"+apkPath+"');";
            			   	System.out.println(sql);
            				stmt.executeUpdate(sql);
            				c.close();
                			if (!scriptF.exists() && !apkF.exists() ){
    	            			classFile.write(new File(scriptPath));
    	            			apkFile.write(new File(apkPath));
                			}
                			else
                			{
                				pw.write("File already exists on server");
                				c.close();
                				return;
                			}                				
            				
            				pw.write("Files Uploaded Successfully : " +appName );
            			} catch (SQLException e) {
            				// TODO Auto-generated catch block
            				c.close();
            				System.out.println("Error in SQL : " + e.toString());
            				pw.write("Error : " +  e.toString());
            			}	            			
            			
            		}
            		else{
            			pw.write("Upload files with .apk or .class file formats");
            			c.close();
            			return;
            		}
            		
        		}
        		else{
        			pw.write("Improper Amount of Input Objects");
        			c.close();
        			return;
        		}
        	}
        }
        catch(Exception e){
        	pw.write("Error : " + e.toString());
        	try {
				c.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
    	

    	


 
        // creates the directory if it does not exist
//        File uploadDir = new File(scriptlocation);
//        if (!uploadDir.exists()) {
//            uploadDir.mkdir();
//        }
//        
//        uploadDir = new File(apklocation);
//        if (!uploadDir.exists()) {
//            uploadDir.mkdir();
//        }        
 

//        
//        try {
//            String scriptPath = "", apkPath = "", scriptName;
//            File storeFile = null;
//            FileItem scriptN;
//            if (formItems != null && formItems.size() > 0) {
//                // iterates over form's fields
//            	
//            	Object[] fileArray = formItems.toArray();
//            	
//            	if(fileArray.length == 3){
// 
//            		
//            		if(classFile.getName().contains(".class") && apkFile.getName().contains(".apk")){
//            			scriptPath = scriptlocation + File.separator + classFile.getName();
//            			apkPath = apklocation + File.separator + apkFile.getName();
//            			
//            			File scriptF = new File(scriptPath);
//            			File apkF = new File(apkPath);
//            			
//            			stmt = c.createStatement();
//            			ResultSet startrs = stmt.executeQuery("select * from Scripts where ");
//            			
//            			if (!scriptF.exists() && !apkF.exists() ){
//	            			classFile.write(new File(scriptPath));
//	            			apkFile.write(new File(apkPath));
//            			}
//            			else
//            			{
//            				pw.write("File already exists on server");
//            				return;
//            			}
//            			
//            			try {
//            				stmt = c.createStatement();
//            			    String sql = "INSERT INTO Scripts (ScriptName,ScriptFile,APKFile) Values('"+scriptN.getString()+"','"+scriptPath+"','"+apkPath+"');";
//            			    	System.out.println(sql);
//            					stmt.executeUpdate(sql);
//            					pw.write("Files Uploaded Successfully : " +scriptN.getString() );
//            				} catch (SQLException e) {
//            					// TODO Auto-generated catch block
//            					System.out.println("Error in SQL : " + e.toString());
//            				}	            			
//            			
//            		}
//            		else{
//            			pw.write("Please upload files with .class and .apk file format");
//            			return;
//            		}
//            	}
//            	else{
//            		pw.write("Improper Amount of File Objects");
//            		return;
//            	}
//            	
//                for (FileItem item : formItems) {
//                    // processes only fields that are not form fields
//                    if (!item.isFormField()) {
//                        String fileName = new File(item.getName()).getName();
//                        //Add Class file to Scripts folder
//                        if(fileName.contains(".class")){
//                        	uploadPath = scriptlocation;
//                        	scriptPath = uploadPath + File.separator + fileName;
//                            System.out.println(scriptPath);
//                            storeFile = new File(scriptPath);
//                        }
//                      //Add Class file to Scripts folder
//                        else if(fileName.contains(".apk")){
//                        	uploadPath = apklocation;
//                        	apkPath = uploadPath + File.separator + fileName;
//                            System.out.println(apkPath);
//                            storeFile = new File(apkPath);
//                        }
//                        else{
//                        	pw.write("Files should be .class and .apk format");
//                        	return;
//                        }
//                        if(storeFile.exists() && !storeFile.isDirectory()){
//                        	pw.write("File Already Exists : " + fileName + "</br>");
//                        }
//                        else{
//                        	// saves the file on disk
//	                        item.write(storeFile);
//	                        pw.write("File Uploaded : " + fileName + "</br>");
//                        }
//                        
//                    }
//                }
				
                
//            }
//        } catch (Exception ex) {
//        	pw.write( "There was an error: " + ex.getMessage());
//        }
	
    }
}