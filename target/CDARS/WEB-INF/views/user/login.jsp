<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>

        <title>CMTS</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta name="description" content="">
        <meta name="author" content="" />

        <link rel="shortcut icon" href="${contextPath}/resources/img/favicon.ico" />
        <link rel="stylesheet" href="${contextPath}/resources/css/opensans.css" />
        <!--<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,800italic,400,600,800" type="text/css">-->
        <link rel="stylesheet" href="${contextPath}/resources/css/font-awesome.min.css" type="text/css" />		
        <link rel="stylesheet" href="${contextPath}/resources/css/bootstrap.min.css" type="text/css" />	
        <link rel="stylesheet" href="${contextPath}/resources/js/libs/css/ui-lightness/jquery-ui-1.9.2.custom.css" type="text/css" />	

        <link rel="stylesheet" href="${contextPath}/resources/css/App.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/css/Login.css" type="text/css" />

        <link rel="stylesheet" href="${contextPath}/resources/css/custom.css" type="text/css" />

    </head>

    <body>

        <div id="login-container">

            <div id="logo" style="background-color: #B6B1B0;">
                <a href="${contextPath}/">
                    <img src="${contextPath}/resources/img/ftc.png" alt="Logo" width="90%" />
                </a>
            </div>

            <div id="login">

                <h3>Course Management & Tracking System</h3>

                <h5>Please sign in to get access.</h5>

                <form id="login-form" action="${contextPath}/" class="form" method="post">

                    <div class="form-group">
                        <label for="login-username">Username</label>
                        <input type="text" class="form-control" id="login-username" placeholder="Username" name="username">
                    </div>

                    <div class="form-group">
                        <label for="login-password">Password</label>
                        <input type="password" class="form-control" id="login-password" placeholder="Password" name="password">
                    </div>

                    <div class="form-group">
                        <button type="submit" id="login-btn" class="btn btn-primary btn-block">Signin &nbsp; <i class="fa fa-play-circle"></i></button>
                    </div>
                    
                </form>

                <a href="javascript:;" class="btn btn-default">Forgot Password?</a>

            </div> <!-- /#login -->

        </div> <!-- /#login-container -->

        <script src="${contextPath}/resources/js/libs/jquery-1.9.1.min.js"></script>
        <script src="${contextPath}/resources/js/libs/jquery-ui-1.9.2.custom.min.js"></script>
        <script src="${contextPath}/resources/js/libs/bootstrap.min.js"></script>

        <script src="${contextPath}/resources/js/App.js"></script>

        <script src="${contextPath}/resources/js/Login.js"></script>

    </body>
</html>