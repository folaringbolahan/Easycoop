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
                 <c:set var="now" value="<%=new java.util.Date()%>" />
                 
	      <form:form method="post" action="saveUser.htm" commandName="user">

		  <%--
			  <div class="form-group"> 
				  <form:label path="id" cssClass="col-sm-3 control-label">ID:</form:label>  
				  <div class="col-sm-8">
				     <form:input path="id" value="${user.id}" readonly="true"/>
				  </div>  
			  </div> 
		  --%>

		  <div class="form-group"> 
			  <form:label path="companyId" data-toggle="tooltip" title="Select Cooperative" cssClass="col-sm-3 tooltips control-label">Cooperative *:</form:label>  
			  <div class="col-sm-8">
			     <form:select id="companyId" path="companyId" cssClass="width300">
				<c:forEach items="${companies}" var="item">  
				     <form:option value="${item.id}">${item.name}</form:option>
				</c:forEach>
			      </form:select>
			      <form:errors path="companyId" cssClass="error"></form:errors>
			  </div>  
		  </div> 

		  <div class="form-group">
			  <form:label path="branchId" cssClass="col-sm-3 control-label">Select Branch *:</form:label>  
			  <div class="col-sm-8">
			      <form:select id="branchId" path="branchId" cssClass="width300">
			         
				<c:forEach items="${branches}" var="item">  
				  <form:option value="${item.id}">${item.branchName}</form:option>
				</c:forEach>
				<form:option value="" selected="true">--select--</form:option>
			      </form:select>
			      <form:errors path="branchId" cssClass="error"></form:errors>
			  </div>  
		   </div>

		   <div class="form-group">
			  <form:label path="groupId" cssClass="col-sm-3 control-label">User Group *:</form:label>  
			  <div class="col-sm-8">
			     <form:select id="groupId" path="groupId" cssClass="width300">
			       <form:option value="">--select--</form:option>
			       <c:forEach items="${userGroups}" var="obj">  
				    <form:option value="${obj.code}">${obj.description}</form:option>
				</c:forEach>
			      </form:select>
			      <form:errors path="groupId" cssClass="error"></form:errors>
			  </div>  
		   </div> 	  

		   <div class="form-group"> 
			  <form:label path="username" cssClass="col-sm-3 control-label">Username *:</form:label>
			  <div class="col-sm-8">
			     <form:input path="username" value="${user.username}" data-toggle="tooltip" title="Enter username here" cssClass="tooltips"/>
			     <form:errors path="username" cssClass="error"></form:errors>
			  </div>  
		   </div>

	       <%--  
		   <div class="form-group"> 
			  <form:label path="password" cssClass="col-sm-3 control-label">Password:</form:label>
			  <div class="col-sm-8">
			     <form:input type="password" path="password" value="${user.password}" />
			     <form:errors path="password" cssClass="error"></form:errors>
			  </div>  
		   </div>	   


		   <div class="form-group"> 
			  <form:label path="role" cssClass="col-sm-3 control-label">User Role</form:label>  
			  <div class="col-sm-8">
			       <form:select id="role" path="role">
				 <form:option value="">--select--</form:option>

				 <c:forEach items="${userRoles}" var="obj">  
				    <form:option value="${obj.roleCode}">${obj.roleName}</form:option>
				 </c:forEach>

			      </form:select>
			      <form:errors path="role" cssClass="error"></form:errors>
			  </div>  
		   </div>
		 --%>

		   <div class="form-group"> 
			  <form:label path="email" cssClass="col-sm-3 control-label">Email Address *:</form:label>  
			  <div class="col-sm-8">
			     <form:input path="email" value="${user.email}" data-toggle="tooltip" title="Enter a working email address here" cssClass="tooltips"/>
			     <form:errors path="email" cssClass="error"></form:errors>
			  </div>  
		   </div> 
		   <div class="form-group"> 
			  <form:label path="phone" cssClass="col-sm-3 control-label">Phone *:</form:label>
			  <div class="col-sm-8">
			     <form:input path="phone" value="" data-toggle="tooltip" title="Enter user mobile number" cssClass="tooltips"/>
			     <form:errors path="phone" cssClass="error"></form:errors>
			  </div>  
		   </div>  

		   <input type="hidden" name="ACTION_ID" value="1">
		   <form:hidden path="authMode" value="DBLOGIN"/>
		   <form:hidden path="enabled" value="1"/>
		   <form:hidden path="deleted" value="0"/>
                   <form:hidden path="accountNonLocked" value="1"/>
                   <form:hidden path="accountNonExpired" value="1"/>
                   <form:hidden path="credentialsNonExpired" value="1"/>
		   <form:hidden path="passwordTenure" value="90"/>
		   <form:hidden path="createdBy" value="<%=request.getRemoteUser()%>"/>
		   <form:hidden path="lastModifiedBy" value="<%=request.getRemoteUser()%>"/>

		   <form:hidden path="creationDate" id="creationDate" name="creationDate" data-format="dd/MM/yyyy"  value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>
		   <form:hidden path="lastModificationDate" id="lastModificationDate" name="lastModificationDate"   value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>

		   <div class="form-group">
			  <button class="btn btn-danger mr5" type="submit">ADD USER</button>
			  <button type="reset" class="btn btn-default">RESET</button>
		   </div><!-- panel-footer -->        
	   </form:form>  
   </div>
   
   <%
      	com.sift.admin.bean.UserBean userBean=(com.sift.admin.bean.UserBean)request.getAttribute("user");
      	//System.out.println("Email:"+userBean.getCreatedBy());
      	//System.out.println("R Email:"+request.getRemoteUser());      	
      	boolean permit=false; //userBean.getCreatedBy().trim().equalsIgnoreCase(request.getRemoteUser().trim())?false:true;   
   %>
   
   <c:if test="${!empty users}">  
             <div class="media-body">
                <h4>LIST OF USERS</h4>
             </div>
   	     <table id="users-list" class="table table-striped table-bordered responsive">  
   		 <thead>
   	      <tr>  
   	       <th>ID</th>  
   	       <th>Company</th>  
   	       <th>Branch</th>  
   	       <th>Email</th>  
   	       <th>Username</th>  
   	       <th>Enabled </th> 
   	       <th>Action </th> 
   	      </tr>  
   </thead>
   <tbody>
   	      <c:set var="varEN" value="Y" />
   	      <c:forEach items="${users}" var="user">  
   	       <tr>  
   		<td><c:out value="${user.id}"/></td>  
		<td><c:out value="${user.companyName}"/></td>  
		<td><c:out value="${user.branchName}"/></td>  
		<td><c:out value="${user.email}"/></td> 
   		<td><c:out value="${user.username}"/></td>  
   				
   		<c:if test="${user.enabled==0}">  
   		     <c:set var="varEN" value="N" />
   		</c:if>
   		
		<c:if test="${user.enabled==1}">  
		     <c:set var="varEN" value="Y" />
   		</c:if>
   		
   		<td><c:out value="${varEN}"/></td>
   		<td align="center"><a href="editUser.htm?id=${user.id}">Edit</a></td>  
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
   