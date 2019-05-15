<%@ include file="includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Manage Field</li>
    </ul>
    <h4>Extra Field </h4>
</div>
<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<script lanuage="Javascript">
	function doSubmit(frm){	
	    if(frm.description.value==""){alert("Type Name is required"); frm.typeName.focus(); return;}
	    frm.submit();
	}	  
  </script>
  <div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
	     <div class="panel-body">	      
                 <form:form method="post" action="saveManageField" commandName="memberExtraField">
                     
                 <div class="form-group"> 
			  <form:label path="companyid" data-toggle="tooltip" title="Select Cooperative" cssClass="col-sm-3 tooltips control-label">Cooperative *:</form:label>  
			  <div class="col-sm-8">
			     <form:select id="companyid" path="companyid" cssClass="width300">
				<c:forEach items="${companies}" var="item">  
				     <form:option value="${item.id}">${item.name}</form:option>
				</c:forEach>
			      </form:select>
			      <form:errors path="companyid" cssClass="error"></form:errors>
			  </div>  
		  </div> 
                            <div class="form-group">
                        <label data-toggle="tooltip"  class="col-sm-3 tooltips control-label">Description <span class="asterisk">*</span></label>
                          <div class="col-sm-7">                                           
                               <form:input path="description" id="description" cssClass="form-control" value="${memberExtraField.description}" />                                                                 
                          </div>
                    </div>
		  
                          
                   <div class="form-group">   
				  <form:label path="grouped" cssClass="col-sm-4 control-label">Select Option ?</form:label> 
				  <div class="col-sm-4">
				     <form:radiobutton path="grouped" value="Y"/>&nbsp; Yes 
				     &nbsp; &nbsp;<form:radiobutton path="grouped" value="N" checked="true"/>&nbsp; No 
				  </div>  
			   </div>
                            
                    <input type="hidden" name="ACTION_ID" value="1">
                    <input type="hidden" name="branchid" value="0">
		     <form:hidden path="active" value="Y"/>
		     <form:hidden path="deleted" value="N"/>
		     
		     <form:hidden path="createdBy" value="<%=request.getRemoteUser()%>"/>
		     <form:hidden path="lastModifiedBy" value="<%=request.getRemoteUser()%>"/>

		     <form:hidden path="creationDate"  value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>
		     <form:hidden path="lastModificationDate" value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>

		  
           <div class="form-group">
		    <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">SAVE</button>
		    <button type="reset" class="btn btn-default">RESET</button>
	    </div><!-- panel-footer -->       
            
      </form:form>  
      </div>
     <c:if test="${!empty allMemberExtraFields}">  
          <h4>LIST OF FIELDS</h4>  
	     <table id="addrtype-list" class="table table-striped table-bordered responsive">  
	      <thead>
	           <tr>  
		       <th>ID</th>  
		       <th>Grouped</th>  
		       <th>Field Name</th>
                       <th>Company Name</th>
                       <th>Action</th> 
                       
	           </tr>  
	      </thead>
	      <c:forEach items="${allMemberExtraFields}" var="membExtFlds">  
	       <tr>  
		<td><c:out value="${membExtFlds.id}"/></td>  
		<td><c:out value="${membExtFlds.grouped}"/></td>  
                <td><c:out value="${membExtFlds.description}"/></td> 
                 <td><c:out value="${membExtFlds.companyName}"/></td> 
                <td><a href="editExtraField.htm?id=${membExtFlds.id}">Edit</a> &nbsp; | &nbsp; <a href="deleteExtraField.htm?id=${membExtFlds.id}">Delete</a> </td>  
		<%--<td><c:out value="${addressType.active}"/></td> 
		<td><c:out value="${addressType.deleted}"/></td> --%>

		
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
   