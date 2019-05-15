<%@ include file="includes/header.jsp" %>  

<div class="media-body">
    <!-- changes starts -->
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="#">Home</a></li>
            <li>Product Management</li>
        </ul>
        <h4>Edit Product</h4>
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
                    <li class="active"><a href="#home6" data-toggle="tab"><strong>Add Product</strong></a></li>
                    <li><a href="#profile6" data-toggle="tab"><strong>Add Account(s) to Product</strong></a></li>

                </ul>

                <!-- Tab panes -->
                <div class="tab-content tab-content-danger mb30">
                    <div class="tab-pane active" id="home6">

                        <p>   <form:form method="POST" commandName="productForm" action="${pageContext.request.
                                                                                           contextPath}/views/product/editproduct" id="productForm">
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

                                           <div id="submitResponse" class="alert alert-success" style="display:none" >
                                               <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                               <span id="successMessageStatus">Operation successful! </span>
                                           </div>
                                           <div class="errorForm"></div>
                                           <div class="form-group">
                                               <label class="col-sm-4 control-label">Product Code<span class="asterisk">*</span></label>
                                               <div class="col-sm-4">

                                                   <form:input path="code" cssClass="form-control"  id="productCode" required="required" disabled="disabled" />
                                                   <span id="availability"></span>
                                               </div>
                                           </div><!-- form-group -->
                                           <div class="form-group">
                                               <label class="col-sm-4 control-label">Segment Code<span class="asterisk">*</span></label>
                                               <div class="col-sm-4">

                                                   <form:input path="segmentCode" cssClass="form-control"  id="segmentCode" required="required" disbaled="disbaled"  />
                                               </div>
                                           </div><!-- form-group -->
                                           <div class="form-group">
                                               <label data-toggle="tooltip" title="Enter product name for identification" class="col-sm-4 tooltips control-label">Product Name <span class="asterisk">*</span></label>
                                               <div class="col-sm-4">

                                                   <form:input path="name"   cssClass="form-control" id="productName"  required="required" />
                                               </div>
                                           </div>    <div class="form-group">
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
                                                   <form:select cssClass="width300" id="loanTypeCode"  path="loanTypeCode"   >
                                                       <form:option value="" label="Choose Interest Type" />
                                                       <form:options items="${interestTypes}" itemValue="typeCode" itemLabel="typeName" />
                                                   </form:select>

                                               </div>
                                           </div><!-- form-group -->  

                                           <div class="form-group" id="loadDurationContainer">
                                               <label data-toggle="tooltip" title="Type in the duration of the loan(months) " class="col-sm-4 tooltips control-label">Enter Loan Duration(months)<span class="asterisk">*</span></label>
                                               <div class="col-sm-4">
                                                   <form:input path="loanDuration" cssClass="form-control" id="loanDuration"  />

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

                                                   <form:input path="penaltyFormula"   cssClass="form-control" id="penaltyFormula"   />
                                               </div>
                                           </div><!-- form-group -->
                                           <div id="fade"></div>
                                           <div id="image-loader" style="display:none">
                                               <img src="${resourceUrl}/images/loaders/loader19.gif" alt="" /> 
                                           </div>
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
                                           </div>
                                           <div class="form-group">
                                               <label data-toggle="tooltip" title="Does this product attract any interest? Select Yes if it does" class="col-sm-4 tooltips control-label">Attracts Interest?<span class="asterisk">*</span></label>
                                               <div class="col-sm-8">
                                                   <div class="ckbox ckbox-danger">
                                                       <div class="rdio rdio-danger">


                                                           <form:radiobutton id="yesInterest" value="1" path="hasInterest"  required="required"  />

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
                                                           <form:radiobutton id="noPenalty" value="0"  path="hasPenalty"  required="required" />
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
                                               <label class="col-sm-4 control-label">Attracts Tax?<span class="asterisk">*</span></label>
                                               <div class="col-sm-8">
                                                   <div class="ckbox ckbox-danger">
                                                       <div class="rdio rdio-danger">

                                                           <form:radiobutton id="yesTax" value="1" path="isTaxable"  required="required" />
                                                           <label for="yesTax">Yes</label>
                                                       </div><!-- rdio -->
                                                       <div class="rdio rdio-danger">
                                                           <form:radiobutton id="noTax" value="0"  path="isTaxable"  required="required" />
                                                           <label for="noTax">No</label>
                                                       </div><!-- rdio -->

                                                   </div>
                                               </div>
                                           </div><!-- form-group -->
                                           <div class="form-group" id="dvTaxCode1">
                                               <label class="col-sm-4 control-label">Tax Code 1</label>
                                               <div class="col-sm-4">

                                                   <form:input path="taxCode1" cssClass="form-control"  id="taxCode1" />
                                               </div>
                                           </div><!-- form-group -->
                                           <div class="form-group" id="dvTaxCode2">
                                               <label class="col-sm-4 control-label">Tax Code 2</label>
                                               <div class="col-sm-4">

                                                   <form:input path="taxCode2" cssClass="form-control"  id="taxCode2"  />
                                               </div>
                                           </div><!-- form-group -->
                                           <div class="form-group" id="dvTaxCode3">
                                               <label class="col-sm-4 control-label">Tax Code 3</label>
                                               <div class="col-sm-4">

                                                   <form:input path="taxCode3" cssClass="form-control"  id="taxCode3"  />
                                               </div>
                                           </div><!-- form-group -->
                                       </div><!-- panel-body -->
                                       <div class="panel-footer">
                                           <button class="btn btn-danger mr5">Submit</button>
                                           <button type="reset" class="btn btn-default">Reset</button>
                                       </div><!-- panel-footer -->
                                   </div><!-- panel-default -->
                        </form:form></p>
                    </div><!-- tab-pane -->

                    <div class="tab-pane" id="profile6">

                        <div class="row">
                            <div class="col-md-6">
                                <form method="POST" action="${pageContext.request.
                                                              contextPath}/views/productaccount/addaccount" id="productAccountForm">


                                    <div class="form-group">
                                        <label class="col-sm-8 control-label">Gl Account Type</label>
                                        <div class="col-sm-4">
                                            <select  class="width300" id="productAccountTypeCode"  name="productAccountTypeCode"  required="required"  >
                                                <option value="">Choose Account Type</option>
                                                <c:forEach  var="accountType" items="${accountTypes}" varStatus="status" >

                                                    <option value="${accountType.code}">${accountType.description}</option>
                                                </c:forEach>
                                            </select>
                                            <div class="mb30"></div>
                                        </div>
                                    </div><!-- form-group -->
                                    <div class="form-group">

                                        <!-- form-group -->

                                        <div class="form-group">
                                            <label class="col-sm-8 control-label">Assign Account</label>
                                            <div class="col-sm-4">
                                                <select  class="width300" id="glAccountNumber"  path="glAccountNumber"  required="required"  >
                                                    <option value="">Choose Account</option>
                                                    <c:forEach  var="account" items="${accounts}" varStatus="status" >

                                                        <option data-control="${account.controlAccount}" value="${account.accountNo}">${account.name}</option>
                                                    </c:forEach>
                                                </select>
                                                <input type="hidden" name="productid" id="productAccountProductId" />
                                                <div class="mb30"></div>
                                            </div>
                                        </div><!-- form-group -->


                                        <div >
                                            <button class="btn btn-danger mr5" id="btnAddProduct">Add</button>
                                            <button type="reset" class="btn btn-default">Reset</button>
                                        </div>
                                    </div><!-- form-group -->
                                </form>
                            </div>

                        </div>
                        <div class="row">
                            <div class="col-md-10">
                                <div id="accountResponse" class="alert alert-success" style="display:none" >
                                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                    <span id="accountSuccessMessageStatus">Operation successful! </span>
                                </div>
                                <table id="product-account-list" class="table table-danger mb30">
                                    <thead class="">
                                        <tr>

                                            <th>Id</th>
                                            <th>Gl Account</th>
                                            <th>Account Type</th>
                                            <th></th>
                                        </tr>
                                    </thead>

                                    <tbody>

                                        <c:forEach  var="productAccount" items="${productAccounts}" varStatus="status" >
                                            <tr>

                                                <td>${status.index + 1}</td>
                                                <td >${productAccount.glAccountNumber}</td>
                                                <td >${productAccount.description}</td>

                                                <td class="table-action">
                                                    <a id="deleteaccount-${productAccount.id}" href="#" data-id="${productAccount.id}"  data-toggle="tooltip" title="Delete" class="delete-row tooltips deleteaccount" onclick="deleteAccount(${productAccount.id})"><i class="fa fa-trash-o"></i></a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div><!-- tab-pane -->


                </div><!-- tab-content -->
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
        $.get("<%=request.getContextPath()%>/views/product/getcode", {"code": code}, function (result) {

            if (result === 'ok') {
                $("#availability").html('<i class="fa fa-check"></i>');
                $('#productCode').css("border-color", "#999");
            } else {
                $("#availability").html('<i class="fa fa-warning">Not available</i>');
                $('#productCode').css("border-color", "#FF0000");
            }

        });
    }

    function deleteAccount(id) {
        var c = confirm("Continue delete?");
        if (c)
            $.get("<%=request.getContextPath()%>/views/productaccount/delete", {"id": id}, function (data) {

                if (data === "ok") {
                    jQuery("#deleteaccount-" + id).closest('tr').fadeOut(function () {
                        jQuery(this).remove();
                    });
                }

            });

    }
    $(document).ready(function () {

        jQuery("#productCode").attr("disbaled", "disabled");
        jQuery("#segmentCode").attr("disbaled", "disabled");
        var productTypeCode = "${product.productTypeCode}";
        var hasInterest = ${product.hasInterest};
        var hasPenalty = ${product.hasPenalty};
        var isTaxable = ${product.isTaxable};
        var isDefault = ${product.isDefault};

        if (hasInterest === 1) {
            $('#yesInterest').prop('checked', true);
            $('#noInterest').prop('checked', false);
        } else {
            $('#noInterest').prop('checked', true);
            $('#yesInterest').prop('checked', false);
        }
        if (hasPenalty === 1) {
            $('#yesPenalty').prop('checked', true);
            $('#noPenalty').prop('checked', false);
        } else {
            $('#noPenalty').prop('checked', true);
            $('#yesPenalty').prop('checked', false);
        }
        if (isTaxable === 1) {
            $('#yesTax').prop('checked', true);
            $('#noTax').prop('checked', false);
        } else {
            $('#noTax').prop('checked', true);
            $('#yesTax').prop('checked', false);
        }
        if (isDefault === 1) {
            $('#yesIsDefault').prop('checked', true);
            $('#noIsDefault').prop('checked', false);
        } else {
            $('#noIsDefault').prop('checked', true);
            $('#yesIsDefault').prop('checked', false);
        }
        if (productTypeCode === "L" || productTypeCode === "P") {

            $('#dayspenalty-div').css("display", 'block');
            $("#operandsContainer").css("display", "block");
            $("#formularContainer").css("display", "block");

            jQuery("#interestLoanType").css("display", "block");
            jQuery("#loadDurationContainer").css("display", "block");
        } else {

            $('#dayspenalty-div').css("display", 'none');
            $("#operandsContainer").css("display", "none");
            $("#formularContainer").css("display", "none");
            jQuery("#interestLoanType").css("display", "none");
            jQuery("#loadDurationContainer").css("display", "none");


        }
        $('#dvTaxCode1').css("display", 'none');
        $('#dvTaxCode2').css("display", 'none');
        $('#dvTaxCode3').css("display", 'none');

        $("#submitResponse").hide();
        // Delete row in a table


        jQuery("#productTypeCode").change(function () {
            var productValue = jQuery(this).val();
            if (productValue === "L" || productValue === "P") {
                jQuery("#interestLoanType").css("display", "block");
                jQuery("#loadDurationContainer").css("display", "block");
                $("#operandsContainer").css("display", "block");
                $("#formularContainer").css("display", "block");
                $.get("<%=request.getContextPath()%>/views/product/loantypes", {"type": productValue}, function (records) {
                    $('#loanTypeCode').children('option:not(:first)').remove();
                    $.each(records, function (i, item) {

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
                    $.get("<%=request.getContextPath()%>/views/product/checkproduct", {"code": productValue}, function (result) {

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
        $("#productAccountTypeCode").change(function () {
            var option = $('option:selected', this).val();
            var isCtrl = 0;
            if (option === "CTR") {
                isCtrl = 1;
            } else {
                isCtrl = 0;
            }
            $.get("<%=request.getContextPath()%>/views/product/getaccounts", {"control": isCtrl}, function (result) {
                $("#glAccountNumber option").remove();
                var html = "";
                $.each(result, function (index) {
                    $('#glAccountNumber')
                            .append($("<option></option>")
                                    .attr("value", result[index].accountNo)
                                    .data("control", result[index].controlAccount)
                                    .text(result[index].name));

                });

            });

        });
        jQuery('.deleteaccount').click(function () {

            //alert("Click click");
            var objId = $(this).attr("id");
            var id = $("#" + objId).data('id');

            var c = confirm("Continue delete?");
            if (c)
                jQuery(this).closest('tr').fadeOut(function () {


                    $.getJSON("<%=request.getContextPath()%>/views/productaccount/delete", {"id": id}, function (data) {

                        jQuery(this).remove();

                    });


                    //jQuery(this).remove();
                });
            return false;
        });

        jQuery('select').select2({
            minimumResultsForSearch: -1
        });
        jQuery('#products-list').DataTable({
            responsive: true
        });
        jQuery('#products-list .group-checkable').change(function () {
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

        $('#productAccountForm').submit(function (event) {
            var productId = ${product.id};
            var glAccountNumber = $("#glAccountNumber").val();
            var productAccountTypeCode = $("#productAccountTypeCode").val();


            var json = {"productId": productId, "glAccountNumber": glAccountNumber, "productAccountTypeCode": productAccountTypeCode};

            $.ajax({
                url: $("#productAccountForm").attr("action"),
                data: JSON.stringify(json),
                type: "POST",
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("Accept", "application/json");
                    xhr.setRequestHeader("Content-Type", "application/json");
                    $("#btnAddProduct").attr("disabled","disabled");
                },
                success: function (data) {
                    var respContent = "";
                    $("#product-account-list tbody").html("");
                    var html = "";
                    var rowCount = 0;
                    $.each(data, function (index) {
                        rowCount += 1;
                        html += "<tr><td>" + rowCount + "</td>";
                        html += "<td>" + data[index].glAccountNumber + "</td>";
                        html += "<td>" + data[index].description + "</td>";
                        html += '<td class="table-action"><a id="deleteaccount-' + data[index].id + '" href="#" data-id="' + data[index].id + '"  data-toggle="tooltip" title="Delete" class="delete-row tooltips deleteaccount"><i class="fa fa-trash-o"></i></a> </td></tr>';

                    });
                    $("#product-account-list tbody").append(html);

                    $("#accountResponse").css("display", "block");
                    $("#accountSuccessMessageStatus").html("Operation successful");

                    setTimeout(function () {
                        jQuery("#accountResponse").css("display", "none");
                        $("#btnAddProduct").removeAttr("disabled");
                    }, 3000);
                }
            });

            event.preventDefault();
        });

        $("input[name='hasInterest']").change(function () {
            var selected = $(this).val();
            if (selected === '1') {
                $('#interest-div').css("display", 'block');
            } else {
                $('#interest-div').css("display", 'none');
            }
        });
        $("input[name='hasPenalty']").change(function () {
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

        jQuery("#addOperand").click(function () {
            var selected = jQuery("#cmbOperands").val();
            var formula = jQuery("#penaltyFormula").val();
            var dirtyFormula = formula + selected;
            jQuery("#penaltyFormula").val(dirtyFormula);
        });
        jQuery("#addOperator").click(function () {
            var selected = jQuery("#cmbOperators").val();
            var formula = jQuery("#penaltyFormula").val();
            var dirtyFormula = formula + selected;
            jQuery("#penaltyFormula").val(dirtyFormula);
        });
        $('#productForm').submit(function (event) {
            var productId = ${product.id};
            var code = $("#productCode").val();
            var name = $("#productName").val();
            var interestRate = $("#interestRate").val();
            var productType = $("#productTypeCode").val();
            var loanDuration = $("#loanDuration").val();
            var segmentCode = $("#segmentCode").val();
            var companyId = $("#companyId").val();
            var hasInterest = $("input[name='hasInterest']:checked").val();
            var isDefault = $("input[name='isDefault']:checked").val();
            var isTaxable = $("input[name='isTaxable']:checked").val();
            var taxCode1 = $("#taxCode1").val();
            var taxCode2 = $("#taxCode2").val();
            var taxCode3 = $("#taxCode3").val();
            var penalty = $("#penalty").val();
            var hasPenalty = $("input[name='hasPenalty']:checked").val();
            var loanTypeCode = $("#loanTypeCode").val();
            var penaltyFormula = $("#penaltyFormula").val();
            var defaultPenaltyDays = $("#defaultPenaltyDays").val();

            var json = {"id": productId, "code": code, "name": name, "interestRate": interestRate, "productTypeCode": productType, "hasInterest": hasInterest, "companyId": companyId, "segmentCode": segmentCode, "isTaxable": isTaxable, "taxCode1": taxCode1, "taxCode2": taxCode2, "taxCode3": taxCode3, "hasPenalty": hasPenalty, "penalty": penalty, "loanTypeCode": loanTypeCode, "isDefault": isDefault, "loanDuration": loanDuration, "penaltyFormula": penaltyFormula, "defaultPenaltyDays": defaultPenaltyDays};

            $.ajax({
                url: $("#productForm").attr("action"),
                data: JSON.stringify(json),
                type: "POST",
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("Accept", "application/json");
                    xhr.setRequestHeader("Content-Type", "application/json");
                },
                success: function (data) {
                    var respContent = "";

                    $("#submitResponse").css("display", "block");
                    $("#submitResponse").html("Operation successful");

                }
            });

            event.preventDefault();
        });

    });
</script>