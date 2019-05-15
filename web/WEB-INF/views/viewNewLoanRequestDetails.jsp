<%@ include file="includes/header.jsp" %> 
<div class="media-body">
   <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="#">Home</a></li>
            <li>Loan Request Details View</li>
        </ul>
        <h4>View Loan Request Details</h4>
    </div>
<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
  <script lanuage="Javascript">
	function doSubmit(frm){	
	   
	    var gcount=frm.guarantorCount.value;
	    
	    if(gcount>0){
		    for(tj=1; tj<=gcount; tj++){
		      var fieldName="guarantor_" + tj;		      
		      if(frm[fieldName].value==""){alert("Please specify guarantor"); return;}		      
		    }
	    }	    
	    
	    if(gcount>0){
		    for(tj=1; tj<=gcount; tj++){
		      var fieldValue=frm["guarantor_${counter1}" + tj].value;		      
		      
		      for(jt=1; jt<=gcount; jt++){
		        if(jt!=tj){
		           if(frm["guarantor_${counter1}" + jt].value==fieldValue){alert("You cannot pick guarantor multiple times"); return;}
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
		      
		        
                    <form:form method="POST" action="saveReplacedGuarantor" commandName="loanRequest">

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
                            <form:label path="companyId" cssClass="col-sm-4 control-label">Cooperative:</form:label> 
                                <div class="col-sm-8">
                                <form:select id="companyId" path="companyId">
                                    <c:forEach items="${companies}" var="item">  
                                        <form:option value="${item.id}">${item.name}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>  
                        </div> 

                        <div class="form-group">   
                            <form:label path="branchId" cssClass="col-sm-4 control-label">Branch</form:label>
                                <div class="col-sm-8">
                                <form:select id="branchId" path="branchId" >
                                    <c:forEach items="${branches}" var="item">  
                                        <form:option value="${item.id}">${item.branchName}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>  
                        </div>			   

                        <div class="form-group">   
                            <form:label path="memberNo" cssClass="col-sm-4 control-label">Requesting Member:</form:label>
                                <div class="col-sm-8">
                                <form:select id="memberNo" path="memberNo" >
                                    <c:forEach items="${membersInc}" var="item">  
                                        <form:option value="${item.memberId}">${item.compmemberId} -> ${item.surname},${item.firstname}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>  
                        </div>			   

                        <HR/> 
                        <div class="form-group">   
                            <div class="form-group">
                                <div class="col-sm-4 control-label"><div class="error">Member Name</div></div>
                                <div class="col-sm-4"><div class="error"><c:out value="${memberSummaryBean.memberName}"/></div></div>
                            </div> 

                            <div class="form-group"> 
                                <div class="col-sm-4 control-label"><div class="error">Running Loans Count</div></div>
                                <div class="col-sm-4"><div class="error"><c:out value="${memberSummaryBean.runningLoanCount}"/></div></div>
                            </div> 

                            <div class="form-group"> 
                                <div class="col-sm-4 control-label"><div class="error">Running Loans Sum</div></div>
                                <div class="col-sm-4"><div class="error"><c:out value="${memberSummaryBean.runningLoanSum}"/></div></div>
                            </div> 
                              <div class="form-group">
                                <div class="col-sm-4 control-label"><div class="error">Running Loan Balance</div></div>
                                <div class="col-sm-4"><div class="error"><fmt:formatNumber type="number" maxFractionDigits="2" value="${memberSummaryBean.runningLoanBalance}" /> </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-4 control-label"><div class="error">Total Savings/Contribution</div></div>
                                <div class="col-sm-4">
                                    <div class="error">
                                        <fmt:formatNumber type="number" maxFractionDigits="2" value="${memberSummaryBean.totalMemberContribution}" />
                                    </div>
                                </div>
                            </div>
                        </div> 
                        <HR/> 
                        <div class="form-group">   
                            <form:label path="loanType" cssClass="col-sm-4 control-label">Product</form:label> 
                                <div class="col-sm-4">
                                <form:select id="loanType" path="loanType" disabled="true">
                                    <c:forEach items="${products}" var="item">  
                                        <form:option value="${item.id}">${item.name}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>  
                        </div>         

                        <div class="form-group">   
                            <form:label path="requestDate" cssClass="col-sm-4 control-label">Request Date:</form:label> 
                                <div class="col-sm-4">
                                <fmt:formatDate pattern="dd/MM/yyyy" value="${loanRequest.requestDate}"/>
                            </div>  
                        </div> 

                        <div class="form-group">   
                            <form:label path="requestedAmount" cssClass="col-sm-4 control-label">Requested Amount:</form:label> 
                                <div class="col-sm-4">
                                <fmt:formatNumber type="number" maxFractionDigits="2" value="${loanRequest.requestedAmount}" />
                            </div>  
                        </div> 

                        <div class="form-group">   
                            <form:label path="approvedAmount" cssClass="col-sm-4 control-label">Approved Amount:</form:label> 
                                <div class="col-sm-4">
                                <fmt:formatNumber type="number" maxFractionDigits="2" value="${loanRequest.approvedAmount}" />
                            </div>  
                        </div> 

                        <div class="form-group">  
                            <form:label path="productRate" cssClass="col-sm-4 control-label">Product Rate:</form:label> 
                                <div class="col-sm-4">
                                    <div id="productInfo">
                                    <form:input id="productRate" path="productRate" value="${loanRequest.productRate}" readonly="true"/>
                                </div>
                            </div>  
                        </div>  

                        <div class="form-group">  
                            <form:label path="appliedRate" cssClass="col-sm-4 control-label">Applied Rate:</form:label> 
                                <div class="col-sm-4">
                                    <div id="productInfo">
                                    <form:input id="appliedRate" cssClass="error" path="appliedRate" value="${loanRequest.appliedRate}" readonly="true"/>
                                </div>
                            </div>  
                        </div> 

                        <div class="form-group">   
                            <form:label path="interestType" cssClass="col-sm-4 control-label">Interest Type </form:label>  
                                <div class="col-sm-4">
                                <form:select id="interestType" path="interestType" disabled="true">
                                    <c:forEach items="${interestTypes}" var="item">  
                                        <form:option value="${item.typeCode}">${item.typeName}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>  
                        </div> 

                        <div class="form-group">   
                            <form:label path="repayFrequency" cssClass="col-sm-4 control-label">Repayment Frequency</form:label>  
                                <div class="col-sm-4">
                                <form:select id="repayFrequency" path="repayFrequency" disabled="true">
                                    <c:forEach items="${loanRepayFreqs}" var="item">  
                                        <form:option value="${item.code}">${item.name}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>  
                        </div>

                        <fmt:formatDate pattern="dd/MM/yyyy" value="${loanRequest.proposedCommencementDate}" var="pcDateVar"/>			   
                        <div class="form-group">   
                            <form:label path="proposedCommencementDate" cssClass="col-sm-4 control-label">Commencement Date:</form:label>  
                                <div class="col-sm-4">
                                    <div class="input-group">
                                    <form:input path="proposedCommencementDate" value="${pcDateVar}" class="form-control period"  placeholder="dd/mm/yyyy" readonly="true"/>
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
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

                        <div class="form-group">   
                            <form:label path="noOfInstallments" cssClass="col-sm-4 control-label">No Of Installments:</form:label>  
                                <div class="col-sm-4">
                                <form:input path="noOfInstallments" value="${loanRequest.noOfInstallments}" readonly="true"/>
                            </div>  
                        </div>

                        <div class="form-group">   
                            <form:label path="requestStatus" cssClass="col-sm-4 control-label">Request Status:</form:label>  
                                <div class="col-sm-4">
                                ${loanRequest.requestStatus}
                            </div>  
                        </div>

                        <div class="form-group">   
                            <form:label path="approvalComment" cssClass="col-sm-4 control-label">Approval/Rejection Comment:</form:label>  
                                <div class="col-sm-4">
                                ${loanRequest.approvalComment}
                            </div>  
                        </div>

                        <div class="form-group">   
                            <form:label path="approvalDate" cssClass="col-sm-4 control-label">Date Approved:</form:label> 
                                <div class="col-sm-4">
                                <fmt:formatDate pattern="dd/MM/yyyy" value="${loanRequest.approvalDate}"/>
                            </div>  
                        </div> 

                        <div class="form-group">   
                            <form:label path="approvedBy" cssClass="col-sm-4 control-label">Approved By:</form:label> 
                                <div class="col-sm-4">
                                ${loanRequest.approvedBy}
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
                                        <div class="col-sm-4 control-label"><div class="greenH">Exception Detail(s)</div></div>
                                        <div class="col-sm-4"><div class="error"><c:out value="${item.exceptionMessage}"/></div></div>
                                    </div> 
                                    <c:set var="counter" value="${counter+1}" /> 

                                    <input type="hidden" name="exception_${counter}" value="${item.exceptionMessage}">
                                </c:forEach>	
                            </div>
                        </c:if>

                        <input type="hidden" name="exceptionCount" value="${counter}">                           
                        <c:if test="${not empty(guarantorsSummaryBeans)}">
                            <br/>
                            <HR size="2"/>
                            <div class="form-group">   
                                <div>&nbsp;&nbsp;<<<<<b>LOAN GUARANTORS</b> >>></div>			  
                            </div>                           
                            <HR/>
                         
                             <c:set var="counter1" value="1" />
                            <c:forEach items="${guarantorsSummaryBeans}" var="item">
		   <div class="form-group">   
                                    <div class="form-group">
                                        <div class="col-sm-4 control-label"><div class="error">Guarantor Name</div></div>
                                        <div class="col-sm-4"><div class="greenH">

                                                <select id="guarantor_${counter1}" path="memberName" name="memberName"  cssClass="width300" >
                                                    <option  value="${item.memberId}"> ${item.memberName}</option>
                                                    
                                                    <c:forEach  items="${members}" var="membersList">
                                                        
                                                        <%--<form:option name="name" value="${membersList.memberId}">${membersList.surname} ${membersList.firstname}</form:option>--%>
                                                        <option   value="${membersList.memberId}">${membersList.surname} ${membersList.firstname}</option>
                                                    </c:forEach>
                                                <select>
                                            </div></div>
                                    </div> 

                                    <div class="form-group"> 
                                        <div class="col-sm-4 control-label"><div class="error">Running Loans Count</div></div>
                                        <div class="col-sm-4"><div class="greenH"><c:out value="${item.runningLoanCount}"/></div></div>
                                    </div> 

                                    <div class="form-group"> 
                                        <div class="col-sm-4 control-label"><div class="error">Running Loans Sum</div></div>
                                        <div class="col-sm-4"><div class="greenH">
                                                <fmt:formatNumber type="number" maxFractionDigits="2" value="${item.runningLoanSum}" />
                                            </div></div>
                                    </div> 

                                    <div class="form-group">
                                        <div class="col-sm-4 control-label"><div class="error">Total Savings/Contribution</div></div>
                                        <div class="col-sm-4"><div class="greenH">
                                                <fmt:formatNumber type="number" maxFractionDigits="2" value="${item.totalMemberContribution}" />                                                 
                                            </div></div>
                                    </div>     

                                </div>
                                <HR/>
                                 
                                 <c:set var="counter1" value="${counter1+1}" />
                                 <input type="hidden" name="oldGuarantor" value="${item.memberId}"> 
                           </c:forEach>			

                            <input type="hidden" name="guarantorCount" value="${counter1-1}">
                        </c:if>	                           
                        <HR/>

                        <input type="hidden" name="ACTION_ID" value="4">
                         
                        <form:hidden path="id" value="${loanRequest.id}"/>
                         
                         <form:hidden path="lastModifiedBy" value="<%=request.getRemoteUser()%>"/>
                        <form:hidden path="approvalDate" id="approvalDate" name="approvalDate" data-format="dd/MM/yyyy"  value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>
                        <form:hidden path="lastModificationDate" id="lastModificationDate" name="lastModificationDate"   value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>
                        
                        <!-- Trigger the modal with a button -->

                        <div class="form-group">
                             <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">SUBMIT </button>

                        </div>

                        <%--
                                                   <!-- Trigger the modal with a button -->
                                                   <button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#myModal2">VIEW GUARANTORS</button>

                           <!-- Trigger the modal with a button -->
                           <button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#myModal3">VIEW EXCEPTION</button>
                        --%>
                        <hr/>
                    </form:form>  
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

          jQuery('select').select2({
                    minimumResultsForSearch: -1
                });
				
    })
</script>
