<%@ include file="../includes/header.jsp" %>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Account Setup</li>
        </ul>
        <h4>Edit Account </h4>
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
            <form:form method="post" action="gl_pfeditaccount.htm" modelAttribute="accountdet">  
                <div class="panel-body" id="panbod">


                    <div class="form-group">
                        <form:label path="accountNo" cssClass="col-sm-4 control-label">Account no: </form:label>
                            <div class="col-sm-6">
                          <!--  <//form:select path="accountNo" cssClass="width100p" id="accountNo" >
                                <//form:option value="" label="Choose Account" />
                                <//form:options  items="${accounts}"
                                         itemValue="accountNo" itemLabel="name"/>
                            <///form:select> -->
                            <select  class="width100p" id="accountNo" >
                             <option value="">Choose Account</option>   
                            <c:forEach  var="account1" items="${accounts}" varStatus="status" >
                                <option value="${account1.accountNo}">${account1.name}</option>
                            </c:forEach>
                            </select>
                            
                            <div class="error">
                                <form:errors path="accountNo" />
                            </div>
                        </div>
                    </div><!-- form-group -->

                    <div class="form-group">
                        <form:label path="name" cssClass="col-sm-4 control-label">Name: </form:label>
                            <div class="col-sm-6">
                             <div id="nameinfo">   
                            <form:input path="name" size="50" id="name" />
                            <div class="error">
                                <form:errors path="name" />
                            </div>
                             </div>
                        </div>
                    </div><!-- form-group -->
                    
                    
                    <div class="form-group">
                        <form:label path="acGroup" cssClass="col-sm-4 control-label">Account Group: </form:label>
                            <div class="col-sm-6">
                                 <div id="acGroupinfo"> 
                            <form:select path="acGroup" cssClass="width100p" id="acGroup"  >
                                <form:option value="" label="Choose Group" />
                                <form:options  items="${accountgroups}"
                                         itemValue="acGrpId" itemLabel="description"/>
                            </form:select>
                            <form:errors path="acGroup" cssClass="error" />
                                 </div>
                        </div>
                    </div><!-- form-group -->
                    <div class="form-group">
                        <form:label path="acStruct" cssClass="col-sm-4 control-label">Account Structure: </form:label>
                            <div class="col-sm-4">
                            <form:select path="acStruct" id="acStruct" disabled="true">
                                <form:option value="" label="Choose Account Structure" />
                                <form:options  items="${accountstructs}" 
                                               itemValue="structurecode" itemLabel="description"/>
                                </form:select>
                            <form:errors path="acStruct" cssClass="error" />

                        </div>
                    </div><!-- form-group -->

                    <div class="form-group">
                     <form:label path="dateOpenedstr" cssClass="col-sm-4 control-label">Date Opened: </form:label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <div id="startdatepickerinfo">
                                    <form:input path="dateOpenedstr" placeholder="dd/mm/yyyy" id="startdatepicker" cssClass="form-control"  size="10" readonly="true" disabled="true" />
                            <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                                 </div>
                                </div>
                                <form:errors path="dateOpenedstr" cssClass="error" />
                            </div>
                    </div><!-- form-group -->
                    
                    <div class="form-group">
                            <form:label path="controlAccount" cssClass="col-sm-4 control-label">Control Account?:</form:label>
                                <div class="col-sm-2">
                                <form:checkbox path="controlAccount" value="1"/> 
                            </div>
                        </div> 
                        <div class="form-group">
                            <form:label path="balanceType" cssClass="col-sm-4 control-label">Balance Type:</form:label>
                                <div class="col-sm-8">
                                    <div id="balanceTypeinfo">
                                <form:radiobutton path="balanceType" value="C"/> &nbsp;&nbsp;Credit Balance
                                <form:radiobutton path="balanceType" value="D"/> &nbsp;&nbsp;Debit Balance
                                </div>
                                </div>
                        </div>
                        <div class="form-group">
                            <form:label path="activeorclosed" cssClass="col-sm-4 control-label">Status:</form:label>
                                <div class="col-sm-8">
                                    <div id="activeorclosedinfo">
                                <form:radiobutton path="activeorclosed" value="a"/>&nbsp;&nbsp;Active
                                <form:radiobutton path="activeorclosed" value="c"/>&nbsp;&nbsp;Closed
                                <form:checkbox path="blocked" value="1"/> &nbsp;&nbsp;Blocked
                                </div>
                                </div>
                        </div>        

                    
                </div><!-- panel-body -->
                <div>
                    <input type="submit" class="btn btn-danger mr5" value="Save"/>
                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="gl_removeaccount.htm" class="btn btn-danger mr5" onclick="return confirm('Delete record with code - ${accountNo} Continue?')" type="submit">Delete Account</a>
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
      jQuery('#data-list').DataTable({
                    responsive: true
                });
       
       jQuery("#accountNo").select2();
       
       
       
           $("select#accountNo").change(
           function(){
							//alert($(this).val());
                    $.getJSON('getAccdet.htm',{
                            accno : $(this).val(),
                            ajax : 'true'
                    }, function(data) {
                            var len = 0;
                            var html ='';

                           //  html = '<input type="text" value="' + data.name + '" id="name" />';
                           
                           // $('#nameinfo').html(html);
                           $("#name").val(data.name).change();  
                            $("#acGroup").val('').change();  
                            if (data.acGroup!=null&& data.acGroup!='') {
                              $("#acGroup").val(data.acGroup).change();
                            }
                             $("#acStruct").val('').change();  
                            if (data.acStruct!=null&& data.acStruct!='') {
                              $("#acStruct").val(data.acStruct).change();
                            }
                            //html = '<input type="text" value="' + data.dateOpenedstr + '" id="startdatepicker" />';
                            //$('#startdatepickerinfo').html(html);
                            $('#startdatepicker').val(data.dateOpenedstr).change();
                            if (data.balanceType=='C') {
                                $("#balanceType1").attr("checked", true);
                            }
                            if (data.balanceType=='D') 
                            {
                                $("#balanceType2").attr("checked", true);
                            }  
                            if (data.activeorclosed=='c') {
                              $("#activeorclosed2").attr("checked", true);
                            }
                            if (data.activeorclosed=='a') {
                              $("#activeorclosed1").attr("checked", true);
                            }
                            $("#controlAccount1").attr("checked", false);
                            if (data.controlAccount==true) {
                              $("#controlAccount1").attr("checked", true);
                            }
                            if (data.blocked==true) {
                              $("#blocked1").attr("checked", true);
                            }
                            if (data.blocked==false) {
                              $("#blocked1").attr("checked", false);
                            }
                          
                    });     
            });
       });
       
  
</script>
    </body>


</html>
