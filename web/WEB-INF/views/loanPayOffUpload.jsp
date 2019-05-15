<%@ include file="includes/header.jsp" %>
<div class="media-body">
   <div style="float:left">
	    <ul class="breadcrumb">
		<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		<li><a href="#">Home</a></li>
		<li>Loan PayOff Bulk Interface</li>
	    </ul>
	    <h4>Loan PayOff Upload</h4>
    </div>
    <%@include file="includes/topright.jsp" %>
</div>

</div><!-- media -->
</div><!-- pageheader -->
  <script lanuage="Javascript">
	function ConfirmUpload(frm){	
	    
	    if(frm.userUploadcount.value==""){alert("Record count is required"); frm.userUploadcount.focus(); return;}
	    if(frm.userUploadSum.value==""){alert("Payment Sum is required"); frm.userUploadSum.focus(); return;}
	    if(frm.userUploadFine.value==""){alert("Penalty Sum is required"); frm.userUploadFine.focus(); return;}
		
	    frm.submit();
	}
  </script>
<div class="contentpanel">
    <div class="row">
        <div class="col-md-6">
            <!-- CONTENT GOES HERE -->  
           <div class="panel panel-default"> 
                <div class="panel-heading">   
                  <div class="panel-btns">
                   </div><!-- panel-btns -->
                   <h4 class="panel-title">LOAN PAYOFF UPLOAD</h4>
                   <p>To upload bulk Loan PayOff for loans</p>
                   
             </div>
	     <div class="panel-body">
	     
	        <form:form action="uploadFile.htm" commandName="bulkupload" method="post" enctype="multipart/form-data">
	             <div class="form-group">  
	                   <form:label for="fileData" path="fileData" data-toggle="tooltip" title="select file to upload" cssClass="col-sm-4 tooltips control-label">Select file</form:label>	                   
			   <div class="col-sm-2">
			     <form:input path="fileData" type="file"/>
			  </div> 
	             </div>
	             
		     <div class="form-group">                  
			  <form:label path="userUploadcount" data-toggle="tooltip" title="Enter No of records in the upload file" cssClass="col-sm-4 tooltips control-label">Record Count *:</form:label>
			  <div class="col-sm-2">
			      <form:input path="userUploadcount" value="${userUploadcount}"/>
			  </div>
		     </div> 

		     <div class="form-group">                  
			  <form:label path="userUploadSum" data-toggle="tooltip" title="Total sum of Loan Amount (Principal + Interest) as contained in the upload file" cssClass="col-sm-4 tooltips control-label">Total Sum (Principal + Interest):</form:label>
			  <div class="col-sm-2">
			      <form:input path="userUploadSum" value="${userUploadSum}"/>
			  </div>
		     </div> 

		     <div class="form-group">                  
			  <form:label path="userUploadFine" data-toggle="tooltip" title="Total Penalty as contained in the upload file" cssClass="col-sm-4 tooltips control-label">Total Penalty:</form:label>
			  <div class="col-sm-2">
			      <form:input path="userUploadFine" value="${userUploadFine}"/>
			  </div>
		     </div>		     
		     
		     <input type="hidden" name="ACTION_ID" value="1">
                     <form:hidden  path="uploadType" value="LP"/>
		     <form:hidden path="uploadedBy"  value="<%=request.getRemoteUser()%>"/>
		     <form:hidden path="uploadDate"  value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>

		     <div class="form-group">
			    <button class="btn btn-danger mr5" type="button" onclick="Javascript:ConfirmUpload(this.form);">SUBMIT</button>
			    <button type="reset" class="btn btn-default">RESET</button>
		     </div><!-- panel-footer -->      
            
      		</form:form>  
             </div>  

            </div><!-- col-md-6 -->
        </div> 
        
        
        
        
        
            <!-- new panel for download start -->  
            <div class="col-md-6">
                <div class="panel panel-default"> 
                    <div class="panel-heading">
                        <div class="panel-btns">
                        </div><!-- panel-btns -->
                        <h5 class="panel-title">TEMPLATE FOR LOAN PAYOFF UPLOAD</h5>
                        <p>Download this template for loan pay off upload. </p>
                    </div><!-- panel-heading -->
                    <div class="panel-body ">  
                        <div class="col-md-12">
                            <div class="table-responsive">
                                <table class="table table-danger table-bordered table-hover mb30">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>File Name</th>
                                            <th>Description</th>
                                            <th>Download</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>1</td>
                                            <td>Loan_PayOff</td>
                                            <td>Loan PayOff Upload Template</td>
                                             <td><a href="loanrpytuploadteml.htm?downFile=Loan_PayOff_Template.xls">Download</a></td>
                                        </tr>

                                    </tbody>
                                </table>
                            </div><!-- table-responsive -->
                        </div>

                    </div>      
                </div>
            </div>   
            <!-- new for download panel end -->
        
        
        
        
    </div>
</div><!-- contentpanel -->
<%@ include file="includes/footer.jsp" %>  

   