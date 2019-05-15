<%@ include file="includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Edit External Company Setup</li>
    </ul>
    <h4>Edit External Company Setup</h4>
</div>
<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<script lanuage="Javascript">
	function doSubmit(frm){	
	    if(frm.description.value==""){alert(" is required"); frm.typeName.focus(); return;}
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
                   
            
      </form:form>  
      </div>
     <c:if test="${!empty externalAgmList}">  
          <h4>LIST OF AGM SETUP</h4>  
	     <table id="addr-item-type" class="table table-striped table-bordered responsive">  
	      <thead>
	           <tr>  
		       <th>ID</th>  
		       <th>Company Id</th>  
		       <th>Agm Name</th>
                       <th>Company Name</th>
                     
                       <th>Action</th> 
                       
	           </tr>  
	      </thead>
	      <c:forEach items="${externalAgmList}" var="setup">  
	       <tr>  
		<td><c:out value="${setup.id}"/></td>  
		<td><c:out value="${setup.companyrefid}"/></td>  
                <td><c:out value="${setup.description}"/></td> 
                 <td><c:out value="${setup.companyName}"/></td> 
               
                <td><a href="editExternalAgm.htm?id=${setup.id}">Edit</a> </td>  
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
   