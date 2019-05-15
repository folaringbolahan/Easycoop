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
        <h4>Agm Member Upload</h4>
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
                    <h5 class="panel-title"> MEMBER UPLOAD</h5>
                    <p>Use the form below to upload  Agm Members</p>
                </div>
                <div class="panel-body">                   
      
                    <form action="${pageContext.request.
                                    contextPath}/extmembuploads" enctype="multipart/form-data" id="agmForm" method="post">
                        <div class="form-group">
                            <label class="col-sm-4 control-label tooltips" data-toggle="tooltip" title="Select Agms">Agm <span class="asterisk">*</span></label>
                            <div class="col-sm-8">
                                <select  cssClass="width300" id="description"  name="description"  required="required"  >
                                    <option value="0">Choose Agm</option>
                                    <c:forEach  var="items" items="${agms}" varStatus="status" >

                                        <option value="${items.id}">${items.description}--${items.companyName}</option>
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
       <!-- form-group --> 
                        <div class="form-group">
                            <label class="col-sm-4 control-label tooltips" data-toggle="tooltip" title="Browse the file to upload">Select file <span class="asterisk">*</span></label>

                            <span class="btn btn-success fileinput-button">
                                <i class="glyphicon glyphicon-plus"></i>
                                <span>Add files...</span>
                                <!-- The file input field used as target for the file upload widget -->
                               <input id="fileupload" type="file" name="files" >
                                
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
                          <input type="hidden" name="createdby" value="<%=request.getRemoteUser()%>">
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
                        <h5 class="panel-title">TEMPLATE FOR AGM MEMBER UPLOAD</h5>
                        <p>Download this template for  member upload. </p>
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
                                            <td> Agm Member Template</td>
                                            <td>Agm Members</td>
                                            <td><a href="downloadagmtmpl.htm?downFile=Agm_Members_Templ.xls">Download</a></td>
                                        </tr>

                                    </tbody>
                                </table>
                            </div><!-- table-responsive -->
                        </div>

                      
                       

                    </div>   
                    
                     <c:if test="${not empty batchref}">
                
               <div class="panel panel-default">
                           <div class="panel-body">
                              
                                <div class="alert alert-success">
                                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                    <strong>Upload Completed!</strong> Please note Batch Reference for Errors - <strong>${batchref}</strong>. 
                                     <br><a href="externalImporterrchk" target="_blank" class="alert-link">View Errors </a>
                                    <c:if test="${haserrors==true}">
                                      <a href="extimpuploaderr?rf=${batchref}&amp;agid=${agi}" class="alert-link"> View this Batch Errors</a>.
                                    </c:if>
                                </div>
                              
                                
                                
                            </div><!-- panel-body -->
                        </div><!-- panel --> 
                
                </c:if> 
                    
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