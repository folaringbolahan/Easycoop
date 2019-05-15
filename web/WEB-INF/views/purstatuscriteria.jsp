<%-- 
    Document   : purstatusdterangcriteria
    Created on : Feb 10, 2016, 4:15:00 PM
    Author     : Olakunle Awotunbo
--%>

<%@ include file="includes/header.jsp" %>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Purchase Status & Date Range Criteria</li>
        </ul>
        <h4>Purchase Status & Date Range Criteria</h4>
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
            <form:form method="post" action="${reportpath}" modelAttribute="reportdet">  
                <div class="panel-body">
                    <p></p>
                    <p></p>
                    <p></p>
                    <p></p>
                    <p></p>
                    <p></p>
                    <p>
                     <p>   
                    <p>
                        <div class="form-group">
                        <form:label path="purchasestatus" cssClass="col-sm-4 control-label">Purchase Status: </form:label>
                            <div class="col-sm-8">
                             <form:select class="width100p" id="purchasestatus" path="purchasestatus"  >
                             <option value="">Choose Purchase Status</option>    
                                            <option value="A">APPROVED</option>
                                            <option value="C">CANCELLED</option>
                                            <option value="F">FINISHED PAYMENT</option>
                                            <option value="N">NEW</option>
                                            <option value="S">ACTIVE</option>
                                        
                            </form:select>
                            
                            <div class="error">
                                <form:errors path="accountno1" />
                            </div>
                        </div>
                    </div><!-- form-group -->
                        <br><br> 
                    <div class="form-group">
                     <form:label path="startdatestr" cssClass="col-sm-4 control-label">From Date: </form:label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                <form:input path="startdatestr" placeholder="dd/mm/yyyy" id="startdatepicker" cssClass="form-control"  size="10" />
                            <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                             
                                </div>
                                <form:errors path="startdatestr" cssClass="error" />
                            </div>
                    </div><!-- form-group -->
                    <div class="form-group">
                     <form:label path="enddatestr" cssClass="col-sm-4 control-label">To Date: </form:label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                <form:input path="enddatestr" placeholder="dd/mm/yyyy" id="enddatepicker" cssClass="form-control"  size="10" />
                            <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                             
                                </div>
                                <form:errors path="enddatestr" cssClass="error" />
                                <input type="hidden" id="paratype" name="paratype" value="${paratype}"/>
                            <form:hidden path="reppath" />
                            </div>
                    </div>
                    <!-- form-group -->

                </div><!-- panel-body -->
                <div>
                    <input type="submit" class="btn btn-danger mr5" value="Submit"/>
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
                    dateFormat: 'dd/mm/yy'
                });
       jQuery('select').select2({
                    minimumResultsForSearch: -1
                });
      jQuery('#data-list').DataTable({
                    responsive: true
                });
       
       jQuery("#purchasestatus").select2();
    });
    
    
</script>
    </body>


</html>
