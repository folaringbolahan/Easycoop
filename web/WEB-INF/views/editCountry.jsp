<%@ include file="includes/header.jsp" %>  

<div class="media-body">
       <div style="float:left">
	    <ul class="breadcrumb">
		<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		<li><a href="#">Home</a></li>
		<li>Country Management</li>
	    </ul>
	    <h4>Manage Country</h4>
	</div>
	<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<script lanuage="Javascript">
       function doSubmit(frm){	
	    if(frm.currencyCode.value=="0"){alert("please select Currency Code"); frm.currencyCode.focus(); return;}	    
	    frm.submit();
       }	  
</script>
<div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-12">
		<div class="panel-body">
                 <c:set var="now" value="<%=new java.util.Date()%>" />
      
           <form:form method="post" action="saveCountry.htm" commandName="country">		
		 <div class="form-group">
		        <form:label path="id" cssClass="col-sm-2 control-label">ID:</form:label>		
			
			<div class="col-sm-2">
			     <form:input path="id" value="${country.id}" readonly="true"/>
			</div> 
         	  </div>
         	
		 <div class="form-group">
		   <form:label path="countryCode" cssClass="col-sm-2 control-label">Country Code:</form:label>
		   <div class="col-sm-10">
		       <form:input path="countryCode" value="${country.countryCode}"/> 
		       <form:errors path="countryCode" cssClass="error"></form:errors>
		   </div>
                 </div>
		 
		 <div class="form-group">
		   <form:label path="countryName" cssClass="col-sm-2 control-label">Country Name:</form:label> 
		    <div class="col-sm-10">
            		<form:input path="countryName" value="${country.countryName}"/>
            		<form:errors path="countryName" cssClass="error"></form:errors>
		     </div>
                 </div>
                
		 <div class="form-group">   
		     <form:label path="currencyCode" cssClass="col-sm-2 control-label">Currency:</form:label> 
		     <div class="col-sm-10">
		       <form:select cssClass="width300" id="currencyCode" path="currencyCode">
			<form:option value="0">--select--</form:option>
			    <c:forEach items="${currencies}" var="item">  
			         <form:option value="${item.currencyCode}">${item.currencyName}</form:option>
			    </c:forEach>
		      </form:select>
		     </div>  
		 </div>
           
                <input type="hidden" name="ACTION_ID" value="2">    
		<form:hidden path="active" value="Y"/>
		<form:hidden path="deleted" value="N"/>  

		<form:hidden path="createdBy" value="admin"/>
		<form:hidden path="lastModifiedBy" value="admin"/>
	        <form:hidden path="lastModificationDate" id="lastModificationDate" name="lastModificationDate"   value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>

		   <div class="form-group">
		      <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">MODIFY COUNTRY</button>
		      <button type="reset" class="btn btn-default">RESET</button>
		   </div><!-- panel-footer -->
       
            
      </form:form>  
        </div>
      <c:if test="${!empty countrys}">  
          <h4>LIST OF COUNTRIES</h4>  
	     <table id="country-list" class="table table-striped table-bordered responsive">  
		 <thead>
	      <tr>  
	       <th>ID</th>  
	       <th>Code</th>  
	       <th>Name</th>  
	     <%--
	       <th>Active </th> 
	       <th>Deleted </th> 
	     --%>
	       <th>Action </th> 
	      </tr>  
</thead>
<tbody>
	      <c:forEach items="${countrys}" var="country">  
	       <tr>  
		<td><c:out value="${country.id}"/></td>  
		<td><c:out value="${country.countryCode}"/></td>  
		<td><c:out value="${country.countryName}"/></td>  
	 <%--
	 	<td><c:out value="${country.active}"/></td> 
		<td><c:out value="${country.deleted}"/></td> 
	 --%>
		<td align="center"><a href="editCountry.htm?id=${country.id}">Edit</a></td>  
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
				 jQuery('#country-list').DataTable({
                    responsive: true
                });
    })
</script>
   