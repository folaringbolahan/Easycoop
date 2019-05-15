<%@ include file="includes/header.jsp" %>  
<script src="<%=request.getContextPath()%>/js/utilityfc.js"></script>
<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="#">Home</a></li>
            <li>Savings Management</li>
        </ul>
        <h4>Verify Transactions</h4>
    </div>
    <%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->


<div class="contentpanel">
    <div class="row">
        <!--  <div class="col-md-8">-->  
        <!-- CONTENT GOES HERE -->  
        <!--   <div class="col-md-10">-->
        <div class="panel panel-primary-head">
            <div id="submitResponse" class="alert alert-success" style="display:none">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <span id="successMessageStatus">Operation successful! </span>
            </div>
            <div class="alert alert-danger"  id="errorMessage" style="display:none;">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true" >&times;</button>
                <span id="errorMessageStatus">Operation failed! Please try again</span>
            </div> 
            
             <form:form method="post" action="" modelAttribute="approveAcct"> 
                 
            <table id="upload-list" class="table table-striped table-bordered responsive">
                <thead class="">
                    <tr>
                        <!--  <th style="width:8px;"><input type="checkbox" class="group-checkable" data-set="#upload-list .checkboxes" /></th>-->
                        <th>S/N</th>
                        <th>Reference #</th>
                    <th>File Name</th>
                        <th>Date</th>
                        <th>Total Records</th>
                        <th>Success Count</th>
                        <!-- <th>Failed Count</th> -->
                        <th>Sum</th>
                        <th>Processed Sum</th>
                        <th>Passed</th>
                        <th>Failed</th>
                        <th>Verify</th>
                        <th>Reject</th>
                     <!--   <th></th>  -->
                    </tr>
                </thead>
                <tbody>
                    <c:forEach  var="upload" items="${uploads}" varStatus="status" >
                        <tr>

                            <td>${status.index + 1}</td>
                            <td>${upload.referenceNumber}</td>  
                         <td>${upload.fileName}</td>
                            <td>${upload.processedDate}</td>
                            <td>${upload.totalRecords}</td>
                            <td>${upload.successCount}</td>
                           <!-- <td>${upload.failedCount}</td> -->
                            <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${upload.fileSum}" /></td>
                            <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${upload.processedSum}" /></td> 
                            <td>
                                <a href="<%=request.getContextPath()%>/savings/successfulupload/${upload.referenceNumber}">View </a>
                            </td>
                            <td>
                                <a href="<%=request.getContextPath()%>/savings/savingsuploaderror/${upload.referenceNumber}"> View </a>
                            </td>
                            <td>
                                
                                            <c:if test="${upload.userId == currrentuserServicex.curruser.userId}">
                                                <font color="red"><div data-toggle="tooltip" title="A different user is required to reject this Upload" cssClass="tooltips">NOT ALLOWED</div></font>
                                            </c:if>
                                            <c:if test="${upload.userId ne currrentuserServicex.curruser.userId}">
                                      <!--          <a href="#"  onclick="verify(${upload.referenceNumber})">Verify</a>  -->
                                          <button class="btn btn-success mr5" id="approveSavings" name="action" onClick="javascript:form.action='<%=request.getContextPath()%>/views/savings/verifybatch/${upload.referenceNumber}.htm'" value="VERIFY">VERIFY</button> 

                                            </c:if>
                                
                            </td>
                            <td>
                                            <c:if test="${upload.userId == currrentuserServicex.curruser.userId}">
                                                <font color="red"><div data-toggle="tooltip" title="A different user is required to reject this Upload" cssClass="tooltips">NOT ALLOWED</div></font>
                                            </c:if>
                                            <c:if test="${upload.userId ne currrentuserServicex.curruser.userId}">
                                        <!--      <a href="#"  onclick="reject(${upload.referenceNumber})">Reject</a>  -->
                                         <button class="btn btn-danger mr5" id="rejectupload" name="action" onClick="javascript:form.action='<%=request.getContextPath()%>/views/savings/rejectVerifybatch2/${upload.referenceNumber}.htm'" value="REJECT">REJECT</button> 

                                            </c:if>
                            </td> 
                     
                        </tr>
                    </c:forEach>
                </tbody>
            </table> 
                   </form:form>  
        </div>
    </div><!-- col-md-6 -->
</div>
</div>

<!-- </div>
</div>-->

<!-- modal ends -->
</div><!-- contentpanel -->

<%@ include file="includes/footer.jsp" %>  
<script src="<%=request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>
<script>                                    
    function reject(reference){
        $.blockUI({message: '<h1>Processing. Please wait...</h1>'});
                                               // $.get("<%=request.getContextPath()%>/views/savings/rejecttransaction", {"ReferenceNumber": reference}, function (result) {
                                                
     $.get("<%=request.getContextPath()%>/views/savings/rejectbatchappr", {"ReferenceNumber": reference}, function (result) {                                               
    
                                                    if (result === 'ok') {
                                                        jQuery("#submitResponse").css("display", "block");
                                                        setTimeout(function () {

                                                            jQuery("#submitResponse").css("display", "none");
                                                            location.href = "<%=request.getContextPath()%>/views/savings/verifyuploads.htm";
                                                        }, 500);

                                                    } else if (result === 'notok') {
                                                        jQuery("#submitResponse").css("display", "block");
                                                    } else {
                                                        jQuery("#errorMessageStatus").html(result);
                                                        jQuery("#errorMessage").css("display", "block");

                                                    }

                                                });
                                                $.unblockUI(); 
    }
                                            function verify(ref) {

                                                $.blockUI({message: '<h1>Processing. Please wait...</h1>'});
                                                $.get("<%=request.getContextPath()%>/views/savings/verifytransaction", {"ReferenceNumber": ref}, function (result) {

                                                    if (result === 'ok') {
                                                        jQuery("#submitResponse").css("display", "block");
                                                        setTimeout(function () {

                                                            jQuery("#submitResponse").css("display", "none");
                                                            location.href = "<%=request.getContextPath()%>/views/savings/verifyuploads.htm";
                                                        }, 500);

                                                    } else if (result === 'notok') {
                                                        jQuery("#submitResponse").css("display", "block");
                                                    } else {
                                                        jQuery("#errorMessageStatus").html(result);
                                                        jQuery("#errorMessage").css("display", "block");

                                                    }

                                                });
                                                $.unblockUI();
                                            }
                                            $(document).ready(function () {

                                                jQuery('#upload-list').DataTable({
                                                    responsive: true
                                                });

                                                jQuery('#upload-list .group-checkable').change(function () {
                                                    var set = jQuery(this).attr("data-set");
                                                    var checked = jQuery(this).is(":checked");
                                                    jQuery(set).each(function () {
                                                        if (checked) {
                                                            $(this).attr("checked", true);
                                                        } else {
                                                            $(this).attr("checked", false);
                                                        }
                                                    });
                                                    jQuery.uniform.update(set);
                                                });

                                            });
</script>