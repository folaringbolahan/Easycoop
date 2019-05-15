<%@ include file="includes/header.jsp" %>  
<script src="<%=request.getContextPath()%>/js/utilityfc.js"></script>
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
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="#">Home</a></li>
            <li>Product Management</li>
        </ul>
        <h4>Manage</h4>
    </div>
    <%@include file="includes/topright.jsp" %>
</div><!-- media -->
</div><!-- pageheader -->


<div class="contentpanel">
    <div class="row">
        <!--  <div class="col-md-8">-->  
        <!-- CONTENT GOES HERE -->  
        <!--   <div class="col-md-10">-->
        <div class="panel panel-primary-head">
            <div id="validateSuccessResponse" class="alert alert-success" style="display:none">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <span id="validateMessageStatus">Operation successful! Necessary accounts have been attached to this product.</span>
            </div>
            <div class="alert alert-danger"  id="validateErrorResponse" style="display:none;">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true" >&times;</button>
                <span id="validateErrorMessageStatus"  ></span>
            </div>
            <p>  <button class="btn btn-danger mr5" id="addNewProduct">Add New Product</button></p>
            <table id="products-list" class="table table-striped table-bordered responsive">
                <thead class="">
                    <tr>
                    
                        <th> Id</th>
                        <th>Name</th>
                        <th>Code</th>
                        <th>Status</th>

                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach  var="product" items="${products}" varStatus="status" >
                        <tr>
                           
                            <td data-id="${product.id}" data-code="${product.code}" data-name="${product.name}" data-companyid="${product.companyId}" data-hasinterest="${product.hasInterest}" data-interest="${product.interestRate}">${status.index + 1}</td>
                            <td data-id="${product.id}" data-code="${product.code}" data-name="${product.name}" data-companyid="${product.companyId}" data-hasinterest="${product.hasInterest}" data-interest="${product.interestRate}">${product.name}</td>
                            <td data-id="${product.id}" data-code="${product.code}" data-name="${product.name}" data-companyid="${product.companyId}" data-hasinterest="${product.hasInterest}" data-interest="${product.interestRate}">${product.code}</td>
                            <c:if test="${product.isActive==0}">
                            <td data-id="${product.id}" data-code="${product.code}" data-name="${product.name}" data-companyid="${product.companyId}" data-hasinterest="${product.hasInterest}" data-interest="${product.interestRate}" data-segment="${product.segmentCode}">Inactive</td>
                            </c:if>
                            <c:if test="${product.isActive==1}">
                            <td data-id="${product.id}" data-code="${product.code}" data-name="${product.name}" data-companyid="${product.companyId}" data-hasinterest="${product.hasInterest}" data-interest="${product.interestRate}" data-segment="${product.segmentCode}">Active</td>
                            </c:if>
                            <td class="table-action">
                                <a href="<%=request.getContextPath()%>/views/product/add/${product.id}" id="edit-${product.id}" data-id="${product.id}" data-code="${product.code}" data-name="${product.name}" data-companyid="${product.companyId}" data-hasinterest="${product.hasInterest}" data-interest="${product.interestRate}"  data-segment="${product.segmentCode}" data-taxable="${product.isTaxable}" data-taxcode1="${product.taxCode1}" data-taxcode2="${product.taxCode2}" data-taxcode3="${product.taxCode3}" data-producttype="${product.productTypeCode}" data-hasPenalty="${product.hasPenalty}" data-penalty="${product.penalty}" data-default="${product.isDefault}" data-duration="${product.loanDuration}" data-toggle="tooltip" title="Edit" class="tooltips edit"><i class="fa fa-pencil"></i></a>
                                <a id="delete-${product.id}" data-id="${product.id}" onclick='return deleteProduct("delete-${product.id}");' data-toggle="tooltip" title="Delete" class="delete-row tooltips delete"><i class="fa fa-trash-o"></i></a>

                                <c:if test="${product.isActive==0}">
                                    <button data-productid="${product.id}" data-producttypecode="${product.productTypeCode}" class="btn btn-danger btn-metro" id="validate-${status.index + 1}" onclick='return validate("validate-${status.index + 1}");' >Validate</button>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div><!-- col-md-6 -->
</div>
</div>


        <!-- modal ends -->
    </div><!-- contentpanel -->

    <%@ include file="includes/footer.jsp" %>  
    <script>
                                    function loadEditModal(objId) {
                                        var id = $("#" + objId).data('id');
                                        var code = $("#" + objId).data('code');
                                        var name = $("#" + objId).data('name');
                                        var producttype = $("#" + objId).data('producttype');
                                        var interestRate = $("#" + objId).data('interest');
                                        var penalty = $("#" + objId).data('penalty');
                                        var segmentCode = $("#" + objId).data('segment');
                                        var hasInterest = $("#" + objId).data('hasinterest');
                                        var hasPenalty = $("#" + objId).data('hasPenalty');
                                        var loanDuration = $("#" + objId).data('duration');
                                        var isDefault = $("#" + objId).data('default');
                                        var isTaxable = $("#" + objId).data('taxable');
                                        var taxCode1 = $("#" + objId).data('taxcode1');
                                        var taxCode2 = $("#" + objId).data('taxcode2');
                                        var taxCode3 = $("#" + objId).data('taxcode3');

                                        if (hasInterest === 1) {
                                            $('#yesInterest').prop('checked', true);
                                        } else {
                                            $('#noInterest').prop('checked', true);

                                        }
                                        if (hasPenalty === 1) {
                                            $('#yesPenalty').prop('checked', true);
                                        } else {
                                            $('#noPenalty').prop('checked', true);

                                        }
                                        if (isTaxable === 1) {
                                            $('#yesTax').prop('checked', true);
                                        } else {
                                            $('#noTax').prop('checked', true);

                                        }
                                        if (isDefault === 1) {
                                            $('#yesIsDefault').prop('checked', true);
                                        } else {
                                            $('#noIsDefault').prop('checked', true);

                                        }
                                        $.getJSON("<%=request.getContextPath()%>/views/productaccount/getaccounts", {"id": id}, function(data) {
                                            var html = "";
                                            if ($.isEmptyObject(data)) {

                                            } else {
                                                var rowCount = 0;
                                                $.each(data, function(index) {
                                                    rowCount += 1;
                                                    html += "<tr><td>" + rowCount + "</td>";
                                                    html += "<td>" + data[index].glAccountNumber + "</td>";
                                                    html += "<td>" + data[index].description + "</td>";
                                                    html += '<td class="table-action"><a id="deleteaccount-' + data[index].id + '"  href="#" data-id="' + data[index].id + '" onclick="deleteAccount(' + data[index].id + ')"  data-toggle="tooltip" title="Delete" class="tooltips deleteaccount"><i class="fa fa-trash-o"></i></a> </td></tr>';
                                                });
                                            }

                                            $("#product-account-list tbody").html("");
                                            $("#product-account-list tbody").append(html);


                                        });
                                        $('#editModal').modal('show');

                                        $('#editModal').on('hidden.bs.modal', function() {
                                            location.href = "<%=request.getContextPath()%>/views/product/list.htm";

                                        })

                                        jQuery("select#productTypeCode option[selected]").removeAttr("selected");
                                        $("#productTypeCode option[value='" + producttype + "']").attr('selected', 'selected');
                                        //$("#currencyId option[value='" + currencyId + "']").attr("selected", "selected");
                                        $("#productCode").val(code);
                                        $("#productName").val(name);
                                        $("#interestRate").val(interestRate);
                                        $("#penalty").val(penalty);
                                        $("#productId").val(id);
                                        $("#productAccountProductId").val(id);
                                        $("#segmentCode").val(segmentCode);

                                        $("#taxCode1").val(taxCode1);
                                        $("#taxCode2").val(taxCode2);
                                        $("#taxCode3").val(taxCode3);
                                        $("#loanDuration").val(loanDuration);
                                        if (producttype === "L") {
                                            
                                            jQuery("#loanTypeCode").css("display", "block");
                                            jQuery("#loadDurationContainer").css("display", "block");
                                        } else {
                                            jQuery("#interestLoanType").css("display", "none");
                                            jQuery("#loadDurationContainer").css("display", "block");
                                        }
                                    }
                                    function deleteProduct(objId) {
                                        var c = confirm("Continue delete?");
                                        var id = jQuery("#"+ objId).data("id");
                                       
                                        if (c)
                                            $.get("<%=request.getContextPath()%>/views/product/delete", {"id": id}, function(data) {
                                           // $.get("<%=request.getContextPath()%>/views/productaccount/delete", {"id": id}, function(data) {
                                                
                                                if (data === "ok") {
                                                    jQuery("#"+ id).closest('tr').fadeOut(function() {
                                                        jQuery(this).remove();
                                                    });
                                                }

                                            });

                                    }
                                    function validate(cid) {
                                        var productId = jQuery("#" + cid).data("productid");
                                        var productTypeCode = jQuery("#" + cid).data("producttypecode");
                                        var title = "";
                                        $.get("<%=request.getContextPath()%>/views/productaccount/validate", {"productId": productId, "productTypeCode": productTypeCode}, function(data) {

                                            if (data === "ok") {
                                                jQuery("#" + cid).css("display", "none");
                                                jQuery("#validateErrorResponse").css("display", "none");
                                                jQuery("#validateSuccessResponse").css("display", "block");
                                                
                                                setTimeout(function() {
                                                    jQuery("#validateSuccessResponse").css("display", "none");

                                                }, 3000);
                                            } else {
                                            var text = "Operation failed! Necessary accounts need to be added to this product";
                                               jQuery("#validateErrorMessageStatus").html(text+" "+data);
                                               jQuery("#validateErrorResponse").attr("data-toggle", "tooltip");

                                                jQuery("#validateErrorResponse").addClass("tooltips");
                                                jQuery("#validateErrorResponse").attr("title", data);
                                                jQuery("#validateErrorResponse").css("display", "block");
                                                jQuery("#validateErrorResponse").show();

                                            }
                                        });
                                    }
                                    $(document).ready(function() {
                                        $("#submitResponse").hide();
                                        // Delete row in a table
                                       
                                        // jQuery("#interestLoanType").css("display","none");   
                                        jQuery("#productTypeCode").change(function() {
                                            var productValue = jQuery(this).val();
                                            if (productValue === "L") {
                                                jQuery("#interestLoanType").css("display", "block");
                                                 jQuery("#loadDurationContainer").css("display", "block");
                                            } else {
                                                jQuery("#interestLoanType").css("display", "none");
                                                 jQuery("#loadDurationContainer").css("display", "block");
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
                                                    $('#glAccountNumber')
                                                            .append($("<option></option>")
                                                            .attr("value", result[index].accountNo)
                                                            .data("control", result[index].controlAccount)
                                                            .text(result[index].name));

                                                });

                                            });

                                        });
                                        jQuery('.deleteaccount').click(function() {

                                            //alert("Click click");
                                            var objId = $(this).attr("id");
                                            var id = $("#" + objId).data('id');

                                            var c = confirm("Continue delete?");
                                            if (c)
                                                jQuery(this).closest('tr').fadeOut(function() {


                                                    $.getJSON("<%=request.getContextPath()%>/views/productaccount/delete", {"id": id}, function(data) {

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
                                        jQuery('#products-list .group-checkable').change(function() {
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
                                        jQuery('#addNewProduct').click(function() {

                                            location.href = "<%=request.getContextPath()%>/views/product/add";
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
                                                        html += "<td>" + data[index].description + "</td>";
                                                        html += '<td class="table-action"><a id="deleteaccount-' + data[index].id + '" href="#" data-id="' + data[index].id + '"  data-toggle="tooltip" title="Delete" class="delete-row tooltips deleteaccount"><i class="fa fa-trash-o"></i></a> </td></tr>';

                                                    });
                                                    $("#product-account-list tbody").append(html);

                                                    $("#submitResponse").show();
                                                    $("#submitResponse").html("Operation successful");
                                                }
                                            });

                                            event.preventDefault();
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
                                        $('#productForm').submit(function(event) {
                                            var productId = $("#productId").val();
                                            var code = $("#productCode").val();
                                            var name = $("#productName").val();
                                            var interestRate = $("#interestRate").val();
                                            var productType = $("#productTypeCode").val();
                                            var loanDuration =  $("#loanDuration").val();
                                            var segmentCode = $("#segmentCode").val();
                                            var companyId = $("#companyId").val();
                                            var hasInterest = $("input[name='hasInterest']").val();
                                            var isDefault = $("input[name='isDefault']").val();
                                            var isTaxable = $("input[name='isTaxable']").val();
                                            var taxCode1 = $("#taxCode1").val();
                                            var taxCode2 = $("#taxCode2").val();
                                            var taxCode3 = $("#taxCode3").val();
                                            var penalty = $("#penalty").val();
                                            var hasPenalty = $("input[name='hasPenalty']").val();
                                            var loanTypeCode = $("#loanTypeCode").val();

                                            var json = {"id": productId, "code": code, "name": name, "interestRate": interestRate, "productTypeCode": productType, "hasInterest": hasInterest, "companyId": companyId, "segmentCode": segmentCode, "isTaxable": isTaxable, "taxCode1": taxCode1, "taxCode2": taxCode2, "taxCode3": taxCode3, "hasPenalty": hasPenalty, "penalty": penalty, "loanTypeCode": loanTypeCode, "isDefault": isDefault,"loanDuration":loanDuration};

                                            $.ajax({
                                                url: $("#productForm").attr("action"),
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
                                    });
    </script>