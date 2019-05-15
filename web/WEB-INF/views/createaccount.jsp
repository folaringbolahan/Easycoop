<%@ include file="includes/header.jsp" %>  
<style>
    .autocomplete-suggestions { border: 1px solid #999; background: #FFF; overflow: auto; }
    .autocomplete-suggestion { padding: 5px 5px; white-space: nowrap; overflow: hidden; font-size:18px}
    .autocomplete-selected { background: #F0F0F0; }
    .autocomplete-suggestions strong { font-weight: bold; color: #D9534F; }
</style>
<div class="media-body">
    <!-- changes starts -->
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="#">Home</a></li>
            <li>Savings Account Management</li>
        </ul>
        <h4>Setup Account</h4>
    </div>
    <%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->

<div class="contentpanel">
    <div class="row">
        <div class="col-md-12">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-12">
                <!-- Nav tabs -->
                <ul class="nav nav-tabs nav-danger">
                    <li class="active"><a href="#home6" data-toggle="tab"><strong>Setup Account</strong></a></li>

                    <li><a href="#profile7" data-toggle="tab"><strong>Manage Accounts</strong></a></li>
                </ul>

                <!-- Tab panes -->
                <div class="tab-content tab-content-danger mb30">
                    <div class="tab-pane active" id="home6">

                        <p>  <form method="POST"  action="${pageContext.request.contextPath}/views/savings/getaccount" id="accountForm">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <div class="panel-btns">
                                        <a href="#" class="panel-minimize tooltips" data-toggle="tooltip" title="Minimize Panel"><i class="fa fa-minus"></i></a>

                                    </div><!-- panel-btns -->
                                    <h4 class="panel-title">Account Setup Form</h4>
                                    <p>Kindly fill the form below to set up a new account.</p>

                                </div>
                                <div class="panel-body">
                                    <div id="submitResponse" class="alert alert-success" style="display:none">
                                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                        <p id="success-message"></p>
                                    </div>
                                    <div class="errorForm"></div>
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label tooltips" data-toggle="tooltip" title="Search for customer to create account for">Customer Name<span class="asterisk">*</span></label>
                                        <div class="col-sm-4">

                                            <input name="customerName" class="form-control"  id="customerName" required="required" />
                                            <input name="customerId" class="form-control" type="hidden"  id="customerId" required="required"  />
                                        </div>
                                    </div><!-- form-group -->
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label tooltips" data-toggle="tooltip" title="Select the product attached to this account">Product<span class="asterisk">*</span></label>
                                        <div class="col-sm-4">
                                            <select  cssClass="width300" id="productCode"  name="productCode"  required="required"  >
                                                <option value="">Choose Product</option>>
                                                <c:forEach  var="product" items="${products}" varStatus="status" >

                                                    <option value="${product.code}">${product.name}</option>
                                                </c:forEach>
                                            </select>


                                        </div>
                                    </div><!-- form-group -->


                                </div><!-- panel-body -->
                                <div class="panel-footer">
                                    <button class="btn btn-danger mr5" id="createAccount">Submit</button>
                                    <button type="reset" class="btn btn-default">Reset</button>
                                </div><!-- panel-footer -->
                            </div><!-- panel-default -->
                        </form></p>
                    </div><!-- tab-pane -->


                    <div class="tab-pane" id="profile7">

                        <div class="row">
                            <div class="col-md-12">
                                <table id="account-list" class="table table-striped table-bordered responsive">
                                    <thead class="">
                                        <tr>
                                          
                                            <th>SN</th>
                                            <th>Account No</th>
                                            <th>Product</th>
                                            <th>Title</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                        <c:forEach  var="account" items="${accounts}" varStatus="status" >
                                            <tr>
                                                
                                                <td data-id="${account.accountNumber}">${status.index + 1}</td>
                                                <td data-id="${account.accountNumber}">${account.accountNumber}</td>
                                                <td data-id="${account.accountNumber}">${account.name} - ${account.productTypeCode}</td>
                                                <td data-id="${account.accountNumber}">${account.title}</td>

                                                <td class="table-action">
                                                    
                                                    <c:if test="${account.productTypeCode=='S' || account.productTypeCode=='C'}">
                                                 <button data-accountnumber="${account.accountNumber}" data-product="${account.productCode}" class="btn btn-danger btn-metro add-product" id="addSavings-${status.index + 1}" onclick='return addSavings("addSavings-${status.index + 1}");' >Post Contribution</button> 
                                                 </c:if>&nbsp;
                                                     <c:if test="${account.productTypeCode=='S'}">
                                                 <button data-accountnumber="${account.accountNumber}" data-product="${account.productCode}" class="btn btn-danger btn-metro add-withdrawal" onclick='return addWithdrawal("addWithdrawal-${status.index + 1}");'  id="addWithdrawal-${status.index + 1}">Withdraw</button>
                                                </c:if>
                                                     </td>
                                            </tr>
                                        </c:forEach>
                                  
                                    </tbody>
                                </table>
                            </div>

                        </div>
                        <div class="row">
                            <div class="col-md-12">

                            </div>
                        </div>
                    </div><!-- tab-pane -->

                </div><!-- tab-content -->



            </div>
        </div>
        <!-- savings Modal starts -->

        <div class="modal fade bs-example-modal-lg" tabindex="-1" id="savingModal" role="dialog">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button aria-hidden="true" data-dismiss="modal" class="close" type="button">&times;</button>
                        <h4 class="modal-title">Add Contribution</h4>
                    </div>
                    <div class="modal-body">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="panel-btns">
                                    <a href="#" class="panel-minimize tooltips" data-toggle="tooltip" title="Minimize Panel"><i class="fa fa-minus"></i></a>

                                </div><!-- panel-btns -->
                                <h4 class="panel-title">Add Contribution</h4>
                                <p>Select your preferred option and click submit</p>
                            </div><!-- panel-heading -->
                            <div class="panel-body">
                                <div class="row">


                                    <form method="POST"  action="${pageContext.request.
                                                                   contextPath}/views/savings/transaction" id="savingsForm">


                                        <div class="panel-body">
                                            <div id="postResponse" class="alert alert-success" style="display: none;">
                                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                                <p id="post-message"></p>
                                            </div>
                                            <div class="errorForm"></div>
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">Customer Account<span class="asterisk">*</span></label>
                                                <div class="col-sm-4">

                                                    <input name="accountNumber" class="form-control"  id="accountNumber" required="required" />
                                                </div>
                                            </div><!-- form-group -->

                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">Amount <span class="asterisk">*</span></label>
                                                <div class="col-sm-4">

                                                    <input name="amount"  class="form-control" id="amount" value="0.0" required="required" />
                                                </div>
                                            </div><!-- form-group -->
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">Product<span class="asterisk">*</span></label>
                                                <div class="col-sm-4">
                                                    <input type="hidden" name="productId"  class="form-control" id="productId"  required="required" />
                                                    <input type="text" name="sproductCode"  class="form-control" id="sproductCode"  required="required" />
                                                </div>
                                            </div><!-- form-group -->
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">Transaction Date<span class="asterisk">*</span></label>
                                                <div class="col-sm-4">
                                                    <input path="stringDate"  class="form-control" id="stringDate"  required="required" />



                                                </div>
                                            </div><!-- form-group -->

                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">Description <span class="asterisk"></span></label>
                                                <div class="col-sm-4">

                                                    <textarea name="description"  class="form-control" id="description"></textarea>
                                                </div>
                                            </div><!-- form-group -->
                                        </div><!-- panel-body -->
                                        <div class="panel-footer">
                                            <button class="btn btn-danger mr5" id="trxButton">Submit</button>
                                            <button type="reset" class="btn btn-default">Reset</button>
                                        </div><!-- panel-footer -->

                                    </form>

                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">

                        </div>
                    </div>
                </div>
            </div> <!--Modal ends -->
        </div> <!-- savings Modal ends -->
        <!-- withdraw Modal starts -->

        <div class="modal fade bs-example-modal-lg" tabindex="-1" id="withdrawModal" role="dialog">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button aria-hidden="true" data-dismiss="modal" class="close" type="button">&times;</button>
                        <h4 class="modal-title">Withdaw Transaction</h4>
                    </div>
                    <div class="modal-body">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="panel-btns">
                                    <a href="#" class="panel-minimize tooltips" data-toggle="tooltip" title="Minimize Panel"><i class="fa fa-minus"></i></a>

                                </div><!-- panel-btns -->
                                <h4 class="panel-title">Make Withdrawal</h4>
                                <p>Fill the form below to make a withdraw transaction</p>
                            </div><!-- panel-heading -->
                            <div class="panel-body">
                                <div class="row">


                                    <form method="POST"  action="${pageContext.request.
                                                                   contextPath}/views/savings/withdraw" id="withdrawForm">


                                        <div class="panel-body">
                                            <div id="withdrawResponse" class="alert alert-success" style="display: none;">
                                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                                <p id="withdraw-message"></p>
                                            </div>
                                            <div class="errorForm"></div>
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">Customer Account<span class="asterisk">*</span></label>
                                                <div class="col-sm-4">

                                                    <input name="withdrawAccountNumber" class="form-control"  id="withdrawAccountNumber" required="required" />
                                                    <span id="availability"></span>
                                                </div>
                                            </div><!-- form-group -->

                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">Amount <span class="asterisk">*</span></label>
                                                <div class="col-sm-4">

                                                    <input name="withdrawAmount"  class="form-control" id="withdrawAmount" value="0.0" required="required" />
                                                </div>
                                            </div><!-- form-group -->
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">Product<span class="asterisk">*</span></label>
                                                <div class="col-sm-4">
                                                    <input type="hidden" name="withdrawProductId"  class="form-control" id="withdrawProductId"  required="required" />
                                                    <input type="text" name="sWithdrawProductCode"  class="form-control" id="sWithdrawProductCode"  required="required" />
                                                </div>
                                            </div><!-- form-group -->
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">Transaction Date<span class="asterisk">*</span></label>
                                                <div class="col-sm-4">
                                                    <input path="stringWithdrawDate"  class="form-control" id="stringWithdrawDate"  required="required" />



                                                </div>
                                            </div><!-- form-group -->

                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">Description <span class="asterisk"></span></label>
                                                <div class="col-sm-4">

                                                    <textarea name="withdrawDescription"  class="form-control" id="withdrawDescription"></textarea>
                                                </div>
                                            </div><!-- form-group -->
                                        </div><!-- panel-body -->
                                        <div class="panel-footer">
                                            <button class="btn btn-danger mr5" id="withdrawButton">Submit</button>
                                            <button type="reset" class="btn btn-default">Reset</button>
                                        </div><!-- panel-footer -->

                                    </form>

                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">

                        </div>
                    </div>
                </div>
            </div> <!--Modal ends -->
        </div> <!-- withdraw Modal ends -->
    </div><!-- contentpanel -->

    <%@ include file="includes/footer.jsp" %>  
    <script>
                                                        function addSavings(objId) {

                                                            var product = $("#" + objId).data("product");
                                                            var accountNumber = $("#" + objId).data("accountnumber");
                                                            $("#accountNumber").val(accountNumber);
                                                            $("#sproductCode").val(product);
                                                            jQuery("#postResponse").css("display", "none");
                                                            jQuery("#amount").val("0.00");
                                                            jQuery("#stringDate").val("");
                                                            jQuery("#description").val("");
                                                            $("#savingModal").modal('show');
                                                        }
                                                        function addWithdrawal(objId) {
                                                            var product = $("#" + objId).data("product");
                                                            var accountNumber = $("#" + objId).data("accountnumber");
                                                            $("#withdrawAccountNumber").val(accountNumber);

                                                            $("#sWithdrawProductCode").val(product);
                                                            jQuery("#withdrawResponse").css("display", "none");
                                                            jQuery("#withdrawAmount").val("0.00");
                                                            jQuery("#stringWithdrawDate").val("");
                                                            jQuery("#withdrawDescription").val("");
                                                            $("#withdrawModal").modal('show');
                                                        }
                                                        $(document).ready(function() {
                                                            jQuery('#stringDate').datepicker();
                                                            jQuery('#stringWithdrawDate').datepicker();

                                                            $.datepicker.setDefaults({dateFormat: 'dd-mm-yy'});
                                                            jQuery('#account-list').DataTable({
                                                                responsive: true
                                                            });
                                                            jQuery('#account-list .group-checkable').change(function() {
                                                                var set = jQuery(this).attr("data-set");
                                                                var checked = jQuery(this).is(":checked");
                                                                jQuery(set).each(function() {
                                                                    if (checked) {
                                                                        $(this).attr("checked", true);
                                                                    } else {
                                                                        $(this).attr("checked", false);
                                                                    }
                                                                });

                                                            });
                                                            $('#accountForm').submit(function(event) {
                                                                var customerId = $('#customerId').val();
                                                                var productCode = $('#productCode').val();
                                                                $("#createAccount").attr("disabled", "disabled");
                                                                $.get("<%=request.getContextPath()%>/views/savings/getaccount", {"customerId": customerId, "productCode": productCode}, function(result) {

                                                                    $("#success-message").html(result);
                                                                    $('#submitResponse').css("display", "block");
                                                                    $('#submitResponse').focus();
                                                                    $("#createAccount").attr("disabled", "");
                                                                });
                                                                event.preventDefault();
                                                            });
                                                            $('#savingsForm').submit(function(event) {
                                                                var accountNumber = $("#accountNumber").val();
                                                                var amount = $("#amount").val();
                                                                var sproductCode = $("#sproductCode").val();
                                                                var description = $("#description").val();
                                                                var stringDate = $("#stringDate").val();
                                                                var json = {"accountNumber": accountNumber, "amount": amount, "description": description, "sproductCode": sproductCode, "stringDate": stringDate};
                                                                $("#trxButton").attr("disabled", "disabled");
                                                                $.get("<%=request.getContextPath()%>/views/savings/transaction", {"accountNumber": accountNumber, "amount": amount, "description": description, "sproductCode": sproductCode, "stringDate": stringDate}, function(result) {

                                                                    $("#post-message").html(result);
                                                                    $('#postResponse').css("display", "block");
                                                                    $('#postResponse').focus();

                                                                });
                                                                event.preventDefault();


                                                            });
                                                            $('#withdrawForm').submit(function(event) {
                                                                var accountNumber = $("#withdrawAccountNumber").val();
                                                                var amount = $("#withdrawAmount").val();
                                                                var sproductCode = $("#sWithdrawProductCode").val();
                                                                var description = $("#withdrawDescription").val();
                                                                var stringDate = $("#stringWithdrawDate").val();
                                                                var json = {"accountNumber": accountNumber, "amount": amount, "description": description, "sproductCode": sproductCode, "stringDate": stringDate};
                                                                $("#trxButton").attr("disabled", "disabled");
                                                                $.get("<%=request.getContextPath()%>/views/savings/withdraw", {"accountNumber": accountNumber, "amount": amount, "description": description, "sproductCode": sproductCode, "stringDate": stringDate}, function(result) {

                                                                    $("#withdraw-message").html(result);
                                                                    $('#withdrawResponse').css("display", "block");
                                                                    $('#withdrawResponse').focus();

                                                                });
                                                                event.preventDefault();


                                                            });
                                                            //attach autocomplete
                                                            $('#customerName').autocomplete({
                                                                serviceUrl: '${pageContext.request.contextPath}/views/savings/getMembers',
                                                                paramName: "name",
                                                                delimiter: ",",
                                                                transformResult: function(response) {

                                                                    return {
                                                                        suggestions: $.map($.parseJSON(response), function(item) {

                                                                            return {value: item.firstName + " " + item.middleName + " " + item.surname, data: item.id};
                                                                        })

                                                                    };

                                                                },
                                                                onSelect: function(suggestions) {
                                                                    $("#customerId").val(suggestions.data);

                                                                },
                                                            });
                                                            /** $(".add-product").click(function() {
                                                             var product = $(this).data("product");
                                                             var accountNumber = $(this).data("accountnumber");
                                                             $("#accountNumber").val(accountNumber);
                                                             $("#sproductCode").val(product);
                                                             $("#savingModal").modal('show');
                                                             });**/
                                                            /**    $(".add-withdrawal").click(function() {
                                                             var product = $(this).data("product");
                                                             var accountNumber = $(this).data("accountnumber");
                                                             $("#withdrawAccountNumber").val(accountNumber);
                                                             $("#sWithdrawProductCode").val(product);
                                                             $("#withdrawModal").modal('show');
                                                             });**/
                                                        });
    </script>