<%@ include file="../includes/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Journal Postings for Approval</li>
        </ul>
        <h4>Pending Journal Postings</h4>
    </div>
    <%@include file="../includes/topright.jsp" %>
    
</div>
</div><!-- media -->
</div><!-- pageheader -->



<div class="contentpanel">

<p>
        
           <!-- form section above and list section below   -->
           <div class="col-md-12">
               <table  id="data-list" class="table table-striped table-bordered responsive"  >       
                <thead style=" font-size: 11px;padding: 0px 0px 0px 0px;margin: 0px 0px 0px 0px">
                <tr align="left">
                    <!--<th align="left">Id</th>-->
                    <!--<th>Code</th>-->
                    <th>No.</th>
                    <th>Reference</th>
                    <th class=" tooltips"  data-original-title="Click to Sort" data-toggle="tooltip" data-placement="top">Post Date</th>
                    <th>Posted By</th>
                    <th>Journal Summary</th>
                    <!--<th>Procd. Sum</th> -->
                    <!--<th>Recs Details</th>-->
                    
                    <!--<th>Filename</th>-->
                    <!--<th>Corr. Acc.</th>-->
                    <th>Entry Date</th>
                    <th>View Details</th>
                    <th>Action</th>
                    <!--<th></th>-->
                   
                </tr>
            </thead>
            <tbody style=" font-size: 11px; padding: 0px 0px 0px 0px;margin: 0px 0px 0px 0px">
                 <c:forEach var="accttxn4apprv" items="${accttxns4apprv}" >
                    
                  <tr>
                  <td>${accttxn4apprv.id}</td>    
                  <td>${accttxn4apprv.accountno}</td>
                  <td>${accttxn4apprv.txnDatestr}</td>
                  <td>${accttxn4apprv.uploadedBy}</td>
                  <td>
                      Debit : <font color="red"> <fmt:formatNumber type="number" maxFractionDigits="2" value="${accttxn4apprv.filesum}" /></font>, Credit : <font color="red"><fmt:formatNumber type="number" maxFractionDigits="2" value="${accttxn4apprv.processedsum}" /></font>
                     . Journal Items : <font color="red"> <fmt:formatNumber type="number" maxFractionDigits="0" value="${accttxn4apprv.totalRecords}" /></font>
                   </td>
                 
                  <td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${accttxn4apprv.uploadDate}" /></td>
                  <td><a href="gl_gentblview.htm?id=${accttxn4apprv.batchId}&AMP;nm=4">View Records</a> <br>
                  </td>
                  <td>
                      
                      <c:if test="${accttxn4apprv.uploadFilename!='donotshow'}"> 
                        <c:if test="${accttxn4apprv.failedCount==0}"> 
                          <a href="gl_cfpfjrnltxn4apprv.htm?id=${accttxn4apprv.batchId}">Approve</a>
                           &nbsp;&nbsp;&nbsp; <br>
                        </c:if>
                      
                      <a href="gl_cfpfjrnltxn4apprvrj.htm?id=${accttxn4apprv.batchId}" onclick="return confirm('Reject Journal with reference - ${accttxn4apprv.batchId} Continue?')" type="submit" class="buttons" value="Go">Reject</a>
                      </c:if>
                      &nbsp;
                     </td> 
                  </tr>
                </c:forEach>
                
            </tbody>
        </table>
               </div>
          
      </div>       
            <%@ include file="../includes/footer.jsp" %>  