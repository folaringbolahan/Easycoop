<%@ include file="includes/header.jsp" %>  
<script src="<%=request.getContextPath()%>/js/utilityfc.js"></script>
<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="#">Home</a></li>
            <li>Savings Management</li>
        </ul>
        <h4>Pending Transactions</h4>
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
            <p>  <button class="btn btn-danger mr5" id="approveSavings">Approve Selected</button></p>
            <div id="submitResponse" class="alert alert-success" style="display:none">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <span id="successMessageStatus">Operation successful! A detailed summary of the operation performed will be sent to your email address.</span>
            </div>
            <div class="alert alert-danger"  id="errorMessage" style="display:none;">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true" >&times;</button>
                <span id="errorMessageStatus">Operation failed! Please try again</span>
            </div>
            <table id="savings-list" class="table table-striped table-bordered responsive">
                <thead class="">
                    <tr>
                        <th style="width:8px;"><input type="checkbox" class="group-checkable" data-set="#savings-list .checkboxes" /></th>
                        <th> Id</th>
                        <th>Account Number</th>
                        <th>Title</th>
                        <th>Amount</th>
                        <th>Reference Number</th>
                        <th>Description</th>
                        <th>Date</th>
                     <!--   <th></th> -->
                    </tr>
                </thead>
                <tbody>
                    <c:forEach  var="saving" items="${savings}" varStatus="status" >
                        <tr data-id="${saving.id}" id="sav-${saving.id}">
                         <c:if test="${saving.userId==currrentuserServicex.curruser.userId || (!empty saving.verifierId && saving.verifierId==currrentuserServicex.curruser.userId)}">
                      <td data-id="${saving.id}" >NA</td>

                            </c:if> 
                     <c:if test="${saving.userId!=currrentuserServicex.curruser.userId && (empty saving.verifierId || saving.verifierId!=currrentuserServicex.curruser.userId)}">
                      <td data-id="${saving.id}" ><input type="checkbox"  class="checkboxes" value="${saving.id}" /></td>

                            </c:if>
                            <td>${status.index + 1}</td>
                            <td >${saving.accountNumber}</td>
                            <td >${saving.accountNameobj.accountName}</td> 
                            <td ><fmt:formatNumber type="number" maxFractionDigits="2" value="${saving.amount}" /></td>
                            <td >${saving.referenceNumber}</td>
                            <td >${saving.description}</td>
                            <td >${saving.trxDate}</td>
                        <!--      <td class="table-action">

                            </td> 
                         -->
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
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

    $(document).ready(function() {

        jQuery('#savings-list').DataTable({
            responsive: true
        });

        jQuery('#savings-list .group-checkable').change(function() {
            var set = jQuery(this).attr("data-set");
            var checked = jQuery(this).is(":checked");
            jQuery(set).each(function() {
                if (checked) {
                    $(this).attr("checked", true);
                } else {
                    $(this).attr("checked", false);
                }
            });
            //jQuery.uniform.update(set);
        });

        jQuery('#approveSavings').click(function() {
            var id = "";
           
            $('#savings-list .checkboxes').each(function() {
                var checked = jQuery(this).is(":checked");

                if (checked) {
                    if (id === "") {
                        id += $(this).val();
                    } else {
                        id += "," + $(this).val();
                    }

                }

            });
            if(id===""){
                alert("Please make selection(s) before proceeding!")
            }else{
         $.blockUI({message: '<h1>Processing...</h1>'});   
        $.get("<%=request.getContextPath()%>/views/savings/approve", {"id": id}, function(result) {
        jQuery("#submitResponse").css("display", "block");
          location.href = "<%=request.getContextPath()%>/views/savings/pendingsavings.htm";
      
         
            setTimeout(function() {

                jQuery("#submitResponse").css("display", "none");
                location.href = "<%=request.getContextPath()%>/views/savings/pendingsavings.htm";
            }, 0);
          
        });
}         $.unblockUI();
            });

         

    });
</script>