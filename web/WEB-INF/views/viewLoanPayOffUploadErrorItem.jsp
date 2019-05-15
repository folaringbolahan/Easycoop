<%@ include file="includes/header.jsp" %> 
<div class="media-body">
    <div style="float:left">
	    <ul class="breadcrumb">
		<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		<li><a href="#">Home</a></li>
		<li>View Loan PayOff Upload Error</li>
	    </ul>
	    <h4>Loan PayOff Upload Error Details</h4>
    </div>
    <%@include file="includes/topright.jsp" %>
</div>

</div><!-- media -->
</div><!-- pageheader -->
<div class="contentpanel">
    <div class="row">
        <div class="col-md-12">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-12">
	       <div class="panel-body">
                 <c:set var="now" value="<%=new java.util.Date()%>" />
		      
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-i18n.min.js"></script> 
                      
                         <form:form method="post" action="#" commandName="loanpayoffuploaderror"> 
			   <HR/>
			   <div class="form-group">   
				  <form:label path="batchId" cssClass="col-sm-4 control-label">Batch Id:</form:label>
				  <div class="col-sm-8">
				    ${loanpayoffuploaderror.batchId}
				  </div>  
			   </div> 

			   <div class="form-group">   
				  <form:label path="loanCaseId" cssClass="col-sm-4 control-label">Loan Case Id:</form:label>
				  <div class="col-sm-8">
				    ${loanpayoffuploaderror.loanCaseId}
				  </div>  
			   </div> 

			   <div class="form-group">   
				  <form:label path="amount" cssClass="col-sm-4 control-label">Amount:</form:label>
				  <div class="col-sm-8">
				    ${loanpayoffuploaderror.amount}
				  </div>  
			   </div> 
			   
			   <div class="form-group">   
				  <form:label path="uploadedDate" cssClass="col-sm-4 control-label">Upload Date</form:label>
				  <div class="col-sm-8">
				    <fmt:formatDate pattern="dd/MM/yyyy" value="${loanpayoffuploaderror.uploadedDate}"/>
				  </div>  
			   </div> 	

			   <div class="form-group">   
				  <form:label path="processingStatus" cssClass="col-sm-4 control-label">Processed Status:</form:label>
				  <div class="col-sm-8">
				    FAILED
				  </div>  
			   </div> 
			   
			   <div class="form-group">   
				  <form:label path="penalty" cssClass="col-sm-4 control-label">Error Details:</form:label>
				  <div class="col-sm-8">
				    ${loanpayoffuploaderror.errorMsg}
				  </div>  
			   </div> 			   
		       
			   <div class="form-group">
				 <button type="button" class="btn btn-default" onclick="Javasscript:history.back(1);">BACK</button>
			   </div><!-- panel-footer --> 			  
			 </form:form>  
               </div>
            </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->   
<%@ include file="includes/footer.jsp" %>  
