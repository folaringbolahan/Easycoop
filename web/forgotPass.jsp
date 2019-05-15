<%--
Views should be stored under the WEB-INF folder so that
they are not accessible except through controller process.

This JSP is here to provide a redirect to the dispatcher
servlet but should be the only JSP outside of WEB-INF.
--%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty param.login_error}">
<font color="red">
Login error. <br />
Reason : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
</font>
</c:if>
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
		    if(frm.email.value==""){alert("email is required"); frm.mail.focus(); return;}
		    //if(frm.password.value==""){alert("please enter your password"); frm.password.focus(); return;}
	
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
                    <h4 class="text-center mb5">Logon Password Reset</h4>
                    <p class="text-center">&nbsp;</p>
                    
                    <div class="mb30"></div>			
			<form method="post" class="signin" action="updateAppLogon.htm" commandName="user">     
                         <div class="input-group mb15">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                            <input id="email" name="email" type="text" class="form-control" placeholder="Username (e.g yomi@syphox.com)">
                        </div>
                        
                        <%--                        
				<div class="input-group mb15">
				    <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
				    <input id="password" type="password" name="password" class="form-control" placeholder="Password">
				</div>

				<div class="input-group mb15">
				    <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
				    <input id="confirmPassword" type="password" name="confirmPassword" class="form-control" placeholder="confirmPassword">
				</div>                        
                        --%>
                        
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