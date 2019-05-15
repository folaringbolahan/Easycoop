<%@ include file="../includes/header.jsp" %> 

<c:set var="datePattern" value="dd-MMM-yyyy" />
<c:set var="amtPattern" value="###,###,###,###.00" />
<c:set var="numPattern" value="###,###,###,###" />
<c:set var="dateTimePattern" value="dd-MMM-yyyy hh:mm:ss a" />

<div class="media-body">
      <div style="float:left">
    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Member Enquiry</li>
    </ul>
    <h4></h4>
     </div>
     <%@include file="../includes/topright.jsp" %>
</div>

<div class="contentpanel">
    <div class="row">
        <div class="col-md-12">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-12">
                <!-- Nav tabs -->
                <ul class="nav nav-tabs nav-danger">
                    <c:forEach items="${pageData}" var="entry" >
                        <c:set var="theKey" value="${entry.key}"/>
                        <c:set var="theValue" value="${entry.value}"/>
                        <c:choose>
                        <c:when test="${theKey eq 'MEM'}">
            
                            <li class="active"><a href="#${theKey}" data-toggle="tab"><strong>${theValue['tab']}</strong></a></li>
                            
                        </c:when>
                        <c:otherwise>
                            <li class=""><a href="#${theKey}" data-toggle="tab"><strong>${theValue['tab']}</strong></a></li>
                        </c:otherwise>
                        </c:choose>
                        
                    </c:forEach>

                </ul>
                
                <!-- Tab panes -->
                <div class="tab-content tab-content-danger mb30">
                    
                      <c:forEach items="${pageData}" var="entry" >
    
                        <c:set var="theKey" value="${entry.key}"/>
                        <c:set var="theValue" value="${entry.value}"/>
                        <c:set var="thePage" value="${theValue['page']}"/>
                    
                        <c:choose>
                        <c:when test="${theKey eq 'MEM'}">
                            <div class="tab-pane active" id="${theKey}">
                                    <jsp:include page="${thePage}" />
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="tab-pane" id="${theKey}">
                                    <jsp:include page="${thePage}" />
                            </div>                       
                        </c:otherwise>
                        </c:choose>
                        
                        </c:forEach>
                    
                </div>  
                
            </div>
        </div>
    </div>
  </div >

<%@ include file="../includes/footer.jsp" %>  
<script>
    
</script>