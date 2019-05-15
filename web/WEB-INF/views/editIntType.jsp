<%@ include file="includes/header.jsp" %>  
<div class="media-body">
	 <div style="float:left">
	    <ul class="breadcrumb">
		<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		<li><a href="#">Home</a></li>
		<li>Interest Type Management</li>
	    </ul>
	    <h4>Manage Interest Type</h4>
	</div>
	<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<div class="contentpanel">
    <div class="row">
        <div class="col-md-10">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
	       <div class="panel-body">
		<form:form method="post" action="saveIntType.htm" commandName="interestT">
		  
		  <div class="form-group">
		      <form:label path="id" cssClass="col-sm-3 control-label">ID:</form:label>  
			<div class="col-sm-8">
			   <form:input path="id" value="${interestType.id}" readonly="true"/>
		       </div>   
		  </div>
		  
		  <div class="form-group"> 
			  <form:label path="typeName" cssClass="col-sm-3 control-label">Name:</form:label></td>  
			  <div class="col-sm-8">
			     <form:input path="typeName" value="${interestType.typeName}"/>
			     <form:errors path="typeName" cssClass="error"></form:errors>
			  </div>
		  </div> 
		  
		   <div class="form-group">
			  <form:label path="active"  cssClass="col-sm-3 control-label">Active:</form:label>
			  <div class="col-sm-8">
			      <form:select path="active">
				<form:option value="Y">Yes</form:option>
				<form:option value="N">No</form:option>
			      </form:select>
			  </div>  
		   </div>
			   
                   <input type="hidden" name="ACTION_ID" value="2">
		   
		   <form:hidden path="deleted" value="N"/> 
		   <form:hidden path="createdBy" value="admin"/>
		   <form:hidden path="lastModifiedBy" value="admin"/>

		   <form:hidden path="creationDate"  value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>
		   <form:hidden path="lastModificationDate" value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>

		  <div class="form-group">
		    <button class="btn btn-danger mr5" type="submit">ADD INTEREST TYPE</button>
		    <button type="reset" class="btn btn-default">RESET</button>
                  </div>   
      </form:form>  
      </div>
   
      <c:if test="${!empty interestTypes}">  
          <h4>LIST OF INTEREST TYPES</h4>  
	     <table id="interesttype-list" align="left" border="1">  
	      <tr>  
	       <th>ID</th>  
	       <th>Name</th>  
	       <th>Active </th> 
	       <th>Deleted </th> 
	       <th>Action </th> 
	      </tr>  

	      <c:forEach items="${interestTypes}" var="interestT">  
	       <tr>  
		<td><c:out value="${interestT.id}"/></td>  
		<td><c:out value="${interestT.typeName}"/></td>  
		<td><c:out value="${interestT.active}"/></td> 
		<td><c:out value="${interestT.deleted}"/></td> 

		<td align="center"><a href="editIntType.htm?id=${interestT.id}">Edit</a></td>  
	       </tr>  
	      </c:forEach>  
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
				 jQuery('#interesttype-list').DataTable({
                    responsive: true
                });
    })
</script>
   