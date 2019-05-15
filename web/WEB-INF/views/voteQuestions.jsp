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
        .autocomplete-suggestion { padding: 5px 5px; white-space: nowrap; overflow: hidden; font-size:22px}
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
                <form method="post" class="signin" id="questionsForm" action="<%=request.getContextPath()%>/views/vote/getaccess">
                    <div id="submitResponse" class="alert alert-success" style="display:none">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <p id="success-message"></p>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label"><span class="asterisk">Company</span></label>
                        <div class="col-sm-4">

                            <input name="company" class="form-control"  id="company" required="required"  />
                            <input name="companyId" class="form-control"  id="companyId"  type="hidden" />
                        </div>
                    </div><!-- form-group -->
                    <c:forEach  var="question" items="${voteQuestions}" varStatus="status" >
                        <div class="form-group">
                            <label class="col-sm-4 control-label"><span class="asterisk">${question.question}</span></label>
                            <div class="col-sm-4">

                                <input name="question${status.index + 1}" data-code="${question.code}" class="form-control"  id="code-${status.index + 1}" required="required"  />

                            </div>
                        </div><!-- form-group -->
                    </c:forEach>
                    <div class="clearfix">

                        <div class="pull-right">
                            <button type="submit" class="btn btn-danger">Continue <i class="fa fa-angle-right ml5"></i></button>
                        </div>
                    </div>                      



                </form>

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
//attach autocomplete
        $('#company').autocomplete({
            serviceUrl: '${pageContext.request.contextPath}/views/vote/getcompanies',
            paramName: "name",
            delimiter: ",",
            transformResult: function(response) {

                return {
                    suggestions: $.map($.parseJSON(response), function(item) {

                        return {value: item.name, data: item.id};
                    })

                };

            },
            onSelect: function(suggestions) {
                $("#companyId").val(suggestions.data);
                alert('You selected: ' + suggestions.value + " id:" + suggestions.data);
            },
        });

        $('#questionsForm').submit(function(event) {

            var question1 = $("#code-1").val();
            var question2 = $("#code-2").val();
            var question3 = $("#code-3").val();
            var question4 = $("#code-4").val();
            
            var code1 = $("#code-1").data("code");
            var code2 = $("#code-2").data("code");
            var code3 = $("#code-3").data("code");
            var code4 = $("#code-4").data("code");
            
            var companyId =  $("#companyId").val();
            
            var json = {"question1": question1, "question2": question2,"question3":question3,"question4":question4,"code1":code1,"code2":code2,"code3":code3,"code4":code4,"companyId":companyId};

            $.get("<%=request.getContextPath()%>/views/vote/getaccess", {"question1": question1, "question2": question2,"question3":question3,"question4":question4,"code1":code1,"code2":code2,"code3":code3,"code4":code4,"companyId":companyId}, function(result) {

             if(result==="ok"){
                    
               location.href="<%=request.getContextPath()%>/views/vote/list";
             }else{
               $("#success-message").html(result);
                $('#submitResponse').css("display", "block");
                $('#submitResponse').focus();   
             }
            });
    event.preventDefault();
        });
    });
</script>

</html>