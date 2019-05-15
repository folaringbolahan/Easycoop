<%@ include file="../includes/header.jsp" %> 
<div class="media-body">
      <div style="float:left">
    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Shares Management</li>
    </ul>
    <h4>${CompStockType.action} Shares</h4>
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
 		       
		      <form:form method="post" action="saveStock.htm" modelAttribute="CompStockType">
			 <form:errors path="*" cssClass="error"></form:errors>
			   <div class="form-group" >   
				  <form:label path="compStockName" class="col-sm-4 control-label tooltips" data-original-title="A Unique name for the shares across the Cooperative" data-toggle="tooltip" data-placement="left">Name*:</form:label> 
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
				   <form:select  path="registerStock" >
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
				  <form:label path="defaultStock" class="col-sm-4 control-label tooltips" data-original-title="Property to tell if every member should have this shares" data-toggle="tooltip" data-placement="left">Is Default Member Shares?*</form:label>
				  <div class="col-sm-2">
				   <form:select  path="defaultStock" >
					<form:option value="">--select--</form:option>
					<form:option value="N">No</form:option>
					<form:option value="Y">Yes</form:option>
					</form:select>
                                        <div class="error">
                                             <form:errors path="defaultStock" />
                                        </div>
				     <%-- <form:input path="defaultStock" /> --%>
				      <input type="hidden" id="action" name="action" value="${CompStockType.action}"/>
				      <input type="hidden" name="createdBy" value="${CompStockType.createdBy}"/>
 					<input type="hidden"  name="delFlg"  value="N"/>
                                       <input type="hidden" name="company.id" value="${CompStockType.company.id}"/>
			     <%--    <fmt:formatDate pattern="dd/MM/yyyy" type="date" value="${now}" var="currentDate"/>
			   <input type="hidden"  name="createdDate" data-format="dd/MM/yyyy"  value="${currentDate}"/> --%>
			  		      
				  </div>  
			   </div>
     <hr>        	   
           <c:forEach var="ppty" items="${referenceList.stockPptyList}">
               
                <c:forEach var="detail" items="${CompStockType.compStockTypeDetails}">
                        
                     <c:if test='${detail.compStockProperty.stockPptyName eq ppty.stockPptyName}'>
                          <c:set var="pptyVal" value='${detail.compStockPptyVal}'/>
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
                                                    <option value="" <c:if test=' ${pptyVal eq ""}'>selected</c:if>>---select an option-------</option>
                                                    <option value="Y" <c:if test=' ${pptyVal eq "Y"}'>selected</c:if>>Yes</option>
                                                     <option value="N" <c:if test=' ${pptyVal eq "N"}'>selected</c:if>>No</option>
	   					 </select>
				  </c:when>
				  <c:otherwise>
	   					 <select name="${ppty.stockPptyName}" id="${ppty.stockPptyName}" >
                                                    <option value='' <c:if test='${pptyVal eq ""}'>selected</c:if>>---select an option-------</option>
                                                     <option value='Y' <c:if test='${pptyVal eq "Y"}'>selected</c:if>>Yes</option>
                                                     <option value='N' <c:if test='${pptyVal eq "N"}'>selected</c:if>>No</option>
	   					 </select>
	   					<%--  <input name="${ppty.stockPptyName}" type="checkbox" value=""/> --%>
	 			 </c:otherwise>
                                          			  
                      </c:choose>
				  
                    </div>  
                    </div>  
		 <c:set var="pptyVal" value=''/>
           </c:forEach>    
          <hr>                
            <div class="form-group" >   
                        <form:label path="stockCashAcct" class="col-sm-4 control-label tooltips" data-original-title="Account to debit for sale of Shares or Member Registration." data-toggle="tooltip" data-placement="left">Cash Account *</form:label>
                        <div class="col-sm-8">
                         <form:select class="width100p" path="stockCashAcct">
                              <form:option value="">----------select----------</form:option>
                                       <c:forEach items="${referenceList.nonControlAcctList}" var="map">  
                                                      <form:option value="${map['accountno']}"> ${map['name']} === ${map['accountno']}</form:option>
                                        </c:forEach>
                              </form:select>	
                              <div class="error">
                                   <form:errors path="stockCashAcct" />
                              </div>
                        </div>  
                </div>
                                  
               <div class="form-group" >   
                        <form:label path="stockParAcct" class="col-sm-4 control-label tooltips" data-original-title="Account to credit/debit legal capital of shares." data-toggle="tooltip" data-placement="left">Legal Capital Account *</form:label>
                        <div class="col-sm-8">
                         <form:select  class="width100p" path="stockParAcct">
                              <form:option value="">----------select----------</form:option>
                                       <c:forEach items="${referenceList.nonControlAcctList}" var="map">  
                                                      <form:option value="${map['accountno']}"> ${map['name']} === ${map['accountno']}</form:option>
                                        </c:forEach>
                              </form:select>	
                              <div class="error">
                                   <form:errors path="stockParAcct" />
                              </div>
                        </div>  
                </div>  
                            
                <div class="form-group" >   
                        <form:label path="stockExcessAcct" class="col-sm-4 control-label tooltips" data-original-title="Account to credit/Debit excess capital." data-toggle="tooltip" data-placement="left">Excess Account *</form:label>
                        <div class="col-sm-8">
                         <form:select  class="width100p" path="stockExcessAcct">
                              <form:option value="">----------select----------</form:option>
                                       <c:forEach items="${referenceList.nonControlAcctList}" var="map">  
                                                      <form:option value="${map['accountno']}"> ${map['name']} === ${map['accountno']}</form:option>
                                        </c:forEach>
                              </form:select>	
                              <div class="error">
                                   <form:errors path="stockExcessAcct" />
                              </div>
                        </div>  
                </div>
                                  
                <div class="form-group" >   
                        <form:label path="stockTreasuryAcct" class="col-sm-4 control-label tooltips" data-original-title="Account to credit/debit on repurchase." data-toggle="tooltip" data-placement="left">Treasury Account *</form:label>
                        <div class="col-sm-8">
                         <form:select class="width100p" path="stockTreasuryAcct">
                              <form:option value="">----------select----------</form:option>
                                       <c:forEach items="${referenceList.nonControlAcctList}" var="map">  
                                                      <form:option value="${map['accountno']}"> ${map['name']} === ${map['accountno']}</form:option>
                                        </c:forEach>
                              </form:select>	
                              <div class="error">
                                   <form:errors path="stockTreasuryAcct" />
                              </div>
                        </div>  
                </div>
                             
                        
                           
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
    jQuery(document).ready(function() {
        
        var act = $('#action').val();
        if(act==="EDIT")
        {
            //$('#compStockName').attr("readonly", true);
            $('#registerStock').attr("readonly", true);
        }
    
     $('#stckoperand').change(function() 
	{
            //if $('#VOTEFORMULA').attr('value').eq("YES")
            //{
                //alert('Value now ' + $('#VOTERIGHTPROP').attr('value'));
                 var content = $('#VOTERIGHTPROP').attr('value')+ $(this).attr('value');
                // alert('Value change to ' + content);
                 $('#VOTERIGHTPROP').val(content);
          // }
	});

       
   $('#registerStock').change(function() 
	{
          var content = $('#registerStock').val();
          var noConent = 'N';
          
          if(content=="Y")
            {
               //alert('Value now ' + $('#registerStock').val());
               $('#defaultStock').val(content).change();
              // $('#defaultStock option[value="' + content + '"]').attr('selected', true);
               $('#defaultStock').attr('readonly', true);
               //$('#MEMBER').val("Y");
               $('#MEMBER').val(content).change();
               //$('#MEMBER option[value="' + content + '"]').prop('selected', true);
               $("#MEMBER").attr("readonly", true);
               // $('#REPURCHASABLE').val("N");
               $('#REPURCHASABLE').val(noConent).change();
               //$('#REPURCHASABLE option[value="' + noConent + '"]').prop('selected', true);
               $('#REPURCHASABLE').attr("readonly", true);
               //$('MINVALUE').val("1");
               $('#MINVALUE').val("1").change();
               $('#MINVALUE').attr("readonly", true);
               $('#MAXVALUE').val("1").change();
               $('#MAXVALUE').attr("readonly", true);
               //$('#TOTALLIMITED').val("Y");
               $('#TOTALLIMITED').val(noConent).change();
               $('#TOTALLIMITED').attr("readonly", true);
               $('#TOTALAVAILABLE').val('').change();
               $('#TOTALAVAILABLE').attr("readonly", true);
               $('#PARVALUE').attr("readonly", true);
            }
            if(content=="N")
            {
               // alert('Value now ' + $('#registerStock').val());
               $('#defaultStock').val('').change();
              // $('#defaultStock option[value="' + content + '"]').attr('selected', true);
               $('#defaultStock').attr('readonly', false);
               //$('#MEMBER').val("Y");
               $('#MEMBER').val('').change();
               //$('#MEMBER option[value="' + content + '"]').prop('selected', true);
               $("#MEMBER").attr("readonly", false);
               // $('#REPURCHASABLE').val("N");
               $('#REPURCHASABLE').val('').change();
               //$('#REPURCHASABLE option[value="' + noConent + '"]').prop('selected', true);
               $('#REPURCHASABLE').attr("readonly", false);
               //$('MINVALUE').val("1");
               $('#MINVALUE').val("").change();
               $('#MINVALUE').attr("readonly", false);
               $('#MAXVALUE').val("").change();
               $('#MAXVALUE').attr("readonly", false);
               //$('#TOTALLIMITED').val("Y");
               $('#TOTALLIMITED').val('').change();
               $('#TOTALLIMITED').attr("readonly", false);
               $('#TOTALAVAILABLE').val('').change();
               $('#TOTALAVAILABLE').attr("readonly", false);
               $('#PARVALUE').attr("readonly", false);
            }
	});
        
        $('#UNITCOST').blur(function() 
	{
            if ($('#registerStock').val()==='Y')
            {
               $('#PARVALUE').val($('#UNITCOST').val()).change();
               $('#PARVALUE').attr("readonly", true);
            }
        });
        
        $('#CANVOTE').change(function() 
	{
            if ($('#CANVOTE').val()==='N')
            {
               $('#VOTEFORMULA').attr("readonly", true);
               $('#stckoperand').attr("readonly", true);
               $('#VOTERIGHTPROP').attr("readonly", true);
            }
            
            if ($('#CANVOTE').val()==='Y')
            {
               $('#VOTEFORMULA').attr("readonly", false);
               $('#stckoperand').attr("readonly", false);
               $('#VOTERIGHTPROP').attr("readonly", false);
            }
        });
        
        $('#VOTEFORMULA').change(function() 
	{
            if ($('#VOTEFORMULA').val()==='N')
            {
               $('#stckoperand').attr("readonly", true);
            }
            if ($('#VOTEFORMULA').val()==='Y')
            {
               $('#stckoperand').attr("readonly", false);
            }
        });
        
        $('#TOTALLIMITED').change(function() 
	{
            if ($('#TOTALLIMITED').val()==='N')
            {
               $('#TOTALAVAILABLE').attr("readonly", true);
            }
            if ($('#TOTALLIMITED').val()==='Y')
            {
               $('#TOTALAVAILABLE').attr("readonly", false);
            }
        });
        
        $('select').select2({
                    minimumResultsForSearch: -1
                });
                
        $("#stockCashAcct").select2();
        $("#stockParAcct").select2();
        $("#stockExcessAcct").select2();
        $("#stockTreasuryAcct").select2();
      
     });
</script>
