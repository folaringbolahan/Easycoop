<%@ include file="includes/header.jsp" %>  
<script src="<%=request.getContextPath()%>/js/utilityfc.js"></script>
<div class="media-body">
    <div style="float:left">
        <ul class="breadcrumb">
            <li><a href="#"><i class="glyphicon glyphicon-home"></i></a></li>
            <li><a href="#">Home</a></li>
            <li>Votes</li>
        </ul>
        <h4>Vote</h4>
    </div>
      <%@include file="includes/topright.jsp" %>
</div>
</div><!-- media -->
</div><!-- pageheader -->


<div class="contentpanel">
    <div class="row">
        <!--  <div class="col-md-8">-->  
        <!-- CONTENT GOES HERE -->  
        <!--   <div class="col-md-10">-->
        <div class="panel panel-primary-head">

            <table id="vote-list" class="table table-striped table-bordered responsive">
                <thead class="">
                    <tr>

                        <th>Id</th>
                        <th>Title</th>

                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach  var="vote" items="${votes}" varStatus="status" >
                        <tr>

                            <td>${status.index + 1}</td>
                            <td >${vote.title}</td>
                            <td class="table-action">
                                <button class="btn btn-danger btn-rounded vote" data-agmid="${vote.agmId}" data-voteid="${vote.id}" id="vote-${vote.id}">Place Vote</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div><!-- col-md-6 -->
</div>
</div>

<div class="modal fade bs-example-modal-lg" tabindex="-1" id="voteModal" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">&times;</button>
                <h4 class="modal-title">Place Vote</h4>
            </div>
            <div class="modal-body">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="panel-btns">
                            <a href="#" class="panel-minimize tooltips" data-toggle="tooltip" title="Minimize Panel"><i class="fa fa-minus"></i></a>

                        </div><!-- panel-btns -->
                        <h4 class="panel-title">Place Vote</h4>
                        <p>Select your preferred option and click submit</p>
                    </div><!-- panel-heading -->
                    <div class="panel-body">
                        <div class="row">
                            <div id="submitResponse" class="alert alert-success" style="display:none">
                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                <p id="success-message"></p>
                                <input type="hidden" id="voteId" name="voteId" />
                            </div>

                            <div id="option-list">

                            </div>
                            <div>
                                <button class="btn btn-danger btn-metro" id="castVote">Vote</button>
                            </div>


                        </div>
                    </div>
                </div>
                <div class="modal-footer">

                </div>
            </div>
        </div>
    </div> <!--Modal ends -->
</div><!-- contentpanel -->

<%@ include file="includes/footer.jsp" %>  
<script>

    $(document).ready(function() {

        $('.vote').click(function() {
            var vote_id = $(this).data('voteid');
            var agm_id = $(this).data('agmid');
            $("#voteId").val(vote_id);

           
            $.getJSON("<%=request.getContextPath()%>/views/votes/getoptions", {"id": vote_id}, function(data) {
                var html = "";
                if ($.isEmptyObject(data)) {

                } else {
                    html = '<div class="form-group"><div class="col-sm-8">';
                    $.each(data, function(index) {
                        html += '<div class="rdio rdio-danger">';
                        html += '<input type="radio" id="option-' + data[index].id + '" value="' + data[index].id + '" name="voteOption" required="required" />';
                        html += '<label for="option-' + data[index].id + '">' + data[index].voteOption + '</label>';
                        html += '</div>';
                    });
                    html += '</div></div>';
                }

                $("#option-list").html("");
                $("#option-list").append(html);


            });

            $('#voteModal').modal('show');
        });
        $('#castVote').click(function() {
             var voteId = $("#voteId").val();
             //var vote_option_id = $("input[name='voteOption']").val();
             var vote_option_id = 0;
          if($("input[name='voteOption']").is(':checked')){
              vote_option_id = $("input[name=voteOption]:checked").val();//$("input[name='voteOption']").val();
          
                var json = {"voteId": voteId, "voteOptionId": vote_option_id};
             
              $.get("<%=request.getContextPath()%>/views/votes/castvote", {"voteId": voteId, "voteOptionId": vote_option_id}, function(result) {
                    
                        $("#success-message").html(result);
                        $('#submitResponse').css("display", "block");
                        $('#submitResponse').focus();
       
                });
             
            
          }else{
              $("#success-message").html("Please select an option");
                        $('#submitResponse').css("display", "block");
                        $('#submitResponse').focus();
          }
          
          
           
        });

    });
</script>