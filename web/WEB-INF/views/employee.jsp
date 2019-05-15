<%@ include file="includes/header.jsp" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script lanuage="Javascript">
	function doSubmit(emp){	
	    if(emp.name.value==""){alert("Name is required"); emp.name.focus(); return;}	    
	    if(emp.shortName.value==""){alert("Salary is required"); emp.salary.focus(); return;}	

	    if(emp.regNo.value==""){alert("Designation is required"); emp.designation.focus(); return;}	    
	    emp.submit();
	}	  
</script>



<div class="media-body">
<div style="float:left">

    <ul class="breadcrumb">
        <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
        <li><a href="#">Home</a></li>
        <li>Employee</li>
    </ul>
    <h4>Manage Employee</h4>
</div>
<%@include file="includes/topright.jsp" %>
 </div>
 </div><!-- media -->
 </div><!-- pageheader -->
 <div class="contentpanel">
     <div class="row">
         <div class="col-md-10">
             <!-- CONTENT GOES HERE -->  
             <div class="col-md-15">
 	       <div class="panel-body">
                 <c:set var="now" value="<%=new java.util.Date()%>" />
                 <%--   <form:form method="GET" action="employee" modelAttribute="employee">  
                 
                                                                                        <div class="form-group">  
				  <form:label path="name" cssClass="col-sm-4 control-label">Name: *</form:label>
				  <div class="col-sm-2">
				    <form:input path="name" value="${emp.name}"/>
				  </div>  
			   </div>  
                 
                                                            <div class="form-group">
			  <button class="btn btn-danger mr5" type="submit">ADD USER</button>
			  <button type="reset" class="btn btn-default">RESET</button>
		   </div><!-- panel-footer -->        
	
                      </form:form>
                 --%>
                 
                 <form:form method="post" action="employee.htm" commandName="employee">

   <div class="form-group">
       
<button class="btn btn-danger mr5" type="button" onclick="Javascript:doSubmit(this.form);">SUBMIT </button>
 <button type="reset" class="btn btn-default">RESET</button>
 </div><!-- panel-footer -->
</form:form>  
<div class="contentpanel">
      <div class=" col-md-10">
        <table  id="data-list" class="table table-striped table-bordered responsive" >       
            <thead>
                <tr>
                       <th>ID</th>  
                       <th>Name</th>  
                       <th>Salary</th>
                       <th>Designation</th>       
                       <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="emp" items="${employee}" >

                    <tr>
                        <td>${emp.id}</td>    
                        <td>${emp.name}</td>
                        <td>${emp.salary}</td>
                        <td>${emp.designation}</td>
                    
                        <td><a href="">Edit</a></td>
                   </tr>
                </c:forEach>

            </tbody>
        </table>
    </div>
   
</div>



   