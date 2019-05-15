<%@ include file="../includes/header.jsp" %>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Hire Purchase Repayment Transaction Upload</li>
        </ul>
        <h4>Hire Purchase Repayment Transaction Import </h4>
    </div>
    <%@include file="../includes/topright.jsp" %>
    
</div>
</div><!-- media -->
</div><!-- pageheader -->


<div class="contentpanel">
    
        <div class="row col-md-12">
            <!-- CONTENT GOES HERE -->  
           
                <form:form action="hp_rpymttxnuploadfile.htm" method="post" enctype="multipart/form-data" modelAttribute="rpymttxnbulkupload">
	          <div class="panel-body">
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
                            <form:input path="filesum" id="filesum" size="10"/>
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