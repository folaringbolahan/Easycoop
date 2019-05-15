<%@ include file="includes/header.jsp" %>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="index.htm">Home</a></li>
            <li>User Account Unlock</li>
        </ul>
        <h4>Unlock Account </h4>
    </div>
    <%@include file="includes/topright.jsp" %>
    
</div>
</div><!-- media -->
</div><!-- pageheader -->



<div class="contentpanel">
 
    <div class=" col-md-10">
        <table  id="data-list" class="table table-striped table-bordered responsive" >       
            <thead>
                <tr>
                    <th class=" tooltips"  data-original-title="Click to Sort" data-toggle="tooltip" data-placement="top">User Id</th>
                    <th class=" tooltips"  data-original-title="Click to Sort" data-toggle="tooltip" data-placement="top">Name</th>
                    <th>Lock Date</th>
                    <th>Locked?</th>
                    <th>Company</th>
                    <th>Branch</th>
                    <th></th>
               </tr>
            </thead>
            <tbody>
                <c:forEach var="account" items="${accountssup}" >

                    <tr>
                        <td>${account.userId}</td>    
                        <td>${account.userName}</td>
                        <td>${account.lastmodifiedstr}</td>
                        <td>${account.accountNonLocked}</td>
                        <td>${account.department}</td>
                        <td>${account.jobCode}</td>
                        <td><a href="pfaccountsunlock.htm?id=${account.id}">Unlock</a></td>
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
       
       
       });
       
  
</script>
    </body>


</html>
