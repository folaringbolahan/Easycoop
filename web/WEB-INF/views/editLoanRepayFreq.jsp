<%@ include file="includes/header.jsp" %> 
<div class="media-body">
	  <div style="float:left">
	    <ul class="breadcrumb">
		<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		<li><a href="#">Home</a></li>
		<li>Loan Repay Frequency Management</li>
	    </ul>
	    <h4>Manage Loan Repay Frequency</h4>
	</div>
	<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
	       <div class="panel-body">
                 <c:set var="now" value="<%=new java.util.Date()%>" />      
                 <form:form method="post" action="saveLoanRepayFreq.htm" commandName="loanRepayFreq">
			  <div class="form-group"> 
				<form:label path="id" cssClass="col-sm-4 control-label">ID:</form:label> 
				  <div class="col-sm-2">
				     <form:input path="id" value="${loanRepayFreq.id}" readonly="true"/>
				  </div>  
			   </div>  
			   <div class="form-group"> 
				  <form:label path="name" cssClass="col-sm-4 control-label">Name:</form:label>  
				  <div class="col-sm-2">
				      <form:input path="name" value="${loanRepayFreq.name}"/>
				  </div>  
			   </div>  

			   <form:hidden path="active" value="Y"/>
			   <form:hidden path="deleted" value="N"/> 
			   
           		   <input type="hidden" name="ACTION_ID" value="2">
	
			   <form:hidden path="createdBy" value="admin"/>
			   <form:hidden path="lastModifiedBy" value="admin"/>

			   <form:hidden path="creationDate" value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>
			   <form:hidden path="lastModificationDate" value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>
           
		    <div class="form-group">
			    <button class="btn btn-danger mr5" type="submit">UPDATE</button>
			    <button type="reset" class="btn btn-default">RESET</button>
		    </div><!-- panel-footer -->   
      </form:form>  
 </div> 
 
      <c:if test="${!empty loanRepayFreqs}">  
          <h4>LIST OF LOAN REPAYMENT FREQUENCIES</h4>  
	     <table id="loanrepay-list" class="table table-striped table-bordered responsive">  
	      <tr>  
	       <th>ID</th>  
	       <th>Name</th>  
	       <th>Active </th> 
	       <th>Deleted </th> 
	       <th>Action </th> 
	      </tr>  

	      <c:forEach items="${loanRepayFreqs}" var="item">  
	       <tr>  
		<td><c:out value="${item.id}"/></td>  
		<td><c:out value="${item.name}"/></td>  
		<td><c:out value="${item.active}"/></td> 
		<td><c:out value="${item.deleted}"/></td> 

		<td align="center"><a href="editLoanRepayFreq.htm?id=${item.id}">Edit</a> </td>  
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
				 jQuery('#loanrepay-list').DataTable({
                    responsive: true
                });
    })
</script>
   