<%@ include file="../includes/header.jsp" %>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>GL Settings</li>
        </ul>
        <h4>Edit GL Settings</h4>
    </div>
    <%@include file="../includes/topright.jsp" %>
    
</div>
</div><!-- media -->
</div><!-- pageheader -->



<div class="contentpanel">

   
    <div class=" col-md-10">
        <table  id="data-list" class="table table-striped table-bordered responsive" >       
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Setting</th>
                    <th>Value</th>
                    <th></th>
                 

                </tr>
            </thead>
            <tbody>
                <c:forEach var="setting" items="${settings}" >

                    <tr>
                        <td>${setting.id}</td>    
                        <td>${setting.setting}</td>
                        <td>${setting.settingval}</td>
                        <td><a href="gl_editsettings.htm?id=${setting.id}&AMP;ds=${setting.setting}">Edit</a></td>
                   </tr>
                </c:forEach>

            </tbody>
        </table>
    </div><!-- col-md-10 -->

</div>


<!-- contentpanel -->
 
<%@ include file="../includes/footer.jsp" %>  