<%@ include file="includes/header.jsp" %>  
<div class="media-body">
     <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Loan Settings Management</li>
    </ul>
    <h4>View Branch Loan Configuration</h4>
</div>
<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<div class="contentpanel">
    <div class="row">
        <div class="col-md-10">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-15">
	           <div class="panel-body">
                      <c:set var="now" value="<%=new java.util.Date()%>" />		      
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-i18n.min.js"></script> 
      		   </div>
        
		      <c:if test="${!empty appConfigurations}">  
			  <h4>BRANCH LOAN CONFIGURATION SETTINGS</h4>  
			     <table id="appconfig-list" class="table table-striped table-bordered responsive">  
			      <thead>
			          <tr>  
				       <th>ID</th>  
				       <th>Name</th>  
				       <th>Type</th>  
				       <th>Derivation</th> 
				       <th>Min</th>  
				       <th>Max</th> 
				       <th>Action </th> 
			          </tr>  
			      </thead>
			      <c:forEach items="${appConfigurations}" var="appConfig">  
			          <tr>  
					<td><c:out value="${appConfig.id}"/></td>  
					<td><c:out value="${appConfig.configName}"/></td>  
					<td><c:out value="${appConfig.configType}"/></td>  
					<td><c:out value="${appConfig.computationType}"/></td>  
					<td><c:out value="${appConfig.configMinValue}"/></td> 
					<td><c:out value="${appConfig.configMaxValue}"/></td>
					<td align="center"><a href="editAppConfig.htm?id=${appConfig.id}">Edit</a></td>  
			          </tr>  
			      </c:forEach>  
			     </table>  
		    </c:if>  
            </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->

<%@ include file="includes/footer.jsp" %>  
<script>
    $(document).ready(function(){
         $("#formula1").hide();
         $("#valueGroup").hide();
         
         jQuery('select').select2({
                    minimumResultsForSearch: -1
                });
			jQuery('#appconfig-list').DataTable({responsive: true });
		        //jQuery('#appconfig-list').DataTable({responsive: true });

               })
</script>
   