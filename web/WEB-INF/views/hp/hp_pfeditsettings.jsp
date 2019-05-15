<%@ include file="../includes/header.jsp" %>

<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="../index.htm">Home</a></li>
            <li>Hire Purchase Validation Rules</li>
        </ul>
        <h4>Hire Purchase Validation Edit </h4>
    </div>
    <%@include file="../includes/topright.jsp" %>
    
</div>
</div><!-- media -->
</div><!-- pageheader -->



<div class="contentpanel">

    <div class="row col-md-8">
        <!-- CONTENT GOES HERE -->  
        <!-- <div class="panel-body">-->
            <!-- form starts -->
            <form:form method="post" action="hp_pfeditsettings.htm" modelAttribute="hpvalidationrules">  
                <div class="panel-body">

                    <div class="form-group">
                        <form:label path="id" cssClass="col-sm-3 control-label tooltips"  data-original-title="Id" data-toggle="tooltip" data-placement="left">Id: </form:label>
                            <div class="col-sm-6">
                            <form:input path="id" size="30" id="id" readonly="true"/>
                            <div class="error">
                                <form:errors path="id" />
                            </div>
                        </div>
                    </div><!-- form-group -->
                    
                    <div class="form-group">
                        <form:label path="productcode" cssClass="col-sm-3 control-label tooltips"  data-original-title="Product" data-toggle="tooltip" data-placement="left">Product: *</form:label>
                            <div class="col-sm-6">
                            <form:select path="productcode" cssClass="width100p" >
                                <form:option value="" label="Choose Product" />
                                <form:options  items="${hpproducts}"
                                         itemValue="code" itemLabel="name"/>
                            </form:select>
                            <form:errors path="productcode" cssClass="error" />
                        </div>
                    </div><!-- form-group -->

                    <div class="form-group">
                        <form:label path="code" cssClass="col-sm-3 control-label tooltips"  data-original-title="Validation Settings Code" data-toggle="tooltip" data-placement="left">Code: *</form:label>
                            <div class="col-sm-6">
                            <form:input path="code" size="30" id="code"/>
                            <div class="error">
                                <form:errors path="code" />
                            </div>
                        </div>
                    </div><!-- form-group -->

                    <div class="form-group">
                        <form:label path="description" cssClass="col-sm-3 control-label tooltips"  data-original-title="Description of validation rule" data-toggle="tooltip" data-placement="left">Description: *</form:label>
                            <div class="col-sm-6">
                            <form:input path="description" size="50" />
                            <div class="error">
                                <form:errors path="description" />
                            </div>
                        </div>
                    </div><!-- form-group -->
                    
                    
                    
                    <div class="form-group">
                        <form:label path="validationtype" cssClass="col-sm-3 control-label tooltips"  data-original-title="Rule Type(Flat or Formula)" data-toggle="tooltip" data-placement="left">Rule Type: *</form:label>
                            <div class="col-sm-4">
                            <form:select path="validationtype" id="validationtype">
                                <form:option value="" label="Choose Rule Type" />
                                <form:option value="Flat" label="Flat" />
                                <form:option value="Formula" label="Formula" />
                            </form:select>
                            <form:errors path="validationtype" cssClass="error" />

                        </div>
                    </div><!-- form-group -->

                   <!-- form-group -->
                            <div class="form-group" id="operandsContainer">
                                <label data-toggle="tooltip" title="Build formula for validation" class="col-sm-3 tooltips control-label">Select Operand</label>
                                <div class="col-sm-5">
                                    <select class="width100p" id="cmbOperands" >
                                        <option value="">Choose Operands</option>

                                        <c:forEach  var="operand" items="${hpoperands}" varStatus="status" >

                                            <option value="${operand.code}">${operand.description} - ${operand.code}</option>

                                        </c:forEach>
                                    </select>
                                      <button class="btn btn-default" type="button"  id="addOperand">Add</button>
                                </div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                
                                <div  class="col-sm-2">
                                    <select class="width50p" id="cmbOperators" >
                                        <option value="">Choose Operator</option>

                                        <option value="*">*</option>
                                        <option value="/">/</option>
                                        <option value="+">+</option>
                                        <option value="-">-</option>
                                        <option value="(">(</option>
                                        <option value=")">)</option>
                                    </select>
                                    <button class="btn btn-default" type="button" id="addOperator">Add</button>
                                </div>
                            </div><!-- form-group -->  
                             <div class="form-group" id="formularContainer">
                                <label data-toggle="tooltip" title="Build formula for validation" class="col-sm-3 tooltips control-label" >Formula for validation*</label>
                                <div class="col-sm-8">
                                    <form:input path="formula" size="50" id="formula"  required="required" />
                                </div>
                            </div><!-- form-group -->
                    
                          <div class="form-group">
                        <form:label path="resultcond" cssClass="col-sm-3 control-label tooltips"  data-original-title="Choose Operator that Formula defined above must evaluate to the Formula built" data-toggle="tooltip" data-placement="left">Conditional Operator: *</form:label>
                            <div class="col-sm-6">
                            <form:select path="resultcond" cssClass="width100p" >
                                <form:option value="" label="Choose Evaluation Operator" />
                                <form:option value="<" label="<" />
                                <form:option value=">" label=">" />
                                <form:option value="<=" label="<=" />
                                <form:option value=">=" label=">=" />
                                <form:option value="=" label="=" />
                            </form:select>
                            <form:errors path="resultcond" cssClass="error" />
                        </div>
                    </div><!-- form-group -->


                    </div><!-- panel-body -->
                <div>
                    <input type="submit" class="btn btn-danger mr5" value="Save" id="save"/>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                   
                </div><!-- panel-footer -->

            </form:form>  
                
                  
            <!-- form ends -->
       <!--  </div> -->
        <!-- End of panel-body -->
    </div>

   


</div>
<!-- contentpanel -->

</div>
            </div><!-- mainwrapper -->
        </section>


        <script src="<%=request.getContextPath()%>/resources/js/jquery-1.11.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery-migrate-1.2.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.10.3.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/modernizr.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/pace.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/retina.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery.cookies.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery.autogrow-textarea.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery.mousewheel.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery.tagsinput.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/toggles.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/bootstrap-timepicker.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery.maskedinput.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/select2.min.js"></script>
        <script src="<%=request.getContextPath()%>/resources/js/jquery.gritter.min.js"></script>
        
        <script src="<%=request.getContextPath()%>/resources/js/jquery.validate.min.js"></script>

        <script src="<%=request.getContextPath()%>/resources/js/jquery.dataTables.min.js"></script>
       <script src="<%=request.getContextPath()%>/resources/js/dataTables.bootstrap.js"></script>
        
        <script src="<%=request.getContextPath()%>/resources/js/dataTables.responsive.js"></script>
         <script src="${resourceUrl}/js/custom.js"></script>
         <script src="<%=request.getContextPath()%>/resources/js/jquery.autocomplete.min.js"></script>
        <script>
    $(document).ready(function(){
        // Date Picker
      
       jQuery('select').select2({
                    minimumResultsForSearch: -1
                });
      jQuery('#data-list').DataTable({
                    responsive: true
                });
       
       jQuery("#addOperand").click(function(){
                var selected = jQuery("#cmbOperands").val();
                var formula = jQuery("#formula").val();
                var dirtyFormula = formula+selected;
                jQuery("#formula").val(dirtyFormula);
            });
            jQuery("#addOperator").click(function(){
                var selected = jQuery("#cmbOperators").val();
                var formula = jQuery("#formula").val();
                var dirtyFormula = formula+selected;
                jQuery("#formula").val(dirtyFormula);
            });
                  
    });
    
    
</script>
    </body>


</html>
