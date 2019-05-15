<%@ include file="includes/header.jsp" %> 
<div class="media-body">
     <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Branch Management</li>
    </ul>
<h4>Activate Branch</h4>
</div>
<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<div class="contentpanel">
    <div class="row">
        <div class="col-md-10">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-12">
	       <div class="panel-body">
                 <c:set var="now" value="<%=new java.util.Date()%>" />
		      
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-i18n.min.js"></script> 
                      
                      
		      <script type="text/javascript">
			      $(document).ready(function(){	        
				$('select#countryId').change(
					//$('#addressContainer').html('&nbsp;');
					function(){
						//alert($(this).val());
						$.getJSON('countryAdressItemAjaxList.do', {
							countryId : $(this).val(),
							ajax : 'true'
						}, function(data) {
							var len = data.length;
							var  html='<table>';
							for (var i = 0; i < len; i++) {
								html += '<div class="form-group">'; 
								html += '   <div class="col-sm-4 control-label">' + data[i].addrFieldName + '</div> ';  
								html += '   <div class="col-sm-4 control-label"><input type="text" name="' + data[i].addrFieldName + '" value=""/></div> '; 
								html += '</div>  ';   
							}

							html+='</table>';
							//alert(html);
							$('#addressContainer').html(html);
						});
					});
			      });
		      </script>
		      
		      <c:if test="${!empty branches}">  
			  <h4>LIST OF BRANCHES</h4>  
			     <table id="branch-list" class="table table-striped table-bordered responsive">  
			      <thead><tr>  
			       <th>ID</th>  
			       <th>Coop Name</th> 
			       <th>Code</th> 
			       <th>Name</th> 
			       <th>Email</th>  
			       <th>Action </th> 
			      </tr></thead>  

			      <c:forEach items="${branches}" var="item">  
			       <tr>  
				<td><c:out value="${item.id}"/></td>  
				<td><c:out value="${item.companyName}"/></td> 
				<td><c:out value="${item.branchCode}"/></td> 
				<td><c:out value="${item.branchName}"/></td>  
				<td><c:out value="${item.email}"/></td>  
				<td align="center"><a href="activateBranch_2.htm?id=${item.id}">CONTINUE</a> </td>  
			       </tr>  
			      </c:forEach>  
			     </table>  
		    </c:if> 
		    <c:if test="${empty branches}">  
		           <%
		             //response.sendRedirect("/doFeedback.htm?message=Branch Status Update was successful&redirectURI=activateBranch_1.htm");
		           %>
    			     <div class="media-body error">
    				<h4>NO MATCHING RECORD FOUND</h4>
    			     </div>
    			     
		    </c:if>
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
				 jQuery('#branch-list').DataTable({
                    responsive: true
                });
    })
</script>