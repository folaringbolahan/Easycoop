<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'success.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
<table  align="left" width="100%">
            <tr class="darkrow"><td width="100%">
        <table width="90%" align="left" cellpadding="6"  cellspacing="0" bgcolor="#FFFFFF" >
            
            <tr class="row2">
                <td align="center"><center>
	           <font size="2" color="red" face="century gothic"><b><%if(pageContext.getAttribute("result",PageContext.REQUEST_SCOPE)!=null){ %><%=(String)pageContext.getAttribute("result",PageContext.REQUEST_SCOPE)%><%}%></b></font>
	    </center></td>
            </tr>
        </table>
    </td></tr>
</table> 
  </body>
</html>
