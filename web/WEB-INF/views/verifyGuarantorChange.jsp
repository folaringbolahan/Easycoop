<%@ include file="includes/header.jsp" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="media-body">
<div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Loan Guarantor For Approval</li>
    </ul>
    <h4>LOAN GUARANTOR APPROVAL</h4>
</div>
<%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->
 
<div class="contentpanel">
      <div class=" col-md-10">
        <table  id="data-list" class="table table-striped table-bordered responsive" >       
            <thead>
                <tr>
                     
		       <th>Loan Case Id</th>
                       <th>Approval</th>
		       <th>Action </th>  
                 

                </tr>
            </thead>
            <tbody>
                <c:forEach var="guarantor" items="${loanGuarant}" >

                    <tr>
                          
                        <td>${guarantor.loanCaseId}</td>
                        <td>${guarantor.approved}</td>
                        
                        
                        <td><a href="editNewGuarantor.htm?id=${guarantor.loanCaseId}">Edit</a>
                              </td>
                   </tr>
                </c:forEach>

            </tbody>
        </table>
    </div>
    
   
</div>


<%@ include file="includes/footer.jsp" %>  

   