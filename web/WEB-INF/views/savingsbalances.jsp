<%@ include file="includes/header.jsp" %>  
<script src="<%=request.getContextPath()%>/js/utilityfc.js"></script>
<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="#">Home</a></li>
            <li>Savings Management</li>
        </ul>
        <h4>Accounts and Balances</h4>
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
             
                        <th>Id</th>
                        <th>Account Number</th>
                        <th>Title</th>
                        <th>Balance</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach  var="account" items="${accounts}" varStatus="status" >
                        <tr>
                           
                            <td>${status.index + 1}</td>
                            <td >${account.accountNumber}</td>
                            <td >${account.title}</td>
                            <td ><fmt:formatNumber type="number" maxFractionDigits="2" value="${account.balance}" /></td>
                            
                            <td class="table-action">
                                <button  data-accountno="${account.accountNumber}" class="btn btn-danger btn-metro viewSavings" onclick='return viewSavings("viewSavings-${status.index + 1}");' id="viewSavings-${status.index + 1}">View Transactions</button>
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
                   function viewSavings(objId){
                       var accountNumber = jQuery('#'+objId).data("accountno");
                       location.href="<%=request.getContextPath()%>/views/savings/list/"+accountNumber;
                   }              
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