<%@ include file="../includes/header.jsp" %>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Hire Purchase Repayment (Single)</li>
        </ul>
        <h4>Hire Purchase Repayment (Single)</h4>
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
            <form:form method="post" action="hp_pfpaysingpostx.htm" modelAttribute="hprequestdetres">  
               
                <div class="panel panel-default">
                     <div class="panel-heading">
                        <h4 class="panel-title">HP Details</h4>
                     </div><!-- panel-heading -->
                
                
                <div class="panel-body">

                    <div class="form-group">
                        <form:label path="refid" cssClass="col-sm-4 control-label">Reference : ${hprequestdetres.refid} </form:label>
                        <form:label path="memberno" cssClass="col-sm-4 control-label">Member : ${hprequestdetres.membername} - ${hprequestdetres.memberno} </form:label>
                        <form:label path="product" cssClass="col-sm-4 control-label">Product : ${hprequestdetres.productname}</form:label>
                        <form:label path="txndate" cssClass="col-sm-4 control-label">Trans. Date : ${hprequestdetres.txndate}</form:label>
                        <form:label path="hpprice" cssClass="col-sm-4 control-label">HP price : ${hprequestdetres.hpprice}</form:label>
                        <form:label path="cashprice" cssClass="col-sm-4 control-label">Cash price : ${hprequestdetres.cashprice}</form:label>
                        <form:label path="downpaymentamount" cssClass="col-sm-4 control-label">Down payment Amount : ${hprequestdetres.downpaymentamount}</form:label>
                        <form:label path="interestamt" cssClass="col-sm-4 control-label">Interest Amount : ${hprequestdetres.interestamt}</form:label>
                        <form:label path="interestrate" cssClass="col-sm-4 control-label">Interest rate : ${hprequestdetres.interestrate}</form:label>
                        <form:label path="repaymentperiodinmonths" cssClass="col-sm-4 control-label">Repayment Period(months) : ${hprequestdetres.repaymentperiodinmonths}</form:label>
                        <form:label path="repaymentfrequency" cssClass="col-sm-4 control-label">Repayment frequency : ${hprequestdetres.repaymentfrequency}</form:label>
                        <form:label path="interestcalcmtd" cssClass="col-sm-4 control-label">Interest method : ${hprequestdetres.interestcalcmtd}</form:label>
                        <input type="hidden" id="productcode" value="${hprequestdetres.product}"/>
                     </div>
                   </div><!-- panel-body -->
                  </div>  
            </form:form>                    
                <form:form method="post" action="hp_pfpaysingpost.htm" modelAttribute="hpscdldetres">      
                <div>
                    
                    <div class="form-group">
                       <div class="form-group">
                        <form:label path="instalment" cssClass="col-sm-3 control-label tooltips"  data-original-title="Total Instalment Payment" data-toggle="tooltip" data-placement="left">Instalment: *</form:label>
                            <div class="col-sm-4">
                            <form:input path="instalment" size="30" id="instalment" readonly="true"/>
                            <div class="error">
                                <form:errors path="instalment" />
                            </div>
                        </div>
                    </div><!-- form-group -->
                    <div class="form-group">
                        <form:label path="principal" cssClass="col-sm-3 control-label tooltips"  data-original-title="Principal" data-toggle="tooltip" data-placement="left">Principal: *</form:label>
                            <div class="col-sm-4">
                            <form:input path="principal" size="30" id="principal" readonly="true"/>
                            <div class="error">
                                <form:errors path="principal" />
                            </div>
                        </div>
                    </div><!-- form-group -->
                    <div class="form-group">
                        <form:label path="interest" cssClass="col-sm-3 control-label tooltips"  data-original-title="Interest" data-toggle="tooltip" data-placement="left">Interest: *</form:label>
                            <div class="col-sm-4">
                            <form:input path="interest" size="30" id="interest" readonly="true"/>
                            <div class="error">
                                <form:errors path="interest" />
                            </div>
                        </div>
                    </div><!-- form-group -->
                     <div class="form-group">
                     <form:label path="rpymtdatestr" cssClass="col-sm-3 control-label tooltips"  data-original-title="Date" data-toggle="tooltip" data-placement="left">Repayment Date: *</form:label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                <form:input path="rpymtdatestr" placeholder="dd/mm/yyyy" id="rpymtdatepicker" cssClass="form-control"  size="5" />
                            <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                             
                                </div>
                                <form:errors path="rpymtdatestr" cssClass="error" />
                            </div>
                    </div><!-- form-group -->
                     <div class="form-group">
                     <form:label path="paymentdatestr" cssClass="col-sm-3 control-label tooltips"  data-original-title="Date" data-toggle="tooltip" data-placement="left">Actual Payment Date: *</form:label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                <form:input path="paymentdatestr" placeholder="dd/mm/yyyy" id="paymentdatepicker" cssClass="form-control"  size="10" />
                            <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                             
                                </div>
                                <form:errors path="paymentdatestr" cssClass="error" />
                            </div>
                    </div><!-- form-group -->
                    <div class="form-group">
                        <form:label path="penalty" cssClass="col-sm-3 control-label tooltips"  data-original-title="Penalty due for late repayment" data-toggle="tooltip" data-placement="left">Penalty: *</form:label>
                            <div class="col-sm-4">
                            <form:input path="penalty" size="30" id="penalty" readonly="true"/>
                            <div class="error">
                                <form:errors path="penalty" />
                            </div>
                        </div>
                    </div><!-- form-group -->
                                <form:hidden path="instalNo"/>
                                <form:hidden path="refid"/>
                                <form:hidden path="id"/>
                               
                        </div>
                    
                    <input type="submit" class="btn btn-danger mr5" value="Save" id="save"/>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                   
                </div><!-- panel-footer -->
               <c:if test="${hprequestdetres.analysiscode1!=''&& hprequestdetres.analysiscode1!=null}">
                <div class="alert alert-info fade in nomargin">
			  <button aria-hidden="true" data-dismiss="alert" class="close" type="button">&times;</button>
			  <h5 align="center">${hprequestdetres.analysiscode1}</h5>
			
                     </div>
               </c:if>   
                
                
                
                
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
        <script src="<%=request.getContextPath()%>/resources/js/jquery.gritter.min.js"></script>
        
        <script src="<%=request.getContextPath()%>/resources/js/jquery.validate.min.js"></script>

        <script src="<%=request.getContextPath()%>/resources/js/jquery.dataTables.min.js"></script>
       <script src="<%=request.getContextPath()%>/resources/js/dataTables.bootstrap.js"></script>
        
        <script src="<%=request.getContextPath()%>/resources/js/dataTables.responsive.js"></script>
         <script src="${resourceUrl}/js/custom.js"></script>
         <script src="<%=request.getContextPath()%>/resources/js/jquery.autocomplete.min.js"></script>
        <script>
    $(document).ready(function(){
       // Date Picker
               jQuery('#rpymtdatepicker').datepicker();  
                jQuery('#paymentdatepicker').datepicker();
                $.datepicker.setDefaults({
                    dateFormat: 'dd/mm/yy'
                });
       jQuery('#select-search-hide').select2({
                    minimumResultsForSearch: 15
                });
      
      $("#paymentdatepicker").change(
           function(){
							//alert($(this).val());
                    $.getJSON('getPenalty.htm',{
                            paydate : $(this).val(),
                            principal : $("#principal").val(),
                            interest : $("#interest").val(),
                            rpymtdatestr : $("#rpymtdatepicker").val(),
                            product : $("#productcode").val(),
                            ajax : 'true'
                    }, function(data) {
                            var len = 0;
                            var html ='';
                            $("#penalty").val(data).change();  
                           
                            
                          
                    });     
            });
        
                     
            
                    
    });
    
    
</script>
    </body>


</html>
