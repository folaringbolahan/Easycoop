<%@ include file="includes/header.jsp" %> 
<div class="media-body">
        <div style="float:left">
	    <ul class="breadcrumb">
		<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		<li><a href="#">Home</a></li>
		<li>Bulk Upload Listing</li>
	    </ul>
	    <h4>Payment Uploads</h4>
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
                      <c:set var="now" value="<%=new java.util.Date()%>"/>
		      
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-i18n.min.js"></script> 
                                            
		       <c:if test="${!empty bulkuploads}">  
				     <c:set var="count" scope="page" value="0"/>
				     <div class="media-body">
					<h4>BULK UPLOAD - VIEW </h4>
				     </div>

				     <table id="schedule-list" class="table table-striped table-bordered responsive">  
					 <thead>
					      <tr>  
					       <th>ID</th>  
					       <th>BatchId</th>  
					       <th>Filename</th>  
					       <th>Upload Status</th>  
					       <th>Upload Date</th>
					       <th>Payment Status</th> 
					       <th>Upload Type</th> 
					      </tr>  
					</thead>
					<tbody>
					      <c:forEach items="${bulkuploads}" var="item">  
						       <c:set var="count" scope="page" value="${count + 1}" />
						       <tr>  
							<td><c:out value="${count}"/></td>  
							<td><a href="viewBulkUpload.htm?id=${item.id}">${item.batchId}</a></td>  
							<td>${item.uploadFileShortName}</td>  
							<td>${item.uploadStatusDesc}</td>  
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${item.uploadDate}"/></td>  
							<td>${item.paymentStatus}</td> 
							<td>${item.uploadType}</td> 
							
							<%--
							<td>
							   <c:if test="${item.uploadStatus=='P'}">
							       <c:if test="${item.paymentStatus=='N'}">
							           <a href="processBulkUploadRepayment.htm?id=${item.id}">PROCESS PAYMENT</a>
							       </c:if>  
							       
							       <c:if test="${item.paymentStatus!='N'}">	
							          ------
							       </c:if>  
							   </c:if> 
							   
							   <c:if test="${item.uploadStatus!='P'}">
							        ------
							   </c:if>
							
						        </td> 
						        --%>
						        
						       </tr>  
					      </c:forEach>  
					 </tbody>
				    </table>  
		       </c:if> 
		       
		       <c:if test="${empty bulkuploads}">
			     <div class="media-body  error">
				    <h4>NO CURRENT UPLOAD </h4>
			     </div>
		       </c:if>
               </div>
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
		
		jQuery('#schedule-list').DataTable({
                    responsive: true
                });
    })
</script>
