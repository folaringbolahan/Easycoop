<%@ include file="includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Manage Member Group</li>
    </ul>
    <h4>Member Group</h4>
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
       
      </div>
     <c:if test="${!empty allMemberExtraFields}">  
          <h4>LIST OF FIELDS</h4>  
	     <table id="addr-item-type" class="table table-striped table-bordered responsive">  
	      <thead>
	           <tr>  
		       <th>ID</th>  
		       <th>Field Name</th>
                       <th>Company Name</th>
                       <th>Member Group ?</th>
                       <th>Action</th> 
                       
	           </tr>  
	      </thead>
	      <c:forEach items="${allMemberExtraFields}" var="membExtFlds">  
	       <tr>  
		<td><c:out value="${membExtFlds.id}"/></td>  
		 
                <td><c:out value="${membExtFlds.description}"/></td> 
                 <td><c:out value="${membExtFlds.companyName}"/></td>
                 <td>
                     <c:if test="${membExtFlds.groupstatus eq true}">
                     <c:out value="Y"/> 
                    </c:if> 
                      <c:if test="${membExtFlds.groupstatus eq false}">
                     <c:out value="N"/> 
                    </c:if> 
                 </td>
                <td><a href="viewGroupedExtrafield.htm?id=${membExtFlds.id}">Set As Member Group</a> &nbsp; | &nbsp; <a href="deleteMemberGroup.htm?id=${membExtFlds.id}">Remove</a> </td>  
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
   