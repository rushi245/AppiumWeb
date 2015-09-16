<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

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

<title>Insert title here</title>
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
                <div class ="row">
                	<button type="button" class="btn btn-primary col-sm-2" onClick="newScript()" id="btnNewBut">New Script</button> &nbsp;
                </div>
                </br>
                <div class="row">
                	<table class="table">
                		<thead>
                			<tr>
                				<th>Script ID</th>
                				<th>App Name</th>
                				<th>Package Name</th>
                				<th>Script File</th>
                				<th>APK File</th>
                				<th>Modify</th>
                				<th>Delete</th>
                			</tr>
                		</thead>
						<tbody onload="loadScripts">
						
						</tbody>
                	</table>
                </div>
     		</div>
     	</div>
     </div>

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

<script>

function newScript(){
	window.location = "AddScript.jsp";
}

$(window).load(function(){
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
		 	document.getElementsByTagName("tbody")[0].innerHTML = xmlhttp.responseText;    				    
	  }
	  
	}
	xmlhttp.open("GET","ScriptList",true);
	xmlhttp.send(null);
});

function modify(number){
	var url = 'EditScript.jsp';
	var form = $('<form action="' + url + '" method="post">' +
	  '<input type="hidden" name="script_id" value="' + number + '" />' +
	  '</form>');
	$('body').append(form);
	form.submit();	
}

</script>

</body>
</html>