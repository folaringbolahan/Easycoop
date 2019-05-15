<%@ include file="includes/header.jsp" %> 
<div class="media-body">
    <div style="float:left">
	    <ul class="breadcrumb">
		<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		<li><a href="#">Home</a></li>
		<li>Running Loan Requests</li>
	    </ul>
    	    
    	    <h4>Running Loan(s)</h4>
    </div>
    <%@include file="includes/topright.jsp" %>
</div>

</div><!-- media -->
</div><!-- pageheader -->
<div class="contentpanel">
    <div class="row">
        <div class="col-md-12">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-16">
	       <div class="panel-body">
                 <c:set var="now" value="<%=new java.util.Date()%>" />
		      
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-i18n.min.js"></script> 
                      
                      
		          <c:if test="${!empty loanRequests}">  
			     <div class="media-body">
				<h4>RUNNING LOANS</h4>
			     </div>
			     
			     <table id="loans-list" class="table table-striped table-bordered responsive">  
				 <thead>
				      <tr>  
				       <th>ID</th>  
				       <th>Company</th>  
				       <th>Branch</th>  
				       <th>Member No</th>  
				       <th>Amount</th>  
				       <th>Interest Rate</th>  
				       <th>Loan Case Id</th>  
				       <th>Request Status</th> 
				       <th>Action </th> 
				      </tr>  
		   		</thead>
		                <tbody>
				      <c:forEach items="${loanRequests}" var="loan">  
					       <tr>  
						<td><c:out value="${loan.id}"/></td>  
						<td><c:out value="${loan.companyId}"/></td>  
						<td><c:out value="${loan.branchId}"/></td>  
						<td><c:out value="${loan.memberNo}"/></td>  
						<td><c:out value="${loan.approvedAmount}"/></td>  
						<td><c:out value="${loan.appliedRate}"/></td> 
						<td><c:out value="${loan.loanCaseId}"/></td>  
						<td><c:out value="${loan.requestStatus}"/></td>  
						<td align="center"><a href="loanRepayment1.htm?id=${loan.id}">PAYMENT</a></td>  
					       </tr>  
				      </c:forEach>  
			         </tbody>
			    </table>  
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
				 jQuery('#loans-list').DataTable({
                    responsive: true
                });
    })
</script>
