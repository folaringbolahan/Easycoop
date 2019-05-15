<%@ include file="includes/header.jsp" %> 
<div class="media-body">
    <div style="float:left">

        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="#">Home</a></li>
            <li>Loan Request Management</li>
        </ul>
        <h4>Manage Loan Request - Step 1</h4>
    </div>
    <%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->

<script lanuage="Javascript">
    function doSubmit(frm) {
        if (frm.companyId.value == "") {
            alert("please select cooperative");
            frm.companyId.focus();
            return;
        }
        if (frm.companyId.value == "") {
            alert("please select branch");
            frm.branchId.focus();
            return;
        }
        if (frm.memberNo.value == "") {
            alert("please select customer");
            frm.memberNo.focus();
            return;
        }
        if (frm.loanType.value == "") {
            alert("product is required");
            frm.loanType.focus();
            return;
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

                                            alert(html);
                                            $('select#branchId').html(html);
                                        });
                                    });
                        });
                    </script>
                    <script type="text/javascript">
                        $(document).ready(function() {
                            $('select#loanType').change(
                                    function() {
                                        //alert($(this).val());
                                        $.getJSON('getProductRate.do', {
                                            loanType: $(this).val(),
                                            ajax: 'true'
                                        }, function(data) {
                                            var len = 0;
                                            var html = '';

                                            if (data != null) {
                                                len = data.length;
                                            }

                                            //alert("len:="+len);
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

                    <form:form method="post" action="newLoanRequest.htm" commandName="loanRequest">
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
                            <form:label path="branchId" cssClass="col-sm-3 control-label">Branch *</form:label>
                                <div class="col-sm-8">
                                <form:select id="branchId" path="branchId" cssClass="width300" >
                                    <c:forEach items="${branches}" var="item">  
                                        <form:option value="${item.id}">${item.branchName}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>  
                        </div>			   

                        <div class="form-group">   
                            <form:label path="memberNo" cssClass="col-sm-3 control-label">Select Member *</form:label>
                                <div class="col-sm-8">
                                <form:select id="memberNo" path="memberNo" cssClass="width300" >
                                    <form:option value="">--select--</form:option>
                                    <c:forEach items="${members}" var="item">  
                                        <form:option value="${item.memberId}">${item.memberNo} -> ${item.surname},${item.firstname}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>  
                        </div> 

                        <div class="form-group">   
                            <form:label path="loanType" cssClass="col-sm-3 control-label">Select Product *</form:label> 
                                <div class="col-sm-8">
                                <form:select id="loanType" path="loanType"  cssClass="width300" >
                                    <form:option value="">--select--</form:option>
                                    <c:forEach items="${products}" var="item">  
                                        <form:option value="${item.code}">${item.name}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>  
                        </div> 

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
                <th>Int.Rate (%)</th>  
                <th>Loan Case Id</th>  
                <th>Status  </th> 
                <th>Action </th> 
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
                    <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${loan.approvedAmount}" /></td>  
                    <td><c:out value="${loan.appliedRate}"/></td> 
                    <td><c:out value="${loan.loanCaseId}"/></td>  
                    <td><c:out value="${loan.loanStatusDesc}"/></td>  
                    <td align="center"><a href="viewLoanRequestDetails.htm?id=${loan.id}">VIEW</a><c:if test="${loan.requestStatus=='E'}"> &nbsp;|| &nbsp;<a href="updateLoanRequest0.htm?id=${loan.id}">EDIT</a> </c:if></td>  
                    </tr>  
            </c:forEach>  
        </tbody>
    </table>  
</c:if>  

<%@ include file="includes/footer.jsp" %>  
<script>
                             $(document).ready(function() {
                                 jQuery('select').select2({
                                     minimumResultsForSearch: -1
                                 });
                                 jQuery('#loans-list').DataTable({
                                     responsive: true
                                 });
                             })
</script>
