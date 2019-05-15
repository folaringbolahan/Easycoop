<%@ include file="includes/header.jsp" %>  
<div class="media-body">
       <div style="float:left">
	    <ul class="breadcrumb">
		<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		<li><a href="#">Home</a></li>
		<li>Country Address Mapping Management</li>
	    </ul>
	    <h4>Manage Country Address Mapping</h4>
	</div>
	<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
  <script lanuage="Javascript">
	function doSubmit(frm){	
	    if(frm.countryId.value=="" || frm.countryId.value=="0"){alert("please specify country"); frm.countryId.focus(); return;}
	    if(frm.addrFieldName.value=="0"){alert("please select address field"); frm.addrFieldName.focus(); return;}	    
	    if(frm.addrFieldIndx.value==""){alert("Field Position is required"); frm.addrFieldIndx.focus(); return;}
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
          
          	<form:form method="post" action="saveCountryAddrFilter.htm" commandName="countryAdrFilter">
		  <div class="form-group">  
			  <form:label path="id" cssClass="col-sm-4 control-label">ID:</form:label> 
			  <div class="col-sm-2">
			     <form:input path="id" value="${countryAdrFilter.id}" readonly="true"/>
			  </div>  
		   </div>
		   <div class="form-group">  
			  <form:label path="countryId" cssClass="col-sm-4 control-label">Country *:</form:label>  
			  <div class="col-sm-2">
			      <form:select id="countryId" path="countryId" cssClass="width300">
				<c:forEach items="${countries}" var="item">  
				     <form:option value="${item.id}">${item.countryName}</form:option>
				</c:forEach>
			      </form:select>
			  </div>  
		   </div>  

		   <div class="form-group">  
			  <form:label path="addrFieldName" cssClass="col-sm-4 control-label">Field Name *:</form:label>  
			  <div class="col-sm-2">
			      <form:select id="addrFieldName" path="addrFieldName" cssClass="width300">
				<c:forEach items="${addressItems}" var="item">  
				     <form:option value="${item.addrFieldName}">${item.addrFieldName}</form:option>
				</c:forEach>
			      </form:select>
			  </div>  
		   </div> 

		   <div class="form-group">  
			  <form:label path="addrFieldIndx" cssClass="col-sm-4 control-label">Field Position *:</form:label> 
			  <div class="col-sm-2">
			      <form:input path="addrFieldIndx" value="${countryAdrFilter.addrFieldIndx}"/>
			  </div>  
		   </div> 
		   
		   <input type="hidden" name="ACTION_ID" value="2">

	      	<div class="form-group">
		    <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">UPDATE</button>
		    <button type="reset" class="btn btn-default">RESET</button>
	    	</div><!-- panel-footer -->       

	 </form:form>  
     </div>

      <c:if test="${!empty countryAddressFilters}">  
          <h4>LIST COUNTRY ADDRESS MAPPING</h4>  
	     <table id="countryAddr" class="table table-striped table-bordered responsive">  
	      <tr>  
	       <th>ID</th>  
	       <th>Country Name</th>  
	       <th>Pos</th> 
	       <th>Name</th>	       
	       <th>Action </th> 
	      </tr>  

	      <c:forEach items="${countryAddressFilters}" var="item">  
	       <tr>  
		<td><c:out value="${item.id}"/></td>  
	        <td><c:out value="${item.countryName}"/></td> 		  
		<td><c:out value="${item.addrFieldIndx}"/></td>  
		<td><c:out value="${item.addrFieldName}"/></td>
		<td align="center"><a href="editCountryAddrFilter.htm?id=${item.id}">Edit</a></td>  
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
         jQuery('select').select2({
                    minimumResultsForSearch: -1
                });
				 jQuery('#countryAddr-list').DataTable({
                    responsive: true
                });
    })
</script>
   