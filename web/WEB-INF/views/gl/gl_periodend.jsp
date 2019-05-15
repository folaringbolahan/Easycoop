<%@ include file="../includes/header.jsp" %>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>End of Day/Period Processing</li>
        </ul>
        <h4>End of Day/Period</h4>
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
            <form:form method="post" action="gl_periodendproc.htm" modelAttribute="periodenddet">  
                <div class="panel-body">
                    <p></p>
                    
                    End of Day/Period Processing
                    
                     
                    <p>
                         <br><br>
                    <div class="form-group">
                     <form:label path="opmessage" cssClass="col-sm-4 control-label">${periodenddet.opmessage}</form:label>   
                     
                    </div><!-- form-group -->
                    
                   

                </div><!-- panel-body -->
                <p></p>
                <div>
                
                <c:choose> 
                    <c:when test="${periodenddet.completionstatus==1}">
                        <input type="submit" class="btn btn-danger mr5" value="Process" disabled />
                    </c:when>
                    <c:otherwise>    
                        <input type="submit" class="btn btn-danger mr5" value="Process"/>
                    </c:otherwise>
                </c:choose>
                </div><!-- panel-footer -->

            </form:form>  
            <!-- form ends -->
       <!--  </div> -->
        <!-- End of panel-body -->
    </div>
    

</div>


<!-- contentpanel -->
 
<%@ include file="../includes/footer.jsp" %>  