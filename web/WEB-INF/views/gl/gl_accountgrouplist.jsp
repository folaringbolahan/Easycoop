<%@ include file="../includes/header.jsp" %>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Account Group</li>
        </ul>
        <h4>Add Account Group</h4>
    </div>
    <%@include file="../includes/topright.jsp" %>
    
</div>
</div><!-- media -->
</div><!-- pageheader -->



<div class="contentpanel">

    <div class="row col-md-6">
        <!-- CONTENT GOES HERE -->  
        <!-- <div class="panel-body">-->
            <!-- form starts -->
            <form:form method="post" action="gl_accountgroups.htm" modelAttribute="accountgroupdet">  
                <div class="panel-body">


                    <div class="form-group">
                        <form:label path="acGrpId" cssClass="col-sm-4 control-label tooltips"  data-original-title="Group Id must be numeric and unique" data-toggle="tooltip" data-placement="left">Group id: *</form:label>
                            <div class="col-sm-6">
                            <form:input path="acGrpId" size="2"/>
                            <div class="error">
                                <form:errors path="acGrpId" />
                            </div>
                        </div>
                    </div><!-- form-group -->

                    <div class="form-group">
                        <form:label path="description" cssClass="col-sm-4 control-label tooltips"  data-original-title="Group Title" data-toggle="tooltip" data-placement="left">Description: *</form:label>
                            <div class="col-sm-6">
                            <form:input path="description" size="50" />
                            <div class="error">
                                <form:errors path="description" />
                            </div>
                        </div>
                    </div><!-- form-group -->

                    <div class="form-group">
                        <form:label path="classId" cssClass="col-sm-4 control-label tooltips"  data-original-title="Categorization on Balance Sheet and Income Statement" data-toggle="tooltip" data-placement="left">Class: *</form:label>
                            <div class="col-sm-4">
                            <form:select path="classId" >
                                <form:option value="" label="Choose Class"/>
                                <form:options  items="${accountclasses}"
                                         itemValue="code" itemLabel="description"/>
                            </form:select>
                            <form:errors path="classId" cssClass="error" />
                        </div>
                    </div><!-- form-group -->
                    

                </div><!-- panel-body -->
                <div>
                    <input type="submit" class="btn btn-danger mr5" value="Save"/>
                </div><!-- panel-footer -->

            </form:form>  
            <!-- form ends -->
       <!--  </div> -->
        <!-- End of panel-body -->
    </div>
    <div class=" col-md-10">
        <table  id="data-list" class="table table-striped table-bordered responsive" >       
            <thead>
                <tr>
                    <th>Id</th>
                    <th class=" tooltips"  data-original-title="Click to Sort" data-toggle="tooltip" data-placement="top">Group Id</th>
                    <th class=" tooltips"  data-original-title="Click to Sort" data-toggle="tooltip" data-placement="top">Description</th>
                    <th class=" tooltips"  data-original-title="Click to Sort" data-toggle="tooltip" data-placement="top">Class</th>
                    <th>Report Group</th>
                    <th></th>
                    <th></th>

                </tr>
            </thead>
            <tbody>
                <c:forEach var="accountgroup" items="${accountgroups}" >

                    <tr>
                        <td>${accountgroup.groupId}</td>    
                        <td>${accountgroup.acGrpId}</td>
                        <td>${accountgroup.description}</td>
                        <td>${accountgroup.classId}</td>
                        <td>${accountgroup.reportGroup}</td>
                        <td><a href="gl_editaccountgroup.htm?id=${accountgroup.groupId}&AMP;nm=${accountgroup.description}&AMP;loc=${accountgroup.classId}&AMP;acg=${accountgroup.acGrpId}">Edit</a></td>
                        <td><a href="gl_removeaccountgroup.htm?id=${accountgroup.groupId}" onclick="return confirm('Delete record with code - ${accountgroup.groupId} Continue?')" type="submit" class="buttons" value="Go">Delete</a></td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>
    </div><!-- col-md-10 -->

</div>


<!-- contentpanel -->
 
<%@ include file="../includes/footer.jsp" %>  