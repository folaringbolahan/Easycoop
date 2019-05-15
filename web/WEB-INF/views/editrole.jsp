<%@ include file="includes/header.jsp" %>  

<div class="media-body">
    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Role Management</li>
    </ul>
    <h4>Manage User Roles</h4>
</div>
<%@include file="includes/topright.jsp" %>
</div><!-- media -->
</div><!-- pageheader -->

<div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
	    <div class="panel-body">
         <c:set var="now" value="<%=new java.util.Date()%>" />
         <form:form method="post" action="saveRole.htm" commandName="userRole">		
	     <div class="form-group">
		<form:label path="id" cssClass="col-sm-4 control-label">ID:</form:label>
		<div class="col-sm-2">
		      <form:input path="id" value="${userRole.id}" readonly="true"/>
		</div> 
	      </div>	
	      <div class="form-group">
		     <form:label path="roleCode" cssClass="col-sm-4 control-label">Role Code:</form:label>  
		     <div class="col-sm-2">
		       <form:input path="roleCode" value="${userRole.roleCode}"/>
		     </div>
	      </div>	      
	      <div class="form-group">
		     <form:label path="roleName" cssClass="col-sm-4 control-label">Role Name:</form:label>  
		     <div class="col-sm-2">
		       <form:input path="roleName" value="${userRole.roleName}"/>
		     </div>
	      </div>
	<%--
	      <div class="form-group">
		  <form:label path="groupId" cssClass="col-sm-4 control-label">User Group</form:label>
		  <div class="col-sm-2">
		     <form:select path="groupId">
		       <c:forEach items="${userGroups}" var="obj">  
			    <form:option value="${obj.id}">${obj.groupName}</form:option>
			</c:forEach>
		      </form:select>
		  </div>  
	      </div>
   	--%>
   	
              <div class="form-group">
   		  <form:label path="active" cssClass="col-sm-4 control-label">Active:</form:label>
   		  <div class="col-sm-2">
   		     <form:select path="active">
   		        <form:option value="-" selected="selected"> -- select -- </form:option>
   	                <form:option value="Y">Yes</form:option>
   	                <form:option value="N">No</form:option>
   	            </form:select>
   	          </div>  
   	      </div>
   	     
              <div class="form-group">
   		  <form:label path="deleted" cssClass="col-sm-4 control-label">Deleted (Y or N)?:</form:label>
   		  <div class="col-sm-2">
   		     <form:select path="deleted">
   		        <form:option value="-" selected="selected"> -- select -- </form:option>
   	                <form:option value="Y">Y</form:option>
   	                <form:option value="N">N</form:option>
   	              </form:select>
   	          </div>  
	     </div>
	    
	 <%--  
           <form:hidden path="active" value="Y"/>
           <form:hidden path="deleted" value="N"/>  
         --%>
         
           <input type="hidden" name="ACTION_ID" value="2">
            <div class="form-group">
	    <button class="btn btn-danger mr5" type="submit">UPDATE ROLE</button>
	    <button type="reset" class="btn btn-default">RESET</button>
	</div><!-- panel-footer -->       
            
      </form:form>  
        </div>
      <c:if test="${!empty userRoles}">  
          <h4>LIST OF USER ROLES</h4>  
	     <table id="role-list" class="table table-striped table-bordered responsive">  
		 <thead>
		      <tr>  
		       <th>ID</th>  
		       <th>Code</th>  
		       <th>Name</th>  
		       <th>Active </th> 
		       <th>Deleted </th> 
		       <th>Action </th>  
		      </tr>  
		</thead>
		<tbody>
		    <c:forEach items="${userRoles}" var="role">  
		       <tr>  
			<td><c:out value="${role.id}"/></td>  
			<td><c:out value="${role.roleCode}"/></td>  
			<td><c:out value="${role.roleName}"/></td>  
			<td><c:out value="${role.active}"/></td> 
			<td><c:out value="${role.deleted}"/></td> 

			<td align="center"><a href="editRole.htm?id=${role.id}">Edit</a></td>  
		       </tr>  
		      </c:forEach>  
		  </tbody>
	     </table>  
    </c:if>  
            </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->

<%@ include file="includes/footer.jsp" %>  
<script>
    $(document).ready(function(){
         jQuery('select').select2({
                    minimumResultsForSearch: -1
                });
				 jQuery('#role-list').DataTable({
                    responsive: true
                });
    })
</script>
   