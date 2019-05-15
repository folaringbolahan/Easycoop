<%@ include file="includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Edit Vote Setup</li>
    </ul>
    <h4>Edit Setup</h4>
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
                 <form:form method="post" action="saveManageField" commandName="votAgm">          
                   
            
      </form:form>  
      </div>
     <c:if test="${!empty agmList}">  
          <h4>LIST OF AGM FOR VOTE SETUPS</h4>  
	     <table id="addr-item-type" class="table table-striped table-bordered responsive">  
	      <thead>
	           <tr>  
		      
		       <th>Agm id</th>
                       <th>Agm</th> 
                       <th>Action</th> 
	           </tr>  
	      </thead>
	      <c:forEach items="${agmList}" var="setup">  
	       <tr> 
		<td><c:out value="${setup.agmid}"/></td>  
		<td><c:out value="${setup.description}"/></td>  
                
                <td><a href="editQuestions.htm?id=${setup.agmid}">Edit</a> </td>  
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
   