<%@ include file="includes/header.jsp" %>  
   <style>
        .autocomplete-suggestions { border: 1px solid #999; background: #FFF; overflow: auto; }
        .autocomplete-suggestion { padding: 5px 5px; white-space: nowrap; overflow: hidden; font-size:18px}
        .autocomplete-selected { background: #F0F0F0; }
        .autocomplete-suggestions strong { font-weight: bold; color: #D9534F; }
    </style>
<div class="media-body">
    <!-- changes starts -->
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="#">Home</a></li>
            <li>AGM Management</li>
        </ul>
        <h4>Manage AGM</h4>
    </div>
    <%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->

<div class="contentpanel">
    <div class="row">
        <div class="col-md-120">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
                <!-- Nav tabs -->
                <ul class="nav nav-tabs nav-danger">
                    <li class="active"><a href="#home6" data-toggle="tab"><strong>Setup AGM</strong></a></li>
                    <li><a href="#profile6" data-toggle="tab"><strong>Manage AGM</strong></a></li>

                </ul>

                <!-- Tab panes -->
                <div class="tab-content tab-content-danger mb30">
                    <div class="tab-pane active" id="home6">

                        <p>   <form:form method="POST" commandName="agmForm" action="${pageContext.request.
                                                                                       contextPath}/views/agm/edit" id="agmForm">
                                   <div class="panel panel-default">
                                       <div class="panel-heading">
                                           <div class="panel-btns">
                                               <a href="#" class="panel-minimize tooltips" data-toggle="tooltip" title="Minimize Panel"><i class="fa fa-minus"></i></a>
                                               <a href="#" class="panel-close tooltips" data-toggle="tooltip" title="Close Panel"><i class="fa fa-times"></i></a>
                                           </div><!-- panel-btns -->
                                           <h4 class="panel-title">Agm Setup Form</h4>
                                           <p>Kindly fill the form below to create a AGM.</p>

                                       </div>
                                       <div class="panel-body">

                                           <div class="errorForm"></div>
 <div class="form-group">
                                               <label class="col-sm-4 control-label">Company<span class="asterisk">*</span></label>
                                               <div class="col-sm-4">

                                                   <input name="companyName" class="form-control"  id="companyName" required="required" />
                                               </div>
                                           </div>
                                           <div class="form-group">
                                               <label class="col-sm-4 control-label">Start Time<span class="asterisk">*</span></label>
                                               <div class="col-sm-4">
                                                   <div class="input-group mb15">
                                                       <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                                                       <div class="bootstrap-timepicker"><form:input path="stringStartTime" cssClass="form-control"  id="stringStartTime" required="required" /></div>
                                                   </div>

                                               </div>
                                           </div>
                                           <div class="form-group">
                                               <label class="col-sm-4 control-label">End Time<span class="asterisk">*</span></label>
                                               <div class="col-sm-4">
                                                   <div class="input-group mb15">
                                                       <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                                                       <div class="bootstrap-timepicker"><form:input path="stringEndTime" cssClass="form-control"  id="stringEndTime" required="required" /></div>
                                                   </div>

                                               </div>
                                           </div>
                                           <div class="form-group">
                                               <label class="col-sm-4 control-label">Start Date<span class="asterisk">*</span></label>
                                               <div class="col-sm-4">

                                                   <form:input path="stringStartDate" cssClass="form-control"  id="stringStartDate" required="required" />
                                               </div>
                                           </div>
                                           <div class="form-group">
                                               <label class="col-sm-4 control-label">Allow Proxy?<span class="asterisk">*</span></label>
                                               <div class="col-sm-8">
                                                   <div class="ckbox ckbox-danger">
                                                       <div class="rdio rdio-danger">

                                                           <form:radiobutton id="yesProxy" value="${agm.allowProxy}" path="allowProxy"  required="required" />
                                                           <label for="yesProxy">Yes</label>
                                                       </div><!-- rdio -->
                                                       <div class="rdio rdio-danger">
                                                           <form:radiobutton id="noProxy" value="${agm.allowProxy}"  path="allowProxy"  required="required" />
                                                           <label for="noProxy">No</label>
                                                       </div><!-- rdio -->

                                                   </div>
                                               </div>
                                           </div><!-- form-group -->
                                           <div class="form-group">
                                               <label class="col-sm-4 control-label">Note/Description</label>
                                               <div class="col-sm-2">
                                                   <form:textarea path="note" cssClass="form-control" cols="150" rows="5" id="note" />
                                               </div>
                                               <div>
                                                   <form:hidden value="" id="companyId" path="companyId"  />
                                               </div>
                                           </div><!-- form-group -->  


                                       </div><!-- panel-body -->
                                       <div class="panel-footer">
                                           <button class="btn btn-danger mr5">Submit</button>
                                           <button type="reset" class="btn btn-default">Reset</button>
                                       </div><!-- panel-footer -->
                                   </div><!-- panel-default -->
                        </form:form></p>
                    </div><!-- tab-pane -->

                    <div class="tab-pane" id="profile6">

                        <div class="row">
                            <div class="col-md-6">
                                <!-- Manage agm here-->
                            </div>

                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <table id="agm-list" class="table table-striped table-bordered responsive">
                                    <thead class="">
                                        <tr>

                                            <th>Id</th>
                                            <th>Date</th>
                                            <th>Start Time</th>
                                            <th>End Time</th>
                                            <th>Note </th>
                                            <th></th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <c:forEach  var="agm" items="${agms}" varStatus="status" >
                                            <tr>
                                                <td>${status.index + 1}</td>
                                                <td>${agm.startDate}</td>
                                                <td>${agm.startTime}</td>
                                                <td>${agm.endTime}</td>
                                                <td>${agm.note}</td>
                                                <td><a href="#" class="add-attendees" id="attend-${agm.id}" data-company="${agm.companyId}" data-id="${agm.id}">Add Participant(s)</a> | <a href="<%=request.getContextPath()%>/views/votes/setup/${agm.id}" lass="add-attendees"  data-id="${agm.id}">Setup Vote(s)</a> | <a href="#" class="view-attendees" id="attend-${agm.id}" data-id="${agm.id}">View Participants</a></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div><!-- tab-pane -->


                </div><!-- tab-content -->
                
                    <div class="modal fade bs-example-modal-lg" tabindex="-1" id="attendeesModal" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">&times;</button>
                                <h4 class="modal-title">Add Participants</h4>
                            </div>
                            <div class="modal-body">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <div class="panel-btns">
                                            <a href="#" class="panel-minimize tooltips" data-toggle="tooltip" title="Minimize Panel"><i class="fa fa-minus"></i></a>

                                        </div><!-- panel-btns -->
                                        <h4 class="panel-title">Search and add Members</h4>
                                        <p>Select the member(s) you wish to add and click the add to list action button</p>
                                    </div><!-- panel-heading -->
                                    <div class="panel-body">
                                        <div id="submitResponse" class="alert alert-success" style="display:none">
                                            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                            <p id="success-message"></p>
                                        </div>
                                        <form id="searchForm" name="searchForm" action="${pageContext.request.
                                                                                          contextPath}/views/agm/search">
                                            <div class="form-group">
                                                <label class="sr-only" for="searchName"> </label>
                                                <div class="col-sm-4">
                                                    <input type="text" name="searchName" class="form-control" id="searchName" placeholder="Member Name">

                                                </div>
                                                <div class="ckbox ckbox-danger mr20 inline-block">
                                                    <input type="checkbox" checked="checked"  name="radName" id="radName" value="0">
                                                    <label for="searchName">Name</label>
                                                </div>
                                            </div><!-- form-group -->
                                            <div class="form-group">
                                                <label class="sr-only" for="searcMemberNo"> </label>
                                                <div class="col-sm-4">
                                                    <input type="text" name="searcMemberNo" class="form-control" id="searcMemberNo" placeholder="Member Number">

                                                </div>
                                                <div class="ckbox ckbox-danger mr20 inline-block">
                                                    <input type="checkbox"   name="radMemberNo" id="radMemberNo" value="0">
                                                    <label for="radMemberNo">Member Number</label>
                                                </div>
                                            </div><!-- form-group -->
                                            <div class="form-group">
                                                <label class="sr-only" for="searchEmail"> </label>
                                                <div class="col-sm-4">
                                                    <input type="email" name="searchEmail" class="form-control" id="searchEmail" placeholder="Email">

                                                </div>
                                                <div class="ckbox ckbox-danger mr20 inline-block">
                                                    <input type="checkbox"   name="radEmail" id="radEmail" value="0">
                                                    <label for="radEmail">Email</label>
                                                </div>
                                            </div><!-- form-group -->
                                            <!-- form-group -->
                                            <div class="form-group">
                                                <label class="sr-only" for="searchPhone"> </label>
                                                <div class="col-sm-4">
                                                    <input type="text" name="searchPhone" class="form-control" id="searchPhone" placeholder="Phone">

                                                </div>
                                                <div class="ckbox ckbox-danger mr20 inline-block">
                                                    <input type="checkbox"   name="radPhone" id="radPhone" value="0">
                                                    <label for="radPhone">Phone Number</label>
                                                </div>
                                            </div><!-- form-group -->
                                            <button type="submit" class="btn btn-danger mr5">Search</button>
                                            <input type="hidden" id="agmId" name="agmId" />
                                        </form>
                                        <div style="margin-top:20px">
                                            <table id="search-list" style="display:none" class="table table-striped table-bordered responsive">
                                                <thead class="">
                                                    <tr>
                                                        <th style="width:8px;"><input type="checkbox" class="group-checkable" data-set="#search-list .checkboxes" /></th>    
                                                        <th>First Name</th>
                                                        <th>Middle Name</th>
                                                        <th>Last Name</th>
                                                        <th>Member No</th>
                                                        <th></th>
                                                    </tr>
                                                </thead>
                                                <tbody>

                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">

                                </div>
                            </div>
                        </div>
                    </div> 
					</div>
					<!--Modal ends -->
                    <div class="modal fade bs-example-modal-lg" tabindex="-1" id="proxyModal" role="dialog">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button aria-hidden="true" data-dismiss="modal" class="close" type="button">&times;</button>
                                    <h4 class="modal-title">Add Proxy</h4>
                                </div>
                                <div class="modal-body">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <div class="panel-btns">
                                                <a href="#" class="panel-minimize tooltips" data-toggle="tooltip" title="Minimize Panel"><i class="fa fa-minus"></i></a>

                                            </div><!-- panel-btns -->
                                            <h4 class="panel-title"></h4>
                                            <p>Add Proxy </p>
                                        </div><!-- panel-heading -->
                                         <form id="proxyForm" name="proxyForm" action="${pageContext.request.
                                                                                          contextPath}/views/agm/addproxy">
                                        <div class="panel-body">
                                           
                                            <div id="proxyResponse" class="alert alert-success" style="display:none">

                                                <p id="proxysuccess-message"></p>
                                            </div>
                                             <div class="form-group">
                                               <label class="col-sm-4 control-label">First Name<span class="asterisk">*</span></label>
                                               <div class="col-sm-4">

                                                   <input name="proxyFirstName" class="form-control"  id="proxyFirstName" required="required" />
                                               </div>
                                           </div>
                                              <div class="form-group">
                                               <label class="col-sm-4 control-label">Middle Name</label>
                                               <div class="col-sm-4">

                                                   <input name="proxyMiddleName" class="form-control"  id="proxyMiddleName"  />
                                               </div>
                                           </div>
                                              <div class="form-group">
                                               <label class="col-sm-4 control-label">Last Name<span class="asterisk">*</span></label>
                                               <div class="col-sm-4">

                                                   <input name="proxyLastName" class="form-control"  id="proxyLastName" required="required"  />
                                               </div>
                                           </div><!-- form-group-->
                                           <div class="form-group">
                                               <label class="col-sm-4 control-label">Gender<span class="asterisk">*</span></label>
                                               <div class="col-sm-4">
                                                   <select name="proxyGender" id="proxyGender">
                                                       <option value="">Select...</option> 
                                                       <option value="M">Male</option> 
                                                       <option value="F">Female</option> 
                                                   </select>
                                                   
                                               </div>
                                           </div><!-- form-group-->
                                           <div class="form-group">
                                               <label class="col-sm-4 control-label">Email<span class="asterisk">*</span></label>
                                               <div class="col-sm-4">

                                                   <input name="proxyEmail" class="form-control"  id="proxyEmail" required="required"  />
                                               </div>
                                           </div><!-- form-group-->
                                           <div class="form-group">
                                               <label class="col-sm-4 control-label">Phone Number<span class="asterisk">*</span></label>
                                               <div class="col-sm-4">
                                                   
                                                   <input name="proxyMemberId" class="form-control"  id="proxyMemberId" type="hidden" />
                                                   <input name="proxyAgmId" class="form-control"  id="proxyAgmId" type="hidden" />
                                                   <input name="proxyCompanyId" class="form-control"  id="proxyCompanyId" type="hidden" />
                                                   <input name="proxyPhoneNumber" class="form-control"  id="proxyPhoneNumber" required="required"  />
                                               </div>
                                           </div><!-- form-group-->
                                           <div class="form-group">
                                               <label class="col-sm-4 control-label">Address<span class="asterisk">*</span></label>
                                               <div class="col-sm-4">

                                                   <textarea name="proxyAddress" class="form-control"  cols="5"  id="proxyAddress" required="required"></textarea>
                                               </div>
                                           </div><!-- form-group-->
                                        </div>
                                         <div class="panel-footer">
                                           <button class="btn btn-danger mr5">Submit</button>
                                           <button type="reset" class="btn btn-default">Reset</button>
                                       </div><!-- panel-footer -->
                                         </form>
                                    </div>
                                    <div class="modal-footer">

                                    </div>
                                </div>
                            </div>
                        </div> 
						</div>
						<!--Modal ends -->
                
                <div class="modal fade bs-example-modal-sm" tabindex="-1" id="statusMoal" role="dialog">
                    <div class="modal-dialog modal-sm">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">&times;</button>
                                <h4 class="modal-title">Status Update</h4>
                            </div>
                            <div class="modal-body">
                                <p id="status-text"></p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Ok</button>


                            
                            </div>
                        </div>
                    </div>
                </div> <!--Modal ends -->

            
                        <div class="modal fade bs-example-modal-lg" tabindex="-1" id="viewParticipantsModal" role="dialog">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button aria-hidden="true" data-dismiss="modal" class="close" type="button">&times;</button>
                                        <h4 class="modal-title">View Participants</h4>
                                    </div>
                                    <div class="modal-body">
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <div class="panel-btns">
                                                    <a href="#" class="panel-minimize tooltips" data-toggle="tooltip" title="Minimize Panel"><i class="fa fa-minus"></i></a>

                                                </div><!-- panel-btns -->
                                                <h4 class="panel-title">List of Participants</h4>
                                                <p>Select the member(s) you wish to add and click the add to list action button</p>
                                            </div><!-- panel-heading -->
                                            <div class="panel-body">

                                                <table id="participant-list" class="table table-striped table-bordered responsive">
                                                    <thead class="">
                                                        <tr>
                                                         
                                                            <th>Id</th>
                                                            <th>Full Name</th>
                                                            <th></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <td></td>
                                                            <td></td>
                                                            <td></td>


                                                        </tr>
                                                    </tbody>
                                                </table>

                                            </div>
                                        </div>
                                        <div class="modal-footer">

                                        </div>
                                    </div>
                                </div>
                            </div> <!--Modal ends -->
                        </div>
                
                <!-- col-md-6 -->
                    </div>

                </div>
            </div><!-- contentpanel -->

            <%@ include file="includes/footer.jsp" %>  
            <script>
                function addToList(id) {
                    var agmId = $('#agmId').val();
                    $.get("<%=request.getContextPath()%>/views/agm/addAttendees", {"ids": id, "agmId": agmId}, function(result) {

                        $("#success-message").html("You have successfully added the selected members to the list");
                        $('#submitResponse').css("display", "block");

                        $("#searchForm").scrollTop();
                    });
                }
                function addProxy(memberId) {
                    
                    
                    $("#proxyMemberId").val(memberId);
                    $("#proxyModal").modal('show');

                }
                $(document).ready(function() {
                    jQuery('#stringStartDate').datepicker();
                    jQuery('#stringStartTime').timepicker({showMeridian: false});
                    jQuery('#stringEndTime').timepicker({showMeridian: false});

                    $('#proxyModal').on('show.bs.modal', function() {
                        $('#attendeesModal').modal('hide');
                    });
                    $.datepicker.setDefaults({dateFormat: 'dd-mm-yy'});
                    jQuery('select').select2({
                        minimumResultsForSearch: -1
                    });
                    jQuery('#agm-list').DataTable({
                        responsive: true
                    });
                    $('#submitResponse').css("display", "none");
                    /**jQuery('#member-list').DataTable({
                     responsive: true
                     });**/

                      $('#companyName').autocomplete({
            serviceUrl: '${pageContext.request.contextPath}/views/agm/getcompanies',
            paramName: "name",
            delimiter: ",",
            transformResult: function(response) {

                return {
                    suggestions: $.map($.parseJSON(response), function(item) {

                        return {value: item.name, data: item.id};
                    })

                };

            },
            onSelect: function(suggestions) {
                $("#companyId").val(suggestions.data);
                alert('You selected: ' + suggestions.value + " id:" + suggestions.data);
            },
        });

                    $('#deleteMember').click(function() {
                        var ids = "";

                        $('#participant-list .checkboxes').each(function() {
                            var chkValue = $(this).val();
                            var memberId = $(this).data("memberid");
                            if ($(this).attr('checked') === 'checked')
                            {

                                if (ids === "") {
                                    ids += memberId;
                                } else {
                                    ids += ',' + memberId;
                                }

                            }

                        });
                        var agmId = $('#agmId').val();
                        $.get("<%=request.getContextPath()%>/views/agm/deleteAttendees", {"ids": ids, "agmId": agmId}, function(result) {

                            $("#success-message").html("You have successfully removed the selected members from the list the list of participants");
                            $('#submitResponse').css("display", "block");
                            $('#submitResponse').focus();

                        });


                    });
                    $('#addToList').click(function() {
                        var ids = "";

                        $('#member-list .checkboxes').each(function() {
                            var chkValue = $(this).val();
                            var memberId = $(this).data("memberid");
                            if ($(this).attr('checked') === 'checked')
                            {

                                if (ids === "") {
                                    ids += memberId;
                                } else {
                                    ids += ',' + memberId;
                                }

                            }

                        });
                        var agmId = $('#agmId').val();
                        $.get("<%=request.getContextPath()%>/views/agm/addAttendees", {"ids": ids, "agmId": agmId}, function(result) {

                            $("#success-message").html("You have successfully added the selected members to the list");
                            $('#submitResponse').css("display", "block");
                            $('#submitResponse').focus();

                        });


                    });

                    jQuery('#participant-list .group-checkable').change(function() {
                        var set = jQuery(this).attr("data-set");
                        var checked = jQuery(this).is(":checked");
                        jQuery(set).each(function() {
                            if (checked) {
                                $(this).attr("checked", true);
                            } else {
                                $(this).attr("checked", false);
                            }
                        });

                    });
                    jQuery('#member-list .group-checkable').change(function() {
                        var set = jQuery(this).attr("data-set");
                        var checked = jQuery(this).is(":checked");
                        jQuery(set).each(function() {
                            if (checked) {
                                $(this).attr("checked", true);
                            } else {
                                $(this).attr("checked", false);
                            }
                        });

                    });
                    jQuery('#search-list .group-checkable').change(function() {
                        var set = jQuery(this).attr("data-set");
                        var checked = jQuery(this).is(":checked");
                        jQuery(set).each(function() {
                            if (checked) {
                                $(this).attr("checked", true);
                            } else {
                                $(this).attr("checked", false);
                            }
                        });

                    });
                    jQuery('#productAddStatus').css('display', 'none');
                    jQuery("#proxyForm").validate({
                        highlight: function(element) {
                            jQuery(element).closest('.form-group').removeClass('has-success').addClass('has-error');
                        },
                        success: function(element) {
                            jQuery(element).closest('.form-group').removeClass('has-error');
                        }
                    });
                    jQuery("#agmForm").validate({
                        highlight: function(element) {
                            jQuery(element).closest('.form-group').removeClass('has-success').addClass('has-error');
                        },
                        success: function(element) {
                            jQuery(element).closest('.form-group').removeClass('has-error');
                        }
                    });
                    jQuery(".view-attendees").click(function() {
                       

                        $.get("<%=request.getContextPath()%>/views/agm/viewmembers", {"id": $(this).data("id")}, function(data) {
                            var html = "";

                            $("#participant-list tbody").html("");
                            $("#participant-list tbody").append(data);


                        });

 jQuery('#viewParticipantsModal').modal('show');

                    });
                    $('.add-attendees').click(function() {
                        $('#attendeesModal').modal('show');
                        var agmId = $(this).data('id');
                        var companyId = $(this).data('company');
                        $('#agmId').val(agmId);
                        $('#proxyAgmId').val(agmId);
                        $('#proxyCompanyId').val(companyId);

                        $.getJSON("<%=request.getContextPath()%>/views/agm/getmembers", function(data) {
                            var html = "";

                            if ($.isEmptyObject(data)) {

                            }
                            else {

                                $.each(data, function(index) {

                                    html += '<tr><td><input type="checkbox" class="checkboxes" data-memberId="' + data[index].id + '" value="' + data[index].id + '" /></td>';
                                    html += "<td>" + data[index].id + "</td>";
                                    html += "<td>" + data[index].firstName + "</td>";
                                    html += "<td>" + data[index].middleName + "</td>";
                                    html += "<td>" + data[index].surname + "</td>";
                                    html += "<td>" + data[index].gender + "</td>";
                                    html += '<td> </td></tr>';
                                });
                            }

                            $("#member-list tbody").html("");
                            $("#member-list tbody").append(html);


                        });

                    });
                     $('#proxyForm').submit(function(event) {

                        var firstName = $("#proxyFirstName").val();
                        var surname = $("#proxyLastName").val();
                        var middleName = $("#proxyMiddleName").val();
                        var gender = $("#proxyGender").val();
                        var address = $("#proxyAddress").val();
                        var phoneNumber = $("#proxyPhoneNumber").val();
                        var emailAddress = $("#proxyEmail").val();
                        var companyId = $("#proxyCompanyId").val();
                        var agmId = $("#proxyAgmId").val();
                        var memberId = $("#proxyMemberId").val();
                        
                        if (firstName !== "" && surname !== "" && gender !== "" && phoneNumber !== "" && emailAddress !== "" && address !=="") {
                            var json = {"phoneNumber":phoneNumber,"emailAddress":emailAddress,"firstName": firstName, "surname": surname, "middleName": middleName, "gender": gender, "address": address, "companyId": companyId, "agmId": agmId, "memberId": memberId};

                            $.ajax({
                                url: $("#proxyForm").attr("action"),
                                data: JSON.stringify(json),
                                type: "POST",
                                contentType: "application/json; charset=utf-8",
                                dataType: 'json',
                                beforeSend: function(xhr) {
                                    xhr.setRequestHeader("Accept", "application/json");
                                    xhr.setRequestHeader("Content-Type", "application/json");
                                },
                                success: function(data) {

                                    var html = "";
                                    if(data.id > 0){
                                    $("#proxysuccess-message").html("Operation Successful");
                                    $('#proxyResponse').modal('show');
                                    }
                                }
                            });
                        }
                        event.preventDefault();
                    });
                    $('#agmForm').submit(function(event) {

                        var stringStartDate = $("#stringStartDate").val();

                        var stringStartTime = $("#stringStartTime").val() + ":00";
                        var stringEndTime = $("#stringEndTime").val() + ":00";
                        var note = $("#note").val();
                        var companyId = $("#companyId").val();
                        var allowProxy = $("input[name='allowProxy']").val();

                        if (stringStartDate !== "" && stringStartTime !== "" && stringEndTime !== "" && allowProxy !== "") {
                            var json = {"stringStartTime": stringStartTime, "stringEndTime": stringEndTime, "stringStartDate": stringStartDate, "note": note, "allowProxy": allowProxy, "companyId": companyId};

                            $.ajax({
                                url: $("#agmForm").attr("action"),
                                data: JSON.stringify(json),
                                type: "POST",
                                contentType: "application/json; charset=utf-8",
                                dataType: 'json',
                                beforeSend: function(xhr) {
                                    xhr.setRequestHeader("Accept", "application/json");
                                    xhr.setRequestHeader("Content-Type", "application/json");
                                },
                                success: function(data) {

                                    var html = "";
                                    html += "<tr><td>" + data.id + "</td>";
                                    html += "<td>" + data.stringStartDate + "</td>";
                                    html += "<td>" + data.stringStartTime + "</td>";
                                    html += "<td>" + data.stringEndTime + "</td>";
                                    html += "<td>" + data.note + "</td>";
                                    html += '<td><a id="attend-' + data.id + '" href="#" data-id="' + data.id + '"  data-toggle="tooltip" title="Add Attendees" class="add-attendees">Add Attendees</a> </td></tr>';

                                    $("#agm-list tbody").append(html);
                                    $('#productAccountProductId').val(data.id);
                                    $("#status-text").html("Operation Successful");
                                    $('#statusMoal').modal('show');

                                }
                            });
                        }
                        event.preventDefault();
                    });

                    $('#searchForm').submit(function(event) {
                        var json = $('#searchForm').serialize();
                        $.ajax({
                            url: $("#searchForm").attr("action"),
                            data: json,
                            type: "GET",
                            contentType: "application/json; charset=utf-8",
                            dataType: 'json',
                            beforeSend: function(xhr) {
                                xhr.setRequestHeader("Accept", "application/json");
                                xhr.setRequestHeader("Content-Type", "application/json");
                            },
                            success: function(data) {
                                var html = "";
                                $.each(data, function(index) {

                                    html += '<tr><td><input type="checkbox" class="checkboxes" data-memberId="' + data[index].id + '" value="' + data[index].id + '" /></td>';
                                    html += "<td>" + data[index].firstName + "</td>";
                                    html += "<td>" + data[index].middleName + "</td>";
                                    html += "<td>" + data[index].surname + "</td>";
                                    html += "<td>" + data[index].memberNo + "</td>";
                                    html += '<td><button type="submit" onclick="addToList(' + data[index].id + ')" class="btn btn-danger mr5 addPart" data-memberid="' + data[index].id + '" >Add</button> <button type="submit" data-memberid="' + data[index].id + '" onclick="addProxy(' + data[index].id + ')"  class="btn btn-danger mr5 addProxy">Add Proxy</button></td></tr>';
                                });

                                jQuery('#search-list').css("display", "block");
                                jQuery('#search-list tbody').html("");
                                jQuery('#search-list tbody').html(html);
                                jQuery('#search-list').DataTable({
                                    responsive: true
                                });
                            }
                        });
                        event.preventDefault();
                    });
                    // Progress Wizard

                })
            </script>