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
	       
		       
		     <form:form method="post" action="../discp/apprvMemberDiscipline.htm" commandName="member" class="form-horizontal form-bordered">
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
                        <cif test = "${member.gender == 'M'}">
                         
                            MALE                       
                        </cif>
                         <cif test = "${member.gender == 'F'}">
                         
                            FEMALE                       
                        </cif>
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
			                          
			   <div class="form-group">
			   <input type="hidden" name="memberId" value="${member.memberId}" />
                
                           <button class="btn btn-danger mr5" type="submit" name="action" value="REINSTATE" > REINSTATE MEMBER </button> 
		           <!--<button class="btn btn-danger mr5" type="submit" name="action" value="REJECT SUSPENSION" >REJECT</button>-->
				    
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
    })
</script>