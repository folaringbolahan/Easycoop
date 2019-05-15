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
		<p class="style2">This is a reminder on the online voting invitation on EasyCoop Application. <br> 
                This is for the ${getAgmdescription}
	         <strong>Below is your logon details:  </strong></p> 
		<table width="550" border="0" cellpadding="1" cellspacing="1"> 
		  <tr> 
			<td><span class="style2">Application URL:</span></td> 
			<td><span class="style2">${uri}   </span></td> 
		  </tr> 
                   <tr> 
			<td width="50%"><span class="style2">Username(Email):</span></td> 
			<td width="50%"><span class="style2">  ${getEmail}  </span></td> 
		  </tr> 
	
		  <tr> 
			<td colspan=2>Voting Period</td> 			 
		  </tr> 	
	
		  <tr> 
			<td><span class="style2">Start Date and Time:</span></td> 
			<td><span class="style2">${getStartdate} :  ${getStarttime}  </span></td> 
		  </tr>  
                  <tr> 
			<td><span class="style2">Close Date and Time:</span></td> 
			<td><span class="style2">${getEnddate} :  ${getEndtime}  </span></td> 
		  </tr>         
		</table> 
		<p class="style2"><br> 
		  Please cast your vote within the time stipulated.Thanks </p>
</body>
</html>