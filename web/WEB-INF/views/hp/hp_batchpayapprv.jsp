<%@ include file="../includes/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Hire Purchase Repayments for Approval</li>
        </ul>
        <h4>Approve Hire Purchase Repayments</h4>
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
                    <th class=" tooltips"  data-original-title="Click to Sort" data-toggle="tooltip" data-placement="top">Upload Date</th>
                    <th>Uploader</th>
                    <th>File Sum</th>
                    <!--<th>Procd. Sum</th> -->
                    <th>Recs Details</th>
                    <!--<th>Success</th>
                    <th>Failure</th>-->
                    <th>Filename</th>
                    <th>View Details</th>
                    <th>Action</th>
                    <!--<th></th>-->
                   
                </tr>
            </thead>
            <tbody style=" font-size: 11px; padding: 0px 0px 0px 0px;margin: 0px 0px 0px 0px">
                 <c:forEach var="accttxn4apprv" items="${hpreptxns4apprv}" >
                    
                  <tr>
                  <td>${accttxn4apprv.id}</td>    
                  <td>${accttxn4apprv.batchId}</td>
                  <td>${accttxn4apprv.uploadDate}</td>
                  <td>${accttxn4apprv.uploadedBy}</td>
                  <td>
                     <c:if test="${accttxn4apprv.filesum!=accttxn4apprv.processedsum}">
                         File Sum : <font color="red"> <fmt:formatNumber type="number" maxFractionDigits="2" value="${accttxn4apprv.filesum}" /></font>,
                         Processed Sum : <fmt:formatNumber type="number" maxFractionDigits="2" value="${accttxn4apprv.processedsum}" />
                     </c:if>
                     <c:if test="${accttxn4apprv.filesum==accttxn4apprv.processedsum}">
                      File Sum : <fmt:formatNumber type="number" maxFractionDigits="2" value="${accttxn4apprv.filesum}" />, 
                      Processed Sum : <fmt:formatNumber type="number" maxFractionDigits="2" value="${accttxn4apprv.processedsum}" />
                     </c:if> 
                   </td>
                  <!--<td>${accttxn4apprv.processedsum}</td>-->
                  <td>
                      <c:if test="${accttxn4apprv.totalRecords!=accttxn4apprv.successCount+accttxn4apprv.failedCount}">
                         Total : <font color="red"> ${accttxn4apprv.totalRecords}</font>, Success : ${accttxn4apprv.successCount} , Failed : ${accttxn4apprv.failedCount}
                     </c:if>
                     <c:if test="${accttxn4apprv.totalRecords==accttxn4apprv.successCount+accttxn4apprv.failedCount}">
                      Total : ${accttxn4apprv.totalRecords} , Success : ${accttxn4apprv.successCount} , Failed : ${accttxn4apprv.failedCount}</td>
                     </c:if>
                  <!-- <td>${accttxn4apprv.successCount}</td>
                  <td>${accttxn4apprv.failedCount}</td> -->
                  <td>${accttxn4apprv.uploadFilename}</td>
                 
                  <td><a href="hp_gentblview.htm?id=${accttxn4apprv.batchId}&AMP;nm=1">View Recs. Ok</a> <br>
                      <a href="hp_gentblview.htm?id=${accttxn4apprv.batchId}&AMP;nm=2">View Failed Recs.</a> &nbsp;&nbsp;&nbsp;
                  </td>
                  <td><a href="hp_cfpfreptxn4apprv.htm?id=${accttxn4apprv.batchId}">Approve</a> &nbsp;&nbsp;&nbsp; <br>
                  <a href="hp_cfpfreptxn4apprvrj.htm?id=${accttxn4apprv.batchId}" onclick="return confirm('Reject Batch file list with reference - ${accttxn4apprv.batchId} Continue?')" type="submit" class="buttons" value="Go">Reject</a></td>
                 </tr>
                </c:forEach>
                
            </tbody>
        </table>
               </div>
          
      </div>       
            <%@ include file="../includes/footer.jsp" %>  