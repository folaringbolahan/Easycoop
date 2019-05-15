<%@ include file="includes/header.jsp" %>  

<div class="media-body">
    <!-- changes starts -->
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="#">Home</a></li>
            <li>Vote(s) Management</li>
        </ul>
        <h4>Manage Votes</h4>
    </div>
       <%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->

<div class="contentpanel">
    <div class="row">
        <div class="col-md-10">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
                <!-- Nav tabs -->
                <ul class="nav nav-tabs nav-danger">
                    <li class="active"><a href="#home6" data-toggle="tab"><strong>Setup Votes</strong></a></li>
                    <li><a href="#profile6" data-toggle="tab"><strong>Setup Vote Options</strong></a></li>
                    <li><a href="#profile7" data-toggle="tab"><strong>Manage Votes</strong></a></li>
                </ul>

                <!-- Tab panes -->
                <div class="tab-content tab-content-danger mb30">
                    <div class="tab-pane active" id="home6">

                        <p>   <form:form method="POST" commandName="voteForm" action="${pageContext.request.
                                                                                        contextPath}/views/votes/edit" id="votesForm">
                                   <div class="panel panel-default">
                                       <div class="panel-heading">
                                           <div class="panel-btns">
                                               <a href="#" class="panel-minimize tooltips" data-toggle="tooltip" title="Minimize Panel"><i class="fa fa-minus"></i></a>

                                           </div><!-- panel-btns -->
                                           <h4 class="panel-title">Vote Setup Form</h4>
                                           <p>Kindly fill the form below to set up a new  Vote.</p>

                                       </div>
                                       <div class="panel-body">
                                           <div id="submitResponse" class="alert alert-success" style="display:none">
                                               <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                               <p id="success-message"></p>
                                           </div>
                                           <div class="errorForm"></div>
                                           <div class="form-group">
                                               <label class="col-sm-4 control-label">Title<span class="asterisk">*</span></label>
                                               <div class="col-sm-4">

                                                   <form:input path="title" cssClass="form-control"  id="title" required="required" />
                                               </div>
                                           </div><!-- form-group -->
                                           <div class="form-group">
                                               <label class="col-sm-4 control-label">Start Date<span class="asterisk">*</span></label>
                                               <div class="col-sm-4">

                                                   <form:input path="stringDate" cssClass="form-control"  id="stringDate" required="required" />
                                               </div>
                                           </div>

                                           <div class="form-group">
                                               <label class="col-sm-4 control-label">End Date<span class="asterisk">*</span></label>
                                               <div class="col-sm-4">

                                                   <form:input path="stringEndDate" cssClass="form-control"  id="stringEndDate" required="required" />
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
                                               <label class="col-sm-4 control-label">Note/Description</label>
                                               <div class="col-sm-2">
                                                   <form:textarea path="description" cssClass="form-control" cols="150" rows="5" id="description" />
                                               </div>
                                               <div>
                                                   <form:hidden value="${currrentuserServicex.curruser.companyid}" path="companyId"  />
                                                   <form:hidden value="${votes.agmId}" path="agmId"  />
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
                            <div class="col-md-12">
                                <p>   <form method="POST" id="optionForm" action="${pageContext.request.
                                                                                    contextPath}/views/votes/editOptions" name="optionForm">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <div class="panel-btns">
                                                <a href="#" class="panel-minimize tooltips" data-toggle="tooltip" title="Minimize Panel"><i class="fa fa-minus"></i></a>

                                            </div><!-- panel-btns -->
                                            <h4 class="panel-title">Vote Options Setup Form</h4>
                                            <p>Kindly fill the form below to set up Vote Options.</p>

                                        </div>
                                        <div class="panel-body">
                                            <div id="submitOptionResponse" class="alert alert-success" style="display:none">
                                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                                <p id="option-success-message"></p>
                                            </div>
                                            <div class="errorForm"></div>
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">Option<span class="asterisk">*</span></label>
                                                <div class="col-sm-4">
                                                    <input type="hidden" value="" id="voteId" name="voteId"  />
                                                    <input name="vote_options" class="form-control"  id="vote_options" required="required" />
                                                </div>
                                            </div>

                                        </div><!-- panel-body -->
                                        <div class="panel-footer">
                                            <button class="btn btn-danger mr5" id="submit-option">Submit</button>
                                            <button type="reset" class="btn btn-default">Reset</button>
                                        </div><!-- panel-footer -->
                                    </div><!-- panel-default -->


                                </form></p>

                                <table id="options-list" class="table table-striped table-bordered responsive">
                                    <thead class="">
                                        <tr>
                                            <th style="width:8px;"><input type="checkbox" class="group-checkable" data-set="#options-list .checkboxes" /></th>
                                            <th>Id</th>
                                            <th>Option</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                    </tbody>
                                </table>

                            </div>

                        </div>
                        <div class="row">
                            <div class="col-md-10">

                            </div>
                        </div>
                    </div><!-- tab-pane -->
                    <div class="tab-pane" id="profile7">

                        <div class="row">
                            <div class="col-md-12">
                                <table id="votes-list" class="table table-striped table-bordered responsive">
                                    <thead class="">
                                        <tr>
                                            <th style="width:8px;"><input type="checkbox" class="group-checkable" data-set="#votes-list .checkboxes" /></th>
                                            <th>Id</th>
                                            <th>Title</th>
                                            <th>Date Setup</th>
                                            <th>Description</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach  var="rvote" items="${rvotes}" varStatus="status" >
                                            <tr>

                                                <td><input type="checkbox" class="checkboxes" value="${rvote.id}" /></td>
                                                <td>${status.index + 1}</td>
                                                <td>${rvote.title}</td>
                                                <td>${rvote.voteDate}</td>
                                                <td>${rvote.description}</td>
                                                <td></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                        </div>
                        <div class="row">
                            <div class="col-md-12">

                            </div>
                        </div>
                    </div><!-- tab-pane -->

                </div><!-- tab-content -->



            </div>
        </div>
    </div><!-- contentpanel -->

    <%@ include file="includes/footer.jsp" %>  
    <script>
        $(document).ready(function() {

            jQuery('#stringDate').datepicker();
            jQuery('#stringEndDate').datepicker();
            jQuery('#stringStartTime').timepicker({showMeridian: false});
            jQuery('#stringEndTime').timepicker({showMeridian: false});
            $.datepicker.setDefaults({dateFormat: 'dd-mm-yy'});
            $('#options-list').DataTable({
                responsive: true
            });
            $('#votes-list').DataTable({
                responsive: true
            });
            jQuery("#votesForm").validate({
                highlight: function(element) {
                    jQuery(element).closest('.form-group').removeClass('has-success').addClass('has-error');
                },
                success: function(element) {
                    jQuery(element).closest('.form-group').removeClass('has-error');
                }
            });
            jQuery("#optionForm").validate({
                highlight: function(element) {
                    jQuery(element).closest('.form-group').removeClass('has-success').addClass('has-error');
                },
                success: function(element) {
                    jQuery(element).closest('.form-group').removeClass('has-error');
                }
            });
            $('#votesForm').submit(function(event) {

                var title = $("#title").val();
                var stringDate = $("#stringDate").val();
                var stringEndDate = $("#stringEndDate").val();
                var description = $("#description").val();
                var agmId = $("#agmId").val();
                var stringEndDate = $("#stringEndDate").val();

                var stringStartTime = $("#stringStartTime").val() + ":00";
                var stringEndTime = $("#stringEndTime").val() + ":00";
                if (title !== "" && stringDate !== "" && stringEndDate !== "" && description !== "") {
                    var json = {"stringStartTime": stringStartTime, "stringEndTime": stringEndTime, "title": title, "stringDate": stringDate, "description": description, "agmId": agmId, "stringEndDate": stringEndDate};

                    $.ajax({
                        url: $("#votesForm").attr("action"),
                        data: JSON.stringify(json),
                        type: "POST",
                        contentType: "application/json; charset=utf-8",
                        dataType: 'json',
                        beforeSend: function(xhr) {
                            xhr.setRequestHeader("Accept", "application/json");
                            xhr.setRequestHeader("Content-Type", "application/json");
                        },
                        success: function(data) {

                        if(data.id<1){
                            $("#success-message").html("Operation Failed! Please again");
                            $('#submitResponse').css("display", "block");
                        }else{
                            $('#voteId').val(data.id);
                            $("#success-message").html("You have successfully setup a vote. You should now create options.");
                            $('#submitResponse').css("display", "block");
                        }
                        }
                    });
                }
                event.preventDefault();
            });
            $('#optionForm').submit(function(event) {
                var voteOption = $("#vote_options").val();
                var voteId = $("#voteId").val();




                var json = {"voteOption": voteOption, "voteId": voteId};

                $.ajax({
                    url: $("#optionForm").attr("action"),
                    data: JSON.stringify(json),
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    dataType: 'json',
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader("Accept", "application/json");
                        xhr.setRequestHeader("Content-Type", "application/json");
                    },
                    success: function(data) {
                        var respContent = "";
                        // $("#options-list tbody").html("");
                        var html = "";
                        //   $.each(data, function(index) {

                        html += "<tr><td>" + data.id + "</td>";
                        html += "<td>" + data.voteOption + "</td>";
                        html += '<td class="table-action"><a id="delete-' + data.id + '" href="#" data-id="' + data.id + '"  data-toggle="tooltip" title="Delete" class="delete-row tooltips delete"><i class="fa fa-trash-o"></i></a> </td></tr>';

                        // });
                        $("#options-list tbody").append(html);

                        $("#submitOptionResponse").css("display", "block");
                        $("#option-success-message").html("Operation successful");
                    }
                });

                event.preventDefault();
            });
        })
    </script>