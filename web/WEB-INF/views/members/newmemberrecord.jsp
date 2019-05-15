<%@ include file="includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
      <li>Approve Email Changes</li>
    </ul>
    
           <h4>APPROVE MEMBERS EMAILS CHANGES</h4>
</div>
<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<script lanuage="Javascript">
	function doSubmit(frm){	
	   
	    frm.submit();
	}	  
  </script>
  <form:form method="POST" action="approveMemberMailChange" commandName="membEmail">
      
      
  <div class="contentpanel">
    <div class="row">
       <!-- <div class="col-md-8">-->
            <!-- CONTENT GOES HERE -->  
           <!-- <div class="col-md-8">-->
	     <div class="panel-body">	      
                 <c:set var="logonUser" value="<%=request.getRemoteUser()%>" />      
                    
      </div>
  
	     <table id="addr-item-type" class="table table-striped table-bordered responsive">  
	      <thead>
	           <tr>
                      <th>Company</th>
                       <th>Branch</th>
                      <th>Firstname</th>
                      <th>Middlename</th>
                      <th>Surname</th>
                       <th>Old Email</th>
                        <th>New Email</th>
                        <th>Action</th>  
                </tr> 
	      </thead>
	       <c:forEach items="${membersmail}" var="emailupdate">  
	       <tr>  
                  <td><c:out value="${emailupdate.companyName}"/></td>
                   <td><c:out value="${emailupdate.branchName}"/></td>  
		 <td><c:out value="${emailupdate.firstname}"/></td>  
                  <td><c:out value="${emailupdate.middlename} "/></td> 
                   <td><c:out value="${emailupdate.surname} "/></td> 
                  <td><c:out value="${emailupdate.emailAdd1}"/></td>  
                  <td><c:out value="${emailupdate.newemail}"/> </td>  
                <td> 
                <c:if test="${emailupdate.createdby == logonUser}">
<font color="red"><div data-toggle="tooltip" title="A different user is required to approve this request" cssClass="tooltips">NOT ALLOWED</div></font>
					    </c:if>
					    <c:if test="${emailupdate.createdby ne logonUser}">
                                                 <input type="checkbox" name="id"  value="${emailupdate.id}" >
					          <input type="hidden" name="approvedby"  value="<%=request.getRemoteUser()%>" >
					    </c:if>
                </td>  
    <%--<td><c:out value="${addressType.active}"/></td> 
    <td><c:out value="${addressType.deleted}"/></td> --%>

		
	       </tr>  
	      </c:forEach>  
	     </table> 
                 <div class="form-group">
                            <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">APPROVE</button>

                        </div>
      
      
           <!-- </div>--><!-- col-md-6 -->
       <!-- </div>-->
    </div>
</div>
  </form:form>
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
   