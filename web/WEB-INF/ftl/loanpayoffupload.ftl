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
		 <p class="style2">Hello ${getUploadedBy}, </p> 
		<p class="style2">The Processing of your Bulk Loan PayOff has been completed. <br> 
	         <strong>Below is your processing reports: </strong></p> 
		<table width="550" border="0" cellpadding="1" cellspacing="1"> 
		  <tr> 
			<td width="50%"><span class="style2">Upload Batch Id:</span></td> 
			<td width="50%"><span class="style2">${getBatchId}  </span></td> 
		  </tr> 
	
		  <tr> 
			<td><span class="style2">Total Records Processed:</span></td> 
			<td><span class="style2">${getTotalRecords}   </span></td> 
		  </tr> 
	
		  <tr> 
			<td><span class="style2">Success Count:</span></td> 
			<td><span class="style2">${getSuccessCount}   </span></td> 
		  </tr> 
	
		  <tr> 
			<td><span class="style2">Success Sum (Amount):</span></td> 
			<td><span class="style2">${getSuccessSum}   </span></td> 
		  </tr> 
	
		  <tr> 
			<td><span class="style2">Failed Count:</span></td> 
			<td><span class="style2">${getFailedCount}   </span></td> 
		  </tr> 
	
		  <tr> 
			<td><span class="style2">Failed Sum (Amount):</span></td> 
			<td><span class="style2">${getFailedSum}   </span></td> 
		  </tr> 
	
		  <tr> 
			<td><span class="style2">Penalty Sum (Amount):</span></td> 
			<td><span class="style2">${getPenaltySum}   </span></td> 
		  </tr> 
	
		  <tr> 
			<td><span class="style2">Upload Current Status:</span></td> 
			<td><span class="style2">Awaiting Validation By 2nd Level Oficer </span></td> 
		  </tr> 
	
		  <tr> 
			<td colspan=2><hr/></td> 			 
		  </tr> 	
	
		  <tr> 
			<td><span class="style2">User Specified Record Count:</span></td> 
			<td><span class="style2">${getUserUploadcount}   </span></td> 
		  </tr> 
	
		  <tr> 
			<td><span class="style2">User Specified Record Sum (Amount):</span></td> 
			<td><span class="style2">${getUserUploadSum}   </span></td> 
		  </tr> 
	
		  <tr> 
			<td><span class="style2">User Specified Penalty Sum (Amount):</span></td> 
			<td><span class="style2">${getUserUploadFine}   </span></td> 
		  </tr> 
	
		  <tr> 
			<td colspan=2><hr/></td> 			 
		  </tr>         
		</table> 
		<p class="style2"><br> 
		  Thanks </p>
</body>
</html>