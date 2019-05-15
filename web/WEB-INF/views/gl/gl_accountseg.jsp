<%@ include file="../includes/header.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Account Segments</li>
        </ul>
        <h4>Setup Account Segments</h4>
    </div>
    
        
</div>
</div><!-- media -->
</div><!-- pageheader -->
<div class="contentpanel">

    <div class="row col-md-10">
        <!-- CONTENT GOES HERE -->  
        <div class="panel-body">
            <!-- form starts -->
                 <form:form method="post" action="gl_upaccountseg" modelAttribute="accountsegmentdetlist">  
               <div class="panel-body">
       <!--     <table width="50%" border="0" cellspacing="7" cellpadding="7" >       
            <thead>
                <tr>
                    <th colspan="5"><strong>Setup Account Segments</strong></th>
                    
                </tr>
                <tr>
                    <th><strong>No.</strong></th>
                    <th><strong>Description</strong></th>
                    <th><strong>Code</strong></th>
                    <th><strong>Length</strong></th>
                    <th><strong>Use</strong></th>
                </tr>
            </thead>
            
             <tbody> --->
       <div class="form-group">
                   <div class="col-sm-1 control-label">
                      <strong>No.</strong>
                  </div>
                  <div class="col-sm-5 control-label">
                      <strong>Description</strong>
                  </div>
                  <div class="col-sm-1 control-label">
                      <strong>Code</strong>
                  </div>
                  <div class="col-sm-1 control-label">
                      <strong>Length</strong>
                  </div>
                  <div class="col-sm-1 control-label">
                      <strong>Use</strong>
                  </div>
       </div>          
                <c:set var="xeven" value="0"/>
                <c:forEach var="accountseg" items="${accountsegmentdetlist.accsegdets}" varStatus="counter" >
                   
                <div class="form-group">
                   <div class="col-sm-1 control-label">
                      ${counter.count}
                  </div>    
                 
                      <c:set var="getpredf" value="${accountseg.predefined}"/>
                      <c:choose>
                        <c:when test="${getpredf==true}">
                            <c:set var="toenable" value="true"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="toenable" value="false"/>
                        </c:otherwise>
                       </c:choose> 
                     
                     <!-- <input name="accountsegs[${counter.index}].name" value="${accountseg.name}">-->
                     <div class="col-sm-5">   
                      <form:input  path="accsegdets[${counter.index}].name" id="name${counter.index}" size="30" readonly="${toenable}" />
                      <div class="error">
                       <form:errors path="accsegdets[${counter.index}].name" /> 
                      </div> 
                     </div>   
                 <div class="col-sm-1">
                      <form:input path="accsegdets[${counter.index}].type" id="type${counter.index}" size="1" readonly="true" />
                       <div class="error">
                       <form:errors path="accsegdets[${counter.index}].type"  />
                       </div>
                  </div>
                  <div class="col-sm-1">
                      <form:input path="accsegdets[${counter.index}].length" id="length${counter.index}" size="1" />
                       <div class="error">
                       <form:errors path="accsegdets[${counter.index}].length"  />
                       </div>
                  </div>
                  <div class="col-sm-1">
                      <form:checkbox path="accsegdets[${counter.index}].inuse" id="inuse${counter.index}"  />
                       
                  </div>
                   <div class="col-sm-1">
                       <form:hidden path="accsegdets[${counter.index}].segmentid" id="segmentid${counter.index}"  />
                       
                  </div>
                 </div>     
                </c:forEach>
         <!--      </tbody> 
            </table> -->
             </div><!-- panel-body -->
                <div>
                    <input type="submit" class="btn btn-danger mr5" value="Save"/>
                </div><!-- panel-footer -->
                </form:form>
            </div>
        <!-- End of panel-body -->
    </div> 
                
                
  </div>     
       <!-- contentpanel -->
            
            <%@ include file="../includes/footer.jsp" %>  