<%@ include file="../includes/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Hire Purchase Approved Pending Posting</li>
        </ul>
        <h4>Approved Hire Purchase Awaiting Posting</h4>
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
                    <th class=" tooltips"  data-original-title="Click to Sort" data-toggle="tooltip" data-placement="top">Reference</th>
                    <th>Member</th>
                    <th>Product</th>
                    
                    <!--<th>Procd. Sum</th> -->
                    <th>HP price</th>
                    <!--<th>Success</th>
                    <th>Failure</th>-->
                    <th>Cash price</th>
                    <th>Trans. Date</th>
                    <th>Approved By</th>
                    <th>Approval Date</th>
                    
                    <th>Action</th>
                    <!--<th></th>-->
                   
                </tr>
            </thead>
            <tbody style=" font-size: 11px; padding: 0px 0px 0px 0px;margin: 0px 0px 0px 0px">
                 <c:forEach var="hpreq4apprv" items="${hpreqs4apprv}" >
                    
                  <tr>
                  <td>${hpreq4apprv.id}</td>    
                  <td>${hpreq4apprv.refid}</td>
                  <td>${hpreq4apprv.membername} - ${hpreq4apprv.memberno}</td>
                  <td>${hpreq4apprv.product}</td>
                  
                  <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${hpreq4apprv.hpprice}" /></td>
                  <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${hpreq4apprv.cashprice}" /></td>
                  <td>${hpreq4apprv.txndate}</td>
                  <td>${hpreq4apprv.createdby}</td> 
                  <td>${hpreq4apprv.createddate}</td>
                 
                  <td><a href="hp_postsales.htm?id=${hpreq4apprv.refid}">Sale</a> &nbsp;&nbsp;&nbsp; <br>
                  </tr>
                </c:forEach>
                
            </tbody>
        </table>
               </div>
          
      </div>       
            <%@ include file="../includes/footer.jsp" %>  