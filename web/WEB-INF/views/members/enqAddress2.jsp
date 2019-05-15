<%-- 
    Document   : enqAddress2
    Created on : Nov 13, 2015, 12:59:33 AM
    Author     : baydel200
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%--<c:set var="theValue" value="${data.}"/>--%>

<div class="contentpanel">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                   <div class="panel-heading">
                       <div class="panel-btns">
                           <a href="#" class="panel-minimize tooltips" data-toggle="tooltip" title="Minimize Panel"><i class="fa fa-minus"></i></a>
                           <a href="#" class="panel-close tooltips" data-toggle="tooltip" title="Close Panel"><i class="fa fa-times"></i></a>
                       </div><!-- panel-btns -->
                       <h4 class="panel-title"></h4>
                       <p> </p>
                   </div><!-- panel-heading -->
              <div class="panel-body nopadding">     
        <c:forEach items="${data['ADDR-HOM']}" var="entry" >
	     
                 <div class="form-group">
                  <label class="col-sm-4">${entry['addr_fld']}</label>
                    <div class="col-sm-8">
                         ${entry['addr_val']}
                    </div>
                </div><!-- form-group -->

               </c:forEach>
              </div>
            </div>
        </div>
    </div>
</div>
