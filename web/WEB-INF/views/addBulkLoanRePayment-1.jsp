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
					}, function(data){
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
    
 <script lanuage="Javascript">
 	function doSubmit(frm){	
 	    var itemsCount=frm.scheduleIds.length;
 	    var isSelected=0;
 	    
 	    for(var k=0; k<itemsCount; k++){
 	       if(frm.scheduleIds[k].checked){isSelected=1;}
 	    }
 	    
 	    if(isSelected==0){alert("Please select at least an item");  return;} 
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
				  <form:form method="post" action="newBulkLoanRePayment-2.htm" commandName="bulkLoanRePayment">
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
					   
					   <fmt:formatNumber type="number" maxFractionDigits="2" var="PrlTotal" value="${balloonPayment.repayTotPrl}" />
					   <c:set var="DISABLE" scope="page" value='class="btn btn-default" disable="true"' />
					   
					   <div class="form-group">					  
						 <c:if test="${!empty schedules}">  
						     <c:set var="count" scope="page" value="0" />
						     <c:set var="DISABLE" scope="page" value='class="btn btn-danger mr5" disable="true"' />
						     <div class="media-body">
							<h4>LOAN REPAYMENT SCHEDULE - VIEW </h4>
						     </div>

						      <table id="schedule-list" class="table table-striped table-bordered responsive">  
							 <thead>
							      <tr>  
							       <th>ID</th>  
							       <th>Interest</th>  
							       <th>Principal</th>  
							       <th>Amount</th>  
							       <th>Date Due</th>
							      </tr>  
							</thead>
							<tbody>
							      <c:forEach items="${schedules}" var="item">  
									<c:set var="count" scope="page" value="${count + 1}" />						  					       
									<tr>  
										<td><c:out value="${count}"/> &nbsp;&nbsp; <input id="scheduleIds" type="checkbox" name="scheduleIds" value="${item.id}"/></td>  
										<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${item.amountInterest}" /></td>  
										<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${item.amountPrincipal}"/></td>  
										<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${item.expectedRepaymentAmount}"/></td>  
										<td><fmt:formatDate pattern="dd/MM/yyyy" value="${item.expectedRepaymentDate}"/></td>						  						  
								       </tr>  
							      </c:forEach>  
							 </tbody>
						    </table>  
					       </c:if>    
					   </div>					   
					   
					   <div class="form-group">
						<button ${DISABLE} type="button" onclick="Javascript:doSubmit(this.form);"> CONTINUE </button>
						<button type="reset" class="btn btn-danger mr5" onclick="Javascript:history.back();">BACK</button>
					</div><!-- panel-footer -->
				  </form:form>
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
