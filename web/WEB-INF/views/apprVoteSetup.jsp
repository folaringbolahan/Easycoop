<%@ include file="includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Agm Vote Setup</li>
    </ul>
    <h4>Setup Options </h4>
</div>
<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<script lanuage="Javascript">
	function doSubmit(frm){	
	   
	    frm.submit();
	}	  
  </script>
  <form:form method="POST" action="approveVoteSetup" commandName="votQuest">
  <div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
	     <div class="panel-body">	
                  <c:set var="logonUser" value="<%=request.getRemoteUser()%>" />
                   <c:if test="${!empty setup}">  
          <h4>LIST OF  VOTE SETUPS FOR APPROVAL</h4>  
	     <table id="addr-item-type" class="table table-striped table-bordered responsive">  
	      <thead>
	           <tr>  
		      
		       <th>Question id</th>
                       <th>Question</th> 
                       <th>Options</th> 
                       <th>Approve</th> 
	           </tr>  
	      </thead>
	      <c:forEach items="${setup}" var="setupObj">  
	       <tr> 
		<td><c:out value="${setupObj.qestionid}"/></td>  
		<td><c:out value="${setupObj.question}"/></td>  
                <td>
                  <c:forEach items="${setupObj.voteOptions}" var="setupObjopt">   
                    <c:out value="${setupObjopt.description}"/><br>
                      </c:forEach> 
                </td>  
                <td>
                       <!--when the logged in user is not the person that created the vote setup or modified the setup-->
                    <c:choose>
         
          <c:when test='${(setupObj.createdby == logonUser)&& (setupObj.modifiedby == null || setupObj.modifiedby== "") }'>
    <font color="red"><div data-toggle="tooltip" title="A different user is required to approve this Vote Setup " cssClass="tooltips">NOT ALLOWED</div></font>
    </c:when>    
   
              <c:when test='${(setupObj.createdby ne logonUser) && (setupObj.modifiedby==null || setupObj.modifiedby=="")}'>
         <!--when the logged in user is  the person that created the vote setup but did not  modify the setup-->
   <input type="checkbox" name="id"  value="${setupObj.qestionid}" >
              </c:when> 
   
     <c:when test='${(setupObj.createdby == logonUser) && (setupObj.modifiedby ne logonUser ) && (setupObj.modifiedby ne null && setupObj.modifiedby ne "" ) }'>
         <!--when the logged in user is  the person that created the vote setup but another admin has   modified  the setup-->
    <input type="checkbox" name="id"  value="${setupObj.qestionid}" >
              </c:when> 
    
    <c:when test='${(setupObj.createdby ne logonUser) && (setupObj.modifiedby == logonUser ) && (setupObj.modifiedby ne null || setupObj.modifiedby ne "" ) }'>
         <!--when the logged in user is  the person that created the vote setup but another admin has   modified  the setup-->
  <font color="red"><div data-toggle="tooltip" title="A different user is required to approve this Vote Setup " cssClass="tooltips">NOT ALLOWED</div></font>
              </c:when> 
    
        <c:when test='${(setupObj.createdby == logonUser) && (setupObj.modifiedby== logonUser)}'>
           
    <font color="red"><div data-toggle="tooltip" title="A different user is required to approve this Vote Setup " cssClass="tooltips">NOT ALLOWED</div></font>
        </c:when> 
    
     
      <c:when test='${(setupObj.createdby ne logonUser )&& (setupObj.modifiedby ne logonUser) && (setupObj.modifiedby ne null && setupObj.modifiedby ne "")}'>
     
    <input type="checkbox" name="id"  value="${setupObj.qestionid}" >
    </c:when>
    
      
   
    
    <c:otherwise>
       <input type="checkbox" name="id"  value="${setupObj.qestionid}" > 
    </c:otherwise>
     
                 </c:choose>
                </td>  
              
		<%--<td><c:out value="${addressType.active}"/></td> 
		<td><c:out value="${addressType.deleted}"/></td> --%>

		
	       </tr>  
	      </c:forEach>  
	     </table>  
    </c:if> 
           <c:set var="agmid" value='<%= request.getParameter("id") %>' />
       <input type="hidden" name="agmid" value="${agmid}">
       <div class="form-group">
                            <button class="btn btn-danger mr5" type="submit" >APPROVE </button>

                        </div>         
      </div>
 
            </div><!-- col-md-6 -->
        </div>
    </div>
</div>
 </form:form>

<!-- contentpanel -->

<%@ include file="includes/footer.jsp" %>  
<script>
    $(document).ready(function(){
         jQuery('select').select2({
                    minimumResultsForSearch: -1
         });
	 
	 jQuery('#addr-item-type').DataTable({
                    responsive: true
         });
    })
</script>
   
   