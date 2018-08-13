<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/select2.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/bootstrap-select.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/buttons.dataTables.min.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style>
            @media print {
                /*                table  {
                                    border-top: #000 solid 1px;
                                    border-bottom: #000 solid 1px;
                                    border-left: #000 solid 1px;
                                    border-right: #000 solid 1px;
                                }*/
                table thead {
                    border-top: #000 solid 2px;
                    border-bottom: #000 solid 2px;
                }
                table tbody {
                    border-top: #000 solid 2px;
                    border-bottom: #000 solid 2px;
                }
            }
            .dataTables_wrapper .dt-buttons {
                float:none;  
                text-align:right;
            }

            .select2-container-active .select2-choice,
            .select2-container-active .select2-choices {
                border: 1px solid $input-border-focus !important;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
            }

            .select2-dropdown-open .select2-choice {
                border-bottom: 0 !important;
                background-image: none;
                background-color: #fff;
                filter: none;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
            }

            .select2-dropdown-open.select2-drop-above .select2-choice,
            .select2-dropdown-open.select2-drop-above .select2-choices {
                border: 1px solid $input-border-focus !important;
                border-top: 0 !important;
                background-image: none;
                background-color: #fff;
                filter: none;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
            }

            .no-border {
                border: 0;
                box-shadow: none; /* You may want to include this as bootstrap applies these styles too */
            }
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>Time-Lapse Report</h1>
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box">
                        <h2>Query Requirements Search</h2>
                        <form id="update_hardwareinventory_form" class="form-horizontal" role="form" action="${contextPath}/whTimelapse/timeLapse/downloadExcel" method="post" style="width: 100%">
                            <div class="form-group col-lg-12" style="font-style: italic; color: red;" >
                                *Please insert the requirement(s) accordingly.</font
                                <br />
                            </div>

                            <div class="form-group col-lg-12" id = "alert_placeholder"></div>
                            <div class="form-group col-lg-11" >
                                <label for="requestType" class="col-lg-2 control-label">Ship/Retrieve*</label>
                                <div class="col-lg-3">
                                    <select id="requestType" name="requestType" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${requestTypeList}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                        <option value="all">Ship & Retrieve</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group col-lg-11" >
                                <label for="equipmentType" class="col-lg-2 control-label">Hardware Category*</label>
                                <div class="col-lg-3">
                                    <select id="equipmentType" name="equipmentType" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <option value="%%">All</option>
                                        <c:forEach items="${hardwareTypeList}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <!--<div class="form-group col-lg-12" ></div>-->
                            <div class="form-group col-lg-11">
                                <label for="dateStart" class="col-lg-2 control-label">Report Month End* </label>
                                <div class="col-lg-3">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" name="dateStart" class="form-control" id="dateStart" value="">
                                    </div>
                                    <label id="datepickerDate-error" class="error" for="dateStart" style="display: none;"></label>
                                </div>
                            </div>    
                            <!--                            <div class="form-group col-lg-11" >
                                                            <label for="sentTo" class="col-lg-2 control-label">Sent To*</label>
                                                            <div class="col-lg-5">
                                                                <select id="sentTo" name="sentTo" class="js-example-basic-multiple" multiple="multiple" style="width: 100%">
                            <c:forEach items="${user}" var="group">
                                <option email="${group.email}" value="${group.email}" ${group.selected}>${group.firstname} ${group.lastname}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>-->
                            <div class="col-lg-12">
                                <br/>
                            </div>
                            <div class="col-lg-12">
                                <!--<a href="${contextPath}/wh/whRetrieve" class="btn btn-info pull-left" id="cancel"><i class="fa fa-reply"></i>Back</a>-->
                                <button type="submit" class="btn btn-primary pull-right" name="submit" id="submit" >Export to Excel</button>
                            </div>
                            <div class="clearfix"><br/></div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/private/js/select2.min.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-select.js"></script>
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/resources/validation/additional-methods.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-datepicker.js"></script>

        <!--print-->
        <script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/dataTables.buttons.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.print.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.flash.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.html5.min.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {

//                var delay = (function () {
//                    var timer = 0;
//                    return function (callback, ms) {
//                        clearTimeout(timer);
//                        timer = setTimeout(callback, ms);
//                    };
//                })();
//
//                $("#email").on("input", function () {
//                    delay(function () {
//                        if ($("#email").val().length < 5) {
//                            $("#email").val("");
//                        }
//                    }, 20);
//                });

                $(".js-example-basic-single").select2({
                    placeholder: "Choose one",
                    allowClear: true
                });

                $(".js-example-basic-multiple").select2({
                    placeholder: "Choose one",
                    allowClear: true
                });

                $(".js-example-basic-tag").select2({
                    tags: true,
                    tokenSeparators: [',', ' '],
                    allowClear: true
                });

                $('#dateStart').datepicker({
                    minViewMode: 1,
                    format: 'mm/yyyy',
                    endDate: '+0d',
                    autoclose: true
                });


                var validator = $("#update_hardwareinventory_form").validate({
                    rules: {
                        dateStart: {
                            required: true
                        },
                        sentTo: {
                            required: true
                        },
                        requestType: {
                            required: true
                        },
                        equipmentType: {
                            required: true
                        }
                    }
                });

                $(".cancel").click(function () {
                    validator.resetForm();
                });

//                $(".submit").click(function () {
//                    $("#data").show();
//                });

//                oTable = $('#dt_spml').DataTable({
//                    dom: 'Bfrtip',
//                    buttons: [
//                        'print'
//                    ]
//                });
//                oTable = $('#dt_spml').DataTable({
//                    dom: 'Brtip',
//                    buttons: [
//                        {
//                            extend: 'copy',
//                            exportOptions: {
//                                columns: [0, 1, 2, 3, 4, 5, 6, 7]
//                            }
//                        },
//                        {
//                            extend: 'excel',
//                            exportOptions: {
//                                columns: [0, 1, 2, 3, 4, 5, 6, 7]
//                            }
//                        },
//                        {
//                            extend: 'pdf',
//                            exportOptions: {
//                                columns: [0, 1, 2, 3, 4, 5, 6, 7]
//                            }
//                        },
//                        {
//                            extend: 'print',
//                            exportOptions: {
//                                columns: [0, 1, 2, 3, 4, 5, 6, 7]
//                            },
//                            customize: function (win) {
//                                $(win.document.body)
//                                        .css('font-size', '10pt');
//                                $(win.document.body).find('table')
//                                        .addClass('compact')
//                                        .css('font-size', 'inherit');
//                            }
//                        }
//                    ]
//                });
//
////                oTable.buttons().container().appendTo($("#dt_spml_tt", oTable.table().container() ) );
//
//                $('#dt_spml_search').keyup(function () {
//                    oTable.search($(this).val()).draw();
//                });
//
//                $("#dt_spml_rows").change(function () {
//                    oTable.page.len($(this).val()).draw();
//                });
            });
        </script>
    </s:layout-component>
</s:layout-render>