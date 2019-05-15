<%@ include file="../includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">
    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="index.htm">Home</a></li>
        <li>End of Day Processing</li>
    </ul>
    <h4>End of Day Processing Report</h4>
  </div>  
    <%@include file="../includes/topright.jsp" %>
</div>

</div><!-- media -->
</div><!-- pageheader -->

<div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <div class="col-md-12">
	      <div class="panel-body">
	      	      
		     <div class="alert alert-info fade in nomargin">
			  <button aria-hidden="true" data-dismiss="alert" class="close" type="button">&times;</button>
			  <h4 align="center">Process Report!</h4>
			  <hr/>
			  
                          <c:choose> 
                            <c:when test ="${pedm.completionstatus=='0'}" >
                               <c:set  var="dmessage" value="Job Queued for Processing. Please Refresh page periodically to get status of process."/>
                            </c:when>
                            <c:when test ="${pedm.completionstatus=='-1'}" >
                               <c:set  var="dmessage" value="Processing...Please Refresh page periodically to get status of process."/>
                            </c:when>
                            <c:when test ="${pedm.completionstatus=='1'}" >
                               <c:set  var="dmessage" value="Processing Completed."/>
                            </c:when>
                            <c:otherwise>
                              <c:set  var="dmessage" value="Job Queued for Processing."/>
                            </c:otherwise> 
                           </c:choose> 
                          
                          
			  <p><h5 align="center">${dmessage} </h5></p>
			
                     </div>
	      
	      
               </div>
          </div><!-- col-md-6 -->
      </div>
    </div>
                              
    
                              
</div><!-- contentpanel -->


<%@ include file="../includes/footer.jsp" %>  




   