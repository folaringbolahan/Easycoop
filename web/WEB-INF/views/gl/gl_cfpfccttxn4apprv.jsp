<%@ include file="../includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">
    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="index.htm">Home</a></li>
        <li>Accounts Transactions Approval Confirmation</li>
    </ul>
    <h4>Confirm Accounts Transactions Approval</h4>
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
			  <h4 align="center">Approval Confirmation!</h4>
			  <hr/>
			  
			  <p><h5 align="center"><%=request.getParameter("id")%>  !!!</h5></p>
			
                     </div>
	      
	      
               </div>
          </div><!-- col-md-6 -->
      </div>
    </div>
                              
    <form:form method="post" action="gl_pfccttxn4apprv.htm" modelAttribute="batchfile">  
                <div class="panel-body">
                    <div class="form-group">
                       <div class="col-sm-6">
                           <form:input path="batchId" size="30" id="batchId" readonly="true"/>
                       </div>
                    </div><!-- form-group -->
                </div><!-- panel-body -->
                <div>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="submit" class="btn btn-danger mr5" value="Approve" id="verify"/>
                </div><!-- panel-footer -->

            </form:form>  
                              
</div><!-- contentpanel -->


<%@ include file="../includes/footer.jsp" %>  




   