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
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="#">Home</a></li>
            <li>Product Management</li>
        </ul>
        <h4>Add Accounts to Product</h4>
    </div>
    <%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->

<div class="contentpanel">
    <div class="col-md-10">
        <div id="row">
            <div class="alert alert-info fade in nomargin">
                <button aria-hidden="true" data-dismiss="alert" class="close" type="button">&times;</button>
                <h4>Operation Successful!</h4>
                <p>You have successfully created <b>${product.name}</b>. You can add GL accounts to this product</p>
                <p>
                 <!--   <button class="btn btn-danger" type="button">Continue</button>
                    <button class="btn btn-white" type="button">Cancel</button>-->
                </p>
            </div>
        </div>
        <p>
            <form:form method="POST" commandName="productAccountForm" action="${pageContext.request.
                                                                                contextPath}/views/productaccount/addaccount" id="productAccountForm">
            <div class="form-group">
                <label class="col-sm-4 control-label">Product Code</label>
                <div class="col-sm-4">
                    ${product.code}
                </div>
            </div><!-- form-group -->
            <div class="form-group">
                <label class="col-sm-4 control-label">Product Name</label>
                <div class="col-sm-4">

                    ${product.name}
                    <form:hidden path="productId" value="${productId}" />


                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label tooltips" data-toggle="tooltip" title="Select the GL Account type to attach to this product">Gl Account Type<span class="asterisk">*</span></label>
                <div class="col-sm-4">
                    <form:select  cssClass="width300" id="productAccountTypeCode"  path="productAccountTypeCode"  required="required"  >
                        <form:option value="" label="Choose Account Type" />
                        <form:options items="${accountTypes}" itemValue="code" itemLabel="description" />
                    </form:select>
                    <div class="mb30"></div>
                </div>
            </div><!-- form-group -->
            <div class="form-group">

                <!-- form-group -->

                <div class="form-group">
                    <label class="col-sm-4 control-label tooltips" data-toggle="tooltip" title="Assign GL Account to the selected account type">Assign Account<span class="asterisk">*</span></label>
                    <div class="col-sm-4">
                        <form:select  cssClass="width300" id="glAccountNumber"  path="glAccountNumber"  required="required"  >
                            <form:option value="" label="Choose Account" />
                            <form:options items="${accounts}" itemValue="accountNo" itemLabel="name" />
                        </form:select>
                         
                        <div class="mb30"></div>
                    </div>
                </div><!-- form-group -->


                <div >
                    <button class="btn btn-danger mr5">Submit</button>
                    <button type="reset" class="btn btn-default">Reset</button>
                </div>
            </div><!-- form-group -->
        </form:form>
        </p>
    </div>
    <!--second row for table starts -->
    <div class=" col-md-10">
         <div id="image-loader" style="display:none">
                                <img src="${resourceUrl}/images/loaders/loader19.gif" alt="" /> 
                            </div>
        <table id="product-account-list" class="table table-striped table-bordered responsive">
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
    <!-- second row for table ends -->
    <!-- CONTENT GOES HERE -->    

</div><!-- contentpanel -->
<%@ include file="includes/footer.jsp" %>  
<script>

                                function format(item) {
                                    return '<i class="fa ' + ((item.element[0].getAttribute('rel') === undefined) ? "" : item.element[0].getAttribute('rel')) + ' mr10"></i>' + item.text;
                                }
                                function deleteAccount(id) {
                                    var c = confirm("Continue delete?");
                                    if (c)
                                        $.get("<%=request.getContextPath()%>/views/productaccount/delete", {"id": id}, function(data) {
                                            
                                            if(data==="ok"){
                                            jQuery("#deleteaccount-" + id).closest('tr').fadeOut(function() {
                                                jQuery(this).remove();
                                            });
                                            }

                                        });

                                }
                                $(document).ready(function() {
                                    jQuery('#glAccountNumber').select2({
                                        formatResult: format,
                                        formatSelection: format,
                                        escapeMarkup: function(m) {
                                            return m;
                                        }
                                    });
                                    jQuery('#productAccountTypeCode').select2({
                                        minimumResultsForSearch: -1
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
                                    $("#glAccountNumber").change(function() {
                                        var option = $('option:selected', this).data('control');

                                    });
                                    jQuery('#product-account-list').DataTable({
                                        responsive: true
                                    });
                                    $('#productAccountForm').submit(function(event) {
                                        var productId = $("#productId").val();
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
                                                   $("#image-loader").css("display", "block");
                                            },
                                            success: function(data) {
                                                var respContent = "";
                                                $("#product-account-list tbody").html("");
                                                var html = "";
                                                var rowCount = 0;
                                                $.each(data, function(index) {
                                                    rowCount += 1;
                                                    html += "<tr><td>" +rowCount + "</td>";
                                                    html += "<td>" + data[index].glAccountNumber + "</td>";
                                                    html += "<td>" + data[index].description + "</td>";
                                                    html += '<td class="table-action"><a id="deleteaccount-' + data[index].id + '" href="#" data-id="' + data[index].id + '"  data-toggle="tooltip" title="Delete" class="delete-row tooltips deleteaccount" onclick="deleteAccount(' + data[index].id + ')"><i class="fa fa-trash-o"></i></a> </td></tr>';

                                                });
                                                $("#product-account-list tbody").append(html);

                                                $("#submitResponse").show();
                                                $("#submitResponse").html("Operation successful");
                                                   $("#image-loader").css("display", "none");
                                            }
                                        });

                                        event.preventDefault();
                                    });
                                })
</script>