<%@ include file="../includes/header.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Settings</li>
        </ul>
        <h4>Edit GL Settings</h4>
    </div>
    <%@include file="../includes/topright.jsp" %>
    
</div>
</div><!-- media -->
</div><!-- pageheader -->



<div class="contentpanel">

    <div class="row col-md-6">
        <!-- CONTENT GOES HERE -->  
        <!-- <div class="panel-body">-->
            <!-- form starts -->
            <form:form method="post" action="gl_pfeditsettings.htm" modelAttribute="settingsdet">  
                <div class="panel-body">


                    <div class="form-group">
                        <form:label path="id" cssClass="col-sm-4 control-label">Id: </form:label>
                            <div class="col-sm-6">
                            <form:input path="id" size="2"  readonly="true" disabled="disable" />
                            <div class="error">
                                <form:errors path="id" />
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="setting" cssClass="col-sm-4 control-label">Setting: </form:label>
                            <div class="col-sm-6">
                            <form:input path="setting" size="50" readonly="true" disabled="disable"/>
                            <div class="error">
                                <form:errors path="setting" />
                            </div>
                        </div>
                    </div><!-- form-group -->

                    <div class="form-group">
                        <form:label path="settingval" cssClass="col-sm-4 control-label">Value: </form:label>
                            <div class="col-sm-6">
                           <c:choose> 
                            <c:when test ="${settingsdet.setting=='ACCOUNTINGMETHOD'}" >
                               <c:set value="" var="accval"/>
                               <c:if test="${settingsdet.settingval=='ACCRUAL'}">
                                  <c:set value="selected" var="accval"/>
                               </c:if>
                               <c:set value="" var="cashval"/>
                               <c:if test="${settingsdet.settingval=='CASH'}">
                                  <c:set value="selected" var="cashval"/>
                               </c:if>  
                               <select  class="width100p" id="settingval" name="settingval">
                                   <option value="ACCRUAL" ${accval}>ACCRUAL</option>
                                   <option value="CASH" ${cashval}>CASH</option>
                               </select> 
                              </c:when>
                              <c:when test ="${settingsdet.setting=='PROCESSINGMETHOD'}" >
                               <c:set value="" var="autval"/>
                               <c:if test="${settingsdet.settingval=='AUTO'}">
                                  <c:set value="selected" var="autval"/>
                               </c:if>
                               <c:set value="" var="manval"/>
                               <c:if test="${settingsdet.settingval=='MANUAL'}">
                                  <c:set value="selected" var="manval"/>
                               </c:if>  
                               <select  class="width100p" id="settingval" name="settingval">
                                 <option value="AUTO" ${autval}>AUTO</option>
                                 <option value="MANUAL" ${manval}>MANUAL</option>
                               </select> 
                              </c:when>
                            <c:otherwise>
                              <form:input path="settingval" size="50" />
                            </c:otherwise> 
                           </c:choose>     
                            <div class="error">
                                <form:errors path="settingval" />
                            </div>
                        </div>
                    </div><!-- form-group -->

                   

                </div><!-- panel-body -->
                <div>
                    <input type="submit" class="btn btn-danger mr5" value="Save"/>
                </div><!-- panel-footer -->

            </form:form>  
            <!-- form ends -->
       <!--  </div> -->
        <!-- End of panel-body -->
    </div>
</div>       
            <%@ include file="../includes/footer.jsp" %>  