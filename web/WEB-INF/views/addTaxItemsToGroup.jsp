<%@ include file="includes/header.jsp" %> 
<div class="media-body">
  <div style="float:left">
    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Tax Element Management</li>
    </ul>
    <h4>Assign Tax Elements To Group</h4>
</div>
<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
  <script lanuage="Javascript">
	function doSubmit(frm){	
	    if(frm.companyId.value=="" || frm.companyId.value=="0"){alert("please specify cooperative"); frm.companyId.focus(); return;}
	    if(frm.branchId.value==""  || frm.branchId.value=="0"){alert("please select branch"); frm.branchId.focus(); return;}
	    if(frm.taxGroupId.value=="" || frm.taxGroupId.value=="0"){alert("please select tax group"); frm.taxGroupId.focus(); return;}	    
	    frm.submit();
	}	  
  </script>
  
  <div class="contentpanel">
    <div class="row">
        <div class="col-md-10">
            <div class="col-md-15">
		<div class="panel-body">
                 <c:set var="now" value="<%=new java.util.Date()%>" />
                     
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-i18n.min.js"></script> 
		
		      <script type="text/javascript">
			      $(document).ready(function(){	        
				$('select#taxGroupId').change(
					//trying to reset values
					function(){
						//alert($(this).val());
						$('#menuitemscontainer').html('&nbsp;');
						
						$.getJSON('taxgroupitemlist.do',{
							taxGroupId : $(this).val(),
							ajax : 'true'
						}, function(data){
							var  html='';
							var  itemCheck='';
							
							var  taxGroupItemsK=data.taxGroupItems;
							var  taxitemsK=data.taxitems;
							
							var  len1 = taxGroupItemsK==null?0:taxGroupItemsK.length;
							var  len2 = taxitemsK==null?0:taxitemsK.length;
						
							//*****************************Tax Items************************//
							//*****************************Tax Menus************************//
							if(len2>0){								
								html='';

								for(var ii = 0; ii < len2; ii++){
								    if(len1>0){
									    for(var kk = 0; kk < len1; kk++){
									       
									       if(taxitemsK[ii].id==taxGroupItemsK[kk].taxId){
										  itemCheck=' checked';
										  break;
									       }								       
									    }
								    }

								    html += '<input type="checkbox" name="selectedTaxIds" value="'+ taxitemsK[ii].id + '"'+itemCheck+' /> &nbsp; [' + taxitemsK[ii].taxName + '] &nbsp; --> &nbsp; '+ taxitemsK[ii].taxCode + '</br>';  
								    itemCheck=' ';
								}
							}
							
							$('#menuitemscontainer').html(html); 
						});
					});
			      });
		      </script>
		      
	      <form:form method="post" action="saveTaxGroupItem.htm" commandName="taxGroupItem"> 		 
                 <form:hidden path="id" value="${taxGroupItem.id}"/>
                 
                 <div class="form-group">  
			  <form:label path="companyId" cssClass="col-sm-5 control-label">Select Cooperative *:</form:label>  
			  <div class="col-sm-5">
			      <form:select id="companyId" path="companyId" cssClass="width300">
				<form:option value="">--select--</form:option>
				<c:forEach items="${companies}" var="item">  
				     <form:option value="${item.id}">${item.name}</form:option>
				</c:forEach>
			      </form:select>
			      <form:errors path="companyId" cssClass="error"></form:errors>
			  </div>  
		 </div> 
                 
		 <div class="form-group">
			  <form:label path="branchId" cssClass="col-sm-5 control-label">Select Branch *:</form:label>  
			  <div class="col-sm-5">
			      <form:select id="branchId" path="branchId" cssClass="width300">
				<c:forEach items="${branches}" var="item">  
				  <form:option value="${item.id}">${item.branchName}</form:option>
				</c:forEach>
			      </form:select>
			      <form:errors path="branchId" cssClass="error"></form:errors>
			  </div>  
		 </div>
		 
		 <div class="form-group">   
		  <form:label path="taxGroupId" cssClass="col-sm-5 control-label">Tax Groups *:</form:label> 
		  <div class="col-sm-5">
		      <form:select id="taxGroupId" path="taxGroupId">			
			<c:forEach items="${taxgroups}" var="item">  
			     <form:option value="${item.id}">${item.code} -> ${item.description}</form:option>
			</c:forEach>
			<form:option value="0" selected="true">--select--</form:option>
		      </form:select>
		   </div>  
	        </div> 
	 	<HR/>	 	
       
	        <div class="form-group">  
		     <div class="col-sm-5"><label  cssClass="control-label">Select Tax Items *:</label></div>
		     <div id="menuitemscontainer" class="col-sm-5">&nbsp;</div>
	        </div> 
	        
	        <HR/>
           
		<div class="form-group">
		    <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">SUBMIT</button>
		    <button type="reset" class="btn btn-default">RESET</button>
		</div>          
             </form:form>  
            </div>
           </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->
<%@ include file="includes/footer.jsp" %>  

   