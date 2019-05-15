<%@ include file="includes/header.jsp" %> 
<div class="media-body">
      <div style="float:left">
	    <ul class="breadcrumb">
		<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		<li><a href="#">Home</a></li>
		<li>Approve Bulk Upload</li>
	    </ul>
	    <h4>Approve Upload</h4>
      </div>
      
      <%@include file="includes/topright.jsp" %>
</div>

</div><!-- media -->
</div><!-- pageheader -->
  <script lanuage="Javascript">
	function ConfirmApproval(frm){	
	    var r = confirm("Do you want to APPROVE this upload for processing?");
	    if (r == true) {
	        frm.actionId.value="A";
	        frm.action="authorizeBulkPay.htm";
	        frm.submit();
	    } else {
	        return;
            }	    
	}
	
	function ConfirmReject(frm){	
	    var r = confirm("Do you want to REJECT this upload?");
	    if (r == true) {
	        frm.actionId.value="R";
	        frm.action="rejectBulkPay.htm";
		frm.submit();
	    } else {
		return;
	    }	    
	}
  </script>
<div class="contentpanel">
    <div class="row">
        <div class="col-md-12">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-12">
	       <div class="panel-body">
                 <c:set var="now" value="<%=new java.util.Date()%>"/>
		      
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-i18n.min.js"></script> 
                                            
                         <form:form method="post" action="" commandName="bulkupload"> 
			   <HR/>
			   <div class="form-group">   
				  <form:label path="batchId" cssClass="col-sm-4 control-label">Batch Id:</form:label>
				  <div class="col-sm-8">
				    ${bulkupload.batchId}
				  </div>  
			   </div> 

			   <div class="form-group">   
				  <form:label path="batchId" cssClass="col-sm-4 control-label">Upload Filename:</form:label>
				  <div class="col-sm-8">
				    ${bulkupload.uploadFileShortName}
				  </div>  
			   </div> 
			   
			   <div class="form-group">   
				  <form:label path="uploadDate" cssClass="col-sm-4 control-label">Upload Date</form:label>
				  <div class="col-sm-8">
				    <fmt:formatDate pattern="dd/MM/yyyy" value="${bulkupload.uploadDate}"/>
				  </div>  
			   </div> 

			   <div class="form-group">   
				  <form:label path="uploadedBy" cssClass="col-sm-4 control-label">Upload By:</form:label>
				  <div class="col-sm-8">
				    ${bulkupload.uploadedBy}
				  </div>  
			   </div> 	

			   <div class="form-group">   
				  <form:label path="uploadStatus" cssClass="col-sm-4 control-label">Upload Status:</form:label>
				  <div class="col-sm-8">
				    ${bulkupload.uploadStatusDesc}
				  </div>  
			   </div> 
			   
			   <c:if test="${bulkupload.uploadType=='LR'}">
				<c:set var="uT" value="Bulk Repayment" />
			   </c:if>

			   <c:if test="${bulkupload.uploadType=='LP'}">
				<c:set var="uT" value="Loan PayOff" />
			   </c:if>
			   
			   <div class="form-group">   
				  <form:label path="uploadType" cssClass="col-sm-4 control-label">Upload Type:</form:label>
				  <div class="col-sm-8">
				    ${uT}
				  </div>  
			   </div> 
			   
			   <c:if test="${bulkupload.uploadStatus=='U'}"> 
				   <div class="form-group">   
					  <form:label path="totalRecords" cssClass="col-sm-4 control-label">Total Records:</form:label>
					  <div class="col-sm-8">
					    ${bulkupload.totalRecords}
					  </div>  
				   </div> 

				   <div class="form-group">   
					  <form:label path="successCount" cssClass="col-sm-4 control-label">Success Count:</form:label>
					  <div class="col-sm-8">
					    ${bulkupload.successCount}
					  </div>				  
				   </div>
				   
				   <div class="form-group">   
					  <form:label path="successCount" cssClass="col-sm-4 control-label">Success SUM:</form:label>
					  <div class="col-sm-8">
					    ${bulkupload.successSum}
					  </div>				  
				   </div>
				   
				   <div class="form-group">   
					  <form:label path="failedCount" cssClass="col-sm-4 control-label">Error Count:</form:label>
					  <div class="col-sm-8">
					     ${bulkupload.failedCount}
					  </div>  
				   </div> 

				   <div class="form-group">   
					  <form:label path="failedCount" cssClass="col-sm-4 control-label">Error SUM:</form:label>
					  <div class="col-sm-8">
					     ${bulkupload.failedSum}
					  </div>  
				   </div>
				   
				   <HR/>
				   
				   <div class="form-group">   
					  <form:label path="successCount" cssClass="col-sm-4 control-label">Recount Count (User Specified):</form:label>
					  <div class="col-sm-8">
					    ${bulkupload.userUploadcount}
					  </div>				  
				   </div>
				   
				   <div class="form-group">   
					  <form:label path="failedCount" cssClass="col-sm-4 control-label">Upload Sum (User Specified):</form:label>
					  <div class="col-sm-8">
					     ${bulkupload.userUploadSum}
					  </div>  
				   </div> 

				   <div class="form-group">   
					  <form:label path="failedCount" cssClass="col-sm-4 control-label">Penalty SUM (User Specified):</form:label>
					  <div class="col-sm-8">
					     ${bulkupload.userUploadFine}
					  </div>  
				   </div>
				   
				   <c:if test="${bulkupload.successCount!=0}">
					   <HR/>
						   <div class="form-group">   
							  <div class="col-sm-8">
							       <a href="download.htm?filename=${reportFileLocation}${bulkupload.batchId}_${bulkupload.uploadType}_success.xls">Download Success Report</a>
							  </div>				  
						   </div>

						   <div class="form-group">   
							  <div class="col-sm-8">
								   <c:if test="${bulkupload.uploadType=='LP'}">
							           	<a href="listLoanPayOffUploadItems.htm?batchId=${bulkupload.batchId}">View Success List</a>
			   					   </c:if>
			   					   
								   <c:if test="${bulkupload.uploadType=='LR'}">
							           	<a href="listBulkUploadItems.htm?batchId=${bulkupload.batchId}">View Success List</a>
			   					   </c:if>							           
							  </div>				  
						   </div>
					   <HR/>
				   </c:if>

				   <c:if test="${bulkupload.failedCount!=0}">
					   <HR/>
						   <div class="form-group">   
							  <div class="col-sm-8">
							       <a href="download.htm?filename=${reportFileLocation}${bulkupload.batchId}_${bulkupload.uploadType}_errors.xls">Download Error Report</a>
							  </div>				  
						   </div>
						   
						   <div class="form-group">   
							  <div class="col-sm-8">
								   <c:if test="${bulkupload.uploadType=='LP'}">
							           	<a href="listLoanPayOffUploadErrors.htm?batchId=${bulkupload.batchId}">View Error List</a>
			   					   </c:if>
			   					   
								   <c:if test="${bulkupload.uploadType=='LR'}">
							           	<a href="listBulkUploadErrors.htm?batchId=${bulkupload.batchId}">View Error List</a>
			   					   </c:if>			   					   
							  </div>				  
						   </div>
					   <HR/>
				   </c:if>
			       </c:if>
			       
			       <input type="hidden"  name="actionId"  value="">
			       <input type="hidden"  name="batchId"  value="${bulkupload.batchId}">
			       <input type="hidden"  name="uT"  value="${bulkupload.uploadType}">
			       <form:hidden path="uploadType" value="${bulkupload.uploadType}"/>

			       <div class="form-group">
			            <button type="button" class="btn btn-default" onclick="Javascript:ConfirmApproval(this.form);">APROVE</button>
				    &nbsp;
				    <button type="button" class="btn btn-default" onclick="Javascript:ConfirmReject(this.form);">REJECT</button>
				    &nbsp;
				    <button type="button" class="btn btn-default" onclick="Javascript:history.back(1);">BACK</button>
			       </div><!-- panel-footer --> 			  
			 </form:form>  
               </div>
               
               <c:if test="${!empty bulkuploaditems}">  
	                    <div class="media-body">
	                       <h4>RECORDS FOR AUTHORIZATION</h4>
	                    </div>
	          	     <table id="items-list" class="table table-striped table-bordered responsive">  
	          		 <thead>
				      <tr>  
				       <th>Loan Id</th>  
				       <th>Payment Amount</th>  
				       <th>Penalty</th>  
				       <th>Upload Date</th>  
				       <th>Upload By</th> 
				       <th>Status</th> 
				      </tr>  
	          		</thead>
	          		<tbody>
				      <c:forEach items="${bulkuploaditems}" var="item">  
					       <tr>  
						<td><c:out value="${item.loanCaseId}"/></td>  
						<td><c:out value="${item.amount}"/></td>  
						<td><c:out value="${item.penalty}"/></td>  
						<td><c:out value="${item.uploadedDate}"/></td> 
						<td><c:out value="${item.uploadedBy}"/></td> 
						<td><c:out value="${item.processedStatus}"/></td>  
					       </tr>  
				      </c:forEach>  
	          	       </tbody>
	          	    </table>  
    		</c:if>
    		
            </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->   
<%@ include file="includes/footer.jsp" %>   
<script>
    $(document).ready(function(){
         jQuery('select').select2({
                    minimumResultsForSearch: -1
                });
				 jQuery('#items-list').DataTable({
                    responsive: true
                });
    })
</script>
