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
            <h1>Query</h1>
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box">
                        <h2>Query Requirements Search</h2>
                        <form id="update_hardwareinventory_form" class="form-horizontal" role="form" action="${contextPath}/wh/query" method="post" style="width: 100%">
                            <div class="form-group col-lg-12" style="font-style: italic; color: red;" >
                                *Please insert the requirement(s) accordingly.</font
                                <br />
                            </div>

                            <div class="form-group col-lg-12" id = "alert_placeholder"></div>
                            <div class="form-group col-lg-11" >
                                <label for="equipmentType" class="col-lg-2 control-label">Hardware Category</label>
                                <div class="col-lg-3">
                                    <select id="equipmentType" name="equipmentType" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${hardwareTypeList}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="equipmentId" class="col-lg-2 control-label">Hardware ID</label>
                                <div class="col-lg-5">                                      
                                    <select id="equipmentId" name="equipmentId" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${eqptIdList}" var="group">
                                            <option value="${group.equipmentId}" ${group.selected}>${group.equipmentId}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group col-lg-11">
                                <label for="materialPassNo" class="col-lg-2 control-label">Material Pass No.</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="materialPassNo" name="materialPassNo">
                                </div>
                                <label for="materialPassExpiry1" class="col-lg-2 control-label">M/Pass Expiry Date BETWEEN </label>
                                <div class="col-lg-2">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" name="materialPassExpiry1" class="form-control" id="materialPassExpiry1" value="">
                                    </div>
                                    <label id="datepickerDate-error" class="error" for="materialPassExpiry1" style="display: none;"></label>
                                </div>
                                <label for="materialPassExpiry2" class="col-lg-1 control-label" style="text-align: center;"> AND </label>
                                <div class="col-lg-2">
                                    <!--<input type="text" class="form-control" id="materialPassExpiry2" name="materialPassExpiry2">-->
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" name="materialPassExpiry2" class="form-control" id="materialPassExpiry2" value="">
                                    </div>
                                    <label id="datepickerDate-error" class="error" for="materialPassExpiry2" style="display: none;"></label>
                                </div>
                            </div>
                            <!--<div class="form-group col-lg-12" ></div>-->
                            <div class="form-group col-lg-11">
                                <label for="requestedBy" class="col-lg-2 control-label">Requested By</label>
                                <div class="col-lg-3">                                      
                                    <select id="requestedBy" name="requestedBy" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${requestedByList}" var="group">
                                            <option value="${group.requestedBy}" ${group.selected}>${group.requestedBy}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="requestedDate1" class="col-lg-2 control-label">Requested Date BETWEEN </label>
                                <div class="col-lg-2">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" name="requestedDate1" class="form-control" id="requestedDate1" value="">
                                    </div>
                                    <label id="datepickerDate-error" class="error" for="requestedDate1" style="display: none;"></label>
                                </div>
                                <label for="requestedDate2" class="col-lg-1 control-label" style="text-align: center;">AND</label>
                                <div class="col-lg-2">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" name="requestedDate2" class="form-control" id="requestedDate2" value="">
                                    </div>
                                    <label id="datepickerDate-error" class="error" for="requestedDate2" style="display: none;"></label>
                                </div>

                            </div>
                            <div class="form-group col-lg-11">
                                <label for="status" class="col-lg-2 control-label">Status</label>
                                <div class="col-lg-3">                                      
                                    <select id="status" name="status" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${statusList}" var="group">
                                            <option value="${group.status}" ${group.selected}>${group.status}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="retrievalReason" class="col-lg-2 control-label">Retrieve for</label>
                                <div class="col-lg-3">                                      
                                    <select id="retrievalReason" name="retrievalReason" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${retrievalReasonList}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>                            
                            <div class="col-lg-12">
                                <br/>
                            </div>
                            <div class="col-lg-12">
                                <!--<a href="${contextPath}/wh/whRetrieve" class="btn btn-info pull-left" id="cancel"><i class="fa fa-reply"></i>Back</a>-->
                                <button type="submit" class="btn btn-primary pull-right" name="submit" id="submit" >View Data</button>
                            </div>
                            <div class="clearfix"><br/></div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-lg-12">
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <!--                        <div class="clearfix">
                                                    <h2 class="pull-left">Query Search List</h2>
                                                </div>-->
                        <hr/>
                        <div class="clearfix">
                            <div class="form-group pull-left">
                                <select id="dt_spml_rows" class="form-control">
                                    <option value="10">10</option>
                                    <option value="25">25</option>
                                    <option value="50">50</option>
                                    <option value="100">100</option>
                                </select>
                            </div>
                            <div class="filter-block pull-right">
                                <div id="dt_spml_tt" class="form-group pull-left" style="margin-right: 5px;"></div>
                                <div class="form-group pull-left" style="margin-right: 0px;">
                                    <input id="dt_spml_search" type="text" class="form-control" placeholder="<f:message key="general.label.search"/>">
                                    <i class="fa fa-search search-icon"></i>
                                </div>
                            </div>
                        </div>
                        <!--<div><br/></div>-->
                        <div class="table-responsive">            
                            <!--<table id="dt_spml" class="table" style="font-size: 10px;">-->
                            <table id="dt_spml" class="table">
                                <thead>
                                    <tr>
                                        <th><span>No</span></th>
                                        <th><span>Hardware Type</span></th>
                                        <th><span>Hardware ID</span></th>
                                        <th><span>Material Pass No</span></th> 
                                        <th><span>Material Pass Expiry</span></th>
                                        <!--<th><span>Quantity</span></th>-->
                                        <th><span>Requested By</span></th>
                                        <th><span>Requested Date</span></th>
                                        <th><span>Status</span></th>
                                        <th><span>Log</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${retrieveQueryList}" var="whRetrieve" varStatus="whRetrieveLoop">
                                        <tr>
                                            <td><c:out value="${whRetrieveLoop.index+1}"/></td>
                                            <td><c:out value="${whRetrieve.equipmentType}"/></td>
                                            <td><c:out value="${whRetrieve.equipmentId}"/></td>
                                            <td><c:out value="${whRetrieve.mpNo}"/></td>
                                            <td><c:out value="${whRetrieve.mpExpiryDateView}"/></td>
                                            <!--<td><c:out value="${whRetrieve.quantity}"/></td>-->
                                            <td><c:out value="${whRetrieve.requestedBy}"/></td>
                                            <td><c:out value="${whRetrieve.requestedDateView}"/></td>
                                            <td><c:out value="${whRetrieve.status}"/></td>
                                            <td align="left">
                                                <a href="${contextPath}/wh/whRequest/query/view/${whRetrieve.id}" class="table-link" title="Log">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                        <i class="fa fa-search-plus fa-stack-1x fa-inverse"></i>
                                                    </span>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
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

                $(".js-example-basic-single").select2({
                    placeholder: "Choose one",
                    allowClear: true
                });
                $('#requestedDate1').datepicker({
                    format: 'yyyy-mm-dd',
                    autoclose: true
                });

                $('#requestedDate2').datepicker({
                    format: 'yyyy-mm-dd',
                    autoclose: true
                });

                $('#materialPassExpiry1').datepicker({
                    format: 'yyyy-mm-dd',
                    autoclose: true
                });

                $('#materialPassExpiry2').datepicker({
                    format: 'yyyy-mm-dd',
                    autoclose: true
                });

                var validator = $("#update_hardwareinventory_form").validate({
                    rules: {
                        requestedDate1: {
                            required: function (element) {
                                if ($('#requestedDate1').val() === "" && $('#requestedDate2').val() !== "") {
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        },
                        requestedDate2: {
                            required: function (element) {
                                if ($('#requestedDate2').val() === "" && $('#requestedDate1').val() !== "") {
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        },
                        materialPassExpiry1: {
                            required: function (element) {
                                if ($('#materialPassExpiry1').val() === "" && $('#materialPassExpiry2').val() !== "") {
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        },
                        materialPassExpiry2: {
                            required: function (element) {
                                if ($('#materialPassExpiry2').val() === "" && $('#materialPassExpiry1').val() !== "") {
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        }
                    }
                });

                $(".cancel").click(function () {
                    validator.resetForm();
                });

                $(".submit").click(function () {
                    $("#data").show();
                });

//                oTable = $('#dt_spml').DataTable({
//                    dom: 'Bfrtip',
//                    buttons: [
//                        'print'
//                    ]
//                });
                oTable = $('#dt_spml').DataTable({
                    dom: 'Brtip',
                    buttons: [
                        {
                            extend: 'copy'
                        },
                        {
                            extend: 'excel'
                        },
                        {
                            extend: 'pdf'
                        },
                        {
                            extend: 'print',
                            customize: function (win) {
                                $(win.document.body)
                                        .css('font-size', '10pt')
                                $(win.document.body).find('table')
                                        .addClass('compact')
                                        .css('font-size', 'inherit');
                            }
                        }
                    ]
                });

//                oTable.buttons().container().appendTo($("#dt_spml_tt", oTable.table().container() ) );

                $('#dt_spml_search').keyup(function () {
                    oTable.search($(this).val()).draw();
                });

                $("#dt_spml_rows").change(function () {
                    oTable.page.len($(this).val()).draw();
                });
            });
        </script>
    </s:layout-component>
</s:layout-render>