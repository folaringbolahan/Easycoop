    <%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>  
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
    <html>  
     <head>  
      <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">  
      <title>Spring MVC Form Handling</title>  
		      
      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui.js"></script>
      <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-i18n.min.js"></script> 
        
   
      <script type="text/javascript">
	      $(document).ready(function(){	        
		$('select#companyId').change(		        
			function(){
			        alert($(this).val());
				$.getJSON('branchesAjaxList.htm', {
					companyId : $(this).val(),
					ajax : 'true'
				}, function(data) {
					var len = data.length;
                                        var html ='';
					for ( var i = 0; i < len; i++) {
					    html += '<option value="' + data[i].id + '">'
					     + data[i].branchName + '</option>';
				        }
				        
				        //alert(html);					
					$('select#branchId').html(html);
				});
			});
	      });
      </script>
     </head>  
     <body>  
      <h2>Add Loan Guarantor:</h2>      
      <form:form method="post" action="saveGuarantor.htm" commandName="loanGuarantor">
          <table>  
           <tr>  
                  <td><form:label path="id"> ID:</form:label></td>  
                  <td><form:input path="id" value="${loan.id}" readonly="true"/></td>  
           </tr> 
	   <tr>  
		  <td><form:label path="companyId">Company:</form:label></td>  
		  <td><form:select id="company" path="companyId">
		        <form:option value="0">--select--</form:option>
			<c:forEach items="${companies}" var="item">  
			     <form:option value="${item.id}">${item.name}</form:option>
			</c:forEach>
		      </form:select>
		  </td>  
           </tr> 
	   <tr>  
		  <td><form:label path="branchId">Branch</form:label></td>  
		  <td><form:select id="branchId" path="branchId">
		         <form:option value="0">--select--</form:option>
		      </form:select>
		  </td>  
	   </tr>
           <tr>  
                  <td><form:label path="loanCaseId">Loan Case Id:</form:label></td>  
                  <td><form:input path="loanCaseId" value="${loanGuarantor.loanCaseId}"/></td>  
           </tr> 
           <tr>  
		  <td><form:label path="memberNo">Member No:</form:label></td>  
		  <td><form:input path="memberNo" value="${loanGuarantor.memberNo}"/></td>  
	   </tr> 
           <tr>  
		  <td><form:label path="guarantorNo">Guarantor No:</form:label></td>  
		  <td><form:input path="guarantorNo" value="${loanGuarantor.guarantorNo}"/></td>  
	   </tr>
	   
           <tr>  
		  <td><form:label path="active">Active:</form:label></td>  
		  <td><form:input path="active" value="Y" readonly="true"/></td>  
	   </tr>  
	   <tr>  
		  <td><form:label path="deleted">Deleted:</form:label></td>  
		  <td><form:input path="deleted" value="N" readonly="true"/></td>  
           </tr>	   
	              
	   <input type="hidden" name="ACTION_ID" value="1">;

           <tr>  
             <td colspan="2"><input type="submit" value="Submit"/></td>  
            </tr>  
       </table>   
      </form:form>  
     </body>  
    </html>  