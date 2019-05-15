<%-- 
    Document   : viewStock
    Created on : Sep 25, 2015, 9:00:30 AM
    Author     : baydel200
--%>

<%@ include file="../includes/header.jsp" %> 
<div class="media-body">
    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Shares Management</li>
    </ul>
    <h4>${CompStockType.action} Shares</h4>
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
 		       
		      <form:form  commandName="CompStockType">
			 <form:errors path="*" cssClass="error"></form:errors>
			   <div class="form-group">   
				  <form:label path="compStockName" cssClass="col-sm-4 control-label">Name:</form:label> 
				  <div class="col-sm-2">
				     ${CompStockType.compStockName}
				  </div>  
			   </div>  
			   
			   <div class="form-group">   
				  <form:label path="shortName" cssClass="col-sm-4 control-label">Short Name:</form:label>  
				  <div class="col-sm-2">
				     ${CompStockType.shortName}
				  </div>  
			   </div>  
			   <div class="form-group">   
				  <form:label path="defaultStock" cssClass="col-sm-4 control-label">Is Default Member Shares?</form:label>
				  <div class="col-sm-2">
                                    ${CompStockType.defaultStock}
				  </div>  
			   </div>
			   
			    <div class="form-group">   
				  <form:label path="stockAcctProd" cssClass="col-sm-4 control-label">Trading Account Product</form:label>
				  <div class="col-sm-2">
				   	   ${CompStockType.stockAcctProd}	  		      
				  </div>  
			   </div>
                                  
                             <div class="form-group">   
				  <form:label path="stockControlAcct" cssClass="col-sm-4 control-label">Control Account</form:label>
				  <div class="col-sm-2">
                                        ${CompStockType.stockControlAcct}		  		      
				  </div>  
			   </div>
                                               
               <c:forEach var="detail" items="${CompStockType.compStockTypeDetails}" varStatus="loopCount">
                   
                            <div class="form-group">
                             <form:label path="" cssClass="col-sm-4 control-label"> ${detail.compStockProperty.stockPptyDisplay}</form:label>   
                             <div class="col-sm-2">
                                 ${detail.compStockPptyVal}
                              </div>  
                            </div>  
		</c:forEach>
	            
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

