<%@ include file="includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
     <li>Agm Deactivation For Approval</li>
    </ul>
    
           <h4>AGM DE-ACTIVATION FOR APPROVAL</h4>
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
  <form:form method="POST" action="approveAgmDeactivation" commandName="votAgmDeactivation">
      
  <div class="contentpanel">
            <c:set var="logonUser" value="<%=request.getRemoteUser()%>" />

    <div class="row">
        <div class="col-md-8">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
	     <div class="panel-body">	      
                      
                   
            
       
      </div>
  
	     <table id="addr-item-type" class="table table-striped table-bordered responsive">  
	      <thead>
	           <tr>
                     
		       <th>Id</th>
                       <th>Agm</th>
                        <th>Action</th>  
                 

                </tr>
	      </thead>
	       <c:forEach items="${pendingAgmDeactivation}" var="item">  
	      <tr>  
		<td><c:out value="${item.id}"/></td>  
		<td><c:out value="${item.description}"/></td>  
                <td>
                    <c:if test="${item.createdby == logonUser}">
    <font color="red"><div data-toggle="tooltip" title="A different user is required to approve this agm deactivation" cssClass="tooltips">NOT ALLOWED</div></font>
                     </c:if>
           <c:if test="${item.createdby ne logonUser}">
		 <input type="checkbox" name="agmid"  value="${item.agmid}" >
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
   