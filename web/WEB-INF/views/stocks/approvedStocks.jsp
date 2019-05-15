<%-- 
    Document   : approvedStocks
    Created on : Sep 25, 2015, 5:53:32 AM
    Author     : baydel200
--%>

<%@ include file="../includes/header.jsp" %>
<div class="media-body">
    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Shares Management</li>
    </ul>
    <h4>Approved Shares</h4>
</div>

<div class="contentpanel">
        
          <div class="media-body">
             <h4></h4>
          </div>
  <form method="post" action="">
    <c:if test="${!empty listStocks}">  
	     <table id="stock-list" class="table table-striped table-bordered responsive">  
		 <thead>
	      <tr>  
                  <%--     <th></th> --%>
	       <th>S/N</th> 
               <th>Name</th>  
               <th>Created By </th> 
               <th>Created Date</th> 
               <th> </th> 
               <th> </th> 
               <th> </th> 
	     </tr>  
</thead>
<tbody>
<%--    
<c:forEach items="${listStocks}" var="stck">  
	       <tr>   --%>
                   <%--  <td><input type="checkbox" name="stkIds" value="${stck[0]}"></td> --%>
		<%--  <td><c:out value="${stck[2]}"/></td>  
		<td><c:out value="${stck[4]}"/></td>  
		<td><c:out value="${stck[3]}"/></td>  
		<td><a href="../stock/viewStockInfo.htm?stkId=${stck[0]}">View</a></td> 
	       </tr>  
</c:forEach>
           --%>     
  <c:forEach items="${listStocks}" var="stck" varStatus = "status">  
                        <tr>           
                            <td><c:out value="${status.index + 1}"/></td> 
                            <td><c:out value="${stck.compStockName}"/></td>  
                            <td><c:out value="${stck.createdBy}"/></td>  
                            <td><c:out value="${stck.createdDate}"/></td>  
                            <td><a href="../stock/viewStockInfo.htm?stkId=${stck.compStockTypeId}">View</a></td> 
                            <td><a href="../stock/update.htm?stkId=${stck.compStockTypeId}">Edit</a></td> 
                            <td>

                                <c:choose>
                                    <c:when test="${stck.isUsedByMember}">

                                        <a href="../stock/delete.htm?stkId=${stck.compStockTypeId}">Delete</a>
                                    </c:when>
                                    <c:otherwise>
                                        
                                    </c:otherwise>
                                </c:choose>        

                            </td> 
                            <%--<td>${stck.isUsedByMember}</td>--%> 
                        </tr>  
                    </c:forEach>             
               
 <%-- 	<tr>  
		  <td></td> 
                  <td></td> 
		<td></td> 
	 </tr>  
 --%>
</tbody>
</table>  
 </c:if>   
    
    <c:if test="${empty listStocks}">
        <div>		 
	<h4>NO RECORD FOUND </h4>  
        </div>
    </c:if>

   </form>
  
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
