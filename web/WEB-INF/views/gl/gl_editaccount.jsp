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
 
    <div class=" col-md-10">
        <table  id="data-list" class="table table-striped table-bordered responsive" >       
            <thead>
                <tr>
                    <th class=" tooltips"  data-original-title="Click to Sort" data-toggle="tooltip" data-placement="top">Account no.</th>
                    <th class=" tooltips"  data-original-title="Click to Sort" data-toggle="tooltip" data-placement="top">Name</th>
                    <th>Group</th>
                    <th>Account Structure</th>
                    <th>Date Opened</th>
                    <th>Control Account?</th>
                    <th></th>
                    <th></th>

                </tr>
            </thead>
            <tbody>
                <c:forEach var="account" items="${accounts}" >

                    <tr>
                        <td>${account.accountNo}</td>    
                        <td>${account.name}</td>
                        <td>${account.analysiscode1}</td>
                        <td>${account.analysiscode2}</td>
                        <td>${account.dateOpenedstr}</td>
                        <td> ${account.controlAccount}
                        
                        </td>
                        <td><a href="gl_pfeditaccount.htm?id=${account.accountNo}">Edit</a></td>
                        <td><a href="gl_remaccount.htm?id=${account.accountNo}" onclick="return confirm('Delete record with code - ${account.accountNo} Continue?')" type="submit" class="buttons" value="Go">Delete</a></td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>
    </div><!-- col-md-10 -->
    
    

    


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
