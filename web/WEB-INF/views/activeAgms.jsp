<%@ include file="includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
      <li>Agm Deactivation</li>
    </ul>
    
           <h4>AGM DEACTIVATION</h4>
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
  <form:form method="POST" action="deactivateAgms" commandName="votAgm">
      
      
  <div class="contentpanel">
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
          
                        <th>Deactivate</th>  
                </tr> 
	      </thead>
	      <c:forEach items="${activeAgms}" var="agm">  
	       <tr>  
		 <td><c:out value="${agm.id}"/></td>  
    <td><c:out value="${agm.description}"/></td>  
                <td>
                     <input type="checkbox" name="agmid"  value="${agm.id}" >
                  
                        
                 </td>  
    <%--<td><c:out value="${addressType.active}"/></td> 
    <td><c:out value="${addressType.deleted}"/></td> --%>

		
	       </tr>  
	      </c:forEach>  
	     </table>  
       <input type="hidden" name="createdby" value="<%=request.getRemoteUser()%>">
        <div class="form-group">
                           <button class="btn btn-danger mr5" type="submit" >DEACTIVATE </button>

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
   