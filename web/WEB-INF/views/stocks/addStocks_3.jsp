<%@ include file="../includes/header.jsp" %> 
<div class="media-body">
      <div style="float:left">
    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Stock Management</li>
    </ul>
    <h4>${CompStockType.action} Stock</h4>
     </div>
     <%@include file="../includes/topright.jsp" %>
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
			   <div class="form-group" >   
				  <form:label path="compStockName" class="col-sm-4 control-label tooltips" data-original-title="A Unique name for the stock across the company" data-toggle="tooltip" data-placement="left">Name*:</form:label> 
				  <div class="col-sm-2">
				     <form:input path="compStockName" />
                                     
                                     <div class="error">
                                                        <form:errors path="compStockName" />
                                                      </div>
				  </div>  
			   </div>  
			   
		<%--	   <div class="form-group">   
				  <form:label path="shortName" cssClass="col-sm-4 control-label">Short Name:</form:label>  
				  <div class="col-sm-2">
				     <form:input path="shortName" />
				  </div>  
			   </div>  
                --%>       
                           <div class="form-group" >   
				  <form:label path="registerStock" class="col-sm-4 control-label tooltips" data-original-title="This is for registration, all member must have at a minimum if defined.Maximum Value is 1" data-toggle="tooltip" data-placement="left">Registration</form:label>
				  <div class="col-sm-2">
				   <form:select  path="registerStock">
					<form:option value="">--select--</form:option>
					<form:option value="N">No</form:option>
					<form:option value="Y">Yes</form:option>
					</form:select>
                                        <div class="error">
                                                        <form:errors path="registerStock" />
                                        </div>		      
				  </div>  
			   </div>
                             
			   <div class="form-group" >   
				  <form:label path="defaultStock" class="col-sm-4 control-label tooltips" data-original-title="Property to tell if every member should have this stock" data-toggle="tooltip" data-placement="left">Is Default Member Stock?*</form:label>
				  <div class="col-sm-2">
				   <form:select  path="defaultStock">
					<form:option value="">--select--</form:option>
					<form:option value="N">No</form:option>
					<form:option value="Y">Yes</form:option>
					</form:select>
                                        <div class="error">
                                                        <form:errors path="defaultStock" />
                                                      </div>
				     <%-- <form:input path="defaultStock" /> --%>
				      <input type="hidden" name="action" value="${CompStockType.action}"/>
				      <input type="hidden" name="createdBy" value="${CompStockType.createdBy}"/>
 					<input type="hidden"  name="delFlg"  value="N"/>
                                       <input type="hidden" name="company.id" value="${CompStockType.company.id}"/>
			     <%--    <fmt:formatDate pattern="dd/MM/yyyy" type="date" value="${now}" var="currentDate"/>
			   <input type="hidden"  name="createdDate" data-format="dd/MM/yyyy"  value="${currentDate}"/> --%>
			  		      
				  </div>  
			   </div>
			   
                             <div class="form-group" >   
				  <form:label path="stockAcctProd" class="col-sm-4 control-label tooltips" data-original-title="The Product stock is assocaited with." data-toggle="tooltip" data-placement="left">Trading Account Product *</form:label>
				  <div class="col-sm-2">
				   <form:select  path="stockAcctProd">
					<form:option value="">----------select----------</form:option>
					<form:option value="NEW">--Create New Product --</form:option>
						 <c:forEach items="${referenceList.productList}" var="map">  
								<form:option value="${map['code']}"> ${map['name']} ==== ${map['segement_code']}</form:option>
						  </c:forEach>
					</form:select>	
                                        <div class="error">
                                                        <form:errors path="stockAcctProd" />
                                                      </div>
				  </div>  
			   </div>
                                  
                             <div class="form-group" >   
				  <form:label path="stockControlAcct" class="col-sm-4 control-label tooltips" data-original-title="Control account for Stock product" data-toggle="tooltip" data-placement="left">Control Account *</form:label>
				  <div class="col-sm-2">
				   <form:select  path="stockControlAcct">
					<form:option value="">----Select Control Account---------</form:option>
                                    <c:forEach items="${referenceList.controlAcctList}" var="map">  
                                        <form:option value="${map['accountno']}"> ${map['name']} ==== ${map['accountno']}</form:option>
                                     </c:forEach>
					</form:select>
                                        
                                         <div class="error">
                                                        <form:errors path="stockControlAcct" />
                                         </div>
				  </div>  
			   </div>
                                  
                                  

           <c:forEach var="ppty" items="${referenceList.stockPptyList}">
               
               <c:forEach var="detail" items="${CompStockType.compStockTypeDetails}" varStatus="loopCount">
                        ${detail.compStockProperty.stockPptyName}
                        ${detail.compStockPptyVal}
                        ${ppty.stockPptyName}
                     <c:if test=" ${detail.compStockProperty.stockPptyName == ppty.stockPptyName}">
                         <c:set var="pptyVal" value="${detail.compStockPptyVal}"/>
                      </c:if>
		</c:forEach>
           
               
	            <div class="form-group" >
	            <form:label path="" class="col-sm-4 control-label tooltips" data-original-title="${ppty.stockPptyTip}" data-toggle="tooltip" data-placement="left"> ${ppty.stockPptyDisplay}</form:label>   
	            <div class="col-sm-2">
	              <c:choose> 
	             		  <c:when test = "${ppty.stockPptyDesc == 'TEXT'}">      
					      <input name="${ppty.stockPptyName}"   id="${ppty.stockPptyName}" type="text" value="${pptyVal}"/>
				  </c:when>
				  
				  <c:when test = "${ppty.stockPptyDesc == 'TEXTAREA' and ppty.stockPptyName =='VOTERIGHTPROP'}">      
					  
					    <select name="stckoperand" id="stckoperand" onChange="" >
	   					 <option value="">---select an option-------</option>
	   					   <c:forEach items="${referenceList.operandList}" var="map">  
								<option value="${map['operand_val']}"> ${map['operand_display']} </option>
						  </c:forEach>
	   					 </select>
	   					 
					  <textarea rows="4" cols="25" name="${ppty.stockPptyName}" id ="${ppty.stockPptyName}" >${pptyVal}</textarea>
				      
				  </c:when>
				  
				  <c:when test = "${ppty.stockPptyDesc == 'TEXTAREA'}">      
					  <textarea rows="4" cols="25" name="${ppty.stockPptyName}" id ="${ppty.stockPptyName}" >${pptyVal}</textarea>
				  </c:when>
				  
				   <c:when test = "${ppty.stockPptyDesc == 'BOOL' and ppty.stockPptyName =='VOTEFORMULA'}">      
					      <select name="${ppty.stockPptyName}" id="${ppty.stockPptyName}" onChange="" >
                                                    <option value="" <c:if test=" ${pptyVal eq ''}">selected</c:if>>---select an option-------</option>
                                                    <option value="Y" <c:if test=" ${pptyVal eq 'Y'}">selected</c:if>>Yes</option>
                                                     <option value="N" <c:if test=" ${pptyVal eq 'N'}">selected</c:if>>No</option>
	   					 </select>
				  </c:when>
				  <c:otherwise>
	   					 <select name="${ppty.stockPptyName}" id="${ppty.stockPptyName}" >
                                                    <option value="" <c:if test=" ${pptyVal eq ''}">selected</c:if>>---select an option-------</option>
                                                     <option value="Y" <c:if test=" ${pptyVal eq 'Y'}">selected</c:if>>Yes</option>
                                                     <option value="N" <c:if test=" ${pptyVal eq 'N'}">selected</c:if>>No</option>
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