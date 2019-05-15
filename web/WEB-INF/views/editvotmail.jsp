<%@ include file="includes/header.jsp" %>  
<div class="media-body">
        <div style="float:left">
	    <ul class="breadcrumb">
		<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		<li><a href="#">Home</a></li>
		<li>EDIT VOTE MEMBERS EMAIL</li>
	    </ul>
	    <h4>Edit Vote Members Email</h4>
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
                
		      <form:form method="post" action="updateVotMemberEmails" commandName="votmembers">
                           <div class="form-group">
                        <label data-toggle="tooltip"  class="col-sm-3 tooltips control-label">First Name : </label>
                          <div class="col-sm-7">                                           
                              <form:input path="firstname" id="firstname" cssClass="form-control" readonly="true" value= "${membersdet.firstname}  " />                                                                 
                          </div>
                    </div>
                          
                           <div class="form-group">
                        <label data-toggle="tooltip"  class="col-sm-3 tooltips control-label">Middle Name : </label>
                          <div class="col-sm-7">                                           
                              <form:input path="middlename" id="middlename" cssClass="form-control" readonly="true" value= "${membersdet.middlename} " />                                                                 
                          </div>
                    </div>
                         <div class="form-group">
                        <label data-toggle="tooltip"  class="col-sm-3 tooltips control-label"> Surname : </label>
                          <div class="col-sm-7">                                           
                              <form:input path="surname" id="surname" cssClass="form-control" readonly="true" value= "${membersdet.surname}" />                                                                 
                          </div>
                    </div>
                            <div class="form-group">
                        <label data-toggle="tooltip"  class="col-sm-3 tooltips control-label"> Email : </label>
                          <div class="col-sm-7">                                           
                              <form:input path="newemail" id="newemail" cssClass="form-control"  value= "${membersdet.email}" />                                                                 
                          </div>
                    </div>
			      
                               <form:hidden path="memberid" value="${membersdet.memberid}"/>   
                               <form:hidden path="oldemail" value="${membersdet.email}"/>      
                               <form:hidden path="agmid" value="${membersdet.agmid}"/> 
                          
                               
                                   <form:hidden path="createdby" value="<%=request.getRemoteUser()%>"/>
                                    
                                     <div class="form-group">
                            <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">CHANGE</button>
                            
                        </div>
                    </form:form>  
        </div>
            
	     
	  
    
            </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->

<%@ include file="includes/footer.jsp" %>  
