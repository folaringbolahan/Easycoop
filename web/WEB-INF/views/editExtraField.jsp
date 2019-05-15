<%@ include file="includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Extra Field Management</li>
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
                 <form:form method="post" action="updateField" commandName="memberExtraField">
                      <div class="form-group">
                        <label data-toggle="tooltip"  class="col-sm-3 tooltips control-label">Company Name <span class="asterisk">*</span></label>
                          <div class="col-sm-7">                                           
                               <form:input path="companyName" id="companyName" cssClass="form-control" value="${ExtraField.companyName}" readonly="true" />                                                                 
                          </div>
                    </div>
                     <div class="form-group">
                        <label data-toggle="tooltip"  class="col-sm-3 tooltips control-label">Description <span class="asterisk">*</span></label>
                          <div class="col-sm-7">                                           
                               <form:input path="description" id="description" cssClass="form-control" value="${ExtraField.description}" />                                                                 
                          </div>
                    </div>
		  
                     <input type="hidden" name="id" value="${ExtraField.id}">
                     <input type="hidden" name="grouped" value="${ExtraField.grouped}">
                     <input type="hidden" name="companyid" value="${ExtraField.companyid}">
                     <input type="hidden" name="branchid" value="${ExtraField.branchid}">
                     <input type="hidden" name="lastModificationDate" value="<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>">
                     <input type="hidden" name="active" value="${ExtraField.active}">
                     <input type="hidden"  name="creationDate" value="<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>">
		     <input type="hidden" name="deleted" value="${ExtraField.deleted}">
                     <input type="hidden" name="createdBy" value="${ExtraField.createdBy}">
                     <input type="hidden" name="lastModifiedBy" value="${ExtraField.lastModifiedBy}">
           <div class="form-group">
		    <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">UPDATE</button>
		    <button type="reset" class="btn btn-default">RESET</button>
	    </div><!-- panel-footer -->       
            
      </form:form>  
      </div>
   
      <c:if test="${!empty ExtraFields}">  
          <h4>LIST OF EXTRA FIELDS</h4>   
	     <table id="addr-item-type" class="table table-striped table-bordered responsive">  
	      <thead>
		      <tr>  
		       <th>ID</th>  
		       <th>Description</th>  
                       <th>Company Name</th>
		       <%--<th>Active </th> 
		       <th>Deleted </th> --%>
		       <th>Action </th> 
		      </tr>  
	      </thead>
	      <c:forEach items="${ExtraFields}" var="memExtFld">  
	       <tr>  
		<td><c:out value="${memExtFld.id}"/></td>  
		<td><c:out value="${memExtFld.description}"/></td>  
                <td><c:out value="${memExtFld.companyName}"/></td>  
		<%--<td><c:out value="${addressType.active}"/></td> 
		<td><c:out value="${addressType.deleted}"/></td> --%>

		<td align="center"><a href="editExtraField.htm?id=${memExtFld.id}">Edit</a></td>  
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
	 
	 jQuery('#addr-item-type').DataTable({
                    responsive: true
         });
    })
</script>
   