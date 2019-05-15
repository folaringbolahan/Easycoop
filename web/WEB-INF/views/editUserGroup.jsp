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
				$.getJSON('userRolesAjaxList.do', {
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
        
      
         <script type="text/javascript">
	      $(document).ready(function(){	        
		$('select#companyId').change(		        
			function(){
			        //alert($(this).val());
				$.getJSON('branchesAjaxList.do', {
					companyId : $(this).val(),
					ajax : 'true'
				}, function(data) {
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
				        }
				        
				        //alert(html);					
					$('select#branchId').html(html);
				});
			});
	      });
      </script> 
       --%>
<div class="media-body">
                 <div style="float:left">

		    <ul class="breadcrumb">
			<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
			<li><a href="#">Home</a></li>
			<li>User Group Management</li>
		    </ul>
		    <h4>Manage User Group</h4>
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
                 
                 <form:form method="post" action="saveUserGroup.htm" commandName="userGroup">		
		  <div class="form-group">
			<form:label path="id" cssClass="col-sm-4 control-label">ID:</form:label>
		        <div class="col-sm-2">
			      <form:input path="id" value="${userGroup.id}" readonly="true"/>
			</div> 
		   </div>
		   
		<%--   
		   <div class="form-group">  
			  <form:label path="companyId" cssClass="col-sm-4 control-label">Cooperative:</form:label>  
			  <div class="col-sm-2">
			      <form:select id="companyId" path="companyId" cssClass="width300">
				<c:forEach items="${companies}" var="item">  
				     <form:option value="${item.id}">${item.name}</form:option>
				</c:forEach>
			      </form:select>
			      <form:errors path="companyId" cssClass="error"></form:errors>
			  </div>  
		   </div> 
		   
		   <div class="form-group">  
			  <form:label path="branchId" cssClass="col-sm-4 control-label">Branch</form:label>
			  <div class="col-sm-4">
			      <form:select id="branchId" path="branchId" cssClass="width300">
				 <c:forEach items="${branches}" var="item">  
				      <form:option value="${item.id}">${item.branchName}</form:option>
				</c:forEach>
			      </form:select>
			      <form:errors path="branchId" cssClass="error"></form:errors>
			  </div>  
		   </div>
		--%>
		
	           <div class="form-group">
		      <form:label path="code" cssClass="col-sm-4 control-label">Code *:</form:label> 
		      <div class="col-sm-4">
                         <form:input path="code" value="${userGroup.code}"/>
                         <form:errors path="code" cssClass="error"></form:errors>
		      </div>
                  </div>
                  
	          <div class="form-group">
		      <form:label path="description" cssClass="col-sm-4 control-label">Description *:</form:label> 
		      <div class="col-sm-4">
                         <form:input path="description" value="${userGroup.description}"/>
                         <form:errors path="description" cssClass="error"></form:errors>
		      </div>
                  </div>
                 
           <%--       
		   <div class="form-group">  
			  <form:label path="accessId" cssClass="col-sm-4 control-label">Access Level</form:label>
			  <div class="col-sm-2">
			      <form:select id="accessId" path="accessId">
				 <form:option value="-1">--select--</form:option>
				 <form:option value="2">Branch Admin</form:option>
				 <form:option value="1">App Admin</form:option>
				 <form:option value="0">Company Admin</form:option>
			      </form:select>
			      <form:errors path="accessId" cssClass="error"></form:errors>
			  </div>  
		   </div>
    	   --%>  
		   
           <form:hidden path="active" value="Y"/> 
           <form:hidden path="deleted" value="N"/> 
           <input type="hidden" name="ACTION_ID" value="2">
	   <form:hidden path="companyId" value="${userGroup.companyId}"/> 
	   <form:hidden path="branchId" value="${userGroup.branchId}"/> 
	   <form:hidden path="accessId" value="2"/> 

           
           <div class="form-group">
		    <button class="btn btn-danger mr5" type="submit">UPDATE USERGROUP</button>
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
				 jQuery('#usergroup-list').DataTable({
                    responsive: true
                });
    })
</script>
   