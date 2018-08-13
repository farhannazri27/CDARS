<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
<!--        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/dataTables.tableTools.css" type="text/css" />-->
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/buttons.dataTables.min.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style>
            @media print {
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


        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>Time Lapse - Pending Action</h1>
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <h2 class="pull-left">Time Lapse List</h2>
                        </div>
                        <div class="filter-block pull-left">
                            <a href="${contextPath}/whTimelapse/retrieve" class="btn btn-primary pull-right">
                                <i class="fa fa-reply"></i> Back
                            </a>
                        </div><br>
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
                                <div id="dt_spml_tt" class="form-group pull-left" style="margin-right: 5px;">
                                </div>
                                <div class="form-group pull-left" style="margin-right: 0px;">
                                    <input id="dt_spml_search" type="text" class="form-control" placeholder="<f:message key="general.label.search"/>">
                                    <i class="fa fa-search search-icon"></i>
                                </div>
                            </div>
                        </div>
                        <div class="table-responsive">
                            <table id="dt_spml" class="table">
                                <thead>
                                    <tr>
                                        <th><span>No</span></th>
                                        <th><span>Hardware Type</span></th>
                                        <th><span>Hardware ID</span></th>
                                        <th><span>MP No</span></th>
                                        <th><span>Requested Date</span></th>
                                        <th><span>Duration (hrs)</span></th>
                                        <th><span>Process over USL</span></th>
                                        <th><span>Manage</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${surrogate}" var="whInventory" varStatus="whInventoryLoop">
                                        <tr>
                                            <td><c:out value="${whInventoryLoop.index+1}"/></td>
                                            <td><c:out value="${whInventory.eqptType}"/></td>
                                            <c:choose>
                                                <c:when test="${whInventory.eqptType == 'Load Card'}">
                                                    <td id="modal_delete_info_${whInventory.requestId}"><c:out value="${whInventory.loadCard}"/></td>
                                                </c:when>
                                                <c:when test="${whInventory.eqptType == 'Program Card'}">
                                                    <td id="modal_delete_info_${whInventory.requestId}"><c:out value="${whInventory.programCard}"/></td>
                                                </c:when>
                                                <c:when test="${whInventory.eqptType == 'Load Card & Program Card'}">
                                                    <td id="modal_delete_info_${whInventory.requestId}"><c:out value="${whInventory.loadCard}"/><br><c:out value="${whInventory.programCard}"/></td>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <td id="modal_delete_info_${whInventory.requestId}"><c:out value="${whInventory.eqptId}"/></td>
                                                </c:otherwise>
                                            </c:choose>
                                            <td><c:out value="${whInventory.mpNo}"/></td>
                                            <td><c:out value="${whInventory.requestDate}"/></td>
                                            <td><c:out value="${whInventory.totalHourTakeShip}"/></td>
                                            <td><c:out value="${whInventory.ca}"/></td>
                                            <td align="left">
                                                <a href="${contextPath}/whTimelapse/manageRetrieve2/${whInventory.requestId}" class="table-link" title="Manage">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                        <i class="fa fa-pencil fa-stack-1x fa-inverse"></i>
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
<!--        <script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/dataTables.tableTools.js"></script>-->

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

                oTable = $('#dt_spml').DataTable({
                    dom: 'Brtip',
                    buttons: [
                        {
                            extend: 'copy',
                            exportOptions: {
                                columns: [0, 1, 2, 3, 4, 5, 6, 7]
                            }
                        },
                        {
                            extend: 'excel',
                            exportOptions: {
                                columns: [0, 1, 2, 3, 4, 5, 6, 7]
                            }
                        },
                        {
                            extend: 'pdf',
                            exportOptions: {
                                columns: [0, 1, 2, 3, 4, 5, 6, 7]
                            }
                        },
                        {
                            extend: 'print',
                            exportOptions: {
                                columns: [0, 1, 2, 3, 4, 5, 6, 7]
                            },
                            customize: function (win) {
                                $(win.document.body)
                                        .css('font-size', '10pt');
                                $(win.document.body).find('table')
                                        .addClass('compact')
                                        .css('font-size', 'inherit');
                            }
                        }
                    ]
                });


                oTable
                        .order([1, 'asc'], [2, 'asc'])
                        .draw();

//                oTable.buttons().container().appendTo($("#dt_spml_tt", oTable.table().container() ) );

                $('#dt_spml_search').keyup(function () {
                    oTable.search($(this).val()).draw();
                });

                $("#dt_spml_rows").change(function () {
                    oTable.page.len($(this).val()).draw();
                });

//                oTable = $('#dt_spml').DataTable({
//                    "pageLength": 10,
//                    "order": [],
//                    "aoColumnDefs": [
//                        {"bSortable": false, "aTargets": [0]},
//                        {"bSortable": false, "aTargets": [8]}
//                    ],
//                    "sDom": "tp"
//                });
//                var exportTitle = "Inventory List";
//                var tt = new $.fn.dataTable.TableTools(oTable, {
//                    "sSwfPath": "${contextPath}/resources/private/datatables/swf/copy_csv_xls_pdf.swf",
//                    "aButtons": [
//                        {
//                            "sExtends": "copy",
//                            "sButtonText": "Copy",
//                            "sTitle": exportTitle,
//                            "mColumns": [0, 1, 2, 3, 4, 5, 6,7]
//                        },
//                        {
//                            "sExtends": "xls",
//                            "sButtonText": "Excel",
//                            "sTitle": exportTitle,
//                            "mColumns": [0, 1, 2, 3, 4, 5, 6,7]
//                        },
//                        {
//                            "sExtends": "pdf",
//                            "sButtonText": "PDF",
//                            "sTitle": exportTitle,
//                            "mColumns": [0, 1, 2, 3, 4, 5, 6, 7,7]
//                        },
//                        {
//                            "sExtends": "print",
//                            "sButtonText": "Print"
//                        }
//                    ]
//                });
//                $(tt.fnContainer()).appendTo("#dt_spml_tt");
//                $('#dt_spml_search').keyup(function () {
//                    oTable.search($(this).val()).draw();
//                });
//                $("#dt_spml_rows").change(function () {
//                    oTable.page.len($(this).val()).draw();
//                });
            });

        </script>
    </s:layout-component>
</s:layout-render>