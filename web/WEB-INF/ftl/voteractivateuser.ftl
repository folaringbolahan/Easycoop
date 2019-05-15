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
		<p class="style2">Your voting profile for the online voting system on EasyCoop Application has been setup. <br> 
                This is for the ${getAgmdescription}.
                 <br>
	         <strong>Below is your logon details:  </strong></p> 
		<table width="600" border="0" cellpadding="1" cellspacing="1"> 
		  <tr> 
			<td width="30%"><span class="style2">Application URL:</span></td> 
			<td width="70%"><span class="style2"><a href = "${uri}"  target="_blank"> Click here </a> or paste in your browser the address - ${uri}   </span></td> 
		  </tr> 
                   <tr> 
			<td width="30%"><span class="style2">Username(Email):</span></td> 
			<td width="70%"><span class="style2">  ${getEmail}  </span></td> 
		  </tr> 
	
		  <tr> 
			<td><span class="style2">Password:</span></td> 
			<td><span class="style2">${getClearpass}   </span></td> 
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
		  Please cast your vote within the time stipulated. Note that the password can only be used once. 
                <br>Thanks </p>
</body>
</html>