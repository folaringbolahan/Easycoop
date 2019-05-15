<%@ include file="includes/header.jsp" %> 
<div class="media-body">
     <div style="float:left">
	    <ul class="breadcrumb">
		<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		<li><a href="#">Home</a></li>
		<li>View Password Reset Request</li>
	    </ul>
	    <h4>View Password Reset Details</h4>
    </div>    
    <%@include file="includes/topright.jsp" %>
</div>

</div><!-- media -->
</div><!-- pageheader -->
  <script lanuage="Javascript">
	function doSubmit(frm){	
	    if(frm.approvalComment.value==""){alert("approval/rejection comment is required"); frm.approvalComment.focus(); return;}
	    frm.submit();
	}	  
  </script>
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
                      
                         <form:form method="post" action="approveAuthPermit.htm" commandName="authPermit"> 
			   <HR/>
			   <div class="form-group">   
				  <form:label path="id" cssClass="col-sm-4 control-label">Id:</form:label>
				  <div class="col-sm-8">
				    ${authPermit.id}
				  </div>  
			   </div> 

			   <div class="form-group">   
				  <form:label path="email" cssClass="col-sm-4 control-label">Email:</form:label>
				  <div class="col-sm-8">
				    ${authPermit.email}
				  </div>  
			   </div> 

			   <div class="form-group">   
				  <form:label path="requestStatus" cssClass="col-sm-4 control-label">Request Status:</form:label>
				  <div class="col-sm-8">
				    ${authPermit.requestStatus}
				  </div>  
			   </div> 
			   
			   <div class="form-group">   
				  <form:label path="requestDate" cssClass="col-sm-4 control-label">Request Date</form:label>
				  <div class="col-sm-8">
				    <fmt:formatDate pattern="dd/MM/yyyy" value="${authPermit.requestDate}"/>
				  </div>  
			   </div> 
			   
			   <div class="form-group">   
				  <form:label path="approvalComment" cssClass="col-sm-4 control-label">Approve:</form:label> 
				  <div class="col-sm-8">
				      <input type="radio" name="approvalStatus" value="R" > Reject
				      <input type="radio" name="approvalStatus" value="A" checked="true"> Approve 
				  </div>  
			   </div>
			   
			   <div class="form-group">   
				  <form:label path="approvalComment" cssClass="col-sm-4 control-label">Comment:</form:label>  
				  <div class="col-sm-8">
				     <form:textarea path="approvalComment" value="${authPermit.approvalComment}"/>
				  </div>  
			   </div>
			   
			   <form:hidden path="id" value="${authPermit.id}"/>
			   <form:hidden path="email" value="${authPermit.email}"/>
		       
			   <div class="form-group">
			         <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">SUBMIT </button>
				 <button type="button" class="btn btn-default" onclick="Javasscript:history.back(1);">BACK</button>
			   </div><!-- panel-footer --> 			  
			 </form:form>  
               </div>
            </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->   
<%@ include file="includes/footer.jsp" %>  
