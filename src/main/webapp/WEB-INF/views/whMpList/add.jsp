<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/jquery.dataTables.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/datatables/css/buttons.dataTables.min.css" type="text/css" />
    </s:layout-component>
    <s:layout-component name="page_css_inline">
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
                                <label for="mpNo" class="col-lg-4 control-label">Material Pass Number *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="mpNo" name="mpNo" placeholder="Material Pass Number" value="" autofocus="autofocus">
                                </div>
                            </div>
                            <!--<a href="${contextPath}/wh/whShipping/whMpList" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>-->
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
                                    <i class="fa fa-trash-o fa-lg"></i> Delete All
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
                            <!--<a href="${contextPath}/wh/whShipping/whMpList/print" class="btn btn-primary pull-left printAndEmail"><i class="fa fa-envelope"></i> Print & Send Email</a>-->
                            <button type="submit" id="submit1" name="submit1" class="btn btn-primary pull-left printAndEmail" onclick="window.open('${contextPath}/wh/whShipping/whMpList/viewPackingListPdf', 'Packing List', 'width=1600,height=1100').print()"><i class="fa fa-envelope"></i> Print & Send Email</button>
                            <input type="hidden" class="form-control" id="count" name="count" value="${count}">
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

                                        if ($('#count').val() === '0') {
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
                                    });
                                    $('#equipmentType').on('change', function () {
                                        if ($(this).val() === "Motherboard") {
                                            $("#mblistdiv").show();
                                            $("#listdiv").hide();
                                            $("#stencillistdiv").hide();
                                            $("#traylistdiv").hide();
                                            $("#pcblistdiv").hide();
                                            $("#typediv").hide();
                                            $("#quantitydiv").hide();
                                            $("#equipmentIdStencil").val("");
                                            $("#equipmentIdTray").val("");
                                            $("#equipmentIdPcb").val("");
                                            $("#quantity").val("");
                                            $("#type").val("");
                                        } else if ($(this).val() === "Stencil") {
                                            $("#stencillistdiv").show();
                                            $("#listdiv").hide();
                                            $("#mblistdiv").hide();
                                            $("#traylistdiv").hide();
                                            $("#pcblistdiv").hide();
                                            $("#typediv").hide();
                                            $("#quantitydiv").hide();
                                            $("#equipmentIdMb").val("");
                                            $("#equipmentIdTray").val("");
                                            $("#equipmentIdPcb").val("");
                                            $("#quantity").val("");
                                            $("#type").val("");
                                        } else if ($(this).val() === "Tray") {
                                            $("#traylistdiv").show();
                                            $("#quantitydiv").show();
                                            $("#listdiv").hide();
                                            $("#stencillistdiv").hide();
                                            $("#mblistdiv").hide();
                                            $("#pcblistdiv").hide();
                                            $("#typediv").hide();
                                            $("#equipmentIdMb").val("");
                                            $("#equipmentIdStencil").val("");
                                            $("#equipmentIdPcb").val("");
                                            $("#type").val("");
                                        } else if ($(this).val() === "PCB") {
                                            $("#pcblistdiv").show();
                                            $("#typediv").hide();
                                            $("#quantitydiv").show();
                                            $("#listdiv").hide();
                                            $("#stencillistdiv").hide();
                                            $("#mblistdiv").hide();
                                            $("#traylistdiv").hide();
                                            $("#equipmentIdMb").val("");
                                            $("#equipmentIdStencil").val("");
                                            $("#equipmentIdTray").val("");
                                        } else {
                                            $("#listdiv").show();
                                            $("#pcblistdiv").hide();
                                            $("#mblistdiv").hide();
                                            $("#stencillistdiv").hide();
                                            $("#traylistdiv").hide();
                                            $("#typediv").hide();
                                            $("#quantitydiv").hide();
                                            $("#equipmentIdMb").val("");
                                            $("#equipmentIdStencil").val("");
                                            $("#equipmentIdTray").val("");
                                            $("#equipmentIdPcb").val("");
                                            $("#quantity").val("");
                                            $("#type").val("");
                                        }

                                    });

                                    function modalDelete(e) {

                                        var deleteUrl = "${contextPath}/wh/whShipping/whMpList/changeFlag1";
                                        var deleteMsg = "Are you sure want to delete all? All related data will be deleted.";
                                        $("#delete_modal .modal-body").html(deleteMsg);
                                        $("#modal_delete_button").attr("href", deleteUrl);
                                    }

        </script>
    </s:layout-component>
</s:layout-render>