<%-- 
    Document   : bank
    Created on : Nov 17, 2015, 11:02:07 AM
    Author     : Olakunle
--%>

<%@ include file="includes/header.jsp" %>
<div class="media-body">
<div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Manage Bank</li>
    </ul>
    <h4>Manage Bank</h4>
</div>
<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
 <script lanuage="Javascript">
	function doSubmit(frm){	
	    if(frm.countryId.value=="" || frm.countryId.value=="0"){alert("please specify country"); frm.countryId.focus(); return;}
	    
	    if(frm.bankCode.value==""){alert("Bank Code is required"); frm.bankCode.focus(); return;}
            if(frm.bankName.value==""){alert("Bank Name is required"); frm.bankName.focus(); return;}
	    frm.submit();
	}	  
  </script>
<div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-12">
			<div class="panel-body">
                 <c:set var="now" value="<%=new java.util.Date()%>" />
                 <form:form method="post" action="saveBank" modelAttribute="bank">		  
				<form:hidden path="bankId"/>                   
                   
		  
                <div class="form-group">  
                       <label data-toggle="tooltip" id="select-templating" title="Select Country" class="col-sm-3 tooltips control-label">Country <span class="asterisk">*</span></label>
			  
			  <div class="col-sm-7">
			      <form:select  cssClass="width300" id="select-search-hide" path="countryId" required ="required">
				<form:option value="0">--Choose Country--</form:option>
				<c:forEach items="${countries}" var="item">  
				     <form:option value="${item.id}">${item.countryName}</form:option>                                     
				</c:forEach>                                 
			      </form:select>
			  </div>  
		   </div> 	   
                  
                   <div class="form-group">
                        <label data-toggle="tooltip" title="Enter Bank Code here " class="col-sm-3 tooltips control-label">Bank Code <span class="asterisk">*</span></label>
                          <div class="col-sm-7">                                           
                               <form:input path="bankCode" id="bankCode" cssClass="form-control" placeholder="Type Bank Code here" required ="required"/>                                                                 
                          </div>
                    </div><!-- form-group -->  
                          
                   <div class="form-group">
                        <label data-toggle="tooltip" title="Enter Bank Name here " class="col-sm-3 tooltips control-label">Bank Name <span class="asterisk">*</span></label>
                          <div class="col-sm-7">                                           
                               <form:input path="bankName" id="bankName" cssClass="form-control" placeholder="Type Bank name here" required ="required"/>                                                                 
                          </div>
                    </div><!-- form-group -->                    
         
           <form:hidden path="delFlg" value="N"/>  

            <div class="form-group">
                            <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">SUBMIT</button>
                            <button type="reset" class="btn btn-default">RESET</button>
                        </div><!-- panel-footer -->
       
            
      </form:form>  
        </div>
      <c:if test="${!empty listAllBanks}">  
          <div class="media-body">
             <h4>LIST ALL BANKS</h4>
          </div>
	     <table id="bank-list" class="table table-striped table-bordered responsive">  
		 <thead>
	      <tr>  
	       <th>S/N</th>  
	       <th>COUNTRY</th>
	       <th>BANK CODE</th> 	       
	       <th>BANK NAME</th>
	       <th> </th>
	       <th></th>  
	      </tr>  
            </thead>
            <tbody>
	      <c:forEach items="${listAllBanks}" var="bank" varStatus = "status">  
	       <tr>  
		<td><c:out value="${status.index + 1}"/></td>  
		<td><c:out value="${bank.countryName}"/></td>  
		<td><c:out value="${bank.bankCode}"/></td> 
                <td><c:out value="${bank.bankName}"/></td>                 
		<td align="center"><a href="bank/edit/${bank.bankId}">Edit</a></td>  
                <td><a href="bank/delete/${bank.bankId}">Delete</a></td>
	       </tr>  
	      </c:forEach>  
		  </tbody>
	     </table>  
    </c:if>  
            </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->

<%@ include file="includes/footer.jsp" %>  
<script>
    $(document).ready(function(){
         jQuery('select').select2({
                    minimumResultsForSearch: -1
                });
	jQuery('#bank-list').DataTable({
                    responsive: true
                });
                
        jQuery('#select-search-hide').select2({
                    minimumResultsForSearch: 15
                });        
    })
</script>
   