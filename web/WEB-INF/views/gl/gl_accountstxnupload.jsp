<%@ include file="../includes/header.jsp" %>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Accounts Transaction Upload</li>
        </ul>
        <h4>Accounts Transaction Import </h4>
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
                   <h4 class="panel-title">Accounts Transaction Import</h4>
                   <p>To do bulk posting into account</p>
                   
             </div>
                <form:form action="gl_accttxnuploadfile.htm" method="post" enctype="multipart/form-data" modelAttribute="acctxnbulkupload">
	          <div class="panel-body">
                      
                  <!--     <div class="row col-md-8">       
                    <div class="form-group">  
                             <form:label path="txnDatestr" cssClass="col-sm-3 control-label">Transaction Date:*</form:label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                <form:input path="txnDatestr" placeholder="dd/MM/yyyy" id="startdatepicker" cssClass="form-control"  size="10" />
                            <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                                
                                </div>
                               <form:errors path="txnDatestr" cssClass="error" />
                            </div>
                    </div>                                
                    </div> --->
                   
                                         
                            <div class="row col-md-8">
	              <div class="form-group">  
	                   <form:label for="fileData" path="fileData" cssClass="col-sm-3 control-label">Select file*</form:label>
	                   
			   <div class="col-sm-3">
			     <form:input path="fileData" type="file"/>
			  </div> 
	             </div>
                       </div>                   
                     <div class="row col-md-8"> 
                    <div class="form-group">
                       <form:label path="totalRecords" cssClass="col-sm-3 control-label">Total # of Records: *</form:label>
                            <div class="col-sm-3">
                                <div class="input-group">
                            <form:input path="totalRecords" id="totalRecords" size="10"/>
                            <div class="error">
                                <form:errors path="totalRecords" />
                            </div>
                            </div> 
                           </div>
                       
                    </div><!-- form-group -->
                    </div>        
                        
                      <div class="row col-md-8"> 
                    <div class="form-group">
                       <form:label path="filesum" cssClass="col-sm-3 control-label">Total Amount: </form:label>
                            <div class="col-sm-3">
                                <div class="input-group">
                            <form:input path="filesum" id="filesum" size="15"/>
                            <div class="error">
                                <form:errors path="filesum" />
                            </div>
                            </div> 
                           </div>
                       
                    </div><!-- form-group -->
                    </div>          
                            
                     <input type="hidden" name="ACTION_ID" value="1">
		     <form:hidden path="uploadedBy" value="<%=request.getRemoteUser()%>"/>
		     <form:hidden path="uploadDate"  value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>
             <div class="row col-md-8">
		     <div class="form-group">
			    <button class="btn btn-danger mr5" type="submit">SUBMIT</button>
			    <button type="reset" class="btn btn-default">RESET</button>
		     </div><!-- panel-footer -->       
             </div>
      		</form:form>  
             </div>
        <!-- End of panel-body -->
  </div>      
  </div>
        
        
            <!-- new panel for download start -->  
            <div class="col-md-6">
                <div class="panel panel-default"> 
                    <div class="panel-heading">
                        <div class="panel-btns">
                        </div><!-- panel-btns -->
                        <h5 class="panel-title">TEMPLATE FOR BULK ACCOUNT TRANSACTIONS </h5>
                        <p>Download this template for bulk account transactions. </p>
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
                                            <td>Accounttxnimporttemplate</td>
                                            <td>For bulk account upload transactions</td>                                            
                                             <td><a href="accountUploadTempl.htm?downFile=Accounttxnimporttemplatenewbat.xls">Download</a></td>
                                        </tr>

                                    </tbody>
                                </table>
                            </div><!-- table-responsive -->
                        </div>

                    </div>      
                </div>
            </div>   
            <!-- new for guarantor panel end -->   
        
        
       <!-- contentpanel -->
            
 
</div>
            </div><!-- mainwrapper -->
        </section>


        <script src="<%=request.getContextPath()%>/resources/js/jquery-1.11.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery-migrate-1.2.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.10.3.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/modernizr.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/pace.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/retina.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery.cookies.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery.autogrow-textarea.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery.mousewheel.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery.tagsinput.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/toggles.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/bootstrap-timepicker.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery.maskedinput.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/select2.min.js"></script>
        
        <script src="<%=request.getContextPath()%>/resources/js/jquery.validate.min.js"></script>

        <script src="<%=request.getContextPath()%>/resources/js/jquery.dataTables.min.js"></script>
       <script src="<%=request.getContextPath()%>/resources/js/dataTables.bootstrap.js"></script>
        
        <script src="<%=request.getContextPath()%>/resources/js/dataTables.responsive.js"></script>
         <script src="${resourceUrl}/js/custom.js"></script>
         <script src="<%=request.getContextPath()%>/resources/js/jquery.autocomplete.min.js"></script>
        <script>
    $(document).ready(function(){
        // Date Picker
                jQuery('#startdatepicker').datepicker();  
                jQuery('#enddatepicker').datepicker();
                $.datepicker.setDefaults({
                    dateFormat: 'dd/mm/yy'
                });
       jQuery('#select-search-hide').select2({
                    minimumResultsForSearch: 15
                }); 
      jQuery('#data-list').DataTable({
                    responsive: true
                });
       
       jQuery("#accountNo").select2();
       
       
                     
            
                    
    });
    
    
</script>
    </body>


</html>