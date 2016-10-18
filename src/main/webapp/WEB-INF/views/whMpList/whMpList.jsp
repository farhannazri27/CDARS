<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/dataTables.tableTools.css" type="text/css" />
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style>
            th { font-size: 12px; }
            td { font-size: 11px; }
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>Warehouse Management - Material Pass List</h1>
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <h2 class="pull-left">Material Pass List</h2>

                            <div class="filter-block pull-right">
                                <a href="${contextPath}/wh/whShipping/whMpList/add" class="btn btn-primary pull-right">
                                    <i class="fa fa-plus-circle fa-lg"></i> Add New Material Pass Number
                                </a>
                            </div>
                            <div class="filter-block pull-right">
                                <a href="#delete_modal" data-toggle="modal" class="btn btn-danger danger group_delete pull-right" onclick="modalDelete(this);">
                                    <i class="fa fa-trash-o fa-lg"></i> Delete All
                                </a>
                            </div>
                        </div>
                        <!--<div class="alert_placeholder col-lg-4" >-->
                        <div class="alert alert-danger alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                            <b>*Please delete all data after print the shipping material pass number list.</b>
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
                                        <th><span>Material Pass Number</span></th>
                                        <th><span>Material Pass Expiry Date</span></th>
                                        <th><span>Hardware Type</span></th>
                                        <th><span>Hardware ID</span></th>
                                        <th><span>Quantity</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${whMpListList}" var="whMpList" varStatus="whMpListLoop">
                                        <tr>
                                            <td class="col-lg-1"><c:out value="${whMpListLoop.index+1}"/></td>
                                            <td class="col-lg-4"><c:out value="${whMpList.mpNo}"/></td>
                                            <td class="col-lg-2"><c:out value="${whMpList.viewMpExpiryDate}"/></td>
                                            <td class="col-lg-1"><c:out value="${whMpList.hardwareType}"/></td>
                                            <td class="col-lg-3"><c:out value="${whMpList.hardwareId}"/></td>
                                            <td class="col-lg-1"><c:out value="${whMpList.quantity}"/></td>
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
                                                {"bSortable": false, "aTargets": [5]}
                                            ],
                                            "sDom": "Bfrtip"
                                        });
                                        var exportTitle = "Material Pass List";
                                        var tt = new $.fn.dataTable.TableTools(oTable, {
                                            "sSwfPath": "${contextPath}/resources/private/datatables/swf/copy_csv_xls_pdf.swf",
                                            "aButtons": [
                                                {
                                                    "sExtends": "copy",
                                                    "sButtonText": "Copy",
                                                    "sTitle": exportTitle,
                                                    "mColumns": [0, 1, 2, 3, 4, 5]
                                                },
                                                {
                                                    "sExtends": "xls",
                                                    "sButtonText": "Excel",
                                                    "sTitle": exportTitle,
                                                    "mColumns": [0, 1, 2, 3, 4, 5]
                                                },
                                                {
                                                    "sExtends": "pdf",
                                                    "sButtonText": "PDF",
                                                    "sTitle": exportTitle,
                                                    "mColumns": [0, 1, 2, 3, 4, 5]
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

                                    function modalDelete(e) {
//                var deleteId = $(e).attr("modaldeleteid");
//                var deleteInfo = $("#modal_delete_info_" + deleteId).html();
                                        var deleteUrl = "${contextPath}/wh/whShipping/whMpList/changeFlag1";
                                        var deleteMsg = "Are you sure want to delete all? All related data will be deleted.";
                                        $("#delete_modal .modal-body").html(deleteMsg);
                                        $("#modal_delete_button").attr("href", deleteUrl);
                                    }


        </script>
    </s:layout-component>
</s:layout-render>