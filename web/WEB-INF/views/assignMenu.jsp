<%@ include file="includes/header.jsp" %> 
<div class="media-body">
        <div style="float:left">
	    <ul class="breadcrumb">
		<li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
		<li><a href="#">Home</a></li>
		<li>Menu Management</li>
	    </ul>
	    <h4>Assign Menu</h4>
	</div>
	<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
  <script lanuage="Javascript">
	function doSubmit(frm){	
	    if(frm.companyid.value=="" || frm.companyid.value=="0"){alert("please specify cooperative"); frm.companyid.focus(); return;}
	    if(frm.branchid.value==""  || frm.branchid.value=="0"){alert("please select branch"); frm.branchid.focus(); return;}
	    if(frm.usergroup.value=="" || frm.usergroup.value=="0"){alert("please select user group"); frm.usergroup.focus(); return;}	    
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
				$('select#usergroup').change(
					//trying to reset values
					function(){
						//alert($(this).val());
						$('#menuitemscontainer').html('&nbsp;');
						$('#menuitemscontainer2').html('&nbsp;');
						
						$.getJSON('usergroupmenulist.do',{
							usergroup : $(this).val(),
							ajax : 'true'
						}, function(data){
							var  html='';
							var  html2='';
							var  html3='';
							var  html4='';
							
							var  itemCheck='';
							var  usergrpmdlsK=data.usergrpmdls;
							var  modulemenusK=data.modulemenus;
							var  reportmenusK=data.reportmenus;
							
							var  modulesK=data.modules;
							var  reportsK=data.reports;
							
							var  len1 = usergrpmdlsK==null?0:usergrpmdlsK.length;
							var  len2 = modulemenusK==null?0:modulemenusK.length;
							var  len3 = reportmenusK==null?0:reportmenusK.length;
							
							var  len4 = modulesK==null?0:modulesK.length;
							var  len5 = reportsK==null?0:reportsK.length;
							
							//alert("len1:="+len1);
							//alert("len2:="+len2);
							//alert("len3:="+len3);
							//alert("len4:="+len4);
							//alert("len5:="+len5);
							
							if(len2>0){								
								html='';
								var CURRENT_HEADER_1='';
								var LAST_HEADER_1='';

								for(var i = 0; i < len2; i++){
							    
								    if(len1>0){
									    for(var k = 0; k < len1; k++){
									       if(modulemenusK[i].menucode==usergrpmdlsK[k].menu){
										  itemCheck=' checked';
										  break;
									       }								       
									    }
								    }
								    
								    if(len4>0){
									    for(var mm = 0; mm < len5; mm++){
									       if(modulemenusK[i].module==modulesK[mm].code){
										  html4=modulesK[mm].description;

										  
										  if(CURRENT_HEADER_1==''  && LAST_HEADER_1==''){
										      CURRENT_HEADER_1=modulesK[mm].description;
										      html+= '<br/><b>'+ CURRENT_HEADER_1.toUpperCase() + '</b><br/>';
										  }else{
										      CURRENT_HEADER_1=modulesK[mm].description;
										  }									  
										  
										  
										  if(LAST_HEADER_1==''){
										     LAST_HEADER_1=modulesK[mm].description;										     
										  }else{
										     if(LAST_HEADER_1==CURRENT_HEADER_1){
										        //nothing happens
										     }else{
										        html+= '<br/><b>'+ CURRENT_HEADER_1.toUpperCase() + '</b><br/>';
										     }										  
										  }
										  
										  LAST_HEADER_1=CURRENT_HEADER_1;				  
										  break;
									       }								       
									    }
								    }
								    
								    //html += '<input type="checkbox" name="selectedMenus" value="'+ modulemenusK[i].menucode + '"'+itemCheck+' /> &nbsp; [' + modulemenusK[i].module + '] &nbsp; --> &nbsp; '+ modulemenusK[i].displaytext + '</br>';  
								    html += '<input type="checkbox" name="selectedMenus" value="'+ modulemenusK[i].menucode + '"'+itemCheck+' /> &nbsp; [' + html4 + '] &nbsp; --> &nbsp; '+ modulemenusK[i].displaytext + '</br>';  
								    itemCheck=' ';
								    html4='';
								}
							}
							
							//***************************** Report Menus *****************************
							//***************************** Report Menus *****************************
							if(len3>0){								
								html2='';
								html3='';
								var CURRENT_HEADER='';
								var LAST_HEADER='';

								for(var ii = 0; ii < len3; ii++){
								    if(len1>0){
									    for(var kk = 0; kk < len1; kk++){
									       
									       if(reportmenusK[ii].reportcode==usergrpmdlsK[kk].menu){
										  itemCheck=' checked';
										  break;
									       }								       
									    }
								    }
								    
								    if(len5>0){
									    for(var mm = 0; mm < len5; mm++){
									       if(reportmenusK[ii].reportgroup==reportsK[mm].code){
										  html3=reportsK[mm].description;
										  
										  if(CURRENT_HEADER==''  && LAST_HEADER==''){
										      CURRENT_HEADER=reportsK[mm].description;
										      html2 += '<br/><b>'+ CURRENT_HEADER.toUpperCase() + '</b><br/>';
										  }else{
										      CURRENT_HEADER=reportsK[mm].description;
										  }									  
										  
										  
										  if(LAST_HEADER==''){
										     LAST_HEADER=reportsK[mm].description;										     
										  }else{
										     if(LAST_HEADER==CURRENT_HEADER){
										        //nothing happens
										     }else{
										        html2 += '<br/><b>'+ CURRENT_HEADER.toUpperCase() + '</b><br/>';
										     }										  
										  }
										  
										  LAST_HEADER=CURRENT_HEADER;										  
										  break;
									       }								       
									    }
								    }

								    //html2 += '<input type="checkbox" name="selectedMenus2" value="'+ reportmenusK[ii].reportcode + '"'+itemCheck+' /> &nbsp; [' + reportmenusK[ii].reportgroup + '] &nbsp; --> &nbsp; '+ reportmenusK[ii].description + '</br>';  
								    html2 += '<input type="checkbox" name="selectedMenus2" value="'+ reportmenusK[ii].reportcode + '"'+itemCheck+' /> &nbsp; [' + html3 + '] &nbsp; --> &nbsp; '+ reportmenusK[ii].description + '</br>';  
								    itemCheck=' ';
								    html3='';
								}
							}
							
							$('#menuitemscontainer').html(html); 
							$('#menuitemscontainer2').html(html2); 
						});
					});
			      });
		      </script>
		      
	      <form:form method="post" action="saveMenuAssign.htm" commandName="usergrpmdl"> 		 
                 <form:hidden path="id" value="${usergrpmdl.id}"/>
                 
                 <div class="form-group">  
			  <form:label path="companyid" cssClass="col-sm-5 control-label">Select Cooperative:</form:label>  
			  <div class="col-sm-5">
			      <form:select id="companyid" path="companyid" cssClass="width300">
				<form:option value="">--select--</form:option>
				<c:forEach items="${companies}" var="item">  
				     <form:option value="${item.id}">${item.name}</form:option>
				</c:forEach>
			      </form:select>
			      <form:errors path="companyid" cssClass="error"></form:errors>
			  </div>  
		 </div> 
                 
		 <div class="form-group">
			  <form:label path="branchid" cssClass="col-sm-5 control-label">Select Branch</form:label>  
			  <div class="col-sm-5">
			      <form:select id="branchid" path="branchid" cssClass="width300">
				<c:forEach items="${branches}" var="item">  
				  <form:option value="${item.id}">${item.branchName}</form:option>
				</c:forEach>
			      </form:select>
			      <form:errors path="branchid" cssClass="error"></form:errors>
			  </div>  
		 </div>
		 
		 <div class="form-group">   
		  <form:label path="usergroup" cssClass="col-sm-5 control-label">User Groups:</form:label> 
		  <div class="col-sm-5">
		      <form:select id="usergroup" path="usergroup" cssClass="width300">			
			<c:forEach items="${usergroups}" var="item">  
			     <form:option value="${item.code}">${item.description}</form:option>
			</c:forEach>
			<form:option value="0" selected="true">--select--</form:option>
		      </form:select>
		   </div>  
	        </div> 
	 	<HR/>
	 
	 <%--   
	        <div class="form-group">   
		  <div class="col-sm-3"><label  cssClass="col-sm-3 control-label">Modules:</label></div> 
		  <div class="col-sm-5">
		       <select id="module">
			  <c:forEach items="${modules}" var="item">  
			     <option value="${item.code}">${item.description}</option>
			  </c:forEach>
			</select>
		   </div>  
	        </div>
	
	        <div class="form-group">   
		  <div class="col-sm-3"><label  cssClass="col-sm-3 control-label">Module Menus:</label></div>
		  <div id="menuitemscontainer" class="col-sm-5">
			<c:forEach items="${modulemenus}" var="item">  
			     <input type="checkbox" name="selectedMenus" value="${item.menucode}" /> &nbsp; [${item.module}] &nbsp; --> &nbsp; ${item.displaytext}</br>
			</c:forEach>
		   </div>  
	        </div> 
	        
	        <div class="form-group">  
		     <div class="col-sm-3"><label  cssClass="control-label">Module Menus:</label></div>
		     <div id="menuitemscontainer" class="col-sm-5">&nbsp;</div>
	        </div> 
	 --%>	 
	 
	 	<div class="form-group">  
		     <div class="col-sm-5"><label  cssClass="control-label"><B>VIEW MENUS</B>:</label></div>
		     <div class="col-sm-5"><label  cssClass="control-label"><B>REPORT MENUS</B>:</label></div>	     
	        </div> 
	        
		<div class="form-group">  
		     <div id="menuitemscontainer" class="col-sm-5">&nbsp;</div>
		     <div id="menuitemscontainer2" class="col-sm-5">&nbsp;</div>
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

   