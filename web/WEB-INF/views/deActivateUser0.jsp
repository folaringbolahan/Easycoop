<%@ include file="includes/header.jsp" %>  

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-i18n.min.js"></script> 

<script type="text/javascript"> 
      function doSubmitForm(frm){
	if(frm.email.value==""){
	   alert("Please select user to deactivate"); 
	   return;
	}	     

	frm.submit();
      }
</script>
 
<div class="media-body">
         <div style="float:left">
	     <ul class="breadcrumb">
		 <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		 <li><a href="#">Home</a></li>
		 <li>User Deactivation</li>
	     </ul>
	     <h4>Deactivate User</h4>
	 </div>
	<%@include file="includes/topright.jsp" %>
 </div>
 </div><!-- media -->
 </div><!-- pageheader -->
 <div class="contentpanel">
     <div class="row">
         <div class="col-md-10">
             <!-- CONTENT GOES HERE -->  
             <div class="col-md-15">
 	       <div class="panel-body">
                 <c:set var="now" value="<%=new java.util.Date()%>" />                 
	      		<form:form method="post" action="deactivateUserNew.htm" commandName="user">
				  <div class="form-group"> 
					  <form:label path="email" data-toggle="tooltip" title="Select User's Email Address" cssClass="col-sm-3 tooltips control-label">Select User:</form:label>  
					  <div class="col-sm-8">
					     <form:select id="email" path="email" cssClass="width300">
					        <form:option value="">--select--</form:option>
						<c:forEach items="${users}" var="item">  
						     <form:option value="${item.email}">${item.email}</form:option>
						</c:forEach>
					      </form:select>
					      <form:errors path="email" cssClass="error"></form:errors>
					  </div>  
				  </div> 

				   <div class="form-group">
					  <button class="btn btn-danger mr5" type="button" onclick="doSubmitForm(this.form);"> SUBMIT </button>
					  <button type="reset" class="btn btn-default">RESET</button>
				   </div><!-- panel-footer -->        
	   		</form:form>  
   </div>

   
             </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->
   