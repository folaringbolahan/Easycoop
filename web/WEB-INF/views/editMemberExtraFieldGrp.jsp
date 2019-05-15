<%@ include file="includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Extra Field Option Management</li>
    </ul>
    <h4>Extra Field Option </h4>
</div>
<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<script lanuage="Javascript">
	function doSubmit(frm){	
	    if(frm.fieldOption.value==""){alert("Type Name is required"); frm.fieldOption.focus(); return;}
	    frm.submit();
	}	  
  </script>
<div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
	     <div class="panel-body">	      
                 <form:form method="post" action="saveMemberExtraFieldGrp" commandName="memberExtraFieldGrp">
                      <div class="form-group">
                        <label data-toggle="tooltip"  class="col-sm-3 tooltips control-label">Company Name <span class="asterisk">*</span></label>
                          <div class="col-sm-7">                                           
                               <form:input path="companyName" id="companyName" cssClass="form-control" value="${memberExtraFieldGrp.companyName}" readonly="true"/>                                                                 
                          </div>
                    </div>
                     <div class="form-group">
                        <label data-toggle="tooltip"  class="col-sm-3 tooltips control-label">Field Option <span class="asterisk">*</span></label>
                          <div class="col-sm-7">                                           
                               <form:input path="fieldOption" id="fieldOption" cssClass="form-control" value="${memberExtraFieldGrp.fieldOption}" />                                                                 
                          </div>
                    </div>
		
                      <input type="hidden" name="id" value="${memberExtraFieldGrp.id}">
                      <input type="hidden" name="groupid" value="${memberExtraFieldGrp.groupid}">
		  
           <div class="form-group">
		    <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">UPDATE</button>
		    <button type="reset" class="btn btn-default">RESET</button>
	    </div><!-- panel-footer -->       
            
      </form:form>  
      </div>
   
      <c:if test="${!empty memberExtraFieldGrps}">  
          <h4>LIST OF EXTRA FIELD OPTIONS</h4>   
	     <table id="addr-item-type" class="table table-striped table-bordered responsive">  
	      <thead>
		      <tr>  
		       <th>ID</th> 
                       <th>Extra Field Name</th>
		       <th>Description</th>  
                       <th>Company Name</th>  
		 
		       <th>Action </th> 
		      </tr>  
	      </thead>
	      <c:forEach items="${memberExtraFieldGrps}" var="memExtFld">  
	       <tr>  
		<td><c:out value="${memExtFld.id}"/></td>  
                <td><c:out value="${memExtFld.description}"/></td> 
		<td><c:out value="${memExtFld.fieldOption}"/></td>  
		<td><c:out value="${memExtFld.companyName}"/></td>

		<td align="center"><a href="editMemberExtraFieldGrp.htm?id=${memExtFld.id}">Edit</a></td>  
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
   