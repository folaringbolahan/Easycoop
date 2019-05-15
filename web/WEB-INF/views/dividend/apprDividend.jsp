<%@ include file="../includes/header.jsp" %> 
<div class="media-body">
    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Dividend Declaration</li>
    </ul>
    <h4>Approve Dividend</h4>
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
				  <label path="dividendType" cssClass="col-sm-4 control-label">Dividend Type</label>
				  <div class="col-sm-2">
                     
                                      ${dividend.dividendType.dividendTypeName}
				   		  		      
				  </div>  
			   </div>
			   <div class="form-group">   
				  <label path="divYear" cssClass="col-sm-4 control-label">Dividend Year:</label> 
				  <div class="col-sm-2">
				      ${dividend.divYear}
				  </div>  
			   </div>  
			   
			   <div class="form-group">   
				  <label path="divPeriod" cssClass="col-sm-4 control-label">Dividend Period:</label>  
				  <div class="col-sm-2">
                                        ${dividend.divPeriod}
				  </div>  
			   </div>  
			   
			    <div class="form-group">   
				  <label path="formula" cssClass="col-sm-4 control-label">Is Allocation by Formula?</label>
				  <div class="col-sm-2">
                                                 ${dividend.formula}	
					 <input type="hidden" name="dividendId" value="${dividend.dividendId}"/>
				      <input type="hidden" name="createdBy" value="${dividend.createdBy}"/>		  		      
				  </div>  
			   </div>
			   
			  <div class="form-group">   
			   <label path="divValue" cssClass="col-sm-4 control-label">Is Allocation by Formula?</label>
				  <div class="col-sm-2">
			  	     ${dividend.divValue}	
			   	</div>  
			   </div>
			   
			    <div class="form-group">
                             <label class="col-sm-4">Dividend Declaration Date</label>
                  		     <div class="input-group">
                                        ${dividend.divDeclarationDate}  
                      
                                    </div><!-- input-group -->
                                 </div><!-- form-group -->
            	
                                 <div class="form-group">
                        <label class="col-sm-4">Dividend Record Date</label>
                  		     <div class="input-group">
                                           ${dividend.divDateRecord}  
                                        </div><!-- input-group -->
                        </div><!-- form-group -->
                        
                        <div class="form-group">
                        <label class="col-sm-4">Estimated Ex Dividend Date</label>
                  		     <div class="input-group">
                                           ${dividend.exDividendDate}  
                                     </div><!-- input-group -->
                        </div><!-- form-group -->
            	            	 
            	<div class="form-group">
                     <label class="col-sm-4">Dividend Payment Date</label>
                  		     <div class="input-group">
                                           ${dividend.divPayDate}  
                                        </div><!-- input-group -->
            	</div><!-- form-group -->
            	
            	<div class="form-group">
                     <label class="col-sm-4">Dividend Payment Account</label>
                  		     <div class="input-group">
                                          ${dividend.dividendPayAccount}  
                                     </div><!-- input-group -->
            	</div><!-- form-group -->
			  
			   <div class="form-group">
				    <button class="btn btn-danger mr5" type="submit" name="acction">APPROVE </button>
				    <button class="btn btn-danger mr5" type="submit" name="acction">REJECT</button>
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
        // This will empty first option in select to enable placeholder
        //jQuery('select option:first-child').text('');
        $('#divoperand').css('display','none');
    });
    
    $('#formula').change(function() 
	{
	   	if ($('#formula').attr('value')=='Yes')
	   	{
	   	  $('#divoperand').css('display','block');
	   	}

	});
	
	$('#divoperand').change(function() 
	{
   	alert('Value now ' + $('#divValue').attr('value'));
   	 var content = $('#divValue').attr('value')+ $(this).attr('value');
   	 alert('Value change to ' + content);
   	 $('#divValue').val(content);
	});
	
	
 </script>
