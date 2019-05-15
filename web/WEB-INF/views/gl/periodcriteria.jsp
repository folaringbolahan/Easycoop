<%@ include file="../includes/header.jsp" %>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Period Criteria</li>
        </ul>
        <h4>Period Criteria</h4>
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
            <form:form method="post" action="${reportpath}" modelAttribute="reportdet">  
                <div class="panel-body">

                    <div class="form-group">
                        <form:label path="yr" cssClass="col-sm-4 control-label">Year: </form:label>
                            <div class="col-sm-4">
                            <form:select path="yr" >
                                <form:option value="" label="Choose Year"/>
                                <form:options  items="${yrs}"
                                         itemValue="code" itemLabel="description"/>
                            </form:select>
                            <form:errors path="yr" cssClass="error" />
                        </div>
                    </div><!-- form-group -->
                    <div class="form-group">
                        <form:label path="prd" cssClass="col-sm-4 control-label">Period: </form:label>
                            <div class="col-sm-4">
                            <form:select path="prd">
                                <form:option value="" label="Choose Period" />
                                <form:options  items="${prds}" 
                                               itemValue="code" itemLabel="description"/>
                                </form:select>
                            <form:errors path="prd" cssClass="error" />
                            <form:hidden path="paratype" />
                            <form:hidden path="reppath" />
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


<!-- contentpanel -->
 
<%@ include file="../includes/footer.jsp" %>  