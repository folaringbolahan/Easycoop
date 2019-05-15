<%-- 
    Document   : enqContrb
    Created on : Nov 13, 2015, 12:58:21 AM
    Author     : baydel200
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


   <c:set var="datePattern" value="yyyy-MM-dd" />
   <c:set var="amtPattern" value="###,###,###,###.00" />
   <c:set var="numPattern" value="###,###,###,###" />
   <c:set var="dateTimePattern" value="yyyy-MM-dd hh:mm:ss a" />
   
<c:set var="theValue" value="${data['CNTR']}"/>
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
                   
          <c:forEach items="${data['CNTR']}" var="entry" >
	     <div class="panel-body nopadding">
                 <div class="form-group">
                  <label class="col-sm-4">Product</label>
                    <div class="col-sm-8">
                       ${entry['name']}
                    </div>
                </div><!-- form-group -->
                <div class="form-group">
                  <label class="col-sm-4">Amount</label>
                    <div class="col-sm-8">
                         <fmt:formatNumber pattern="${amtPattern}" value="${entry['contr_val']}"/>
                    </div>
                </div><!-- form-group -->
                
                <div class="form-group">
                 <label class="col-sm-4">Created Date</label>
              		     <div class="input-group">
              		      <fmt:formatDate pattern="${dateTimePattern}" value="${entry['created_date']}"/>
                            </div><!-- input-group -->
                </div><!-- form-group -->
        
                <div class="form-group">
                    <label class="col-sm-4">Created By.</label>
                     <div class="col-sm-4">
                   ${entry['created_by']}
                    </div>
                 </div><!-- form-group -->
                 
                <div class="form-group">
                 <label class="col-sm-4">Modified Date</label>
              		     <div class="input-group">
              		      <fmt:formatDate pattern="${dateTimePattern}" value="${entry['modified_date']}"/>
                            </div><!-- input-group -->
                </div><!-- form-group -->
        
                <div class="form-group">
                    <label class="col-sm-4">Modified By.</label>
                     <div class="col-sm-4">
                   ${entry['modified_by']}
                    </div>
                 </div><!-- form-group -->
                 
                 <div class="form-group">
                 <label class="col-sm-4">Approved Date</label>
              		     <div class="input-group">
              		      <fmt:formatDate pattern="${dateTimePattern}" value="${entry['approved_date']}"/>
                            </div><!-- input-group -->
                </div><!-- form-group -->
        
                <div class="form-group">
                    <label class="col-sm-4">Approved By.</label>
                     <div class="col-sm-4">
                   ${entry['approved_by']}
                    </div>
                 </div><!-- form-group -->
                  
                </div>
              </c:forEach>
            </div>
        </div>
    </div>
</div>
