<%-- 
    Document   : pendingBatchValidation
    Created on : Nov 30, 2015, 12:07:35 AM
    Author     : baydel200
--%>

<%@ include file="../includes/header.jsp" %>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>${title}</li>
        </ul>
        <h4></h4>
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
                    <th>Coop ID</th>
                    <th>First Name</th>
                    <th>Surname</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Type</th>
                    <th>Gender</th>
                     <th>Error</th>
                    <th>Cont. Amount</th>
                    
                </tr>
            </thead>
            <tbody>
                <c:forEach var="mem" items="${listMembers}" >
                    <tr>
                        <td >${mem.memberCompId}</td>    
                        <td>${mem.firstName} </td>
                        <td>${mem.surname} </td>
                        <td>${mem.emailAdd1} </td>
                        <td>${mem.phoneNo1}</td>
                        <td>${mem.memberTypeId} </td>
                        <td>${mem.gender}</td>
                        <td>${mem.valError}</td>
                        <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${mem.contribution}" /></td> 
                        
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

