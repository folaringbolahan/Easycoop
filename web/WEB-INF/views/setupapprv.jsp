<%@ include file="includes/header.jsp" %>  

<div class="media-body">
<div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Agm Setup For Approval</li>
    </ul>
    <h4>AGM/VOTE SETUP APPROVAL</h4>
</div>
<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<form:form method="POST" action="approveAgmSetup" commandName="votAgm">
<div class="contentpanel">

      <c:set var="logonUser" value="<%=request.getRemoteUser()%>" />
      <div class=" col-md-10">
        <table  id="data-list" class="table table-striped table-bordered responsive" >       
            <thead>
                <tr>
                     
		       <th>Agm</th>
                       <th>Company</th>
		       <th>Total Members Imported</th>  
                        <th>Approve</th>  
                 

                </tr>
            </thead>
           
             
              <c:forEach var="setup" items="${setupList}" >
                  
                  <tbody>
                  <tr>
                          
                        <td>
                           
                               Agm :&nbsp;${setup.description}</br>
                           Agm Year:&nbsp; ${setup.agmyear}</br>
                         Start Date:&nbsp; ${setup.startdate}</br>
                           End Date:&nbsp; ${setup.enddate}</br>
                           Start time:&nbsp; ${setup.starttime}</br>
                           End Time:&nbsp; ${setup.endtime}
                        </td>
                        <td>${setup.companyName}</td>       
                        <td>${setup.memberCount}</td>
                        <td> 
                      <c:if test="${setup.createdby == logonUser}">
    <font color="red"><div data-toggle="tooltip" title="A different user is required to approve this Agm" cssClass="tooltips">NOT ALLOWED</div></font>
                     </c:if>
            <c:if test="${setup.createdby ne logonUser}">
		 <input type="checkbox" name="selectedAgm"  value="${setup.id}" >
                  <input type="hidden" name="ids" value="${setup.id}">
                   <form:hidden path="modifieddate"  value='<%=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())%>'/>
                   <form:hidden path="modifiedby" value="<%=request.getRemoteUser()%>"/>
                    <form:hidden path="lastreminderdate" value='<%=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())%>'/>
					    </c:if>
                           
                        </td>
                   </tr>
                  
                </c:forEach>
               
            </tbody>
                
                    
         
        </table>
           <div class="form-group">
                           <button class="btn btn-danger mr5" type="submit" >APPROVE </button>

                        </div>
    </div>
    

</div>
</form:form>

<%@ include file="includes/footer.jsp" %>  

  


