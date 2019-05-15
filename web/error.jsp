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
		    if(frm.oldpassword.value==""){alert("please enter value for old password"); frm.oldpassword.focus(); return;}
		    if(frm.password.value==""){alert("please enter value for new password"); frm.password.focus(); return;}
		    if(frm.password.value!=frm.confirmPassword.value){alert("The Two passwords must match"); frm.password.focus(); return;}
	
		    frm.submit();
		}	  
        </script>
    </head>
<body>
<div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <div class="col-md-12">
	      <div class="panel-body">
	      	      
		     <div class="alert alert-info fade in nomargin">
			  <button aria-hidden="true" data-dismiss="alert" class="close" type="button">&times;</button>
			  <h4 align="center"><font color="red">Processing Error !!!</font></h4>
			  <hr/>
			  
			  <p><h5 align="center"><font color="red"><%=request.getParameter("message")%> !!!</font></h5></p>
			  <p align="center">
			      <button onclick="location.href='<%=request.getParameter("redirectURI")%>'" class="btn btn-danger" type="button">Continue</button>
			      <button class="btn btn-white" type="button">Cancel</button>
			  </p>
                     </div>
	      
	      
               </div>
          </div><!-- col-md-6 -->
      </div>
    </div>
</div><!-- contentpanel -->


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




   