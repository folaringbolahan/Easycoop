<%@ include file="includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Agm Vote Setup</li>
    </ul>
    <h4>Vote Setup </h4>
</div>
<%@include file="includes/topright.jsp" %>

</div>
</div><!-- media -->
</div><!-- pageheader -->
<script lanuage="Javascript">
	function doSubmit(frm){	
	    if(frm.description.value==""){alert("Vote Question is required"); frm.description.focus(); return;}  
	    
            if( frm.electionanswertypeid.value=="2" && frm.maxoption.value=="0"  ){alert("max option cannot be empty for multiple options"); frm.maxoption.focus(); return;}  
	      if( frm.electionanswertypeid.value=="2" && frm.voteoptions.value==""  ){alert("number of options cannot be empty "); frm.voteoptions.focus(); return;}  
        frm.submit();
	}	  
  </script>
  <div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
	     <div class="panel-body">
               
  
                 <form:form method="post" action="options" commandName="votQuest">
                      <div class="form-group"> 
			  <form:label path="agmid" data-toggle="tooltip" title="Select Agm" cssClass="col-sm-3 tooltips control-label">Agm:</form:label>  
			  <div class="col-sm-8">
			     <form:select id="agmid" path="agmid" cssClass="width300">
				<c:forEach items="${allagms}" var="agm">  
				     <form:option value="${agm.id}">${agm.description}</form:option>
				</c:forEach>
			      </form:select>
			      <form:errors path="agmid" cssClass="error"></form:errors>
			  </div>  
		  </div>
                        <div class="form-group" >
                            <label class="col-sm-3 tooltips control-label" data-toggle="tooltip" title="Sort order">Sort Order</label>
                            <div class="col-sm-3">
                                <input name="sortorder" class="form-control" type="number" id="sortorder" required="required" />
                            </div>
                            <div>

                            </div>
                        </div>
                          
                          
                          <div class="form-group"> 
			  <form:label path="votetypeid" data-toggle="tooltip" title="Select Vote Types" cssClass="col-sm-3 tooltips control-label">Vote Type:</form:label>  
			  <div class="col-sm-8">
			     <form:select id="votetypeid" path="votetypeid" cssClass="width300">
				<c:forEach items="${votetypes}" var="vote">  
				     <form:option value="${vote.id}">${vote.description}</form:option>
				</c:forEach>
			      </form:select>
			      <form:errors path="votetypeid" cssClass="error"></form:errors>
			  </div>  
		  </div>
                    <div class="form-group"> 
			  <form:label path="electionanswertypeid" data-toggle="tooltip" title="Select Answer types" cssClass="col-sm-3 tooltips control-label">Answer Type:</form:label>  
			  <div class="col-sm-8">
			     <form:select id="electionanswertypeid" path="electionanswertypeid" cssClass="width300">
				<c:forEach items="${answertypes}" var="answer">  
				     <form:option value="${answer.id}">${answer.description}</form:option>
				</c:forEach>
			      </form:select>
			      <form:errors path="electionanswertypeid" cssClass="error"></form:errors>
			  </div>  
		  </div>
                                <div class="form-group"> 
			  <form:label path="maxoption" data-toggle="tooltip" title="maxoption" cssClass="col-sm-3 tooltips control-label">Option Limit:</form:label>  
			  <div class="col-sm-3">
			     <form:select id="maxoption" path="maxoption" cssClass="width">
                                  <form:option value="0">Select Limit</form:option>
				<c:forEach var="i" begin="1" end="10">
                                    
		                   <form:option value="${i}">${i}</form:option>
				</c:forEach>
			      </form:select>
			      <form:errors path="maxoption" cssClass="error"></form:errors>
			  </div>  
                          
		  </div>
                   <div class="form-group" >
                            <label class="col-sm-3 tooltips control-label" data-toggle="tooltip" title="description">Vote Question</label>
                            <div class="col-sm-7">
                              <textarea id="description" style="width:300px; height:80px;"name="description" ></textarea>
                            </div>
                            <div>

                            </div>
                        </div>  
               <div class="form-group" >
                            <label class="col-sm-3 tooltips control-label" data-toggle="tooltip" title="voteoptions">Number Of Options</label>
                            <div class="col-sm-3">
                                <input name="voteoptions" class="form-control" value="" type="number" id="voteoptions" required="required" />
                            </div>
                            <div>

                            </div>
                        </div>          
                       
                       <form:hidden path="active" value="N"/>
		     <form:hidden path="deleted" value="N"/>
		     
		     <form:hidden path="createdby" value="<%=request.getRemoteUser()%>"/>
		   

		     <form:hidden path="createdate"  value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>   
                          
                     
                          
                
                            
               

		  
           <div class="form-group">
		    <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">Next</button>
		    
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
         $("#maxoption").prop("disabled", "true");
         jQuery('select').select2({
                    minimumResultsForSearch: -1
         });
	 
	 jQuery('#addr-item-type').DataTable({
                    responsive: true
         });
    });
    $("#votetypeid").on("change", function() {
        var id = $(this).val();
        if (parseInt(id) === 2) {
            $("#electionanswertypeid").prop("disabled", "true");
        } else {
            $("#electionanswertypeid").removeAttr("disabled");            
        }
    });
      $("#electionanswertypeid").on("change", function() {
        var id = $(this).val();
        if (parseInt(id) === 1) {
            $("#voteoptions").val("3");
        } else {
           $("#voteoptions").val("");        
        }
          if (parseInt(id) === 2) {
            $("#maxoption").removeAttr("disabled");
        }else{
            
              $("#maxoption").prop("disabled", "true");
        }
          if (parseInt(id) === 1) {
            //  window.location='http://localhost:8080/Easycoopfin/voteSetup'
        }
         
    });
  

</script>
   