<%@ include file="includes/header.jsp" %>  
<div class="media-body">
        <div style="float:left">
	    <ul class="breadcrumb">
		<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		<li><a href="#">Home</a></li>
		<li>Extra Field Setup</li>
	    </ul>
	    <h4>Field Setup</h4>
	</div>
	<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<script lanuage="Javascript">
	function doSubmit(frm){	
	 if(frm.fieldOption.value==""){alert("Field is required"); frm.fieldOption.focus(); return;}
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
                 <form:form method="post" action="saveMemberExtraFieldGrp.htm" commandName="memberExtraFieldGrp">
		  <div class="form-group"> 
			  <form:label path="groupid" data-toggle="tooltip" title="Select field group" cssClass="col-sm-3 tooltips control-label">Extra Fields *</form:label>  
			  <div class="col-sm-8">
                           <select id="groupid" path="groupid" name="groupid"  cssClass="width300" >
			     
				<c:forEach items="${memberExtraFields}" var="item">  
				     <option value="${item.id}">${item.description}------>${item.companyName}</option>
                                     
				</c:forEach>
			      </select>
			      <form:errors path="groupid" cssClass="error"></form:errors>
			  </div>  
		  </div> 
     
                  <div class="form-group">
                        <label data-toggle="tooltip"  class="col-sm-3 tooltips control-label">Description <span class="asterisk">*</span></label>
                          <div class="col-sm-7">                                           
                               <form:input path="fieldOption" id="fieldOption" cssClass="form-control" value="${memberExtraFieldGrp.fieldOption}" />                                                                 
                          </div>
                    </div>
          
                
               
           <div class="form-group">
		    <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">SAVE</button>
		    <button type="reset" class="btn btn-default">RESET</button>
	    </div><!-- panel-footer -->       
            
      </form:form>  
        </div>
        
      <c:if test="${!empty memberExtraFieldGrps}">  
          <h4>LIST OF FIELDS</h4>  
	     <table id="addrtype-list" class="table table-striped table-bordered responsive">  
	      <thead>
	           <tr>  
		       <th>ID</th>  
		       <th>Group Id</th>  
		       <%--<th>Active </th> 
		       <th>Deleted </th> --%>
                       <th>Company Name</th>  
		       <th>Description </th> 
                        <th>Action</th> 
	           </tr>  
	      </thead>
	      <c:forEach items="${memberExtraFieldGrps}" var="membExtFld">  
	       <tr>  
		<td><c:out value="${membExtFld.id}"/></td>  
		<td><c:out value="${membExtFld.groupid}"/></td>  
                <td><c:out value="${membExtFld.companyName}"/></td>
                <td><c:out value="${membExtFld.fieldOption}"/></td> 
		<%--<td><c:out value="${addressType.active}"/></td> 
		<td><c:out value="${addressType.deleted}"/></td> --%>

		<td><a href="editMemberExtraFieldGrp.htm?id=${membExtFld.id}">Edit</a> | <a href="deleteMemberExtraFieldGrp.htm?id=${membExtFld.id}">Delete</a> </td>  
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
				 jQuery('#addrtype-list').DataTable({
                    responsive: true
                });
    })
</script>
