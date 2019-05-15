<%@ include file="includes/header.jsp" %>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Loan Status Criteria</li>
        </ul>
        <h4>Loan Status Criteria</h4>
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
                        <form:label path="strval1" cssClass="col-sm-4 control-label">Loan Status: </form:label>
                            <div class="col-sm-8">
                             <form:select class="width100p" id="strval1" path="strval1"  >
                             	    <option value=""> -- select --</option>
                             	    <option value="E">Entered</option>
                             	    <option value="A">Approved</option>
                             	    <option value="R">Rejected</option>
                             	    <option value="D">Disbursed(Active)</option>
                                    <option value="C">Completed</option>
                                    <option value="X">Canceled</option>
                            </form:select>
                        </div>
                    </div><!-- form-group -->
                        <br><br>
                   <p>
                        <div class="form-group">
                        <form:label path="intval1" cssClass="col-sm-4 control-label tooltips" data-toggle="tooltip" title="Select product">Product: </form:label>
                            <div class="col-sm-8">
                             <form:select class="width100p" id="productId" path="intval1"  >
                             <option value="">Choose Product</option>   
                            <c:forEach  var="product" items="${products}" varStatus="status" >
                                <option value="${product.id}"> ${product.name}</option>
                            </c:forEach>

                            </form:select>
                            <%--    <input type="hidden" id="strval1" name="strval1" value="${paratype}"/> --%>
                            <div class="error">
                                <form:errors path="productId" />
                            </div>
                            <input type="hidden" id="paratype" name="paratype" value="${paratype}"/>
                            <form:hidden path="reppath" />
                        </div>
                    </div><!-- form-group -->
                        <br>
                    
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
    });


</script>
    </body>


</html>
