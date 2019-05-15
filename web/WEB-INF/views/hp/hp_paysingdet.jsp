<%@ include file="../includes/header.jsp" %>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Hire Purchase Repayment Details(Single)</li>
        </ul>
        <h4>Hire Purchase Repayment Details(Single)</h4>
    </div>
    <%@include file="../includes/topright.jsp" %>
    
</div>
</div><!-- media -->
</div><!-- pageheader -->



<div class="contentpanel">

    <div class="row col-md-12">
        <!-- CONTENT GOES HERE -->  
        <!-- <div class="panel-body">-->
            <!-- form starts -->
            <form:form method="post" action="hp_paysidet.htm" modelAttribute="hprequestdetres">  
               
                <div class="panel panel-default">
                     <div class="panel-heading">
                        <h4 class="panel-title">HP Details</h4>
                     </div><!-- panel-heading -->
                
                
                <div class="panel-body">


                    
                      
                    <div class="form-group">
                        <form:label path="refid" cssClass="col-sm-4 control-label">Reference : ${hprequestdetres.refid} </form:label>
                        <form:label path="memberno" cssClass="col-sm-4 control-label">Member : ${hprequestdetres.membername} - ${hprequestdetres.memberno} </form:label>
                        <form:label path="product" cssClass="col-sm-4 control-label">Product : ${hprequestdetres.productname}</form:label>
                        <form:label path="txndate" cssClass="col-sm-4 control-label">Trans. Date : ${hprequestdetres.txndate}</form:label>
                        <form:label path="invref" cssClass="col-sm-4 control-label">Inv ref : ${hprequestdetres.invref}</form:label>
                        <form:label path="hpprice" cssClass="col-sm-4 control-label">HP price : ${hprequestdetres.hpprice}</form:label>
                        <form:label path="cashprice" cssClass="col-sm-4 control-label">Cash price : ${hprequestdetres.cashprice}</form:label>
                        <form:label path="downpaymentamount" cssClass="col-sm-4 control-label">Down payment Amount : ${hprequestdetres.downpaymentamount}</form:label>
                        <form:label path="interestamt" cssClass="col-sm-4 control-label">Interest Amount : ${hprequestdetres.interestamt}</form:label>
                        <form:label path="interestrate" cssClass="col-sm-4 control-label">Interest rate : ${hprequestdetres.interestrate}</form:label>
                        <form:label path="repaymentperiodinmonths" cssClass="col-sm-4 control-label">Repayment Period(months) : ${hprequestdetres.repaymentperiodinmonths}</form:label>
                        <form:label path="repaymentfrequency" cssClass="col-sm-4 control-label">Repayment frequency : ${hprequestdetres.repaymentfrequency}</form:label>
                        <form:label path="interestcalcmtd" cssClass="col-sm-4 control-label">Interest method : ${hprequestdetres.interestcalcmtd}</form:label>
                        <form:label path="createdby" cssClass="col-sm-4 control-label">Created by : ${hprequestdetres.createdby}</form:label>
                        <form:label path="createddate" cssClass="col-sm-4 control-label">Creation Date : ${hprequestdetres.createddate}</form:label>
                   </div>
                     
                    
                   
                    </div><!-- panel-body -->
                  </div>  
                  
               
                
                
            </form:form>  
                
                  
            <!-- form ends -->
       <!--  </div> -->
        <!-- End of panel-body -->
        
        <div class="panel panel-default">
                     <div class="panel-heading">
                        <h4 class="panel-title">Schedule Details</h4>
                     </div><!-- panel-heading -->
                
                
        <div class="panel-body">
        
        <table  id="data-list" class="table table-striped table-bordered responsive" >       
                <thead style="font-size: 12px">
               
                   <tr align="left">
                     <c:forEach var="hdrlistvalue" items="${headere}">
                       <th>${hdrlistvalue}</th>
                     </c:forEach>
                   </tr>
                   
                    <!--<th></th>-->
                 
            </thead>
            <tbody style="font-size: 12px">
                 <c:forEach var="genrow" items="${body}" >
                     <c:set var="count" value="0" />
                   <tr>
                      <c:forEach var="gencell" items="${genrow}" >
                          <c:set var="count" value="${count + 1}" />
                          
                          <c:choose>
                            <c:when test="${gencell=='Unpaid' && count==11}">
                          
                                <td><a href="hp_pfpaysing.htm?id=${rowid}&AMP;rf=${rfid}"><font color="red">${gencell}</font></a></td>
                          
                           
                            </c:when>
                            <c:when test="${count==1}">
                                <c:set var="rowid" value="${gencell}" />
                            </c:when>   
                            <c:when test="${count==10}">
                                <c:set var="rfid" value="${gencell}" />
                            </c:when>    
                            <c:otherwise>    
                              <td>${gencell}</td> 
                            </c:otherwise>
                           
                           
                          </c:choose> 
                           
                      </c:forEach>     
                  </tr>
                </c:forEach>
                
            </tbody>
        </table>
        
           </div>  
        </div>  
    </div>
 
    


</div>
<!-- contentpanel -->

</div>
            </div><!-- mainwrapper -->
        </section>


        <script src="<%=request.getContextPath()%>/resources/js/jquery-1.11.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery-migrate-1.2.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.10.3.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/modernizr.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/pace.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/retina.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery.cookies.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery.autogrow-textarea.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery.mousewheel.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery.tagsinput.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/toggles.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/bootstrap-timepicker.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery.maskedinput.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/select2.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery.gritter.min.js"></script>
        
        <script src="<%=request.getContextPath()%>/resources/js/jquery.validate.min.js"></script>

        <script src="<%=request.getContextPath()%>/resources/js/jquery.dataTables.min.js"></script>
       <script src="<%=request.getContextPath()%>/resources/js/dataTables.bootstrap.js"></script>
        
        <script src="<%=request.getContextPath()%>/resources/js/dataTables.responsive.js"></script>
         <script src="${resourceUrl}/js/custom.js"></script>
         <script src="<%=request.getContextPath()%>/resources/js/jquery.autocomplete.min.js"></script>
        <script>
    $(document).ready(function(){
        
        
        
        // Date Picker
               jQuery('#startdatepicker').datepicker();  
                jQuery('#enddatepicker').datepicker();
                $.datepicker.setDefaults({
                    dateFormat: 'dd/mm/yy'
                });
       jQuery('#select-search-hide').select2({
                    minimumResultsForSearch: 15
                });
      jQuery('#data-list').DataTable({
                    responsive: true
                });
       
       jQuery("#memberNo").select2();
       
        
                     
            
                    
    });
    
    
</script>
    </body>


</html>
