<%@ include file="includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
      <li>Edit Members Email</li>
    </ul>
    
           <h4>EDIT MEMBERS EMAILS</h4>
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
        <div class="col-md-10">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
	     <div class="panel-body">	      
                      
                   
            
       
      </div>
  
	     <table id="addr-item-type" class="table table-striped table-bordered responsive">  
	      <thead>
	           <tr>
                      <th>Firstname</th>
                      <th>Middlename</th>
                      <th>Surname</th>
                       <th>Email</th>
          
                        <th>Edit</th>  
                </tr> 
	      </thead>
	      <c:forEach items="${member}" var="votmemb">  
	       <tr>  
		 <td><c:out value="${votmemb.firstname}"/></td>  
                  <td><c:out value="${votmemb.middlename} "/></td> 
                   <td><c:out value="${votmemb.surname} "/></td> 
                  <td><c:out value="${votmemb.email}"/></td>  
                <td><a href="editVotMembEmail.htm?memberid=${votmemb.memberid}">Edit</a> </td>  
    <%--<td><c:out value="${addressType.active}"/></td> 
    <td><c:out value="${addressType.deleted}"/></td> --%>

		
	       </tr>  
	      </c:forEach>  
	     </table>  
      
      
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
   