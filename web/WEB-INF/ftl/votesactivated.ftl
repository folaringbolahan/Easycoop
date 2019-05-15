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
		 <p class="style2">Hello Administrator, </p> 
		<p class="style2">Below are the details of vote questions approved <br> 
                This is for the AGM - <strong>${agm} </strong>.
                 <br>
	         <table width="600" border="0" cellpadding="1" cellspacing="1"> 
		  <tr> 
			<td width="75%"><span class="style2">Number of votes newly approved :</span></td> 
			<td width="25%"><span class="style2">${noofvotesnewlyappr}   </span></td> 
		  </tr> 
                   <tr> 
			<td width="75%"><span class="style2">Cumulative Number of votes approved :</span></td> 
			<td width="25%"><span class="style2">  ${noofvotesapprcum}  </span></td> 
		  </tr> 
	
		  <tr> 
			<td><span class="style2">Number of votes <strong>un-approved</strong> :</span></td> 
			<td><span class="style2">${noofvotesnotappr}   </span></td> 
		  </tr> 
	
		  
		  <tr> 
			<td><span class="style2">Total Number of votes setup :</span></td> 
			<td><span class="style2">${noofvotesall}  </span></td> 
		  </tr>  
                         
		</table> 
		<p class="style2"><br> 
		<br>Thanks </p>
</body>
</html>