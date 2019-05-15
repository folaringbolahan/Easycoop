<%@ include file="../includes/header.jsp" %>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Accounts Upload</li>
        </ul>
        <h4>Accounts Import </h4>
    </div>
    <%@include file="../includes/topright.jsp" %>
    
</div>
</div><!-- media -->
</div><!-- pageheader -->

<div class="contentpanel">
    <div class="row">
        <div class="col-md-6">
        <!-- CONTENT GOES HERE -->  
        <div class="panel panel-default"> 
             <div class="panel-heading">   
                  <div class="panel-btns">
                   </div><!-- panel-btns -->
                   <h4 class="panel-title">Bulk Upload For GL Account Creation</h4>
                   <p>Use the form below to upload the chart of Account</p>
                   
             </div>
        <div class="panel-body"> 
                    <form:form action="gl_acctuploadfile.htm" method="post" enctype="multipart/form-data" modelAttribute="accbulkupload">    
                       <div class="form-group">
                            <form:label for="fileData" path="fileData" cssClass="col-sm-4 control-label">Select file</form:label>
                            <div class="col-sm-2">
                                 <form:input path="fileData" type="file"/>
                            </div>
                            
                        </div>
                            
                     	    <input type="hidden" name="ACTION_ID" value="1">
		            <form:hidden path="uploadedBy" value="<%=request.getRemoteUser()%>"/>
		            <form:hidden path="uploadDate"  value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>

                            
                        <div class="form-group">
                            <label class="col-sm-4 control-label"></label>
                            <div class="col-sm-4">
                                <div class="form-group">
			           <button class="btn btn-danger mr5" type="submit">SUBMIT</button>
			           <button type="reset" class="btn btn-default">RESET</button>
		                </div>
                            </div>
                        </div><!-- form-group -->
                        <!-- The fileinput-button span is used to style the file input field as button -->


                    </form:form>
                </div><!-- panel-body -->
            </div><!-- panel -->
            <!-- form ends -->
        </div>
        <!-- End of panel-body -->  
        
        
        
        
        
        
        
            <!-- new panel for guarantor start -->  
            <div class="col-md-6">
                <div class="panel panel-default"> 
                    <div class="panel-heading">
                        <div class="panel-btns">
                        </div><!-- panel-btns -->
                        <h5 class="panel-title">TEMPLATE FOR ACCOUNT CREATION </h5>
                        <p>Download this template for bulk GL account creation. </p>
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
                                            <td>CHART OF ACCOUNT TEMPLATE</td>
                                            <td>Template for Chart of Account Import</td>
                                            <td><a href="downloadgltempl.htm?downFile=Accountimporttemplate.xls">Download</a> </td>
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

</div>




<!-- contentpanel -->
            
  <%@ include file="../includes/footer.jsp" %>  

   