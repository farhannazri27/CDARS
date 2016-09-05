<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/dataTables.tableTools.css" type="text/css" />
    </s:layout-component>
    <s:layout-component name="page_css_inline">
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>Warehouse Management - Inventory</h1>
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <h2 class="pull-left">Inventory List</h2>
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
                                        <th><span>Hardware Type</span></th>
                                        <th><span>Hardware ID</span></th>
                                        <th><span>Quantity</span></th>
                                        <th><span>Material Pass No</span></th>
                                        <th><span>Rack</span></th>
                                        <th><span>Slot</span></th>
                                        <th><span>Inventory Date</span></th>
                                        <th><span>Manage</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${whInventoryList}" var="whInventory" varStatus="whInventoryLoop">
                                        <tr>
                                            <td><c:out value="${whInventoryLoop.index+1}"/></td>
                                            <td><c:out value="${whInventory.equipmentType}"/></td>
                                            <td><c:out value="${whInventory.equipmentId}"/></td>
                                            <td><c:out value="${whInventory.quantity}"/></td>
                                            <td><c:out value="${whInventory.mpNo}"/></td>
                                            <td><c:out value="${whInventory.invetoryRack}"/></td>
                                            <td><c:out value="${whInventory.inventorySlot}"/></td>
                                            <td><c:out value="${whInventory.viewInventoryDate}"/></td>
                                            <td align="left">
                                                <a href="${contextPath}/wh/whInventory/view/${whInventory.id}" class="table-link" title="View">
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
        <script src="${contextPath}/resources/private/datatables/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/dataTables.tableTools.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {

                oTable = $('#dt_spml').DataTable({
                    "pageLength": 10,
                    "order": [],
                    "aoColumnDefs": [
                        {"bSortable": false, "aTargets": [0]},
                        {"bSortable": false, "aTargets": [8]}
                    ],
                    "sDom": "tp"
                });
                var exportTitle = "Inventory List";
                var tt = new $.fn.dataTable.TableTools(oTable, {
                    "sSwfPath": "${contextPath}/resources/private/datatables/swf/copy_csv_xls_pdf.swf",
                    "aButtons": [
                        {
                            "sExtends": "copy",
                            "sButtonText": "Copy",
                            "sTitle": exportTitle,
                            "mColumns": [0, 1, 2, 3, 4, 5, 6, 7]
                        },
                        {
                            "sExtends": "xls",
                            "sButtonText": "Excel",
                            "sTitle": exportTitle,
                            "mColumns": [0, 1, 2, 3, 4, 5, 6, 7]
                        },
                        {
                            "sExtends": "pdf",
                            "sButtonText": "PDF",
                            "sTitle": exportTitle,
                            "mColumns": [0, 1, 2, 3, 4, 5, 6, 7]
                        },
                        {
                            "sExtends": "print",
                            "sButtonText": "Print"
                        }
                    ]
                });
                $(tt.fnContainer()).appendTo("#dt_spml_tt");
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