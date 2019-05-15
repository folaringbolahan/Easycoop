<%@ include file="includes/header.jsp" %> 
<%
 String capturedBy=request.getRemoteUser();
%>
<div class="media-body">
    <div style="float:left">
	    <ul class="breadcrumb">
		<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		<li><a href="#">Home</a></li>
		<li>Request For Extra Field Approval</li>
	    </ul>
	    <h4>Approve Extra Fields </h4>
    </div>
    
    <%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<div class="contentpanel">
    <div class="row">
        <div class="col-md-12">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-16">
	       <div class="panel-body">
                 <c:set var="logonUser" value="<%=request.getRemoteUser()%>" />
		      
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-i18n.min.js"></script> 
                          
		          <c:if test="${!empty allExtraFieldapprvs}">  
			     <div class="media-body">
				<h4>LIST OF PENDING EXTRA FIELD REQUESTS</h4>
			     </div>
			     
			     <table id="loans-list" class="table table-striped table-bordered responsive">  
				 <thead>
				      <tr>  
				       <th>Id</th>  
				       <th>Field Name</th>  
				       <th>Company Name</th>  
				       <th>Action</th>  
				       
				      </tr>  
		   		</thead>
		                <tbody>
				      <c:forEach items="${allExtraFieldapprvs}" var="extrafield">  
				       <tr>  
					<td><c:out value="${extrafield.id}"/></td> 
                                        
				        <td><c:out value="${extrafield.description}"/></td> 
                                        <td><c:out value="${extrafield.companyName}"/></td> 
					
					<td align="center">
					    <c:if test="${extrafield.createdBy == logonUser}">
					       <font color="red"><div data-toggle="tooltip" title="A different user is required to approve this request" cssClass="tooltips">NOT ALLOWED</div></font>
					    </c:if>
					    <c:if test="${extrafield.createdBy ne logonUser}">
					       <a href="approveExtraField.htm?id=${extrafield.id}">APPROVE</a>
					    </c:if>
					</td>  
				       </tr>  
				      </c:forEach>  
			         </tbody>
			    </table>  
		       </c:if> 
		       <c:if test="${empty allExtraFieldapprvs}">  
			     <div class="media-body error">
				<h4>THERE IS NO PENDING EXTRA FIELD REQUEST TO APPROVE</h4>
			     </div>
		       </c:if>
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
				 jQuery('#loans-list').DataTable({
                    responsive: true
                });
    })
</script>
