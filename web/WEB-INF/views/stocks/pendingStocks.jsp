<%-- 
    Document   : pendingStocks
    Created on : Sep 25, 2015, 5:53:08 AM
    Author     : baydel200
--%>

<%@ include file="../includes/header.jsp" %>
<div class="media-body">
    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Shares Management</li>
    </ul>
    <h4>New Shares Awaiting Approval</h4>
</div>

<div class="contentpanel">
        
          <div class="media-body">
             <h3> </h3>
          </div>
  
    <c:if test="${!empty listStocks}">  
         <c:set var="count" scope="page" value="0" />
	     <table id="stock-list" class="table table-striped table-bordered responsive">  
		 <thead>
	      <tr>  
                <th>ID</th>
	       <th>Name</th>  
	       <th>Created By </th> 
	       <th>Created Date</th> 
	       <th> </th> 
               <th> </th> 
	     </tr>  
</thead>
<tbody>
  <form method="post" action="">
<c:forEach items="${listStocks}" var="stck">  
     <c:set var="count" scope="page" value="${count + 1}" />
	       <tr>  
                <td><c:out value="${count}"/></td>  
                <td><input type="checkbox" name="stkIds" value="${stck[0]}"></td>  
		<td><c:out value="${stck[2]}"/></td>  
		<td><c:out value="${stck[4]}"/></td>  
		<td><c:out value="${stck[3]}"/></td>  
		<td><a href="../stock/apprvViewStock.htm?stkId=${stck[0]}">Approve</a></td> 
                <td><a href="../stock/delete.htm?stkId=${stck[0]}">Delete</a></td>
	       </tr>  
</c:forEach>
                  
<tr>  
            <td colspan="5"></td> 
                  
	 </tr>  
	<tr>  
      <td colspan="5"><input name="action" type="Submit" value="APPROVE" class="btn btn-danger" onClick="Javascript:form.action='bulkApprv.htm'"/>
     <input name="action" type="Submit" value="REJECT" class="btn btn-danger" onClick="Javascript:form.action='bulkApprv.htm'"/>
</td> 
                  
	 </tr> 
         
         <tr>  
            <td colspan="5"> </td> 
                  
	 </tr>  
   </form>
</tbody>
</table>  
 </c:if>   
    
    <c:if test="${empty listStocks}">
        <div>		 
	<h4>NO RECORD FOUND </h4>  
        </div>
    </c:if>

 
  
</div><!-- contentpanel -->

<%@ include file="../includes/footer.jsp" %>  
<script>
    $(document).ready(function(){
         jQuery('select').select2({
                    minimumResultsForSearch: -1
                });
			jQuery('#stock-list').DataTable({
                    responsive: true
                });
    })
</script>
