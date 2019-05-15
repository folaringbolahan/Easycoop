<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


   <c:set var="datePattern" value="yyyy-MM-dd" />
   <c:set var="amtPattern" value="###,###,###,###.00" />
   <c:set var="numPattern" value="###,###,###,###" />
   <c:set var="dateTimePattern" value="yyyy-MM-dd hh:mm:ss a" />
   
<c:set var="theValue" value="${data['SAV']}"/>
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
                   
          <c:forEach items="${data['SAV']}" var="entry" >
	     <div class="panel-body nopadding">
                 <div class="form-group">
                  <label class="col-sm-4">Account name</label>
                    <div class="col-sm-8">
                       ${entry['name']}
                    </div>
                </div><!-- form-group -->
                <div class="form-group">
                  <label class="col-sm-4">Amount</label>
                    <div class="col-sm-8">
                         <fmt:formatNumber pattern="${amtPattern}" value="${entry['balance']}"/>
                    </div>
                </div><!-- form-group -->
                
                <div class="form-group">
                 <label class="col-sm-4">Created Date</label>
              		     <div class="input-group">
              		      <fmt:formatDate pattern="${dateTimePattern}" value="${entry['dateopened']}"/>
                            </div><!-- input-group -->
                </div><!-- form-group -->
        
               
                  
                </div>
              </c:forEach>
            </div>
        </div>
    </div>
</div>
