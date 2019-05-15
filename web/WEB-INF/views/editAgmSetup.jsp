<%@ include file="includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Agm External Company Set-up</li>
    </ul>
    <h4>Edit AGM External Company</h4>
</div>
<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<script lanuage="Javascript">
	function doSubmit(frm){
             if(frm.companyrefid.value==""){alert("Company id is required"); frm.companyrefid.focus(); return;} 
          if(frm.companyName.value==""){alert("Company name is required"); frm.companyName.focus(); return;}
	    
	    frm.submit();
	}	  
  </script>
  <div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
	     <div class="panel-body">	
          
                 <form:form method="post" action="updateExternalAgmSetup" commandName="votAgm">
                
            
          
                
                   <div class="form-group">
                  <label data-toggle="tooltip"  class="col-sm-3 tooltips control-label"> Company Id: </label>
                    <div class="col-sm-7">                                           
                       <form:input path="companyrefid" id="companyrefid" cssClass="form-control" value= "${extSetup.companyrefid}" />                                                                 
                          </div>
                    </div>
                
               
                      
                  
              
                    </div>
            
                                
       
                
               
                  <div class="form-group">
                  <label data-toggle="tooltip"  class="col-sm-3 tooltips control-label">Agm Company: </label>
                    <div class="col-sm-7">                                           
                       <form:input path="companyName" id="companyName" cssClass="form-control" value= "${extSetup.companyName}" />                                                                 
                          </div>
                    </div>
              
                  
                  
                  
              <form:hidden path="companyid" value="${extSetup.companyid}"/>
             <input type="hidden" name="id" value="${extSetup.id}">
   </body>               
                  
                    
                    
		   
		     
		  
           <div class="form-group">
		    <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">UPDATE</button>
		   
	    </div><!-- panel-footer -->       
            
      </form:form>  
      </div>
    
      
            </div><!-- col-md-6 -->
        </div>
    </div>
</div>
<!-- contentpanel -->

<%@ include file="includes/footer.jsp" %>  
<script src="<%=request.getContextPath()%>/resources/js/bootstrap-timepicker.min.js"></script>
<script>
  
    $(document).ready(function(){
      
           jQuery('#starttime').timepicker({showMeridian: false});
         jQuery('#endtime').timepicker({showMeridian: false});      
        jQuery('#startdate').datepicker();  
                jQuery('#startdatepicker').datepicker();
                $.datepicker.setDefaults({
                    dateFormat: 'dd/mm/yy'
                });
            jQuery('#enddate').datepicker();  
                jQuery('#enddatepicker').datepicker();
                $.datepicker.setDefaults({
                    dateFormat: 'dd/mm/yy'
                });
            
                     $("#enddate").change(function () {
    var startDate = document.getElementById("startdate").value;
    var endDate = document.getElementById("enddate").value;

    if ((Date.parse(startDate) >= Date.parse(endDate))) {
        alert("End date should be greater than Start date");
        document.getElementById("enddate").value = "";
    }
});    
               
         jQuery('select').select2({
                    minimumResultsForSearch: -1
         });
	 
	 jQuery('#addr-item-type').DataTable({
                    responsive: true
         });
    })
</script>
   