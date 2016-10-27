<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
        <!--<link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/dataTables.tableTools.css" type="text/css" />-->
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/buttons.dataTables.min.css" type="text/css" />
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style>
            /*            th { font-size: 11px;}
                        td { font-size: 11px;}*/
            .print-this {
                display: none;
            }
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
                        <div class="clearfix">
                            <div class ="btn btn-primary pull-left printAndEmail">Print & Send Email</div>
                            <input type="hidden" class="form-control" id="count" name="count" value="${count}">
                        </div>

                        <div class="table-responsive">
                            <table id="dt_spml" class="table align-center">
                                <!--<p class="print-this">TEXT TO BE SHOWN IN PRINT</p>-->
                                <thead title="Test">
                                    <tr>
                                        <th class="col-lg-1"><span>No</span></th>
                                        <th class="col-lg-3"><span>Material Pass Number</span></th>
                                        <th class="col-lg-3"><span>Material Pass Expiry Date</span></th>
                                        <th class="col-lg-2"><span>Hardware Type</span></th>
                                        <th class="col-lg-3"><span>Hardware ID</span></th>
                                        <th class="col-lg-1"><span>Quantity</span></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${whMpListList}" var="whMpList" varStatus="whMpListLoop">
                                        <tr>
                                            <td class="col-lg-1 align-center"><c:out value="${whMpListLoop.index+1}"/></td>
                                            <td class="col-lg-3 align-center"><c:out value="${whMpList.mpNo}"/></td>
                                            <td class="col-lg-3"><c:out value="${whMpList.viewMpExpiryDate}"/></td>
                                            <td class="col-lg-2"><c:out value="${whMpList.hardwareType}"/></td>
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
        <!--<script src="${contextPath}/resources/private/datatables/js/dataTables.tableTools.js"></script>-->
        <script src="${contextPath}/resources/private/datatables/js/dataTables.buttons.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.print.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.flash.min.js"></script>
        <script src="${contextPath}/resources/private/datatables/js/buttons.html5.min.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
                                    $(document).ready(function () {
//                                        
                                        if ($('#count').val() === '0') {
                                            $('.printAndEmail').hide();
                                        } else {
                                            $('.printAndEmail').show();
                                        }

                                        oTable = $('#dt_spml').DataTable({
//                                             pageLength: '100',
                                            dom: 'Bfrtip',
                                            buttons: [
//                                                'copy', 'csv', 'excel', 'pdf',
                                                {
                                                    extend: 'print',
                                                    customize: function (win) {
                                                        $(win.document.body)
                                                                .css('font-size', '10pt')
                                                        $(win.document.body).find('table')
                                                                .addClass('compact')
                                                                .css('font-size', 'inherit');
                                                    },
                                                    autoPrint: true,
                                                }
                                            ]
                                        });

                                        $('#dt_spml_search').keyup(function () {
                                            oTable.search($(this).val()).draw();
                                        });
                                        $("#dt_spml_rows").change(function () {
                                            oTable.page.len($(this).val()).draw();
                                        });

                                        $('.dt-buttons').hide();

                                    });


                                    function modalDelete(e) {

                                        var deleteUrl = "${contextPath}/wh/whShipping/whMpList/changeFlag1";
                                        var deleteMsg = "Are you sure want to delete all? All related data will be deleted.";
                                        $("#delete_modal .modal-body").html(deleteMsg);
                                        $("#modal_delete_button").attr("href", deleteUrl);
                                    }

                                    function printDiv() {
                                        var divToPrint = document.getElementById('dt_spml');
                                        var htmlToPrint = '' +
                                                '<style type="text/css">' +
                                                'table th, table td {' +
                                                'font-size: 12px;' +
                                                'text-align: left;' +
                                                'padding;0.5em;' +
                                                '}' +
                                                '</style>';
                                        htmlToPrint += divToPrint.outerHTML;
                                        newWin = window.open("");
                                        newWin.document.write(htmlToPrint);
                                        newWin.print();
                                        newWin.close();
                                        location.href = 'http://fg79cj-l1:8080/CDARS/wh/whShipping/whMpList/email';
                                    }

                                    $('.printAndEmail').on('click', function () {
//                                        printData();
                                        printDiv();
                                    })


        </script>
    </s:layout-component>
</s:layout-render>