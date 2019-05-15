<%@ include file="../includes/header.jsp" %> 
<div class="media-body">
    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Dividend Declaration</li>
    </ul>
    <h4>${dividend.action} Dividend</h4>
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
 	       
		     <form:form method="post" action="saveDividend.htm" commandName="dividend">
		     
			 <form:errors path="*" cssClass="error"></form:errors>
			 
			  <div class="form-group">   
				  <form:label path="dividendType.dividendTypeId" class="col-sm-4 control-label tooltips" data-original-title="Dividend Type">Dividend Type * </form:label>
				  <div class="input-group">
				   <form:select  path="dividendType.dividendTypeId">
					<form:option value="">----------select----------</form:option>
						 <c:forEach items="${referenceList.divTypeList}" var="map">  
								<form:option value="${map.dividendTypeId}"> ${map.dividendTypeName}</form:option>
						  </c:forEach>
					</form:select>	
                                         <div class="error">
                                             <form:errors path="dividendType" />
                                        </div>
				  </div>  
			   </div>
			   <div class="form-group">   
				  <form:label path="divYear" class="col-sm-4 control-label tooltips" data-original-title="Dividend Year">Dividend Year * </form:label> 
				  <div class="input-group">
				     <%--<form:input path="divYear" type="text" class="form-control" placeholder="mm/dd/yyyy" size="10" id="datepicker3"/>
                                      <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>--%>
                                     
                                       <form:input path="divYear" type="text" class="form-control" id="divYear"/>
                                     
                                     
				  </div>  
			   </div>  
			   
			   <div class="form-group">   
				  <form:label path="divPeriod" class="col-sm-4 control-label tooltips" data-original-title="Dividend Period">Dividend Period * </form:label>  
				  <div class="input-group">
                                      <form:select  path="divPeriod">
					<form:option value="">--select Period----</form:option>
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
				    <%-- <form:input path="divPeriod" />--%>
				  </div>  
			   </div> 
                                  
                           <div class="form-group">   
				  <form:label path="divNumber" class="col-sm-4 control-label tooltips" data-original-title="Dividend Number">Dividend Number * </form:label>  
				  <div class="input-group">
				     <form:input path="divNumber" />
				  </div>  
			   </div>
			   
			    <div class="form-group">   
				  <form:label path="formula" class="col-sm-4 control-label tooltips" data-original-title="Indicates if dividend allocation is by formula">Is Allocation by Formula? *</form:label>
				  <div class="input-group">
				   <form:select  path="formula" id="formula">
					<form:option value="">----------select----------</form:option>
						 <form:option value="Y">Yes</form:option>
						 <form:option value="N">No</form:option>
					</form:select>	
				      <input type="hidden" name="action" value="${dividend.action}"/>
				      <input type="hidden" name="createdBy" value="${dividend.createdBy}"/>
                                      <input type="hidden" name="status.statusId" value="${dividend.status.statusId}"/>
                                     <!-- <input type="hidden" name="createdDate" value="${dividend.createdDate}"/>-->
                                      <input type="hidden" name="dividendId" value="${dividend.dividendId}"/>
                                      <input type="hidden" name="postEntries" value="${dividend.postEntries}"/>
				  </div>  
			   </div>
			   
			   <div class="form-group">   
				  	<label path="05" class="col-sm-4 control-label tooltips" data-original-title="Possible variables in the formula">Formula Variables</label>
				  <div class="input-group">
                                    <select name="divoperand" id="divoperand" onChange="" >
                                                          <option value="">---select an option-------</option>
                                                            <c:forEach items="${referenceList.operandList}" var="map">  
                                                                         <option value="${map['operand_val']}"> ${map['operand_display']} </option>
                                                           </c:forEach>
                                    </select>
	   			</div>  
			   </div>
			   
	   		  	<div class="form-group">   
				  	<form:label path="divValue" class="col-sm-4 control-label tooltips" data-original-title="Contains the formula or absolute value of dividend">Dividend Value</form:label>
				  <div class="input-group">
			  			<form:textarea rows="4" cols="25" path="divValue" id ="divValue"></form:textarea>
			   	</div>  
			   </div>
                                
                    <c:if test="${dividend.action=='ADD'}">
                        <c:set var="now" value="<%=new java.util.Date()%>" />
                        <fmt:formatDate pattern="MM/dd/yyyy" type="date" value="${now}" var="currentDate9"/>
                        <fmt:formatDate pattern="MM/dd/yyyy" type="date" value="${now}" var="currentDate10"/>
                        <fmt:formatDate pattern="MM/dd/yyyy" type="date" value="${now}" var="currentDate11"/>
                    </c:if>
                                
                    <c:if test="${dividend.action=='EDIT'}">
                        <c:set var="now" value="<%=new java.util.Date()%>" />
                        <fmt:formatDate pattern="MM/dd/yyyy" type="date" value="${dividend.divDeclarationDate}" var="currentDate9"/>
                         <fmt:formatDate pattern="MM/dd/yyyy" type="date" value="${dividend.divDateRecord}" var="currentDate10"/>
                        <fmt:formatDate pattern="MM/dd/yyyy" type="date" value="${dividend.divPayDate}" var="currentDate11"/>
                    </c:if>
                                
                    <div class="form-group">
                        <label class="col-sm-4 control-label tooltips" data-original-title="Dividend declaration date" >Dividend Declaration Date * </label>
                                        <div class="input-group">
                          <form:input path="divDeclarationDate" value="${currentDate9}" type="text" class="form-control" placeholder="mm/dd/yyyy" size="10" id="datepicker"/>
                          <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                      </div><!-- input-group -->
                   </div><!-- form-group -->

            	   <div class="form-group">
                     <label class="col-sm-4 control-label tooltips" data-original-title="Dividend Record Date">Dividend Record Date * </label>
                      <div class="input-group">
                       <form:input path="divDateRecord" type="text"  value="${currentDate10}" class="form-control" placeholder="mm/dd/yyyy" size="10" id="datepicker2"/>
                       <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                      </div><!-- input-group -->
            	   </div><!-- form-group -->
                
            	<div class="form-group">
                     <label class="col-sm-4 control-label tooltips" data-original-title="Dividend payment date">Dividend Payment Date * </label>
                  		    <div class="input-group">
                       <form:input path="divPayDate" type="text"  value="${currentDate11}" class="form-control" placeholder="mm/dd/yyyy" size="10" id="datepicker1"/>
                       <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                   </div><!-- input-group -->
            	</div><!-- form-group -->
                
                
                 <div class="form-group" >   
                        <form:label path="divRetainedEarningsAcct" class="col-sm-4 control-label tooltips" data-original-title="Retained Earning Account to debit for dividend @ Declaration" data-toggle="tooltip" data-placement="left">Retained Earnings Account*</form:label>
                        <div class="col-sm-8">
                         <form:select  class="width100p" path="divRetainedEarningsAcct">
                              <form:option value="">----------select----------</form:option>
                                       <c:forEach items="${referenceList.nonControlAcctList}" var="map">  
                                                      <form:option value="${map['accountno']}"> ${map['name']} === ${map['accountno']}</form:option>
                                        </c:forEach>
                              </form:select>	
                              <div class="error">
                                   <form:errors path="divRetainedEarningsAcct" />
                              </div>
                        </div>  
                </div>  
                            
                <div class="form-group" >   
                        <form:label path="divPayableAcct" class="col-sm-4 control-label tooltips" data-original-title="Dividend Payable Account to credit dividend payable @ declaration. And Also debit @ Pay Date" data-toggle="tooltip" data-placement="left">Dividend Payable Account *</form:label>
                        <div class="col-sm-8">
                        <form:select class="width100p" path="divPayableAcct">
                              <form:option value="">----------select----------</form:option>
                                       <c:forEach items="${referenceList.nonControlAcctList}" var="map">  
                                                      <form:option value="${map['accountno']}"> ${map['name']} === ${map['accountno']}</form:option>
                                        </c:forEach>
                              </form:select>	
                              <div class="error">
                                   <form:errors path="divPayableAcct" />
                              </div>
                        </div>  
                </div>
                                  
                <div class="form-group" >   
                        <form:label path="divCashAcct" class="col-sm-4 control-label tooltips" data-original-title="Account to credit Dividend Payable @ Pay Date." data-toggle="tooltip" data-placement="left">Cash Account *</form:label>
                        <div class="col-sm-8">
                         <form:select class="width100p" path="divCashAcct">
                              <form:option value="">----------select----------</form:option>
                                       <c:forEach items="${referenceList.nonControlAcctList}" var="map">  
                                                      <form:option value="${map['accountno']}"> ${map['name']} === ${map['accountno']}</form:option>
                                        </c:forEach>
                              </form:select>	
                              <div class="error">
                                   <form:errors path="divCashAcct" />
                              </div>
                        </div>  
                </div>
                
                <div class="form-group">
                    <label class="col-sm-4 control-label tooltips" data-original-title="Dividend Bank Name" data-toggle="tooltip" data-placement="left"> Bank *</label>
                     <div class="col-sm-8">
                    <form:select class="width100p" path="banks.bankId"  data-placeholder="Choose One">
                        <form:option value="" label="--Please Select Bank--" />
                        <c:forEach var="bnkList" items="${referenceList.banksList}">
                                <form:option label="${bnkList[1]}" value="${bnkList[0]}" />
                        </c:forEach>
                                                                    </form:select>
                                <div class="error">
                                            <form:errors path="banks" />
                                </div>
                    </div>
                </div>
            	
            	<div class="form-group">
                     <label class="col-sm-4 control-label tooltips" data-original-title="Dividend Payment Account @ Bank">Dividend Payment Account * </label>
                  		     <div class="input-group">
                        <form:input path="dividendPayAccount" />
                   </div><!-- input-group -->
            	</div><!-- form-group -->
			  
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
        
        
        // Select2
        jQuery('select').select2({
            minimumResultsForSearch: -1
        });

        jQuery('#datepicker').datepicker();
        $.datepicker.setDefaults({
           dateFormat: 'mm/dd/yy'
        });
        
        jQuery('#datepicker1').datepicker();
        $.datepicker.setDefaults({
           dateFormat: 'mm/dd/yy'
        });
        
        jQuery('#datepicker2').datepicker();
        $.datepicker.setDefaults({
           dateFormat:
                   'mm/dd/yy'
        });
        
      jQuery('#datepicker3').datepicker();
      $.datepicker.setDefaults({
         dateFormat:  'mm/dd/yy'
      });
        
           
     $('#divoperand').change(function() 
	{
            //if $('#VOTEFORMULA').attr('value').eq("YES")
            //{
                //alert('Value now ' + $('#divValue').attr('value'));
                 var content = $('#divValue').attr('value')+ $(this).attr('value');
                 //alert('Value change to ' + content);
                 $('#divValue').val(content);
          // }
	});

       
        $('#formula').change(function() 
	{
            if ($('#formula').val()==='N')
            {
               $('#divoperand').attr("readonly", true);
            }
            if ($('#formula').val()==='Y')
            {
               $('#divoperand').attr("readonly", false);
            }
        });
        
                    
        $("#divRetainedEarningsAcct").select2();
        $("#divPayableAcct").select2();
        $("#divCashAcct").select2();
       // $("#[banks.bankId]").select2();
        //$("#stockTreasuryAcct").select2();

     });
</script>
