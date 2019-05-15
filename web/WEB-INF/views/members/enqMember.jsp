<%-- 
    Document   : enqMember
    Created on : Nov 13, 2015, 1:01:18 AM
    Author     : baydel200
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

   <c:set var="datePattern" value="yyyy-MM-dd" />
   <c:set var="amtPattern" value="###,###,###,###.00" />
   <c:set var="numPattern" value="###,###,###,###" />
   <c:set var="dateTimePattern" value="yyyy-MM-dd hh:mm:ss a" />
   
 <c:set var="theValue" value="${data.MEM}"/>
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
                   
        <c:forEach items="${data.MEM}" var="entry" >
	     <div class="panel-body nopadding">
                 <div class="form-group">
                  <label class="col-sm-4">Member No.</label>
                    <div class="col-sm-8">
                         ${entry['member_no']}
                    </div>
                </div><!-- form-group -->
                <div class="form-group">
                  <label class="col-sm-4">First Name</label>
                    <div class="col-sm-8">
                         ${entry['firstName']}
                    </div>
                </div><!-- form-group -->
                
                <div class="form-group">
                    <label class="col-sm-4">Middle Name</label>
                    <div class="col-sm-8">
                         ${entry['middlename']}
                    </div>
                </div><!-- form-group -->
                
                <div class="form-group">
                    <label class="col-sm-4">Last Name</label>
                    <div class="col-sm-8">
                          ${entry['surname']}
                     </div>
                </div><!-- form-group -->
                
                <div class="form-group">
                    <label class="col-sm-4">Gender</label>
                    <div class="col-sm-8">
                        <c:choose>
                            <c:when test="${fn:toUpperCase(entry['gender']) eq 'M'}">
                               MALE 
                            </c:when>
                            <c:otherwise>
                               FEMALE
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div><!-- form-group -->
                <div class="form-group">
                 <label class="col-sm-4">Date Of Birth</label>
              		     <div class="input-group">
              		      <fmt:formatDate pattern="${datePattern}" value="${entry['dob']}"/>
               </div><!-- input-group -->
        </div><!-- form-group -->
        
        <div class="form-group">
                    <label class="col-sm-4">Religion</label>
                     <div class="col-sm-4">
                   ${entry['religion_name']}
                    </div>
                 </div><!-- form-group -->
                    
                    <div class="form-group">
                    <label class="col-sm-4">Phone No</label>
                    <div class="col-sm-8">
                          ${entry['phone_no_1']}
                    </div>
                </div><!-- form-group -->
                <div class="form-group">
                    <label class="col-sm-4">Phone No(2)</label>
                    <div class="col-sm-8">
                          ${entry['phone_no_2']}
                    </div>
                </div><!-- form-group -->
                <div class="form-group">
                    <label class="col-sm-4">Phone No(3)</label>
                    <div class="col-sm-8">
                          ${entry['phone_no_3']}
                    </div>
                </div><!-- form-group -->
                <div class="form-group">
                    <label  class="col-sm-4">Email Address</label>
                    <div class="col-sm-8">
                         ${entry['email_add_1']}
                    </div>
                </div><!-- form-group -->
                <div class="form-group">
                    <label  class="col-sm-4">Email Address(2)</label>
                    <div class="col-sm-8">
                         ${entry['email_add_2']}
                    </div>
                </div><!-- form-group -->
                <div class="form-group">
                    <label  class="col-sm-4">Email Address(3)</label>
                    <div class="col-sm-8">
                          ${entry['email_add_3']}
                    </div>
                </div><!-- form-group -->
                
                <div class="form-group">
                        <label class="col-sm-4">Company</label>
                         <div class="col-sm-8">
                                  ${entry['comp_name']}
                        </div>
                </div><!-- form-group -->
                                   
            <div class="form-group">
                <label class="col-sm-4">Branch</label>
                 <div class="col-sm-8">
                ${entry['branch_name']}

                </div>
            </div><!-- form-group -->

            <div class="form-group">
                <label class="col-sm-4">Company No.</label>
                <div class="col-sm-8">
                      ${entry['comp_mem_id']}
                </div>
            </div><!-- form-group -->

             <div class="form-group">
                <label class="col-sm-4">Member Type</label>
                 <div class="col-sm-4">
                         ${entry['type_val']}
                </div>
            </div><!-- form-group -->

            <div class="form-group">
                         <label class="col-sm-4">Identification Document</label>
                          <div class="col-sm-4">
                        ${entry['iden_doc_name']}

                         </div>
                     </div><!-- form-group -->

                      <div class="form-group">
                         <label class="col-sm-4">Identification No.</label>
                         <div class="col-sm-8">
                                ${entry['ident_code']}
                         </div>
                     </div><!-- form-group -->   
                     
                     <div class="form-group">
                         <label class="col-sm-4">Next of Kin Surname.</label>
                         <div class="col-sm-8">
                                ${entry['nok_surname']}
                         </div>
                     </div><!-- form-group --> 
                   
                      <div class="form-group">
                         <label class="col-sm-4">Next of Kin First Name.</label>
                         <div class="col-sm-8">
                                ${entry['nok_name']}
                         </div>
                     </div><!-- form-group --> 
                     
                     <div class="form-group">
                         <label class="col-sm-4">Next of Kin Middle Name</label>
                         <div class="col-sm-8">
                                ${entry['nok_middlename']}
                         </div>
                     </div><!-- form-group --> 
                     
                     <div class="form-group">
                         <label class="col-sm-4">Next Kin Phone No.</label>
                         <div class="col-sm-8">
                                ${entry['nok_phone']}
                         </div>
                     </div><!-- form-group --> 
                         
                      <div class="form-group">
                         <label class="col-sm-4">Member's Bank Name</label>
                         <div class="col-sm-8">
                                ${entry['bank_name']}
                         </div>
                     </div><!-- form-group --> 
                     
                     <div class="form-group">
                         <label class="col-sm-4">Member's Bank Account Number</label>
                         <div class="col-sm-8">
                                ${entry['bank_account']}
                         </div>
                     </div><!-- form-group --> 
                      <div class="form-group">
                         <label class="col-sm-4">Member's Contribution</label>
                         <div class="col-sm-8">
                                ${entry['member_contrib_value']}
                         </div>
                     </div><!-- form-group --> 
                      <div class="form-group">
                         <label class="col-sm-4">Member's Holdings</label>
                         <div class="col-sm-8">
                                ${entry['holdings']}
                         </div>
                     </div><!-- form-group --> 
                </div>
               </c:forEach>
            </div>
        </div>
    </div>
</div>

