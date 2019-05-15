<%@ include file="includes/header.jsp" %>  

<div class="media-body">
        <div style="float:left">
	    <ul class="breadcrumb">
		<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		<li><a href="#">Home</a></li>
		<li>Processing Feedback</li>
	    </ul>
	    <h4>Processing Report</h4>
	</div>
	<%@include file="includes/topright.jsp" %>
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
			  <h4 align="center">Operation Successful!</h4>
			  <hr/>
			  
			  <p><h5 align="center">${feedback.message}  !!!</h5></p>
			  <p align="center">
			      <button onclick="location.href='${feedback.redirectURI}'" class="btn btn-danger" type="button">Continue</button>
			    <%--  <button class="btn btn-white" type="button">Cancel</button> --%>
			  </p>
                     </div>
	      
	      
               </div>
          </div><!-- col-md-6 -->
      </div>
    </div>
</div><!-- contentpanel -->


<%@ include file="includes/footer.jsp" %>  




   