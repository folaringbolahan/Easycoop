<%@ include file="../includes/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>View Details</li>
        </ul>
        <h4>View Details</h4>
    </div>
    <%@include file="../includes/topright.jsp" %>
    
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
       <button class="btn btn-success mr5" id="downloadexcel" name="action" onClick="javascript:form.action = '<%=request.getContextPath()%>/hp/hp_downloadrecs/${dBatchId}/${nm}.htm'" value="Download">DOWNLOAD FILE</button>
    </p>   
            <table  id="data-list" class="table table-striped table-bordered responsive" >       
                <thead style="font-size: 12px">
               
                   <tr align="left">
                     <c:forEach var="hdrlistvalue" items="${headere}">
                       <th>${hdrlistvalue}</th>
                     </c:forEach>
                   </tr>
                   
                    <!--<th></th>-->
                 
            </thead>
            <tbody style="font-size: 12px">
                 <c:forEach var="accttxnrow" items="${body}" >
                   <tr>
                      <c:forEach var="accttxncell" items="${accttxnrow}" >
                          
                           <td>${accttxncell}</td> 
                      </c:forEach>     
                  </tr>
                </c:forEach>
                
            </tbody>
        </table>
            </form:form> 
               </div>
          
      </div>       
            <%@ include file="../includes/footer.jsp" %>  