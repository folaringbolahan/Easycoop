<%@ include file="includes/header.jsp" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="media-body">
<div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Settings</li>
    </ul>
    <h4>Manage Setting</h4>
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
                       <th>ID</th>  
		       <th>Setting</th>  
		       <th>Value</th>
                       <th>Company</th>
		       <th>Action </th>  
                 

                </tr>
            </thead>
            <tbody>
                <c:forEach var="setting" items="${setting_coop}" >

                    <tr>
                        <td>${setting.id}</td>    
                        <td>${setting.setting}</td>
                        <td>${setting.value}</td>
                        <td>${setting.companyName}</td>
                        
                        <td><a href="editsettings.htm?id=${setting.companyid}">Edit</a></td>
                   </tr>
                </c:forEach>

            </tbody>
        </table>
    </div>
    
   
</div>


<%@ include file="includes/footer.jsp" %>  

   