<%@ include file="includes/header.jsp" %> 
<div class="media-body">
    <div style="float:left">
	    <ul class="breadcrumb">
		<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		<li><a href="#">Home</a></li>
		<li>View Loan Schedule</li>
	    </ul>
    	    <h4>View Loan Schedule</h4>
    </div>
    <%@include file="includes/topright.jsp"%>
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
                      
                       <div id="LoanView">                      
                         <form:form method="post" action="#" commandName="loanRequest">
			   <HR/>
			   <div class="form-group">   
				  <form:label path="loanCaseId" cssClass="col-sm-4 control-label">Loan Case Id:</form:label>
				  <div class="col-sm-8">
				    <form:input path="loanCaseId" value="${loanRequest.loanCaseId}" readonly="true"/>
				  </div>  
			   </div> 
			   
			   <div class="form-group">   
				  <form:label path="companyId" cssClass="col-sm-4 control-label">Cooperative:</form:label> 
				  <div class="col-sm-8">
				      <form:select id="companyId" path="companyId" disabled="true">
					<c:forEach items="${companies}" var="item">  
					     <form:option value="${item.id}">${item.name}</form:option>
					</c:forEach>
				      </form:select>
				  </div>  
			   </div> 
			   
			   <div class="form-group">   
				  <form:label path="branchId" cssClass="col-sm-4 control-label">Branch</form:label>
				  <div class="col-sm-8">
				      <form:select id="branchId" path="branchId" disabled="true">
					 <c:forEach items="${branches}" var="item">  
						<form:option value="${item.id}">${item.branchName}</form:option>
					 </c:forEach>
				      </form:select>
				  </div>  
			   </div>			   

			   <div class="form-group">   
				  <form:label path="memberNo" cssClass="col-sm-4 control-label">Requesting Member:</form:label>
				  <div class="col-sm-8">
				      <form:select id="memberNo" path="memberNo" disabled="true">
					 <c:forEach items="${membersInc}" var="item">  
						<form:option value="${item.memberId}">${item.compmemberId} -> ${item.surname},${item.firstname}</form:option>
					 </c:forEach>
				      </form:select>
				  </div>  
			   </div>			   
			  
			  <HR/> 
			  
			  <%--
			   <div class="form-group">   
				<div class="form-group">
				    <div class="col-sm-4 control-label"><div class="error">Member Name</div></div>
				    <div class="col-sm-8"><div class="error"><c:out value="${memberSummaryBean.memberName}"/></div></div>
				</div> 
				
				<div class="form-group"> 
				    <div class="col-sm-4 control-label"><div class="error">Running Loans Count</div></div>
				    <div class="col-sm-8"><div class="error"><c:out value="${memberSummaryBean.runningLoanCount}"/></div></div>
				</div> 
				
				<div class="form-group"> 
				    <div class="col-sm-4 control-label"><div class="error">Running Loans Sum</div></div>
				    <div class="col-sm-4"><div class="error"><c:out value="${memberSummaryBean.runningLoanSum}"/></div></div>
				</div>
			   </div> 
			   <HR/> 
			   <div class="form-group">   
				  <form:label path="loanType" cssClass="col-sm-4 control-label">Selected Product</form:label> 
				  <div class="col-sm-8">
				      <form:select id="loanType" path="loanType">
					 <c:forEach items="${products}" var="item">  
					     <form:option value="${item.id}">${item.name}</form:option>
					 </c:forEach>
				      </form:select>
				  </div>  
			   </div>  
			   --%>

			   <div class="form-group">   
				  <form:label path="requestDate" cssClass="col-sm-4 control-label">Request Date:</form:label> 
				  <div class="col-sm-8">
				     <fmt:formatDate pattern="dd/MM/yyyy" value="${loanRequest.requestDate}"/>
				  </div>  
			   </div> 

			   <div class="form-group">   
				  <form:label path="approvedAmount" cssClass="col-sm-4 control-label">Approved Amount:</form:label> 
				  <div class="col-sm-3">
				     <form:input path="approvedAmount" cssClass="error" value="${loanRequest.approvedAmount}" readonly="true"/>
				  </div>  
			   </div> 

			   <div class="form-group">  
				  <form:label path="appliedRate" cssClass="col-sm-4 control-label">Applied Rate:</form:label> 
				  <div class="col-sm-3">
				     <div id="productInfo">
				     	  <form:input id="appliedRate"  cssClass="error" path="appliedRate" value="${loanRequest.appliedRate}" readonly="true"/>
				     </div>
				  </div>  
			   </div> 

			   <div class="form-group">   
				  <form:label path="actualCommencementDate" cssClass="col-sm-4 control-label">Actual Commencement Date:</form:label>  
				  <div class="col-sm-4">
				     <div class="input-group">
				     	<form:input path="actualCommencementDate" value="${pcDateVar}" class="form-control period"  placeholder="dd/mm/yyyy" readonly="true"/>
				      	<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
				      </div>
				  </div>  
			   </div>
		    
	         </form:form>  
	      </div>
	      
	      <c:if test="${!empty schedules}">  
	                     <c:set var="count" scope="page" value="0" />
			     <div class="media-body">
				<h4>LOAN SCHEDULE - VIEW </h4>
			     </div>
			     
			     <table id="schedule-list" class="table table-striped table-bordered responsive">  
				 <thead>
				      <tr>  
				       <th>ID</th>  
				       <th>Interest</th>  
				       <th>Principal</th>  
				       <th>Amount</th>  
				       <th>Date Due</th>
				       <th>Cumm. Principal</th>
				       <th>Status </th> 
				      </tr>  
		   		</thead>
		                <tbody>
				      <c:forEach items="${schedules}" var="item">  
				               <c:set var="count" scope="page" value="${count + 1}" />
					       <tr>  
						<td><c:out value="${count}"/></td>  
						<td><c:out value="${item.amountInterest}"/></td>  
						<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${item.amountPrincipal}"/></td>  
						<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${item.expectedRepaymentAmount}"/></td>  
						<td><fmt:formatDate pattern="dd/MM/yyyy" value="${item.expectedRepaymentDate}"/></td>
						<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${item.cummPrincipal}"/></td>
						<td><c:out value="Pending"/></td>  
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
		
		jQuery('#schedule-list').DataTable({
                    responsive: true
                });
    })
</script>
