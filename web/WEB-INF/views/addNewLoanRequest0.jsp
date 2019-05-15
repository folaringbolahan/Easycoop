<%@ include file="includes/header.jsp" %> 
<div class="media-body">
    <div style="float:left">

        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="#">Home</a></li>
            <li>Loan Request Management</li>
        </ul>
        <h4>Edit Loan Request</h4>
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
                    <td align="center"><a href="viewNewLoanRequestDetails.htm?id=${loan.id}">EDIT</a><c:if test="${loan.requestStatus=='E'}"> &nbsp;|| &nbsp;<a href="viewNewLoanRequestDetails.htm?id=${loan.id}">EDIT</a> </c:if></td>  
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
