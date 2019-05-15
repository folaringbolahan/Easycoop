<%@ include file="../includes/header.jsp" %>
<div class="media-body">
      <div style="float:left">
    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Member Single Batch Upload</li>
    </ul>
    <h4>Member Single Batch Upload</h4>
     </div>
     <%@include file="../includes/topright.jsp" %>
</div>

<div class="contentpanel">
    <div class="row">
        <div class="col-md-10">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
	     <div class="panel-body">	
	     
	        <form:form commandName="batchUploadFile" method="post" enctype="multipart/form-data" action="singleSave.htm">
	            
                    <div class="form-group">
                        <label class="col-sm-4 control-label tooltips" data-original-title="Member's Religion" data-toggle="tooltip" data-placement="left">Upload Type *</label>
                         <div class="col-sm-4">
                        <form:select path="batchUploadType.batchUploadTypeId"  class="width200p" data-placeholder="Choose One">
                          <form:option value="0" label="--Please Select Upload Type--" />
                                <form:options items="${referenceList.typeList}" itemLabel="uploadTypeName" itemValue="batchUploadTypeId" />
                          </form:select>
                              <div class="error">
                                                <form:errors path="batchUploadType" />
                                              </div>
                        </div>
                    </div><!-- form-group -->
                    <div class="form-group">
                        <label class="col-sm-4 control-label tooltips" data-original-title="Member's Religion" data-toggle="tooltip" data-placement="left">Record Count *</label>
                         <div class="col-sm-4">
                            <form:input path="batchRecordCount" type="text"/>
                              <div class="error">
                                                <form:errors path="batchRecordCount" />
                              </div>
                        </div>
                    </div><!-- form-group -->
                    <div class="form-group">  
                          <form:label for="file" path="file" cssClass="col-sm-3 control-label">Select file</form:label>

                          <div class="col-sm-8">
                            <form:input path="file" type="file"/>
                         </div> 
                    </div>	
           		     
		     <form:hidden path="createdBy" value="${batchUploadFile.createdBy}"/>
		     <form:hidden path="createdDate"  value="${batchUploadFile.createdDate}"/>
                     
                     
                     <div class="form-group">
			                  
                           <button class="btn btn-danger mr5" type="submit" name="action" value="CREATE-BATCH" > UPLOAD BATCH </button> 
		           <button class="btn btn-danger mr5" type="reset" >RESET</button>
				    
			    </div><!-- panel-footer -->    
            
      		</form:form>  
             </div>   

            </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->
<%@ include file="../includes/footer.jsp" %>  

   
