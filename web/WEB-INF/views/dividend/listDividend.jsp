<%@ include file="../includes/header.jsp" %>
<div class="media-body">
    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Dividend Management</li>
    </ul>
    <h4>${title}</h4>
</div>
   <%@include file="../includes/topright.jsp" %>
<div class="contentpanel">
        
          <div class="media-body">
             <h4>Declared Dividend</h4>
          </div>
	     <table id="stock-list" class="table table-striped table-bordered responsive">  
		 <thead>
	      <tr>  
	       <th>Div. No.</th>  
	       <th>Div. Type</th>  
	       <th>Year </th> 
	       <th>Period</th> 
	       <th>Date Of Declaration</th>  
	       <th>Ex Dividend </th> 
	       
	       <th> </th> 
	     </tr>  
</thead>
<tbody>

 <c:if test="${!empty listDividend}">  
 
<c:forEach items="${listDividend}" var="divd">  
	    <tr>  
		<td><c:out value="${divd.divNumber}"/></td>  
		<td><c:out value="${divd.dividendType.dividendTypeName}"/></td>  
		<td><c:out value="${divd.divYear}"/></td>  
		<td><c:out value="${divd.divPeriod}"/></td>
		<td><c:out value="${divd.divDeclarationDate}"/></td> 
		<td><c:out value="${divd.exDividendDate}"/></td> 
		<td><a href="../dividend/update.htm?stkId=${divd.dividendId}">Edit</a></td> 
	       </tr>  
	       
	      </c:forEach> 
	 </c:if>   
	<tr>  
		<td></td>  
		<td></td>  
		<td></td>  
		<td></td> 
	 </tr>  
	 
	 <tr>  
		<td colspan=4><form method="get"><input name="add" type="Submit" value="Add Dividend" class="btn btn-danger" onClick="Javascript:form.action='new.htm'"/></form></td> 
   </tr>  
		  </tbody>
	     </table>  

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
   
