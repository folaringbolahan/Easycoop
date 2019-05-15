      <%@ include file="includes/header.jsp" %>  
		      
      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui.js"></script>
      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-i18n.min.js"></script> 
    	      
      <%--  
	  <script type="text/javascript">
	      $(document).ready(function(){	        
		$('select#groupId').change(		        
			function(){
			        alert($(this).val());
				$.getJSON('userRolesAjaxList.do',{
					groupId : $(this).val(),
					ajax : 'true',
					cache: false
				}, function(data) {
					var len = 0;
					var html ='';

					if(data!=null){
					   len=data.length;
					}
												
					alert("len:="+len);
					
					if(len>0){
					  for ( var i = 0; i < len; i++){
					       html += '<option value="' + data[i].id + '">'  + data[i].roleName + '</option>';
					   }
				        }
				        
				        alert(html);					
					$('select#role').html(html);
				});
			});
	      });
           </script>	
      --%>
      
      <script type="text/javascript">
	      $(document).ready(function(){	        
		$('select#companyId').change(		        
			function(){
			        //alert($(this).val());
				if($(this).val()!=""){
				   //alert($(this).val());
				   //$('select#branchId').html('');
				   $("select#branchId option").remove();				   
				   
				   $("select#branchId option").each(function() {$(this).remove();});
				   //alert($('select#branchId').html());				   
			        }
			        
				$.getJSON('branchesAjaxList.do', {
					companyId : $(this).val(),
					ajax : 'true',
					cache: false
				}, function(data){
					var len = 0;
                                        var html ='';
                                        
					if(data!=null){
                                           len=data.length;
				        }
							
					//alert("len:="+len);
					if(len>0){					        
						
						for ( var i = 0; i < len; i++) {
						    html += '<option value="' + data[i].id + '">' + data[i].branchName + '</option>';
						}
						
						html+= '<option value="" selected> -- select -- </option>';
				        }else{
				                html= '<option value="" selected> select </option>';
				        }
				        
				        //alert(html);					
					$('select#branchId').html(html);
				});
			});
	      });
      </script>
 
 <div class="media-body">
         <div style="float:left">
	     <ul class="breadcrumb">
		 <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		 <li><a href="#">Home</a></li>
		 <li>User Management</li>
	     </ul>
	     <h4>Manage User</h4>
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
                 
   		</div>
   
   <%
      	com.sift.admin.bean.UserBean userBean=(com.sift.admin.bean.UserBean)request.getAttribute("user");
      	//System.out.println("Email:"+userBean.getCreatedBy());
      	//System.out.println("R Email:"+request.getRemoteUser());      	
      	boolean permit=false; //userBean.getCreatedBy().trim().equalsIgnoreCase(request.getRemoteUser().trim())?false:true;   
   %>
   
   <c:if test="${!empty users}">  
             <div class="media-body">
                <h4>LIST OF INACTIVE BRANCH ADMINS</h4>
             </div>
   	     <table id="users-list" class="table table-striped table-bordered responsive">  
                 <thead style="font-size: 12px">
		      <tr>  
			       <th>Username</th>  
			       <th>Company</th>  
			       <th>Branch</th>  
			       <th>Email</th>  
			       <th>Enabled </th> 
			       <th>Action </th> 
		      </tr>  
   		</thead>
		<tbody style="font-size: 12px">
		      <c:set var="varEN" value="Y" />
		      <c:forEach items="${users}" var="user">  
		       <tr>  
				<td><c:out value="${user.username}"/></td>  
				<td><c:out value="${user.companyName}"/></td>  
				<td><c:out value="${user.branchName}"/></td>  
				<td><c:out value="${user.email}"/></td> 
				<td>N</td>
				<td align="center"><a href="coyAdminActivateBUser2.htm?id=${user.id}">ACTIVATE</a></td>  
		       </tr>  
		      </c:forEach>  
	         </tbody>
   	     </table>  
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
				 jQuery('#users-list').DataTable({
                    responsive: true
                });
    })
</script>
   