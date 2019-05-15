<%@ include file="includes/header.jsp" %> 
<div class="media-body">
        <div style="float:left">
	    <ul class="breadcrumb">
		<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		<li><a href="#">Home</a></li>
		<li>Loan Request For Disbursement</li>
	    </ul>
	    <h4>Disburse Loan Request(s)</h4>
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
				<h4>PENDING LOAN FOR DISBURSEMENT</h4>
			     </div>
			     
			     <table id="loans-list" class="table table-striped table-bordered responsive">  
				 <thead>
				      <tr>  
				       <th>ID</th>  
				 <%--
				       <th>Coop</th>  
				       <th>Branch</th>  
				 --%>
				 
				       <th>Member Name</th>  
				       <th>Amount</th>  
				       <th>Interest Rate</th>  
				       <th>Loan Case Id</th>  
				       <th>Request Status </th> 
				       <th>Action </th> 
				      </tr>  
		   		</thead>
		                <tbody>
				      <c:forEach items="${loanRequests}" var="loan">  
					       <tr>  
						<td><c:out value="${loan.id}"/></td>  
					<%--
						<td><c:out value="${loan.companyId}"/></td>  
						<td><c:out value="${loan.branchId}"/></td>  
					--%> 
						<td><c:out value="${loan.memberNoStr} -> ${loan.memberName}"/></td>  
						<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${loan.approvedAmount}" /></td>  
						<td><c:out value="${loan.appliedRate}"/></td> 
						<td><c:out value="${loan.loanCaseId}"/></td>  
						<td><c:out value="${loan.loanStatusDesc}"/></td>  
						<td align="center">						   
						    <c:if test="${loan.approvedBy == logonUser}">
						       <font color="red"><div data-toggle="tooltip" title="A different user is required to disburse this Loan" cssClass="tooltips">NOT ALLOWED</div></font>
						    </c:if>
						    <c:if test="${loan.approvedBy ne logonUser}">
						       <a href="disburseLoanRequest1.htm?id=${loan.id}">DISBURSE</a>
						    </c:if>
						</td>  
					       </tr>  
				      </c:forEach>  
			         </tbody>
			    </table>  
		       </c:if> 
		       <c:if test="${empty loanRequests}">  
			     <div class="media-body error">
				<h4>THERE IS NO PENDING LOAN TO DISBURSE</h4>
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
				 jQuery('#loans-list').DataTable({
                    responsive: true
                });
    })
</script>
