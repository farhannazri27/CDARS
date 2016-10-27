<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>SPML - Register</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="Sistem Pengurusan Maklumat Latihan">
        <meta name="author" content="FTC">

        <!-- Le styles -->
        <link href="${contextPath}/resources/public/css/bootstrap.min.css" rel="stylesheet">
        <link href="${contextPath}/resources/public/css/bootstrap-responsive.min.css" rel="stylesheet">

        <link rel="stylesheet" href="${contextPath}/resources/public/css/typica-login.css">

        <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
          <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->

        <!-- Le favicon -->
        <link rel="shortcut icon" href="${contextPath}/resources/img/favicondddd.ico">

    </head>

    <body>

        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </a>
                    <center style="margin-top: 30px;"><a href="${contextPath}/" style="font-family: 'Lato'; font-size: 22px;">Sistem Pengurusan Maklumat Latihan (SPML)</a></center>
                </div>
            </div>
        </div>

        <div class="container">
            <div class="row">

                <div class="span6">

                    <div class="register-info-wraper">
                        <div id="register-info">

                            <h1>Become our Strategic Partner in providing the Training for our students!</h1><br/><br/><br/><br/><br/><br/>

                            <ul dir="rtl">
                                <li>Register</li>
                                <li>Wait for Approval</li>
                                <li>Approved</li>
                                <li>Provide Training</li>
                            </ul>

                        </div>
                    </div>

                </div>

                <div class="span6">
                    <div id="register-wraper">
                        <form id="register-form" action="${contextPath}/register" method="post"  class="form">
                            <a href="${contextPath}/">
                                <img src="${contextPath}/resources/img/cdars_logo.png" alt="FTC" width="35%" />
                            </a>
                            <legend>Register to <span class="blue">CDARS</span></legend>

                            <div class="body">
                                <!-- full name -->
                                <label>Full Name / Company Name</label>
                                <input class="input-huge" type="text">
                                <!-- username -->
                                <label>Username</label>
                                <input class="input-huge" type="text">
                                <!-- email -->
                                <label>E-mail</label>
                                <input class="input-huge" type="text">
                                <!-- password -->
                                <label>Password</label>
                                <input class="input-huge" type="text">

                            </div>

                            <div class="footer">
                                <!--<label class="checkbox inline">
                                    <input type="checkbox" id="inlineCheckbox1" value="option1"> I agree to something I will never read
                                </label>-->
                                <button type="submit" class="btn btn-success">Register</button>
                            </div>
                        </form>
                    </div>
                </div>

            </div>
        </div>

        <footer class="white navbar-fixed-bottom">
            Already registered as Strategic Partner?&nbsp;&nbsp;<a href="${contextPath}/" class="btn btn-black">Sign in</a>
        </footer>


        <!-- Le javascript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${contextPath}/resources/public/js/jquery.js"></script>
        <script src="${contextPath}/resources/public/js/bootstrap.js"></script>
        <script src="${contextPath}/resources/public/js/backstretch.min.js"></script>
        <script src="${contextPath}/resources/public/js/typica-login.js"></script>

    </body>
</html>

