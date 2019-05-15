<%@ include file="includes/header.jsp" %> 
<div class="media-body">
       <div style="float:left">
	    <ul class="breadcrumb">
		<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		<li><a href="#">Home</a></li>
		<li>Branch Management</li>
	    </ul>
	    <h4>Manage Branch</h4>
	</div>
	<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<script lanuage="Javascript">
	function doSubmit(frm){	
	    if(frm.companyId.value==""){alert("Company is required"); frm.companyId.focus(); return;}	    
	    if(frm.branchName.value==""){alert("Barnch is required"); frm.branchName.focus(); return;}	
	    
	    if(frm.phone1.value==""){alert("Phone 1 is required"); frm.phone1.focus(); return;}  
	    //if(frm.phone2.value==""){alert("Phone 2 is required"); frm.phone2.focus(); return;}  
	    if(frm.email.value==""){alert("Email is required"); frm.email.focus(); return;}
	    
	    if(frm.currentPeriod.value==""){alert("Current Period is required"); frm.currentPeriod.focus(); return;} 
	    	
	    if(isNaN(frm.currentPeriod.value)){
	    	alert("Numeric value is required for Current Period"); 
	    	frm.currentPeriod.focus(); 
	    	return;
	    }
	    
	    if(frm.currentYear.value==""){alert("Current Year is required"); frm.currentYear.focus(); return;}
	    
	    if(isNaN(frm.currentYear.value)){
	    	alert("Numeric value is required for Current Year"); 
	    	frm.currentYear.focus(); 
	    	return;  
	    }	    
	    
	    if(frm.postDate.value==""){alert("Post Date is required"); frm.postDate.focus(); return;}
	    if(frm.baseCurrency.value==""){alert("Currency is required"); frm.baseCurrency.focus(); return;}
	    
	    frm.submit();
	}	  
</script>
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
		      
		      <form:form method="post" action="saveBranch.htm" commandName="branch">
			  <div class="form-group">  
				  <form:label path="id" cssClass="col-sm-4 control-label">ID:</form:label> 
				  <div class="col-sm-2">
				      <form:input path="id" value="${branch.id}" readonly="true"/>
				   </div>  
			   </div>

			   <div class="form-group">  
				  <form:label path="countryId" cssClass="col-sm-4 control-label">Country:  *</form:label>
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
				  <form:label path="companyId" cssClass="col-sm-4 control-label">Cooperative:  *</form:label>  
				  <div class="col-sm-2">
				      <form:select id="companyId" path="companyId" cssClass="width300">
					<form:option value="0">--select--</form:option>
					<c:forEach items="${companies}" var="item">  
					     <form:option value="${item.id}">${item.name}</form:option>
					</c:forEach>
				      </form:select>
				  </div>  
			   </div>

			   <div class="form-group">  
				  <form:label path="branchName" cssClass="col-sm-4 control-label">Name:  *</form:label>
				  <div class="col-sm-2">
				    <form:input path="branchName" value="${branch.branchName}"/>
				  </div>  
			   </div>  			  
			
			   <div class="form-group">  
				  <form:label path="branchCode" cssClass="col-sm-4 control-label">Branch Code:</form:label>
				  <div class="col-sm-2">
				    <form:input path="branchCode" value="${branch.branchCode}" readonly="true"/>
				  </div>  
			   </div>
						
			   <div class="form-group">  
				  <form:label path="phone1" cssClass="col-sm-4 control-label">Phone 1:  *</form:label>  
				  <div class="col-sm-2">
				    <form:input path="phone1" value="${branch.phone1}"/>
				  </div>  
			   </div>       
			   
			   <div class="form-group">  
				  <form:label path="phone2" cssClass="col-sm-4 control-label">Phone 2:</form:label> 
				  <div class="col-sm-2">
				     <form:input path="phone2" value="${branch.phone2}"/>
				  </div>  
			   </div>
			   
			   <div class="form-group">  
				  <form:label path="email" cssClass="col-sm-4 control-label">Email Address:  *</form:label>  
				  <div class="col-sm-2">
				    <form:input path="email" value="${branch.email}"/>
				  </div>  
			   </div>

			   <div id="addressContainer">
				 <c:if test="${!empty addrEntries}">  
				      <c:forEach items="${addrEntries}" var="item">  
						<div class="form-group">
						    <div class="col-sm-4 control-label"><c:out value="${item.addrFieldName}"/></div>  
						    <div class="col-sm-2"><input type="text" name='<c:out value="${item.addrFieldName}"/>' value='<c:out value="${item.addrFieldValue}"/>'/></div>  
						</div>  
				      </c:forEach>  
				 </c:if>				    
			   </div
			
			   <div class="form-group"> 
				  <form:label path="currentPeriod" cssClass="col-sm-4 control-label">Current Period *:</form:label>
				  <div class="col-sm-2">
				     <form:select id="currentPeriod" path="currentPeriod">
					   <form:option value="">--select--</form:option>
					   <form:option value="1">1</form:option>
					   <form:option value="2">2</form:option>
					   <form:option value="3">3</form:option>
					   <form:option value="4">4</form:option>
					   <form:option value="5">5</form:option>
					   <form:option value="6">6</form:option>
					   <form:option value="7">7</form:option>
					   <form:option value="8">8</form:option>
					   <form:option value="9">9</form:option>
					   <form:option value="10">10</form:option>
					   <form:option value="11">11</form:option>
					   <form:option value="12">12</form:option>
				      </form:select>
				  </div>  
			   </div>

			   <div class="form-group"> 
				  <form:label path="currentYear" cssClass="col-sm-4 control-label">Current Year *:</form:label>
				  <div class="col-sm-2">
				     <form:select id="currentYear" path="currentYear">
					   <form:option value="">--select--</form:option>
					   <form:option value="2010">2010</form:option>
					   <form:option value="2011">2011</form:option>
					   <form:option value="2012">2012</form:option>
					   <form:option value="2013">2013</form:option>					   
					   <form:option value="2014">2014</form:option>		   
					   
					   <form:option value="2015">2015</form:option>
					   <form:option value="2016">2016</form:option>
					   <form:option value="2017">2017</form:option>
					   <form:option value="2018">2018</form:option>
					   <form:option value="2019">2019</form:option>
					   <form:option value="2020">2020</form:option>
					   
				      </form:select>
				  </div>  
			   </div>

			   <fmt:formatDate pattern="dd/MM/yyyy" type="date" value="${branch.postDate}" var="currentDate9"/>
			   <div class="form-group">   
				  <form:label path="postDate" cssClass="col-sm-4 control-label">Post Date *:</form:label>  
				  <div class="col-sm-2">
				     <div class="input-group">
				     	<form:input path="postDate" value="${currentDate9}" class="form-control period"  placeholder="dd/mm/yyyy" />
				      	<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
				      </div>
				  </div>  
			   </div>
			   
			   <div class="form-group">   
				  <form:label path="baseCurrency" cssClass="col-sm-4 control-label">Base Currency *:</form:label> 
				  <div class="col-sm-2">
				      <form:select id="baseCurrency" path="baseCurrency">
					<form:option value="">--select--</form:option>
					<c:forEach items="${currencies}" var="item">  
					     <form:option value="${item.currencyCode}">${item.currencyName}</form:option>
					</c:forEach>
				      </form:select>
				  </div>  
			   </div>
			   
			   <div class="form-group">   
				  <form:label path="connectToEazyCoop" cssClass="col-sm-4 control-label">Connect To Easy Coop *:</form:label> 
				  <div class="col-sm-2">
				     <form:radiobutton path="connectToEazyCoop" value="Y"/>&nbsp; Yes 
				     &nbsp; &nbsp;<form:radiobutton path="connectToEazyCoop" value="N"/>&nbsp; No 
				  </div>  
			   </div>
			   
			   <input type="hidden" name="ACTION_ID" value="2">                	   
			   <form:hidden path="active" value="Y"/>
			   <form:hidden path="deleted" value="N"/> 

			   <form:hidden path="createdBy" value="admin"/>
			   <form:hidden path="lastModifiedBy" value="admin"/>

			   <fmt:formatDate pattern="dd/MM/yyyy" type="date" value="${now}" var="currentDate"/>
			   <form:hidden path="creationDate" id="creationDate" name="creationDate" data-format="dd/MM/yyyy"  value="${currentDate}"/>
			   <form:hidden path="lastModificationDate" id="lastModificationDate" name="lastModificationDate" data-format="dd/MM/yyyy"  value="${currentDate}"/>

			   <div class="form-group">
			   <button class="btn btn-danger mr5" type="button" onClick="Javascript:doSubmit(this.form);">UPDATE </button>
				    <button type="reset" class="btn btn-default">RESET</button>
			    </div><!-- panel-footer -->  

		      </form:form>  
      </div>
      <p>&nbsp;</p>    
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
			<td align="center"><a href="editBranch.htm?id=${item.id}">Edit</a> </td>  
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
				 jQuery('#branch-list').DataTable({
                    responsive: true
                });
    })
</script>