<%@ include file="includes/header.jsp" %> 
<div class="media-body">
      <div style="float:left">
	    <ul class="breadcrumb">
		<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		<li><a href="#">Home</a></li>
		<li>Loan Schedule Payment</li>
	    </ul>
	    <h4>Loan Schedule Payment</h4>
      </div>
      <%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<div class="contentpanel">
    <div class="row">
        <div class="col-md-10">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-12">
	       <div class="panel-body">
                 <c:set var="now" value="<%=new java.util.Date()%>" />      
                 <form:form method="post" action="saveLoanRepaySchedule.htm" commandName="loanRepaySch">
			  <div class="form-group">  
				  <form:label path="companyId" cssClass="col-sm-4 control-label">Cooperative:</form:label>  
				  <div class="col-sm-4">
				      <form:select id="companyId" path="companyId">
					<c:forEach items="${companies}" var="item">  
					     <form:option value="${item.id}">${item.name}</form:option>
					</c:forEach>
				      </form:select>
				      <form:errors path="companyId" cssClass="error"></form:errors>
				  </div>  
			  </div>			  

			  <div class="form-group">  
				  <form:label path="branchId" cssClass="col-sm-4 control-label">Branch:</form:label>  
				  <div class="col-sm-4">
				      <form:select id="branchId" path="branchId">
					<c:forEach items="${branches}" var="item">  
					     <form:option value="${item.id}">${item.branchName}</form:option>
					</c:forEach>
				      </form:select>
				      <form:errors path="branchId" cssClass="error"></form:errors>
				  </div>  
			  </div>			  

			  <div class="form-group">  
				  <form:label path="paymentStatus" cssClass="col-sm-4 control-label">Payment Status:</form:label>  
				  <div class="col-sm-4">
				      PENDING
				  </div>  
			  </div>
			   
			  <div class="form-group"> 
				<form:label path="loanCaseId" cssClass="col-sm-4 control-label">Loan Case Id:</form:label> 
				  <div class="col-sm-4">
				     <form:input path="loanCaseId" value="${loanRepaySch.loanCaseId}" readonly="true"/>
				  </div>  
			   </div>  

			   <div class="form-group"> 
				  <form:label path="memberNo" cssClass="col-sm-4 control-label">Member Name:</form:label>  
				  <div class="col-sm-4">
				      ${loanRepaySch.memberName}
				  </div>  
			   </div>  
			   
			   <div class="form-group"> 
				  <form:label path="amountPrincipal" cssClass="col-sm-4 control-label">Amount Principal:</form:label>  
				  <div class="col-sm-4">
				      <fmt:formatNumber type="number" maxFractionDigits="2" value="${loanRepaySch.amountPrincipal}" />
				  </div>  
			   </div> 
			   
			   <div class="form-group"> 
				  <form:label path="amountInterest" cssClass="col-sm-4 control-label">Amount Interest:</form:label>  
				  <div class="col-sm-4">
				      <fmt:formatNumber type="number" maxFractionDigits="2" value="${loanRepaySch.amountInterest}" />
				  </div>  
			   </div> 
			   
			   <div class="form-group"> 
				  <form:label path="expectedRepaymentAmount" cssClass="col-sm-4 control-label">Expected Payment:</form:label>  
				  <div class="col-sm-4">
				      ${loanRepaySch.expectedRepaymentAmount}
				  </div>  
			   </div> 

			   <div class="form-group"> 
				  <form:label path="actualRepaymentAmount" cssClass="col-sm-4 control-label">Actual Payment:</form:label>  
				  <div class="col-sm-4">
				      <form:input path="actualRepaymentAmount" value="${loanRepaySch.expectedRepaymentAmount}" readonly="true"/>
				  </div>  
			   </div> 
			   
			   <fmt:formatDate pattern="dd/MM/yyyy" value="${loanRepaySch.expectedRepaymentDate}" var="pcDateVar"/>
			   
			   <div class="form-group">   
				  <form:label path="expectedRepaymentDate" cssClass="col-sm-4 control-label">Expected Repayment Date:</form:label>  
				  <div class="col-sm-4">
				     <div class="input-group">
				     	<form:input path="expectedRepaymentDate" value="${pcDateVar}" class="form-control period"  placeholder="dd/mm/yyyy" />
				      	<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
				      </div>
				  </div>  
			   </div>

		           <fmt:formatDate pattern="dd/MM/yyyy" value="${now}" var="pcDateVar1"/>
			   
			   <div class="form-group">   
				  <form:label path="actualRepaymentDate" cssClass="col-sm-4 control-label">Actual Repayment Date:</form:label>  
				  <div class="col-sm-4">
				     <div class="input-group">
				     	<form:input path="actualRepaymentDate" value="${pcDateVar1}" class="form-control period"  placeholder="dd/mm/yyyy" />
				      	<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
				      </div>
				  </div>  
			   </div>

			   <div class="form-group"> 
				  <form:label path="penaltyIncurred" cssClass="col-sm-4 control-label">Penalty Incurred:</form:label>  
				  <div class="col-sm-4 ">
				      <form:input path="penaltyIncurred" cssClass="error" value="${loanRepaySch.penaltyIncurred}" readonly="true"/>
				  </div>  
			   </div>

			   <form:hidden path="active" value="Y"/>
			   <form:hidden path="deleted" value="N"/> 
			   
           		   <input type="hidden" name="ACTION_ID" value="2">
			   <form:hidden path="id" value="${loanRepaySch.id}"/>
			   <form:hidden path="lastModifiedBy" value="<%=request.getRemoteUser()%>"/>
			   <form:hidden path="lastModificationDate" value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>

			   <div class="form-group">
				    <button class="btn btn-danger mr5" type="submit">PAY</button>
				    <button type="reset" class="btn btn-default">RESET</button>
			   </div><!-- panel-footer -->   
	      	      </form:form>  
		 </div> 
 
            </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->
<%@ include file="includes/footer.jsp" %>  
