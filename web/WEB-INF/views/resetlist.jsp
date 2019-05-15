<%-- 
    Document   : resetlist
    Created on : Oct 18, 2016, 1:16:38 PM
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
        <h4>User Reset</h4>
    </div>
    <%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->


<div class="contentpanel">
    <div class="row">
        <!--  <div class="col-md-8">-->  
        <!-- CONTENT GOES HERE -->  
        <!--   <div class="col-md-10">-->
        <div class="panel panel-primary-head">
            
         <form:form method="post" action="userpassresetp" modelAttribute="user"> 
          <div class="col-md-15">
            <table id="upload-list" class="table table-striped table-bordered responsive">
                <thead class="">
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
                             <button class="btn btn-danger mr5" id="resetpass" name="action" onClick="javascript:form.action='<%=request.getContextPath()%>/doPasswordReset.htm?id=${item.id}'" value="RESET">RESET</button> 
                       </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>             
            </div>
                 </form:form>  
            
            
        </div>
    </div><!-- col-md-6 -->
</div>
</div>

<!-- </div>
</div>-->

<!-- modal ends -->
</div><!-- contentpanel -->

<%@ include file="includes/footer.jsp" %>  
