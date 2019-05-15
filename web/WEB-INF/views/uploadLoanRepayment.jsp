<%@ include file="includes/header.jsp" %>  

<div class="media-body">
    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Address Type Management</li>
    </ul>
    <h4>Manage Address Type</h4>
</div>
<%@include file="includes/topright.jsp" %>
</div><!-- media -->
</div><!-- pageheader -->

<div class="contentpanel">
    <div class="row">
        <div class="col-md-10">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
	     <div class="panel-body">	
	     
	        <form:form modelAttribute="fileBean" method="post" enctype="multipart/form-data">
	             <div class="form-group">  
	                   <form:label for="fileData" path="fileData" cssClass="col-sm-3 control-label">Select file</form:label><br/><br/>
	                   
			   <div class="col-sm-8">
			     <form:input path="fileData" type="file"/>
			  </div> 
	             </div>	                    
                           
                     <input type="hidden" name="ACTION_ID" value="1">;
		     
		     <form:hidden path="createdBy" value="admin"/>
		     <form:hidden path="creationDate"  value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>

		   <div class="form-group">
			    <button class="btn btn-danger mr5" type="submit">SUBMIT</button>
			    <button type="reset" class="btn btn-default">RESET</button>
		    </div><!-- panel-footer -->       
            
      		</form:form>  
             </div>   

            </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->
<%@ include file="includes/footer.jsp" %>  

   