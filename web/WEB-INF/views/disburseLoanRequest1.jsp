<%@ include file="includes/header.jsp" %> 

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="#">Home</a></li>
            <li>Loan Request Management</li>
        </ul>
        <h4>Disburse Loan Request</h4>
    </div>
    <%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<div class="contentpanel">
    <div class="row">
        <div class="col-md-6">
            <!-- CONTENT GOES HERE -->  
            <div class="panel panel-default">   
                <div class="panel-heading">
                    <div class="panel-btns">
                    </div><!-- panel-btns -->
                    <h5 class="panel-title">LOAN DISBURSEMENT </h5>
                    <p>Loan disbursement to member. </p>
                </div><!-- panel-heading -->
                <div class="panel-body">
                    <c:set var="now" value="<%=new java.util.Date()%>" />

                    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
                    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui.js"></script>
                    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-i18n.min.js"></script> 

                    <script type="text/javascript">
                        $(document).ready(function() {
                            $('select#companyId').change(
                                    function() {
                                        alert($(this).val());
                                        $.getJSON('branchesAjaxList.do', {
                                            companyId: $(this).val(),
                                            ajax: 'true'
                                        }, function(data) {
                                            var len = 0;
                                            var html = '';

                                            if (data != null) {
                                                len = data.length;
                                            }

                                            alert("len:=" + len);
                                            if (len > 0) {
                                                for (var i = 0; i < len; i++) {
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
                        $(document).ready(function() {
                            $('select#loanType').change(
                                    function() {
                                        alert($(this).val());
                                        $.getJSON('getProductRate.do', {
                                            loanType: $(this).val(),
                                            ajax: 'true'
                                        }, function(data) {
                                            var len = 0;
                                            var html = '';

                                            if (data != null) {
                                                len = data.length;
                                            }

                                            alert("len:=" + len);
                                            if (len > 0) {
                                                for (var i = 0; i < len; i++) {
                                                    html += '<input type="text" value="' + data[i].interestRate + '" id="productRate" />';
                                                }
                                            }

                                            //alert(html);					
                                            $('#productInfo').html(html);
                                        });
                                    });
                        });
                    </script>	 

                    <script lanuage="Javascript">
                        function ConfirmSave(frm) {
                            var r = confirm("Do you want to DISBURSE this Loan?");
                            if (r == true) {
                                //frm.actionId.value="A";
                                frm.action = "doDisburseLoan.htm";
                                frm.submit();
                            } else {
                                return;
                            }
                        }

                        function ConfirmCancel(frm) {
                            var r = confirm("Do you want to CANCEL this Loan?");
                            if (r == true) {
                                //frm.actionId.value="R";
                                frm.action = "cancelDisburseLoan.htm";
                                frm.submit();
                            } else {
                                // alert('ERROR');
                                return;
                            }
                        }
                        
                       
                        
                    </script>

                    <form:form method="post" action="" commandName="loanRequest">

                        <div class="form-group">   
                            <form:label path="loanCaseId" cssClass="col-sm-4 control-label">Loan Case Id:</form:label>
                                <div class="col-sm-8">
                                <form:input path="loanCaseId" value="${loanRequest.loanCaseId}" readonly="true"/>
                            </div>  
                        </div> 

                        <div class="form-group">   
                            <form:label path="companyId" cssClass="col-sm-4 control-label">Cooperative:</form:label> 
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
                            <div class="form-group">
                                <div class="col-sm-4 control-label"><div class="error">Total Savings/Contribution</div></div>
                                <div class="col-sm-4"><div class="error">
                                        <fmt:formatNumber type="number" maxFractionDigits="2" value="${memberSummaryBean.totalMemberContribution}" />                                                 
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
                            <form:label path="requestedAmount" cssClass="col-sm-4 control-label">Requested Amount:</form:label> 
                                <div class="col-sm-8">
                                <form:input path="requestedAmount" value="${loanRequest.requestedAmount}"/>
                            </div>  
                        </div> 


                        <div class="form-group">   
                            <form:label path="approvedAmount" cssClass="col-sm-4 control-label">Approved Amount:</form:label> 
                                <div class="col-sm-3">
                                <form:input path="approvedAmount" cssClass="error" value="${loanRequest.approvedAmount}"/>
                            </div>  
                        </div> 

                        <div class="form-group">  
                            <form:label path="productRate" cssClass="col-sm-4 control-label">Product Rate:</form:label> 
                                <div class="col-sm-8">
                                    <div id="productInfo">
                                    <form:input id="productRate" path="productRate" value="${loanRequest.productRate}"/>
                                </div>
                            </div>  
                        </div>  

                        <div class="form-group">  
                            <form:label path="appliedRate" cssClass="col-sm-4 control-label">Applied Rate:</form:label> 
                                <div class="col-sm-3">
                                    <div id="productInfo">
                                    <form:input id="appliedRate"  cssClass="error" path="appliedRate" value="${loanRequest.appliedRate}"/>
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
                         <form:label path="changeDisbursement" cssClass="col-sm-4 control-label">Change Disbursement A/c:</form:label>  
                                <div class="col-sm-4">
                                <input type="checkbox" id="changeDisbursement"  value="checked" >
                                 
                            </div>  
                        </div>
                         <div class="form-group">
                            <label class="col-sm-4 control-label tooltips" data-toggle="tooltip" title="Select Account">Disbursement A/C </span></label>
                            <div class="col-sm-8">
                                <select  cssClass="width300"  id="disbursementAcct" path="disbursementAcct" name="disbursementAcct"  >
                                    <option value="">------------Choose Account------------</option>
                                    <c:forEach  var="items" items="${disurseAccts}" varStatus="status" >

                                        <option value="${items.accountNo}">${items.accountNo}--${items.name}</option>
                                    </c:forEach>
                                </select>

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
                                <div class="col-sm-8">
                                <form:input path="noOfInstallments" value="${loanRequest.noOfInstallments}" readonly="true"/>
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

                        <%--         
                      <input type="hidden" name="exceptionCount" value="${counter}">
                      
                      <c:if test="${not empty(guarantorsSummaryBeans)}">
                              <br/>
                              <HR size="2"/>
                              <div class="form-group">   
                                   <div>&nbsp;&nbsp;<<<<<b>LOAN GUARANTORS</b> >>></div>			  
                              </div>                           
                              <HR/>
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
                                           <input type="hidden" name="guarantor_${counter1}" value="${item.memberId}">
                                   </c:forEach>	

                                   <input type="hidden" name="guarantorCount" value="${counter1}">
                           </c:if>	                           
                           <HR/>
                        --%> 


                        <form:hidden path="loanStatus" value="D"/>
                        <input type="hidden" name="ACTION_ID" value="4">
                        <form:hidden path="id" value="${loanRequest.id}"/>

                        <form:hidden path="approvalDate" id="approvalDate" name="approvalDate" data-format="dd/MM/yyyy"  value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>
                        <form:hidden path="lastModificationDate" id="lastModificationDate" name="lastModificationDate"   value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>
                        <%--
                                                   <div class="form-group">   
                                                          <form:label path="duration" cssClass="col-sm-4 control-label">Action:</form:label>  
                                                          <div class="col-sm-8">
                                                              <input type="radio" name="requestStatus" value="R"> Reject
                                                              <input type="radio" name="requestStatus" value="A"> Accept
                                                          </div>  
                                                   </div>
                                                   
                                                   <div class="form-group">   
                                                          <form:label path="duration" cssClass="col-sm-4 control-label">Comment:</form:label>  
                                                          <div class="col-sm-8">
                                                             <form:textarea path="approvalComment" value="${loanRequest.approvalComment}"/>
                                                          </div>  
                                                   </div>
                                                   <hr/>
                        --%>

                        <!-- Trigger the modal with a button -->

                        <div class="form-group">
                            <button type="button" class="btn btn-success mr5" onclick="Javascript:ConfirmSave(this.form);">DISBURSE</button>
                            &nbsp;
                            <button type="button" class="btn btn-danger mr5" onclick="Javascript:ConfirmCancel(this.form);">CANCEL</button>
                            &nbsp;
                            <button type="button" class="btn btn-default" onclick="Javascript:history.back(1);">BACK</button>
                            
                        </div>


                        <!-- panel-footer --> 
                    </form:form>  
                    <!--  --> 

                    <%--
                          <!-- Loan Schedule Modal -->
                          <div id="myModal" class="modal fade" role="dialog">
                             <div class="modal-dialog">

                             <!-- Modal content-->
                             <div class="modal-content">
                               <div class="modal-header">
                                 <button type="button" class="close" data-dismiss="modal">&times;</button>
                                 <h5 class="modal-title">LOAN REPAYMENT SCHEDULE</h5>
                               </div>
                               <div class="modal-body">
                                 <p>
                                    <c:if test="${!empty schedules}">  
                                             <c:set var="count" scope="page" value="0" />
                                             <table id="schedule-list" class="table table-striped table-bordered responsive">  
                                                 <thead>
                                                      <tr>  
                                                       <th>ID</th>  
                                                       <th>Interest</th>  
                                                       <th>Principal</th>  
                                                       <th>Total</th>  
                                                       <th>Date Due</th>
                                                       <th>Cumm. Principal</th>
                                                      </tr>  
                                                </thead>
                                                <tbody>
                                                      <c:forEach items="${schedules}" var="item">
                                                              <c:set var="count" scope="page" value="${count + 1}" />
                                                               <tr>  
                                                                <td><c:out value="${count}"/></td>  
                                                                <td><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.amountInterest}" /></td>  
                                                                <td><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.amountPrincipal}"/></td>  
                                                                <td><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.expectedRepaymentAmount}"/></td>  
                                                                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${item.expectedRepaymentDate}"/></td>  
                                                                <td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${item.cummPrincipal}"/></td>
                                                               </tr>  
                                                      </c:forEach>  

                                                 </tbody>
                                            </table> 
                                     </c:if>    
                                 </p>
                               </div>
                               <div class="modal-footer">
                                 <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                               </div>
                             </div>

                           </div>
                        </div>
                        <!-- End of Loan Schedule Modal  -->  
                    --%>
                </div>
            </div><!-- col-md-6 -->
        </div>  




        <!-- new panel for guarantor start -->  
        <div class="col-md-6">
            <div class="panel panel-default"> 
                <div class="panel-heading">
                    <div class="panel-btns">
                    </div><!-- panel-btns -->
                    <h5 class="panel-title">LOAN GUARANTORS</h5>
                    <p>The loan guarantors for  this loan. </p>
                </div><!-- panel-heading -->
                <div class="panel-body ">  




                    <input type="hidden" name="exceptionCount" value="${counter}">

                    <c:if test="${not empty(guarantorsSummaryBeans)}">
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


                                <div class="form-group">
                                    <div class="col-sm-4 control-label"><div class="error">Total Savings/Contribution</div></div>
                                    <div class="col-sm-4"><div class="greenH">
                                            <fmt:formatNumber type="number" maxFractionDigits="2" value="${item.totalMemberContribution}" />                                                 
                                        </div></div>
                                </div>     

                            </div>
                            <HR/>
                            <c:set var="counter1" value="${counter1+1}" /> 
                            <input type="hidden" name="guarantor_${counter1}" value="${item.memberId}">
                        </c:forEach>	

                        <input type="hidden" name="guarantorCount" value="${counter1}">
                    </c:if>



                </div>      
            </div>
        </div>   



        <!-- new for guarantor panel end -->






        <!-- new panel start -->  
        <div class="col-md-6">
            <div class="panel panel-default"> 
                <div class="panel-heading">
                    <div class="panel-btns">
                    </div><!-- panel-btns -->
                    <h5 class="panel-title">PROPOSED LOAN SCHEDULE </h5>
                    <p>The proposed loan schedule for the current loan is subject to change. </p>
                </div><!-- panel-heading -->
                <div class="panel-body nopadding">  

                    <c:if test="${!empty schedules}">  
                        <c:set var="count" scope="page" value="0" />
                        <table id="schedule-list" class="table table-striped table-bordered responsive">  
                            <thead>
                                <tr>  
                                    <th>ID</th>  
                                    <th>Interest</th>  
                                    <th>Principal</th>  
                                    <th>Total</th>  
                                    <th>Date Due</th>
                                    <th>Cumm. Principal</th>
                                </tr>  
                            </thead>
                            <tbody>
                                <c:forEach items="${schedules}" var="item">
                                    <c:set var="count" scope="page" value="${count + 1}" />
                                    <tr>  
                                        <td><c:out value="${count}"/></td>  
                                        <td><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.amountInterest}" /></td>  
                                        <td><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.amountPrincipal}"/></td>  
                                        <td><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.expectedRepaymentAmount}"/></td>  
                                        <td><fmt:formatDate pattern="dd/MM/yyyy" value="${item.expectedRepaymentDate}"/></td>  
                                        <td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${item.cummPrincipal}"/></td>
                                    </tr>  
                                </c:forEach>  

                            </tbody>
                        </table> 
                    </c:if>    

                </div>      
            </div>
        </div>   
        <!-- new panel end -->  

    </div>  

</div><!-- contentpanel -->
<%@ include file="includes/footer.jsp" %> 


<script>
    var filesList = new Array();
    function format(item) {
        return '<i class="fa ' + ((item.element[0].getAttribute('rel') === undefined) ? "" : item.element[0].getAttribute('rel')) + ' mr10"></i>' + item.text;
    }
    $(document).ready(function() {
    $("#disbursementAcct").prop("disabled", "true");
        jQuery.validator.addMethod(
                "select",
                function(value, element) {
                    if (element.value === "0")
                    {
                        return false;
                    }
                    else
                        return true;
                },
                "Please select an account"
                );
       
         
        jQuery('select').select2({
                    minimumResultsForSearch: -1
                });
     
       
       jQuery("#disbursementAcct").select2();  
       
  $("#changeDisbursement").change(function() {
         var chkd=$(this).val();
        if ( $(this).attr('checked')) {
          
            $("#disbursementAcct").removeAttr("disabled"); 
            
        } 
        else {
            $("#disbursementAcct").prop("disabled", "true");           
        }
    /**
        if (chkd==='checked') {
          
            $("#disbursementAcct").removeAttr("disabled"); 
            
        } 
        else {
            $("#disbursementAcct").prop("disabled", "true");           
        }**/
    });

 

    });
</script>



