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
         --%>
      
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
<div class="media-body">
   	<div style="float:left">
	    <ul class="breadcrumb">
		<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		<li><a href="#">Home</a></li>
		<li>Tax Group Management</li>
	    </ul>
	    <h4>Manage Tax Group</h4>
	</div>
	<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
  <script lanuage="Javascript">
	function doSubmit(frm){	
	    if(frm.companyId.value==""){alert("company is required"); frm.companyId.focus(); return;}
	    if(frm.code.value==""){alert("code is required"); frm.code.focus(); return;}
	    if(frm.description.value==""){alert(" description is required"); frm.description.focus(); return;}

	    frm.submit();
	}	  
  </script><div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
			<div class="panel-body">
                 <c:set var="now" value="<%=new java.util.Date()%>" />
                 <form:form method="post" action="saveTaxGroup.htm" commandName="taxGroup">
		   <div class="form-group">
			<form:label path="id" cssClass="col-sm-4 control-label">ID:</form:label>
		        <div class="col-sm-2">
			      <form:input path="id" value="${taxGroup.id}" readonly="true"/>
			</div> 
		   </div>
		   
		   <div class="form-group">  
			  <form:label path="companyId" cssClass="col-sm-4 control-label">Select Cooperative *:</form:label>  
			  <div class="col-sm-2">
			      <form:select id="companyId" path="companyId" cssClass="width300">
				<form:option value="">--select--</form:option>
				<c:forEach items="${companies}" var="item">  
				     <form:option value="${item.id}">${item.name}</form:option>
				</c:forEach>
			      </form:select>
			      <form:errors path="companyId" cssClass="error"></form:errors>
			  </div>  
		   </div> 
		   
		   <div class="form-group">   
			  <form:label path="branchId" cssClass="col-sm-4 control-label">Branch *:</form:label>
			  <div class="col-sm-2">
			      <form:select id="branchId" path="branchId" cssClass="width300">
				 <form:option value="">--select--</form:option>
				 <c:forEach items="${branches}" var="item">  
					<form:option value="${item.id}">${item.branchName}</form:option>
				 </c:forEach>
			      </form:select>
			  </div>  
		  </div>
		  
		  <div class="form-group">
		      <form:label path="code" cssClass="col-sm-4 control-label">Code *:</form:label> 
		      <div class="col-sm-2">
                         <form:input path="code" value="${taxGroup.code}"/>
		      </div>
                  </div>
                  
	           <div class="form-group">
		      <form:label path="description" cssClass="col-sm-4 control-label">Description *:</form:label> 
		      <div class="col-sm-2">
                         <form:input path="description" value="${taxGroup.description}"/>
		      </div>
                  </div>
    
           <form:hidden path="active" value="Y"/>
           <form:hidden path="deleted" value="N"/> 
           <input type="hidden" name="ACTION_ID" value="2">

           <div class="form-group">
	      <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">SUBMIT</button>
	      <button type="reset" class="btn btn-default">RESET</button>
	   </div><!-- panel-footer -->       
            
      </form:form>  
        </div>
      <c:if test="${!empty taxGroups}">  
          <div class="media-body"><h4>LIST OF TAX GROUPS</h4></div>
	     <table id="taxgroup-list" class="table table-striped table-bordered responsive">  
		 <thead>
		      <tr>  
		       <th>ID</th>  
		       <th>Code</th>
		       <th>Description</th>
		       <th>Action </th> 
		      </tr>  
		 </thead>
<tbody>
	      <c:forEach items="${taxGroups}" var="taxgroup">  
	       <tr>  
		<td><c:out value="${taxgroup.id}"/></td>  
		<td><c:out value="${taxgroup.code}"/></td>  
		<td><c:out value="${taxgroup.description}"/></td> 
		<td align="center"><a href="editTaxGroup.htm?id=${taxgroup.id}">Edit</a></td>  
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
				 jQuery('#taxgroup-list').DataTable({
                    responsive: true
                });
    })
</script>
   