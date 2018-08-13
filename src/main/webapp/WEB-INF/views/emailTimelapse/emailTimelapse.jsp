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
            <h1>Email Time-lapse</h1>
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <h2 class="pull-left">Email List</h2>

                            <div class="filter-block pull-right">
                                <a href="${contextPath}/whTimelapse/emailTimelapse/add" class="btn btn-primary pull-right">
                                    <i class="fa fa-plus-circle fa-lg"></i> Add New email 
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
                                        <th><span>Dist List/CC</span></th>
                                        <th><span>User</span></th> 
                                        <th><span>Email</span></th>
                                        <th><span>Manage</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${emailTimelapseList}" var="emailConfig" varStatus="emailConfigtLoop">
                                        <tr>
                                            <td><c:out value="${emailConfigtLoop.index+1}"/></td>
                                            <td><c:out value="${emailConfig.sendCc}"/></td>
                                            <td><c:out value="${emailConfig.userName}"/></td>
                                            <td id="modal_delete_info_${emailConfig.id}"><c:out value="${emailConfig.email}"/></td>
                                            <td align="center">
                                                <a href="${contextPath}/whTimelapse/emailTimelapse/edit/${emailConfig.id}" class="table-link" title="Edit">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                        <i class="fa fa-pencil fa-stack-1x fa-inverse"></i>
                                                    </span>
                                                </a>
                                                <c:if test="${groupId == '1'}">
                                                    <a modaldeleteid="${emailConfig.id}" title="Delete" data-toggle="modal" href="#delete_modal" class="table-link danger group_delete" onclick="modalDelete(this);">
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
                                                                    {"bSortable": false, "aTargets": [4]}
                                                                ],
                                                                "sDom": "tp"
                                                            });
                                                            var exportTitle = "Email Time-lapse List";
                                                            var tt = new $.fn.dataTable.TableTools(oTable, {
                                                                "sSwfPath": "${contextPath}/resources/private/datatables/swf/copy_csv_xls_pdf.swf",
                                                                "aButtons": [
                                                                    {
                                                                        "sExtends": "copy",
                                                                        "sButtonText": "Copy",
                                                                        "sTitle": exportTitle,
                                                                        "mColumns": [0, 1, 2, 3, 4]
                                                                    },
                                                                    {
                                                                        "sExtends": "xls",
                                                                        "sButtonText": "Excel",
                                                                        "sTitle": exportTitle,
                                                                        "mColumns": [0, 1, 2, 3, 4]
                                                                    },
                                                                    {
                                                                        "sExtends": "pdf",
                                                                        "sButtonText": "PDF",
                                                                        "sTitle": exportTitle,
                                                                        "mColumns": [0, 1, 2, 3, 4]
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
                                                            var deleteId = $(e).attr("modaldeleteid");
                                                            var deleteInfo = $("#modal_delete_info_" + deleteId).html();
                                                            var deleteUrl = "${contextPath}/whTimelapse/emailTimelapse/delete/" + deleteId;
                                                            var deleteMsg = "<f:message key='general.label.delete.confirmation'><f:param value='" + deleteInfo + "'/></f:message>";
                                                            $("#delete_modal .modal-body").html(deleteMsg);
                                                            $("#modal_delete_button").attr("href", deleteUrl);
                                                        }
            </script>
    </s:layout-component>
</s:layout-render>