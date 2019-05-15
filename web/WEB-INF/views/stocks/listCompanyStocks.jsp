<%@ include file="../includes/header.jsp" %>
<div class="media-body">
    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Shares Management</li>
    </ul>
    <h3>New Shares</h3>
</div>

<div class="contentpanel">
        
          <div class="media-body">
             <h3></h3>
          </div>
   
    <c:if test="${!empty listStocks}">  
	     <table id="stock-list" class="table table-striped table-bordered responsive">  
		 <thead>
	      <tr>  
	       <th>Name</th>  
	       <th>Created By </th> 
	       <th>Created Date</th> 
	       <th> </th> 
               <th> </th> 

	     </tr>  
</thead>
<tbody>
    
<c:forEach items="${listStocks}" var="stck">  
	       <tr>  
		<td><c:out value="${stck[2]}"/></td>  
		<td><c:out value="${stck[4]}"/></td>  
		<td><c:out value="${stck[3]}"/></td>  
		<td><a href="../stock/update.htm?stkId=${stck[0]}">Edit</a></td> 
                 <td><a href="../stock/delete.htm?stkId=${stck[0]}">Delete</a></td> 

	       </tr>  
	       
	      </c:forEach>
	<tr>  
		 <td></td>  <td></td> 
                  <td></td> 
		<td></td> 
                <td></td> 
	 </tr>  
</tbody>
</table>  
 </c:if>   
    
    <c:if test="${empty listStocks}">
        <div>		 
	<h4>NO RECORD FOUND </h4>  
        </div>
    </c:if>

    <div>	<form method="get"><input name="add" type="Submit" value="Add Stock" class="btn btn-danger" onClick="Javascript:form.action='new.htm'"/></form>
   </div>  
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
			jQuery('#stock-list').DataTable({
                    responsive: true
                });
    })
</script>
   
