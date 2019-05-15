<%@ include file="../includes/header.jsp" %>  

<div class="media-body">
  <div style="float:left">
    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="index.htm">Home</a></li>
        <li>Accounts Imports Verification Report</li>
    </ul>
    <h4>Accounts Upload Report</h4>
  </div>  
    <%@include file="../includes/topright.jsp" %>
</div>

</div><!-- media -->
</div><!-- pageheader -->

<div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <div class="col-md-12">
	      <div class="panel-body">
	      	      
		          
               </div>
          </div><!-- col-md-6 -->
      </div>
    </div>
                              
    <form:form method="post" action="gl_accountuplitmadd.htm" modelAttribute="batchfilereport">  
                <div class="panel-body">
                    <div class="form-group">
                        <form:label path="batchId" cssClass="col-sm-1 control-label tooltips" >Batch Id: </form:label>
                        
                       <div class="col-sm-6">
                           <form:input path="batchId" size="30" id="batchId" readonly="true"/>
                       </div>
                    </div><!-- form-group -->
                    
                    
                     <div class=" col-md-10">
        <table  id="data-list" class="table table-striped table-bordered responsive" >       
            <tbody>
                <tr>
                    <td><strong>Summary: ${batchfilereport.uploadStatus}</strong></td>
                </tr>
                 <tr>
                     <td>${batchfilereport.uploadFilename}</td>    
                 </tr>
           </tbody>
        </table>
    </div>   
                </div><!-- panel-body -->
               <!-- panel-footer -->

            </form:form>  
                              
</div><!-- contentpanel -->


<%@ include file="../includes/footer.jsp" %>  




   