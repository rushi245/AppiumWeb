<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
	<title>Client Configuration</title>
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
    </style>

</head>

<body>
	<div id="container">
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
                    <a href="AddScript.jsp">Add Script</a>
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
                    <div class="col-lg-12">
                        <a href="#menu-toggle" class="btn btn-default" id="menu-toggle">Menu</a>
                    </div>
                </div>
            </div>

        <!-- User input for host wrapper -->
		<div class="host-wrapper-top"></div>		        
		  <form role="form" id="frmHostDet">
		    <div class="form-group">
		    
		      <label for="txtHostName">Host Name:</label>
		      <div class="row">
		      <div class="col-sm-6">
		      <input type="text" class="form-control rounded-border" id="txtHostName" placeholder="Enter Host Name">
		      </div>
		      <div class="col-sm-6">
		      <img style="height:20px;width:20px" id="imgName" src="Resources/check_sign_icon_green.png"/>
		      </div>
		      </div>
		    </div>
		    <div class="form-group">
		      <label for="txtHostIP">Host IP:</label>
		      <div class="row">
		      <div class="col-sm-6">
		      <input type="text" class="form-control rounded-border" id="txtHostIP" placeholder="Enter Host IP">
		      </div>
		      <div class="col-sm-6">
		      <img style="height:20px;width:20px" id="imgIP" src="Resources/check_sign_icon_green.png"/>
		      </div>
		      </div>
		    </div>
		    <div class="row host-wrapper-left">
		    <button type="button" class="btn btn-primary col-sm-2" onClick="testConnect()">Test Connection</button> &nbsp;
		    <div class="col-sm-1"></div>
		    <button type="button" class="btn btn-primary col-sm-2" onClick="getDevices()">Fetch Devices</button>
		    
		    </div>
		    <div class="host-wrapper-left" style="margin-top:10px" id="lblTestMsg"></div>
		  </form>
		</div>
		<!-- End of User input for host wrapper -->            
        <br/><br/>
        <!-- Connected Mobiles Listing -->
        <div id="divMobileList">
			<table class="table table-striped">
			    <thead>
			      <tr>
			        <th>UDID</th>
			        <th>Mobile Name</th>
			      </tr>
			    </thead>
			    <tbody id="tableBody">

			    </tbody>
			  </table>        	
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
    $("#divMobileList").hide();
    $('#imgName').hide();
    $('#imgIP').hide();  
    
    
    $('#txtHostIP').focusout(function(){
		if (/^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/.test(frmHostDet.txtHostIP.value))  
		  {  
		    $('#imgIP').show(); 
		  }  
		else{
			$('#imgIP').hide(); 
		}  
		return (false)     	
    });
    
    $('#txtHostName').focusout(function(){
    	if(/^[a-zA-Z0-9_]*$/.test(frmHostDet.txtHostName.value)) 
    	{
    		$('#imgName').show(); 
    	}
    	else{
    		$('#imgName').hide(); 
    	}
    });
    
    function testConnect(){
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
		    alert("Response");
		    document.getElementById("lblTestMsg").innerHTML =xmlhttp.responseText;
		    
		    }
		}
		alert("Test Called");
		xmlhttp.open("GET","Connection?task=testConnection&ipaddress="+document.getElementById("txtHostIP").value,true);
		xmlhttp.send();		  
		  
    }
    
    function getDevices(){
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
		    alert("Response");
		    document.getElementById("tableBody").innerHTML =xmlhttp.responseText;
		    $("#divMobileList").show();
		    }
		}
		xmlhttp.open("GET","Connection?task=getDevices&ipaddress="+document.getElementById("txtHostIP").value,true);
		xmlhttp.send();	    	
    }
    
    
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
    
    
    
    </script>

</body>

</html>
