<%--
Views should be stored under the WEB-INF folder so that
they are not accessible except through controller process.

This JSP is here to provide a redirect to the dispatcher
servlet but should be the only JSP outside of WEB-INF.
--%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty param.login_error}">
    <font color="red">
    Login error. <br />
    Reason : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
    </font>
</c:if>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>EasyCoopFin - Accounting System for Cooperatives</title>

    <link href="<%=request.getContextPath()%>/resources/css/style.default.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
    
    <style>
    .autocomplete-suggestions { border: 1px solid #999; background: #FFF; overflow: auto; }
    .autocomplete-suggestion { padding: 5px 5px; white-space: nowrap; overflow: hidden; font-size:18px}
    .autocomplete-selected { background: #F0F0F0; }
    .autocomplete-suggestions strong { font-weight: bold; color: #D9534F; }
</style>
</head>

<body class="signup">


    <section>

        <div class="panel panel-vote">
            <div class="panel-body">
                <div class="logo text-center">
                    <img src="<%=request.getContextPath()%>/resources/images/logo-primary.png" alt="Chain Logo" >
                </div>
                <br />
                <h4 class="text-center mb5"></h4>
                <p class="text-center">Please answer the questions below before you can place your vote</p>

                <div class="mb30"></div>
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
            </div><!-- panel-body -->

        </div><!-- panel -->

    </section>


    <script src="<%=request.getContextPath()%>/resources/js/jquery-1.11.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/jquery-migrate-1.2.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/modernizr.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/pace.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/retina.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/jquery.cookies.js"></script>

    <script src="<%=request.getContextPath()%>/resources/js/custom.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/jquery.autocomplete.min.js"></script>

</body>
     <script>
            $(document).ready(function() {
   $('.vote').click(function() {
            var vote_id = $(this).data('voteid');
            var agm_id = $(this).data('agmid');
            $("#voteId").val(vote_id);

           
            $.getJSON("<%=request.getContextPath()%>/views/vote/getoptions", {"id": vote_id}, function(data) {
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
             
              $.get("<%=request.getContextPath()%>/views/vote/castvote", {"voteId": voteId, "voteOptionId": vote_option_id}, function(result) {
                    
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

</html>