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
      
      <script type="text/javascript">
      	      $(document).ready(function(){	        
      		$('select#branchId').change(		        
      			function(){
      			        //alert($(this).val());
      				if($(this).val()!=""){
      				   //alert($(this).val());      				   
      				   //$("select#branchId option").remove();      				   
      				   //$("select#branchId option").each(function() {$(this).remove();});
      				   //alert($('select#branchId').html());				   
      			        }
      			        
      				$.getJSON('ajaxGetInActiveUsers.do', {
      					branchId : $(this).val(),
      					ajax : 'true',
      					cache: false
      				}, function(data){
      					var len = 0;
                                        var html ='';
                                              
      					if(data!=null){
                                            len=data.length;
      				        }
      							
      					if(len>0){
      					        html+='<table id="user-list" class="table table-striped table-bordered responsive"> '; 
	      					html+='<thead> ';
      					        html+='<tr>   ' ;
					        html+='	<th>ID</th>   ';
					        html+='	<th>Branch</th>   ';
					        html+='	<th>Email</th>   ';
					        html+='	<th>Group</th> ';
					        html+='	<th>Action</th> ';
					        html+='</tr> ' ;
					        html+='</thead> ' ;
      						
      						for ( var i = 0; i < len; i++) {
      						    	html+='<tr>  ' ;
							html+='	<td>'+ 	data[i].id + '</td>   ';
							html+='	<td>'+ 	data[i].branchName + '</td>   ';
							html+='	<td>'+ 	data[i].email + '</td>  ';
							html+='	<td>'+ 	data[i].groupId + '</td>  ';
							html+='	<td><a href="userActivation-2.htm?email=" + data[i].email>ACTIVATE</a></td>  ' ;
					       		html+='</tr>';      						    
      						}
      						
      						html+='</table> ' ;
      						
      				        }else{
      				                //html= '<option value="" selected> select </option>';
      				        }
      				        
      				        //alert(html);					
      					$('#usersContainer').html(html);
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
                 
	      <form:form method="post" action="userActivation-1.htm" commandName="user">

		  <div class="form-group"> 
			  <form:label path="companyId" cssClass="col-sm-3 control-label">Cooperative:</form:label>  
			  <div class="col-sm-8">
			     <form:select id="companyId" path="companyId">
				<c:forEach items="${companies}" var="item">  
				     <form:option value="${item.id}">${item.name}</form:option>
				</c:forEach>
			      </form:select>
			      <form:errors path="companyId" cssClass="error"></form:errors>
			  </div>  
		  </div> 

		  <div class="form-group">
			  <form:label path="branchId" cssClass="col-sm-3 control-label">Select Branch</form:label>  
			  <div class="col-sm-8">
			      <form:select id="branchId" path="branchId">
			         
				<c:forEach items="${branches}" var="item">  
				  <form:option value="${item.id}">${item.branchName}</form:option>
				</c:forEach>
				<form:option value="" selected="true">--select--</form:option>
			      </form:select>
			      <form:errors path="branchId" cssClass="error"></form:errors>
			  </div>  
		   </div>

		   <div class="form-group">  
			 <div colspan="2">
			    <div id="usersContainer"></div
			 </div> 
		   </div> 

		   <input type="hidden" name="ACTION_ID" value="1">
		   
		   <%--
			   <form:hidden path="authMode" value="DBLOGIN"/>
			   <form:hidden path="enabled" value="1"/>
			   <form:hidden path="deleted" value="0"/>
			   <form:hidden path="passwordTenure" value="90"/>
			   <form:hidden path="createdBy" value="<%=request.getRemoteUser()%>"/>
			   <form:hidden path="lastModifiedBy" value="<%=request.getRemoteUser()%>"/>

			   <form:hidden path="creationDate" id="creationDate" name="creationDate" data-format="dd/MM/yyyy"  value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>
			   <form:hidden path="lastModificationDate" id="lastModificationDate" name="lastModificationDate"   value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>
		   --%>
		   
		   <div class="form-group">
			  <%--<button class="btn btn-danger mr5" type="submit">ADD USER</button>--%>
			  <button type="reset" class="btn btn-default">RESET</button>
		   </div><!-- panel-footer -->        
	   </form:form>  
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
				 jQuery('#users-list').DataTable({
                    responsive: true
                });
    })
</script>
   