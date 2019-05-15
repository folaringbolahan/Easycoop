<%@ include file="includes/header.jsp" %>  

<div class="media-body">
    <!-- changes starts -->
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="#">Home</a></li>
            <li>Transaction Management</li>
        </ul>
        <h4>Add Transaction</h4>
    </div>
    <%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->

<div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">

                <form:form method="POST" commandName="savingsForm" action="${pageContext.request.
                                                                             contextPath}/views/savings/post" id="savingsForm">
                           <div class="panel panel-default">
                               <div class="panel-heading">
                                   <div class="panel-btns">
                                       <a href="#" class="panel-minimize tooltips" data-toggle="tooltip" title="Minimize Panel"><i class="fa fa-minus"></i></a>
                                       <a href="#" class="panel-close tooltips" data-toggle="tooltip" title="Close Panel"><i class="fa fa-times"></i></a>
                                   </div><!-- panel-btns -->
                                   <h4 class="panel-title">Transaction form</h4>
                                   <p>Kindly fill the form below to create a new transaction.</p>
                               </div>
                               <div class="panel-body">
                                   <div class="alert alert-danger"  id="errorMessage" style="display:none;">
                                       <button type="button" class="close" data-dismiss="alert" aria-hidden="true" >&times;</button>
                                       <span id="errorMessageStatus">Operation failed! Please try again</span>
                                   </div>
                                   <div class="errorForm"></div>
                                   <div class="form-group">
                                       <label class="col-sm-4 control-label tooltips" data-toggle="tooltip" title="Enter Customer Account">Customer Account<span class="asterisk">*</span></label>
                                       <div class="col-sm-4">

                                           <form:input path="accountNumber" cssClass="form-control"  id="accountNumber" required="required" onblur="checkAvailability(this.value)" />
                                           <span id="availability"></span>
                                           <input id="accountNo" type="hidden" name="accountNo" />
                                       </div>
                                   </div><!-- form-group -->

                                   <div class="form-group">
                                       <label class="col-sm-4 control-label tooltips" data-toggle="tooltip" title="Enter Amount to post">Amount <span class="asterisk">*</span>
                                       </label>
                                       <div class="col-sm-4">

                                           <form:input path="amount"  cssClass="form-control" id="amount"  required="required" />
                                       </div>
                                   </div><!-- form-group -->
                                
                                   <div class="form-group">
                                       <label class="col-sm-4 control-label tooltips" data-toggle="tooltip" title="Enter transaction date">Transaction Date<span class="asterisk">*</span></label>
                                       <div class="col-sm-4">
                                           <form:input path="stringDate"  cssClass="form-control" id="stringDate"  required="required" />
                                           <form:hidden path="companyId" value="${user.curruser.companyid}"  cssClass="form-control" id="companyId"  />
                                           <form:hidden path="branchId"  value="${user.curruser.branch}" cssClass="form-control" id="branchId"  />
                                           <form:hidden path="memberId"  cssClass="form-control" id="memberId"  />
                                           <form:hidden path="userId"  cssClass="form-control" id="userId"  />
                                       </div>
                                   </div><!-- form-group -->

                                   <div class="form-group">
                                       <label class="col-sm-4 control-label tooltips" data-toggle="tooltip" title="Enter transaction description">Description <span class="asterisk"></span></label>
                                       <div class="col-sm-4">

                                           <form:textarea path="description"  cssClass="form-control" id="description" />
                                       </div>
                                   </div><!-- form-group -->
                               </div><!-- panel-body -->
                               <div class="panel-footer">
                                   <button class="btn btn-danger mr5" id="btnSave">Submit</button>
                                   <button type="reset" class="btn btn-default">Reset</button>
                               </div><!-- panel-footer -->
                           </div><!-- panel-default -->
                </form:form>
            </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->

<%@ include file="includes/footer.jsp" %>  
<script>
    function checkAvailability(account) {
        $.get("<%=request.getContextPath()%>/views/savings/checkaccount", {"account": account}, function(result) {

            if (result === 'ok') {
                $("#availability").html('<i class="fa fa-check"></i>');
                $('#accountNumber').css("border-color", "#999");
                $("#accountNo").val("ok");
                 jQuery("#errorMessage").css("display", "none");
                  $("#errorMessage").css("display","none");
                    $("#errorMessage").css("display","none");
                  $("#btnSave").removeAttr("disabled");
            }else if(result === 'notok') {
                $("#availability").html('<i class="fa fa-warning">Not available</i>');
                $('#accountNumber').css("border-color", "#FF0000");
                $("#accountNo").val("notok");
                $("#btnSave").attr("disabled","disabled");
                  $("#errorMessage").css("display","none");
            }else{
                $("#errorMessage").css("display","block");
                $("#errorMessageStatus").html(result);
                //$("#availability").html('<i class="fa fa-warning">'+result+'</i>');
                $('#accountNumber').css("border-color", "#FF0000");
                $("#accountNo").val("notok");
                  $("#btnSave").attr("disabled","disabled");
            }

        });
    }

    $(document).ready(function() {

        jQuery('#stringDate').datepicker();
        $.datepicker.setDefaults({dateFormat: 'dd-mm-yy'});
        jQuery('select').select2({
            minimumResultsForSearch: -1
        });

        $('#memberId').val(1);
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
                "Please select a product"
                );
        jQuery.validator.addMethod(
                "dateFormat",
                function(value, element) {

                    return value.match(/^dd?-dd?-dd$/);
                },
                "Invalid date/date format entered"
                );
        jQuery.validator.addMethod(
                "money",
                function(value, element) {
                    var isValidMoney = /^\d{0,20}(\.\d{0,2})?$/.test(value);
                    return this.optional(element) || isValidMoney;
                },
                "Invalid amount entered"
                );
        jQuery("#savingsForm").validate({
            rules: {
                amount: {
                    required: true,
                    money: true
                },
                productId: {
                    required: true,
                    select: true
                }
                /**,
                 stringDate: {
                 required: true,
                 dateFormat: true
                 }**/
            },
            highlight: function(element) {
                jQuery(element).closest('.form-group').removeClass('has-success').addClass('has-error');
            },
            success: function(element) {
                jQuery(element).closest('.form-group').removeClass('has-error');
            }
        });
        $('#savingsForm').submit(function(event) {
            // event.preventDefault();
            if ($("#accountNo").val() === "notok") {
                 jQuery("#errorMessageStatus").html("Invalid Account Number");
                 jQuery("#errorMessage").css("display", "block");
             
                return false;
            } else {
                jQuery(this).submit();
            }
            event.preventDefault();
        });
       

    })
</script>