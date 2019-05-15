<%@ include file="../includes/header.jsp" %> 

<div class="media-body">
    <div style="float:left">
    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Member Management</li>
    </ul>
    <h4>${member.action} Member</h4>
    </div>
    <%@include file="../includes/topright.jsp" %>
</div>

<div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <div class="panel panel-default">
                   <div class="panel-heading">
                       <div class="panel-btns">
                           <a href="#" class="panel-minimize tooltips" data-toggle="tooltip" title="Minimize Panel"><i class="fa fa-minus"></i></a>
                           <a href="#" class="panel-close tooltips" data-toggle="tooltip" title="Close Panel"><i class="fa fa-times"></i></a>
                       </div><!-- panel-btns -->
                       <h4 class="panel-title">Member</h4>
                       <p> </p>
                   </div><!-- panel-heading -->
	       <div class="panel-body nopadding">
	       
		       
		     <form:form method="post" action="../discp/saveMemberDiscipline.htm" commandName="member" class="form-horizontal form-bordered" id="frm">
			 <form:errors path="*" cssClass="error"></form:errors>
			   <div class="form-group">
                  <label class="col-sm-4">Firstname</label>
                    <div class="col-sm-8">
                         ${member.firstName}
                    </div>
                </div><!-- form-group -->
                
                <div class="form-group">
                    <label class="col-sm-4">Middlename</label>
                    <div class="col-sm-8">
                         ${member.middleName}
                    </div>
                </div><!-- form-group -->
                
                <div class="form-group">
                    <label class="col-sm-4">Lastname</label>
                    <div class="col-sm-8">
                          ${member.surname}
                                       </div>
                </div><!-- form-group -->
                
                <div class="form-group">
                    <label class="col-sm-4">Gender</label>
                    <div class="col-sm-8"> 
                        <%--
                        <c:choose>
                            <c:when test="${member.gender == 'm'}">
                               MALE 
                            </c:when>
                            <c:otherwise>
                               FEMALE
                            </c:otherwise>
                        </c:choose> 
                          --%> 
                           <c:choose>
                            <c:when test="${fn:toUpperCase(member.gender) eq 'M'}">
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
              		      ${member.dob}
               </div><!-- input-group -->
        </div><!-- form-group -->
        
        <div class="form-group">
                    <label class="col-sm-4">Religion</label>
                     <div class="col-sm-4">
                    ${member.religion.religionName}
                    </div>
                 </div><!-- form-group -->
                    
                    <div class="form-group">
                    <label class="col-sm-4">Phone No</label>
                    <div class="col-sm-8">
                          ${member.phoneNo1}
                    </div>
                </div><!-- form-group -->
                <div class="form-group">
                    <label class="col-sm-4">Phone No(2)</label>
                    <div class="col-sm-8">
                          ${member.phoneNo2}
                    </div>
                </div><!-- form-group -->
                <div class="form-group">
                    <label class="col-sm-4">Phone No(3)</label>
                    <div class="col-sm-8">
                          ${member.phoneNo3}
                    </div>
                </div><!-- form-group -->
                <div class="form-group">
                    <label  class="col-sm-4">Email Address</label>
                    <div class="col-sm-8">
                          ${member.emailAdd1}
                    </div>
                </div><!-- form-group -->
                <div class="form-group">
                    <label  class="col-sm-4">Email Address(2)</label>
                    <div class="col-sm-8">
                          ${member.emailAdd2}
                    </div>
                </div><!-- form-group -->
                <div class="form-group">
                    <label  class="col-sm-4">Email Address(3)</label>
                    <div class="col-sm-8">
                          ${member.emailAdd3}
                    </div>
                </div><!-- form-group -->
                
                <div class="form-group">
                                       <label class="col-sm-4">Company</label>
                                        <div class="col-sm-4">
                                       <form:select path="company.id"  class="width100p" data-placeholder="Choose One">
							<form:options items="${referenceList.compList}" itemLabel="name" itemValue="Id"  />
                                    </form:select>
                                            
                                       </div>
                                   </div><!-- form-group -->
                                   
                                   <div class="form-group">
                                       <label class="col-sm-4">Branch</label>
                                        <div class="col-sm-4">
                                       ${member.branch.branchName}
                                            
                                       </div>
                                   </div><!-- form-group -->
                                   
                                   <div class="form-group">
                                       <label class="col-sm-4">Company No.</label>
                                       <div class="col-sm-8">
                                             ${member.memberCompId}
                                       </div>
                                   </div><!-- form-group -->
                                   
                                    <div class="form-group">
                                       <label class="col-sm-4">Member Type</label>
                                        <div class="col-sm-4">
                                   		${member.memberType.memberTypeVal}" 
                                       </div>
                                   </div><!-- form-group -->
                                   
                                   <div class="form-group">
                                                <label class="col-sm-4">Identification Document</label>
                                                 <div class="col-sm-4">
                                               ${member.identificationDoc.identificationDocName}"
											                                        
                                                </div>
                                            </div><!-- form-group -->
                                            
                                             <div class="form-group">
                                                <label class="col-sm-4">Identification No.</label>
                                                <div class="col-sm-8">
                                                      ${member.identificationCode}
                                                </div>
                                            </div><!-- form-group -->
                                            
                                             <div class="form-group">
                                                <label class="col-sm-4">Tax Group</label>
                                                 <div class="col-sm-4">
			   											${member.taxGroups.code}" 
                                                </div>
                                            </div><!-- form-group -->
                                            <c:forEach var="addrtype" items="${referenceList.addressType}">
                                         
    										<h5 class="lg-title">${addrtype.value}</h5> 
    										
    										 <c:set var="varList" value="${member.addressEntriesList[addrtype.value]}"/>
    										
    										<c:forEach var="addrList" items="${referenceList.addressList}">
    										
	    										<div class="form-group">
	                                                <label class="col-sm-4">${addrList[2]}</label>
	                                                <div class="col-sm-8">
	                                                     <c:set var="query" value="${addrtype.value}:::${addrList[2]}:::${addrList[1]}"/>
	                                                         	     
	                                                         	   <c:forEach var="addrExist" items="${varList}">
	                                                         	   
	                                                         	   		<c:if test="${addrExist.addrFieldName eq addrList[2]}"><c:set var="addrVal" value="${addrExist.addrFieldValue}"/></c:if>
	                                                         	
	                                                         	    </c:forEach>
	                                                         	                                                     
																		${addrVal}	
	                                                </div>
	                                            </div><!-- form-group -->
    										</c:forEach>
										</c:forEach>
										
                                                    
										<c:forEach var="stckList" items="${member.memberHoldingsMovements}">
    										
	    						<div class="form-group">
                                                                <label class="col-sm-4">${stckList.compStockType.compStockName}</label>
	                                                <div class="col-sm-8">${stckList.movementHoldings} </div>
                                                        
                                                        </div><!-- form-group -->
                                            
    										</c:forEach>
    										 <div class="form-group">
                                                <label class="col-sm-4">Member Bank</label>
                                                 <div class="col-sm-4">
                                                ${member.banks.bankName} 
                                                </div>
                                            </div><!-- form-group -->
                                            
                                            <div class="form-group">
                                                <label class="col-sm-4">Bank Account</label>
                                                 <div class="col-sm-8">
                                                        ${member.bankAccount}
                                                </div>
                                            </div><!-- form-group -->
                                            
                                             <h5 class="lg-title">Additional Fields</h5> 
    										
    										 <c:set var="varList2" value="${member.membersExtrafldEntriesList}"/>
    										<c:forEach var="extraflList" items="${referenceList.extrafldList}">
    										
	    										<div class="form-group">
	                                                <label class="col-sm-4">${extraflList.description}</label>
	                                                <div class="col-sm-8">
	                                                    <c:forEach var="extraflListExist" items="${varList2}">
	                                                         	   
	                                                         	   		<c:if test="${extraflListExist.extraFieldId eq extraflList.id}">
                                                                                            <c:set var="extraflListVal" value="${extraflListExist.extraFieldValue}"/>
                                                                                            <c:set var="extrafloptListVal" value="${extraflListExist.extraFieldoptionValue}"/>
                                                                                        </c:if>
	                                                         	   
	                                                         	    </c:forEach>
	                                                         	                          <c:choose>
                                                                                                      <c:when test="${extraflList.grouped eq 'Y'}">
                                                                                                            
                                                                                                              
                                                                                                         <c:forEach var="extrafldoptList" items="${referenceList.extrafldoptionList}">
                                                                                                             <c:if test="${extrafldoptList[2] == extraflList.id}">
                                                                                                              <c:if test="${extrafloptListVal eq extrafldoptList[0]}">${extrafldoptList[1]}</c:if>    
                                                                                                             </c:if>
													</c:forEach>
												
                                                                                                        
                                                                                                          
                                                                                                      </c:when>
                                                                                                      <c:otherwise>
                                                                                                          ${extraflListVal}
                                                                                                      </c:otherwise>
                                                                                                  </c:choose> 
	                                                </div>
	                                            </div><!-- form-group -->
    										</c:forEach>
                                            
			                          
			   <div class="form-group">
			   <input type="hidden" name="memberId" value="${member.memberId}" />
                
                           <button class="btn btn-danger mr5" id="SUSPEND" type="submit" name="action" value="SUSPEND" onclick="return confirm('Are you sure you want suspend member?')"> SUSPEND </button> 
		           <button class="btn btn-danger mr5" id="EXIT" type="submit" name="action" value="EXIT" onclick="return confirm('Are you sure you want to exit member?')" >EXIT</button>
                           <button class="btn btn-danger mr5" id="RESIGN" type="submit" name="action" value="RESIGN" onclick="return confirm('Are you sure member wants to resign?')" >RESIGN</button>
				    
			    </div><!-- panel-footer -->
		      </form:form>  
      </div>
      
            </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->

<%@ include file="../includes/footer.jsp" %>  
<script>
    $(document).ready(function(){
         jQuery('select').select2({
                    minimumResultsForSearch: -1
                });
	jQuery('#company-list').DataTable({
                    responsive: true
                });
        // This will submit the basicWizard form
        $('.btn btn-danger mr5').click(function() {
            if (confirm('Are you sure?')) {
                //var url = $(this).attr('href');
            $('#frm').submit();
            }
           });
    })
</script>