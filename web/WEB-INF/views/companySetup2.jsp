<%@ include file="includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Agm Set-up For External Source</li>
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
               
                 <form:form method="post" action="saveCompanySetup2" commandName="votCompany">
                  <div class="form-group">
                        <label data-toggle="tooltip"  class="col-sm-3 tooltips control-label">Company Id:</label>
                          <div class="col-sm-7">                                           
                               <form:input path="companyrefid" id="companyrefid" cssClass="form-control" value="${votCompany.companyrefid}" />                                                                 
                          </div>
                    </div>  
                          <div class="form-group">
                        <label data-toggle="tooltip"  class="col-sm-3 tooltips control-label">Company Name:</label>
                          <div class="col-sm-7">                                           
                               <form:input path="companyname" id="companyname" cssClass="form-control" value="${votCompany.companyname}" />                                                                 
                          </div>
                    </div>  
                         <form:hidden path="active" value="N"/>
            
              
           <div class="form-group">
		    <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">NEXT</button>
		  
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
   