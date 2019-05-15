<%@ include file="includes/header.jsp" %> 
<div class="media-body">
              <div style="float:left">
		    <ul class="breadcrumb">
			<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
			<li><a href="#">Home</a></li>
			<li>Password Reset Request View</li>
		    </ul>
		    <h4>Password Reset Requests</h4>
		</div>
		<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<div class="contentpanel">
    <div class="row">
        <div class="col-md-12">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-12">
	       <div class="panel-body">
                 <c:set var="now" value="<%=new java.util.Date()%>" />
		      
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-i18n.min.js"></script> 
                      
		       <c:if test="${!empty authPermits}">  
				     <c:set var="count" scope="page" value="0" />
				     <div class="media-body">
					<h4>PASSWORD RESET REQUESTS- VIEW </h4>
				     </div>

				     <table id="auth-list" class="table table-striped table-bordered responsive">  
					 <thead>
					      <tr>  
					       <th>ID</th>  
					       <th>Email</th>  
					       <th>Request Date</th>  
					       <th>Request Status</th>  
					       <th>Action</th> 
					      </tr>  
					</thead>
					<tbody>
					      <c:forEach items="${authPermits}" var="item">  
						       <c:set var="count" scope="page" value="${count + 1}" />
						       <tr>  
							<td><c:out value="${count}"/></td>  
							<td><a href="viewAuthPermit.htm?id=${item.id}">${item.email}</a></td>  
							<td>${item.requestDate}</td>  
							<td>${item.requestStatus}</td> 
							<td>-</td>  
						       </tr>  
					      </c:forEach>  
					 </tbody>
				    </table>  
		       </c:if> 
		       
		       <c:if test="${empty authPermits}">
		                     <div class="media-body  error">
			  		    <h4>NO RECORD FOUND </h4>
				     </div>
		       </c:if>
		       
		       <div class="form-group">
			    <button type="button" class="btn btn-default" onclick="Javascript:history.back(1);">BACK</button>
		       </div>
               </div>
            </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->
   
<%@ include file="includes/footer.jsp" %>  
<script>
    $(document).ready(function(){
         jQuery('select').select2({
                    minimumResultsForSearch: -1
                });
		
		jQuery('#auth-list').DataTable({
                    responsive: true
                });
    })
</script>
