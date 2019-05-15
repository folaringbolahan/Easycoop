<%@ include file="includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Member Category Management</li>
    </ul>
    <h4>Member Category</h4>
</div>
<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<script lanuage="Javascript">
	function doSubmit(frm){	
	    if(frm.typeName.value==""){alert("Type Name is required"); frm.typeName.focus(); return;}
	    frm.submit();
	}	  
  </script>
<div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
	     <div class="panel-body">	      
                 <form:form method="post" action="saveMemberCategory"  commandName="memberCategory">
                      <div class="form-group">  
			  <form:label path="typeName" cssClass="col-sm-4 control-label"> Type Name *:</form:label>
			  <div class="col-sm-2">
			      <form:input path="typeName" value="${memberCategory.typeName}"/>
			      <form:errors path="typeName" cssClass="error"></form:errors>
			  </div>  
		   </div> 
		       <input type="hidden" name="ACTION_ID" value="1">
		     <form:hidden path="active" value="Y"/>
		     <form:hidden path="deleted" value="N"/>
		     
		     <form:hidden path="createdBy" value="<%=request.getRemoteUser()%>"/>
		     <form:hidden path="lastModifiedBy" value="<%=request.getRemoteUser()%>"/>

		     <form:hidden path="creationDate"  value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>
		     <form:hidden path="lastModificationDate" value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>
           <div class="form-group">
		    <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">ADD</button>
		    <button type="reset" class="btn btn-default">RESET</button>
	    </div><!-- panel-footer -->       
            
      </form:form>  
              </div>
   
      <c:if test="${!empty memberCategorys}">  
          <h4>LIST OF TYPE NAMES </h4>   
	     <table id="addr-item-type" class="table table-striped table-bordered responsive">  
	      <thead>
		      <tr>  
		       <th>ID</th>  
		       <th>Name</th>  
		       <th>Action </th> 
		      </tr>  
	      </thead>
	      <c:forEach items="${memberCategorys}" var="memcategory">  
	       <tr>  
		<td><c:out value="${memcategory.id}"/></td>  
		<td><c:out value="${memcategory.typeName}"/></td>  
		<%--<td><c:out value="${addressType.active}"/></td> 
		<td><c:out value="${addressType.deleted}"/></td> --%>

		<td><a href="editMemberCategory.htm?id=${memcategory.id}">Edit</a> &nbsp; | &nbsp; <a href="deleteMemberCategory.htm?id=${memcategory.id}">Delete</a></td>  
	       </tr>  
	      </c:forEach>  
	     </table>  
    </c:if>  
            </div><!-- col-md-6 -->
        </div>
    </div>
</div>
    <!-- contentpanel -->

<%@ include file="includes/footer.jsp" %>  
<script>
    $(document).ready(function(){
         jQuery('select').select2({
                    minimumResultsForSearch: -1
         });
	 
	 jQuery('#addr-item-type').DataTable({
                    responsive: true
         });
    })
</script>
   