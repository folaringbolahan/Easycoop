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
 if(frm.branchid.value==""){alert("Branch field cannot be empty"); frm.description.focus(); return;}
        frm.submit();
    }
</script>
<div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
                <div class="panel-body">	

                    <form:form method="POST" action="viewBranchMembers" commandName="membMail">

                        <div class="form-group"> 
                            <form:label path="companyid" data-toggle="tooltip" title="Select Cooperative" cssClass="col-sm-3 tooltips control-label">Select Cooperative*:</form:label>  
                                <div class="col-sm-8">
                                <form:select id="companyid" path="companyid"  cssClass="width300">
                                    <form:option value="0">---------------------Select Company--------------</form:option>
                                    <c:forEach items="${cooplist}" var="item">  

                                        <form:option value="${item.id}">${item.name}</form:option>
                                    </c:forEach>
                                </form:select>
                                <form:errors path="companyid" cssClass="error"></form:errors>
                                </div>  
                            </div> 
                            <div class="form-group"> 
                            <form:label path="branchid" data-toggle="tooltip" title="Select Branch" cssClass="col-sm-3 tooltips control-label">Select Branch*:</form:label>  
                                <div class="col-sm-8">
                                <form:select id="branchid" path="branchid"  cssClass="width300" placeholder="Select branch">
                                     <form:option value="0">---------------------Select Branch--------------</form:option>
                                </form:select>
                                <form:errors path="branchid" cssClass="error"></form:errors>
                                </div>  
                            </div> 

                            <input type="hidden" name="createdby" value="<%=request.getRemoteUser()%>">
                         

                        <div class="form-group">
                            <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">GO</button>

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

        $("#companyid").on("change", function() {
         
            var coopid = $(this).val();
           
            if (coopid) {
//                  alert("cooperative id " + coopid);
                var json = {"coopid": coopid};
                $.ajax(
                        {
                            url: '<%=request.getContextPath()%>/listcoopbranch/'+coopid,
//                            contentType: "application/json; charset=utf-8",
//                            data: JSON.stringify(json),
                            type: "GET",  
//                            dataType: "json",    
                            success: function(data) {
                        console.log("start here");
                             console.log(data);
                               var opts=[];// = $.parseJSON(data);
                               
                                // Use jQuery's each to iterate over the opts value
                                 var listItems ;
                                $.each(data, function(index,branchObj) {
                                      listItems += "<option value='" + branchObj.id + "'>" + branchObj.branchName + "</option>";
                                     
//                                    aalert("branch id at success is " +branchObj.id);
                              
         
                                    console.log('branchid is ---'+branchObj.id);
                                    console.log('branch NAme is ---'+branchObj.branchName);
                                   // $('#branchid').('<option value="' + branchObj.id+ '">' +branchObj.branchName + '</option>');
                                    
                                });
                                $("#branchid").html(listItems);
                            }

                        }

                );

            }

        });
    })
</script>
