<%@ include file="includes/header.jsp" %>  
<div class="media-body">
        <div style="float:left">
	    <ul class="breadcrumb">
		<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		<li><a href="#">Home</a></li>
		<li>settings</li>
	    </ul>
	    <h4>Edit Settings</h4>
	</div>
	<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<script lanuage="Javascript">
	function doSubmit(frm){	
	    if(frm.value.value==""){alert("value is required"); frm.value.focus(); return;}
	    
	    frm.submit();
	}	  
  </script>
<div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
	       <div class="panel-body">
                 <c:set var="now" value="<%=new java.util.Date()%>" />  
		      <form:form method="post" action="saveSetting" commandName="setting">
                           <div class="form-group"> 
				  <form:label path="companyName" cssClass="col-sm-4 control-label">Company Name:</form:label>  
 				  <div class="col-sm-2">
 				     <form:input path="companyName" value="${setting.companyName}" disabled="disable" readonly="true"/>
                                    </div>
                               </div>
			       <div class="form-group"> 
				  <form:label path="setting" cssClass="col-sm-4 control-label">Setting:</form:label>  
 				  <div class="col-sm-2">
 				     <form:input path="setting" value="${setting.setting}" disabled="disable" readonly="true"/>
                                    </div>
                               </div>
                                <div class="form-group"> 
				  <form:label path="value" cssClass="col-sm-4 control-label">Value:</form:label>  
 				  <div class="col-sm-2">
 				     <form:input path="value" value="${setting.value}" />
                                    </div>
                               </div>
                                    
                               <form:hidden path="display" value="${setting.display}"/>  
                                   <form:hidden path="id" value="${setting.id}"/>
                                    <form:hidden path="companyid" value="${setting.companyid}"/>
                                     <div class="form-group">
                            <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">SAVE</button>
                            
                        </div>
                    </form:form>  
        </div>
            
	     
	  
    
            </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->

<%@ include file="includes/footer.jsp" %>  
