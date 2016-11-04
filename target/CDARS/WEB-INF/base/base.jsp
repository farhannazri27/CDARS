<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/base/taglibs.jsp" %>

<s:layout-definition>
    <!DOCTYPE html>
    <html>
        <head>
            <meta charset="UTF-8" />
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">


            <!--<title>HMS - Rel Lab</title>-->
            <title>HIMS RL</title>

            <!-- bootstrap -->
            <link href="${contextPath}/resources/private/css/bootstrap/bootstrap.css" rel="stylesheet" />

            <!--lighter readonly form-control-->
            <link href="${contextPath}/resources/private/css/bootstrap/readonly.css" rel="stylesheet" />

            <!-- libraries -->
            <!-- <link href="${contextPath}/resources/private/css/libs/jquery-ui-1.10.2.custom.css" rel="stylesheet" type="text/css" /> -->
            <link href="${contextPath}/resources/private/css/libs/font-awesome.css" type="text/css" rel="stylesheet" />

            <!-- global styles -->
            <link rel="stylesheet" type="text/css" href="${contextPath}/resources/private/css/compiled/layout.css">
            <link rel="stylesheet" type="text/css" href="${contextPath}/resources/private/css/compiled/elements.css">
            <link rel="stylesheet" type="text/css" href="${contextPath}/resources/private/css/sweetalert.css">

            <!-- this page specific styles -->
            <s:layout-component name="page_css">
            </s:layout-component>

            <style>
                label.error, select.error {
                    color: #DD504C;
                }
                input.error, select.error, textarea.error {
                    border-color: #DD504C;
                }
                input.error::-webkit-input-placeholder, select.error::-webkit-input-placeholder, textarea.error::-webkit-input-placeholder {
                    color: #DD504C;
                }
                input.error:-moz-placeholder, select.error:-moz-placeholder, textarea.error:-moz-placeholder {
                    /* FF 4-18 */
                    color: #DD504C;
                }
                input.error::-moz-placeholder, select.error::-moz-placeholder, textarea.error::-moz-placeholder {
                    /* FF 19+ */
                    color: #DD504C;
                }
                input.error:-ms-input-placeholder, select.error:-ms-input-placeholder, textarea.error:-ms-input-placeholder {
                    /* IE 10+ */
                    color: #DD504C;
                }
                input.error:focus, select.error:focus, textarea.error:focus {
                    border-color: #DD504C;
                    box-shadow: 0 0 5px rgba(221, 80, 76, 1);
                }
                //select2 error
                //div.select2-container.error {
                //border-color: #DD504C;
                //}
                .modal-dialog {
                    position: absolute;
                    top: 30% !important;
                    left: 20% !important;
                    right: 20% !important;
                    bottom: 35% !important;
                }

                .modal-lg {
                    top: 10% !important;
                }
                .modal-small {
                    top: 10% !important;
                }
            </style>

            <s:layout-component name="page_css_inline">
            </s:layout-component>

            <!-- Favicon -->
            <link type="image/x-icon" href="${contextPath}/resources/img/favicondddd.ico" rel="shortcut icon"/>

            <!-- google font libraries -->
            <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,600,700,300|Titillium+Web:200,300,400' rel='stylesheet' type='text/css'>

            <!--[if lt IE 9]>
                    <script src="${contextPath}/resources/private/js/html5shiv.js"></script>
                    <script src="${contextPath}/resources/private/js/respond.min.js"></script>
            <![endif]-->
            <!--[if lt IE 8]>
                    <link href="${contextPath}/resources/private/css/libs/font-awesome-ie7.min.css" type="text/css" rel="stylesheet" />
            <![endif]-->
        </head>
        <body>
            <header class="navbar" id="header-navbar">
                <div class="container">
                    <a href="${contextPath}/" id="logo" class="navbar-brand col-md-6 col-sm-6 col-xs-12">
                        <!--<span>Hardware Management System (HMS) - Rel Lab</span>-->
                        <span>Hardware and Inventory Management System (HIMS RL)</span>
                    </a>

                    <button class="navbar-toggle" data-target=".navbar-ex1-collapse" data-toggle="collapse" type="button">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="fa fa-bars"></span>
                    </button>

                    <div class="nav-no-collapse pull-right" id="header-nav">
                        <ul class="nav navbar-nav pull-right">
                            <li class="dropdown profile-dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <img src="${contextPath}/resources/private/img/samples/avatar.png" alt="" style="width: 35px; height: 35px;"/>
                                    <span class="hidden-xs">${sessionScope.userSession.fullname}</span> <b class="caret"></b>
                                </a>
                                <ul class="dropdown-menu">
                                    <li><a href="${contextPath}/profile"><i class="fa fa-user"></i>Profile</a></li>
                                    <li><a href="${contextPath}/logout"><i class="fa fa-power-off"></i>Logout</a></li>
                                </ul>
                            </li>
                            <li class="hidden-xxs">
                                <a href="${contextPath}/logout" class="btn">
                                    <i class="fa fa-power-off"></i>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </header>
            <div class="container">
                <div class="row">
                    <div class="col-md-2" id="nav-col">
                        <section id="col-left">
                            <div class="collapse navbar-collapse navbar-ex1-collapse" id="sidebar-nav">
                                ${userMenu}
                            </div>
                        </section>
                    </div>
                    <div class="col-md-10" id="content-wrapper">
                        <div class="row">
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger">
                                    <a class="close" data-dismiss="alert" href="#" aria-hidden="true">&times;</a>
                                    <strong>${error}</strong>
                                </div>
                            </c:if>
                            <c:if test="${not empty success}">
                                <div class="alert alert-success">
                                    <a class="close" data-dismiss="alert" href="#" aria-hidden="true">&times;</a>
                                    <strong>${success}</strong>
                                </div>
                            </c:if>
                            <s:layout-component name="page_container">
                            </s:layout-component>
                        </div>
                    </div>
                </div>
            </div>
            <footer id="footer-bar">
                <p id="footer-copyright">
                    Copyright &copy; 1999-2016, <a href="http://onsemi.com/" target="_blank">ON Semiconductor</a>.
                </p>
            </footer>
            <!-- Modal -->
            <div class="modal fade" id="delete_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">Delete Confirmation</h4>
                        </div>
                        <div class="modal-body">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                            <a id="modal_delete_button" href="#" class="btn btn-danger"><i class="fa fa-trash-o"></i> Delete</a>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <div class="modal fade" id="photo_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 id="modal_photo_title" class="modal-title">Photo Title</h4>
                        </div>
                        <div class="modal-body">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <div class="modal fade" id="photo_modal_small" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
                <div class="modal-dialog modal-small">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 id="modal_photo_small_title" class="modal-title">Photo Title</h4>
                        </div>
                        <div class="modal-body">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->

            <!-- global scripts -->
            <script src="${contextPath}/resources/private/js/jquery.js"></script>
            <script src="${contextPath}/resources/private/js/bootstrap.js"></script>
            <!--latest-->
            <script src="${contextPath}/resources/private/js/css3-mediaqueries.js"></script> 

            <!-- this page specific scripts -->
            <s:layout-component name="page_js">
            </s:layout-component>

            <!-- theme scripts -->
            <script src="${contextPath}/resources/private/js/scripts.js"></script>
            <!-- blockUI -->
            <script src="${contextPath}/resources/private/js/jquery.blockUI.js"></script>
            <!-- Swal -->
            <script src="${contextPath}/resources/private/js/sweetalert.min.js"></script>

            <script>
                $(document).ready(function () {
                    $("button[type=back]").click(function () {
                        history.go(-1);
                    });
                });
            </script>

            <!-- this page specific inline scripts -->
            <s:layout-component name="page_js_inline">
            </s:layout-component>

        </body>
    </html>

</s:layout-definition>