<%@ include file="includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">
    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Tax Management</li>
    </ul>
    <h4>Manage Tax</h4>
</div>
<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
  <script lanuage="Javascript">
	function doSubmit(frm){	
	    if(frm.taxCode.value==""){alert("tax code is required"); frm.taxCode.focus(); return;}
	    if(frm.taxName.value==""){alert("tax name is required"); frm.taxName.focus(); return;}
	    if(frm.taxDescription.value==""){alert("tax description is required"); frm.taxDescription.focus(); return;}
	    if(frm.rate.value==""){alert("tax rate is required"); frm.rate.focus(); return;}
	    if(frm.countryId.value=="0"){alert("country is required"); frm.countryId.focus(); return;}
	    //if(frm.taxGroupId.value=="0"){alert("tax group is required"); frm.taxGroupId.focus(); return;}

	    frm.submit();
	}	  
  </script>
<div class="contentpanel">
    <div class="row">
        <div class="col-md-10">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-15">
		<div class="panel-body">
                 <c:set var="now" value="<%=new java.util.Date()%>" />
            <form:form method="post" action="saveTax.htm" commandName="tax">		
		<%--
		  <div class="form-group">
			<form:label path="id" cssClass="col-sm-4 control-label">ID:</form:label>
			<div class="col-sm-2">
			      <form:input path="id" value="${tax.id}" readonly="true"/>
			</div> 
                  </div>
                --%>
                
		<div class="form-group">
		   <form:label path="taxCode" cssClass="col-sm-4 control-label">Tax Code *:</form:label>
		   <div class="col-sm-2">
		   <form:input path="taxCode" value="${tax.taxCode}" data-toggle="tooltip" title="Enter a unique code to identify the tax to setup " cssClass="tooltips"/> 
		   <form:errors path="taxCode" cssClass="error"></form:errors>
		   </div>
                </div>
                
		<div class="form-group">
		   <form:label path="taxName" cssClass="col-sm-4 control-label">Tax Name *:</form:label>
		   <div class="col-sm-2">
		   <form:input path="taxName" value="${tax.taxName}" data-toggle="tooltip" title="Enter the name of the tax to setup here" cssClass="tooltips"/> 
		   <form:errors path="taxName" cssClass="error"></form:errors>
		   </div>
                </div>
                
		<div class="form-group">
		   <form:label path="taxDescription" cssClass="col-sm-4 control-label">Tax Description *:</form:label>
		   <div class="col-sm-2">
		   <form:input path="taxDescription" value="${tax.taxDescription}" data-toggle="tooltip" title="Enter a brief description of the tax here" cssClass="tooltips"/> 
		   <form:errors path="taxDescription" cssClass="error"></form:errors>

		   </div>
                </div>			

		<div class="form-group">
		   <form:label path="rate" cssClass="col-sm-4 control-label">Rate *:</form:label>
		   <div class="col-sm-2">
		   <form:input path="rate" value="${tax.rate}" data-toggle="tooltip" title="Specify the Rate in percent(%) here" cssClass="tooltips"/> 
		   </div>
                </div>  
          
          <%--    
		<div class="form-group">  
			  <form:label path="companyId" cssClass="col-sm-4 control-label">Select Cooperative:</form:label>  
			  <div class="col-sm-2">
			      <form:select id="companyId" path="companyId">
				<form:option value="">--select--</form:option>
				<c:forEach items="${companies}" var="item">  
				     <form:option value="${item.id}">${item.name}</form:option>
				</c:forEach>
			      </form:select>
			      <form:errors path="companyId" cssClass="error"></form:errors>
			  </div>  
		 </div> 
		 
		 <div class="form-group">   
			  <form:label path="branchId" cssClass="col-sm-4 control-label">Branch</form:label>
			  <div class="col-sm-2">
			      <form:select id="branchId" path="branchId">
				 <form:option value="">--select--</form:option>
				 <c:forEach items="${branches}" var="item">  
					<form:option value="${item.id}">${item.branchName}</form:option>
				 </c:forEach>
			      </form:select>
			  </div>  
		</div>
	  --%>
	  
		<div class="form-group">   
		  <form:label path="countryId" cssClass="col-sm-4 control-label">Country *:</form:label> 
		  <div class="col-sm-2">
		      <form:select id="countryId" path="countryId"  cssClass="width300">
			<form:option value="0">--select--</form:option>
			<c:forEach items="${countries}" var="item">  
			     <form:option value="${item.id}">${item.countryName}</form:option>
			</c:forEach>
		      </form:select>
		   </div>  
	        </div>
          <%--
	        <div class="form-group">   
		  <form:label path="taxGroupId" cssClass="col-sm-4 control-label">Tax Group:</form:label> 
		  <div class="col-sm-2">
		      <form:select id="taxGroupId" path="taxGroupId">
			<form:option value="0">--select--</form:option>
			<c:forEach items="${taxgroups}" var="item">  
			     <form:option value="${item.code}">${item.description}</form:option>
			</c:forEach>
		      </form:select>
		   </div>  
	        </div>
 	 --%>
 	 
           <input type="hidden" name="ACTION_ID" value="1">

           <form:hidden path="active" value="Y"/>
           <form:hidden path="deleted" value="N"/>  

           <form:hidden path="createdBy" value="<%=request.getRemoteUser()%>"/>
           <form:hidden path="lastModifiedBy" value="<%=request.getRemoteUser()%>"/>
           
           <form:hidden path="branchId" value="${tax.branchId}"/>
           <form:hidden path="companyId" value="${tax.companyId}"/>

           <form:hidden path="creationDate" id="creationDate" name="creationDate" data-format="dd/MM/yyyy"  value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>
           <form:hidden path="lastModificationDate" id="lastModificationDate" name="lastModificationDate"   value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>
           
           <div class="form-group">
	      <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">ADD TAX</button>
	      <button type="reset" class="btn btn-default">RESET</button>
	   </div><!-- panel-footer -->
       
            
      </form:form>  
        </div>
      <c:if test="${!empty taxs}">  
          <h2>List Of Taxes</h2>  
	     <table id="tax-list" class="table table-striped table-bordered responsive">  
		 <thead>
	      <tr>  
	       <th>ID</th>  
	       <th>Code</th>  
	       <th>Name</th>  
	       <th>Rate</th>  
	       <th>Active </th> 
	       <th>Action </th> 
	      </tr>  
</thead>
<tbody>
	      <c:forEach items="${taxs}" var="tax">  
	       <tr>  
		<td><c:out value="${tax.id}"/></td>  
		<td><c:out value="${tax.taxCode}"/></td>  
		<td><c:out value="${tax.taxName}"/></td>  
		<td><c:out value="${tax.rate}"/></td>  
		<td><c:out value="${tax.active}"/></td> 
		<td align="center"><a href="editTax.htm?id=${tax.id}">Edit</a></td>  
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
				 jQuery('#tax-list').DataTable({
                    responsive: true
                });
    })
</script>
   