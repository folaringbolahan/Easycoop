<%-- 
    Document   : listAcctForApproval
    Created on : Dec 12, 2015, 10:40:55 AM
    Author     : OLakunle Awotunbo
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
        <h4>Batch Authorization for ${dBatchId}</h4>
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
               
              
               <c:if test="${uploadedBy == logonUser}">
                                            <font color="red"><div data-toggle="tooltip" title="A different user is required to approve this Upload" cssClass="tooltips">NOT ALLOWED TO AUTHORIZE</div></font>
               </c:if>
               <c:if test="${uploadedBy ne logonUser}">
                       <p>  <button class="btn btn-success mr5" id="approveSavings" name="action" onClick="javascript:form.action='<%=request.getContextPath()%>/gl/gl_approveByBatch/${dBatchId}.htm'" value="APPROVE">APPROVE</button>
                             <button class="btn btn-danger mr5" id="approveSavings2" name="action" onClick="javascript:form.action='<%=request.getContextPath()%>/gl/gl_rejectByBatch/${dBatchId}.htm'" value="REJECT">REJECT</button>
                         </p>
             
               </c:if>
            
           
            <table id="account-list" class="table table-striped table-bordered responsive">
                <thead class="">
                    <tr>                       
                        <th>ID</th>
                        <th>MEMBER NAME</th>
                        <th>MEMBER NO</th>
                        <th>BATCH ID</th>                        
                        
                    </tr>
                </thead>
                <tbody>
                    <c:forEach  var="item" items="${singapprv}" varStatus="status" >
                        <tr >
                       
                            <td>${status.index + 1}</td>
                            <td>${item.memberName}</td>
                            <td>${item.memberNo}</td>
                            <td>${item.batchId}</td>
                                             
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

    });
</script>