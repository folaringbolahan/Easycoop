<%@ include file="includes/header.jsp" %> 
<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Loan Request Management</li>
    </ul>
    <h4>Manage Loan Request - Step 2</h4>
</div>
<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
  <script lanuage="Javascript">
	function doSubmit(frm){	
	    if(frm.companyId.value==""){alert("please select cooperative"); frm.companyId.focus(); return;}
	    if(frm.companyId.value==""){alert("please select branch"); frm.branchId.focus(); return;}
	    if(frm.memberNo.value==""){alert("please select customer"); frm.memberNo.focus(); return;}
	    if(frm.loanType.value==""){alert("product is required"); frm.loanType.focus(); return;}
	    if(frm.requestedAmount.value==""  || frm.requestedAmount.value=="0.0"){alert("amount is required"); frm.requestedAmount.focus(); return;}
	    if(frm.repayFrequency.value=="" || frm.repayFrequency.value=="0"){alert("please select repayment mode"); frm.repayFrequency.focus(); return;}
	    
	    if(frm.productRate.value==""){alert("please enter rate"); frm.productRate.focus(); return;}
	    if(frm.duration.value=="" || frm.duration.value=="0"){alert("please specify valid duration (months)"); frm.duration.focus(); return;}

	    if(frm.proposedCommencementDate.value==""){alert("please specify loan commencement date"); frm.proposedCommencementDate.focus(); return;}
	    var gcount=frm.guarantorCount.value;
	    
	    if(gcount>0){
		    for(tj=1; tj<=gcount; tj++){
		      var fieldName="guarantor_" + tj;		      
		      if(frm[fieldName].value==""){alert("Please specify guarantor"); return;}		      
		    }
	    }	    
	    
	    if(gcount>0){
		    for(tj=1; tj<=gcount; tj++){
		      var fieldValue=frm["guarantor_" + tj].value;		      
		      
		      for(jt=1; jt<=gcount; jt++){
		        if(jt!=tj){
		           if(frm["guarantor_" + jt].value==fieldValue){alert("You cannot pick guarantor multiple times"); return;}
		        }		        
		      }
		    }
	    }
	    
	    frm.submit();
	}	  
  </script>
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

			<!--
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
									    html += '<input type="text" value="' + data[i].interestRate + '" id="productRate" name="productRate"/>';
									}
								}

								//alert(html);					
								$('#productInfo').html(html);
							});
						});
				      });
			 </script>	 
		      -->
		      
		         <form:form method="post" action="newLoanRequest1.htm" commandName="loanRequest">
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
					<form:option value="">--select--</form:option>
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
					 <form:option value="">--select--</form:option>
					 <c:forEach items="${branches}" var="item">  
						<form:option value="${item.id}">${item.branchName}</form:option>
					 </c:forEach>
				      </form:select>
				  </div>  
			   </div>			   

			   <div class="form-group">   
				  <form:label path="memberNo" cssClass="col-sm-3 control-label">Requesting Member:</form:label>
				  <div class="col-sm-8">
				      <form:select id="memberNo" path="memberNo"  cssClass="width300" >
					 <form:option value="">--select--</form:option>
					 <c:forEach items="${membersInc}" var="item">  
						<form:option value="${item.memberId}">${item.memberNo} -> ${item.surname},${item.firstname}</form:option>
					 </c:forEach>
				      </form:select>
				  </div>  
			   </div>
			   
			   <div class="form-group">   
			     <td colspan="2">
				  <div id="memberSummaryContainer"></div
			      </td>  
			   </div> 
			  			  
			   <div class="form-group">   
				  <form:label path="loanType" cssClass="col-sm-3 control-label">Select Product *</form:label> 
				  <div class="col-sm-8">
				      <select id="loanTypeX" disabled cssClass="width300" >
					 <c:forEach items="${products}" var="item">  
					     <option value="${item.code}">${item.name}</option>
					 </c:forEach>
				      </select>
				  </div>  
			   </div>         
			  			  
			   <div class="form-group">   
				  <form:label path="requestedAmount" cssClass="col-sm-3 control-label">Requested Amount *:</form:label> 
				  <div class="col-sm-8">
				     <form:input path="requestedAmount" value="${loanRequest.requestedAmount}" data-toggle="tooltip" title="Loan Amount that customer is applying for" cssClass="tooltips"/>
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
					<%--
					     <div id="productInfo">
							<form:input id="interestType" path="interestType" value="${loanRequest.interestType}" readOnly="true" data-toggle="tooltip" title="Interest calculation code for this Loan product" cssClass="tooltips"/>
					     </div>				        
				     	 --%>
				     	
				     	<form:select id="interestType" path="interestType" cssClass="width300" >
						<c:forEach items="${interestTypes}" var="item">  
						    <form:option value="${item.typeCode}">${item.typeName}</form:option>
						</c:forEach>
				        </form:select>				        
				     
				  </div>  
			   </div> 

			   <div class="form-group">   
				  <form:label path="repayFrequency" cssClass="col-sm-3 control-label">Repayment Frequency *</form:label>  
				  <div class="col-sm-8">
				      <form:select id="repayFrequency" path="repayFrequency" cssClass="width300">
					 <form:option value="0">--select--</form:option>
					 <c:forEach items="${loanRepayFreqs}" var="item">  
					     <form:option value="${item.code}">${item.name}</form:option>
					 </c:forEach>
				      </form:select>
				  </div>  
			   </div> 
			   
			   <div class="form-group">   
				  <form:label path="duration" cssClass="col-sm-3 control-label">Duration (Months) *:</form:label>  
				  <div class="col-sm-8">
				     <form:input path="duration" value="${loanRequest.duration}" data-toggle="tooltip" title="Periods in months that the loan will be re-paid" cssClass="tooltips"/>
				  </div>  
			   </div>
			
			   <div class="form-group">   
				  <form:label path="proposedCommencementDate" cssClass="col-sm-3 control-label">Commencement Date *:</form:label>  
				  <div class="col-sm-4">
				     <div class="input-group">
				     	<form:input path="proposedCommencementDate" value="${loanRequest.proposedCommencementDate}" class="form-control period tooltips"  placeholder="dd/mm/yyyy" data-toggle="tooltip" title="Customer proposed start date for loan repayment"/>
				      	<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
				      </div>
				  </div>  
			   </div>
			
			<%--
			   <div class="form-group">   
				  <form:label path="noOfInstallments" cssClass="col-sm-3 control-label">No Of Installments *:</form:label>  
				  <div class="col-sm-8">
				     <form:input path="noOfInstallments" value="${loanRequest.noOfInstallments}" readonly="true"/>
				  </div>  
			   </div>
			--%> 			   
			   
			   <c:forEach var="i" begin="1" end='${configMap.get("MAX_GUA_NUM")}'>
				   <div class="form-group">   
					  <form:label path="memberNo" cssClass="col-sm-3 control-label">select Guarantor <c:out value="${i}" /></form:label>
					  <div class="col-sm-5">
					      <select id="guarantor_${i}" name="guarantor_${i}" class="width300">
						 <option value="">--select--</option>
						 <c:forEach items="${members}" var="item">  
							<option value="${item.memberId}">${item.memberNo} -> ${item.surname},${item.firstname}</option>
						 </c:forEach>
					      </select>			      
					  </div>  
				   </div>
                           </c:forEach>			

			   <input type="hidden" name="guarantorCount" value='${configMap.get("MAX_GUA_NUM")}'>
			   <form:hidden path="loanType" id="loanType"  value="${loanRequest.loanType}"/>
			   
			   <div class="form-group">
				    <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">CONTINUE </button>
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
		<td><c:out value="${loan.approvedAmount}"/></td>  
		<td><c:out value="${loan.appliedRate}"/></td> 
   		<td><c:out value="${loan.loanCaseId}"/></td>  
   		<td><c:out value="${loan.loanStatusDesc}"/></td>  
   		<td align="center"><a href="viewLoanRequestDetails.htm?id=${loan.id}">VIEW</a> <c:if test="${loan.requestStatus}=='E'"> &nbsp; || &nbsp;  <a href="updateLoanRequest0.htm?id=${loan.id}">UPDATE</a> </c:if></td>  
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
