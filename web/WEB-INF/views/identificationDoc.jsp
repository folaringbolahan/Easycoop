<%-- 
    Document   : identificationDoc
    Created on : Nov 17, 2015, 5:55:00 PM
    Author     : Olakunle Awotunbo
--%>

<%@ include file="includes/header.jsp" %>
<div class="media-body">
<div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Country Management</li>
    </ul>
    <h4>Manage Identification Document</h4>
</div>
<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<script lanuage="Javascript">
	function doSubmit(frm){	
	    if(frm.identificationdocdesc.value==""){alert("Identificationdoc desc is required"); frm.countryCode.focus(); return;}
	    if(frm.identificationdocname.value==""){alert("Identificationdocname name is required"); frm.countryName.focus(); return;}
	    if(frm.countryId.value=="0"){alert("please select Country"); frm.currencyCode.focus(); return;}
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
                 <form:form method="post" action="saveIdenDoc" modelAttribute="identificationDoc">		
		  
		 <form:hidden path="identificationDocId"/>                   
                   
		  
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
                        <label data-toggle="tooltip" title="Enter Identifiaction Document Name " class="col-sm-3 tooltips control-label">Document Name
                            <span class="asterisk">*</span></label>
                          <div class="col-sm-7">                                           
                               <form:input path="identificationdocname" id="identificationdocname" cssClass="form-control" 
                                           placeholder="Enter Identifiaction Document Name" required ="required"/>                                                                 
                          </div>
                    </div><!-- form-group -->  
                    
                    <div class="form-group">
                        <label data-toggle="tooltip" title="Enter Identifiaction Document Code (Without Space)" class="col-sm-3 tooltips control-label">Document Code 
                            <span class="asterisk">*</span></label>
                          <div class="col-sm-7">                                           
                               <form:input path="identificationdocdesc" id="identificationdocdesc" cssClass="form-control" 
                                           placeholder="Enter Identifiaction Document Code(Without Space)" required ="required"/>                                                                 
                          </div>
                    </div><!-- form-group -->  
                   
         
           <form:hidden path="delFlg" value="N"/>  

            <div class="form-group">
                            <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">SUBMIT</button>
                            <button type="reset" class="btn btn-default">RESET</button>
                        </div><!-- panel-footer -->
       
            
      </form:form>  
        </div>
      <c:if test="${!empty listAllIdentificationDoc}">  
          <div class="media-body">
             <h4>LIST OF IDENTIFICATION DOCUMENTS</h4>
          </div>
	     <table id="country-list" class="table table-striped table-bordered responsive">  
		 <thead>
	      <tr>  
	       <th>S/N</th>  
	       <th>COUNTRY </th>  
	       <th>NAME</th>  
               <th>CODE</th>  
	       <th></th>  
	       <th> </th>
	      </tr>  
            </thead>
            <tbody>
	      <c:forEach items="${listAllIdentificationDoc}" var="idenDoc" varStatus = "status">  
	       <tr>  
		<td><c:out value="${status.index + 1}"/></td>  
		<td><c:out value="${idenDoc.countryName}"/></td>  
                <td><c:out value="${idenDoc.identificationdocname}"/></td> 
		<td><c:out value="${idenDoc.identificationdocdesc}"/></td>                 
		<td align="center"><a href="identificationDoc/edit/${idenDoc.identificationDocId}">Edit</a></td>  
                <td><a href="identificationDoc/delete/${idenDoc.identificationDocId}">Delete</a></td>
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
	jQuery('#country-list').DataTable({
                    responsive: true
                });
                
        jQuery('#select-search-hide').select2({
                    minimumResultsForSearch: 15
                });        
    })
</script>
   