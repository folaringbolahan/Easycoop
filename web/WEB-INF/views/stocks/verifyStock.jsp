<%-- 
    Document   : verifyStock
    Created on : Sep 25, 2015, 5:54:06 AM
    Author     : baydel200
--%>

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
 		       
		      <form:form method="post" action="apprvStock.htm" commandName="CompStockType">
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
                                      <c:choose>
                                        <c:when test="${CompStockType.defaultStock eq 'Y'}">
                                                YES
                                        </c:when>
                                        <c:otherwise>
                                            NO
                                         </c:otherwise>
                                      </c:choose>
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
                                        <input type="hidden" name="compStockTypeId" value="${CompStockType.compStockTypeId}"/>
				  </div>  
			   </div>
                                               
               <c:forEach var="detail" items="${CompStockType.compStockTypeDetails}" varStatus="loopCount">
                   
                            <div class="form-group">
                             <form:label path="" cssClass="col-sm-4 control-label"> ${detail.compStockProperty.stockPptyDisplay}</form:label>   
                             <div class="col-sm-2">
                                 
                                 <c:choose>
                                    <c:when test="${detail.compStockPptyVal eq 'Y'}">
                                            YES
                                    </c:when>
                                    <c:when test="${detail.compStockPptyVal eq 'N'}">
                                             NO
                                    </c:when>
                                     <c:otherwise>
                                         ${detail.compStockPptyVal}
                                     </c:otherwise>
                                 </c:choose>
                               <%--<c:if test=""><c:set var="OKVal" value="${OKExist.val}"/></c:if>--%>
                              </div>  
                            </div>  
		</c:forEach>
	            
                <div class="form-group">
                    <input class="btn btn-danger mr5" type="submit" name="action" value="APPROVE"/>
                    <input class="btn btn-danger mr5" type="submit" name="action" value="REJECT"/>
                          <button type="reset" class="btn btn-default" onclick="Javascript:history.back()">CANCEL</button>
                 </div><!-- panel-footer -->
	 </form:form>  
      </div>
        
     
            </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->

<%@ include file="../includes/footer.jsp" %>  
 

