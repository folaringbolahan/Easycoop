<%@ include file="includes/header.jsp" %>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Account Range Criteria</li>
        </ul>
        <h4>Account Range Criteria</h4>
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
                        <form:label path="accountno1" cssClass="col-sm-4 control-label">From Account no: </form:label>
                            <div class="col-sm-8">
                            <form:select class="width100p" id="accountno1" path="accountno1"  >    
                             
                             <option value="">Choose Account</option>   
                            <c:forEach  var="account1" items="${accounts}" varStatus="status"  >
                                <option value="${account1.accountNo}">${account1.accountNo} - ${account1.name}</option>
                            </c:forEach>  
                            </form:select>
                            
                            <div class="error">
                                <form:errors path="accountno1" />
                            </div>
                        </div>
                    </div><!-- form-group -->
                        <br><br> 
                    <div class="form-group">
                        <form:label path="accountno2" cssClass="col-sm-4 control-label">To Account no: </form:label>
                            <div class="col-sm-8">
                            <form:select  class="width100p" id="accountno2" path="accountno2" >
                             <option value="">Choose Account</option>   
                            <c:forEach  var="account2" items="${accounts}" varStatus="status" >
                                <option value="${account2.accountNo}">${account2.accountNo} - ${account2.name}</option>
                            </c:forEach>
                            </form:select>
                            
                            <div class="error">
                                <form:errors path="accountno2" />
                            </div>
                        </div>
                            <input type="hidden" id="paratype" name="paratype" value="${paratype}"/>
                            <form:hidden path="reppath" />
                    </div><!-- form-group -->
                    <!-- form-group -->

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
       
       jQuery("#accountno1").select2();
       jQuery("#accountno2").select2();
    });
    
    
</script>
    </body>


</html>
