<%@ include file="includes/header.jsp" %>  
<div class="media-body">
     <div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>App Settings Management</li>
    </ul>
    <h4>Manage App Settings</h4>
</div>
<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<div class="contentpanel">
    <div class="row">
        <div class="col-md-10">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-15">
	       <div class="panel-body">
                 <c:set var="now" value="<%=new java.util.Date()%>" />
		      
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui.js"></script>
		      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-i18n.min.js"></script> 
                      
                     <%--
		      <script type="text/javascript">
			      $(document).ready(function(){	        
				$('select#companyId').change(		        
					function(){
						alert($(this).val());
						$.getJSON('branchesAjaxList.do', {
							companyId : $(this).val(),
							ajax : 'true'
						}, function(data) {
							var len = 0;
							var html ='';

							if(data!=null){
							   len=data.length;
							}

							alert("len:="+len);
							if(len>0){
								for ( var i = 0; i < len; i++) {
								    html += '<option value="' + data[i].id + '">' + data[i].branchName + '</option>';
								}
							}

							alert(html);					
							$('select#branchId').html(html);
						});
					});
			      });
		      </script>
        --%>        
		      
		      <script type="text/javascript">
			      $(document).ready(function(){	        
				$('select#computationType').change(		        
					function(){
					        //alert("value=:"+$(this).val());						
						if($(this).val()=="STATIC"){						   
						   $("#formula1").hide();
						   $("#valueGroup").show();
						}else if($(this).val()=="FORMULA"){						   
						   $("#valueGroup").hide();
						   $("#formula1").show();
						}
						
					});
			      });
		      </script>
		      
		      <form:form method="post" action="saveAppConfig.htm" commandName="appConfig">
			   <%--
				   <div class="form-group">  
					  <form:label path="id" cssClass="col-sm-3 control-label">ID:</form:label>  
					  <div class="col-sm-8">
					    <form:input path="id" value="${appConfig.id}" readonly="true"/>
					  </div>  
				   </div>  
			   --%>
			   
			   <div class="form-group">   
				  <form:label path="companyId" cssClass="col-sm-3 control-label">Cooperative *:</form:label> 
				  <div class="col-sm-8">
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
				  <form:label path="branchId" cssClass="col-sm-3 control-label">Branch *:</form:label>
				  <div class="col-sm-2">
				      <form:select id="branchId" path="branchId"  cssClass="width300">
					 <form:option value="">--select--</form:option>
					 <c:forEach items="${branches}" var="item">  
						<form:option value="${item.id}">${item.branchName}</form:option>
					 </c:forEach>
				      </form:select>
				  </div>  
			  </div>>
			
			  <div class="form-group">   
				  <form:label path="configType" cssClass="col-sm-3 control-label">Type *:</form:label> 
				  <div class="col-sm-8">
				      <form:select id="configType" path="configType"  cssClass="width300">
				     	   <form:option value="">--select--</form:option>
				     	   <form:option value="LOAN">LOAN</form:option>
				      </form:select>
				      <form:errors path="configType" cssClass="error"></form:errors>
				  </div>  
			   </div> 
			   
			   <div class="form-group">   
				  <form:label path="configName" cssClass="col-sm-3 control-label">Name *:</form:label> 
				  <div class="col-sm-8">
				      <form:select id="configName" path="configName"  cssClass="width300">
					   <form:option value="">--select--</form:option>
					   <form:option value="CONCURRENT LOAN">CONCURRENT LOAN</form:option>
					   <form:option value="RUNNING LOAN SUM">RUNNING LOAN SUM</form:option>
					   <form:option value="LOAN GUARANTORS">LOAN GUARANTORS</form:option>
					   <form:option value="LENGTH OF STAY">LENGTH OF STAY</form:option>
				      </form:select>
				      <form:errors path="configName" cssClass="error"></form:errors>
				  </div>  
			   </div> 
			   
			   <div class="form-group">   
				  <form:label path="computationType" cssClass="col-sm-3 control-label">Computation Type:</form:label> 
				  <div class="col-sm-8">
				      <form:select id="computationType" path="computationType" cssClass="width300">
					   <form:option value="">--select--</form:option>
					   <form:option value="STATIC">STATIC VALUE</form:option>
					   <form:option value="FORMULA">DERIVED / FORMULA</form:option>
				      </form:select>
				      <form:errors path="configType" cssClass="error"></form:errors>
				  </div>  
			   </div> 

			   <div id="formula1" class="form-group">   
				  <form:label path="formulaValue" cssClass="col-sm-3 control-label">Formula:</form:label> 
				  <div class="col-sm-8">
				      <form:select id="formulaValue" path="formulaValue" cssClass="width300">
					   <form:option value="">--select--</form:option>
					   <form:option value="SYM001">MEMBER SAVINGS</form:option>
				      </form:select>
				      -
				      <form:select id="operand" path="operand">
					   <form:option value="">--select--</form:option>
					   <form:option value="OP001">MULTIPLY BY</form:option>
					   <form:option value="OP002">DIVIDES BY</form:option>
				      </form:select>
				      -
				      <form:input path="multiplier" size="5" value="${appConfig.multiplier}"/>
				  </div>  
			   </div> 
			   
			   <div id="valueGroup">			   
				   <div class="form-group">  
					  <form:label path="configMinValue" cssClass="col-sm-3 control-label">Min Value:</form:label> 
					  <div class="col-sm-8">
					     <form:input path="configMinValue" value="${appConfig.configMinValue}"/>
					     <form:errors path="configMinValue" cssClass="error"></form:errors>
					  </div>  
				   </div> 

				   <div class="form-group">   
					  <form:label path="configMaxValue" cssClass="col-sm-3 control-label">Max Value:</form:label> 
					  <div class="col-sm-8">
					     <form:input path="configMaxValue" value="${appConfig.configMaxValue}"/>
					     <form:errors path="configMaxValue" cssClass="error"></form:errors>
					  </div>  
				   </div> 
			   </div>
			   
                           <input type="hidden" name="ACTION_ID" value="1">
			   <form:hidden path="active" value="Y"/>
			   <form:hidden path="deleted" value="N"/> 

			   <form:hidden path="createdBy" value="admin"/>
			   <form:hidden path="lastModifiedBy" value="admin"/>

			   <form:hidden path="creationDate"  value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>
			   <form:hidden path="lastModificationDate" value='<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>'/>

			   <div class="form-group">
				    <button class="btn btn-danger mr5" type="submit">SUBMIT</button>
				    <button type="reset" class="btn btn-default">RESET</button>
			   </div><!-- panel-footer -->       
            
             </form:form>  
        </div>
        
      <c:if test="${!empty appConfigurations}">  
          <h4>LIST OF APP SETTINGS</h4>  
	     <table id="appconfig-list" class="table table-striped table-bordered responsive">  
	      <thead>
	      <tr>  
	       <th>ID</th>  
	       <th>Name</th>  
	       <th>Type</th>  
	       <th>Derivation</th> 
	       <th>Min</th>  
	       <th>Max</th> 
	       <th>Action </th> 
	      </tr>  
	      </thead>
	      <c:forEach items="${appConfigurations}" var="appConfig">  
	       <tr>  
		<td><c:out value="${appConfig.id}"/></td>  
		<td><c:out value="${appConfig.configName}"/></td>  
		<td><c:out value="${appConfig.configType}"/></td>  
		<td><c:out value="${appConfig.computationType}"/></td>  
		<td><c:out value="${appConfig.configMinValue}"/></td> 
		<td><c:out value="${appConfig.configMaxValue}"/></td>
		<td align="center"><a href="editAppConfig.htm?id=${appConfig.id}">Edit</a></td>  
	       </tr>  
	      </c:forEach>  
	     </table>  
    </c:if>  
            </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->

<%@ include file="includes/footer.jsp" %>  
<script>
    $(document).ready(function(){
         $("#formula1").hide();
         $("#valueGroup").hide();
         
         jQuery('select').select2({
                    minimumResultsForSearch: -1
                });
			jQuery('#appconfig-list').DataTable({responsive: true });
		        //jQuery('#appconfig-list').DataTable({responsive: true });

               })
</script>
   