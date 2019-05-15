<%-- 
    Document   : gl_pendingacctlist
    Created on : Dec 23, 2015, 1:28:45 PM
    Author     : Olakunle Awotunbo
--%>


<%@ include file="../includes/header.jsp" %> 
<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="#">Home</a></li>
            <li>Bulk Upload Verification</li>
        </ul>
        <h4>Verify Uploads for Product Account Creation</h4>
    </div>
    <%@include file="../includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
<div class="contentpanel">
    <div class="row">
        <div class="col-md-12">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-12">
                <div class="panel-body">
                    <c:set var="now" value="<%=new java.util.Date()%>"/>

                    
                    <c:if test="${!empty penVeriList}">  
                        <c:set var="count" scope="page" value="0"/>
                        <div class="media-body">
                            <h4>MEMBER UPLOAD - VIEW </h4>
                        </div>

                        <table id="schedule-list" class="table table-striped table-bordered responsive">  
                            <thead>
                                <tr>  
                                    <th>S/N</th>  
                                    <th>REFERENCE NO</th>  
                                    <th>FILE NAME</th>
                                    <th>UPLOAD DATE</th>
                                    <th>PASSED</th>    
                                    <th>FAILED</th>    
                                </tr>  
                            </thead>
                            <tbody>
                                <c:forEach items="${penVeriList}" var="item" varStatus = "status">  
                                    <c:set var="count" scope="page" value="${count+1}" />
                                    <tr>  
                                        <td><c:out value="${status.index + 1}"/></td>  
                                        <td>
                                            <a href="<%=request.getContextPath()%>/gl/gl_approveBulkAcct/${item.referenceNumber}.htm">${item.referenceNumber}</a>   
                                        </td>  
                                        <td>${item.fileName}</td>
                                        <td><fmt:formatDate pattern="dd/MM/yyyy" value="${item.processedDate}"/></td>                                          
                                        <td>

                                            <c:if test="${item.userId == currrentuserServicex.curruser.userId}">
                                                <font color="red"><div data-toggle="tooltip" title="A different user is required to approve this Upload" cssClass="tooltips">NOT ALLOWED</div></font>
                                            </c:if>
                                            <c:if test="${item.userId ne currrentuserServicex.curruser.userId}">
                                                <a href="<%=request.getContextPath()%>/gl/gl_approveBulkAcct/${item.referenceNumber}.htm">APPROVE</a>

                                            </c:if>
                                        </td>
                                        <td>
                                            <a href="<%=request.getContextPath()%>/gl/gl_viewfailedrecords/${item.referenceNumber}.htm">VIEW</a>
                                        </td> 

                                    </tr>  
                                </c:forEach>  
                            </tbody>
                        </table>  
                    </c:if> 

                    <c:if test="${empty penVeriList}">
                        <div class="media-body  error">
                            <h4>NO CURRENT UPLOAD </h4>
                        </div>
                    </c:if>
                </div>
            </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->

<%@ include file="../includes/footer.jsp" %>  
<script>
    $(document).ready(function() {
        jQuery('select').select2({
            minimumResultsForSearch: -1
        });

        jQuery('#schedule-list').DataTable({
            responsive: true
        });
    })
</script>
