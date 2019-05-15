<%@ include file="includes/header.jsp" %>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Loan Reference Criteria</li>
        </ul>
        <h4>Loan Reference Criteria</h4>
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
                    <p></p>
                    <p></p>
                    <p></p>
                    <p></p>
                    <p></p>
                    <p></p>
                    <p>
                     <p>   
                    <p>                    
                    <br><br> <br><br>
                    <div class="form-group">
                    <form:label path="strval1" cssClass="col-sm-4 control-label">Loan Case Id: </form:label>
                            <div class="col-sm-8">
                                <div class="input-group">
                                     <form:input path="strval1" cssClass="form-control" size="60" />
                                </div>
                            </div>
                            <input type="hidden" id="paratype" name="paratype" value="${paratype}"/>
                            <form:hidden path="reppath" />
                    </div><!-- form-group -->                    
                    <!-- form-group -->

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