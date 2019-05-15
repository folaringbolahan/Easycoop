<%@ include file="includes/header.jsp" %>  
<div class="media-body">
        <div style="float:left">
	    <ul class="breadcrumb">
		<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		<li><a href="#">Home</a></li>
		<li>Address Item Management</li>
	    </ul>
	    <h4>Manage Address Item</h4>
	</div>
	<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<script lanuage="Javascript">
	function doSubmit(frm){	
	    if(frm.addrFieldName.value==""){alert("Field Name is required"); frm.addrFieldName.focus(); return;}
	    frm.submit();
	}	  
  </script>
<div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
	       <div class="panel-body">
                 <c:set var="now" value="<%=new java.util.Date()%>" />  
		      <form:form method="post" action="saveAddrItem.htm" commandName="addressItem">
			  <div class="form-group"> 
				  <form:label path="id" cssClass="col-sm-4 control-label">ID:</form:label>  
				  <div class="col-sm-2">
				     <form:input path="id" value="${addressItem.id}" readonly="true"/>
				  </div>  
			   </div>         
			   <div class="form-group">  
				  <form:label path="addrFieldName" cssClass="col-sm-4 control-label">Field Name:</form:label> 
				  <div class="col-sm-2">
				     <form:input path="addrFieldName" value="${addressItem.addrFieldName}"/>
				  </div>  
			   </div> 
			<%--   
			   <div class="form-group">  
				  <form:label path="active" cssClass="col-sm-4 control-label">Active:</form:label>  
				  <div class="col-sm-2">
				      <form:select path="active">
					<form:option value="Y">Yes</form:option>
					<form:option value="N">No</form:option>
				      </form:select>
				  </div>  
			   </div> 
           		--%>
           		
                	   <input type="hidden" name="ACTION_ID" value="2">
                	   
			   <form:hidden path="deleted" value="N"/>
			   <form:hidden path="active" value="Y"/>

			   <form:hidden path="createdBy" value="admin"/>
			   <form:hidden path="lastModifiedBy" value="admin"/>

			   <form:hidden path="creationDate"  value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>
			   <form:hidden path="lastModificationDate" value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>

           <div class="form-group">
		    <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">UPDATE</button>
		    <button type="reset" class="btn btn-default">RESET</button>
	    </div><!-- panel-footer -->       
            
      </form:form>  
        </div>
              <c:if test="${!empty addressItems}">  
          <h4>LIST OF ADDRESS ITEM</h4>  
	     <table id="addritem-list" class="table table-striped table-bordered responsive">  
	      <tr>  
	       <th>ID</th>  
	       <th>Name</th>  
	       <%--<th>Active </th> 
	       <th>Deleted </th> --%>
	       <th>Action </th> 
	      </tr>  

	      <c:forEach items="${addressItems}" var="item">  
	       <tr>  
		<td><c:out value="${item.id}"/></td>  
		<td><c:out value="${item.addrFieldName}"/></td>  
		<%--<td><c:out value="${item.active}"/></td> 
		<td><c:out value="${item.deleted}"/></td> --%>

		<td align="center"><a href="editAddrItem.htm?id=${item.id}">Edit</a></td>  
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
				 jQuery('#addritem-list').DataTable({
                    responsive: true
                });
    })
</script> 