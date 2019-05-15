<%@ include file="includes/header.jsp" %>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="index.htm">Home</a></li>
            <li>Unlock Account</li>
        </ul>
        <h4>Unlock Account </h4>
    </div>
    <%@include file="includes/topright.jsp" %>
    
</div>
</div><!-- media -->
</div><!-- pageheader -->



<div class="contentpanel">

    <div class="row col-md-6">
        <!-- CONTENT GOES HERE -->  
        <!-- <div class="panel-body">-->
            <!-- form starts -->
            <form:form method="post" action="pfaccountsunlocksave.htm" modelAttribute="accountdet">  
                <div class="panel-body" id="panbod">


                    <div class="form-group">
                        <form:label path="id" cssClass="col-sm-4 control-label tooltips"  data-original-title="Number" data-toggle="tooltip" data-placement="left">Id: *</form:label>
                            <div class="col-sm-6">
                            <form:input path="id" size="20" readonly="true"/>
                            
                            <div class="error">
                                <form:errors path="id" />
                            </div>
                        </div>
                    </div><!-- form-group -->

                    <div class="form-group">
                        <form:label path="userName" cssClass="col-sm-4 control-label tooltips"  data-original-title="User Name" data-toggle="tooltip" data-placement="left">Name: </form:label>
                            <div class="col-sm-6">
                            <form:input path="userName" size="50" readonly="true"/>
                            <div class="error">
                                <form:errors path="userName" />
                            </div>
                        </div>
                    </div><!-- form-group -->
                    
                     <div class="form-group">
                        <form:label path="userId" cssClass="col-sm-4 control-label tooltips"  data-original-title="User Id" data-toggle="tooltip" data-placement="left">User Id: </form:label>
                            <div class="col-sm-6">
                            <form:input path="userId" size="50" readonly="true"/>
                            <div class="error">
                                <form:errors path="userId" />
                            </div>
                        </div>
                    </div><!-- form-group -->
                         

                    
                </div><!-- panel-body -->
                <div>
                    <input type="submit" class="btn btn-danger mr5" value="Unlock"/>
                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                   
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
       <script src="http://cdn.datatables.net/plug-ins/725b2a2115b/integration/bootstrap/3/dataTables.bootstrap.js"></script>
        
        <script src="http://cdn.datatables.net/responsive/1.0.1/js/dataTables.responsive.js"></script>
         <script src="${resourceUrl}/js/custom.js"></script>

        <script>
    $(document).ready(function(){
        // Date Picker
                jQuery('#startdatepicker').datepicker();  
                jQuery('#enddatepicker').datepicker();
                $.datepicker.setDefaults({
                    dateFormat: 'mm/dd/yy'
                });
       jQuery('select').select2({
                    minimumResultsForSearch: -1
                });
     
       
       
    });
    
    
</script>
    </body>


</html>
