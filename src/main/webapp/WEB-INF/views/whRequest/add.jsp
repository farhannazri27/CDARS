<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/bootstrap-select.css" type="text/css" />
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style>

            /*            .bootstrap-select > .dropdown-toggle.bs-placeholder,
                        .bootstrap-select > .dropdown-toggle.bs-placeholder:hover,
                        .bootstrap-select > .dropdown-toggle.bs-placeholder:focus,
                        .bootstrap-select > .dropdown-toggle.bs-placeholder:active {
                            color: #ccc;
                            background: none;
                            background-color: #fff;
                            border: 1px solid #ccc;
                            border-radius: 3px;
                        }*/
        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>Add Hardware Request</h1>
            <div class="row">
                <div class="col-lg-6">
                    <div class="main-box">
                        <h2>Hardware Request Information</h2>
                        <form id="add_hardwarequest_form" class="form-horizontal" role="form" action="${contextPath}/wh/whRequest/save" method="post">
                            <div class="form-group">
                                <label for="requestBy" class="col-lg-4 control-label">Request By</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="requestBy" name="requestBy" placeholder="Request For" value="${username}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="requestType" class="col-lg-4 control-label">Request For *</label>
                                <div class="col-lg-3">
                                    <select id="requestType" name="requestType" class="form-control">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${requestType}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="equipmentType" class="col-lg-4 control-label">Hardware Category *</label>
                                <div class="col-lg-5">
                                    <select id="equipmentType" name="equipmentType" class="form-control">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${equipmentType}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="listdiv">
                                <label for="equipmentId" class="col-lg-4 control-label">Equipment ID * </label>
                                <div class="col-lg-8">                                      
                                    <select id="equipmentId" name="equipmentId" class="form-control">
                                        <option value="" selected=""></option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="mblistdiv" hidden>
                                <label for="equipmentIdMb" class="col-lg-4 control-label">Motherboard ID * </label>
                                <div class="col-lg-8">                                      
                                    <select id="equipmentIdMb" name="equipmentIdMb" class="form-control" >
                                        <option value="" selected=""></option>
                                        <c:forEach items="${bibItemList}" var="group">
                                            <option value="${group.ItemID}">${group.ItemID}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="inventorymblistdiv" hidden>
                                <label for="inventoryIdMb" class="col-lg-4 control-label">Motherboard ID * </label>
                                <div class="col-lg-8">                  
                                      <!--<select id="inventoryIdMb" name="inventoryIdMb" class="selectpicker form-control" data-style="btn-info" data-live-search="true">-->
                                    <select id="inventoryIdMb" name="inventoryIdMb" class="form-control" >
                                        <option value="" selected=""></option>
                                        <c:forEach items="${inventoryListMb}" var="group">
                                            <option value="${group.id}" ${group.selected}>${group.equipmentId}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="stencillistdiv" hidden>
                                <label for="equipmentIdStencil" class="col-lg-4 control-label">Stencil ID * </label>
                                <div class="col-lg-8">                                      
                                    <!--<select id="equipmentIdStencil" name="equipmentIdStencil" class="selectpicker form-control" data-style="btn-info" data-live-search="true">-->
                                        <select id="equipmentIdStencil" name="equipmentIdStencil" class="form-control">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${StencilItemList}" var="group">
                                            <option value="${group.ItemID}" >${group.ItemID}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="inventorystencillistdiv" hidden>
                                <label for="inventoryIdStencil" class="col-lg-4 control-label">Stencil ID * </label>
                                <div class="col-lg-8">                                      
                                    <select id="inventoryIdStencil" name="inventoryIdStencil" class="form-control">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${inventoryListStencil}" var="group">
                                            <option value="${group.id}" ${group.selected}>${group.equipmentId}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="traylistdiv" hidden>
                                <label for="equipmentIdTray" class="col-lg-4 control-label">Tray ID * </label>
                                <div class="col-lg-8">                                                    
                                    <!--<select id="equipmentIdTray" name="equipmentIdTray" class="selectpicker" data-style="btn-info" data-live-search="true">-->
                                        <select id="equipmentIdTray" name="equipmentIdTray" class="form-control">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${tray}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="inventorytraylistdiv" hidden>
                                <label for="inventoryIdTray" class="col-lg-4 control-label">Tray ID * </label>
                                <div class="col-lg-8">                                      
                                    <select id="inventoryIdTray" name="inventoryIdTray" class="form-control">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${inventoryListTray}" var="group">
                                            <option value="${group.id}" ${group.selected}>${group.equipmentId}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="pcblistdiv" hidden>
                                <label for="equipmentIdPcb" class="col-lg-4 control-label">PCB ID * </label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="equipmentIdPcb" name="equipmentIdPcb" placeholder="PCB Name" value="">
                                </div>
                            </div>
                            <div class="form-group" id="inventorypcblistdiv" hidden>
                                <label for="inventoryIdPcb" class="col-lg-4 control-label">PCB ID * </label>
                                <div class="col-lg-8">                                      
                                    <select id="inventoryIdPcb" name="inventoryIdPcb" class="form-control">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${inventoryListTray}" var="group">
                                            <option value="${group.id}" ${group.selected}>${group.equipmentId}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div> 
                            <div class="form-group" id="quantitydiv" hidden>
                                <label for="quantity" class="col-lg-4 control-label">Quantity *</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="quantity" name="quantity" value="">
                                </div>
                            </div>      
                            <div class="form-group" id="remarksdiv">
                                <label for="remarks" class="col-lg-4 control-label">Remarks </label>
                                <div class="col-lg-8">
                                    <textarea class="form-control" rows="5" id="remarks" name="remarks">${WhRequest.remarks}</textarea>
                                </div>
                            </div>
                            <a href="${contextPath}/wh/whRequest" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                            <div class="pull-right">
                                <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                <button type="submit" class="btn btn-primary">Save</button>
                            </div>
                            <div class="clearfix"></div>
                        </form>
                    </div>
                </div>	
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/resources/validation/additional-methods.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-datepicker.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-select.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {

                var validator = $("#add_hardwarequest_form").validate({
                    rules: {
                        requestType: {
                            required: true
                        },
                        equipmentType: {
                            required: true
                        },
                        equipmentId: {
                            required: true
                        },
                        equipmentIdMb: {
                            required: true
                        },
                        inventoryIdMb: {
                            required: true
                        },
                        inventoryIdStencil: {
                            required: true
                        },
                        inventoryIdTray: {
                            required: true
                        },
                        inventoryIdPcb: {
                            required: true
                        },
                        equipmentIdStencil: {
                            required: true
                        },
                        equipmentIdTray: {
                            required: true
                        },
                        equipmentIdPcb: {
                            required: true
                        },
                        quantity: {
                            required: true,
                            number: true
                        }
                    }
                });
                $(".cancel").click(function () {
                    validator.resetForm();
                });

            });



//            $('#equipmentType').on('change', function () {
//                var element1 = $('#requestType');
//                if ($(this).val() === "Motherboard" && element1.val() === "Retrieve") {
//                    $("#inventorymblistdiv").show();
//                    $("#listdiv").hide();
//                    $("#inventorystencillistdiv").hide();
//                    $("#inventorytraylistdiv").hide();
//                    $("#inventorypcblistdiv").hide();
//                    $("#quantitydiv").hide();
//                    $("#inventoryIdStencil").val("");
//                    $("#inventoryIdTray").val("");
//                    $("#inventoryIdPcb").val("");
//                    $("#quantity").val("");
//                } else if ($(this).val() === "Stencil" && element1.val() === "Retrieve") {
//                    $("#inventorystencillistdiv").show();
//                    $("#listdiv").hide();
//                    $("#inventorymblistdiv").hide();
//                    $("#inventorytraylistdiv").hide();
//                    $("#inventorypcblistdiv").hide();
//                    $("#quantitydiv").hide();
//                    $("#inventoryIdMb").val("");
//                    $("#inventoryIdTray").val("");
//                    $("#inventoryIdPcb").val("");
//                    $("#quantity").val("");
//                    $("#type").val("");
//                } else if ($(this).val() === "Tray" && element1.val() === "Retrieve") {
//                    $("#inventorytraylistdiv").show();
//                    $("#quantitydiv").show();
//                    $("#listdiv").hide();
//                    $("#inventorystencillistdiv").hide();
//                    $("#inventorymblistdiv").hide();
//                    $("#inventorypcblistdiv").hide();
//                    $("#inventoryIdMb").val("");
//                    $("#inventoryIdStencil").val("");
//                    $("#inventoryIdPcb").val("");
//                } else if ($(this).val() === "PCB" && element1.val() === "Retrieve") {
//                    $("#inventorypcblistdiv").show();
//                    $("#quantitydiv").show();
//                    $("#listdiv").hide();
//                    $("#inventorystencillistdiv").hide();
//                    $("#inventorymblistdiv").hide();
//                    $("#inventorytraylistdiv").hide();
//                    $("#inventoryIdMb").val("");
//                    $("#inventoryIdStencil").val("");
//                    $("#inventoryIdTray").val("");
//                } else {
//                    $("#listdiv").show();
//                    $("#inventorypcblistdiv").hide();
//                    $("#inventorymblistdiv").hide();
//                    $("#inventorystencillistdiv").hide();
//                    $("#inventorytraylistdiv").hide();
//                    $("#quantitydiv").hide();
//                    $("#inventoryIdMb").val("");
//                    $("#inventoryIdStencil").val("");
//                    $("#inventoryIdTray").val("");
//                    $("#inventoryIdPcb").val("");
//                    $("#quantity").val("");
//                }
//
//            });

            $('#equipmentType').on('change', function () {
                var element1 = $('#requestType');
                if ($(this).val() === "Motherboard" && element1.val() === "Ship") {
                    $("#mblistdiv").show();
                    $("#listdiv").hide();
                    $("#stencillistdiv").hide();
                    $("#traylistdiv").hide();
                    $("#pcblistdiv").hide();
                    $("#quantitydiv").hide();
                    $("#equipmentIdStencil").val("");
                    $("#equipmentIdTray").val("");
                    $("#equipmentIdPcb").val("");
                    $("#quantity").val("");

                    $("#inventorypcblistdiv").hide();
                    $("#inventorymblistdiv").hide();
                    $("#inventorystencillistdiv").hide();
                    $("#inventorytraylistdiv").hide();
                    $("#inventoryIdMb").val("");
                    $("#inventoryIdStencil").val("");
                    $("#inventoryIdTray").val("");
                    $("#inventoryIdPcb").val("");
                } else if ($(this).val() === "Stencil" && element1.val() === "Ship") {
                    $("#stencillistdiv").show();
                    $("#listdiv").hide();
                    $("#mblistdiv").hide();
                    $("#traylistdiv").hide();
                    $("#pcblistdiv").hide();
//                    $("#typediv").hide();
                    $("#quantitydiv").hide();
                    $("#equipmentIdMb").val("");
                    $("#equipmentIdTray").val("");
                    $("#equipmentIdPcb").val("");
                    $("#quantity").val("");
//                    $("#type").val("");

                    $("#inventorypcblistdiv").hide();
                    $("#inventorymblistdiv").hide();
                    $("#inventorystencillistdiv").hide();
                    $("#inventorytraylistdiv").hide();
                    $("#inventoryIdMb").val("");
                    $("#inventoryIdStencil").val("");
                    $("#inventoryIdTray").val("");
                    $("#inventoryIdPcb").val("");
                } else if ($(this).val() === "Tray" && element1.val() === "Ship") {
                    $("#traylistdiv").show();
                    $("#quantitydiv").show();
                    $("#listdiv").hide();
                    $("#stencillistdiv").hide();
                    $("#mblistdiv").hide();
                    $("#pcblistdiv").hide();
//                    $("#typediv").hide();
                    $("#equipmentIdMb").val("");
                    $("#equipmentIdStencil").val("");
                    $("#equipmentIdPcb").val("");
//                    $("#type").val("");

                    $("#inventorypcblistdiv").hide();
                    $("#inventorymblistdiv").hide();
                    $("#inventorystencillistdiv").hide();
                    $("#inventorytraylistdiv").hide();
                    $("#inventoryIdMb").val("");
                    $("#inventoryIdStencil").val("");
                    $("#inventoryIdTray").val("");
                    $("#inventoryIdPcb").val("");
                } else if ($(this).val() === "PCB" && element1.val() === "Ship") {
                    $("#pcblistdiv").show();
//                    $("#typediv").hide();
                    $("#quantitydiv").show();
                    $("#listdiv").hide();
                    $("#stencillistdiv").hide();
                    $("#mblistdiv").hide();
                    $("#traylistdiv").hide();
                    $("#equipmentIdMb").val("");
                    $("#equipmentIdStencil").val("");
                    $("#equipmentIdTray").val("");
                    
                    $("#inventorypcblistdiv").hide();
                    $("#inventorymblistdiv").hide();
                    $("#inventorystencillistdiv").hide();
                    $("#inventorytraylistdiv").hide();
                    $("#inventoryIdMb").val("");
                    $("#inventoryIdStencil").val("");
                    $("#inventoryIdTray").val("");
                    $("#inventoryIdPcb").val("");
                } else if ($(this).val() === "Motherboard" && element1.val() === "Retrieve") {
                    $("#inventorymblistdiv").show();
                    $("#listdiv").hide();
                    $("#inventorystencillistdiv").hide();
                    $("#inventorytraylistdiv").hide();
                    $("#inventorypcblistdiv").hide();
                    $("#quantitydiv").hide();
                    $("#inventoryIdStencil").val("");
                    $("#inventoryIdTray").val("");
                    $("#inventoryIdPcb").val("");
                    $("#quantity").val("");
                    
                     $("#pcblistdiv").hide();
                    $("#mblistdiv").hide();
                    $("#stencillistdiv").hide();
                    $("#traylistdiv").hide();
                    $("#equipmentIdMb").val("");
                    $("#equipmentIdStencil").val("");
                    $("#equipmentIdTray").val("");
                    $("#equipmentIdPcb").val("");
                } else if ($(this).val() === "Stencil" && element1.val() === "Retrieve") {
                    $("#inventorystencillistdiv").show();
                    $("#listdiv").hide();
                    $("#inventorymblistdiv").hide();
                    $("#inventorytraylistdiv").hide();
                    $("#inventorypcblistdiv").hide();
                    $("#quantitydiv").hide();
                    $("#inventoryIdMb").val("");
                    $("#inventoryIdTray").val("");
                    $("#inventoryIdPcb").val("");
                    $("#quantity").val("");
                    
                     $("#pcblistdiv").hide();
                    $("#mblistdiv").hide();
                    $("#stencillistdiv").hide();
                    $("#traylistdiv").hide();
                    $("#equipmentIdMb").val("");
                    $("#equipmentIdStencil").val("");
                    $("#equipmentIdTray").val("");
                    $("#equipmentIdPcb").val("");
                } else if ($(this).val() === "Tray" && element1.val() === "Retrieve") {
                    $("#inventorytraylistdiv").show();
                    $("#quantitydiv").show();
                    $("#listdiv").hide();
                    $("#inventorystencillistdiv").hide();
                    $("#inventorymblistdiv").hide();
                    $("#inventorypcblistdiv").hide();
                    $("#inventoryIdMb").val("");
                    $("#inventoryIdStencil").val("");
                    $("#inventoryIdPcb").val("");
                    
                     $("#pcblistdiv").hide();
                    $("#mblistdiv").hide();
                    $("#stencillistdiv").hide();
                    $("#traylistdiv").hide();
                    $("#equipmentIdMb").val("");
                    $("#equipmentIdStencil").val("");
                    $("#equipmentIdTray").val("");
                    $("#equipmentIdPcb").val("");
                } else if ($(this).val() === "PCB" && element1.val() === "Retrieve") {
                    $("#inventorypcblistdiv").show();
                    $("#quantitydiv").show();
                    $("#listdiv").hide();
                    $("#inventorystencillistdiv").hide();
                    $("#inventorymblistdiv").hide();
                    $("#inventorytraylistdiv").hide();
                    $("#inventoryIdMb").val("");
                    $("#inventoryIdStencil").val("");
                    $("#inventoryIdTray").val("");
                    
                     $("#pcblistdiv").hide();
                    $("#mblistdiv").hide();
                    $("#stencillistdiv").hide();
                    $("#traylistdiv").hide();
                    $("#equipmentIdMb").val("");
                    $("#equipmentIdStencil").val("");
                    $("#equipmentIdTray").val("");
                    $("#equipmentIdPcb").val("");
                } else {
                    $("#listdiv").show();
                    $("#pcblistdiv").hide();
                    $("#mblistdiv").hide();
                    $("#stencillistdiv").hide();
                    $("#traylistdiv").hide();
//                    $("#typediv").hide();
                    $("#quantitydiv").hide();
                    $("#equipmentIdMb").val("");
                    $("#equipmentIdStencil").val("");
                    $("#equipmentIdTray").val("");
                    $("#equipmentIdPcb").val("");
                    $("#quantity").val("");
                    $("#inventorypcblistdiv").hide();
                    $("#inventorymblistdiv").hide();
                    $("#inventorystencillistdiv").hide();
                    $("#inventorytraylistdiv").hide();
                    $("#inventoryIdMb").val("");
                    $("#inventoryIdStencil").val("");
                    $("#inventoryIdTray").val("");
                    $("#inventoryIdPcb").val("");
//                    $("#type").val("");
                }

            });

        </script>
    </s:layout-component>
</s:layout-render>