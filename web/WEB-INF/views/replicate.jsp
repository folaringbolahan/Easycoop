<%@ include file="includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Move Records to Gen1</li>
    </ul>
    <h4>Record Replication </h4>
</div>
<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<script lanuage="Javascript">
	function doSubmit(frm){	
	   
	    frm.submit();
	}	  
  </script>
  <div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
	     <div class="panel-body">	
               
                 <form:form method="post" action="moveRecords" commandName="recReplication">
                     <div class="form-group"> 
			  <form:label path="branchid" data-toggle="tooltip" title="Select Cooperative" cssClass="col-sm-3 tooltips control-label">Select Cooperative</form:label>  
			  <div class="col-sm-8">
			     <form:select id="branchid" path="branchid"  cssClass="width300">
				<c:forEach items="${companies}" var="item">  
       
				     <form:option value="${item.branchid}">${item.companyName}----${item.branchName}</form:option>
				</c:forEach>
			      </form:select>
			      <form:errors path="branchid" cssClass="error"></form:errors>
			  </div>  
		  </div> 
                   <div class="form-group">
                        <label data-toggle="tooltip"  class="col-sm-3 tooltips control-label">Move Cooperative </label>
                          <div class="col-sm-3">                                           
                              <form:checkbox path="movecoop" id="movecoop" cssClass="form-control" value="true"  />   
                             
                          </div>
                    </div>
                       <div class="form-group">
                        <label data-toggle="tooltip"  class="col-sm-3 tooltips control-label">Move Product</label>
                          <div class="col-sm-3">                                           
                                <form:checkbox path="moveproduct"  id="moveproduct" cssClass="form-control" value="true"  />
                          </div>
                    </div>
                   <div class="form-group">
                        <label data-toggle="tooltip"  class="col-sm-3 tooltips control-label">Move Members </label>
                          <div class="col-sm-3">                                           
                              <form:checkbox path="movemember"  id="movemember" cssClass="form-control" value= "true"  />   
                             
                          </div>
                    </div>
                     <div class="form-group">
                        <label data-toggle="tooltip"  class="col-sm-3 tooltips control-label">Move Members Addresses </label>
                          <div class="col-sm-3">                                           
                              <form:checkbox path="moveaddress"  id="moveaddress" cssClass="form-control" value= "true"  />   
                             
                          </div>
                    </div>          
                 
                   
           <div class="form-group">
		    <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">Move Records</button>
		  
	    </div><!-- panel-footer -->
                     
            
      </form:form>  
      </div>
   
      
            </div><!-- col-md-6 -->
        </div>
    </div>
</div>
<!-- contentpanel -->

<%@ include file="includes/footer.jsp" %>  
<script>
    $(document).ready(function(){
         jQuery('select').select2({
                    minimumResultsForSearch: -1
         });
	 
	 jQuery('#addr-item-type').DataTable({
                    responsive: true
         });
    })
</script>
   