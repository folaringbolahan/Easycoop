<html>
<head>
<title></title>
</head>
<body>
<style type="text/css"> 
		<!-- 
		.style2 { 
			font-family: Verdana, Arial, Helvetica, sans-serif; 
			font-size: 14px; 
		} 
		--> 
		</style> 
		 <p class="style2">Hello ${getUsername}, </p> 
		<p class="style2">You have been setup as a user on EazyCoop Application. <br> 
	         <strong>Below is your logon details:  </strong></p> 
		<table width="550" border="0" cellpadding="1" cellspacing="1"> 
		  <tr> 
			<td width="50%"><span class="style2">Username(Email):</span></td> 
			<td width="50%"><span class="style2">  ${getEmail}  </span></td> 
		  </tr> 
	
		  <tr> 
			<td><span class="style2">Temporary Password:</span></td> 
			<td><span class="style2">${clearPass}   </span></td> 
		  </tr> 
	
		  <tr> 
			<td><span class="style2">Application URL:</span></td> 
			<td><span class="style2">${uri}   </span></td> 
		  </tr> 
	
		  <tr> 
			<td colspan=2><hr/></td> 			 
		  </tr> 	
	
		  <tr> 
			<td colspan=2><hr/></td> 			 
		  </tr>         
		</table> 
		<p class="style2"><br> 
		  Thanks </p>
</body>
</html>