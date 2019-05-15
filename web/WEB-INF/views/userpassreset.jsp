<%-- 
    Document   : userpassreset
    Created on : Oct 11, 2016, 4:13:48 PM
    Author     : Olakunle Awotunbo
--%>

   <%@ include file="includes/header.jsp" %>  
    <div class="media-body">
         <div style="float:left">
	     <ul class="breadcrumb">
		 <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		 <li><a href="#">Home</a></li>
		 <li>User Management</li>
	     </ul>
	     <h4>Manage User</h4>
	 </div>
	<%@include file="includes/topright.jsp" %>
 </div>
 
 
 </div><!-- media -->
</div><!-- pageheader -->

<div class="contentpanel">

    <div class="row col-md-12">
        <!-- CONTENT GOES HERE -->  
        <!-- <div class="panel-body">-->
            <!-- form starts -->
            <form:form method="post" action="userpassresetp.htm" modelAttribute="company">  
                <div class="panel-body">

                    <div class="row col-md-8">
                        
                     <div class="form-group">   
                            <form:label path="id" cssClass="col-sm-3 control-label">Cooperative:</form:label> 
                                <div class="col-sm-8">
                                <form:select id="id" path="id" cssClass="width300" >
                                    <c:forEach items="${companies}" var="item">  
                                        <form:option value="${item.id}">${item.name}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>  
                        </div>     
                      </div>  
                     <div class="row col-md-8">       
                  
                            <input type="submit" class="btn btn-danger mr5" value="Go"/>
                     </div>
                      
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
                    <th>User Name</th>
                    <th>UserId</th>
                    <th>Email</th>
                    <th>Access  Level</th>
                    <th>Login Attempts</th>
                    <th>Enabled</th>
                    <th>Account Non Locked</th>
                    <th>Must Change Pass</th>
                    <th>Action</th>

                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${userList}" varStatus="status">
                
                    <tr>
                        <td>${status.index + 1}</td>
                        <td>${item.username}</td>    
                        <td>${item.userId}</td>
                        <td>${item.email}</td>
                        <td>${item.groupId}</td>
                        <td>${item.loginAttempts}</td>
                        <td>${item.enabled}</td>
                        <td>${item.accountNonLocked}</td>
                        <td>${item.mustChangePass}</td>
                        <td align="center">
                            <font color="red">
                            <a href="doPasswordReset.htm?id=${item.id}">RESET</a>                                                      
                            </font>
                             <button class="btn btn-danger mr5" id="resetpass" name="action" onClick="javascript:form.action='<%=request.getContextPath()%>/doPasswordReset.htm?id=${item.id}'" value="RESET">RESET</button> 
                              
                        
                        </td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>
    </div><!-- col-md-10 -->


</div>

<%@ include file="includes/footer.jsp" %>
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
       
    
    }
    )
</script>


