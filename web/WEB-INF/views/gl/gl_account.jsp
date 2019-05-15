<%@ include file="../includes/header.jsp" %>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Account Setup</li>
        </ul>
        <h4>Add Account </h4>
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
            <form:form method="post" action="gl_accountadd.htm" modelAttribute="accountdet">  
                <div class="panel-body">


                    <div class="form-group">
                        <form:label path="accountNo" cssClass="col-sm-4 control-label tooltips"  data-original-title="Account Number" data-toggle="tooltip" data-placement="left">Account no: *</form:label>
                            <div class="col-sm-6">
                            <form:input path="accountNo" size="30" id="accountNo"/>
                            <div class="error">
                                <form:errors path="accountNo" />
                            </div>
                        </div>
                    </div><!-- form-group -->

                    <div class="form-group">
                        <form:label path="name" cssClass="col-sm-4 control-label tooltips"  data-original-title="Title of Account" data-toggle="tooltip" data-placement="left">Name: *</form:label>
                            <div class="col-sm-6">
                            <form:input path="name" size="50" />
                            <div class="error">
                                <form:errors path="name" />
                            </div>
                        </div>
                    </div><!-- form-group -->
                    
                    
                    <div class="form-group">
                        <form:label path="acGroup" cssClass="col-sm-4 control-label tooltips"  data-original-title="Account Group in Statements" data-toggle="tooltip" data-placement="left">Account Group: *</form:label>
                            <div class="col-sm-8">
                            <form:select path="acGroup" cssClass="width100p" id="select-search-hide">
                                <form:option value="" label="Choose Group" />
                                <form:options  items="${accountgroups}"
                                         itemValue="acGrpId" itemLabel="description"/>
                            </form:select>
                            <form:errors path="acGroup" cssClass="error" />
                        </div>
                    </div><!-- form-group -->
                    <div class="form-group">
                        <form:label path="acStruct" cssClass="col-sm-4 control-label tooltips"  data-original-title="Account No. Structure" data-toggle="tooltip" data-placement="left">Account Structure: *</form:label>
                            <div class="col-sm-4">
                            <form:select path="acStruct" id="acStruct">
                                <form:option value="" label="Choose Account Structure" />
                                <form:options  items="${accountstructs}" 
                                               itemValue="structurecode" itemLabel="description"/>
                                </form:select>
                            <form:errors path="acStruct" cssClass="error" />

                        </div>
                    </div><!-- form-group -->

                    <div class="form-group">
                     <form:label path="dateOpenedstr" cssClass="col-sm-4 control-label tooltips"  data-original-title="Date" data-toggle="tooltip" data-placement="left">Date Opened: *</form:label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                <form:input path="dateOpenedstr" placeholder="dd/mm/yyyy" id="startdatepicker" cssClass="form-control"  size="10" />
                            <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                             
                                </div>
                                <form:errors path="dateOpenedstr" cssClass="error" />
                            </div>
                    </div><!-- form-group -->
                    
                    <div class="form-group">
                            <form:label path="controlAccount" cssClass="col-sm-4 control-label tooltips"  data-original-title="Control Accounts aggregate subaccounts" data-toggle="tooltip" data-placement="left">Control Account?:</form:label>
                                <div class="col-sm-8">
                                <form:checkbox path="controlAccount" value="1"/> 
                                <form:errors path="controlAccount" cssClass="error" />
                            </div>
                        </div> 
                       <%--
                         <div class="form-group">
                        <form:label path="controlAccountno" cssClass="col-sm-4 control-label tooltips"  data-original-title="Control Account that this legder account reports to as subaccount (if applicable)" data-toggle="tooltip" data-placement="left">Control Account No. :</form:label>
                            <div class="col-sm-8">
                            <form:select path="controlAccountno"  cssClass="width100p" id="controlAccountno" >
                                <form:option value="" label="Select Control Account"/>
                                <form:options  items="${ctrlaccs}"
                                         itemValue="accountNo" itemLabel="name"/>
                            </form:select>
                                <form:errors path="controlAccountno" cssClass="error" />
                        </div>   
                        
                       </div>
                         --%>   
                        <!-- form-group -->     
                        <div class="form-group">
                            <form:label path="balanceType" cssClass="col-sm-4 control-label">Balance Type:</form:label>
                                <div class="col-sm-8">
                                <form:radiobutton path="balanceType" value="C"/> &nbsp;&nbsp;Credit Balance
                                <form:radiobutton path="balanceType" value="D"/> &nbsp;&nbsp;Debit Balance
                                </div>
                        </div>
                        <div class="form-group">
                            <form:label path="activeorclosed" cssClass="col-sm-4 control-label">Status:</form:label>
                                <div class="col-sm-8">
                                <form:radiobutton path="activeorclosed" value="a"/>&nbsp;&nbsp;Active
                                <form:radiobutton path="activeorclosed" value="c"/>&nbsp;&nbsp;Closed
                                <form:checkbox path="blocked" value="1"/> &nbsp;&nbsp;Blocked
                                </div>
                        </div>        

                    </div><!-- panel-body -->
                <div>
                    <input type="submit" class="btn btn-danger mr5" value="Save" id="save"/>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="gl_editaccount.htm" class="btn btn-danger mr5" >Manage Accounts</a>
                </div><!-- panel-footer -->

            </form:form>  
              <c:if test="${lastretmess !=''}">
                <div class="alert alert-info fade in nomargin">
			  <button aria-hidden="true" data-dismiss="alert" class="close" type="button">&times;</button>
			  <h5 align="center">${lastretmess}</h5>
			
                     </div>
               </c:if>       
                  
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
        
                
   jQuery('#acStruct').change(function(){
                      
              $.getJSON('getAccseg.htm',{
                            accno : $("#accountNo").val(),
                            accstr : $(this).val(),
                            ajax : 'true'
                    }, function(data) {
                            $.gritter.add({
                            title: 'Verify Account Segments!',
                            text: data,
                            sticky: false,
                            time: ''
                           });
                          
                    });     
                    
                     
                     return false;
              });  
              
              
      
           
        
                jQuery('#startdatepicker').datepicker();  
                jQuery('#enddatepicker').datepicker();
                $.datepicker.setDefaults({
                    dateFormat: 'dd/mm/yy'
                });
       jQuery('#select-search-hide').select2({
                    minimumResultsForSearch: 15
                }); 
                jQuery('#acStruct').select2({
                    minimumResultsForSearch: -1
                });
                jQuery('#controlAccountno').select2({
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
