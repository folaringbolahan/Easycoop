      <%@ include file="includes/header.jsp" %>  
		      
      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui.js"></script>
      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-i18n.min.js"></script> 

      <script lanuage="Javascript">
	function doSubmit(frm){	
	    if(frm.email.value==""){alert("email is required"); frm.email.focus(); return;}
	    if(frm.oldpassword.value==""){alert("please enter value for old password"); frm.oldpassword.focus(); return;}
	    if(frm.password.value==""){alert("please enter value for password"); frm.password.focus(); return;}
	    if(frm.confirmPassword.value==""){alert("please enter value for confirm password"); frm.confirmPassword.focus(); return;}

	    if(frm.password.value != "" && frm.password.value == frm.confirmPassword.value){
		      if(frm.password.value.length < 6){
			alert("Error: Password must contain at least six characters!");
			frm.password.focus();
			return;
		      }

		      if(frm.password.value == frm.email.value) {
			alert("Error: Password must be different from email!");
			frm.password.focus();
			return;
		      }

		      re = /[0-9]/;
		      if(!re.test(frm.password.value)) {
			alert("Error: password must contain at least one number (0-9)!");
			frm.password.focus();
			return;
		      }

		      re = /[a-z]/;
		      if(!re.test(frm.password.value)) {
			alert("Error: password must contain at least one lowercase letter (a-z)!");
			frm.password.focus();
			return;
		      }

		      re = /[A-Z]/;
		      if(!re.test(frm.password.value)) {
			alert("Error: password must contain at least one uppercase letter (A-Z)!");
			frm.password.focus();
			return;
		      }
	    }else {
		      alert("Error: Please check that you've entered and confirmed your password!");
		      frm.password.focus();
		      return;
	    }

	    //submit if all is well.
	    frm.submit(); 

	}	  
        </script>
    
    
 <div class="media-body">
                  <div style="float:left">
		     <ul class="breadcrumb">
			 <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
			 <li><a href="#">Home</a></li>
			 <li>User Management</li>
		     </ul>
		     <h4>Change Logon Password</h4>
		 </div>
		 <%@include file="includes/topright.jsp"%>
 </div>
 </div><!-- media -->
 </div><!-- pageheader -->
 <div class="contentpanel">
     <div class="row">
         <div class="col-md-8">
             <!-- CONTENT GOES HERE -->  
             <div class="col-md-10">
 	       <div class="panel-body">
                 <c:set var="now" value="<%=new java.util.Date()%>" />
                 
	      			<form method="post" class="signin" action="changeMyLogonPassPost.htm" commandName="user">     
	                               <div class="input-group mb15">
	                                  <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
	                                  <input id="email" name="email" type="text" class="form-control" placeholder="Username (e.g yomi@syphox.com)">
	                              </div><!-- input-group -->
	      
	                              <div class="input-group mb15">
	                                  <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
	                                  <input id="oldpassword" type="password" name="oldpassword" class="form-control" placeholder="Old Password">
	                              </div><!-- input-group -->
	                              
	                              <div class="input-group mb15">
	                                  <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
	                                  <input id="password" type="password" name="password" class="form-control" placeholder="New Password">
	                              </div><!-- input-group -->
	                              
	                              <div class="input-group mb15">
	      			    <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
	      			    <input id="confirmPassword" type="password" name="confirmPassword" class="form-control" placeholder="confirmPassword">
	                              </div><!-- input-group -->
	                              
	                              <div class="clearfix">
	                                  <div class="pull-left">
	                                      <div class="ckbox ckbox-primary mt10">
	                                          <input type="checkbox" id="emailMe" value="1" checked="true">
	                                          <label for="emailMe">Email Me Copy</label>
	                                      </div>
	                                  </div>
	      
	                                  <div class="pull-right">
	                                      <button type="button" class="btn btn-danger" onclick="Javascript:doSubmit(this.form);">Submit <i class="fa fa-angle-right ml5"></i></button>
	                                  </div>
	                              </div>  
	      		  </form>
  
   	</div>   
   

    	   </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->

<%@ include file="includes/footer.jsp" %>   
