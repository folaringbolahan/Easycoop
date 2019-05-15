<%@ include file="includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
     <li>APPROVAL FOR EMAIL CHANGE</li>
    </ul>
    
           <h4>LIST OF CHANGED MAIL FOR APPROVAL</h4>
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
  <form:form method="POST" action="approveEmailChanges" commandName="votmembermail">
      
  <div class="contentpanel">
            <c:set var="logonUser" value="<%=request.getRemoteUser()%>" />
    <div class="row">
        <div class="col-md-10">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
	     <div class="panel-body">	      
                      
                   
            
       
      </div>
  
	     <table id="addr-item-type" class="table table-striped table-bordered responsive">  
	      <thead>
	           <tr>
                     
		       <th>Id</th>
                       <th>Firstname</th>
                       <th>Middlename</th>
                       <th>Surname</th>
                        <th>Old Email</th> 
                        <th>New Email</th> 
                        <th>Approve</th>
                 

                </tr>
	      </thead>
	       <c:forEach items="${mails}" var="member">  
	      <tr>  
		<td><c:out value="${member.id}"/></td>  
		<td><c:out value="${member.firstname}"/></td> 
                <td><c:out value="${member.middlename}"/></td> 
                <td><c:out value="${member.surname}"/></td> 
                <td><c:out value="${member.oldemail}"/></td>  
                <td><c:out value="${member.newemail}"/></td>  
                <td>
                    <c:if test="${member.createdby == logonUser}">
    <font color="red"><div data-toggle="tooltip" title="A different user is required to approve this email" cssClass="tooltips">NOT ALLOWED</div></font>
                     </c:if>
           <c:if test="${member.createdby ne logonUser}">
		 <input type="checkbox" name="agmid"  value="${member.agmid}" >
                 <input type="hidden" name="newemail"  value="${member.newemail}" >
                 <input type="hidden" name="oldemail"  value="${member.oldemail}" >
                 <input type="hidden" name="id"  value="${member.id}" >
                 <form:hidden path="approvedby" value="<%=request.getRemoteUser()%>"/>
					    </c:if>
                   </td>  
		<%--<td><c:out value="${addressType.active}"/></td> 
		<td><c:out value="${addressType.deleted}"/></td> --%>

		
	       </tr>  
	      </c:forEach>  
	     </table>  
       
        <div class="form-group">
                           <button class="btn btn-danger mr5" type="submit" >APPROVE </button>

                        </div>
            </div><!-- col-md-6 -->
        </div>
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
   