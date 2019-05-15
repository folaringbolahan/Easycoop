<%@ include file="includes/header.jsp" %> 
<div class="media-body">
<div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Fiscal Period Management</li>
    </ul>
    <h4>Manage Fiscal Period</h4>
</div>
<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
  <script lanuage="Javascript">
	function doSubmit(frm){	
	    if(frm.companyId.value=="" || frm.companyId.value=="0"){alert("please specify cooperative"); frm.companyId.focus(); return;}
	    if(frm.year.value==""){alert("Fiscal year is required"); frm.year.focus(); return;}
	    if(frm.noOfPeriods.value=="0"){alert("please select no of periods"); frm.noOfPeriods.focus(); return;}	    
	    frm.submit();
	}	  
  </script>
<div class="contentpanel">
    <div class="row">
        <div class="col-md-10">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
	       <div class="panel-body">
                 <c:set var="now" value="<%=new java.util.Date()%>" />
		      
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-i18n.min.js"></script> 
                      
                      <%--
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
			
		      <form:form method="post" action="newFiscalP.htm" commandName="fiscalP">
			   <%--<div class="form-group">  
				  <form:label path="id" cssClass="col-sm-3 control-label">ID:</form:label>  
				  <div class="col-sm-8">
				     <form:input path="id" value="${fiscalP.id}" readonly="true"/>
				  </div>  
			   </div>
			  --%>
			  
			   <div class="form-group">  
				  <form:label path="companyId" cssClass="col-sm-3 control-label">Select Cooperative:</form:label>  
				  <div class="col-sm-8">
				      <form:select id="companyId" path="companyId">
					<c:forEach items="${companies}" var="item">  
					     <form:option value="${item.id}">${item.name}</form:option>
					</c:forEach>
				      </form:select>
				      <form:errors path="companyId" cssClass="error"></form:errors>
				  </div>  
			   </div> 
			   
			   <div class="form-group">  
				  <form:label path="branchId" cssClass="col-sm-3 control-label">Select Branch:</form:label>  
				  <div class="col-sm-8">
				      <form:select id="branchId" path="branchId">
					<c:forEach items="${branches}" var="item">  
					     <form:option value="${item.id}">${item.branchName}</form:option>
					</c:forEach>
				      </form:select>
				      <form:errors path="branchId" cssClass="error"></form:errors>
				  </div>  
			   </div> 
			   
			   <div class="form-group">  
				  <form:label path="year" cssClass="col-sm-3 control-label">Year:</form:label>
				  <div class="col-sm-8">
				     <form:input path="year" value="${fiscalP.year}"/>
				     <form:errors path="year" cssClass="error"></form:errors>
				  </div>  
			   </div> 
			   
			   <div class="form-group"> 
				  <form:label path="noOfPeriods" cssClass="col-sm-3 control-label">No Of Periods:</form:label>
				  <div class="col-sm-8">
				     <form:select id="noOfPeriods" path="noOfPeriods">
				           <form:option value="0">--select--</form:option>
					   <form:option value="12">12</form:option>
					   <form:option value="13">13</form:option>
				      </form:select>
				  </div>  
			   </div> 
			   
                           <input type="hidden" name="ACTION_ID" value="1">
			   <form:hidden path="active" value="Y"/>
           <div class="form-group">
		    <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">SUBMIT </button>
		    <button type="reset" class="btn btn-default">RESET</button>
	    </div><!-- panel-footer -->       
            
      </form:form>  
        </div>
        
      <c:if test="${!empty fiscalPeriods}">  
          <h4>LIST OF FISCAL PERIODS</h4>  
	     <table id="fiscalperiod-list" class="table table-striped table-bordered responsive">  
	      <thead>
		      <tr>  
			       <th>ID</th>  
			       <th>Coop Code</th>  
			       <th>Year </th> 
			       <th>No Of Periods </th> 
			       <th>Active </th> 
			       <th>Action </th> 
		      </tr>
	      </thead>  

	      <c:forEach items="${fiscalPeriods}" var="fiscal">  
	       <tr>  
		<td><c:out value="${fiscal.id}"/></td>  
		<td><c:out value="${fiscal.companyId}"/></td>  
		<td><c:out value="${fiscal.year}"/></td> 
		<td><c:out value="${fiscal.noOfPeriods}"/></td>  
		<td><c:out value="${fiscal.active}"/></td> 
		<td align="center"><a href="editFiscalP.htm?id=${fiscal.id}">Edit</a></td>  
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
				 jQuery('#fiscalperiod-list').DataTable({
                    responsive: true
                });
    })
</script>
