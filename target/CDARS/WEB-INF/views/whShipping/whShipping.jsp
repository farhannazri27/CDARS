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
            <h1>Warehouse Management - Shipping</h1>
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <h2 class="pull-left">Shipping List</h2>

                            <div class="filter-block pull-right">
                                <a href="${contextPath}/wh/whShipping/whMpList/s" class="btn btn-primary pull-right">
                                    <i class="fa fa-bars fa-lg"></i> Create Shipping Material Pass List
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
                                        <th><span>Hardware Type</span></th>
                                        <th><span>Hardware ID</span></th>
                                        <th><span>Quantity</span></th>
                                        <th><span>Material Pass No</span></th>
                                        <th><span>Requested By</span></th>
                                        <th><span>Requested Date</span></th>
                                        <th><span>Status</span></th>
                                        <th><span>Manage</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${whShippingList}" var="whShipping" varStatus="whShippingLoop">
                                        <tr>
                                            <td><c:out value="${whShippingLoop.index+1}"/></td>
                                            <td><c:out value="${whShipping.requestEquipmentType}"/></td>
                                            <td id="modal_delete_info_${whShipping.id}"><c:out value="${whShipping.requestEquipmentId}"/></td>
                                            <td><c:out value="${whShipping.requestQuantity}"/></td>
                                            <td><c:out value="${whShipping.mpNo}"/></td>
                                            <td><c:out value="${whShipping.requestRequestedBy}"/></td>
                                            <td><c:out value="${whShipping.requestViewRequestedDate}"/></td>
                                            <td><c:out value="${whShipping.status}"/></td>
                                            <td align="left">
                                                <a href="${contextPath}/wh/whShipping/edit/${whShipping.id}" class="table-link" title="Edit">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                        <i class="fa fa-pencil fa-stack-1x fa-inverse"></i>
                                                    </span>
                                                </a>
                                                <a href="${contextPath}/wh/whShipping/view/${whShipping.id}" class="table-link" title="Trip Ticket">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                        <i class="fa fa-ticket fa-stack-1x fa-inverse"></i>
                                                    </span>
                                                </a>
                                                <c:if test="${whShipping.status == 'Not Scan Barcode Sticker Yet' || whShipping.status == 'Verified' || whShipping.status == 'Trip Ticket and Barcode Sticker Not Match' || whShipping.status == 'Ship'}">
                                                    <!--<a href="${contextPath}/wh/whShipping/viewBarcodeSticker/${whShipping.id}" class="table-link" title="Barcode Sticker">-->
                                                        <a class="table-link" href="#" title="Barcode Sticker" onclick="window.open('${contextPath}/wh/whShipping/viewWhBarcodeStickerPdf/${whShipping.id}','Barcode Sticker','width=720,height=800').print()">
                                                        <span class="fa-stack">
                                                            <i class="fa fa-square fa-stack-2x"></i>
                                                            <i class="fa fa-barcode fa-stack-1x fa-inverse"></i>
                                                        </span>
                                                    </a>
                                                </c:if>
                                                <c:if test="${groupId == '1' || groupId == '2' || groupId == '29'}">
                                                    <a modaldeleteid="${whShipping.id}" data-toggle="modal" href="#delete_modal" class="table-link danger group_delete" onclick="modalDelete(this);">
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
                                                                    {"bSortable": false, "aTargets": [8]}
                                                                ],
                                                                "sDom": "tp"
                                                            });
                                                            var exportTitle = "Hardware Shipping List";
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

                                                        function printDiv() {
                                                           location.href = 'http://localhost:8080/CDARS/wh/whShipping/viewWhBarcodeStickerPdf/19';
                                                            newWin = window.open("");
                                                            newWin.document.write(location.href);
//                                                            newWin.print();
//                                                            newWin.close();

                                                        }

                                                        $('.printAndEmail').on('click', function () {
                                                            printDiv();
                                                        })

                                                        function modalDelete(e) {
                                                            var deleteId = $(e).attr("modaldeleteid");
                                                            var deleteInfo = $("#modal_delete_info_" + deleteId).html();
                                                            var deleteUrl = "${contextPath}/wh/whShipping/delete/" + deleteId;
                                                            var deleteMsg = "<f:message key='general.label.delete.confirmation'><f:param value='" + deleteInfo + "'/></f:message>";
                                                            $("#delete_modal .modal-body").html(deleteMsg);
                                                            $("#modal_delete_button").attr("href", deleteUrl);
                                                        }
            </script>
    </s:layout-component>
</s:layout-render>