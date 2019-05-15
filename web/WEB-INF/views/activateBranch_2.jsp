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
					//trying to reset values
					//$('#addressContainer').html('&nbsp;');
					function(){
						//alert($(this).val());
						$.getJSON('countryAdressItemAjaxList.do',{
							countryId : $(this).val(),
							ajax : 'true'
						}, function(data){
							var  len = -1;
							var  html='';
							//alert("data"+data);

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
		      <form:form method="post" action="doUpdateBranchActivationStatus.htm" commandName="branch">
			   <div class="form-group">  
				  <form:label path="countryId" cssClass="col-sm-4 control-label">Country:</form:label>
				  <div class="col-sm-2">
				      <form:select id="countryId" path="countryId" disabled="true"  cssClass="width300">
					<form:option value="0">--select--</form:option>
					<c:forEach items="${countries}" var="item">  
					     <form:option value="${item.id}">${item.countryName}</form:option>
					</c:forEach>
				      </form:select>
				  </div>  
			   </div>        

			   <div class="form-group">  
				  <form:label path="companyId" cssClass="col-sm-4 control-label">Cooperative:</form:label>  
				  <div class="col-sm-2">
				      <form:select id="companyId" path="companyId"  disabled="true"  cssClass="width300">
					<form:option value="0">--select--</form:option>
					<c:forEach items="${companies}" var="item">  
					     <form:option value="${item.id}">${item.name}</form:option>
					</c:forEach>
				      </form:select>
				  </div>  
			   </div>

			   <div class="form-group">  
				  <form:label path="branchName" cssClass="col-sm-4 control-label">Name:</form:label>
				  <div class="col-sm-2">
				    <form:input path="branchName" value="${branch.branchName}" readonly="true"/>
				  </div>  
			   </div>  
			   
			   <div class="form-group">  
				  <form:label path="branchCode" cssClass="col-sm-4 control-label">Branch Code:</form:label>
				  <div class="col-sm-2">
				    <form:input path="branchCode" value="${branch.branchCode}"  readonly="true"/>
				  </div>  
			   </div>

			   <div class="form-group">  
				  <form:label path="phone1" cssClass="col-sm-4 control-label">Phone 1:</form:label>  
				  <div class="col-sm-2">
				    <form:input path="phone1" value="${branch.phone1}" readOnly="true"/>
				  </div>  
			   </div>       
			   
			   <div class="form-group">  
				  <form:label path="phone2" cssClass="col-sm-4 control-label">Phone 2:</form:label> 
				  <div class="col-sm-2">
				     <form:input path="phone2" value="${branch.phone2}" readonly="true"/>
				  </div>  
			   </div>
			   
			   <div class="form-group">  
				  <form:label path="email" cssClass="col-sm-4 control-label">Email Address:</form:label>  
				  <div class="col-sm-2">
				    <form:input path="email" value="${branch.email}" readonly="true"/>
				  </div>  
			   </div>

			   <div id="addressContainer">
				 <c:if test="${!empty addrEntries}">  
				      <c:forEach items="${addrEntries}" var="item">  
						<div class="form-group">
						    <div class="col-sm-4 control-label"><c:out value="${item.addrFieldName}"/></div>  
						    <div class="col-sm-2"><input type="text" readonly="true" name='<c:out value="${item.addrFieldName}"/>' value='<c:out value="${item.addrFieldValue}"/>'/></div> 
						</div>  
				      </c:forEach>  
				 </c:if>				    
			    </div			    
			    
			    <div class="form-group">  
				  <form:label path="active" cssClass="col-sm-4 control-label">Active Status:</form:label>  
				  <div class="col-sm-2">
				      <form:select id="active" path="active"  cssClass="width300">
					     <form:option value="Y">Yes</form:option>
					     <form:option value="N"  selected="true">No</form:option>
				      </form:select>
				  </div>  
			    </div>
           
                	   <input type="hidden" name="ACTION_ID" value="2">
			   <form:hidden path="id" value="${branch.id}"/>
			   <form:hidden path="deleted" value="N"/>
			   <form:hidden path="createdBy" value="admin"/>
			   <form:hidden path="lastModifiedBy" value="admin"/>

			   <fmt:formatDate pattern="dd/MM/yyyy" type="date" value="${now}" var="currentDate"/>
			   <form:hidden path="creationDate" id="creationDate" name="creationDate" data-format="dd/MM/yyyy"  value="${currentDate}"/>
			   <form:hidden path="lastModificationDate" id="lastModificationDate" name="lastModificationDate" data-format="dd/MM/yyyy"  value="${currentDate}"/>

			   <div class="form-group">
			   <button class="btn btn-danger mr5" type="submit"> SUBMIT </button>
				    <button type="reset" class="btn btn-default">RESET</button>
			    </div><!-- panel-footer -->  

		      </form:form>  
              </div>     
      
            </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->
<%@ include file="includes/footer.jsp" %>  
