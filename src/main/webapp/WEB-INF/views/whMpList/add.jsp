<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/buttons.dataTables.min.css" type="text/css" />
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
            <h1>Add Shipping Packing List</h1>
            <div class="row">
                <div class="col-lg-6">
                    <div class="main-box">
                        <h2>Material Pass Number</h2>
                        <form id="add_mp_list_form" class="form-horizontal" role="form" action="${contextPath}/wh/whShipping/whMpList/save" method="post">
                            <div class="form-group">
                                <label for="mpNo" class="col-lg-4 control-label">Barcode Sticker(MP No) *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="mpNo" name="mpNo" placeholder="Material Pass Number" value="" autofocus="autofocus">
                                </div>
                            </div>
                            <a href="${contextPath}/wh/whShipping" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                            <div class="pull-right">
                                <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                <button type="submit" class="btn btn-primary">Add</button>
                            </div>
                            <div class="clearfix"></div>
                        </form>
                    </div>
                </div>	
            </div>
        </div>
        <!--tablelist-->
        <div class="col-lg-12">
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <h2 class="pull-left">Packing List</h2>
                            <div class="filter-block pull-right">
                                <a href="#delete_modal" data-toggle="modal" class="btn btn-danger danger group_delete pull-right" onclick="modalDelete(this);">
                                    <i class="fa fa-trash-o fa-lg"></i> Clear All
                                </a>
                            </div>
                        </div>
                        <!--<div class="alert_placeholder col-lg-4" >-->
                        <div class="alert alert-success alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                            <b>*Please delete all data after print the packing list.</b>

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
                            <!--<a href="${contextPath}/wh/whShipping/whMpList/add" input type="button" id="submit1" name="submit1" class="btn btn-primary pull-left printAndEmail"><i class="fa fa-envelope"></i> Print & Send Email</a>-->
                            <button type="submit" id="submit1" name="submit1" class="btn btn-primary printAndEmail"><i class="fa fa-envelope"></i>Print & Send Email</button>
                            <input type="hidden" class="form-control" id="count" name="count" value="${count}">
                            <input type="hidden" class="form-control" id="countAll" name="countAll" value="${countAll}">
                        </div>

                        <div class="table-responsive">
                            <table id="dt_spml" class="table align-center">
                                <thead title="Test">
                                    <tr>
                                        <th class="col-lg-1"><span>No</span></th>
                                        <th class="col-lg-3"><span>Material Pass Number</span></th>
                                        <th class="col-lg-3"><span>Material Pass Expiry Date</span></th>
                                        <th class="col-lg-2"><span>Hardware Type</span></th>
                                        <th class="col-lg-3"><span>Hardware ID</span></th>
                                        <th class="col-lg-1"><span>Quantity</span></th>
                                        <th class="col-lg-1"><span>Delete</span></th>
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
                                            <td align="left">
                                                <c:if test="${whMpList.flag == '0'}">
                                                    <a modaldeleteid="${whMpList.id}" title="Delete" data-toggle="modal" href="#delete_modal" class="table-link danger group_delete" onclick="modalDelete1(this);">
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
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/resources/validation/additional-methods.js"></script>
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
                                                                            columns: [0, 1, 2, 3, 4, 5]
                                                                        }
                                                                    },
                                                                    {
                                                                        extend: 'excel',
                                                                        exportOptions: {
                                                                             columns: [0, 1, 2, 3, 4, 5]
                                                                        }
                                                                    },
                                                                    {
                                                                        extend: 'pdf',
                                                                        exportOptions: {
                                                                            columns: [0, 1, 2, 3, 4, 5]
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

                                                            if ($('#countAll').val() === '0') {
                                                                $('.printAndEmail').hide();
                                                            } else {
                                                                $('.printAndEmail').show();
                                                            }

                                                            var validator = $("#add_mp_list_form").validate({
                                                                rules: {
                                                                    mpNo: {
                                                                        required: true
                                                                    }
                                                                }
                                                            });
                                                            $(".cancel").click(function () {
                                                                validator.resetForm();
                                                            });

                                                            $('.printAndEmail').click(function () {
                                                                window.location.reload();
                                                            });

                                                        });

                                                        $('#submit1').on('click', function () {
                                                            location.reload();
                                                            window.open('${contextPath}/wh/whShipping/whMpList/viewPackingListPdf', 'Packing List', 'width=1600,height=1100').print();

                                                        });


                                                        function modalDelete(e) {
                                                            var deleteUrl = "${contextPath}/wh/whShipping/whMpList/deleteAll";
                                                            var deleteMsg = "Are you sure want to delete all? All related data will be deleted.";
                                                            $("#delete_modal .modal-body").html(deleteMsg);
                                                            $("#modal_delete_button").attr("href", deleteUrl);
                                                        }

                                                        function modalDelete1(e) {
                                                            var deleteId = $(e).attr("modaldeleteid");
                                                            var deleteInfo = $("#modal_delete_info_" + deleteId).html();
                                                            var deleteUrl = "${contextPath}/wh/whShipping/whMpList/delete/" + deleteId;
                                                            var deleteMsg = "Are you sure want to delete this row?";
                                                            $("#delete_modal .modal-body").html(deleteMsg);
                                                            $("#modal_delete_button").attr("href", deleteUrl);
                                                        }

        </script>
    </s:layout-component>
</s:layout-render>