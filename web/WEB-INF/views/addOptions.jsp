<%@ include file="includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Agm Vote Setup</li>
    </ul>
    <h4>Setup Options </h4>
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
                 <form:form method="post" action="resolopts" commandName="votQuest">
                          <div class="form-group" >
                            <label class="col-sm-3 tooltips control-label" data-toggle="tooltip" title="question"> Question :</label>
                            <div class="col-sm-7">
                                <input name="question" class="form-control" value="${votquest.description}" id="question" readonly="true" required="required" />
                            </div>
                            <div>

                            </div>
                        </div>  
                    	   
			   <c:forEach var="item" begin="0" items='${resolutions}'>
		   <div class="form-group">   
		       <form:label path="description" cssClass="col-sm-3 control-label">Option <c:out  value="${item.id}" /></form:label>
                      
			<div class="col-sm-5">
                       
			<input name="description" class="form-control" value="${item.description}" id="description" readonly="true" required="required" />		      
			</div>  
				   </div>
                           </c:forEach>	
                    <form:hidden path="agmid"   value="${votquest.agmid}"/>  
                    <form:hidden path="deleted"   value="N"/>   
                    <form:hidden path="voteid" value="${votquest.id}"/>   
                    
                          
                   
                
               
                
                          
                     
                          
                   
          
		  
                          
                
                            
               

		  
           <div class="form-group">
		    <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">SAVE</button>
		    
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
   