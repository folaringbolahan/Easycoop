<%@ include file="includes/header.jsp" %>  
<style>
    #fade {
        display: none;
        position:absolute;
        top: 0%;
        left: 0%;
        width: 100%;
        height: 100%;
        background-color: #ababab;
        z-index: 1001;
        -moz-opacity: 0.8;
        opacity: .70;
        filter: alpha(opacity=80);
    }

    #image-loader {
        display: none;
        position: absolute;
        top: 45%;
        left: 45%;
        width: 164px;
        height: 64px;
        padding:30px 15px 0px;
        border: 3px solid #ababab;
        box-shadow:1px 1px 10px #ababab;
        border-radius:20px;
        background-color: white;
        z-index: 1002;
        text-align:center;
        overflow: auto;
    }
</style>
<div class="media-body">
    <!-- changes starts -->
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="#">Home</a></li>
            <li>Product Management</li>
        </ul>
        <h4>Add Product</h4>
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


                <p>  
                    <form:form method="POST" commandName="productForm" action="${pageContext.request.
                                                                                 contextPath}/views/product/edit" id="productForm">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div class="panel-btns">
                                <a href="#" class="panel-minimize tooltips" data-toggle="tooltip" title="Minimize Panel"><i class="fa fa-minus"></i></a>
                                <a href="#" class="panel-close tooltips" data-toggle="tooltip" title="Close Panel"><i class="fa fa-times"></i></a>
                            </div><!-- panel-btns -->
                            <h4 class="panel-title">Product creation form</h4>
                            <p>Kindly fill the form below to create a new product.</p>

                        </div>
                        <div class="panel-body">
                            <div class="alert alert-danger"  id="errorMessage" style="display:none;">
                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true" >&times;</button>
                                <span id="errorMessageStatus"></span>
                            </div>
                            <div class="alert alert-info fade in nomargin" id="productAddStatus" style="display: none;">
                                <button aria-hidden="true" data-dismiss="alert" class="close" type="button">&times;</button>
                                <h4>Operation Successful!</h4>
                                <p id="submitResponse"></p>
                                <p>
                                    <button class="btn btn-danger" type="button">Continue</button>
                                    <button class="btn btn-white" type="button">Cancel</button>
                                </p>
                            </div>
                            <div class="errorForm"></div>
                            <div class="form-group">
                                <label data-toggle="tooltip" title="Enter product name for identification" class="col-sm-4 tooltips control-label">Product Name <span class="asterisk">*</span></label>
                                <div class="col-sm-4">

                                    <form:input path="name"   cssClass="form-control" id="productName"  required="required" />
                                </div>
                            </div><!-- form-group -->

                            <div class="form-group">
                                <label class="col-sm-4 control-label tooltips" data-toggle="tooltip" title="Select Product type ">Product Type <span class="asterisk">*</span></label>
                                <div class="col-sm-4">

                                    <form:select cssClass="width300" id="productTypeCode"  path="productTypeCode"  required="required"  >
                                        <form:option value="" label="Choose Product" />
                                        <form:options items="${productList}" itemValue="code" itemLabel="name" />
                                    </form:select>

                                </div>
                            </div>
                            <!-- form-group -->
                            <div class="form-group" id="interestLoanType">
                                <label data-toggle="tooltip" title="Assign Loan Interest Type" class="col-sm-4 tooltips control-label">Assign Loan Interest Type<span class="asterisk">*</span></label>
                                <div class="col-sm-4">
                                    <form:select cssClass="width300" id="loanTypeCode"  path="loanTypeCode"  required="required"  >
                                        <form:option value="" label="Choose Interest Type" />
                                       
                                    </form:select>

                                </div>
                            </div><!-- form-group -->  

                            <div class="form-group" id="loadDurationContainer">
                                <label data-toggle="tooltip" title="Type in the duration of the loan(months) " class="col-sm-4 tooltips control-label">Enter Loan Duration(months)<span class="asterisk">*</span></label>
                                <div class="col-sm-4">
                                    <form:input path="loanDuration" cssClass="form-control" id="loanDuration"  required="required" />

                                </div>
                            </div><!-- form-group -->  
                            <!-- form-group -->
                            <div class="form-group" id="operandsContainer">
                                <label data-toggle="tooltip" title="Build formula for penalty calculation" class="col-sm-4 tooltips control-label">Select Operand</label>
                                <div class="col-sm-4">
                                    <select class="width300" id="cmbOperands" >
                                        <option value="">Choose Operands</option>

                                        <c:forEach  var="operand" items="${operands}" varStatus="status" >

                                            <option value="${operand.code}">${operand.name} - ${operand.code}</option>

                                        </c:forEach>
                                    </select>
                                    <button class="btn btn-default" type="button"  id="addOperand">Add</button>
                                </div>
                                <div  class="col-sm-4">
                                    <select class="width300" id="cmbOperators" >
                                        <option value="">Choose Operator</option>

                                        <option value="*">*</option>
                                        <option value="/">/</option>
                                        <option value="+">+</option>
                                        <option value="-">-</option>
                                    </select>
                                    <button class="btn btn-default" type="button" id="addOperator">Add</button>
                                </div>
                            </div><!-- form-group -->  
                            <div class="form-group" id="formularContainer">
                                <label data-toggle="tooltip" title="Build formula for penalty calculation" class="col-sm-4 tooltips control-label" >Formula for penalty calculation</label>
                                <div class="col-sm-4">

                                    <form:input path="penaltyFormula"   cssClass="form-control" id="penaltyFormula"  required="required" />
                                </div>
                            </div><!-- form-group -->
                            <div id="fade"></div>
                            <div id="image-loader" style="display:none">
                                <img src="${resourceUrl}/images/loaders/loader19.gif" alt="" /> 
                            </div>
                            <div class="form-group">
                                <label data-toggle="tooltip" title="Select Control Account For This Product " class="col-sm-4 tooltips control-label">Assign Control Account <span class="asterisk">*</span></label>
                                <div class="col-sm-4">
                                    <select  class="width300" id="controlAccount"  path="controlAccount"  required="required"  >
                                        <option value="">Choose Account</option>
                                        <c:forEach  var="account" items="${accounts}" varStatus="status" >

                                            <option data-control="${account.controlAccount}" value="${account.accountNo}">${account.name}</option>
                                        </c:forEach>
                                    </select>
                                    <input type="hidden" name="productid" id="productAccountProductId" />
                                    <input type="hidden" name="hiddenSegmnentCode" id="hiddenSegmnentCode" />
                                    <div class="mb30"></div>
                                </div>
                            </div><!-- form-group -->

                            <div class="form-group">
                                <label data-toggle="tooltip" title="Is this product mandatory for all members to have?" class="col-sm-4 tooltips control-label">Mandatory?<span class="asterisk">*</span></label>
                                <div class="col-sm-8">
                                    <div class="ckbox ckbox-danger">
                                        <div class="rdio rdio-danger">

                                            <form:radiobutton id="yesIsDefault" value="1" path="isDefault"  required="required" />
                                            <label for="yesIsDefault">Yes</label>
                                        </div><!-- rdio -->
                                        <div class="rdio rdio-danger">
                                            <form:radiobutton id="noIsDefault" value="0"  path="isDefault"  required="required" />
                                            <label for="noIsDefault">No</label>
                                        </div><!-- rdio -->

                                    </div>
                                </div>
                                <div class="form-group">
                                    <label data-toggle="tooltip" title="Does this product attract any interest? Select Yes if it does" class="col-sm-4 tooltips control-label">Attracts Interest?<span class="asterisk">*</span></label>
                                    <div class="col-sm-8">
                                        <div class="ckbox ckbox-danger">
                                            <div class="rdio rdio-danger">

                                                <form:radiobutton id="yesInterest" value="1" path="hasInterest"  required="required" />
                                                <label for="yesInterest">Yes</label>
                                            </div><!-- rdio -->
                                            <div class="rdio rdio-danger">
                                                <form:radiobutton id="noInterest" value="0"  path="hasInterest"  required="required" />
                                                <label for="noInterest">No</label>
                                            </div><!-- rdio -->

                                        </div>
                                    </div>
                                </div><!-- form-group -->
                                <div class="form-group" id="interest-div">
                                    <label class="col-sm-4 control-label tooltips" data-toggle="tooltip" title="Enter interest rate">Interest Rate</label>
                                    <div class="col-sm-2">
                                        <form:input path="interestRate" cssClass="form-control"  id="interestRate" />
                                    </div>
                                    <div>
                                        <form:hidden value="${companyId}" path="companyId" />

                                    </div>
                                </div><!-- form-group -->  
                                <div class="form-group">
                                    <label class="col-sm-4 control-label tooltips" data-toggle="tooltip" title="Select if thia product attracts penalty">Attracts Penalty?<span class="asterisk">*</span></label>
                                    <div class="col-sm-8">
                                        <div class="ckbox ckbox-danger">
                                            <div class="rdio rdio-danger">

                                                <form:radiobutton id="yesPenalty" value="1" path="hasPenalty"  required="required" />
                                                <label for="yesPenalty">Yes</label>
                                            </div><!-- rdio -->
                                            <div class="rdio rdio-danger">
                                                <form:radiobutton id="noPenalty" value="${product.hasPenalty}"  path="hasPenalty"  required="required" />
                                                <label for="noPenalty">No</label>
                                            </div><!-- rdio -->

                                        </div>
                                    </div>
                                </div><!-- form-group -->
                                <div class="form-group" id="dayspenalty-div">
                                    <label class="col-sm-4 control-label tooltips" data-toggle="tooltip" title="Number of default days before the penalty gets applied">Default days before Application</label>
                                    <div class="col-sm-2">
                                        <form:input path="defaultPenaltyDays" cssClass="form-control"  id="defaultPenaltyDays" />
                                    </div>

                                </div><!-- form-group -->  
                                <!-- form-group -->
                                <div class="form-group" id="penalty-div">
                                    <label class="col-sm-4 control-label tooltips" data-toggle="tooltip" title="Enter penalty">Penalty</label>
                                    <div class="col-sm-2">
                                        <form:input path="penalty" cssClass="form-control"  id="penalty" />
                                    </div>
                                    <div>

                                    </div>
                                </div><!-- form-group --> 
                                <div class="form-group">
                                    <label class="col-sm-4 control-label tooltips" data-toggle="tooltip" title="Select Yes if this product attracts tax and No if it does not">Attracts Tax?<span class="asterisk">*</span></label>
                                    <div class="col-sm-8">
                                        <div class="ckbox ckbox-danger">
                                            <div class="rdio rdio-danger">

                                                <form:radiobutton id="yesTax" value="1" path="isTaxable"  required="required" />
                                                <label for="yesTax">Yes</label>
                                            </div><!-- rdio -->
                                            <div class="rdio rdio-danger">
                                                <form:radiobutton id="noTax" value="${product.isTaxable}"  path="isTaxable"  required="required" />
                                                <label for="noTax">No</label>
                                            </div><!-- rdio -->

                                        </div>
                                    </div>
                                </div>
                            </div><!-- panel-body -->
                            <div class="panel-footer">
                                <button class="btn btn-danger mr5" id="btnSubmit">Submit</button>
                                <button type="reset" class="btn btn-default">Reset</button>
                            </div><!-- panel-footer -->
                        </div><!-- panel-default -->
                    </form:form></p>
                    <!-- tab-pane -->





                    <!-- tab-content -->
                    <div class="modal fade bs-example-modal-sm" tabindex="-1" id="statusMoal" role="dialog">
                        <div class="modal-dialog modal-sm">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button aria-hidden="true" data-dismiss="modal" class="close" type="button">&times;</button>
                                    <h4 class="modal-title">Status Update</h4>
                                </div>
                                <div class="modal-body"><p id="status-text"></p></div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Ok</button>


                                    </form>
                                </div>
                            </div>
                        </div>
                    </div> <!--Modal ends -->
                </div><!-- col-md-6 -->
            </div>
        </div>
    </div><!-- contentpanel -->

    <%@ include file="includes/footer.jsp" %>  
    <script>
        function checkAvailability(code) {
            $.get("<%=request.getContextPath()%>/views/product/getcode", {"code": code}, function(result) {

                if (result === 'ok') {
                    $("#availability").html('<i class="fa fa-check"></i>');
                    $('#productCode').css("border-color", "#999");
                } else {
                    $("#availability").html('<i class="fa fa-warning">Not available</i>');
                    $('#productCode').css("border-color", "#FF0000");
                }

            });
        }
        function getSegmentCode(n) {
            $.get("<%=request.getContextPath()%>/views/product/getSegmentCode", {"accountNumber": n}, function(result) {

                if (result === 'error') {
                    //  $("#availability").html('<i class="fa fa-warning"></i>');
                    //   $('#productCode').css("border-color", "#999");
                } else {
                    //  $("#availability").html('<i class="fa fa-warning">Not available</i>');
                    //  $('#productCode').css("border-color", "#FF0000");
                }

            });
        }
        $(document).ready(function() {
            jQuery('#datepicker').datepicker();
            $('#interest-div').css("display", 'none');
            $('#penalty-div').css("display", 'none');
            $('#dayspenalty-div').css("display", 'none');
            $("#operandsContainer").css("display", "none");
            $("#formularContainer").css("display", "none");
            $('#dvTaxCode1').css("display", 'none');
            $('#dvTaxCode2').css("display", 'none');
            $('#dvTaxCode3').css("display", 'none');
            $('#penalty-div').css("display", 'none');
            jQuery('select').select2({
                minimumResultsForSearch: -1
            });
            jQuery("#interestLoanType").css("display", "none");
            jQuery("#loadDurationContainer").css("display", "none");
            jQuery("#productTypeCode").change(function() {
                var productValue = jQuery(this).val();
                if (productValue === "L" || productValue === "P") {
                    jQuery("#interestLoanType").css("display", "block");
                    jQuery("#loadDurationContainer").css("display", "block");
                    $("#operandsContainer").css("display", "block");
                    $("#formularContainer").css("display", "block");
                    $.get("<%=request.getContextPath()%>/views/product/loantypes", {"type": productValue}, function(records) {
                $('#loanTypeCode').children('option:not(:first)').remove();
                        $.each(records, function(i, item) {
                       /**     $('#loanTypeCode').append($('<option>', {
                                value: item.typeCode,
                                text: item.typeName
                            }));**/
                            
                            $('#loanTypeCode')
                                .append($("<option></option>")
                                .attr("value", item.typeCode)
                                .text(item.typeName));
                        });

                    });
                } else {
                    jQuery("#interestLoanType").css("display", "none");
                    jQuery("#loadDurationContainer").css("display", "none");
                    jQuery("#operandsContainer").css("display", "none");
                    $("#formularContainer").css("display", "none");
                    if (productValue === "C") {
                        $.get("<%=request.getContextPath()%>/views/product/checkproduct", {"code": productValue}, function(result) {

                            if (result === 'ok') {
                                jQuery("#errorMessage").css("display", "none");
                                jQuery("#btnSubmit").removeAttr("disabled");
                                $(this).css("border-color", "#FF0000");
                            } else {
                                jQuery("#errorMessage").css("display", "block");
                                jQuery("#errorMessageStatus").html("Contribution product already exists");

                                $(this).css("border-color", "#999");
                                jQuery("#btnSubmit").attr("disabled", "disabled");
                            }

                        });
                    }
                    jQuery("#errorMessage").css("display", "none");
                    jQuery("#btnSubmit").removeAttr("disabled");
                    $(this).css("border-color", "#FF0000");
                }
            });
            jQuery("#productName").attr("data-toggle", "tooltip");
            jQuery("#productName").attr("title", "Enter Product Name for identification");
            jQuery("#productName").addClass("tooltips");

            jQuery("#productTypeCode").attr("data-toggle", "tooltip");
            jQuery("#productTypeCode").addClass("tooltips");
            jQuery("#productTypeCode").attr("title", "Select Product Type e.g Savings, Purcahses");
            jQuery('#productAddStatus').css('display', 'none');
            jQuery("#productForm").validate({
                highlight: function(element) {
                    jQuery(element).closest('.form-group').removeClass('has-success').addClass('has-error');
                },
                success: function(element) {
                    jQuery(element).closest('.form-group').removeClass('has-error');
                }
            });
            $("#controlAccount").change(function() {
                $.get("<%=request.getContextPath()%>/views/product/getsegmentcode", {"accountNumber": $(this).val()}, function(result) {

                    if (result === '') {
                        alert("No Segment code for this . Please choose another account");
                    } else {
                        $("#hiddenSegmnentCode").val(result);


                    }

                })
                        .fail(function() {
                    alert("No Segment code for this . Please choose another account");
                    $("#hiddenSegmnentCode").val("");
                });
            });
            $('#productForm').submit(function(event) {


                var name = $("#productName").val();
                var interestRate = $("#interestRate").val();
                var penalty = $("#penalty").val();
                var productType = $("#productTypeCode").val();
                var controlAccount = $("#controlAccount").val();
                var loanDuration = $("#loanDuration").val();
                var companyId = $("#companyId").val();
                var hasInterest = $("input[name='hasInterest']:checked").val();
                var hasPenalty = $("input[name='hasPenalty']:checked").val();
                var isTaxable = $("input[name='isTaxable']:checked").val();
                var isDefault = $("input[name='isDefault']:checked").val();
                var hiddenSegmentCode = $("#hiddenSegmnentCode").val();
                var loanTypeCode = $("#loanTypeCode").val();
                var penaltyFormula = $("#penaltyFormula").val();
                var defaultPenaltyDays = $("#defaultPenaltyDays").val();

                if (name !== "" && interestRate !== "" && productType !== "" && hasInterest !== "" && controlAccount !== "" && hiddenSegmentCode !== "") {
                    var json = {"controlAccount": controlAccount, "name": name, "interestRate": interestRate, "productTypeCode": productType, "hasInterest": hasInterest, "companyId": companyId, "isTaxable": isTaxable, "hasPenalty": hasPenalty, "penalty": penalty, "loanTypeCode": loanTypeCode, "isDefault": isDefault, "loanDuration": loanDuration, "penaltyFormula": penaltyFormula, "defaultPenaltyDays": defaultPenaltyDays};

                    $.ajax({
                        url: $("#productForm").attr("action"),
                        data: JSON.stringify(json),
                        type: "POST",
                        contentType: "application/json; charset=utf-8",
                        dataType: 'json',
                        beforeSend: function(xhr) {
                            $("#image-loader").css("display", "block");

                            xhr.setRequestHeader("Accept", "application/json");
                            xhr.setRequestHeader("Content-Type", "application/json");
                        },
                        success: function(data) {

                            $("#image-loader").css("display", "none");
                            $('#productAccountProductId').val(data.id);

                            location.href = "<%=request.getContextPath()%>/views/productaccount/add/" + data.id;
                        }
                    });
                }
                else {
                    $("#errorMessageStatus").html("Operation failed. Please check the form.");
                    $("#errorMessage").css("display", "block");

                }
                event.preventDefault();
            });
            $('#productAccountForm').submit(function(event) {
                var productId = $("#productAccountProductId").val();
                var glAccountNumber = $("#glAccountNumber").val();
                var productAccountTypeCode = $("#productAccountTypeCode").val();



                var json = {"productId": productId, "glAccountNumber": glAccountNumber, "productAccountTypeCode": productAccountTypeCode};

                $.ajax({
                    url: $("#productAccountForm").attr("action"),
                    data: JSON.stringify(json),
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    dataType: 'json',
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader("Accept", "application/json");
                        xhr.setRequestHeader("Content-Type", "application/json");
                    },
                    success: function(data) {
                        var respContent = "";
                        $("#product-account-list tbody").html("");
                        var html = "";
                        var rowCount = 0;
                        $.each(data, function(index) {
                            rowCount += 1;
                            html += "<tr><td>" + rowCount + "</td>";
                            html += "<td>" + data[index].glAccountNumber + "</td>";
                            html += "<td>" + data[index].productAccountTypeCode + "</td>";
                            html += '<td class="table-action"><a id="deleteaccount-' + data[index].id + '" href="#" data-id="' + data[index].id + '"  data-toggle="tooltip" title="Delete" class="delete-row tooltips deleteaccount"><i class="fa fa-trash-o"></i></a> </td></tr>';

                        });
                        $("#product-account-list tbody").append(html);

                        $("#submitResponse").show();
                        $("#submitResponse").html("Operation successful");
                    }
                });

                event.preventDefault();
            });
            $("input[name='isTaxable']").change(function() {
                var selected = $(this).val();
                if (selected === '1') {
                    $('#dvTaxCode1').css("display", 'block');
                    $('#dvTaxCode2').css("display", 'block');
                    $('#dvTaxCode3').css("display", 'block');
                } else {
                    $('#dvTaxCode1').css("display", 'none');
                    $('#dvTaxCode2').css("display", 'none');
                    $('#dvTaxCode3').css("display", 'none');
                }
            });

            $("#productAccountTypeCode").change(function() {
                var option = $('option:selected', this).val();
                var isCtrl = 0;
                if (option === "CTR") {
                    isCtrl = 1;
                } else {
                    isCtrl = 0;
                }
                $.get("<%=request.getContextPath()%>/views/product/getaccounts", {"control": isCtrl}, function(result) {
                    $("#glAccountNumber option").remove();
                    var html = "";
                    $.each(result, function(index) {
                        // rowCount+=1;
                        $('#glAccountNumber')
                                .append($("<option></option>")
                                .attr("value", result[index].accountNo)
                                .data("control", result[index].controlAccount)
                                .text(result[index].name));

                    });

                });

            });
            $("#glAccountNumber").change(function() {
                var option = $('option:selected', this).data('control');

            });
            $("input[name='hasInterest']").change(function() {
                var selected = $(this).val();
                if (selected === '1') {
                    $('#interest-div').css("display", 'block');
                } else {
                    $('#interest-div').css("display", 'none');
                }
            });
            $("input[name='hasPenalty']").change(function() {
                var selected = $(this).val();
                if (selected === '1') {
                    $('#penalty-div').css("display", 'block');
                    $('#penalty-div').css("display", 'block');
                    $('#dayspenalty-div').css("display", 'block');
                } else {
                    $('#penalty-div').css("display", 'none');
                    $('#penalty-div').css("display", 'none');
                    $('#dayspenalty-div').css("display", 'none');
                }
            });

            jQuery("#addOperand").click(function() {
                var selected = jQuery("#cmbOperands").val();
                var formula = jQuery("#penaltyFormula").val();
                var dirtyFormula = formula + selected;
                jQuery("#penaltyFormula").val(dirtyFormula);
            });
            jQuery("#addOperator").click(function() {
                var selected = jQuery("#cmbOperators").val();
                var formula = jQuery("#penaltyFormula").val();
                var dirtyFormula = formula + selected;
                jQuery("#penaltyFormula").val(dirtyFormula);
            });
        })
    </script>