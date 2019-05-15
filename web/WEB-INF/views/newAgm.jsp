<%@ include file="includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Agm Set-up</li>
    </ul>
    <h4>AGM VOTE </h4>
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
                 <form:form method="post" action="checkOption" commandName="votAgm">
                   <div class="form-group">   
		<form:label path="importsource" cssClass="col-sm-6 control-label">Select Option ?</form:label> 
		<div class="col-sm-6">
		<form:radiobutton path="importsource" value="I" checked="true"/> &nbsp;Easycoop 
		 &nbsp;<form:radiobutton path="importsource" value="E" />&nbsp;External Source
		  </div>  
			   </div>  
                   
                             
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
   