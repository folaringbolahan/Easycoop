<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="-1" />
</head>
<%
request.setAttribute(org.zkoss.zk.ui.sys.Attributes.NO_CACHE,
Boolean.TRUE);
%>
<body style="width:100%;height:auto">
<div class="media-body">
                                <ul class="breadcrumb">
                                    <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
                                    <li><a href="index.htm">Home</a></li>
                                    <li>Journal Posting</li>
                                </ul>
                                <h4>Journal Posting</h4>
                            </div>
                        </div><!-- media -->
                    </div>
<div class="contentpanel">
    <p>
       <jsp:include page="/zk/gl_journal.zul"/>
     </p>
 </div>  
</body>
</html>