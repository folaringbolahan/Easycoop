<%@ include file="../includes/header.jsp" %>
<%@ include file="../includes/header.jsp" %>
<%@ include file="../includes/header.jsp" %>
        <p>
        <table  width="100%" border="0" cellspacing="7" cellpadding="7">
            <thead>
                <tr>
                    <th>ERROR!</th>
                    
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>An error has occurred. Please retry operation or check log for details.</td>
                    
                </tr>
                
               <div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <div class="col-md-12">
	      <div class="panel-body">
	      	      
		     <div class="alert alert-danger fade in nomargin">
			  <button aria-hidden="true" data-dismiss="alert" class="close" type="button">&times;</button>
			  <h4 align="center">Error!</h4>
			  <hr/>
			  
			  <p><h5 align="center"><%=request.getParameter("er")%>  !!!</h5></p>
			 <!-- <p align="center">
			      <button onclick="location.href='<%=request.getParameter("redirectURI")%>'" class="btn btn-danger" type="button">Continue</button>
			      <button class="btn btn-white" type="button">Cancel</button>
			  </p> -->
                     </div>
	      
	      
               </div>
          </div><!-- col-md-6 -->
      </div>
    </div>
</div> 
                
                
            </tbody>
        </table>

            </p>
  
<%@ include file="../includes/footer.jsp" %>  