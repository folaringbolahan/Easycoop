<%@ include file="includes/header.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EasyCoop Financials</title>
    </head>
    <body>

    <p>Welcome ${currrentuserServicex.curruser.userName}</p>
        
        <div style="float:right">
    
       
        
                <h3 class="media-heading" style="color: red">${currrentuserServicex.currusercompany.startofDay} </h3>
                
                <small class="text-muted">${currrentuserServicex.currusercompany.name} .</small>
              
                <small class="text-muted">Postdate :${currrentuserServicex.currusercompany.postDate} </small>
                
                <small class="text-muted">Period :${currrentuserServicex.currusercompany.currentPeriod}, ${currrentuserServicex.currusercompany.currentYear} </small>
                
        </div>
          
    </body>
</html>

<%@ include file="includes/footer.jsp" %>  