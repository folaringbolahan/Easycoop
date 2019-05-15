<%@ include file="includes/header.jsp" %> 
<div class="media-body">
<div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Cooperative Management</li>
    </ul>
    <h4>Manage Cooperative</h4>
</div>
<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<script lanuage="Javascript">
	function doSubmit(frm){	
	    if(frm.name.value==""){alert("Company name is required"); frm.name.focus(); return;}	    
	    if(frm.shortName.value==""){alert("short Name is required"); frm.shortName.focus(); return;}	

	    if(frm.regNo.value==""){alert("Reg No is required"); frm.regNo.focus(); return;}
	    if(frm.phone1.value==""){alert("Phone 1 is required"); frm.phone1.focus(); return;}  
	    //if(frm.phone2.value==""){alert("Phone 2 is required"); frm.phone2.focus(); return;}  
	    if(frm.email.value==""){alert("Email is required"); frm.email.focus(); return;}	   
	    //if(frm.fax.value==""){alert("fax is required"); frm.fax.focus(); return;}
	    //if(frm.website.value==""){alert("website is required"); frm.website.focus(); return;}
	    if(frm.countryId.value=="0"){alert("country is required"); frm.countryId.focus(); return;}
	    	    
	    
	    frm.submit();
	}	  
</script>
<div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
	       <div class="panel-body">
                 <c:set var="now" value="<%=new java.util.Date()%>" />
		      
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-i18n.min.js"></script> 
                                            
		      <script type="text/javascript">
			      $(document).ready(function(){	        
				$('select#countryId').change(
					//trying to reset values
					function(){
						//alert($(this).val());
						$.getJSON('countryAdressItemAjaxList.do',{
							countryId : $(this).val(),
							ajax : 'true'
						}, function(data){
						        //alert("returningback");
							var  len = -1;
							var  html='';
							
							//alert("data="+data);
							//alert("data.length="+data.length);

							if(data!=null){
							   len=data.length;
							}

							//alert("len=:"+len);					
							if(len>0){
							   html='<table>';
								for (var i = 0; i < len; i++){
									html += '<div class="form-group">'; 
									html += '   <div class="col-sm-4 control-label">' + data[i].addrFieldName + '</div> ';  
									html += '   <div class="col-sm-2"><input type="text" name="' + data[i].addrFieldName + '" value=""/></div> '; 
									html += '</div>  ';   
								}

							   html+='</table>';
							}					

							//alert(html);
							$('#addressContainer').html(html);
						});
					});
			      });
		      </script>
		      
		      <form:form method="post" action="saveCompany.htm" commandName="company">
			  <%--<div class="form-group">   
				  <form:label path="id" cssClass="col-sm-4 control-label">ID:</form:label> 
				  <div class="col-sm-2">
				     <form:input path="id" value="${company.id}" readonly="true"/>
				      <form:errors path="id" cssClass="error"></form:errors>
				  </div>  
			   </div>
			   --%>
			   
			   <div class="form-group">   
				  <form:label path="name" cssClass="col-sm-4 control-label">Name: *</form:label> 
				  <div class="col-sm-2">
				     <form:input path="name" value="${company.name}"/>
				      <form:errors path="name" cssClass="error"></form:errors>
				  </div>  
			   </div>  
			   <div class="form-group">   
				  <form:label path="shortName" cssClass="col-sm-4 control-label">Short Name:  *</form:label>  
				  <div class="col-sm-4">
				     <form:input path="shortName" value="${company.shortName}"/>
				      <form:errors path="shortName" cssClass="error"></form:errors>
				  </div>  
			   </div>  
			   <div class="form-group">   
				  <form:label path="regNo" cssClass="col-sm-4 control-label">Reg No:  *</form:label>
				  <div class="col-sm-4">
				     <form:input path="regNo" value="${company.regNo}"/>
				      <form:errors path="regNo" cssClass="error"></form:errors>
				  </div>  
			   </div>

			   <div class="form-group">   
				  <form:label path="phone1" cssClass="col-sm-4 control-label">Phone 1:  *</form:label>  
				  <div class="col-sm-4">
				      <form:input path="phone1" value="${company.phone1}"/>
				      <form:errors path="phone1" cssClass="error"></form:errors>
				  </div>  
			   </div>         
			   <div class="form-group">  
				  <form:label path="phone2" cssClass="col-sm-4 control-label">Phone 2:</form:label></td>  
				  <div class="col-sm-4">
				     <form:input path="phone2" value="${company.phone2}"/>
				      <form:errors path="phone2" cssClass="error"></form:errors>
				  </div>  
			   </div>
			   <div class="form-group">   
				  <form:label path="email" cssClass="col-sm-4 control-label">Email:  *</form:label> 
				  <div class="col-sm-4">
				     <form:input path="email" value="${company.email}"/>
				     <form:errors path="email" cssClass="error"></form:errors>
				  </div>  
			   </div> 
			   <div class="form-group">   
				  <form:label path="fax" cssClass="col-sm-4 control-label">Fax:</form:label></td>  
				  <div class="col-sm-4">
				     <form:input path="fax" value="${company.fax}" />
				  </div>  
			   </div>         
			   <div class="form-group">   
				  <form:label path="website" cssClass="col-sm-4 control-label">Website:</form:label> 
				  <div class="col-sm-2">
				     <form:input path="website" value="${company.website}"/>
				  </div>  
			   </div> 			   
		   
		   <%--
			   <div class="form-group">   
				  <form:label path="currentPeriod" cssClass="col-sm-4 control-label">Current Period *:</form:label></td>  
				  <div class="col-sm-2">
				     <form:input path="currentPeriod" value="${company.currentPeriod}" />
				  </div>  
			   </div>  
			   
			   <div class="form-group">   
				  <form:label path="currentYear" cssClass="col-sm-4 control-label">Current Year *:</form:label> 
				  <div class="col-sm-2">
				     <form:input path="currentYear" value="${company.currentYear}"/>
				  </div>  
			   </div> 
			   
			   <div class="form-group">   
				  <form:label path="postDate" cssClass="col-sm-4 control-label">Post Date *:</form:label>  
				  <div class="col-sm-4">
				     <div class="input-group">
				     	<form:input path="postDate" value="${company.postDate}" class="form-control period"  placeholder="dd/mm/yyyy" />
				      	<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
				      </div>
				  </div>  
			   </div>
		  --%>
		
			   <div class="form-group">   
				  <form:label path="connectToEazyCoop" cssClass="col-sm-4 control-label">Connect To Easy Coop *:</form:label> 
				  <div class="col-sm-4">
				     <form:radiobutton path="connectToEazyCoop" value="Y"/>&nbsp; Yes 
				     &nbsp; &nbsp;<form:radiobutton path="connectToEazyCoop" value="N" checked="true"/>&nbsp; No 
				  </div>  
			   </div>
		 		 
			   <div class="form-group">   
				  <form:label path="countryId" cssClass="col-sm-4 control-label">Country *:</form:label> 
				  <div class="col-sm-2">
				      <form:select id="countryId" path="countryId" cssClass="width300" >
					<form:option value="0">--select--</form:option>
					<c:forEach items="${countries}" var="item">  
					     <form:option value="${item.id}">${item.countryName}</form:option>
					</c:forEach>
				      </form:select>
				  </div>  
			   </div>
			   
			   <div class="form-group">   
			     <td colspan="2">
				  <div id="addressContainer"></div
			      </td>  
			   </div> 
                           
                           <input type="hidden" name="ACTION_ID" value="1">
			   <form:hidden path="active" value="Y"/>
			   <form:hidden path="deleted" value="N"/> 

			   <form:hidden path="createdBy" value="<%=request.getRemoteUser()%>"/>
			   <form:hidden path="lastModifiedBy" value="<%=request.getRemoteUser()%>"/>

			   <fmt:formatDate pattern="dd/MM/yyyy" type="date" value="${now}" var="currentDate"/>
			   <form:hidden path="creationDate" id="creationDate" name="creationDate" data-format="dd/MM/yyyy"  value="${currentDate}"/>
			   <form:hidden path="lastModificationDate" id="lastModificationDate" name="lastModificationDate" data-format="dd/MM/yyyy"  value="${currentDate}"/>

			   <div class="form-group">
				    <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">SUBMIT </button>
				    <button type="reset" class="btn btn-default">RESET</button>
			    </div><!-- panel-footer -->
		      </form:form>  
      </div>
        
      <c:if test="${!empty companys}">  
          <h4>LIST OF COOPERATIVES</h4>  
	     <table id="company-list" class="table table-striped table-bordered responsive">  
	      <tr>  
		       <th>ID</th>  
		       <th nowrap>Country Id</th>  
		       <th>Name</th> 
		       <th>Email</th>  
		       <th>Active </th> 
		       <th>Deleted </th> 
		       <th>Action </th> 
	      </tr>  

	      <c:forEach items="${companys}" var="item">  
	       <tr>  
		<td><c:out value="${item.id}"/></td>  
	        <td><c:out value="${item.countryId}"/></td> 
		<td nowrap><c:out value="${item.name}"/></td>  
		<td><c:out value="${item.email}"/></td>  
		<td><c:out value="${item.active}"/></td> 
		<td><c:out value="${item.deleted}"/></td> 

		<td align="center"><a href="editCompany.htm?id=${item.id}">Edit</a></td>  
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
         jQuery('.period').datepicker(); 
    	          
    	 $.datepicker.setDefaults({
    	      dateFormat: 'dd/mm/yy'
    	  });
    	  
         jQuery('select').select2({
                    minimumResultsForSearch: -1
                });
				 jQuery('#company-list').DataTable({
                    responsive: true
                });
    })
</script>