<%@ include file="includes/header.jsp" %>  
<script src="<%=request.getContextPath()%>/js/utilityfc.js"></script>
<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="#">Home</a></li>
            <li>Savings Management</li>
        </ul>
        <h4>Failed Upload for Transaction ${referenceNumber}</h4>
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
         
            <table id="savings-list" class="table table-striped table-bordered responsive">
                <thead class="">
                    <tr>
                        <th style="width:8px;"><input type="checkbox" class="group-checkable" data-set="#savings-list .checkboxes" /></th>
                        <th> Id</th>
                        <th>Account Number</th>
                        <th>Amount</th>
                        <th>Reference Number</th>
                        <th>Description</th>
                        <th>Error Message</th>
                        <th>Date</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach  var="saving" items="${savings}" varStatus="status" >
                        <tr>
                            <td data-id="${saving.id}" ><input type="checkbox" class="checkboxes" value="1" /></td>
                            <td>${status.index + 1}</td>
                            <td >${saving.accountNumber}</td>
                            <td ><fmt:formatNumber type="number" maxFractionDigits="2" value="${saving.amount}" /></td>
                            <td >${saving.referenceNumber}</td>
                            <td >${saving.description}</td>
                             <td >${saving.errorMessage}</td>
                            <td >${saving.trxDate}</td>
                            <td class="table-action">
                                
                            </td>
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
                                            jQuery.uniform.update(set);
                                        });
                                      
                                    });
    </script>