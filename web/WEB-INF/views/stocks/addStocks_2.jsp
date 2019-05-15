<%@ include file="../includes/header.jsp" %> 
<div class="media-body">
    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Stock Management</li>
    </ul>
    <h4>${CompStockType.action} Stock</h4>
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
 		       
		      <form:form method="post" action="saveStock.htm" commandName="CompStockType">
			 <form:errors path="*" cssClass="error"></form:errors>
			   <div class="form-group">   
				  <form:label path="compStockName" cssClass="col-sm-4 control-label">Name:</form:label> 
				  <div class="col-sm-2">
				     <form:input path="compStockName" />
				  </div>  
			   </div>  
			   
			   <div class="form-group">   
				  <form:label path="shortName" cssClass="col-sm-4 control-label">Short Name:</form:label>  
				  <div class="col-sm-2">
				     <form:input path="shortName" />
				  </div>  
			   </div>  
			   <div class="form-group">   
				  <form:label path="defaultStock" cssClass="col-sm-4 control-label">Is Default Member Stock?</form:label>
				  <div class="col-sm-2">
				   <form:select  path="defaultStock">
					<form:option value="">--select--</form:option>
					<form:option value="N">No</form:option>
					<form:option value="Y">Yes</form:option>
					</form:select>
				     <%-- <form:input path="defaultStock" /> --%>
				      <input type="hidden" name="action" value="${CompStockType.action}"/>
				      <input type="hidden" name="createdBy" value="${CompStockType.createdBy}"/>
 					<input type="hidden"  name="delFlg"  value="N"/>
			     <%--    <fmt:formatDate pattern="dd/MM/yyyy" type="date" value="${now}" var="currentDate"/>
			   <input type="hidden"  name="createdDate" data-format="dd/MM/yyyy"  value="${currentDate}"/> --%>
			  		      
				  </div>  
			   </div>
			   
			    <div class="form-group">   
				  <form:label path="stockAcctProd" cssClass="col-sm-4 control-label">Trading Account Product</form:label>
				  <div class="col-sm-2">
				   <form:select  path="stockAcctProd">
					<form:option value="">----------select----------</form:option>
					<form:option value="NEW">--Create New Product --</form:option>
						 <c:forEach items="${referenceList.productList}" var="map">  
								<form:option value="${map['code']}"> ${map['name']} ==== ${map['segement_code']}</form:option>
						  </c:forEach>
					</form:select>			  		      
				  </div>  
			   </div>

           <c:forEach var="ppty" items="${referenceList.stockPptyList}">
           
               
	            <div class="form-group"> 
	            
	            <form:label path="" cssClass="col-sm-4 control-label"> ${ppty.stockPptyDisplay}</form:label>   
	            <div class="col-sm-2">
	            <c:choose> 
	             <c:when test = "${ppty.stockPptyDesc == 'TEXT'}">      
					      <input name="${ppty.stockPptyName}"   id="${ppty.stockPptyName}" type="text" value="${detail[loopCount].stockPptyName}"/>
				  </c:when>
				  
				  <c:when test = "${ppty.stockPptyDesc == 'TEXTAREA' and ppty.stockPptyName =='VOTERIGHTPROP'}">      
					  
					    <select name="stckoperand" id="stckoperand" onChange="" >
	   					 <option value="">---select an option-------</option>
	   					   <c:forEach items="${referenceList.operandList}" var="map">  
								<option value="${map['operand_val']}"> ${map['operand_display']} </option>
						  </c:forEach>
	   					 </select>
	   					 
					  <textarea rows="4" cols="25" name="${ppty.stockPptyName}" id ="${ppty.stockPptyName}" >${detail[loopCount].stockPptyName}</textarea>
				      
				  </c:when>
				  
				  <c:when test = "${ppty.stockPptyDesc == 'TEXTAREA'}">      
					  <textarea rows="4" cols="25" name="${ppty.stockPptyName}" id ="${ppty.stockPptyName}" >${detail[loopCount].stockPptyName}</textarea>
				  </c:when>
				  
				   <c:when test = "${ppty.stockPptyDesc == 'BOOL' and ppty.stockPptyName =='VOTEFORMULA'}">      
					  <select name="${ppty.stockPptyName}" id="${ppty.stockPptyName}" onChange="" >
	   					 <option value="">---select an option-------</option>
	   					  <option value="Y">Yes</option>
	   					  <option value="N">No</option>
	   					 </select>
				  </c:when>
				  <c:otherwise>
	   					 <select name="${ppty.stockPptyName}" id="${ppty.stockPptyName}" >
	   					 <option value="">---select an option-------</option>
	   					  <option value="Y">Yes</option>
	   					  <option value="N">No</option>
	   					 </select>
	   					<%--  <input name="${ppty.stockPptyName}" type="checkbox" value=""/> --%>
	 			 </c:otherwise>
				  
				  </c:choose>
				  </div>  
				  </div>  
		
           </c:forEach>    
                           
			   <div class="form-group">
				    <button class="btn btn-danger mr5" type="submit">SUBMIT </button>
				    <button type="reset" class="btn btn-default">RESET</button>
			    </div><!-- panel-footer -->
		      </form:form>  
      </div>
        
     
            </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->

<%@ include file="../includes/footer.jsp" %>  
<script>
    
    $('#stckoperand').change(function() 
	{
            //if $('#VOTEFORMULA').attr('value').eq("YES")
            //{
                alert('Value now ' + $('#VOTERIGHTPROP').attr('value'));
                 var content = $('#VOTERIGHTPROP').attr('value')+ $(this).attr('value');
                 alert('Value change to ' + content);
                 $('#VOTERIGHTPROP').val(content);
          // }
	});
</script>