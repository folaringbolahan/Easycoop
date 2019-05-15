<%@ include file="includes/header.jsp" %> 
<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Loan Request Management</li>
    </ul>
    <h4>Manage Loan Request - Step 3</h4>
</div>
<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<div class="contentpanel">
    <div class="row">
        <div class="col-md-10">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
	       <div class="panel-body">
                 <c:set var="now" value="<%=new java.util.Date()%>" />
		      
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-i18n.min.js"></script> 
                                            
		      <script type="text/javascript">
				      $(document).ready(function(){	        
					$('select#companyId').change(		        
						function(){
							alert($(this).val());
							$.getJSON('branchesAjaxList.do',{
								companyId : $(this).val(),
								ajax : 'true'
							}, function(data) {
								var len = 0;
								var html ='';

								if(data!=null){
								   len=data.length;
								}

								alert("len:="+len);
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

			  <script type="text/javascript">
				      $(document).ready(function(){	        
					$('select#loanType').change(		        
						function(){
							alert($(this).val());
							$.getJSON('getProductRate.do',{
								loanType : $(this).val(),
								ajax : 'true'
							}, function(data) {
								var len = 0;
								var html ='';

								if(data!=null){
								   len=data.length;
								}

								alert("len:="+len);
								if(len>0){
									for( var i = 0; i < len; i++){
									    html += '<input type="text" value="' + data[i].interestRate + '" id="productRate" />';
									}
								}

								//alert(html);					
								$('#productInfo').html(html);
							});
						});
				      });
			 </script>	 
		      
		         <form:form method="post" action="saveLoanRequest.htm" commandName="loanRequest">
			   <%--
			   <div class="form-group">   
				  <form:label path="id" cssClass="col-sm-3 control-label"> ID:</form:label>  
				  <div class="col-sm-8">
				     <form:input path="id" value="${loanRequest.id}" readonly="true"/>
				  </div>  
			   </div> 
			   --%>
			   
			   <div class="form-group">   
				  <form:label path="loanCaseId" cssClass="col-sm-3 control-label">Loan Case Id:</form:label>
				  <div class="col-sm-8">
				    <form:input path="loanCaseId" value="${loanRequest.loanCaseId}" readonly="true" data-toggle="tooltip" title="Unique identifier for the Loan Request" cssClass="tooltips"/>
				  </div>  
			   </div> 
			   
			   <div class="form-group">   
				  <form:label path="companyId" cssClass="col-sm-3 control-label">Cooperative:</form:label> 
				  <div class="col-sm-8">
				      <form:select id="companyId" path="companyId" cssClass="width300" >
					<c:forEach items="${companies}" var="item">  
					     <form:option value="${item.id}">${item.name}</form:option>
					</c:forEach>
				      </form:select>
				  </div>  
			   </div> 
			   
			   <div class="form-group">   
				  <form:label path="branchId" cssClass="col-sm-3 control-label">Branch</form:label>
				  <div class="col-sm-8">
				      <form:select id="branchId" path="branchId" cssClass="width300" >
					 <c:forEach items="${branches}" var="item">  
						<form:option value="${item.id}">${item.branchName}</form:option>
					 </c:forEach>
				      </form:select>
				  </div>  
			   </div>			   

			   <div class="form-group">   
				  <form:label path="memberNo" cssClass="col-sm-3 control-label">Requesting Member:</form:label>
				  <div class="col-sm-8">
				      <form:select id="memberNo" path="memberNo" cssClass="width300" >
					 <c:forEach items="${membersInc}" var="item">  
						<form:option value="${item.memberId}">${item.memberNo} -> ${item.surname},${item.firstname}</form:option>
					 </c:forEach>
				      </form:select>
				  </div>  
			   </div>			   
			   
			   <div class="form-group">   
				<div class="form-group">
				    <div class="col-sm-3 control-label"><div class="error">Member Name</div></div>
				    <div class="col-sm-8"><div class="error"><c:out value="${memberSummaryBean.memberName}"/></div></div>
				</div> 
				
				<div class="form-group"> 
				    <div class="col-sm-3 control-label"><div class="error">Running Loans Count</div></div>
				    <div class="col-sm-8"><div class="error"><c:out value="${memberSummaryBean.runningLoanCount}"/></div></div>
				</div> 
				
				<div class="form-group"> 
				    <div class="col-sm-3 control-label"><div class="error">Running Loans Sum</div></div>
				    <div class="col-sm-8"><div class="error"> <fmt:formatNumber type="number" maxFractionDigits="2" value="${memberSummaryBean.runningLoanSum}" />
                                           </div></div>
				</div>
				
				<div class="form-group">
				    <div class="col-sm-3 control-label"><div class="error">Total Savings/Contribution</div></div>
				    <div class="col-sm-8"><div class="error">
                                            <fmt:formatNumber type="number" maxFractionDigits="2" value="${memberSummaryBean.totalMemberContribution}" />
                                            </div></div>
				</div>
			   </div> 
			   
			   <div class="form-group">   
				  <form:label path="loanType" cssClass="col-sm-3 control-label">Select Product *</form:label> 
				  <div class="col-sm-8">
				      <form:select id="loanType" path="loanType" cssClass="width300" >
					 <c:forEach items="${products}" var="item">  
					     <form:option value="${item.code}">${item.name}</form:option>
					 </c:forEach>
				      </form:select>
				  </div>  
			   </div>         

			   <div class="form-group">                                 
				  <form:label path="requestedAmount" cssClass="col-sm-3 control-label">Requested Amount *:</form:label> 
				  <div class="col-sm-8">
				     <form:input path="requestedAmount" value="${loanRequest.requestedAmount}"/>
				  </div>  
			   </div> 
			   
			   <div class="form-group">  
				  <form:label path="productRate" cssClass="col-sm-3 control-label">Product Rate *:</form:label> 
				  <div class="col-sm-8">
				     <div id="productInfo">
				     	  <form:input id="productRate" path="productRate" value="${loanRequest.productRate}" data-toggle="tooltip" title="Interest rate attached to this Loan product" cssClass="tooltips"/>
				     </div>
				  </div>  
			   </div>  
			   
			   <div class="form-group">  
				  <form:label path="interestType" cssClass="col-sm-3 control-label">Interest Code *:</form:label> 
				  <div class="col-sm-8">
				     <div id="productInfo">
				     	  <form:input id="interestType" path="interestType" value="${loanRequest.interestType}" readOnly="true" data-toggle="tooltip" title="Interest calculation code for this Loan product" cssClass="tooltips"/>
				     </div>
				  </div>  
			   </div> 
			
			<%--
			   <div class="form-group">   
				  <form:label path="interestType" cssClass="col-sm-3 control-label">Interest Type *</form:label>  
				  <div class="col-sm-8">
				      <form:select id="interestType" path="interestType">
					 <c:forEach items="${interestTypes}" var="item">  
					     <form:option value="${item.typeCode}">${item.typeName}</form:option>
					 </c:forEach>
				      </form:select>
				  </div>  
			   </div> 
			 --%>
			 
			   <div class="form-group">   
				  <form:label path="repayFrequency" cssClass="col-sm-3 control-label">Repayment Frequency *</form:label>  
				  <div class="col-sm-8">
				      <form:select id="repayFrequency" path="repayFrequency" cssClass="width300" >
					 <c:forEach items="${loanRepayFreqs}" var="item">  
					     <form:option value="${item.code}">${item.name}</form:option>
					 </c:forEach>
				      </form:select>
				  </div>  
			   </div> 
			   
			   <fmt:formatDate pattern="dd/MM/yyyy" value="${loanRequest.proposedCommencementDate}" var="pcDateVar"/>

			   <div class="form-group">   
				  <form:label path="proposedCommencementDate" cssClass="col-sm-3 control-label">Commencement Date*:</form:label>  
				  <div class="col-sm-5">
				     <div class="input-group">
				     	<form:input path="proposedCommencementDate"   value="${pcDateVar}" class="form-control period"  placeholder="dd/mm/yyyy" />
				      	<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
				      </div>
				  </div>  
			   </div>   
			   
			 
			   <div class="form-group">   
				  <form:label path="duration" cssClass="col-sm-3 control-label">Duration (Months) *:</form:label>  
				  <div class="col-sm-8">
				     <form:input path="duration" value="${loanRequest.duration}" data-toggle="tooltip" title="Period of repayment in months" cssClass="tooltips" readOnly="true"/>
				  </div>  
			   </div>
			 
			   <div class="form-group">   
				  <form:label path="noOfInstallments" cssClass="col-sm-3 control-label">No Of Installments *:</form:label>  
				  <div class="col-sm-8">
				     <form:input path="noOfInstallments" value="${loanRequest.noOfInstallments}" readonly="true" data-toggle="tooltip" title="Number of Installments in the specified Loan" cssClass="tooltips"/>
				  </div>  
			   </div>
			   
			   <c:set var="counter" value="0" />
			   <c:if test="${not empty(exceptions)}">
				   <div class="form-group">   
					  <div class="greenH">&nbsp; EXCEPTIONS</div>			  
				   </div> 		   
			   			   
				   <div class="form-group"> 
					<c:forEach items="${exceptions}" var="item" >
						<div class="form-group">
						    <div class="col-sm-3 control-label"><div class="greenH">Exception Detail(s)</div></div>
						    <div class="col-sm-8"><div class="error"><c:out value="${item.exceptionMessage}"/></div></div>
						</div> 
						<c:set var="counter" value="${counter+1}" /> 
						
						<input type="hidden" name="exception_${counter}" value="${item.exceptionMessage}">
					</c:forEach>	
				   </div>
                           </c:if>
                           
                           <input type="hidden" name="exceptionCount" value="${counter}">
                           
			   <div class="form-group">   
			        <div><div class="greenH">&nbsp; LOAN GUARANTORS</div></div>			  
			   </div>                           
                           
                           <c:set var="counter1" value="0" />
                           <c:forEach items="${guarantorsSummaryBeans}" var="item">
				   <div class="form-group">   
					<div class="form-group">
					    <div class="col-sm-3 control-label"><div class="error">Guarantor Name</div></div>
					    <div class="col-sm-8"><div class="greenH"><c:out value="${item.memberName}"/></div></div>
					</div> 

					<div class="form-group"> 
					    <div class="col-sm-3 control-label"><div class="error">Running Loans Count</div></div>
					    <div class="col-sm-8"><div class="greenH"><c:out value="${item.runningLoanCount}"/></div></div>
					</div> 

					<div class="form-group"> 
					    <div class="col-sm-3 control-label"><div class="error">Running Loans Sum</div></div>
					    <div class="col-sm-8"><div class="greenH">
                                               <fmt:formatNumber type="number" maxFractionDigits="2" value="${item.runningLoanSum}" />
                                                </div></div>
					</div>
					
					<div class="form-group">
					    <div class="col-sm-3 control-label"><div class="error">Total Savings/Contribution</div></div>
					    <div class="col-sm-8"><div class="greenH">
                                                   <fmt:formatNumber type="number" maxFractionDigits="2" value="${item.totalMemberContribution}" />                                                 
                                                </div></div>
					</div>
				     </div>
				   
				   <c:set var="counter1" value="${counter1+1}" /> 
				   <input type="hidden" name="guarantor_${counter1}" value="${item.memberId}">
				   <hr/>
			   </c:forEach>	
			   
			   <input type="hidden" name="guarantorCount" value="${counter1}">
			   
			   <form:hidden path="loanStatus" value="P"/>
			   <form:hidden path="requestStatus" value="E"/>
			   <form:hidden path="requestBy" value="<%=request.getRemoteUser()%>"/>
			   <form:hidden path="lastModifiedBy" value="<%=request.getRemoteUser()%>"/>

			   <form:hidden path="requestDate" id="requestDate" name="requestDate" data-format="dd/MM/yyyy"  value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>
			   <form:hidden path="lastModificationDate" id="lastModificationDate" name="lastModificationDate"   value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>

			   <div class="form-group">
				    <button class="btn btn-danger mr5" type="submit">SUBMIT </button>
				    <button type="reset" class="btn btn-default">RESET</button>
			    </div><!-- panel-footer --> 
	         </form:form>            
               </div>
            </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->

   <c:if test="${!empty loanRequests}">  
             <div class="media-body">
                <h4>LIST OF LOAN REQUESTS</h4>
             </div>
   	     <table id="loans-list" class="table table-striped table-bordered responsive">  
   		 <thead>
   	      <tr>  
   	       <th>ID</th>  
	<%--
   	       <th>Cooperative</th>  
   	       <th>Branch</th>  
   	--%> 
   	       <th>Member Name</th>  
   	       <th>Amount</th>  
   	       <th>Interest Rate</th>  
   	       <th>Loan Case Id</th>  
   	       <th>Request Status </th> 
   	       <th>Loan Status </th> 
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
		<td><c:out value="${loan.requestedAmount}"/></td>  
		<td><c:out value="${loan.appliedRate}"/></td> 
   		<td><c:out value="${loan.loanCaseId}"/></td>  
   		<td><c:out value="${loan.loanStatusDesc}"/></td>  
   		<td align="center"><a href="viewLoanRequestDetails.htm?id=${loan.id}">VIEW</a><c:if test="${loan.requestStatus}=='E'"> &nbsp; || &nbsp;  <a href="updateLoanRequest0.htm?id=${loan.id}">UPDATE</a> </c:if></td>  
   	       </tr>  
   	      </c:forEach>  
   	       </tbody>
   	     </table>  
    </c:if>  
   
<%@ include file="includes/footer.jsp" %>  
<script>
    $(document).ready(function(){
         jQuery('.period').datepicker(); 
	          
	 $.datepicker.setDefaults({
	      dateFormat: 'dd/mm/yy'
	 });

         jQuery('select').select2({
                    minimumResultsForSearch: -1
                });
				 jQuery('#loans-list').DataTable({
                    responsive: true
                });
    })
</script>
