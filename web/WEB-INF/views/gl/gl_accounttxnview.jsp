<%@ include file="../includes/header.jsp" %>


<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Account Transaction View</li>
        </ul>
        <h4>Account Transaction View</h4>
    </div>
    <%@include file="../includes/topright.jsp" %>
    
</div>
</div><!-- media -->
</div><!-- pageheader -->



<div class="contentpanel">

    <div class="row col-md-12">
        <!-- CONTENT GOES HERE -->  
        <!-- <div class="panel-body">-->
            <!-- form starts -->
            <form:form method="post" action="gl_accounttxnview.htm" modelAttribute="txnparadet">  
                <div class="panel-body">

                    <div class="row col-md-8">
                    <div class="form-group">
                        <form:label path="accountno" cssClass="col-sm-1 control-label">Account  :*</form:label>
                            <div class="col-sm-10">
                            <form:select path="accountno" cssClass="width300" id="select-search-hide" >
                                <form:option value="" label="Select Account"/>
                                <form:options  items="${accs}"
                                         itemValue="accountNo" itemLabel="name"/>
                            </form:select>
                        </div>  
                        </div>
                          </div>  
                     <div class="row col-md-8">       
                    <div class="form-group">  
                             <form:label path="startdate" cssClass="col-sm-1 control-label">Start Date:*</form:label>
                            <div class="col-sm-3">
                                <div class="input-group">
                                <form:input path="startdate" placeholder="mm/dd/yyyy" id="startdatepicker" cssClass="form-control"  size="10" />
                            <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                             
                                </div>
                            </div>
                            
                            <form:label path="enddate" cssClass="col-sm-2 control-label">End Date:*</form:label>
                            <div class="col-sm-3">
                                <div class="input-group">
                            <form:input path="enddate" placeholder="mm/dd/yyyy" id="enddatepicker" cssClass="form-control" size="10"/>
                            <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                                </div>
                            </div>
                            <input type="submit" class="btn btn-danger mr5" value="Go"/>
                    </div>
                     </div>
                    <div class="form-group">
                        <p></p>
                    </div><!-- form-group -->        
                            
                    <!-- form-group -->
                    <div class="row col-md-8"> 
                    <div class="form-group">
                       <form:label path="bal" cssClass="col-sm-1 control-label">Balance: </form:label>
                            <div class="col-sm-3">
                                <div class="input-group">
                                   <fmt:formatNumber type="number" maxFractionDigits="2" value="${txnparadet.bal}" var="theFormattedbal"/> 
                            <form:input path="bal" id="bal" readonly="true" size="20" value="${theFormattedbal}" />
                            </div> 
                           </div>
                       <form:label path="totdr" cssClass="col-sm-1 control-label"> &nbsp;Debits: </form:label>
                            <div class="col-sm-3">
                                <div class="input-group">
                                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${txnparadet.totdr}" var="theFormatteddr"/> 
                            <form:input path="totdr" id="totdeb" readonly="true" size="20" value="${theFormatteddr}"/>
                                </div>
                            </div>
                       <form:label path="totcr" cssClass="col-sm-1 control-label">&nbsp;Credits: </form:label>
                            <div class="col-sm-3">
                                <div class="input-group">
                                 <fmt:formatNumber type="number" maxFractionDigits="2" value="${txnparadet.totcr}" var="theFormattedcr"/>    
                            <form:input path="totcr" id="totcr" readonly="true" size="20" value="${theFormattedcr}"/>
                            </div> 
                           </div>
                    </div><!-- form-group -->
                    </div>
                    <!-- form-group -->
                    

                </div><!-- panel-body -->
                <!-- panel-footer -->

            </form:form>  
            <!-- form ends -->
       <!--  </div> -->
        <!-- End of panel-body -->
    </div>

    <div class=" col-md-12">
        <table  id="data-list" class="table table-striped table-bordered responsive" >       
            <thead>
                <tr>
                    <th>No.</th>
                    <th>Entry Ref.</th>
                    <th class=" tooltips"  data-original-title="Click to Sort" data-toggle="tooltip" data-placement="top">Post Date</th>
                    <th class=" tooltips"  data-original-title="Click to Sort" data-toggle="tooltip" data-placement="top">Value Date</th>
                    <th>Narrative</th>
                    <th>Debit</th>
                    <th>Credit</th>
                    <th>User Id</th>

                </tr>
            </thead>
            <tbody>
                <c:forEach var="accounttxn" items="${accounttxns}" >

                    <tr>
                        <td>${accounttxn.txnId}</td>    
                        <td>${accounttxn.entryref}</td>
                        <td>${accounttxn.postdate}</td>
                        <td>${accounttxn.valuedate}</td>
                        <td>${accounttxn.narrative}</td>
                        <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${accounttxn.debit}" /></td>
                        <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${accounttxn.credit}" /></td>
                        <td>${accounttxn.userId}</td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>
    </div><!-- col-md-10 -->



</div>
<!-- contentpanel 
 
<//%@ include file="../includes/footer.jsp" %>  
-->

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


