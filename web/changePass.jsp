<%--
Views should be stored under the WEB-INF folder so that
they are not accessible except through controller process.

This JSP is here to provide a redirect to the dispatcher
servlet but should be the only JSP outside of WEB-INF.
--%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>EasyCoopFin - Accounting System for Cooperatives</title>

        <link href="<%=request.getContextPath()%>/resources/css/style.default.css" rel="stylesheet">

        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="js/html5shiv.js"></script>
        <script src="js/respond.min.js"></script>
        <![endif]-->
        
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
    </head>

    <body class="signin">              
        <section>            
            <div class="panel panel-signin">
                <div class="panel-body">
                    <div class="logo text-center">
                        <img src="<%=request.getContextPath()%>/resources/images/logo-primary.png" alt="Chain Logo" >
                    </div>
                    <br />
                    <h4 class="text-center mb5">Change Logon Password</h4>
                    <p class="text-center">&nbsp;</p>
                    
                    <div class="mb30"></div>			
			<form method="post" class="signin" action="changeMyLogonPass2.htm" commandName="user">     
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
    
                </div><!-- panel-body -->                
            </div><!-- panel -->            
        </section>

        <script src="<%=request.getContextPath()%>/resources/js/jquery-1.11.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery-migrate-1.2.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/modernizr.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/pace.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/retina.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery.cookies.js"></script>

        <script src="<%=request.getContextPath()%>/resources/js/custom.js"></script>

    </body>
</html>