<%@ include file="includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Password Reset</li>
    </ul>
    <h4>LIST OF MEMBERS</h4>
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
  <div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
	     <div class="panel-body">	      
                 <form:form method="post" action="resetAndSendPasswrd" commandName="memberExtraField">          
                 
          <h4>LIST OF AGM MEMBERS</h4>  
            <div class="form-group">
		    <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">RESET PASSWORD</button>
		   
	    </div>
	     <table id="addr-item-type" class="table table-striped table-bordered responsive">  
	      <thead>
	           <tr>  
		       <th>ID</th>  
		       <th>Member Name</th>  
		       <th>Email</th>
                       <th>Select Members</th>
                     
                      
                       
	           </tr>  
	      </thead>
	      <c:forEach items="${listMembers}" var="member">  
	       <tr>  
		<td><c:out value="${member.memberid}"/></td>  
		<td><c:out value="${member.firstname} ${member.middlename} ${member.surname}"/></td>  
                <td>
                    <c:out value="${member.email}"/>
                    <input type="hidden" name="email" value="${member.email}">
                </td> 
               
               
                <td> <input type="checkbox" name="selectedMemberId"  value="${member.memberid}" >  </td>  
		

		
	       </tr>  
	      </c:forEach>  
	     </table>  
      
            
      </form:form>  
      </div>
 
      
            </div><!-- col-md-6 -->
        </div>
    </div>
</div>
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
   