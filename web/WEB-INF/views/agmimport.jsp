<%@ include file="includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Agm Set-up</li>
    </ul>
    <h4>Step 2 </h4>
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
               
                 <form:form method="post" action="saveEasycoopMemberimport.htm" commandName="votAgm">
                     
                <div class="form-group"> 
			  <form:label path="description" data-toggle="tooltip" title="Select Agm" cssClass="col-sm-3 tooltips control-label">Import Members *:</form:label>  
			  <div class="col-sm-8">
			     <form:select id="description" path="description"  cssClass="width300">
				<c:forEach items="${agmList}" var="item">  
       
				     <form:option value="${item.id}">${item.description}</form:option>
				</c:forEach>
			      </form:select>
			      <form:errors path="description" cssClass="error"></form:errors>
			  </div>  
		  </div> 
                
                  <input type="hidden" name="createdby" value="<%=request.getRemoteUser()%>">
                 
                   
           <div class="form-group">
		    <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">IMPORT</button>
		  
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
   