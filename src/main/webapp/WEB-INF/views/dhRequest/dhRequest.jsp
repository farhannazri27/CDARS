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
            <h1>Warehouse Management - HW Request</h1>
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <h2 class="pull-left">Hardware Request List</h2>

                            <div class="filter-block pull-right">
                                <a href="${contextPath}/dh/dhRequest/request" class="btn btn-primary pull-right">
                                    <i class="fa fa-plus-circle fa-lg"></i> Add New Request
                                </a>
                            </div>
                        </div>
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
                                        <th><span>Hardware From</span></th>
                                        <th><span>Hardware Type</span></th> 
                                        <th class="col-lg-2"><span>Hardware ID</span></th>
                                        <th ><span>Requested By</span></th>
                                        <th><span>Requested Date</span></th>
                                        <th class="col-lg-1"><span>Status</span></th>
                                        <th><span>Manage</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${whRequestList}" var="whRequest" varStatus="whRequestLoop">
                                        <tr>
                                            <td><c:out value="${whRequestLoop.index+1}"/></td>
                                            <td><c:out value="${whRequest.requestType}"/></td>
                                            <td><c:out value="${whRequest.equipmentType}"/></td>
                                            <c:choose>
                                                <c:when test="${whRequest.equipmentType == 'Load Card'}">
                                                    <td id="modal_delete_info_${whRequest.id}"><c:out value="${whRequest.loadCard}"/></td>
                                                </c:when>
                                                <c:when test="${whRequest.equipmentType == 'Program Card'}">
                                                    <td id="modal_delete_info_${whRequest.id}"><c:out value="${whRequest.programCard}"/></td>
                                                </c:when>
                                                <c:when test="${whRequest.equipmentType == 'Load Card & Program Card'}">
                                                    <td id="modal_delete_info_${whRequest.id}"><c:out value="${whRequest.loadCard}"/><br><c:out value="${whRequest.programCard}"/></td>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <td id="modal_delete_info_${whRequest.id}"><c:out value="${whRequest.equipmentId}"/></td>
                                                </c:otherwise>
                                            </c:choose>
                                            <td><c:out value="${whRequest.requestedBy}"/></td>
                                            <td><c:out value="${whRequest.requestedDateView}"/></td>
                                            <td><c:out value="${whRequest.status}"/></td>
                                            <td align="left">
                                                <c:if test="${groupId == '1' || groupId == '2' || groupId == '29' || groupId == '25'}">
                                                    <a href="${contextPath}/wh/whRequest/approval/${whRequest.id}" id="approval" name="approval" class="table-link" title="Approval">
                                                        <span class="fa-stack">
                                                            <i class="fa fa-square fa-stack-2x"></i>
                                                            <i class="fa fa-check fa-stack-1x fa-inverse"></i>
                                                        </span>
                                                    </a>
                                                </c:if>

                                                <a href="${contextPath}/wh/whRequest/view/${whRequest.id}" class="table-link" title="View">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                        <i class="fa fa-search-plus fa-stack-1x fa-inverse"></i>
                                                    </span>
                                                </a>
                                                <a href="${contextPath}/wh/whRequest/edit/${whRequest.id}" class="table-link" title="Edit">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                        <i class="fa fa-pencil fa-stack-1x fa-inverse"></i>
                                                    </span>
                                                </a>
                                                <c:if test="${groupId == '1' || groupId == '2' || groupId == '29'}">
                                                    <a modaldeleteid="${whRequest.id}" title="Delete" data-toggle="modal" href="#delete_modal" class="table-link danger group_delete" onclick="modalDelete(this);">
                                                        <span class="fa-stack">
                                                            <i class="fa fa-square fa-stack-2x"></i>
                                                            <i class="fa fa-trash-o fa-stack-1x fa-inverse"></i>
                                                        </span>
                                                    </a>
                                                </c:if>
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
//                oTable = $('#dt_spml').DataTable({
//                    "pageLength": 10,
//                    "order": [],
//                    "aoColumnDefs": [
//                        {"bSortable": false, "aTargets": [0]},
//                        {"bSortable": false, "aTargets": [7]}
//                    ],
//                    "sDom": "tp"
//                });
//                var exportTitle = "Hardware Request List";
//                var tt = new $.fn.dataTable.TableTools(oTable, {
//                    "sSwfPath": "${contextPath}/resources/private/datatables/swf/copy_csv_xls_pdf.swf",
//                    "aButtons": [
//                        {
//                            "sExtends": "copy",
//                            "sButtonText": "Copy",
//                            "sTitle": exportTitle,
//                            "mColumns": [0, 1, 2, 3, 4, 5, 6]
//                        },
//                        {
//                            "sExtends": "xls",
//                            "sButtonText": "Excel",
//                            "sTitle": exportTitle,
//                            "mColumns": [0, 1, 2, 3, 4, 5, 6]
//                        },
//                        {
//                            "sExtends": "pdf",
//                            "sButtonText": "PDF",
//                            "sTitle": exportTitle,
//                            "mColumns": [0, 1, 2, 3, 4, 5, 6]
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

                                                            oTable = $('#dt_spml').DataTable({
                                                                dom: 'Brtip',
                                                                buttons: [
                                                                    {
                                                                        extend: 'copy',
                                                                        exportOptions: {
                                                                            columns: [0, 1, 2, 3, 4, 5, 6]
                                                                        }
                                                                    },
                                                                    {
                                                                        extend: 'excel',
                                                                        exportOptions: {
                                                                            columns: [0, 1, 2, 3, 4, 5, 6]
                                                                        }
                                                                    },
                                                                    {
                                                                        extend: 'pdf',
                                                                        exportOptions: {
                                                                            columns: [0, 1, 2, 3, 4, 5, 6]
                                                                        }
                                                                    },
                                                                    {
                                                                        extend: 'print',
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

                                                            $('#dt_spml_search').keyup(function () {
                                                                oTable.search($(this).val()).draw();
                                                            });

                                                            $("#dt_spml_rows").change(function () {
                                                                oTable.page.len($(this).val()).draw();
                                                            });
                                                        });

                                                        function modalDelete(e) {
                                                            var deleteId = $(e).attr("modaldeleteid");
                                                            var deleteInfo = $("#modal_delete_info_" + deleteId).html();
                                                            var deleteUrl = "${contextPath}/wh/whRequest/delete/" + deleteId;
                                                            var deleteMsg = "<f:message key='general.label.delete.confirmation'><f:param value='" + deleteInfo + "'/></f:message>";
                                                            $("#delete_modal .modal-body").html(deleteMsg);
                                                            $("#modal_delete_button").attr("href", deleteUrl);
                                                        }
            </script>
    </s:layout-component>
</s:layout-render>