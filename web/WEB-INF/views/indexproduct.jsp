
<div class="media-body">
                                <ul class="breadcrumb">
                                    <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
                                    <li><a href="#">Home</a></li>
                                    <li>Test Page</li>
                                </ul>
                                <h4>Test Page</h4>
                            </div>
                        </div><!-- media -->
                    </div><!-- pageheader -->
                    
                    <div class="contentpanel">
					<div class="row">
					<table class="table  table-bordered responsive">
		  <tr>
		     <td>Name</td>
			 <td><input class="form-control"></td>
		  </tr>
		   <tr>
		     <td>Name</td>
			 <td><input class="form-control"></td>
		  </tr>
		   <tr>
		     <td>Name</td>
			 <td><input class="form-control"></td>
		  </tr>
		   <tr>
		     <td>Name</td>
			 <td><input class="form-control"></td>
		  </tr>
		  <tr>
		    <td>
			
			</td>
			<td>
			 <button class="btn btn-danger mr5">Submit</button>
                            <button type="reset" class="btn btn-default">Reset</button>
			</td>
		  </tr>
		  </table>
					</div>
					<div class="row">
					    <div class="col-md-10">
                <div class="panel panel-primary-head">
                    <button class="btn btn-danger mr5" id="addNewProduct">Add New Product</button>
                    <table id="products-list" class="table table-striped table-bordered responsive">
                        <thead class="">
                            <tr>
                                <th> Id</th>
                                <th>Name</th>
                                <th>Code</th>
                                <th>Company</th>
                                <th>Currency</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach  var="product" items="${products}" varStatus="status" >
                                <tr>
                                    <td>${status.index + 1}</td>
                                    <td >${product.code}</td>
                                    <td>${product.name}</td>
                                    <td>${product.companyId}</td>
                                    <td >${product.currencyId}</td>
                                    <td class="table-action">
                                        <a id="edit-${product.id}"  href="#" data-toggle="tooltip" title="Edit" class="tooltips"><i class="fa fa-pencil"></i></a>
                                        <a id="delete-${product.id}"  href="#" data-toggle="tooltip" title="Delete" class="delete-row tooltips"><i class="fa fa-trash-o"></i></a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
				</div>
                    
                        <!-- CONTENT GOES HERE -->    
                    
                    </div><!-- contentpanel -->
               
				   <script>
    $(document).ready(function() {
        jQuery('#products-list').DataTable({
            responsive: true
        });
    

    })
</script>