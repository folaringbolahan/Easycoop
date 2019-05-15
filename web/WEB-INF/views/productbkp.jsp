<%@ include file="includes/header.jsp" %>  

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
        <div class="col-md-10">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
                <!-- Nav tabs -->
                <ul class="nav nav-tabs nav-danger">
                    <li class="active"><a href="#home6" data-toggle="tab"><strong>Add Product</strong></a></li>
                    <li><a href="#profile6" data-toggle="tab"><strong>Add Account(s) to Product</strong></a></li>

                </ul>

                <!-- Tab panes -->
                <div class="tab-content tab-content-danger mb30">
                    <div class="tab-pane active" id="home6">

                        <p>   <form:form method="POST" commandName="productForm" action="${pageContext.request.
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
                                               <label class="col-sm-4 control-label">Product Code<span class="asterisk">*</span></label>
                                               <div class="col-sm-4">

                                                   <form:input path="code" cssClass="form-control"  id="productCode" required="required" onblur="checkAvailability(this.value)" />
                                                   <span id="availability"></span>
                                               </div>
                                           </div><!-- form-group -->
                                           <div class="form-group">
                                               <label class="col-sm-4 control-label">Segment Code<span class="asterisk">*</span></label>
                                               <div class="col-sm-4">

                                                   <form:input path="segmentCode" cssClass="form-control"  id="segmentCode" required="required" />
                                               </div>
                                           </div><!-- form-group -->
                                           <div class="form-group">
                                               <label class="col-sm-4 control-label">Product Name <span class="asterisk">*</span></label>
                                               <div class="col-sm-4">

                                                   <form:input path="name"  cssClass="form-control" id="productName"  required="required" />
                                               </div>
                                           </div><!-- form-group -->

                                           <div class="form-group">
                                               <label class="col-sm-4 control-label">Product Type <span class="asterisk">*</span></label>
                                               <div class="col-sm-4">

                                                   <form:select  cssClass="width300" id="productTypeCode"  path="productTypeCode"  required="required"  >
                                                       <form:option value="" label="Choose Product" />
                                                       <form:options items="${productList}" itemValue="code" itemLabel="name" />
                                                   </form:select>

                                               </div>
                                           </div><!-- form-group -->


                                           <div class="form-group">
                                               <label class="col-sm-4 control-label">Attracts Interest?<span class="asterisk">*</span></label>
                                               <div class="col-sm-8">
                                                   <div class="ckbox ckbox-danger">
                                                       <div class="rdio rdio-danger">

                                                           <form:radiobutton id="yesInterest" value="1" path="hasInterest"  required="required" />
                                                           <label for="yesInterest">Yes</label>
                                                       </div><!-- rdio -->
                                                       <div class="rdio rdio-danger">
                                                           <form:radiobutton id="noInterest" value="${product.hasInterest}"  path="hasInterest"  required="required" />
                                                           <label for="noInterest">No</label>
                                                       </div><!-- rdio -->

                                                   </div>
                                               </div>
                                           </div><!-- form-group -->
                                           <div class="form-group" id="interest-div">
                                               <label class="col-sm-4 control-label">Interest Amount</label>
                                               <div class="col-sm-2">
                                                   <form:input path="interestRate" cssClass="form-control"  id="interestRate" />
                                               </div>
                                               <div>
                                                   <form:hidden value="${companyId}" path="companyId" />
                                                   <div id="image-loader" style="display:none">
                                                          <img src="${resourceUrl}/images/loaders/loader19.gif" alt="" /> 
                                                   </div>
                                               </div>
                                           </div><!-- form-group -->  
                                           <div class="form-group">
                                               <label class="col-sm-4 control-label">Attracts Penalty?<span class="asterisk">*</span></label>
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
                                           <!-- form-group -->
                                           <div class="form-group" id="penalty-div">
                                               <label class="col-sm-4 control-label">Penalty</label>
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
                                                           <form:radiobutton id="noTax" value="${product.isTaxable}"  path="isTaxable"  required="required" />
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
                                        <label class="col-sm-4 control-label">Gl Account Type</label>
                                        <div class="col-sm-4">
                                            <select  cssClass="width300" id="productAccountTypeCode"  name="productAccountTypeCode"  required="required"  >
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
                                            <label class="col-sm-4 control-label">Assign Account</label>
                                            <div class="col-sm-4">
                                                <select  cssClass="width300" id="glAccountNumber"  path="glAccountNumber"  required="required"  >
                                                    <option value="">Choose Account</option>
                                                    <c:forEach  var="account" items="${accounts}" varStatus="status" >

                                                        <option data-control="${account.controlAccount}" value="${account.accountNumber}">${account.name}</option>
                                                    </c:forEach>
                                                </select>
                                                <input type="hidden" name="productid" id="productAccountProductId" />
                                                <div class="mb30"></div>
                                            </div>
                                        </div><!-- form-group -->


                                        <div >
                                            <button class="btn btn-danger mr5">Add</button>
                                            <button type="reset" class="btn btn-default">Reset</button>
                                        </div>
                                    </div><!-- form-group -->
                                </form>
                            </div>

                        </div>
                        <div class="row">
                            <div class="col-md-10">
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

                                        <tr>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>

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
    $(document).ready(function() {
        jQuery('#datepicker').datepicker();
        $('#interest-div').css("display", 'none');
        $('#penalty-div').css("display", 'none');
        $('#dvTaxCode1').css("display", 'none');
        $('#dvTaxCode2').css("display", 'none');
        $('#dvTaxCode3').css("display", 'none');
        jQuery('select').select2({
            minimumResultsForSearch: -1
        });
        jQuery('#productAddStatus').css('display', 'none');
        jQuery("#productForm").validate({
            highlight: function(element) {
                jQuery(element).closest('.form-group').removeClass('has-success').addClass('has-error');
            },
            success: function(element) {
                jQuery(element).closest('.form-group').removeClass('has-error');
            }
        });

        $('#productForm').submit(function(event) {

            var code = $("#productCode").val();
            var name = $("#productName").val();
            var interestRate = $("#interestRate").val();
            var penalty = $("#penalty").val();
            var productType = $("#productTypeCode").val();
            var segmentCode = $("#segmentCode").val();
            var companyId = $("#companyId").val();
            var hasInterest = $("input[name='hasInterest']").val();
            var hasPenalty = $("input[name='hasPenalty']").val();
            var isTaxable = $("input[name='isTaxable']").val();
            var taxCode1 = $("#taxCode1").val();
            var taxCode2 = $("#taxCode2").val();
            var taxCode3 = $("#taxCode3").val();


            if (code !== "" && name !== "" && interestRate !== "" && productType !== "" && segmentCode !== "" && hasInterest !== "") {
                var json = {"code": code, "name": name, "interestRate": interestRate, "productTypeCode": productType, "segmentCode": segmentCode, "hasInterest": hasInterest, "companyId": companyId, "isTaxable": isTaxable, "taxCode1": taxCode1, "taxCode2": taxCode2, "taxCode3": taxCode3,"hasPenalty":hasPenalty,"penalty":penalty};

                $.ajax({
                    url: $("#productForm").attr("action"),
                    data: JSON.stringify(json),
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    dataType: 'json',
                    beforeSend: function(xhr) {
                    $("#image-loader").css("display","block");    
                    
                        xhr.setRequestHeader("Accept", "application/json");
                        xhr.setRequestHeader("Content-Type", "application/json");
                    },
                    success: function(data) {
                        //jQuery('#productAddStatus').css('display','block');
                         $("#image-loader").css("display","none");   
                        $('#productAccountProductId').val(data.id);
                        $("#status-text").html("You have successfully created <b>" + data.name + "</b>. You should now add General Ledger accounts to this product.");
                        $('#statusMoal').modal('show');

                    }
                });
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
                    $.each(data, function(index) {
                        // alert(data[index].glAccountNumber);
                        html += "<tr><td>" + data[index].id + "</td>";
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
        
          $("#productAccountTypeCode").change(function(){
            var option = $('option:selected', this).val();
            var isCtrl = 0;
            if(option==="CTR"){
                isCtrl = 1;
            }else{
                isCtrl = 0;
            }
             $.get("<%=request.getContextPath()%>/views/product/getaccounts", {"control": isCtrl}, function(result) {
                $("#glAccountNumber option").remove();
                var html = "";
           $.each(result, function(index) {
               $('#glAccountNumber')
         .append($("<option></option>")
         .attr("value",result[index].accountNumber)
         .data("control",result[index].controlAccount)
         .text(result[index].name)); 
          
           });

        });
            
        });
        $("#glAccountNumber").change(function(){
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
            } else {
                $('#penalty-div').css("display", 'none');
            }
        });
    })
</script>