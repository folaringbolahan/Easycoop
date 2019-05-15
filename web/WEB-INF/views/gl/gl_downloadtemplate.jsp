<%-- 
    Document   : gl_downloadtemplate
    Created on : Dec 16, 2015, 12:08:16 PM
    Author     : Olakunle Awotunbo
--%>


<%@ include file="../includes/header.jsp" %>  
<link href="<%=request.getContextPath()%>/resources/css/dropzone.css" rel="stylesheet" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.fileupload.css">
<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="index.htm">Home</a></li>
            <li>Download Template</li>
        </ul>
        <h4>Account Balance Template</h4>
    </div>
    <%@include file="../includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->




<div class="contentpanel">

    <div class="row col-md-10">
        <!-- CONTENT GOES HERE -->  
        <div class="panel-body">
            <!-- form starts -->
            <div class="panel panel-default">
                <div class="panel-heading">                    
                    <h4 class="panel-title">Download Member Account Balance Template</h4>
                </div>
                <div class="panel-body">
                    <p>Use the form below to download members details against products for account balance</p>
                    <br />
                  
                    
                    <form:form action="gl_downloadexcelTemplate.htm"  id="savingsForm" method="post" modelAttribute="fileUpload">
                        <div class="form-group">
                            <label class="col-sm-4 control-label tooltips" data-toggle="tooltip" title="Select the product to download template for">Product <span class="asterisk">*</span></label>
                            <div class="col-sm-4">
                                <form:select  path="productId" cssClass="width300" id="productId"  name="productId"  required="required"  >
                                    <form:option value="0">Choose Product</form:option>
                                    <c:forEach  var="product" items="${products}" varStatus="status" >
                                        <form:option value="${product.id}">${product.name}</form:option> 
                                    </c:forEach>
                                </form:select>

                            </div>
                        </div><!-- form-group -->
                        <!-- form-group -->
              
                       
                        <div class="form-group">
                            <label class="col-sm-4 control-label"></label>
                            <div class="col-sm-4">
                                <button type="submit" class="btn btn-danger mr5" id="uploadBtn">Submit</button>   

                            </div>
                        </div><!-- form-group -->
                        <!-- The fileinput-button span is used to style the file input field as button -->


                    </form:form>
                     

                </div><!-- panel-body -->
            </div><!-- panel -->
            <!-- form ends -->
        </div>
        <!-- End of panel-body -->
    </div>

    



</div>


<!-- contentpanel -->
<%@ include file="../includes/footer.jsp" %>  
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
                fileSum: {
                    required: true,
                    money: true
                },
                productId: {
                    required: true,
                    select: true
                }
                ,
                userUploadcount: {
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

