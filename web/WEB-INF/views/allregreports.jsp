<%@ include file="includes/header.jsp" %>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="index.htm">Home</a></li>
            <li>Report</li>
        </ul>
        <h4>Reports</h4>
    </div>
    <%@include file="includes/topright.jsp" %>
    
</div>
</div><!-- media -->
</div><!-- pageheader -->



<div class="contentpanel">

         <c:set var="prevgrp" value=""/>  
                <c:forEach var="report" items="${reportsdet}" >
               
                   
                    <c:if test ="${prevgrp==''}">
                     <div class="col-md-6">  
                       <table  id="data-list2" class="table table-dark mb30" >       
                           <thead class="">
                               <tr>
                                   <th>${report.reportGroupdesc} &nbsp;Reports</th>
                                   <!-- <th>Report Group</th>-->
                                   <th></th>
                                   <th></th>
                                   <th></th> 
                               </tr>
                           </thead>
                           <tbody>
                    </c:if>
                    
                    <c:if test ="${(prevgrp!='') && (prevgrp!=report.reportGroupCode)}">
                        
                         </tbody>
                        </table>
                    </div>
                      <div class="col-md-6">  
                       <table  class="${report.type}" >       
                           <thead class="">
                               <tr>
                                   <th>${report.reportGroupdesc} &nbsp;Reports</th>
                                    <!--<th>Report Group</th>-->
                                   <th></th>
                                    <th></th>
                                   <th></th> 
                               </tr>
                           </thead>
                           <tbody>
                    </c:if>
                    
                    <tr>
                        <c:set var="rptpara" value=""/>
                        <c:if test ="${(report.rangeCriteria!='N')}">
                           <c:set var="rptpara" value="/para?pr=${report.rangeCriteria}"/>
                        </c:if>
                        <td>${report.description}</td> 
                        <c:if test ="${(report.reportGroupCode!='DW')}">
                         <!--<td>${report.reportGroupCode}</td>-->
                         <td><a href="${report.reportFileName}/html${rptpara}" target="_blank">Html</a></td>
                         <td><a href="${report.reportFileName}/pdf${rptpara}" target="_blank">Pdf</a></td>
                         <td><a href="${report.reportFileName}/xls${rptpara}">Excel Download</a></td>
                        </c:if>
                        <c:if test ="${(report.reportGroupCode=='DW')}">
                         <td><a href="${report.reportFileName}/csv${rptpara}">CSV Download</td>
                        </c:if> 
                    </tr>
                    <c:set var="prevgrp" value="${report.reportGroupCode}"/>  
                </c:forEach>

            </tbody>
        </table>
    </div><!-- col-md-10 -->
</div>
</div>


<!-- contentpanel -->
 
<%@ include file="includes/footer.jsp" %>  