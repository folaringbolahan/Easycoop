<%@ include file="includes/header.jsp" %>		      
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-i18n.min.js"></script> 
<script lanuage="Javascript">
	function doSubmit(frm){	
	    if(frm.loanCaseId.value==""){alert("Loan Case Id is required"); frm.loanCaseId.focus(); return;}

	    frm.submit();
	}	  
</script>
 <div class="media-body">
 <div style="float:left">

      <ul class="breadcrumb">
         <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
         <li><a href="#">Home</a></li>
         <li>Loan Management</li>
     </ul>
     <h4>Bulk Loan Repayment</h4>
 </div>
 
<%@include file="includes/topright.jsp" %>
 </div>
 </div><!-- media -->
 </div><!-- pageheader -->
 <div class="contentpanel">
     <div class="row">
         <div class="col-md-10">
             <!-- CONTENT GOES HERE -->
             <div class="col-md-15">
 	       		<div class="panel-body">

				  <form:form method="post" action="saveBulkLoanRePayment.htm" commandName="bulkLoanRePayment">
					  <form:hidden path="id" value="${bulkLoanRePayment.id}"/>

					  <div class="form-group">
						  <form:label path="companyId" cssClass="col-sm-3 control-label">Cooperative:</form:label>
						  <div class="col-sm-8">
							 <form:select id="companyId" path="companyId">
							<c:forEach items="${companies}" var="item">
								 <form:option value="${item.id}">${item.name}</form:option>
							</c:forEach>
							  </form:select>
							  <form:errors path="companyId" cssClass="error"></form:errors>
						  </div>
					  </div>

					  <div class="form-group">
						  <form:label path="branchId" cssClass="col-sm-3 control-label">Select Branch</form:label>
						  <div class="col-sm-8">
							  <form:select id="branchId" path="branchId">
								<c:forEach items="${branches}" var="item">
							  <form:option value="${item.id}">${item.branchName}</form:option>
							</c:forEach>
							  </form:select>
							  <form:errors path="branchId" cssClass="error"></form:errors>
						  </div>
					   </div>

					   <div class="form-group">
						  <form:label path="loanCaseId" cssClass="col-sm-3 control-label">Loan Case Id:</form:label>
						  <div class="col-sm-8">
							 <form:input path="loanCaseId" value="${bulkLoanRePayment.loanCaseId}" readonly="true"/>
							 <form:errors path="loanCaseId" cssClass="error"></form:errors>
						  </div>
					   </div>
					   <fmt:formatNumber type="number" maxFractionDigits="2" var="PrlTotal" value="${bulkLoanRePayment.repayTotPrl}" />

					   <div class="form-group">
						  <form:label path="repayTotPrl" cssClass="col-sm-3 control-label">Principal Amount (Total):</form:label>
						  <div class="col-sm-8">
							 <form:input path="repayTotPrl" value="${bulkLoanRePayment.repayTotPrl}"  readonly="true"/>
							 <form:errors path="repayTotPrl" cssClass="error"></form:errors>
						  </div>
					   </div>
					   
					   <fmt:formatNumber type="number" maxFractionDigits="2" var="intTotal" value="${bulkLoanRePayment.repayTotInt}" />
					   
					   <div class="form-group">
						  <form:label path="repayTotInt" cssClass="col-sm-3 control-label">Interest Amount (Total)::</form:label>
						  <div class="col-sm-8">
							 <form:input path="repayTotInt" value="${bulkLoanRePayment.repayTotInt}" readonly="true"/>
							 <form:errors path="repayTotInt" cssClass="error"></form:errors>
						  </div>
					   </div>
					   <fmt:formatNumber type="number" maxFractionDigits="2" var="loanTotal" value="${bulkLoanRePayment.repayTotAmt}" />

					   <div class="form-group">
						  <form:label path="repayTotAmt" cssClass="col-sm-3 control-label">Repayment Total (exclude fine):</form:label>
						  <div class="col-sm-8">
							 <form:input path="repayTotAmt" value="${bulkLoanRePayment.repayTotAmt}"  readonly="true"/>
							 <form:errors path="repayTotAmt" cssClass="error"></form:errors>
						  </div>
					   </div>
					   
					   <fmt:formatNumber type="number" maxFractionDigits="2" var="fineTotal" value="${bulkLoanRePayment.repayTotPenalty}" />

					   <div class="form-group">
						  <form:label path="repayTotPenalty" cssClass="col-sm-3 control-label">Penalty:</form:label>
						  <div class="col-sm-8">
							 <form:input path="repayTotPenalty" value="${bulkLoanRePayment.repayTotPenalty}"  readonly="true"/>
							 <form:errors path="repayTotPenalty" cssClass="error"></form:errors>
						  </div>
					   </div>
					   
					   <input type="hidden" name="scheduleIds" value="${scheduleConcat}">
					   
					   <div class="form-group">
						<button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);"> SUBMIT </button>
						<button type="reset" class="btn btn-default">RESET</button>
					</div><!-- panel-footer -->
				  </form:form>
		        </div>
             </div><!-- col-md-6 -->
           </div>
    </div>
</div><!-- contentpanel -->

<%@ include file="includes/footer.jsp" %>
