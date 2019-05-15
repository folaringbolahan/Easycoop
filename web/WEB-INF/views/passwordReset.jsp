<%@ include file="includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Password Reset</li>
    </ul>
    <h4>Members Password Reset </h4>
</div>
<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<script lanuage="Javascript">
	function doSubmit(frm){
             if(frm.description.value==""){alert("Agm name is required"); frm.description.focus(); return;} 
         
	    
	    frm.submit();
	}	  
  </script>
  <div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
	     <div class="panel-body">	
          
                 <form:form method="post" action="selectMembers" commandName="votAgm">
                
                   <div class="form-group"> 
			  <form:label path="description" data-toggle="tooltip" title="Agm" cssClass="col-sm-3 tooltips control-label">Agm :</form:label>  
			  <div class="col-sm-5">
			     <form:select id="description" path="description" cssClass="width300">
				 <c:forEach items="${listAgms}" var="item">  
				     <form:option value="${item.id}">${item.description}</form:option>
				   </c:forEach>
			      </form:select>
			      <form:errors path="description" cssClass="error"></form:errors>
			  </div>  
                          
		  </div>
            
                       
                  
                  
              
                    </div>
        
     

                        
                   
                  
</body> 
                 
                  
                    
            
		     
		  
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
   