<%@ include file="../includes/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Hire Purchase Repayment Schedule Template</li>
        </ul>
        <h4>Hire Purchase Repayment Schedule Template</h4>
    </div>
    <%@include file="../includes/topright.jsp" %>
    
</div>
</div><!-- media -->
</div><!-- pageheader -->



<div class="contentpanel">

<p>
        
           <!-- form section above and list section below   -->
           <div class="col-md-12">
               
                <form:form method="post" action="" modelAttribute="approveAcct"> 
                 <p>  
                   <button class="btn btn-success mr5" id="downloadexcel" name="action" onClick="javascript:form.action = '<%=request.getContextPath()%>/hp/hp_downldsdtemp.htm'" value="Download">DOWNLOAD FILE</button>
                 </p> 
               
               <table  id="data-list" class="table table-striped table-bordered responsive"  >       
                <thead style=" font-size: 11px;padding: 0px 0px 0px 0px;margin: 0px 0px 0px 0px">
                <tr align="left">
                    <!--<th align="left">Id</th>-->
                    <!--<th>Code</th>-->
                    <th>Reference</th>
                    <th class=" tooltips"  data-original-title="Click to Sort" data-toggle="tooltip" data-placement="top">Account No</th>
                    <th>Amount</th>
                    <th>Repayment Date</th>
                   
                </tr>
            </thead>
            <tbody style=" font-size: 11px; padding: 0px 0px 0px 0px;margin: 0px 0px 0px 0px">
                 <c:forEach var="accttxn4apprv" items="${hpreptxns4apprv}" >
                    
                  <tr>
                  <td>${accttxn4apprv.refid}</td>
                  <td>${accttxn4apprv.accountNo}</td>
                  <td>${accttxn4apprv.instalment}</td>
                  <td><fmt:formatDate pattern="dd-MM-yyyy" value="${accttxn4apprv.rpymtdate}"/></td>
                 
                  
                 </tr>
                </c:forEach>
                
            </tbody>
        </table>
            </form:form>       
               </div>
          
      </div>       
            <%@ include file="../includes/footer.jsp" %>  