<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.apache.commons.io.output.*" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
	<title>Add Script</title>
    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/simple-sidebar.css" rel="stylesheet">
    <style>
    	.rounded-border{
    		border-radius:15px;
    	}
    	.host-wrapper-top{
    		margin-top:20px;
    	}
    	.host-wrapper-left{
    		margin-left:5px;
    	}
    	.text-width{
    		width:50%;
    	}
    	.vertical-center{
    		vertical-align:middle;
    	}
		.btn-file {
		  position: relative;
		  overflow: hidden;
		}
		.btn-file input[type=file] {
		  position: absolute;
		  top: 0;
		  right: 0;
		  min-width: 100%;
		  min-height: 100%;
		  font-size: 100px;
		  text-align: right;
		  filter: alpha(opacity=0);
		  opacity: 0;
		  background: red;
		  cursor: inherit;
		  display: block;
		}
		input[readonly] {
		  background-color: white !important;
		  cursor: text !important;
		}    	
    	
    </style>

</head>

<body>

    <div id="wrapper">

        <!-- Sidebar -->
        <div id="sidebar-wrapper">
            <ul class="sidebar-nav">
                <li class="sidebar-brand">
                    <a href="#">
                        Appium Menu
                    </a>
                </li>
                <li>
                    <a href="AddClient.jsp">Add Host</a>
                </li>
                <li>
                    <a href="Scripts.jsp">Scripts</a>
                </li>
                <li>
                    <a href="#">Add Devices</a>
                </li>
                <li>
                    <a href="#">Events</a>
                </li>
                <li>
                    <a href="#">About</a>
                </li>
                <li>
                    <a href="#">Services</a>
                </li>
                <li>
                    <a href="#">Contact</a>
                </li>
            </ul>
        </div>
        <!-- /#sidebar-wrapper -->

        <!-- Page Content -->
        <div id="page-content-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-8">
                        <a href="#menu-toggle" class="btn btn-default" id="menu-toggle">Menu</a>
                    </div>
                </div>
                </br>
                </br>
                
				<% 
				String appName="",packgName="",scriptPath="", apkPath="",scriptFile="",apkFile="";
				try{
					int scriptId = Integer.parseInt(request.getParameter("script_id"));
					
					
					Class.forName("org.sqlite.JDBC");
				    Connection c = DriverManager.getConnection("jdbc:sqlite:Scripts.db");
	                Statement stat = c.createStatement();
	 				
	                ResultSet rs = stat.executeQuery("select * from Scripts where ScriptId="+scriptId+";");
	                
	                while (rs.next()) {
	                	appName = rs.getString(2);
	                	packgName = rs.getString(3);
	                	scriptPath = rs.getString(4);
	                	scriptFile = scriptPath.substring(scriptPath.lastIndexOf('\\')+1);
	                	apkPath = rs.getString(5);
	                	apkFile = apkFile.substring(apkPath.lastIndexOf('\\')+1);
	                	out.println(apkFile);
	                	out.println(apkFile);
	                }
					
				}catch(Exception e){
					
				}
				%>                
                
                <form class="form-group">
				    <div class="form-group">
				    
				      <label for="txtScriptName">Applicatioon Name:</label>
				      <div class="row">
				      <div class="col-sm-6">
				      <input type="text" class="form-control rounded-border" value="<%=appName%>" id="txtScriptName" placeholder="Enter Application Name">
				      </div>
				      </div>
				    </div>
				    <div class="form-group">
				    
				      <label for="txtPakgName">Package Name:</label>
				      <div class="row">
				      <div class="col-sm-6">
				      <input type="text" class="form-control rounded-border" value="<%=packgName%>" id="txtPakgName" placeholder="Enter Package Name">
				      </div>
				      </div>
				    </div>				    
				    
				    <div class="form-group">
				      <label for="txtClassName">Class Name:</label>
				      <div class="row">
				        <div class="col-lg-6 col-sm-6 col-12">
				            <div class="input-group">
				                <span class="input-group-btn">
				                    <span class="btn btn-primary btn-file">
				                        Browse&hellip; <input type="file" id="fileClassName">
				                    </span>
				                </span>
				                <input type="text" id="txtClassName" text="<%=scriptFile %>" placeholder="Select Script Class File" class="form-control rounded-border" readonly>
				            </div>
				        </div>	
				      </div>
				    </div>  
				    <div class="form-group">
				      <label for="txtApkName">APK File Name</label>
				      <div class="row">
				        <div class="col-lg-6 col-sm-6 col-12">
				            <div class="input-group">
				                <span class="input-group-btn">
				                    <span class="btn btn-primary btn-file">
				                        Browse&hellip; <input type="file" id="fileApkName">
				                    </span>
				                </span>
				                <input type="text" id="txtAPKName" text="<%=apkFile %>" placeholder="Select APK File" class="form-control rounded-border" readonly>
				            </div>
				        </div>	
				      </div>
				    </div>  
				    <br/>
				    <div class="row host-wrapper-left">
				    <button type="button" class="btn btn-primary col-sm-2" onClick="saveChanges()" id="btnSaveBut">Save Changes</button> &nbsp;
				    <div class="col-sm-1"></div>
					<button type="button" class="btn btn-primary col-sm-2" onClick="fnReset()" id="btnReset">Reset</button> &nbsp;
				    </div>	
				    <div class="host-wrapper-left" style="margin-top:10px" id="lblTestMsg"></div>	
				    </br>			    
				    		           	
                </form>
                
            </div>
        </div>
        <!-- /#page-content-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

    <!-- Menu Toggle Script -->
    <script>
    
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
     
    $(document).on('change', '.btn-file :file', function() {
    	  var input = $(this),
    	      numFiles = input.get(0).files ? input.get(0).files.length : 1,
    	      label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
    	  input.trigger('fileselect', [numFiles, label]);
    	});

    	$(document).ready( function() {
    	    $('.btn-file :file').on('fileselect', function(event, numFiles, label) {
    	        
    	        var input = $(this).parents('.input-group').find(':text'),
    	            log = numFiles > 1 ? numFiles + ' files selected' : label;
    	        
    	        if( input.length ) {
    	            input.val(log);
    	        } else {
    	            if( log ) alert(log);
    	        }
    	        
    	    });
    	    $("#txtClassName").val('<%=scriptFile%>');
		    $("#txtAPKName").val('<%=apkFile%>');
    	});   
    	
    	function fnReset(){
		    $("#fileClassName").val('');
		    $("#fileApkName").val('');
		    $("#txtClassName").val('');
		    $("#txtAPKName").val('');
		    $("#txtPakgName").val('');
    	}
    	
        function saveChanges(){
        	
        	btnSaveBut.innerHTML = 'Uploading...';
        	btnSaveBut.disables = true;
        	document.getElementById("lblTestMsg").innerHTML = "";
        	
        	// Create a new FormData object.
        	var formData = new FormData();
        	
        	var scriptName = document.getElementById('txtScriptName').value;
        	var packageName = document.getElementById('txtPakgName').value;
        	
        	formData.append('txtScriptName',scriptName);
        	formData.append('txtPakgName',packageName);
        	
        	var fileSelect = document.getElementById('fileClassName');
        	var files = fileSelect.files;
        	
        	// Loop through each of the selected files.
        	for (var i = 0; i < files.length; i++) {
        	  var file = files[i];				
        	  // Add the file to the request.	  
        	  
			  //if (!file.type === 'application/java-vm') {
			  if (file.name.match('\.class')) {					  
				  formData.append('ClassFile', file, file.name);
			  }            	  
        	  
        	}
			  
			  
        	var fileSelect1 = document.getElementById('fileApkName');
        	var files1 = fileSelect1.files;
        	
        	// Loop through each of the selected files.
        	for (var i = 0; i < files1.length; i++) {
        	  var file = files1[i];
        	  // Add the file to the request.
        	  
			  //if (!file.type === 'application/vnd.android.package-archive') {
			  if (file.name.match('\.apk')) {				  
				  formData.append('APKFile', file, file.name);
			  }        	  
        	}
        				  
        	if(scriptName==null || packageName==null || files.length == 0 || files1.length == 0){
        		document.getElementById("lblTestMsg").innerHTML = "No fields to be left blank";
        		
            	btnSaveBut.innerHTML = 'Save Changes';
            	btnSaveBut.disables = true;        		
        		return;
        	}			  
        	
        	if (window.XMLHttpRequest)
    		  {// code for IE7+, Firefox, Chrome, Opera, Safari
    		  xmlhttp=new XMLHttpRequest();
    		  }
    		else
    		  {// code for IE6, IE5
    		  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    		  }
    		  
    		xmlhttp.onreadystatechange=function()
    		  {
    		  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    		    {
	    		    //alert("Response");
	    		    document.getElementById("lblTestMsg").innerHTML =xmlhttp.responseText;
	    		    if(xmlhttp.responseText.indexOf('Successfully') > -1){
		    		    document.getElementById("txtScriptName").value = "";
		    		    $("#fileClassName").val('');
		    		    $("#fileApkName").val('');
		    		    $("#txtClassName").val('');
		    		    $("#txtAPKName").val('');
		    		    $("#txtPakgName").val('');
		    		    
	    		    }
	    		    btnSaveBut.innerHTML = 'Save Changes';
    		    }
    		}
    		xmlhttp.open("POST","UploadFiles",true);
    		xmlhttp.send(formData);		  
    		  
        }    
    </script>

</body>

</html>
