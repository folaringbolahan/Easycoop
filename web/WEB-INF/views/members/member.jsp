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
                                <h5 class="lg-title"></h5>
                                <p class="mb20"></p>

                                <!-- BASIC WIZARD -->
                                <form:form method="post" action="saveMember.htm" id="basicWizard" class="panel-wizard" modelAttribute="member" >
                                    
                               
                                    <ul class="nav nav-justified nav-wizard">
                                        <li><a href="#tab1" data-toggle="tab"><strong>Basic</strong> </a></li>
                                        <li><a href="#tab2" data-toggle="tab"><strong>Cooperative</strong> </a></li>
                                        <li><a href="#tab3" data-toggle="tab"><strong>Contact</strong></a></li>
                                        <li><a href="#tab4" data-toggle="tab"><strong>Address</strong></a></li>
                                        <li><a href="#tab5" data-toggle="tab"><strong>Shares</strong></a></li>
                                        <li><a href="#tab6" data-toggle="tab"><strong>Additional Fields</strong></a></li>
                                    </ul>
                
                                    <div class="tab-content">
                                        <div class="tab-pane" id="tab1">
                                            <div class="form-group " >
                                                <label class="col-sm-4 control-label tooltips" data-original-title="Member's Firstname" data-toggle="tooltip" data-placement="left">Firstname * </label>
                                                <div class="col-sm-8">
                                                     <form:input type="text" path="firstName" class="form-control" required="true"/>
                                                     <div class="error">
                                                        <form:errors path="firstName" />
                                                            <form:errors path="*" />
                                                      </div>
                                                </div>
                                            </div><!-- form-group -->
                                            
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label tooltips" data-original-title="Member's Middlename" data-toggle="tooltip" data-placement="left">Middlename</label>
                                                <div class="col-sm-8">
                                                     <form:input type="text" path="middleName" class="form-control" />
                                                      <div class="error">
                                                        <form:errors path="middleName" />
                                                      </div>
                                                </div>
                                            </div><!-- form-group -->
                                            
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label tooltips" data-original-title="Member's Lastname" data-toggle="tooltip" data-placement="left">Lastname *</label>
                                                <div class="col-sm-8">
                                                     <form:input type="text" path="surname" class="form-control" required="true"/>
                                                     <div class="error">
                                                        <form:errors path="surname" />
                                                      </div>
                                                </div>
                                            </div><!-- form-group -->
                                            
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label tooltips" data-original-title="Member's Gender" data-toggle="tooltip" data-placement="left">Gender *</label>
                                                <div class="col-sm-8">
                                                    <div class="rdio rdio-primary">
                                                         <form:radiobutton path="gender" checked="checked" id="male3" value="M" name="radio"/>
                                                        <label  for="male3">Male</label>
                                                    </div>
                                                    <div class="rdio rdio-primary">
                                                         <form:radiobutton path="gender" value="F" id="female3" name="radio"/>
                                                        <label for="female3">Female</label>
                                                    </div>
                                                    <div class="error">
                                                        <form:errors path="gender" />
                                                      </div>
                                                </div>
                                            </div><!-- form-group -->
                                            <fmt:formatDate pattern="dd/MM/yyyy" type="date" value="${member.dob}" var="currentDate9"/>
                                            <div class="form-group">
                                             <label class="col-sm-4 control-label tooltips" data-original-title="Member's Date Of Birth" data-toggle="tooltip" data-placement="left">Date Of Birth *</label>
                                          		     <div class="input-group">
			                                            <form:input path="dob" type="text" value="${currentDate9}" class="form-control" placeholder="dd/mm/yyyy" id="datepicker" size="10" />
			                                            <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                                                                     <div class="error">
                                                                        <form:errors path="dob" />
                                                                      </div>
			                                        </div><!-- input-group -->
			                                 </div><!-- form-group -->
			                                 
			                                 <div class="form-group">
                                                <label class="col-sm-4 control-label tooltips" data-original-title="Member's Religion" data-toggle="tooltip" data-placement="left">Religion *</label>
                                                 <div class="col-sm-4">
                                                <form:select path="religion.religionId"  class="width200p" data-placeholder="Choose One">
                                                  <form:option value="0" label="--Please Select Religion--" />
			   										<form:options items="${referenceList.religionList}" itemLabel="religionName" itemValue="religionId" />
												</form:select>
                                                      <div class="error">
                                                                        <form:errors path="religion" />
                                                                      </div>
                                                </div>
                                             </div><!-- form-group -->
                                                
                                                <div class="form-group">
                                                <label class="col-sm-4 control-label tooltips" data-original-title="Member's Phone Number" data-toggle="tooltip" data-placement="left">Phone No *</label>
                                                <div class="col-sm-8">
                                                     <form:input type="text" path="phoneNo1" class="form-control" required="true"/>
                                                     <div class="error">
                                                                        <form:errors path="phoneNo1" />
                                                                      </div>
                                                </div>
                                            </div><!-- form-group -->
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label tooltips" data-original-title="Member's Alternate Phone " data-toggle="tooltip" data-placement="left">Phone No(2)</label>
                                                <div class="col-sm-8">
                                                     <form:input type="text" path="phoneNo2" class="form-control" />
                                                        <div class="error">
                                                                        <form:errors path="phoneNo2" />
                                                                      </div>
                                                </div>
                                            </div><!-- form-group -->
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label tooltips" data-original-title="Member's Alternate Phone" data-toggle="tooltip" data-placement="left">Phone No(3)</label>
                                                <div class="col-sm-8">
                                                     <form:input type="text" path="phoneNo3" class="form-control" />
                                                        <div class="error">
                                                                        <form:errors path="phoneNo3" />
                                                                      </div>
                                                </div>
                                            </div><!-- form-group -->
                                            <div class="form-group">
                                                <label  class="col-sm-4 control-label tooltips" data-original-title="Member's Email" data-toggle="tooltip" data-placement="left">Email Address *</label>
                                                <div class="col-sm-8">
                                                     <form:input type="text" path="emailAdd1" class="form-control" required="true"/>
                                                        <div class="error">
                                                                        <form:errors path="emailAdd1" />
                                                                      </div>
                                                </div>
                                            </div><!-- form-group -->
                                            <div class="form-group">
                                                <label  class="col-sm-4 control-label tooltips" data-original-title="Member's Alternate Email" data-toggle="tooltip" data-placement="left">Email Address(2)</label>
                                                <div class="col-sm-8">
                                                     <form:input type="text" path="emailAdd2" class="form-control" />
                                                       <div class="error">
                                                                        <form:errors path="emailAdd2" />
                                                                      </div>
                                                </div>
                                            </div><!-- form-group -->
                                            <div class="form-group">
                                                <label  class="col-sm-4 control-label tooltips" data-original-title="Member's Alternate Email" data-toggle="tooltip" data-placement="left">Email Address(3)</label>
                                                <div class="col-sm-8">
                                                     <form:input type="text" path="emailAdd3" class="form-control" />
                                                       <div class="error">
                                                                        <form:errors path="emailAdd3" />
                                                                      </div>
                                                </div>
                                            </div><!-- form-group -->
                                      
                                        </div><!-- tab-pane -->
                                        
                                        <div class="tab-pane" id="tab2">
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label tooltips" data-original-title="Member's Cooperative" data-toggle="tooltip" data-placement="left">Company</label>
                                                 <div class="col-sm-4">
                                                     
                                                     <c:forEach var="compItem" items="${referenceList.compList}">
                                                           ${compItem.name}
                                                            <input type="hidden" name="company.id" class="form-control" value ="${compItem.id}" />
                                                       </c:forEach>
                                                     
                                             <%--       <form:select path="company"  class="width100p" data-placeholder="Choose One">
			   										<form:options items="${referenceList.compList}" itemLabel="name" itemValue="Id"  />
												</form:select>
                                                 --%>    
                                                </div>
                                            </div><!-- form-group -->
                                            
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label tooltips" data-original-title="Member's Branch Info" data-toggle="tooltip" data-placement="left">Branch</label>
                                                 <div class="col-sm-4">
                                                     ${member.branch.branchName}
                                                      <input type="hidden" name="branch.id" class="form-control"  value="${member.branch.id}"/>
                                              <%-- <form:select path="branch.branchId"  class="width200p" data-placeholder="Choose One">
                                                    <form:option value="0" label="--Please Select Branch--" />
			   										<form:options items="${referenceList.branchList}" itemLabel="branchName" itemValue="branchId" />
												</form:select>
						--%>						
											<%-- <form:select id="branch" path="branch" class="width100p" data-placeholder="Choose One">
											 <c:forEach items="${referenceList.branchList}" var="item">  
												<form:option value="${item.branchId}">${item.branchName}</form:option>
											 </c:forEach>
										      </form:select> --%>
                                                     
                                                </div>
                                            </div><!-- form-group -->
                                            
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label tooltips" data-original-title="Member's Cooperative Identication Number" data-toggle="tooltip" data-placement="left">Cooperative number No.</label>
                                                <div class="col-sm-8">
                                                     <form:input path="memberCompId" type="text" class="form-control" required="false" />
                                                       <div class="error">
                                                                        <form:errors path="memberCompId" />
                                                       </div>
                                                </div>
                                            </div><!-- form-group -->
                                            
                                             <div class="form-group">
                                                <label class="col-sm-4 control-label tooltips" data-original-title="Member's Membership Type" data-toggle="tooltip" data-placement="left">Member Type *</label>
                                                 <div class="col-sm-4">
                                                <form:select path="memberType.memberTypeId"  class="width200p" data-placeholder="Choose One">
                                                    <form:option value="0" label="--Please Select Member Type--" />
			   										<form:options items="${referenceList.memberTypeList}" itemLabel="memberTypeVal" itemValue="memberTypeId" />
												</form:select>
                                                      <div class="error">
                                                                        <form:errors path="memberType" />
                                                                      </div>
                                                </div>
                                            </div><!-- form-group -->
                                        </div><!-- tab-pane -->
                                        
                                        <div class="tab-pane" id="tab3">
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label tooltips" data-original-title="Member's Identication Document" data-toggle="tooltip" data-placement="left">Identification Document *</label>
                                                 <div class="col-sm-4">
                                                <form:select path="identificationDoc.identificationDocId"  class="width200p" data-placeholder="Choose One">
                                                    <form:option value="" label="--Please Select Identification Doc Type--" />
			   										<form:options items="${referenceList.identityDocList}" itemLabel="identificationDocName" itemValue="identificationDocId" />
												</form:select>
                                                      <div class="error">
                                                                        <form:errors path="identificationDoc" />
                                                                      </div>
                                                </div>
                                            </div><!-- form-group -->
                                            
                                             <div class="form-group">
                                                <label class="col-sm-4 control-label tooltips" data-original-title="Member's Identication number on identification document" data-toggle="tooltip" data-placement="left">Identification No.*</label>
                                                <div class="col-sm-8">
                                                     <form:input path="identificationCode" type="text" class="form-control" required="true" />
                                                 <div class="error">
                                                                        <form:errors path="identificationCode" />
                                                                      </div>
                                                </div>
                                            </div><!-- form-group -->
                                            
                                                                                        
                                         <div class="form-group " >
                                            <label class="col-sm-4 control-label tooltips" data-original-title="Member's Next of Kin First Name" data-toggle="tooltip" data-placement="left">Next of Kin Firstname * </label>
                                            <div class="col-sm-8">
                                                 <form:input type="text" path="nokName" class="form-control" required="true"/>
                                                 <div class="error">
                                                    <form:errors path="nokName" />
                                                        <form:errors path="*" />
                                                  </div>
                                            </div>
                                          </div><!-- form-group -->

                                         <div class="form-group">
                                            <label class="col-sm-4 control-label tooltips" data-original-title="Member's Next of Kin Middlename" data-toggle="tooltip" data-placement="left">Next of Kin Middlename</label>
                                            <div class="col-sm-8">
                                                 <form:input type="text" path="nokMiddleName" class="form-control" />
                                                  <div class="error">
                                                    <form:errors path="nokMiddleName" />
                                                  </div>
                                            </div>
                                         </div><!-- form-group -->

                                         <div class="form-group">
                                            <label class="col-sm-4 control-label tooltips" data-original-title="Member's Next of Kin Lastname" data-toggle="tooltip" data-placement="left">Next of Kin Lastname *</label>
                                            <div class="col-sm-8">
                                                 <form:input type="text" path="nokSurname" class="form-control" required="true"/>
                                                 <div class="error">
                                                    <form:errors path="nokSurname" />
                                                  </div>
                                            </div>
                                          </div><!-- form-group -->

                                          <div class="form-group">
                                            <label class="col-sm-4 control-label tooltips" data-original-title="Member's Next of Kin Phone" data-toggle="tooltip" data-placement="left">Next of Kin Phone *</label>
                                            <div class="col-sm-8">
                                                 <form:input type="text" path="nokPhone" class="form-control" required="true"/>
                                                 <div class="error">
                                                    <form:errors path="nokPhone" />
                                                  </div>
                                            </div>
                                          </div><!-- form-group -->
                                            
                                            
                                        </div><!-- tab-pane -->
                                        
                                         <div class="tab-pane" id="tab4">
                                         <div class="error">
                                                                        <form:errors path="addressStr" />
                                                                      </div>
                                         <c:forEach var="addrtype" items="${referenceList.addressType}">
                                         
    										<h5 class="lg-title">${addrtype.value}</h5> 
    										
    										 <c:set var="varList" value="${member.addressEntriesList[addrtype.value]}"/>
    										
    										<c:forEach var="addrList" items="${referenceList.addressList}">
    										
	    										<div class="form-group">
	                                                <label class="col-sm-4 control-label tooltips" data-original-title="${addrtype.value} ${addrList[2]}" data-toggle="tooltip" data-placement="left">${addrList[2]} *</label>
	                                                <div class="col-sm-8">
	                                                     <c:set var="query" value="${addrtype.value}:::${addrList[2]}:::${addrList[1]}"/>
	                                                         	     
	                                                         	   <c:forEach var="addrExist" items="${varList}">
	                                                         	   
	                                                         	   		<c:if test="${addrExist.addrFieldName eq addrList[2]}"><c:set var="addrVal" value="${addrExist.addrFieldValue}"/></c:if>
	                                                         	   
	                                                         	    </c:forEach>
	                                                         	                                                     
															<input name="${query}" type="text" value="${addrVal}" class="form-control" />
															
	                                                </div>
	                                            </div><!-- form-group -->
                                            
    										</c:forEach>
    										
										</c:forEach>
                                           
                                        </div><!-- tab-pane -->
                                        
                                         <div class="tab-pane" id="tab5">
                                             
                                           <c:if test="${member.action=='ADD'}">
                                             <div class="error">
                                                                        <form:errors path="stockStr" />
                                                                      </div>
                                            <c:forEach var="stckList" items="${referenceList.stockList}">
    										
	    										<div class="form-group">
	                                                <label class="col-sm-4 control-label tooltips" data-original-title="Member's Assigned Stock Level" data-toggle="tooltip" data-placement="left">${stckList[2]} *</label>
	                                                <div class="col-sm-8">
	                                                     <c:set var="query" value="${stckList[2]}"/>
	                                                         	     
	                                                         	   <c:forEach var="OKExist" items="${stckExist}">
	                                                         	   
	                                                         	   		<c:if test="${OKExist.name eq stckList[2]}"><c:set var="OKVal" value="${OKExist.val}"/></c:if>
	                                                         	   
	                                                         	    </c:forEach>
	                                                         	                                                     
										<input name="${query}" type="text" value="${OKVal}" class="form-control" />
															
	                                                </div>
	                                            </div><!-- form-group -->
                                            
    										</c:forEach>
                                            </c:if>
                                                    
                                                    <div class="form-group">
                                                <label class="col-sm-4 control-label tooltips" data-original-title="Member's Bank Name" data-toggle="tooltip" data-placement="left">Member Bank *</label>
                                                 <div class="col-sm-4">
                                                <form:select path="banks.bankId"  class="width200p" data-placeholder="Choose One">
                                                    <form:option value="" label="--Please Select Bank--" />
                                                    <c:forEach var="bnkList" items="${referenceList.banksList}">
			   											<form:option label="${bnkList[1]}" value="${bnkList[0]}" />
													</c:forEach>
												</form:select>
                                                            <div class="error">
                                                                        <form:errors path="banks" />
                                                                      </div>
                                                </div>
                                            </div><!-- form-group -->
                                            
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label tooltips" data-original-title="Member's Bank Account Number" data-toggle="tooltip" data-placement="left">Bank Account*</label>
                                                 <div class="col-sm-8">
                                                     <form:input path="bankAccount" type="text" class="form-control" required="true"/>
                                                      <div class="error">
                                                                        <form:errors path="bankAccount" />
                                                      </div>
                                                </div>
                                            </div><!-- form-group -->
                                                    
                                       <c:forEach var="contrib" items="${referenceList.contrList}">
    					<div class="form-group">
                                                <label class="col-sm-4 control-label tooltips" data-original-title="Contribution Product" data-toggle="tooltip" data-placement="left">Contribution: ${contrib['code']} *</label>
                                                 <div class="col-sm-4">
                                                     <select  class="width200p" data-placeholder="Choose One" name="${contrib['code']}">
                                                         <option value="${contrib['code']}" >${contrib['code']}</option>
                                                    </select>

                                                </div>
                                            </div><!-- form-group -->
                                            
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label tooltips" data-original-title="Member's Contribution Amount" data-toggle="tooltip" data-placement="left">Member Contribution *</label>
                                                 <div class="col-sm-4">
                                                                                              
			   				<input name="${contrib['code']}_val" type="text" value="" class="form-control" />
                                                </div>
                                            </div><!-- form-group -->
                                         </c:forEach>
                                            
                                            
                                        </div><!-- tab-pane -->
                                        
                                        <div class="tab-pane" id="tab6">
                                         <div class="error">
                                                                <form:errors path="extrafldStr" />     
                                                                      </div>
                                        
                                         
    										<h5 class="lg-title">Additional Fields</h5> 
    										 <c:set var="varList2" value="${member.membersExtrafldEntriesList}"/>
    										<c:forEach var="extraflList" items="${referenceList.extrafldList}">
    										
	    										<div class="form-group">
	                                                <label class="col-sm-4 control-label tooltips" data-original-title="${extraflList.description}" data-toggle="tooltip" data-placement="left">${extraflList.description} </label>
	                                                <div class="col-sm-8">
	                                                     <c:set var="query2" value="${extraflList.description}:::${extraflList.id}"/>
	                                                         	     
	                                                         	   <c:forEach var="extraflListExist" items="${varList2}">
	                                                         	   
	                                                         	   		<c:if test="${extraflListExist.extraFieldId eq extraflList.id}">
                                                                                            <c:set var="extraflListVal" value="${extraflListExist.extraFieldValue}"/>
                                                                                            <c:set var="extrafloptListVal" value="${extraflListExist.extraFieldoptionValue}"/>
                                                                                        </c:if>
	                                                         	   
	                                                         	    </c:forEach>
	                                                         	                          <c:choose>
                                                                                                      <c:when test="${extraflList.grouped eq 'Y'}">
                                                                                                        <select name="${query2}" id="${query2}" class="width200p" >    
                                                                                                            <option value="">--Please Select--</option>    
                                                                                                         <c:forEach var="extrafldoptList" items="${referenceList.extrafldoptionList}">
                                                                                                             <c:if test="${extrafldoptList[2] == extraflList.id}">
                                                                                                              <option <c:if test="${extrafloptListVal eq extrafldoptList[0]}">selected="selected"</c:if>    value="${extrafldoptList[0]}">${extrafldoptList[1]}</option>
                                                                                                             </c:if>
													</c:forEach>
												
                                                                                                        </select> 
                                                                                                          
                                                                                                      </c:when>
                                                                                                      <c:otherwise>
                                                                                                          <input name="${query2}" type="text" value="${extraflListVal}" class="form-control" />
                                                                                                      </c:otherwise>
                                                                                                  </c:choose>                           
															
															
	                                                </div>
	                                            </div><!-- form-group -->
                                            
    										</c:forEach>
    										
										
                                           
                                        </div><!-- tab-pane -->
                                        
                                    </div><!-- tab-content -->
                
                                    <ul class="list-unstyled wizard">
                                    
                                         <input type="hidden" name="action" value="${member.action}" />
                                         <input type="hidden" name="createdBy" value="${member.createdBy}" />
                                          <input type="hidden" name="state" value="${member.status.statusShort}" />
                                         
                                        <li class="pull-left previous"><button type="button" class="btn btn-default">Previous</button></li>
                                        <li class="pull-right next"><button type="button" class="btn btn-danger">Next</button></li>
                                        <li class="pull-right finish hide"><button type="submit" class="btn btn-danger">Finish</button></li>
                                    </ul>
                                    
                                </form:form><!-- panel-wizard -->
              
                            </div><!-- col-md-6 -->
    </div><!-- row -->
</div><!-- contentpanel -->

<%@ include file="../includes/footer.jsp" %>  

 <script>
            jQuery(document).ready(function() {
                
                // This will empty first option in select to enable placeholder
                //jQuery('select option:first-child').text('');
                
                // Select2
                jQuery("select").select2({
                    minimumResultsForSearch: -1
                });
                
                jQuery('#datepicker').datepicker();
                $.datepicker.setDefaults({
                   dateFormat: 'dd/mm/yy'
                });
                
                 // Basic Wizard
                jQuery('#basicWizard').bootstrapWizard({
                    onTabShow: function(tab, navigation, index) {
                        tab.prevAll().addClass('done');
                        tab.nextAll().removeClass('done');
                        tab.removeClass('done');
                        
                        var $total = navigation.find('li').length;
                        var $current = index + 1;
                        //var $stoppoint = $("#stoppoint").val();
                        if($current >= $total) {
                            $('#basicWizard').find('.wizard .next').addClass('hide');
                            $('#basicWizard').find('.wizard .finish').removeClass('hide');
                            
                        } else {
                            $('#basicWizard').find('.wizard .next').removeClass('hide');
                            $('#basicWizard').find('.wizard .finish').addClass('hide');
                          /*  var $stoppoint = $('#stoppoint').val();
                            alert($current + "<=" +$stoppoint );
                            if($current <= $stoppoint) {
                                $('#basicWizard').find('.wizard .previous').addClass('hide');
                            }
                            else
                            {
                                $('#basicWizard').find('.wizard .previous').removeClass('hide');
                            }*/    
                        }
                    }
                });
                
                // Progress Wizard
                jQuery('#progressWizard').bootstrapWizard({
                    onTabShow: function(tab, navigation, index) {
                        tab.prevAll().addClass('done');
                        tab.nextAll().removeClass('done');
                        tab.removeClass('done');
                        
                        var $total = navigation.find('li').length;
                        var $current = index + 1;
                        
                        if($current >= $total) {
                            $('#progressWizard').find('.wizard .next').addClass('hide');
                            $('#progressWizard').find('.wizard .finish').removeClass('hide');
                        } else {
                            $('#progressWizard').find('.wizard .next').removeClass('hide');
                            $('#progressWizard').find('.wizard .finish').addClass('hide');
                        }
                        
                        var $percent = ($current/$total) * 100;
                        $('#progressWizard').find('.progress-bar').css('width', $percent+'%');
                    }
                });
                
                // Wizard With Disabled Tab Click
                jQuery('#tabWizard').bootstrapWizard({
                    onTabShow: function(tab, navigation, index) {
                        tab.prevAll().addClass('done');
                        tab.nextAll().removeClass('done');
                        tab.removeClass('done');
                        
                        var $total = navigation.find('li').length;
                        var $current = index + 1;
                        
                        if($current >= $total) {
                            $('#tabWizard').find('.wizard .next').addClass('hide');
                            $('#tabWizard').find('.wizard .finish').removeClass('hide');
                        } else {
                            $('#tabWizard').find('.wizard .next').removeClass('hide');
                            $('#tabWizard').find('.wizard .finish').addClass('hide');
                        }
                    },
                    onTabClick: function(tab, navigation, index) {
                        return false;
                    }
                });
                
                // Wizard With Form Validation
                jQuery('#valWizard').bootstrapWizard({
                    onTabShow: function(tab, navigation, index) {
                        tab.prevAll().addClass('done');
                        tab.nextAll().removeClass('done');
                        tab.removeClass('done');
                        
                        var $total = navigation.find('li').length;
                        var $current = index + 1;
                        
                        if($current >= $total) {
                            $('#valWizard').find('.wizard .next').addClass('hide');
                            $('#valWizard').find('.wizard .finish').removeClass('hide');
                        } else {
                            $('#valWizard').find('.wizard .next').removeClass('hide');
                            $('#valWizard').find('.wizard .finish').addClass('hide');
                        }
                    },
                    onTabClick: function(tab, navigation, index) {
                        return false;
                    },
                    onNext: function(tab, navigation, index) {
                        var $valid = jQuery('#valWizard').valid();
                        if (!$valid) {
                            $validator.focusInvalid();
                            return false;
                        }
                    }
                });
                
                // Wizard With Form Validation
                var $validator = jQuery("#valWizard").validate({
                    highlight: function(element) {
                        jQuery(element).closest('.form-group').removeClass('has-success').addClass('has-error');
                    },
                    success: function(element) {
                        jQuery(element).closest('.form-group').removeClass('has-error');
                    }
                });
                
              
                // This will submit the basicWizard form
                jQuery('.panel-wizard').submit(function() {    
                    var retVal = confirm('This will submit the form wizard. Click OK to continue');
                    if( retVal == true )
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                    });

            });
        </script>
        
 <script type="text/javascript">
 
function ShowBox(idops, idtarget)
{
        $("#" + idops).val();
}
</script>




  
