<%@ include file="includes/header.jsp" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="index.htm">Home</a></li>
            <li>View Details</li>
        </ul>
        <h4>View Details</h4>
    </div>
    <%@include file="includes/topright.jsp" %>
    
</div>
</div><!-- media -->
</div><!-- pageheader -->



<div class="contentpanel">
  

<p>
        
           <!-- form section above and list section below   -->
           <div class="col-md-12">
               ${pagetitle}
               <p></p>
             <form:form method="post" action="" modelAttribute="approveAcct"> 
    <p>  
       <button class="btn btn-success mr5" id="downloadexcel" name="action" onClick="javascript:form.action = '<%=request.getContextPath()%>/voterdownloadrecs/${referenceNumber}/${agid}.htm'" value="Download">DOWNLOAD FILE</button>
    </p>   
           
    
    
    
               <table  id="data-list" class="table table-striped table-bordered responsive"  >       
                <thead style=" font-size: 11px;padding: 0px 0px 0px 0px;margin: 0px 0px 0px 0px">
                <tr align="left">
                    <th>Member id.</th>
                    <th>First name</th>
                    <th>Middle name</th>
                    <th>Surname</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Vote Units</th>
                    <th>Error(s)</th>
                    <th>Batch Ref.</th>
                    <th>AGM Id</th>
                    
                    <!--<th></th>-->
                   
                </tr>
            </thead>
            <tbody style=" font-size: 11px; padding: 0px 0px 0px 0px;margin: 0px 0px 0px 0px">
                 <c:forEach var="voterErrors" items="${votersErrors}" >
                    
                  <tr>
                  <td>${voterErrors.memberrefid}</td>    
                  <td>${voterErrors.firstname}</td>
                  <td>${voterErrors.middlename}</td>
                  <td>${voterErrors.surname}</td>
                  <td>${voterErrors.email}</td>    
                  <td>${voterErrors.phone}</td>
                  <td>${voterErrors.voteunits}</td>
                  <td>${voterErrors.errormessage}</td>
                  <td>${voterErrors.referenceid}</td>    
                  <td>${voterErrors.agmid}</td>
                  
                                 
                 </tr>
                </c:forEach>
                
            </tbody>
        </table>
              
    
    
    
            </form:form> 
               </div>
          
      </div>       
            <%@ include file="includes/footer.jsp" %>  