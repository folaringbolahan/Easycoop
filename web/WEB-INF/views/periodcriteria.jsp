<%@ include file="includes/header.jsp" %>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Period Criteria</li>
        </ul>
        <h4>Period Criteria</h4>
    </div>
    <%@include file="includes/topright.jsp" %>
    
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
                        <form:label path="year" cssClass="col-sm-4 control-label">Year: </form:label>
                            <div class="col-sm-4">
                            <form:select path="year" >
                                <form:option value="" label="Choose Year"/>
                                <form:options  items="${yrs}"/>
                                        
                            </form:select>
                            <form:errors path="year" cssClass="error" />
                        </div>
                    </div><!-- form-group -->
                    <div class="form-group">
                        <form:label path="periodId" cssClass="col-sm-4 control-label">Period: </form:label>
                            <div class="col-sm-4">
                            <form:select path="periodId">
                                <form:option value="" label="Choose Period" />
                                <form:options  items="${prds}" />
                                              
                                </form:select>
                            <form:errors path="periodId" cssClass="error" />
                            <!--<form:hidden path="paratype" value="${paratype}" />-->
                            <input type="hidden" id="paratype" name="paratype" value="${paratype}"/>
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
 
<%@ include file="includes/footer.jsp" %>  