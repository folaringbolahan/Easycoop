<%@ include file="includes/header.jsp" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="media-body">
<div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Loan Guarantor For Approval</li>
    </ul>
    <h4>LOAN GUARANTOR APPROVAL</h4>
</div>
<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
 <form:form method="POST" action="approveNewLoanGuarantor" commandName="loanGuarantorChange">
<div class="contentpanel">
      <div class=" col-md-10">
        <table  id="data-list" class="table table-striped table-bordered responsive" >       
            <thead>
                <tr>
                     
		       <th>Loan Case Id</th>
                       <th>Member Name</th>
                       
                       <th>Old Guarantor</th>
                       <th>New  Guarantor</th>
		        
                 

                </tr>
            </thead>
            <tbody>
                <c:set var="counter1" value="0" />
                <c:forEach var="guarantor" items="${loanGuarantorChange}" >
                 
                    <tr>
                        
    	  		
    	  	       
                          
                        <td>${guarantor.loanCaseId}</td>
                        <td>${guarantor.memberName}</td>
                        
                        <td>${guarantor.guarantorReplaced}</td>
                        <td>${guarantor.guarantorName1} </td>
                        <td>  <c:set var="counter1" value="${counter1+1}" />
                            <input type="checkbox" name="selectedGuarantor"  value="${guarantor.guarantorNo}" >
                            <input type="hidden" name="replaced"  value="${guarantor.guarantorReplacedNo}" >
                            <input type="hidden" name="loan" value="${guarantor.loanCaseId}">
                              <input type="hidden" name="ids" value="${guarantor.id}">
                            
                        </td>
                        
                        
                        
                   </tr>
                    
                </c:forEach>
                    <input type="hidden" name="guarantorCount" value="${counter1}">
                    
            </tbody>
            
        </table>
                    <HR>
                    
                        <div class="form-group">   
				  <form:label path="" cssClass="col-sm-4 control-label">Cooperative:</form:label> 
				  <div class="col-sm-8">
				      <form:select id="" path="" disabled="true">
					<c:forEach items="${companies}" var="item">  
					     <form:option value="${item.id}">${item.name}</form:option>
					</c:forEach>
				      </form:select>
				  </div>  
			   </div>  
                        <div class="form-group">   
				  <form:label path="" cssClass="col-sm-4 control-label">Branch</form:label>
				  <div class="col-sm-8">
				      <form:select id="" path="" disabled="true">
					 <c:forEach items="${branches}" var="item">  
						<form:option value="${item.id}">${item.branchName}</form:option>
					 </c:forEach>
				      </form:select>
				  </div>  
			   </div>
                        	
			 
			   
			   			   

			   	
                    <HR>
                             
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
                        
                       <HR>
                        <div class="form-group">   
				  <form:label path="" cssClass="col-sm-4 control-label">Product</form:label> 
				  <div class="col-sm-4">
				      <form:select id="" path="" disabled="true">
					 <c:forEach items="${products}" var="item">  
					     <form:option value="${item.id}">${item.name}</form:option>
					 </c:forEach>
				      </form:select>
				  </div>  
			   </div>  
                  <div class="form-group">   
				  <form:label path="" cssClass="col-sm-4 control-label">Request Date:</form:label> 
				  <div class="col-sm-4">
				     <fmt:formatDate pattern="dd/MM/yyyy" value="${loanRequest.requestDate}"/>
				  </div>  
			   </div>
               
                                  <div class="form-group">   
				  <form:label path="" cssClass="col-sm-4 control-label">Requested Amount:</form:label> 
				  <div class="col-sm-4">
				     <fmt:formatNumber type="number" maxFractionDigits="2" value="${loanRequest.requestedAmount}" />
				  </div>  
			   </div> 

			   <div class="form-group">   
				  <form:label path="" cssClass="col-sm-4 control-label">Approved Amount:</form:label> 
				  <div class="col-sm-4">
				     <fmt:formatNumber type="number" maxFractionDigits="2" value="${loanRequest.approvedAmount}" />
				  </div>  
			   </div> 
			   
			   <div class="form-group">  
				  <form:label path="" cssClass="col-sm-4 control-label">Product Rate:</form:label> 
				  <div class="col-sm-4">
				     <div id="productInfo">
				     	  <form:input id="" path="" value="${loanRequest.productRate}" readonly="true"/>
				     </div>
				  </div>  
			   </div>  

			   <div class="form-group">  
				  <form:label path="" cssClass="col-sm-4 control-label">Applied Rate:</form:label> 
				  <div class="col-sm-4">
				     <div id="productInfo">
				     	  <form:input id="" cssClass="error" path="" value="${loanRequest.appliedRate}" readonly="true"/>
				     </div>
				  </div>  
			   </div> 
			   
			   <div class="form-group">   
				  <form:label path="" cssClass="col-sm-4 control-label">Interest Type </form:label>  
				  <div class="col-sm-4">
				      <form:select id="" path="" disabled="true">
					 <c:forEach items="${interestTypes}" var="item">  
					     <form:option value="${item.typeCode}">${item.typeName}</form:option>
					 </c:forEach>
				      </form:select>
				  </div>  
			   </div> 
			   
			   <div class="form-group">   
				  <form:label path="" cssClass="col-sm-4 control-label">Repayment Frequency</form:label>  
				  <div class="col-sm-4">
				      <form:select id="" path="" disabled="true">
					 <c:forEach items="${loanRepayFreqs}" var="item">  
					     <form:option value="${item.code}">${item.name}</form:option>
					 </c:forEach>
				      </form:select>
				  </div>  
			   </div>
			   
			   <fmt:formatDate pattern="dd/MM/yyyy" value="${loanRequest.proposedCommencementDate}" var="pcDateVar"/>			   
			   <div class="form-group">   
				  <form:label path="" cssClass="col-sm-4 control-label">Commencement Date:</form:label>  
				  <div class="col-sm-4">
				     <div class="input-group">
				     	<form:input path="" value="${pcDateVar}" class="form-control period"  placeholder="dd/mm/yyyy" readonly="true"/>
				      	<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
				      </div>
				  </div>  
			   </div>

			   <div class="form-group">   
				  <form:label path="" cssClass="col-sm-4 control-label">Actual Commencement Date:</form:label>  
				  <div class="col-sm-4">
				     <div class="input-group">
				     	<form:input path="" value="${pcDateVar}" class="form-control period"  placeholder="dd/mm/yyyy" readonly="true"/>
				      	<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
				      </div>
				  </div>  
			   </div>
		
			   <div class="form-group">   
				  <form:label path="" cssClass="col-sm-4 control-label">No Of Installments:</form:label>  
				  <div class="col-sm-4">
				     <form:input path="" value="${loanRequest.noOfInstallments}" readonly="true"/>
				  </div>  
			   </div>
			   
			   <div class="form-group">   
				  <form:label path="" cssClass="col-sm-4 control-label">Request Status:</form:label>  
				  <div class="col-sm-4">
				     ${loanRequest.requestStatus}
				  </div>  
			   </div>
			   
			   <div class="form-group">   
				  <form:label path="" cssClass="col-sm-4 control-label">Approval/Rejection Comment:</form:label>  
				  <div class="col-sm-4">
				     ${loanRequest.approvalComment}
				  </div>  
			   </div>
			   
			   <div class="form-group">   
				  <form:label path="" cssClass="col-sm-4 control-label">Date Approved:</form:label> 
				  <div class="col-sm-4">
				     <fmt:formatDate pattern="dd/MM/yyyy" value="${loanRequest.approvalDate}"/>
				  </div>  
			   </div> 

			   <div class="form-group">   
				  <form:label path="" cssClass="col-sm-4 control-label">Approved By:</form:label> 
				  <div class="col-sm-4">
				     ${loanRequest.approvedBy}
				  </div>  
			   </div> 
                       <HR/>
                        
       <!--second table for other details-->
                             <div class="form-group">   
                                <div>&nbsp;&nbsp;<<<<<b>LOAN DETAILS OF OLD GUARANTORS</b> >>></div>			  
                            </div>
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
                                 
                                                 


                                
                                 
                            </c:forEach>
        <!--the new guarantor details here-->
        
     <div class="form-group">   
                                <div>&nbsp;&nbsp;<<<<<b>LOAN DETAILS OF NEW GUARANTORS</b> >>></div>			  
                            </div>
            <c:forEach items="${newGuarantorsSummaryBeans}" var="items">
                                <div class="form-group">  
                                    <div class="form-group">
	                             <div class="col-sm-4 control-label"><div class="error">Guarantor Name</div></div>
			             <div class="col-sm-4"><div class="greenH"><c:out value="${items.memberName}"/></div></div>
				</div> 
                                  
                                    <div class="form-group"> 
                                        <div class="col-sm-4 control-label"><div class="error">Running Loans Count</div></div>
                                        <div class="col-sm-4"><div class="greenH"><c:out value="${items.runningLoanCount}"/></div></div>
                                    </div> 

                                    <div class="form-group"> 
                                        <div class="col-sm-4 control-label"><div class="error">Running Loans Sum</div></div>
                                        <div class="col-sm-4"><div class="greenH">
                                                <fmt:formatNumber type="number" maxFractionDigits="2" value="${items.runningLoanSum}" />
                                            </div></div>
                                    </div> 

                                    <div class="form-group">
                                        <div class="col-sm-4 control-label"><div class="error">Total Savings/Contribution</div></div>
                                        <div class="col-sm-4"><div class="greenH">
                                                <fmt:formatNumber type="number" maxFractionDigits="2" value="${items.totalMemberContribution}" />                                                 
                                            </div></div>
                                    </div>     

                                </div>
                                <HR/>
                               
                                 
                                 <c:set var="counter1" value="${counter1+1}" />
                                 
                                                 


                                
                                 
                            </c:forEach>
   
       <!--end of second table-->
   
           <div class="form-group">
                            <button class="btn btn-danger mr5" type="submit" >APPROVE </button>

                        </div>
    </div>
    
   
</div>
</form:form>

<%@ include file="includes/footer.jsp" %>  

   