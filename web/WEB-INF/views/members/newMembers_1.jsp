<%@ include file="../includes/header.jsp" %>
<div class="media-body">
    <div style="float:left">
    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Member Management</li>
    </ul>
    <h4>List Members</h4>
    </div>
     <%@include file="../includes/topright.jsp" %>
</div>

<div class="contentpanel">
         <c:if test="${!empty listMember}">  
          <div class="media-body">
             <h4></h4>
          </div>
	     <table id="member-list" class="table table-striped table-bordered responsive">  
		 <thead>
	      <tr>  
	       <th>Code</th>  
	       <th>First Name</th>  
	       <th>Surname </th> 
	       <th>Branch</th> 
	       <th>Type </th> 
	     </tr>  
</thead>
<tbody>
<c:forEach items="${listMember}" var="mem">  
	       <tr>  
		<td><c:out value="${mem[3]}"/></td>  
		<td><c:out value="${mem[1]}"/></td>  
		<td><c:out value="${mem[2]}"/></td>  
		<td><c:out value="${mem[4]}"/></td> 
		<td><c:out value="${mem[5]}"/></td> 

		<td align="center"><a href="getDiscPage.htm?memId=${mem[0]}">View</a></td>  
	       </tr>  
	       
	      </c:forEach>  
		  </tbody>
	     </table>  
    </c:if>  
            </div><!-- col-md-6 -->
        </div>
    </div>

</div><!-- contentpanel -->

<%@ include file="../includes/footer.jsp" %>  
<script>
    $(document).ready(function(){
         jQuery('select').select2({
                    minimumResultsForSearch: -1
                });
				 jQuery('#member-list').DataTable({
                    responsive: true
                });
    })
</script>
   
