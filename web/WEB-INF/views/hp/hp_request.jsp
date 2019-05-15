<%@ include file="../includes/header.jsp" %>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Hire Purchase Request Setup</li>
        </ul>
        <h4>Hire Purchase Request </h4>
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
            <form:form method="post" action="hp_requestadd.htm" modelAttribute="hprequestdet">  
                <div class="panel-body">


                    
                      
                    <div class="form-group">
                        <form:label path="memberno" cssClass="col-sm-4 control-label">Member  :*</form:label>
                            <div class="col-sm-4">
                            <form:select path="memberno" cssClass="width300p" id="select-search-hide" >
                                <form:option value="" label="Select Member"/>
                                <form:options  items="${members}"
                                         itemValue="memberNo" itemLabel="names"/>
                            </form:select>
                        </div>  
                        </div>
                     
                     <div class="form-group">
                        <form:label path="product" cssClass="col-sm-4 control-label tooltips"  data-original-title="Hire Purchase product" data-toggle="tooltip" data-placement="left">Product: *</form:label>
                            <div class="col-sm-4">
                           <form:select path="product" cssClass="width100p" id="product" >
                                <form:option value="" label="Choose Product" />
                                <form:options  items="${hpproducts}"
                                         itemValue="code" itemLabel="name"/>
                            </form:select> 
                            
                          <!--   <select  class="width100p" id="product" >
                             <option value="">Choose Product</option>   
                            <c:forEach  var="hpproduct1" items="${hpproducts}" varStatus="status">
                                <option value="${hpproduct1.code}">${hpproduct1.name}</option>
                            </c:forEach>
                            </select>-->
                            <form:errors path="product" cssClass="error" />
                        </div>
                    </div><!-- form-group -->   
                     <div class="form-group">
                     <form:label path="txndatestr" cssClass="col-sm-4 control-label tooltips"  data-original-title="Date" data-toggle="tooltip" data-placement="left">Transaction Date: *</form:label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                <form:input path="txndatestr" placeholder="dd/mm/yyyy" id="startdatepicker" cssClass="form-control"  size="10" />
                            <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                             
                                </div>
                                <form:errors path="txndatestr" cssClass="error" />
                            </div>
                    </div><!-- form-group -->
                    <div class="form-group">
                        <form:label path="invref" cssClass="col-sm-4 control-label tooltips"  data-original-title="Invoice Document Reference" data-toggle="tooltip" data-placement="left">Invoice Reference: </form:label>
                            <div class="col-sm-6">
                            <form:input path="invref" size="30" id="invref"/>
                            <div class="error">
                                <form:errors path="invref" />
                            </div>
                        </div>
                    </div><!-- form-group -->
                   
                    <div class="form-group">
                        <form:label path="cashprice" cssClass="col-sm-4 control-label tooltips"  data-original-title="Cash Price" data-toggle="tooltip" data-placement="left">Cash Price: *</form:label>
                            <div class="col-sm-6">
                            <form:input path="cashprice" size="30" id="cashprice"/>
                            <div class="error">
                                <form:errors path="cashprice" />
                            </div>
                        </div>
                    </div><!-- form-group -->
                    <div class="form-group">
                        <form:label path="downpaymentamount" cssClass="col-sm-4 control-label tooltips"  data-original-title="Down payment" data-toggle="tooltip" data-placement="left">Down Payment: *</form:label>
                            <div class="col-sm-6">
                            <form:input path="downpaymentamount" size="30" id="downpaymentamount"/>
                            <div class="error">
                                <form:errors path="downpaymentamount" />
                            </div>
                        </div>
                    </div><!-- form-group -->
                    <div class="form-group">
                        <form:label path="interestrate" cssClass="col-sm-4 control-label tooltips"  data-original-title="Interest rate" data-toggle="tooltip" data-placement="left">Interest Rate: *</form:label>
                            <div class="col-sm-6">
                            <form:input path="interestrate" size="30" id="interestrate"/>
                            <div class="error">
                                <form:errors path="interestrate" />
                            </div>
                        </div>
                    </div><!-- form-group -->
                    <div class="form-group">
                        <form:label path="repaymentperiodinmonths" cssClass="col-sm-4 control-label tooltips"  data-original-title="Repayment period in months" data-toggle="tooltip" data-placement="left">Repayment Period(months): *</form:label>
                            <div class="col-sm-6">
                            <form:input path="repaymentperiodinmonths" size="30" id="repaymentperiodinmonths"/>
                            <div class="error">
                                <form:errors path="repaymentperiodinmonths" />
                            </div>
                        </div>
                    </div><!-- form-group -->
                     <div class="form-group">
                        <form:label path="repaymentfrequency" cssClass="col-sm-4 control-label tooltips"  data-original-title="Repayment frequency" data-toggle="tooltip" data-placement="left">Repayment Frequency: *</form:label>
                            <div class="col-sm-4">
                            <form:select path="repaymentfrequency" cssClass="width100p" >
                                <form:option value="" label="Choose frequency" />
                                <form:options  items="${repayfrequencys}"
                                         itemValue="code" itemLabel="description"/>
                            </form:select>
                            <form:errors path="repaymentfrequency" cssClass="error" />
                        </div>
                    </div><!-- form-group -->
                     <div class="form-group">
                        <form:label path="interestcalcmtd" cssClass="col-sm-4 control-label tooltips"  data-original-title="Interest calculation method" data-toggle="tooltip" data-placement="left">Interest Calculation Method: *</form:label>
                            <div class="col-sm-4">
                            <form:select path="interestcalcmtd" cssClass="width200p" id="interestcalcmtd" >
                                <form:option value="" label="Choose method" />
                                <form:options  items="${intcalcmtds}"
                                         itemValue="code" itemLabel="description"/>
                            </form:select>
                            <form:errors path="interestcalcmtd" cssClass="error" />
                        </div>
                    </div><!-- form-group -->
                    <div class="form-group" id="interestamt-div">
                        <form:label path="interestamt" cssClass="col-sm-4 control-label tooltips"  data-original-title="Interest payable" data-toggle="tooltip" data-placement="left">Interest Payable: *</form:label>
                            <div class="col-sm-6">
                            <form:input path="interestamt" size="30" id="interestamt" readonly="true"/>
                            <div class="error">
                                <form:errors path="interestamt" />
                            </div>
                        </div>
                    </div><!-- form-group -->
                    <div class="form-group" id="hpprice-div">
                        <form:label path="hpprice" cssClass="col-sm-4 control-label tooltips"  data-original-title="HP Price" data-toggle="tooltip" data-placement="left">HP Price: *</form:label>
                            <div class="col-sm-6">
                            <form:input path="hpprice" size="30" id="hpprice" readonly="true"/>
                            <div class="error">
                                <form:errors path="hpprice" />
                            </div>
                        </div>
                    </div><!-- form-group -->
                   
                    </div><!-- panel-body -->
                <div>
                    <input type="submit" class="btn btn-danger mr5" value="Save" id="save"/>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                   
                </div><!-- panel-footer -->
               <c:if test="${hprequestdet.analysiscode1!=''}">
                <div class="alert alert-info fade in nomargin">
			  <button aria-hidden="true" data-dismiss="alert" class="close" type="button">&times;</button>
			  <h5 align="center">${hprequestdet.analysiscode1}</h5>
			
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
        
        jQuery("#interestamt-div").css("display", "none");
        jQuery("#hpprice-div").css("display", "none");
        
        jQuery('#interestcalcmtd').change(function(){
              if ($(this).val()==='ACE') {
                  jQuery("#hpprice-div").css("display", "block");
                  jQuery('#hpprice').prop('readonly', false);
              }
              else
              {   
                  jQuery("#hpprice-div").css("display", "none");
                  jQuery('#hpprice').prop('readonly', true); 
              } 
               return false;
              });  
        
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
       
       jQuery("#memberNo").select2();
       
        
       $("select#product").change(
           function(){
							//alert($(this).val());
                    $.getJSON('getProddet.htm',{
                            prodcode : $(this).val(),
                            ajax : 'true'
                    }, function(data) {
                            var len = 0;
                            var html ='';
                           $("#repaymentperiodinmonths").val(data.loanDuration).change();  
                           $("#interestrate").val(data.interestRate).change();
                           $("#interestcalcmtd").val('').change();  
                           if (data.loanTypeCode!=null&& data.loanTypeCode!='') {
                              $("#interestcalcmtd").val(data.loanTypeCode).change();
                           }
                    });     
            });              
            
                    
    });
    
    
</script>
    </body>


</html>
