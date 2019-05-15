<%@ include file="includes/header.jsp" %> 
<div class="media-body">
    <div style="float:left">
	    <ul class="breadcrumb">
		<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		<li><a href="#">Home</a></li>
		<li>Loan Request Management</li>
	    </ul>
    	    <h4>Edit Loan Details</h4>
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

								//alert("len:="+len);
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
		      
		         <form:form method="post" action="updateLoanRequest.htm" commandName="loanRequest">
			   <%--
			   <div class="form-group">   
				  <form:label path="id" cssClass="col-sm-3 control-label"> ID:</form:label>  
				  <div class="col-sm-8">
				     <form:input path="id" value="${loanRequest.id}" readonly="true"/>
				  </div>  
			   </div> 
			   --%>
			   <HR/>
			   <div class="form-group">   
				  <form:label path="loanCaseId" cssClass="col-sm-4 control-label">Loan Case Id:</form:label>
				  <div class="col-sm-8">
				    <form:input path="loanCaseId" value="${loanRequest.loanCaseId}" readonly="true"/>
				  </div>  
			   </div> 
			   
			   <div class="form-group">   
				  <form:label path="companyId" cssClass="col-sm-4 control-label">Company:</form:label> 
				  <div class="col-sm-8">
				      <form:select id="companyId" path="companyId" >
					<c:forEach items="${companies}" var="item">  
					     <form:option value="${item.id}">${item.name}</form:option>
					</c:forEach>
				      </form:select>
				  </div>  
			   </div> 
			   
			   <div class="form-group">   
				  <form:label path="branchId" cssClass="col-sm-4 control-label">Branch</form:label>
				  <div class="col-sm-8">
				      <form:select id="branchId" path="branchId">
					 <c:forEach items="${branches}" var="item">  
						<form:option value="${item.id}">${item.branchName}</form:option>
					 </c:forEach>
				      </form:select>
				  </div>  
			   </div>			   

			   <div class="form-group">   
				  <form:label path="memberNo" cssClass="col-sm-4 control-label">Requesting Member:</form:label>
				  <div class="col-sm-8">
				      <form:select id="memberNo" path="memberNo">
					 <c:forEach items="${membersInc}" var="item">  
						<form:option value="${item.memberId}">${item.memberNo} -> ${item.surname},${item.firstname}</form:option>
					 </c:forEach>
				      </form:select>
				  </div>  
			   </div>			   
			  <HR/> 
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
				    <div class="col-sm-4"><div class="error">
                                            <fmt:formatNumber type="number" maxFractionDigits="2" value="${memberSummaryBean.runningLoanSum}" />
                                            </div></div>
				</div>
			   </div> 
			  <HR/> 
			   <div class="form-group">   
				  <form:label path="loanType" cssClass="col-sm-4 control-label">Select Product</form:label> 
				  <div class="col-sm-8">
				      <form:select id="loanType" path="loanType">
					 <c:forEach items="${products}" var="item">  
					     <form:option value="${item.code}">${item.name}</form:option>
					 </c:forEach>
				      </form:select>
				  </div>  
			   </div>         

			   <div class="form-group">   
				  <form:label path="requestDate" cssClass="col-sm-4 control-label">Request Date:</form:label> 
				  <div class="col-sm-8">
				     <fmt:formatDate pattern="dd/MM/yyyy" value="${loanRequest.requestDate}"/>
				  </div>  
			   </div> 
			   
			   <div class="form-group">   
                               <fmt:formatNumber type="number" maxFractionDigits="2" value="${loanRequest.requestedAmount}" var="pcRqtAmtVar"/>
				  <form:label path="requestedAmount" cssClass="col-sm-4 control-label">Requested Amount:</form:label> 
				  <div class="col-sm-8">
				     <form:input path="requestedAmount" value="${pcRqtAmtVar}"/>
				  </div>  
			   </div> 
			   
			   <div class="form-group">  
				  <form:label path="productRate" cssClass="col-sm-4 control-label">Product Rate:</form:label> 
				  <div class="col-sm-8">
				     <div id="productInfo">
				     	  <form:input id="productRate" path="productRate" value="${loanRequest.productRate}" readOnly="true"/>
				     </div>
				  </div>  
			   </div>  
			   
			   <div class="form-group">   
				  <form:label path="interestType" cssClass="col-sm-4 control-label">Interest Type </form:label>  
				  <div class="col-sm-8">
				      <form:select id="interestType" path="interestType">
					 <c:forEach items="${interestTypes}" var="item">  
					     <form:option value="${item.typeCode}">${item.typeName}</form:option>
					 </c:forEach>
				      </form:select>
				  </div>  
			   </div> 
			   
			   <div class="form-group">   
				  <form:label path="repayFrequency" cssClass="col-sm-4 control-label">Repayment Frequency</form:label>  
				  <div class="col-sm-8">
				      <form:select id="repayFrequency" path="repayFrequency">
					 <c:forEach items="${loanRepayFreqs}" var="item">  
					     <form:option value="${item.code}">${item.name}</form:option>
					 </c:forEach>
				      </form:select>
				  </div>  
			   </div>

			   <div class="form-group">   
				  <form:label path="duration" cssClass="col-sm-4 control-label">Duration (Months):</form:label>  
				  <div class="col-sm-8">
				     <form:input path="duration" value="${loanRequest.duration}"/>
				  </div>  
			   </div>
			  
			  <%--
			   <div class="form-group">   
				  <form:label path="noOfInstallments" cssClass="col-sm-4 control-label">No Of Installments:</form:label>  
				  <div class="col-sm-8">
				     <form:input path="noOfInstallments" value="${loanRequest.noOfInstallments}"/>
				  </div>  
			   </div>
			  --%>
			   
			   <fmt:formatDate pattern="dd/MM/yyyy" value="${loanRequest.proposedCommencementDate}" var="pcDateVar"/>
			   
			   <div class="form-group">   
				  <form:label path="proposedCommencementDate" cssClass="col-sm-4 control-label">Commencement Date:</form:label>  
				  <div class="col-sm-4">
				     <div class="input-group">
				     	<form:input path="proposedCommencementDate" value="${pcDateVar}" class="form-control period"  placeholder="dd/mm/yyyy" />
				      	<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
				      </div>
				  </div>  
			   </div>
			  
			   <c:set var="counter" value="0" />
			   <c:if test="${not empty(exceptions)}">
			           <HR/>
				   <div class="form-group">   
					  <div>&nbsp;&nbsp;<<<<b>EXCEPTIONS</b>>>></div>			  
				   </div> 		   
			   	   <HR/>		   
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
                           <br/>
                           <HR size="2"/>
                           <c:if test="${not empty(guarantorsSummaryBeans)}">
				   <div class="form-group">   
					<div>&nbsp;&nbsp;<<<<<b>LOAN GUARANTORS</b> >>></div>			  
				   </div>                           
				   <HR/>
				   <c:set var="counter1" value="0" />
				   <c:forEach items="${guarantorsSummaryBeans}" var="item">
					   <div class="form-group">   
						<div class="form-group">
						    <div class="col-sm-4 control-label"><div class="error">Guarantor Name</div></div>
						    <div class="col-sm-8"><div class="greenH"><c:out value="${item.memberName}"/></div></div>
						</div> 

						<div class="form-group"> 
						    <div class="col-sm-4 control-label"><div class="error">Running Loans Count</div></div>
						    <div class="col-sm-8"><div class="greenH"><c:out value="${item.runningLoanCount}"/></div></div>
						</div> 

						<div class="form-group">
						    <div class="col-sm-4 control-label"><div class="error">Running Loans Sum</div></div>
						    <div class="col-sm-8"><div class="greenH">
                                                              <fmt:formatNumber type="number" maxFractionDigits="2" value="${item.runningLoanSum}" />
                                                            </div></div>
						</div>
					   </div>
					   <HR/>
					   <c:set var="counter1" value="${counter1+1}" /> 
					   <input type="hidden" name="guarantor_${counter1}" value="${item.memberId}">
				   </c:forEach>	

				   <input type="hidden" name="guarantorCount" value="${counter1}">
			   </c:if>
			   
			   <form:hidden path="loanStatus" value="P"/>
			   <input type="hidden" name="ACTION_ID" value="3">
			   <form:hidden path="id" value="${loanRequest.id}"/>

			   <form:hidden path="lastModificationDate" id="lastModificationDate" name="lastModificationDate"   value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>

			   <div class="form-group">
				    <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">UPDATE </button>
				    <button type="reset" class="btn btn-default">RESET</button>
			   </div><!-- panel-footer --> 
	         </form:form>  
	         <!--  -->
	         
	         <!-- Trigger the modal with a button -->
		 <button type="button" class="btn btn-default btn-lg" data-toggle="modal" data-target="#myModal">VIEW GUARANTORS</button>
		 
		 <!-- Modal -->
		 <div id="myModal" class="modal fade" role="dialog">
		   <div class="modal-dialog">
		 
		     <!-- Modal content-->
		     <div class="modal-content">
		       <div class="modal-header">
		         <button type="button" class="close" data-dismiss="modal">&times;</button>
		         <h4 class="modal-title">LOAN GUARANTORS</h4>
		       </div>
		       <div class="modal-body">
		         <p><c:set var="counter" value="0" />
			   <c:if test="${not empty(guarantorsSummaryBeans)}">
				<div class="form-group">   
				     <c:set var="counter1" value="0" />
				      <c:forEach items="${guarantorsSummaryBeans}" var="item">
					   <div class="form-group">   
						<div class="form-group">
						    <div class="col-sm-4 control-label"><div class="error">Guarantor Name</div></div>
						    <div class="col-sm-4"><div class="greenH"><c:out value="${item.memberName}"/></div></div>
						</div> 

						<div class="form-group"> 
						    <div class="col-sm-4 control-label"><div class="error">Running Loans Count</div></div>
						    <div class="col-sm-4"><div class="greenH"><c:out value="${item.runningLoanCount}"/></div></div>
						</div> 

						<div class="form-group">'; 
						    <div class="col-sm-4 control-label"><div class="error">Running Loans Sum</div></div>
						    <div class="col-sm-4"><div class="greenH"><c:out value="${item.runningLoanSum}"/></div></div>
						</div>
					   </div>
					   <HR/>
					   <c:set var="counter1" value="${counter1+1}" /> 
					   <input type="hidden" name="guarantor_${counter1}" value="${item.memberId}">
				   </c:forEach>	
				   			   
			   <input type="hidden" name="guarantorCount" value="${counter1}">
                           </c:if></p>
		       </div>
		       <div class="modal-footer">
		         <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		       </div>
		     </div>
		 
		   </div>
		</div>
	         <!--  -->
	         
               </div>
            </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->
<%@ include file="includes/footer.jsp" %> 
<script>
    $(document).ready(function(){
         jQuery('.period').datepicker(); 
	          
	 $.datepicker.setDefaults({
	      dateFormat: 'dd/mm/yy'
	 });

    })
</script>

