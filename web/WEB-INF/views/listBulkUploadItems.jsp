<%@ include file="includes/header.jsp" %> 
<div class="media-body">
           <div style="float:left">
		    <ul class="breadcrumb">
			<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
			<li><a href="#">Home</a></li>
			<li>Bulk Upload Items View</li>
		    </ul>
		    <h4>View Upload Items</h4>
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
                      
		       <c:if test="${!empty bulkuploaditems}">  
				     <c:set var="count" scope="page" value="0" />
				     <div class="media-body">
					<h4>BULK UPLOAD ITEMS- VIEW </h4>
				     </div>

				     <table id="schedule-list" class="table table-striped table-bordered responsive">  
					 <thead>
					      <tr>  
					       <th>ID</th>  
					       <th>BatchId</th>  
					       <th>Loan Case Id</th>  
					       <th>Member No</th>  
					       <th>Amount</th>
					       <th>Process Status</th> 
					       <th>Action</th> 
					      </tr>  
					</thead>
					<tbody>
					      <c:forEach items="${bulkuploaditems}" var="item">  
						       <c:set var="count" scope="page" value="${count + 1}" />
						       <tr>  
							<td><c:out value="${count}"/></td>  
							<td><a href="viewBulkUploadItem.htm?id=${item.id}">${item.batchId}</a></td>  
							<td>${item.loanCaseId}</td>  
							<td>${item.memberNo}</td> 
							<td>${item.amount}</td> 
							<td>${item.processedStatus}</td>  
							<td>----</td> 
						       </tr>  
					      </c:forEach>  
					 </tbody>
				    </table>  
		       </c:if> 
		       
		       <c:if test="${empty bulkuploaditems}">
		                     <div class="media-body  error">
			  		    <h4>NO RECORD FOUND </h4>
				     </div>
		       </c:if>
		       
		       <div class="form-group">
			    <button type="button" class="btn btn-default" onclick="Javascript:history.back(1);">BACK</button>
		       </div>
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
