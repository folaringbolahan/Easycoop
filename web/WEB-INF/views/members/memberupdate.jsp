<%@ include file="../includes/header.jsp" %>
<script lanuage="Javascript">
	function doSubmit(frm){	
	   
	    frm.submit();
	}	  
  </script>
<div class="media-body">
  <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Member Profile Update Approval</li>
    </ul>
    <h4>Approval For Member Profile Update </h4>
</div>
 <%@include file="../includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
 <form:form method="POST" action="approveUpdate" commandName="memberprofileupdate">
  <div class="contentpanel">
    <div class="row">
        <div class="col-md-10">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
	    
     
          <h4>LIST OF PENDING MEMBER PROFILE UPDATE</h4>  
	     <table id="addr-item-type" class="table table-striped table-bordered responsive">  
	      <thead>
	           <tr>  
		     
		       <th>Member No</th>  
		       <th>Member Name</th>
                       <th>Change Type</th>
                       <th>Field Value</th>
                       <th>Action</th> 
                       
	           </tr>  
	      </thead>
	      <c:forEach items="${memberupdate}" var="member">  
	       <tr>  
               
		<td><c:out value="${member.memberno}"/></td>  
		<td><c:out value="${member.createdby}"/></td>  
                <td><c:out value="${member.changetype}"/></td> 
                 <td><c:out value="${member.fieldvalue}"/></td> 
                 <td><input type="checkbox" name="id"  value="${member.id}" >
                 <input type="hidden" name="memberno"  value="${member.memberno}" >
                 <input type="hidden" name="createdby"  value="${member.createdby}" >
                 <input type="hidden" name="changetype"  value="${member.changetype}" >
                 <input type="hidden" name="fieldvalue"  value="${member.fieldvalue}" >
                  <input type="hidden" name="branchid"  value="${member.branchid}" >
                   <input type="hidden" name="companyid"  value="${member.companyid}" >
                  </td>
                
                
                
		<%--<td><c:out value="${addressType.active}"/></td> 
		<td><c:out value="${addressType.deleted}"/></td> --%>

		
	       </tr>  
	      </c:forEach>  
	     </table>  
               <div class="form-group">
                           <button class="btn btn-danger mr5" type="submit" >APPROVE </button>

                        </div>
            </div><!-- col-md-6 -->
        </div>
    </div>
</div>
  </form:form>
<!-- contentpanel -->

<%@ include file="../includes/footer.jsp" %>  
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
   