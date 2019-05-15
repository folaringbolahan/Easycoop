<%-- 
    Document   : updatedStocks
    Created on : Oct 7, 2017, 3:54:05 PM
    Author     : Olakunle Awotunbo
--%>

<%@ include file="../includes/header.jsp" %>
<div class="media-body">
    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Shares Management</li>
    </ul>
    <h4>Updated Shares</h4>
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
	       <th>Name</th>  
	       <th>Created By </th> 
	       <th>Created Date</th> 
	       <th> </th> 
                  <!--<th> </th> -->
               <th> </th> 
	     </tr>  
</thead>
<tbody>
    
<c:forEach items="${listStocks}" var="stck">  
	       <tr>  
                   <%--  <td><input type="checkbox" name="stkIds" value="${stck[0]}"></td> --%>
		<td><c:out value="${stck[2]}"/></td>  
		<td><c:out value="${stck[4]}"/></td>  
		<td><c:out value="${stck[3]}"/></td>  
		 <td>
                                <c:if test="${stck[4] == currrentuserServicex.curruser.userId}">
                               <font color="red"><div data-toggle="tooltip" title="A different user is required to approve this shares" cssClass="tooltips">NOT ALLOWED</div></font>
                                            </c:if>
                                <c:if test="${stck[4] ne currrentuserServicex.curruser.userId}">
                                      <a href="../stock/apprvViewStock.htm?stkId=${stck[0]}">Approve</a>
                                            </c:if>
                                </td> 
                                  <!--<td><a href="../stock/update.htm?stkId=${stck[0]}">Edit</a></td> -->
                            <td>
                                 <c:if test="${stck[4] == currrentuserServicex.curruser.userId}">
                                <font color="red"><div data-toggle="tooltip" title="A different user is required to delete this update" cssClass="tooltips">NOT ALLOWED</div></font>
                                 </c:if>
                                 <c:if test="${stck[4] ne currrentuserServicex.curruser.userId}">
                                <a href="../stock/delete.htm?stkId=${stck[0]}">Delete</a>
                                 </c:if>
                                 </td> 
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
