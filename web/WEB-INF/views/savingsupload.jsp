<%@ include file="includes/header.jsp" %>  
<link href="<%=request.getContextPath()%>/resources/css/dropzone.css" rel="stylesheet" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.fileupload.css">
<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="index.htm">Home</a></li>
            <li>Transaction Upload</li>
        </ul>
        <h4>Bulk Transaction Upload</h4>
    </div>
    <%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->




<div class="contentpanel">

    <div class="row">
        <div class="col-md-6">
            <!-- CONTENT GOES HERE -->          
            <!-- form starts -->
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="panel-btns">
                    </div><!-- panel-btns -->
                    <h5 class="panel-title">BULK SAVINGS UPLOAD</h5>
                    <p>Use the form below to upload savings/contribution in bulk</p>
                </div>
                <div class="panel-body">                   

                    <form action="${pageContext.request.
                                    contextPath}/views/savings/uploads" enctype="multipart/form-data" id="savingsForm" method="post">
                        <div class="form-group">
                            <label class="col-sm-4 control-label tooltips" data-toggle="tooltip" title="Select the product to upload transactions for">Product <span class="asterisk">*</span></label>
                            <div class="col-sm-4">
                                <select  cssClass="width300" id="productId"  name="productId"  required="required"  >
                                    <option value="0">Choose Product</option>>
                                    <c:forEach  var="product" items="${products}" varStatus="status" >

                                        <option value="${product.id}">${product.name}</option>
                                    </c:forEach>
                                </select>

                            </div>
                        </div><!-- form-group -->
                        <!-- form-group -->
                        <div class="form-group" >
                            <label class="col-sm-4 control-label tooltips" data-toggle="tooltip" title="Enter the total number of records in the file to upload">No. Of Records</label>
                            <div class="col-sm-3">
                                <input name="noOfRecords" class="form-control"  id="noOfRecords" required="required" />
                            </div>
                            <div>

                            </div>
                        </div><!-- form-group --> 
                        <!-- form-group -->
                        <div class="form-group" >
                            <label class="col-sm-4 control-label tooltips" data-toggle="tooltip" title="Enter the sum of amount in the file to upload">Total Sum(Amount)</label>
                            <div class="col-sm-4">
                                <input name="totalSum" class="form-control"  id="totalSum" required="required" />
                            </div>
                            <div>

                            </div>
                        </div><!-- form-group --> 
                        <div class="form-group">
                            <label class="col-sm-4 control-label tooltips" data-toggle="tooltip" title="Browse the file to upload">Select file <span class="asterisk">*</span></label>

                            <span class="btn btn-success fileinput-button">
                                <i class="glyphicon glyphicon-plus"></i>
                                <span>Add files...</span>
                                <!-- The file input field used as target for the file upload widget -->
                                <input id="fileupload" type="file" name="files[]" >
                            </span>
                            <div id="files" class="files">
                                <br/>
                                <table id="uploaded-files" class="table responsive">
                                    <tr>
                                        <th>File Name</th>
                                        <th>File Size</th>
                                        <th>File Type</th>

                                    </tr>
                                </table>
                            </div>
                        </div><!-- form-group -->
                        <div class="form-group">
                            <label class="col-sm-4 control-label"></label>
                            <div class="col-sm-4">

                                <button type="submit" class="btn btn-danger mr5" id="uploadBtn">Submit</button>   

                            </div>
                        </div><!-- form-group -->
                        <!-- The fileinput-button span is used to style the file input field as button -->


                    </form
                    <div id="progress" class="progress">
                        <div class="progress-bar progress-bar-success bar" style="width: 0%;"></div>
                    </div>

                </div><!-- panel-body -->
            </div><!-- panel -->
            <!-- form ends --> 




            <!-- new panel for guarantor start -->  
            <div class="col-md-6">
                <div class="panel panel-default"> 
                    <div class="panel-heading">
                        <div class="panel-btns">
                        </div><!-- panel-btns -->
                        <h5 class="panel-title">TEMPLATE FOR BULK SAVINGS/CONTRIBUTION UPLOAD</h5>
                        <p>Download this template for bulk savings/contribution upload. </p>
                    </div><!-- panel-heading -->
                    <div class="panel-body ">  



                        <div class="col-md-12">
                            <div class="table-responsive">
                                <table class="table table-danger table-bordered table-hover mb30">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>File Name</th>
                                            <th>Description</th>
                                            <th>Download</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>1</td>
                                            <td>Savings Template</td>
                                            <td>Contribution/Savings</td>
                                            <td><a href="downloadsavingstmpl.htm?downFile=Savings_Contribution_Templ.xls">Download</a></td>
                                        </tr>

                                    </tbody>
                                </table>
                            </div><!-- table-responsive -->
                        </div>




                    </div>      
                </div>
            </div>   
            <!-- new for guarantor panel end -->










        </div>
        <!-- End of panel-body -->
    </div>

    <div class=" col-md-10">
        <div class="modal fade bs-example-modal-sm" tabindex="-1" id="uploadStatus" role="dialog">
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
        </div>
    </div><!-- col-md-10 -->



</div>


<!-- contentpanel -->
<%@ include file="includes/footer.jsp" %>  
<script src="<%=request.getContextPath()%>/resources/js/dropzone.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.ui.widget.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.fileupload.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.iframe-transport.js"></script>
<!-- The File Upload validation plugin -->
<script src="<%=request.getContextPath()%>/resources/js/jquery.fileupload-validate.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>

<script>
    var filesList = new Array();
    function format(item) {
        return '<i class="fa ' + ((item.element[0].getAttribute('rel') === undefined) ? "" : item.element[0].getAttribute('rel')) + ' mr10"></i>' + item.text;
    }
    $(document).ready(function() {
        $('#uploaded-files').hide();
        $('#fileupload').fileupload({
            autoUpload: false,
            //dropZone: $('#dropzone')
        }).on('fileuploadprogressall', function(e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .bar').css(
                    'width',
                    progress + '%'
                    );
        }).on('fileuploadadd', function(e, data) {
            $('#uploaded-files').show();
            $.each(data.files, function(index, file) {
                filesList.push(data.files[index]);
                console.log("Added: " + data.files[index].name);

                var node = $("#uploaded-files").append(
                        $('<tr/>')
                        .append($('<td/>').text(file.name))
                        .append($('<td/>').text(file.size))
                        .append($('<td/>').text(file.type))

                        )//end $("#uploaded-files").append()
                node.appendTo(data.context);
            });
        }).on('fileuploadprocessalways', function(e, data) {
            var index = data.index,
                    file = data.files[index],
                    node = $(data.context.children()[index]);
            if (file.preview) {
                node.prepend('<br>').prepend(file.preview);
            }
            if (file.error) {
                node.append('<br>').append($('<span class="text-danger"/>').text(file.error));
            }
        }).prop('disabled', !$.support.fileInput)
                .parent().addClass($.support.fileInput ? undefined : 'disabled');
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
                totalSum: {
                    required: true,
                    money: true
                },
                productId: {
                    required: true,
                    select: true
                }
                ,
                noOfRecords: {
                    required: true,
                    number: true
                }
            },
            highlight: function(element) {
                jQuery(element).closest('.form-group').removeClass('has-success').addClass('has-error');
            },
            success: function(element) {
                jQuery(element).closest('.form-group').removeClass('has-error');
            }
        });
        $("#savingsForm").submit(function(event) {

            if (filesList.length > 0) {
                $.blockUI({message: '<h1>Processing files. Please wait...</h1>'});
                event.preventDefault();
                $('#fileupload').fileupload('send', {files: filesList})

                        /**.progressall(function(event, data) {
                         var progress = parseInt(data.loaded / data.total * 100, 10);
                         $('#progress .bar').css(
                         'width',
                         progress + '%'
                         );
                         })**/
                        .error(function(jqXHR, textStatus, errorThrown) {
                            console.log('error');
                        })

                        .complete(function(result, textStatus, jqXHR) {

                            $("tr:has(td)").remove();
                            var data = JSON.stringify(result.responseText);

                            if (result.statusText === 'OK') {
                                $.unblockUI();
                                $('#status-text').html("Upload Successful!");
                                $('#uploadStatus').modal('show');
                                $('#noOfRecords').val('');
                                //$('#productId').val('');
                                $('#totalSum').val('');
                                $('#fileupload').val('');
                                //alert("Upload Successful");
                            }

                            $('#files').show();

                        });
            } else {
                console.log("plain default form submit");
            }
        });

        jQuery('select').select2({
            formatResult: format,
            formatSelection: format,
            escapeMarkup: function(m) {
                return m;
            }
        });





    });
</script>