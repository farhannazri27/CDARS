<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
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
            <h1>SPTS</h1>
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <h2 class="pull-left">Item List</h2>

                            <div class="filter-block pull-right">
                                <a href="${contextPath}/spts/add" class="btn btn-primary pull-right">
                                    <i class="fa fa-plus-circle fa-lg"></i> Add Item
                                </a>
                                <a href="${contextPath}/spts" class="btn btn-info pull-left">
                                    <i class="fa fa-reply"></i> Back
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
                                        <th><span>Item Type</span></th>
                                        <th><span>Item ID</span></th>
                                        <th><span>Item Name</span></th>
                                            <th><span>Model</span></th>
                                        <th><span>Last Prod Date</span></th>
                                         <th><span>Sf Qty</span></th>
                                        <th><span>SF</span></th>
                                        <th><span>date Last Used</span></th>
                                        <!--<th><span>Manage</span></th>-->
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${itemList}" var="item" varStatus="itemLoop">
                                        <tr>
                                            <td><c:out value="${itemLoop.index+1}"/></td>
                                            <td><c:out value="${item.ItemType}"/></td>
                                            <td><c:out value="${item.ItemID}"/></td>
                                            <td id="modal_delete_info_${item.PKID}"><c:out value="${item.ItemName}"/></td>
                                            <td><c:out value="${item.Model}"/></td>
                                            <td><c:out value="${item.LastProdReturnDatetime}"/></td>
                                             <td><c:out value="${item.StorageFactoryQty}"/></td>
                                            <td><c:out value="${item.LastSFMovementDatetime}"/></td>
                                            <td><c:out value="${item.LastMovementDatetime}"/></td>
<!--                                            <td align="center">
                                                <a href="${contextPath}/spts/view/${item.PKID}" class="table-link" title="View">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                        <i class="fa fa-search-plus fa-stack-1x fa-inverse"></i>
                                                    </span>
                                                </a>
                                                <a href="${contextPath}/spts/edit/${item.PKID}" class="table-link" title="Edit">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                        <i class="fa fa-pencil fa-stack-1x fa-inverse"></i>
                                                    </span>
                                                </a>
                                                <a modaldeleteid="${item.PKID}" modaldeleteversion="${item.Version}" data-toggle="modal" href="#delete_modal" class="table-link danger group_delete" onclick="modalDelete(this);">
                                                    <span class="fa-stack">
                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                        <i class="fa fa-trash-o fa-stack-1x fa-inverse"></i>
                                                    </span>
                                                </a>
                                            </td>-->
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
                                                                        columns: [0, 1, 2, 3, 4, 5,6,7,8]
                                                                    }
                                                                },
                                                                {
                                                                    extend: 'excel',
                                                                    exportOptions: {
                                                                        columns: [0, 1, 2, 3, 4, 5,6,7,8]
                                                                    }
                                                                },
                                                                {
                                                                    extend: 'pdf',
                                                                    exportOptions: {
                                                                        columns: [0, 1, 2, 3, 4, 5,6,7,8]
                                                                    }
                                                                },
                                                                {
                                                                    extend: 'print',
                                                                    exportOptions: {
                                                                        columns: [0, 1, 2, 3, 4, 5,6,7,8]
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

//                oTable.buttons().container().appendTo($("#dt_spml_tt", oTable.table().container() ) );

                                                        $('#dt_spml_search').keyup(function () {
                                                            oTable.search($(this).val()).draw();
                                                        });

                                                        $("#dt_spml_rows").change(function () {
                                                            oTable.page.len($(this).val()).draw();
                                                        });
                                                    });

                                                    function modalDelete(e) {
                                                        var deleteId = $(e).attr("modaldeleteid");
                                                        var deleteVersion = $(e).attr("modaldeleteversion");
                                                        var deleteInfo = $("#modal_delete_info_" + deleteId).html();
                                                        var deleteUrl = "${contextPath}/spts/delete/" + deleteId + "/" + deleteVersion;
                                                        var deleteMsg = "<f:message key='general.label.delete.confirmation'><f:param value='" + deleteInfo + "'/></f:message>";
                                                        $("#delete_modal .modal-body").html(deleteMsg);
                                                        $("#modal_delete_button").attr("href", deleteUrl);
                                                    }
            </script>
    </s:layout-component>
</s:layout-render>