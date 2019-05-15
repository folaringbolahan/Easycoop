    <%@ include file="includes/header.jsp" %>
		      
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-i18n.min.js"></script> 
                      
                     
                     <script type="text/javascript">
		      $(document).ready(function(){
			$('select#companyId').change(
				function(){
					//alert($(this).val());
					$.getJSON('branchesAjaxList.do', {
						companyId : $(this).val(),
						ajax : 'true'
					}, function(data) {
						var len = 0;
						var html ='';

						if(data!=null){
						   len=data.length;
						}

						//alert("len:="+len);
						if(len>0){
							for ( var i = 0; i < len; i++) {
							    html += '<option value="' + data[i].id + '">' + data[i].branchName + '</option>';
							}
						}

						//alert(html);
						$('select#branchId').html(html);
					});
				});
		      });
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

				  <form:form method="post" action="saveBalloonPayment.htm" commandName="balloonPayment">
					  <div class="form-group">
							  <form:label path="id" cssClass="col-sm-3 control-label">ID:</form:label>
							  <div class="col-sm-8">
								 <form:input path="id" value="${balloonPayment.id}" readonly="true"/>
							  </div>
					  </div>

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
							 <form:input path="loanCaseId" value="${balloonPayment.loanCaseId}" readonly="true"/>
							 <form:errors path="loanCaseId" cssClass="error"></form:errors>
						  </div>
					   </div>
					   <fmt:formatNumber type="number" maxFractionDigits="2" var="PrlTotal" value="${balloonPayment.repayTotPrl}" />

					   <div class="form-group">
						  <form:label path="repayTotPrl" cssClass="col-sm-3 control-label">Principal Amount (Total):</form:label>
						  <div class="col-sm-8">
							 <form:input path="repayTotPrl" value="${PrlTotal}"  readonly="true"/>
							 <form:errors path="repayTotPrl" cssClass="error"></form:errors>
						  </div>
					   </div>
					   
					   <fmt:formatNumber type="number" maxFractionDigits="2" var="intTotal" value="${balloonPayment.repayTotInt}" />
					   
					   <div class="form-group">
						  <form:label path="repayTotInt" cssClass="col-sm-3 control-label">Interest Amount (Total)::</form:label>
						  <div class="col-sm-8">
							 <form:input path="repayTotInt" value="${intTotal}" readonly="true"/>
							 <form:errors path="repayTotInt" cssClass="error"></form:errors>
						  </div>
					   </div>
					   <fmt:formatNumber type="number" maxFractionDigits="2" var="loanTotal" value="${balloonPayment.repayTotAmt}" />

					   <div class="form-group">
						  <form:label path="repayTotAmt" cssClass="col-sm-3 control-label">Loan Amount:</form:label>
						  <div class="col-sm-8">
							 <form:input path="repayTotAmt" value="${loanTotal}"  readonly="true"/>
							 <form:errors path="repayTotAmt" cssClass="error"></form:errors>
						  </div>
					   </div>
					   
					   <div class="form-group">
						<button class="btn btn-danger mr5" type="submit"> CONTINUE </button>
						<button type="reset" class="btn btn-default">RESET</button>
					</div><!-- panel-footer -->
				  </form:form>
		        </div>

             </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->

<%@ include file="includes/footer.jsp" %>
