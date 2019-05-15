<%-- 
    Document   : gl_viewfailedrecords
    Created on : Dec 17, 2015, 10:38:28 AM
    Author     : Olakunle Awotunbo
--%>

<%@ include file="../includes/header.jsp" %>  
<script src="<%=request.getContextPath()%>/js/utilityfc.js"></script>
<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="#">Home</a></li>
            <li>Account Creation Management</li>
        </ul>
        <h4>Failed Records for ${dBatchId}</h4>
    </div>
    <%@include file="../includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->


<div class="contentpanel">
    <div class="row">
        <!--  <div class="col-md-8">-->  
        <!-- CONTENT GOES HERE -->  
        <!--   <div class="col-md-10">-->
        <div class="panel panel-primary-head">

         
            <form:form method="post" action="" modelAttribute="approveAcct">
             <p>  
                    <button class="btn btn-success mr5" id="approveSavings" name="action" onClick="javascript:form.action = '<%=request.getContextPath()%>/gl/gl_downloadFailedRecords/${dBatchId}.htm'" value="APPROVE">DOWNLOAD ERROR(S)</button>
            </p>

                <table id="account-list" class="table table-striped table-bordered responsive">
                    <thead class="">
                        <tr>                       
                            <th>ID</th>
                            <th>MEMBER NAME</th>
                            <th>MEMBER NO</th>                       
                            <th>ERROR(S)</th> 
                           
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach  var="item" items="${errorList}" varStatus="status" >
                            <tr >

                                <td>${status.index + 1}</td>
                                <td>${item.memberName}</td>
                                <td>${item.memberNo}</td>                            
                                <td>${item.errorMessage}</td>
                               

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

<%@ include file="../includes/footer.jsp" %>  
<script src="<%=request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>
<script>

                           $(document).ready(function() {

                               jQuery('#account-list').DataTable({
                                   responsive: true
                               });

                               jQuery('#approveSavings').click(function() {
                                   var batchId = "";
                                   var memberNo = "";

                                   batchId += $(this).val();

                                   $.get("<%=request.getContextPath()%>/gl/gl_downloadFailedRecords");
                                   $.get("<%=request.getContextPath()%>/gl/gl_downloadFailedRecords2", {"batchId": batchId}, function(result) {
                                       //jQuery("#submitResponse").css("display", "block");          
                                   });
                                   //$.get("<%=request.getContextPath()%>/gl/gl_singleapprove3", {"id": id}, function(result) 
                                   // $.get("<%=request.getContextPath()%>/gl/gl_downloadFailedRecords2", {"batchId": batchId}, function(result){
                                   //jQuery("#submitResponse").css("display", "block");          
                                   // });



                               });
                           });

</script>