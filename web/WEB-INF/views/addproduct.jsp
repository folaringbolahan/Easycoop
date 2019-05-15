<%@ include file="includes/header.jsp" %>  

<div class="media-body">
    <!-- changes starts -->
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="#">Home</a></li>
            <li>Product Management</li>
        </ul>
        <h4>Add Product</h4>
    </div>
    <div style="float:right">
        <div class="headerprofile" >
            <div class="profile-right">
                <a class="pull-left profile-thumb" href="#">
                    <img class="img-circle" src="<%=request.getContextPath()%>/images/photos/biz-logo.jpg" alt="">
                </a>
                <div class="media-body" >
                    <h4 class="media-heading">Taiwo Okechukwu</h4>
                    <small class="text-muted">Loan Manager</small>
                </div>
            </div>
        </div>
    </div>
    <!-- changes end -->
</div>
</div><!-- media -->
</div><!-- pageheader -->

<div class="contentpanel">
    <div class="row">
        <div class="col-md-8">
            <!-- CONTENT GOES HERE -->  
            <div class="col-md-10">
                               
                <form:form method="POST" commandName="productForm" action="${pageContext.request.
contextPath}/views/product/addProduct" id="productForm">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div class="panel-btns">
                                <a href="#" class="panel-minimize tooltips" data-toggle="tooltip" title="Minimize Panel"><i class="fa fa-minus"></i></a>
                                <a href="#" class="panel-close tooltips" data-toggle="tooltip" title="Close Panel"><i class="fa fa-times"></i></a>
                            </div><!-- panel-btns -->
                            <h4 class="panel-title">Product creation form</h4>
                            <p>Kindly fill the form below to create a new product.</p>
                        </div>
                        <div class="panel-body">
                            <div class="errorForm"></div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Product Code<span class="asterisk">*</span></label>
                                <div class="col-sm-4">

                                    <form:input path="code" cssClass="form-control"  id="productCode" required="required" />
                                </div>
                            </div><!-- form-group -->

                            <div class="form-group">
                                <label class="col-sm-4 control-label">Product Name <span class="asterisk">*</span></label>
                                <div class="col-sm-4">

                                    <form:input path="name"  cssClass="form-control" id="productName"  required="required" />
                                </div>
                            </div><!-- form-group -->

                            <div class="form-group">
                                <label class="col-sm-4 control-label">Product Type <span class="asterisk">*</span></label>
                                <div class="col-sm-4">

                                    <form:select  cssClass="width300" id="productTypeId"  path="productTypeId"  required="required"  >
                                        <form:option value="" label="Choose Product" />
                                        <form:options items="${productList}" itemValue="id" itemLabel="name" />
                                    </form:select>

                                </div>
                            </div><!-- form-group -->
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Currency<span class="asterisk">*</span></label>
                                <div class="col-sm-4">
                                    <form:select cssClass="width300" id="currencyId"  path="currencyId"  required="required" >
                                        <form:option value="" label="Choose Currency" />
                                        <form:options items="${currencyList}" itemValue="id" itemLabel="name" />
                                    </form:select>

                                </div>
                            </div><!-- form-group -->

                            <div class="form-group">
                                <label class="col-sm-4 control-label">Attracts Interest?<span class="asterisk">*</span></label>
                                <div class="col-sm-8">
                                    <div class="ckbox ckbox-danger">
                                        <div class="rdio rdio-danger">

                                            <form:radiobutton id="yesInterest" value="${product.hasInterest}" path="hasInterest"  required="required" />
                                            <label for="yesInterest">Yes</label>
                                        </div><!-- rdio -->
                                        <div class="rdio rdio-danger">
                                            <form:radiobutton id="noInterest" value="${product.hasInterest}"  path="hasInterest"  required="required" />
                                            <label for="noInterest">No</label>
                                        </div><!-- rdio -->

                                    </div>
                                </div>
                            </div><!-- form-group -->
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Interest Amount</label>
                                <div class="col-sm-2">
                                    <form:input path="interestRate" cssClass="form-control"  id="interestRate" />
                                </div>
                                <div>
                                    <form:hidden value="1" path="companyId"  />
                                </div>
                            </div><!-- form-group -->  


                        </div><!-- panel-body -->
                        <div class="panel-footer">
                            <button class="btn btn-danger mr5">Submit</button>
                            <button type="reset" class="btn btn-default">Reset</button>
                        </div><!-- panel-footer -->
                    </div><!-- panel-default -->
                </form:form>
            </div><!-- col-md-6 -->
        </div>
    </div>
</div><!-- contentpanel -->

<%@ include file="includes/footer.jsp" %>  
<script>
    $(document).ready(function() {
        jQuery('#datepicker').datepicker();
        jQuery('select').select2({
            minimumResultsForSearch: -1
        });
        jQuery("#productForm").validate({
            highlight: function(element) {
                jQuery(element).closest('.form-group').removeClass('has-success').addClass('has-error');
            },
            success: function(element) {
                jQuery(element).closest('.form-group').removeClass('has-error');
            }
        });

        // Progress Wizard
                jQuery('#progressWizard').bootstrapWizard({
                    onTabShow: function(tab, navigation, index) {
                        tab.prevAll().addClass('done');
                        tab.nextAll().removeClass('done');
                        tab.removeClass('done');
                        
                        var $total = navigation.find('li').length;
                        var $current = index + 1;
                        
                        if($current >= $total) {
                            $('#progressWizard').find('.wizard .next').addClass('hide');
                            $('#progressWizard').find('.wizard .finish').removeClass('hide');
                        } else {
                            $('#progressWizard').find('.wizard .next').removeClass('hide');
                            $('#progressWizard').find('.wizard .finish').addClass('hide');
                        }
                        
                        var $percent = ($current/$total) * 100;
                        $('#progressWizard').find('.progress-bar').css('width', $percent+'%');
                    }
                });
    })
</script>