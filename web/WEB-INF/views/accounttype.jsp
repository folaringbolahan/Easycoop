<%@ include file="includes/header.jsp" %>  
<div class="media-body">
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
</div>
</div><!-- media -->
</div><!-- pageheader -->

<div class="contentpanel">
    <div class="row col-md-6">
        <table class="table table-bordered responsive">
            <tr>
                <td>Name </td>
                <td><input type="text" class="form-control" /></td>
            </tr>
          
            </tr>
            <tr>
                <td> </td>
                <td><input type="submit" class="btn btn-danger mr5" value="Save"/></td>
            </tr>

        </table>

    </div><!-- panel -->

</div>
<!--second row for table starts -->
<div class=" col-md-10">
    <table id="basic-table" class="table table-striped table-bordered responsive">
        <thead class="">
            <tr>
                <th>Name</th>
                <th>Position</th>
                <th>Office</th>
                <th>Age</th>
                <th>Start date</th>
                <th>Salary</th>
            </tr>
        </thead>

        <tbody>
            <tr>
                <td>Tayo Brown</td>
                <td>System Architect</td>
                <td>Lagos</td>
                <td>61</td>
                <td>2015/04/25</td>
                <td>N320,800</td>
            </tr>
            <tr>
                <td>Gbenga Williams</td>
                <td>Accountant</td>
                <td>Lagos</td>
                <td>63</td>
                <td>2015/07/25</td>
                <td>N170,750</td>
            </tr>
            <tr>
                <td>Tunde koiki</td>
                <td>Junior Technical Author</td>
                <td>Abuja</td>
                <td>66</td>
                <td>2009/01/12</td>
                <td>N86,000</td>
            </tr>
            <tr>
                <td>Fadare Musiliu</td>
                <td>Senior Javascript Developer</td>
                <td>Abeokuta</td>
                <td>22</td>
                <td>2012/03/29</td>
                <td>N433,060</td>
            </tr>
            <tr>
                <td>Abimbola Odeyemi</td>
                <td>Accountant</td>
                <td>Abeouka</td>
                <td>33</td>
                <td>2008/11/28</td>
                <td>N162,700</td>
            </tr>
            <tr>
                <td>Ibrahim Balogun</td>
                <td>Integration Specialist</td>
                <td>Oyo</td>
                <td>61</td>
                <td>2012/12/02</td>
                <td>N372,000</td>
            </tr>
            <tr>
                <td>Nike Brown</td>
                <td>Sales Assistant</td>
                <td>Ilorin</td>
                <td>59</td>
                <td>2012/08/06</td>
                <td>N137,500</td>
            </tr>
            <tr>
                <td>Sadeeq Saliu</td>
                <td>Integration Specialist</td>
                <td>Auchi</td>
                <td>55</td>
                <td>2010/10/14</td>
                <td>N327,900</td>
            </tr>
            <tr>
                <td>Yaaseer Enifeni</td>
                <td>Javascript Developer</td>
                <td>Abia</td>
                <td>39</td>
                <td>2009/09/15</td>
                <td>N205,500</td>
            </tr>
            <tr>
                <td>Hafeez Bola</td>
                <td>Software Engineer</td>
                <td>Gusau</td>
                <td>23</td>
                <td>2008/12/13</td>
                <td>N103,600</td>
            </tr>
            <tr>
                <td>John Akinduro</td>
                <td>Office Manager</td>
                <td>Abuja</td>
                <td>30</td>
                <td>2008/12/19</td>
                <td>N90,560</td>
            </tr>
            <tr>
                <td>Olamipo Bello</td>
                <td>Support Lead</td>
                <td>Lagos</td>
                <td>22</td>
                <td>2013/03/03</td>
                <td>N342,000</td>
            </tr>
            <tr>
                <td>Kayode Peters</td>
                <td>Regional Director</td>
                <td>Osun</td>
                <td>36</td>
                <td>2008/10/16</td>
                <td>$470,600</td>
            </tr>
            <tr>
                <td>Lagabja Tamedo</td>
                <td>Senior Marketing Designer</td>
                <td>Adamawa</td>
                <td>43</td>
                <td>2012/12/18</td>
                <td>N313,500</td>
            </tr>
            <tr>
                <td>Olanrewaju Peters</td>
                <td>Regional Director</td>
                <td>Lagos</td>
                <td>19</td>
                <td>2010/03/17</td>
                <td>N385,750</td>
            </tr>
            <tr>
                <td>Zainab Enifeni</td>
                <td>Marketing Designer</td>
                <td>Ogun</td>
                <td>66</td>
                <td>2012/11/27</td>
                <td>N98,500</td>
            </tr>
            <tr>
                <td>Damola Gbadebo</td>
                <td>Chief Financial Officer (CFO)</td>
                <td>Edo</td>
                <td>64</td>
                <td>2010/06/09</td>
                <td>N725,000</td>
            </tr>
            <tr>
                <td>Rotimi Amaechi</td>
                <td>Systems Administrator</td>
                <td>Rivers</td>
                <td>59</td>
                <td>2009/04/10</td>
                <td>N237,500</td>
            </tr>
            <tr>
                <td>Olusegun Obasanjo</td>
                <td>Software Engineer</td>
                <td>Ogun</td>
                <td>41</td>
                <td>2012/10/13</td>
                <td>N132,000</td>
            </tr>
            <tr>
                <td>Dimeki Bankole</td>
                <td>Personnel Lead</td>
                <td>Ogun</td>
                <td>35</td>
                <td>2012/09/26</td>
                <td>N217,500</td>
            </tr>
            <tr>
                <td>Iyaho coker</td>
                <td>Development Lead</td>
                <td>Abuja</td>
                <td>30</td>
                <td>2011/09/03</td>
                <td>N345,000</td>
            </tr>
            <tr>
                <td>Olamide Lawal</td>
                <td>Chief Marketing Officer (CMO)</td>
                <td>New York</td>
                <td>40</td>
                <td>2009/06/25</td>
                <td>N675,000</td>
            </tr>

        </tbody>
    </table>
</div>
<!-- second row for table ends -->
<!-- CONTENT GOES HERE -->    

</div><!-- contentpanel -->
<%@ include file="includes/footer.jsp" %>  
<script>
    $(document).ready(function() {
        jQuery('select').select2({
            minimumResultsForSearch: -1
        });
        jQuery('#basic-table').DataTable({
            responsive: true
        });
    })
</script>