<%@ include file="includes/header.jsp" %>  
<link href="<%=request.getContextPath()%>/resources/css/dropzone.css" rel="stylesheet" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.fileupload.css">
<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="index.htm">Home</a></li>
            <li>Agm Member Upload</li>
        </ul>
        <h4>Agm Member Upload-Error Check</h4>
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
                    <h5 class="panel-title"> MEMBER UPLOAD ERRORS</h5>
                    <p>Use the form below to check errors on Agm Members upload </p>
                </div>
                <div class="panel-body">                   
      
                    <form action="${pageContext.request.
                                    contextPath}/extimpuploaderr" id="agmForm" method="post">
                        <div class="form-group">
                            <label class="col-sm-4 control-label tooltips" data-toggle="tooltip" title="Select Agms">Agm <span class="asterisk">*</span></label>
                            <div class="col-sm-8">
                                <select  cssClass="width300" id="agid"  name="agid"  required="required"  >
                                    <option value="0">Choose Agm</option>
                                    <c:forEach  var="items" items="${agms}" varStatus="status" >

                                        <option value="${items.id}">${items.description}--${items.companyName}</option>
                                    </c:forEach>
                                </select>

                            </div>
                        </div><!-- form-group -->
                        
                        <!-- form-group -->
                        <div class="form-group" >
                            <label class="col-sm-4 control-label tooltips" data-toggle="tooltip" title="Enter the Batch Id of the upload">Upload Batch Id</label>
                            <div class="col-sm-3">
                                <input name="rf" class="form-control"  id="rf" required="required" />
                            </div>
                            <div>

                            </div>
                        </div><!-- form-group --> 
                        <!-- form-group -->
       <!-- form-group --> 
                        <div class="form-group">
                            <label class="col-sm-4 control-label"></label>
                            <div class="col-sm-4">

                                <button type="submit" class="btn btn-danger mr5" id="uploadBtn">Submit</button>   

                            </div>
                        </div>

                </div><!-- panel-body -->
                
                
                
                
                
                
            </div><!-- panel -->
           
            <!-- form ends --> 



   
            <!-- new for guarantor panel end -->










        </div>
        <!-- End of panel-body -->
    </div>

    



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
     $(document).ready(function() {
        $('#uploaded-files').hide();
       
       
  
        jQuery("#agmForm").validate({
            rules: {
                
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
        





        jQuery('select').select2({
            formatResult: format,
            formatSelection: format,
            escapeMarkup: function(m) {
                return m;
            }
        });





    });
</script>