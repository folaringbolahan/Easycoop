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
  <div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
	     <div class="panel-body">	
                 <c:set var="votetypeid" value='<%= request.getParameter("votetype") %>' />
                 <form:form method="post" action="updateVoteSetup" commandName="votQuest">
                  <div class="form-group" >
                            <label class="col-sm-3 tooltips control-label" data-toggle="tooltip" title="question"> Question :</label>
                            <div class="col-sm-7">
                                <input name="question" class="form-control" value="${question.description}" id="question"  required="required" />
                            </div>
                            <div>

                            </div>
                        </div>  
                    	   
		
                 <c:forEach var="option" begin="0" items='${options}'>
                     <c:choose>
                         <c:when test="${votetypeid == '2'}">
                     
		   <div class="form-group">   
		       <form:label path="description" cssClass="col-sm-3 control-label">Option <c:out  value="${option.id}" /></form:label>
                       <input type="hidden" name="optionid" value="${option.id}">
			<div class="col-sm-5">
                       
			<input name="description" class="form-control" value="${option.description}" id="description" readonly="true" required="required" />		      
			</div>  
				   </div>
                          </c:when>  
                         <c:otherwise>
                      <div class="form-group">   
		       <form:label path="description" cssClass="col-sm-3 control-label">Option <c:out  value="${option.id}" /></form:label>
                         <input type="hidden" name="optionid" value="${option.id}">
			<div class="col-sm-5">
                       
			<input name="description" class="form-control" value="${option.description}" id="description"  required="required" />		      
			</div>  
				   </div>
                         </c:otherwise>
                  </c:choose>
                           </c:forEach>	
                   <input type="hidden" name="agmid" value="${question.agmid}"> 
                  <input type="hidden" name="id" value="${question.id}">
                  <form:hidden path="modifieddate"  value='<%=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())%>'/>
                   <form:hidden path="modifiedby" value="<%=request.getRemoteUser()%>"/>
             
           <div class="form-group">
		    <button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">SAVE</button>
		    
	    </div><!-- panel-footer -->       
            
      </form:form>  
      </div>
 
            </div><!-- col-md-6 -->
        </div>
    </div>
</div>
<!-- contentpanel -->

<%@ include file="includes/footer.jsp" %>  

   