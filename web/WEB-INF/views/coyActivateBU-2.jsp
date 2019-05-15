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
					ajax : 'true'
				}, function(data) {
					var len = 0;
					var html ='';

					if(data!=null){
					   len=data.length;
					}
												
					alert("len:="+len);
					
					if(len>0){
					  for ( var i = 0; i < len; i++) {
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
			        if($(this).val()!=""){
			           $('select#branchId').html('');
			        }
			        
				$.getJSON('branchesAjaxList.do', {
					companyId : $(this).val(),
					ajax : 'true'
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
				        }else{
				                html= '<option value=""> select </option>';
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
		 <%@include file="includes/topright.jsp"%>
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
                 
	      <form:form method="post" action="doActivateUser.htm" commandName="user">
		  <div class="form-group"> 
			  <form:label path="id" cssClass="col-sm-3 control-label">Id:</form:label>  
			  <div class="col-sm-8">
			     <form:input path="id" value="${user.id}" readonly="true"/>
			  </div>  
		   </div> 

		  <div class="form-group"> 
			  <form:label path="companyId" cssClass="col-sm-3 control-label">Cooperative *:</form:label>  
			  <div class="col-sm-8">
			     <form:select id="companyId" path="companyId" disabled="true" cssClass="width300">
				<c:forEach items="${companies}" var="item">  
				     <form:option value="${item.id}">${item.name}</form:option>
				</c:forEach>
			      </form:select>
			     <!-- <form:errors path="companyId" cssClass="error"></form:errors>-->
			  </div>  
		   </div> 

		   <div class="form-group">
			  <form:label path="branchId" cssClass="col-sm-3 control-label">Branch *:</form:label>  
			  <div class="col-sm-8">
			      <form:select id="branchId" path="branchId" disabled="true" cssClass="width300">
					 <form:option value="">--select--</form:option>
					 <c:forEach items="${branches}" var="item">  
						<form:option value="${item.id}">${item.branchName}</form:option>
					</c:forEach>
			       </form:select>
			      <!-- <form:errors path="branchId" cssClass="error"></form:errors> -->
			  </div>  
		   </div>

		   <div class="form-group">
			  <form:label path="groupId" cssClass="col-sm-3 control-label">User Group *:</form:label>  
			  <div class="col-sm-8">
			     <form:select id="groupId" path="groupId" disabled="true" cssClass="width300">
			       <form:option value="">--select--</form:option>
			       <c:forEach items="${userGroups}" var="obj">  
				    <form:option value="${obj.code}">${obj.description}</form:option>
				</c:forEach>
			      </form:select>
			      <!--<form:errors path="groupId" cssClass="error"></form:errors>-->
			  </div>  
		   </div> 


		   <div class="form-group"> 
			  <form:label path="username" cssClass="col-sm-3 control-label">Username *:</form:label>
			  <div class="col-sm-8">
			     <form:input path="username" value="${user.username}"  readOnly="true"  data-toggle="tooltip" title="Username can have same value as user's email address" cssClass="tooltips"/>
			     <!--<form:errors path="username" cssClass="error"></form:errors>-->
			  </div>  
		   </div>

		   <div class="form-group"> 
			  <form:label path="email" cssClass="col-sm-3 control-label">Email Address *:</form:label>  
			  <div class="col-sm-8">
			     <form:input path="email" value="${user.email}" readOnly="true" data-toggle="tooltip" title="User email address" cssClass="tooltips"/>
			     <!--<form:errors path="email" cssClass="error"></form:errors>-->
			  </div>  
		   </div>
            
	  <%
		    com.sift.admin.bean.UserBean userBean=(com.sift.admin.bean.UserBean)request.getAttribute("user");      	
		    String ENABLED=userBean.getEnabled()==1?"YES":"NO";
		    boolean permit=userBean.getCreatedBy().trim().equalsIgnoreCase(request.getRemoteUser().trim())?false:true;

	   %>           
		   <div class="form-group"> 
			  <form:label path="enabled" cssClass="col-sm-3 control-label">Enabled:</form:label>
			  <div class="col-sm-8">
			     <%=ENABLED%>
			  </div>  
		   </div>  

		   <form:hidden path="deleted"  value="0"/>
		   <form:hidden path="authMode" value="${user.authMode}"/>
		   <form:hidden path="enabled"  value="1"/>
		   <form:hidden path="lastModifiedBy" value="<%=request.getRemoteUser()%>"/>
		   <input type="hidden" name="ACTION_ID" value="2">

		   <form:hidden path="lastModificationDate" id="lastModificationDate" name="lastModificationDate"   value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>

		   <div class="form-group">
			    <button type="reset" class="btn btn-default">CANCEL</button>

			    <c:if test="${user.enabled==0}"> 
				   <a href="activateUser.htm?id=${user.id}">
					<button class="btn btn-danger mr5" type="button">ACTIVATE USER</button>
				   </a>
			    </c:if>
		    </div><!-- panel-footer -->        
	      </form:form>  
   	</div>   
   
   <%--
        <c:if test="${!empty users}">  
		     <div class="media-body">
			<h4>LIST OF INACTIVE BRANCH USERS</h4>
		     </div>
		     <table id="users-list" class="table table-striped table-bordered responsive">  
			 <thead>
			      <tr>  
				       <th>ID</th>  
				       <th>Company</th>  
				       <th>Branch</th>  
				       <th>Email</th>  
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
					<td>N</td>
					<td align="center"><a href="coyAdminActivateBUser2.htm?id=${user.id}">ACTIVATE</a></td>  
			       </tr>  
			      </c:forEach>  
			 </tbody>
		     </table>  
	    </c:if>  
    --%>	    
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