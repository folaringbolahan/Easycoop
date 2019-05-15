<%@ include file="includes/header.jsp" %>  

<div class="media-body">
       <div style="float:left">
	    <ul class="breadcrumb">
		<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		<li><a href="#">Home</a></li>
		<li>Currency Management</li>
	    </ul>
	    <h4>Manage Currency</h4>
	</div>
	<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<script lanuage="Javascript">
	function doSubmit(frm){	
	    if(frm.currencyCode.value==""){alert("currency code is required"); frm.currencyCode.focus(); return;}
	    if(frm.currencyName.value==""){alert("currency name is required"); frm.currencyName.focus(); return;}
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
         <form:form method="post" action="saveCurrency.htm" commandName="currency">		
	     <div class="form-group">
		<form:label path="id" cssClass="col-sm-4 control-label">ID:</form:label>
		<div class="col-sm-2">
		      <form:input path="id" value="${currency.id}" readonly="true"/>
		</div> 
	      </div>	      
	      <div class="form-group">
		   <form:label path="currencyCode" cssClass="col-sm-4 control-label">Currency Code:</form:label>
		   <div class="col-sm-2">
			<form:input path="currencyCode" value="${currency.currencyCode}"/>
			<form:errors path="currencyCode" cssClass="error"></form:errors>
		   </div>
              </div>              
	      <div class="form-group">
		   <form:label path="currencyName" cssClass="col-sm-4 control-label">Currency Name:</form:label> 
		    <div class="col-sm-2">
            		<form:input path="currencyName" value="${currency.currencyName}"/>
            		<form:errors path="currencyName" cssClass="error"></form:errors>
		    </div>
           </div>
	   
           <input type="hidden" name="ACTION_ID" value="2">
           <form:hidden path="isBase" value="Y"/>
           <form:hidden path="active" value="Y"/>
           <form:hidden path="deleted" value="N"/>  

           <form:hidden path="createdBy" value="<%=request.getRemoteUser()%>"/>
           <form:hidden path="lastModifiedBy" value="<%=request.getRemoteUser()%>"/>
           <form:hidden path="creationDate" id="creationDate" name="creationDate" data-format="dd/MM/yyyy"  value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>
           <form:hidden path="lastModificationDate" id="lastModificationDate" name="lastModificationDate"   value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>
            <div class="form-group">
                            <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">UPDATE CURRENCY</button>
                            <button type="reset" class="btn btn-default">RESET</button>
                        </div><!-- panel-footer -->       
            
      </form:form>  
        </div>
      <c:if test="${!empty currencys}">  
          <h4>LIST OF CURRENCIES</h4>  
	     <table id="currency-list" class="table table-striped table-bordered responsive">  
		 <thead>
		      <tr>  
		       <th>ID</th>  
		       <th>Code</th>  
		       <th>Name</th>  
		    <%--
		        <th>Is Base</th>  
		       <th>Active </th> 
		       <th>Deleted </th> 
		       <th>Action </th>  
		     --%>
		      </tr>  
		</thead>
		<tbody>
		    <c:forEach items="${currencys}" var="currency">  
		       <tr>  
			<td><c:out value="${currency.id}"/></td>  
			<td><c:out value="${currency.currencyCode}"/></td>  
			<td><c:out value="${currency.currencyName}"/></td>  
		   <%--
		        <td><c:out value="${currency.isBase}"/></td>  
			<td><c:out value="${currency.active}"/></td> 
			<td><c:out value="${currency.deleted}"/></td> 
		   --%>
			<td align="center"><a href="editCurrency.htm?id=${currency.id}">Edit</a></td>  
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
				 jQuery('#currency-list').DataTable({
                    responsive: true
                });
    })
</script>
   