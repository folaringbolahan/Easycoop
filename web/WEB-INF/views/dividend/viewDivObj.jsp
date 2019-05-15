<%-- 
    Document   : viewDivObj
    Created on : May 9, 2016, 1:40:05 AM
    Author     : baydel200
--%>

<%@ include file="../includes/header.jsp" %> 
 <c:set var="amtPattern" value="###,###,###,###.00" />
<div class="media-body">
    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Dividend Declaration</li>
    </ul>
    <h4>Dividend </h4>
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
 	       
		     <form:form method="post" action="approveDividend.htm" commandName="dividend">
		     
					 
			  <div class="form-group">   
				  <label  class="col-sm-4">Dividend Type</label>
				    <div class="col-sm-8">
				   ${dividend.dividendType.dividendTypeName}
					
				  </div>  
			   </div>
			   <div class="form-group">   
				  <label  class="col-sm-4">Dividend Year </label> 
				  <div class="col-sm-8">
				            
                                       ${dividend.divYear}
                                   </div>  
			   </div>  
			   
			   <div class="form-group">   
				  <label  class="col-sm-4">Dividend Period </label>  
				  <div class="col-sm-8">
				   ${dividend.divPeriod}
				  </div>  
			   </div> 
                                  
                           <div class="form-group">   
				  <label class="col-sm-4">Dividend Number</label>  
				  <div class="col-sm-8">
				     ${dividend.divNumber}
				  </div>  
			   </div>
			   
			   		   
			  <div class="form-group">   
				  	<label class="col-sm-4">Dividend Value</label>
				  <div class="col-sm-8">
                                      
			  			${dividend.divValue}
			   	</div>  
			   </div>
                                
                    <div class="form-group">
                        <label class="col-sm-4" >Dividend Declaration Date </label>
                                        <div class="col-sm-8">
                                             <fmt:formatDate pattern="MM/dd/yyyy" type="date" value="${dividend.divDeclarationDate}"/>
                                                </div><!-- input-group -->
                    </div><!-- form-group -->
                   
            	
            	   <div class="form-group">
                     <label class="col-sm-4">Dividend Record Date </label>
                      <div class="col-sm-8">
                           <fmt:formatDate pattern="MM/dd/yyyy" type="date" value="${dividend.divDateRecord}"/>
                       
                      </div><!-- input-group -->
            	   </div><!-- form-group -->
                
            	<div class="form-group">
                     <label class="col-sm-4">Dividend Payment Date </label>
                  		    <div class="col-sm-8">
                                  <fmt:formatDate pattern="MM/dd/yyyy" type="date" value="${dividend.divPayDate}"/>       
                                  </div><!-- input-group -->
            	</div><!-- form-group -->
                
                <div class="form-group">
                    <label class="col-sm-4 control-label tooltips" data-original-title="Dividend Bank Name" data-toggle="tooltip" data-placement="left"> Bank *</label>
                     <div class="input-group">
                          ${dividend.banks.bankName}
                    </div>
                </div>
            	
            	<div class="form-group">
                     <label class="col-sm-4 control-label tooltips" data-original-title="Dividend Payment Account @ Bank">Dividend Payment Account * </label>
                  		     <div class="input-group">
                                                ${dividend.dividendPayAccount}
                        <input type="hidden" name="dividendId" value="${dividend.dividendId}" />
                   </div><!-- input-group -->
            	</div><!-- form-group -->
                
                
                 <div class="form-group">
                     <label class="col-sm-4 control-label tooltips" data-original-title="Retained Earning Account to debit for dividend @ Declaration">Retained Earnings Account * </label>
                  		     <div class="input-group">
                                                ${dividend.divRetainedEarningsAcct}
                        
                   </div><!-- input-group -->
            	</div><!-- form-group -->
                
                <div class="form-group">
                     <label class="col-sm-4 control-label tooltips" data-original-title="Dividend Payable Account to credit dividend payable @ declaration. And Also debit @ Pay Date">Dividend Payable Account* </label>
                  		     <div class="input-group">
                                                ${dividend.divPayableAcct}
                   </div><!-- input-group -->
            	</div><!-- form-group -->
                
                <div class="form-group">
                     <label class="col-sm-4 control-label tooltips" data-original-title="Account to credit Dividend Payable @ Pay Date.">Cash Account * </label>
                  		     <div class="input-group">
                                                ${dividend.divCashAcct}
                                     </div><!-- input-group -->
            	</div><!-- form-group -->
                
                <div class="form-group">
                     <label class="col-sm-4 control-label tooltips" data-original-title="Total Dividend Payable to Members.">Dividend Payable </label>
                            <div class="input-group">
                                 <fmt:formatNumber pattern="${amtPattern}" value=" ${dividend.divPayable}"/>
                                    
                            </div><!-- input-group -->
            	</div><!-- form-group -->
			  
                <div class="form-group">
                                      
                   <button class="btn btn-danger mr5" type="button" onclick="Javascript:history.back()">Cancel</button>
                      
                    
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
        jQuery("select").select2({
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
                alert('Value now ' + $('#divValue').attr('value'));
                 var content = $('#divValue').attr('value')+ $(this).attr('value');
                 alert('Value change to ' + content);
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

     });
</script>


