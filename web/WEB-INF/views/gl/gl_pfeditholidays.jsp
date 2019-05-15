<%@ include file="../includes/header.jsp" %>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Holiday Setup</li>
        </ul>
        <h4>Edit Holiday Settings</h4>
    </div>
    <%@include file="../includes/topright.jsp" %>
    
</div>
</div><!-- media -->
</div><!-- pageheader -->



<div class="contentpanel">

    <div class="row col-md-6">
        <!-- CONTENT GOES HERE -->  
        <!-- <div class="panel-body">-->
            <!-- form starts -->
            <form:form method="post" action="gl_pfeditholidays.htm" modelAttribute="holidatesdet">  
                <div class="panel-body">

                    <div class="form-group">
                        <form:label path="id" cssClass="col-sm-4 control-label">Id: </form:label>
                            <div class="col-sm-6">
                            <form:input path="id" size="2"  readonly="true" disabled="disable" />
                            <div class="error">
                                <form:errors path="id" />
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="description" cssClass="col-sm-4 control-label tooltips"  data-original-title="Title" data-toggle="tooltip" data-placement="left">Description: *</form:label>
                            <div class="col-sm-6">
                            <form:input path="description" size="50" />
                            <div class="error">
                                <form:errors path="description" />
                            </div>
                        </div>
                    </div><!-- form-group -->

                    <div class="form-group">
                     <form:label path="holidate" cssClass="col-sm-4 control-label">Date: *</form:label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                <form:input path="holidate" placeholder="dd/mm/yyyy" id="holidate" cssClass="form-control"  size="10" />
                            <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                             
                                </div>
                                <form:errors path="holidate" cssClass="error" />
                            </div>
                            
                    </div>
                    
                    <div class="form-group">
                        <form:label path="recurring" cssClass="col-sm-4 control-label tooltips"  data-original-title="Recurring" data-toggle="tooltip" data-placement="left">Recurring: *</form:label>
                            <div class="col-sm-4">
                            <form:select path="recurring" >
                                <form:option value="0" label="No"/>
                                <form:option value="1" label="Yes"/>
                            </form:select>
                            <form:errors path="recurring" cssClass="error" />
                        </div>
                    </div><!-- form-group -->
                    

                </div><!-- panel-body -->
                <div>
                    <input type="submit" class="btn btn-danger mr5" value="Save"/>
                </div><!-- panel-footer -->

            </form:form>  
            <!-- form ends -->
       <!--  </div> -->
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
                jQuery('#holidate').datepicker();  
                jQuery('#enddatepicker').datepicker();
                $.datepicker.setDefaults({
                    dateFormat: 'dd/mm/yy'
                });
       jQuery('select').select2({
                    minimumResultsForSearch: -1
                });
      jQuery('#data-list').DataTable({
                    responsive: true
                });
       
       jQuery("#accountNo").select2();
       
       
                     
            
                    
    });
    
    
</script>
    </body>


</html>