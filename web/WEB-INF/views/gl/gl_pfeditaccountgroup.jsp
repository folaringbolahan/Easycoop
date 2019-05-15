<%@ include file="../includes/header.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Account Group</li>
        </ul>
        <h4>Edit Account Group</h4>
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
            <form:form method="post" action="gl_pfeditaccountgroup.htm" modelAttribute="accountgroupdet">  
                <div class="panel-body">


                    <div class="form-group">
                        <form:label path="groupId" cssClass="col-sm-4 control-label">Id: </form:label>
                            <div class="col-sm-6">
                            <form:input path="groupId" size="2"  readonly="true" disabled="disable" />
                            <div class="error">
                                <form:errors path="groupId" />
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="acGrpId" cssClass="col-sm-4 control-label tooltips"  data-original-title="Group Id must be numeric and unique" data-toggle="tooltip" data-placement="left">Group id: * </form:label>
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
</div>       
            <%@ include file="../includes/footer.jsp" %>  