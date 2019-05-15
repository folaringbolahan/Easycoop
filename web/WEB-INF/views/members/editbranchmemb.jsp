<%@ include file="includes/header.jsp" %>  

<div class="media-body">
    <div style="float:left">

        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="#">Home</a></li>
            <li>Members Email Change</li>
        </ul>
        <h4>MEMBERS EMAIL CHANGE </h4>
    </div>
    <%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<script lanuage="Javascript">
    function doSubmit(frm) {
      
       if(frm.newemail.value==""){alert("new email address field cannot be empty"); frm.description.focus(); return;}
      
        frm.submit();
    }
</script>
<div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
                <div class="panel-body">	

                    <form:form method="POST" action="addNewEmail" commandName="membMail">

                        <div class="form-group"> 
                            <form:label path="emailAdd1" data-toggle="tooltip" title="Email" cssClass="col-sm-3 tooltips control-label">Current Email:</form:label>  
                                <div class="col-sm-8">
                        <form:input path="emailAdd1" id="emailAdd1" cssClass="form-control" value="${branchmembdet.emailAdd1}" readonly="true"/>                                            
                                <form:errors path="emailAdd1" cssClass="error"></form:errors>
                                </div>  
                            </div> 
                            <div class="form-group"> 
                            <form:label path="newemail" data-toggle="tooltip" title="New Email" cssClass="col-sm-3 tooltips control-label">New Email:</form:label>  
                                <div class="col-sm-8">
                            <form:input path="newemail" id="newemail" cssClass="form-control"  />           
                                <form:errors path="newemail" cssClass="error"></form:errors>
                                </div>  
                            </div> 

                            <input type="hidden" name="createdby" value="<%=request.getRemoteUser()%>">
                            <input type="hidden" name="companyid" value="${branchmembdet.company.id}">
                            <input type="hidden" name="branchid" value="${branchmembdet.branch.id}">
                            <input type="hidden" name="memberid" value="${branchmembdet.memberId}">
                            <input type="hidden" name="firstname" value="${branchmembdet.firstName}">
                            <input type="hidden" name="middlename" value="${branchmembdet.middleName}">
                            <input type="hidden" name="surname" value="${branchmembdet.surname}">
                            

                        <div class="form-group">
                            <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">UPDATE EMAIL</button>

                        </div><!-- panel-footer -->       

                    </form:form>  
                </div>


            </div><!-- col-md-6 -->
        </div>
    </div>
</div>
<!-- contentpanel -->

<%@ include file="includes/footer.jsp" %>  
<script>
    $(document).ready(function() {
        
        jQuery('select').select2({
            minimumResultsForSearch: -1
        });

        jQuery('#addr-item-type').DataTable({
            responsive: true
        });
     

       
    })
</script>
