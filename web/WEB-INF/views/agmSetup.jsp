<%@ include file="includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Agm Set-up</li>
    </ul>
    <h4> Agm Set-up</h4>
</div>
<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<script lanuage="Javascript">
	function doSubmit(frm){
             if(frm.description.value==""){alert("Agm name is required"); frm.description.focus(); return;} 
          if(frm.agmyear.value==""){alert("Agm year is required"); frm.agmyear.focus(); return;}
	  if(frm.startdate.value>=frm.enddate.value){alert("Enddate cannot be less than Start date"); frm.enddate.focus(); return;}   
	    frm.submit();
	}	  
  </script>
  <div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
	     <div class="panel-body">	
          
                 <form:form method="post" action="saveAgmSetup" commandName="votAgm">
                
                   <div class="form-group"> 
			  <form:label path="companyid" data-toggle="tooltip" title="Ballot Type" cssClass="col-sm-3 tooltips control-label">Company Name <span class="asterisk">*</span> :</form:label>  
			  <div class="col-sm-5">
			     <form:select id="companyid" path="companyid" cssClass="width300">
				 
				     <form:option value="${current.companyid}">${current.companyname}</form:option>
				
			      </form:select>
			      <form:errors path="companyid" cssClass="error"></form:errors>
			  </div>  
                          
		  </div>
             <div class="form-group"> 
			  <form:label path="ballotid" data-toggle="tooltip" title="Ballot Type" cssClass="col-sm-3 tooltips control-label">Ballot Type <span class="asterisk">*</span> :</form:label>  
			  <div class="col-sm-5">
			     <form:select id="ballotid" path="ballotid" cssClass="width300">
				<c:forEach items="${ballot}" var="item">  
				     <form:option value="${item.id}">${item.description}</form:option>
				</c:forEach>
			      </form:select>
			      <form:errors path="ballotid" cssClass="error"></form:errors>
			  </div>  
                          
		  </div>
                           <div class="form-group">
                     <form:label path="startdate" cssClass="col-sm-3 tooltips control-label">Start Date <span class="asterisk">*</span> :</form:label>
                            <div class="col-sm-5">
                                <div class="input-group">
                                <form:input path="startdate" placeholder="dd/mm/yyyy" id="startdate" cssClass="form-control"  size="10" />
                            <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                             
                                </div>
                                <form:errors path="startdate" cssClass="error" />
                            </div>
                            
                    </div>
                            
                            <div class="form-group">
         <form:label path="starttime" cssClass="col-sm-3 tooltips control-label">Start Time <span class="asterisk">*</span> :</form:label>
          <div class="col-sm-5">
          <div class="input-group mb15">
          <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
          <div class="bootstrap-timepicker"><form:input path="starttime" cssClass="form-control"  id="starttime" required="required" /></div>
           </div>

             </div>
             </div>
                            <div class="form-group">
                        <form:label path="enddate" cssClass="col-sm-3 tooltips control-label">End Date <span class="asterisk">*</span> :</form:label>
                            <div class="col-sm-5">
                                <div class="input-group">
                                <form:input path="enddate" placeholder="dd/mm/yyyy" id="enddate" cssClass="form-control"  size="10" />
                            <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                             
                                </div>
                                <form:errors path="enddate" cssClass="error" />
                            </div>
                            
                    </div>
                       <div class="form-group">
                        <form:label path="endtime" cssClass="col-sm-3 tooltips control-label">End time <span class="asterisk">*</span> :</form:label>
                            <div class="col-sm-5">
                                <div class="input-group">
                               
                            <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                             <div class="bootstrap-timepicker"><form:input path="endtime" cssClass="form-control"  id="endtime" required="required" /></div>
                                </div>
                                <form:errors path="endtime" cssClass="error" />
                            </div>
                            
                    </div>
              
                    
          
              

                               <div class="form-group"> 
			  <form:label path="reminderfrequency" data-toggle="tooltip" title="Reminder Frequency" cssClass="col-sm-3 tooltips control-label">Reminder Frequency(Days) <span class="asterisk">*</span> :</form:label>  
			  <div class="col-sm-3">
			     <form:select id="reminderfrequency" path="reminderfrequency" cssClass="width">
                                  <form:option value="0">Choose Reminder</form:option>
				<c:forEach var="i" begin="1" end="31">
                                    
		                   <form:option value="${i}">${i}</form:option>
				</c:forEach>
			      </form:select>
			      <form:errors path="reminderfrequency" cssClass="error"></form:errors>
			  </div>  
                          
		  </div>
                    <div class="form-group">
                        <label data-toggle="tooltip"  class="col-sm-3 tooltips control-label">Agm Year <span class="asterisk">*</span>  : </label>
                          <div class="col-sm-7">                                           
                              <form:input path="agmyear" id="agmyear" cssClass="form-control" value= "" />                                                                 
                          </div>
                    </div>
                     <div class="form-group">
                        <label data-toggle="tooltip"  class="col-sm-3 tooltips control-label">Description <span class="asterisk">*</span> : </label>
                          <div class="col-sm-7">                                           
                              <form:input path="description" id="descripion" cssClass="form-control" value= ""  />                                                                 
                          </div>
                    </div>
                  
</body> 
                 
                  
                    
                    
		     <form:hidden path="active" value="N"/>
                     <form:hidden path="closed" value="N"/>
                      <form:hidden path="lastreminderdate" value='<%=new java.text.SimpleDateFormat("yyyy/MM/dd ").format(new java.util.Date())%>'/>
		     <form:hidden path="createdate"  value='<%=new java.text.SimpleDateFormat("yyyy/MM/dd ").format(new java.util.Date())%>'/>
		     <form:hidden path="createdby" value="<%=request.getRemoteUser()%>"/>
		     
		  
           <div class="form-group">
		    <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">SAVE</button>
		    <button type="reset" class="btn btn-default">RESET</button>
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
    //var startDate = document.getElementById("startdate").value;
    //var endDate = document.getElementById("enddate").value;
     var startDate = $("#startdate").val();  //document.getElementById("startdate").value;
    var endDate = $("#enddate").val(); //document.getElementById("enddate").value;

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
   