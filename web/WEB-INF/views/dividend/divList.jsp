<%-- 
    Document   : divList
    Created on : May 3, 2016, 12:01:51 AM
    Author     : baydel200
--%>

<%@ include file="../includes/header.jsp" %>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Dividend Management</li>
        </ul>
        <h4>${title}</h4>
    </div>
    <%@include file="../includes/topright.jsp" %>
    
</div>
</div><!-- media -->
</div><!-- pageheader -->
<c:set var="datePattern" value="yyyy-MM-dd" />
<div class="contentpanel">
 
    <div class=" col-md-10">
        <table  id="data-list" class="table table-striped table-bordered responsive" >       
            <thead>
                <tr>
               <th> Div. No.</th>  
	       <th> Div. Type</th>  
	       <th> Year </th> 
	       <th> Period</th> 
	       <th> Declaration Date</th>
               <th> Payment Date</th>
               <th> Record Date</th>
	       <th> Ex Dividend</th> 
               <th> </th>
                    
                </tr>
            </thead>
            <tbody>
          <c:forEach items="${listDividend}" var="divd">  
	    <tr>  
		<td><c:out value="${divd.divNumber}"/></td>  
		<td><c:out value="${divd.dividendType.dividendTypeName}"/></td>  
		<td><c:out value="${divd.divYear}"/></td>  
		<td><c:out value="${divd.divPeriod}"/></td>
		<td><fmt:formatDate pattern="${datePattern}" value="${divd.divDeclarationDate}"/></td> 
                <td><fmt:formatDate pattern="${datePattern}" value="${divd.divPayDate}"/></td> 
                <td><fmt:formatDate pattern="${datePattern}" value="${divd.divDateRecord}"/></td> 
		<td nowrap><fmt:formatDate pattern="${datePattern}" value="${divd.exDividendDate}"/></td> 
		<td nowrap><a href="${objView}?dividId=${divd.dividendId}">${objLinkLabel}</a>  ||
                    <a href="${objAprLink}?dividId=${divd.dividendId}">${objApprLabel}</a></td> 
	       </tr>  
	       
	  </c:forEach> 

            </tbody>
        </table>
    </div><!-- col-md-10 -->
   
</div>
<!-- contentpanel -->

</div>
</div><!-- mainwrapper -->
   </section>


        <script src="<%=request.getContextPath()%>/resources/js/jquery-1.11.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery-migrate-1.2.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.10.3.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/modernizr.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/pace.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/retina.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery.cookies.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery.autogrow-textarea.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery.mousewheel.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery.tagsinput.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/toggles.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/bootstrap-timepicker.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery.maskedinput.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/select2.min.js"></script>
        
        <script src="<%=request.getContextPath()%>/resources/js/jquery.validate.min.js"></script>

        <script src="<%=request.getContextPath()%>/resources/js/jquery.dataTables.min.js"></script>
       <script src="http://cdn.datatables.net/plug-ins/725b2a2115b/integration/bootstrap/3/dataTables.bootstrap.js"></script>
        
        <script src="http://cdn.datatables.net/responsive/1.0.1/js/dataTables.responsive.js"></script>
         <script src="${resourceUrl}/js/custom.js"></script>

        <script>
    $(document).ready(function(){
        
       jQuery('select').select2({
                    minimumResultsForSearch: -1
                });
      jQuery('#data-list').DataTable({
                    responsive: true
                });

       });
       
  
</script>
    </body>


</html>
   
