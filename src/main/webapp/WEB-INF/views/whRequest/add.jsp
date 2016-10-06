<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/bootstrap-select.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/select2.css" type="text/css" />
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />
    </s:layout-component>
    <s:layout-component name="page_css_inline">
        <style>
            .select2-container-active .select2-choice,
            .select2-container-active .select2-choices {
                border: 1px solid $input-border-focus !important;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
                /* -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6) !important;
                                   box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6) !important;*/
            }

            .select2-dropdown-open .select2-choice {
                border-bottom: 0 !important;
                background-image: none;
                background-color: #fff;
                filter: none;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
            }

            .select2-dropdown-open.select2-drop-above .select2-choice,
            .select2-dropdown-open.select2-drop-above .select2-choices {
                border: 1px solid $input-border-focus !important;
                border-top: 0 !important;
                background-image: none;
                background-color: #fff;
                filter: none;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #009d9b !important;

            }

        </style>
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>Add Hardware Request</h1>
            <div class="row">
                <div class="col-lg-8">
                    <div class="main-box">
                        <h2>Hardware Request Information</h2>
                        <form id="add_hardwarequest_form" class="form-horizontal" role="form" action="${contextPath}/wh/whRequest/save" method="post">
                            <div class="form-group">
                                <label for="requestBy" class="col-lg-2 control-label">Request By</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="requestBy" name="requestBy" value="${username}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="requestType" class="col-lg-2 control-label">Request For *</label>
                                <div class="col-lg-8">
                                    <select id="requestType" name="requestType" class="js-example-basic-single" style="width: 30%" autofocus="autofocus">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${requestType}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="equipmentType" class="col-lg-2 control-label">Hardware Category *</label>
                                <div class="col-lg-5">
                                    <select id="equipmentType" name="equipmentType" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${equipmentType}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group" id="pcbTypeDiv" hidden>
                                <label for="pcbType" class="col-lg-2 control-label">PCB Type *</label>
                                <div class="col-lg-4">
                                    <select id="pcbType" name="pcbType" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${pcbType}" var="group">
                                            <option value="${group.pcbType}" ${group.selected}>${group.pcbType} &emsp;(Quantity: ${group.quantity})</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="listdiv">
                                <label for="equipmentId" class="col-lg-2 control-label">Equipment ID * </label>
                                <div class="col-lg-8">                                      
                                    <select id="equipmentId" name="equipmentId" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="mblistdiv" hidden>
                                <label for="equipmentIdMb" class="col-lg-2 control-label">Motherboard ID * </label>
                                <div class="col-lg-8">                                      
                                    <select id="equipmentIdMb" name="equipmentIdMb" class="js-example-basic-single" style="width: 100%" >
                                        <option value="" selected=""></option>
                                        <c:forEach items="${bibItemList}" var="group">
                                            <option value="${group.ItemID}">${group.ItemID} &emsp;(Quantity: ${group.OnHandQty})</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="inventorymblistdiv" hidden>
                                <label for="inventoryIdMb" class="col-lg-2 control-label">Motherboard ID * </label>
                                <div class="col-lg-8">                  
                                    <select id="inventoryIdMb" name="inventoryIdMb" class="js-example-basic-single" style="width: 100%">
                                        <!--<select id="inventoryIdMb" name="inventoryIdMb" class="form-control" >-->
                                        <option value="" selected=""></option>
                                        <c:forEach items="${inventoryListMb}" var="group">
                                            <option value="${group.id}" ${group.selected}>${group.equipmentId}  &emsp;(Quantity: ${group.quantity}) </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="stencillistdiv" hidden>
                                <label for="equipmentIdStencil" class="col-lg-2 control-label">Stencil ID * </label>
                                <div class="col-lg-8">                                      
                                    <!--<select id="equipmentIdStencil" name="equipmentIdStencil" class="selectpicker form-control" data-style="btn-info" data-live-search="true">-->
                                    <select id="equipmentIdStencil" name="equipmentIdStencil" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${StencilItemList}" var="group">
                                            <option value="${group.ItemID}" >${group.ItemID} &emsp;(Quantity: ${group.OnHandQty})</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="inventorystencillistdiv" hidden>
                                <label for="inventoryIdStencil" class="col-lg-2 control-label">Stencil ID * </label>
                                <div class="col-lg-8">                                      
                                    <select id="inventoryIdStencil" name="inventoryIdStencil" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${inventoryListStencil}" var="group">
                                            <option value="${group.id}" ${group.selected}>${group.equipmentId}  &emsp;(Quantity: ${group.quantity})</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="traylistdiv" hidden>
                                <label for="equipmentIdTray" class="col-lg-2 control-label">Tray ID * </label>
                                <div class="col-lg-8">                                                    
                                    <!--<select id="equipmentIdTray" name="equipmentIdTray" class="selectpicker" data-style="btn-info" data-live-search="true">-->
                                    <select id="equipmentIdTray" name="equipmentIdTray" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${trayItemList}" var="group">
                                            <option value="${group.ItemID}" ${group.selected}>${group.ItemID} &emsp;(Quantity: ${group.OnHandQty})</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="inventorytraylistdiv" hidden>
                                <label for="inventoryIdTray" class="col-lg-2 control-label">Tray ID * </label>
                                <div class="col-lg-8">                                      
                                    <select id="inventoryIdTray" name="inventoryIdTray" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${inventoryListTray}" var="group">
                                            <option value="${group.id}" ${group.selected}>${group.equipmentId} &emsp;(Quantity: ${group.quantity})</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group" id="pcbCtrlistdiv" hidden>
                                <label for="equipmentIdpcbCtr" class="col-lg-2 control-label">PCB ID Control*  </label>
                                <div class="col-lg-8">                                      
                                    <select id="equipmentIdpcbCtr" name="equipmentIdpcbCtr" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${pcbItemListCtr}" var="group">
                                            <option value="${group.ItemID}" ${group.selected}>${group.ItemID} &emsp;(Quantity: ${group.OnHandQty})</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="pcbCtrQtydiv" hidden>
                                <label for="pcbCtrQty" class="col-lg-2 control-label">Quantity Control* </label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="pcbCtrQty" name="pcbCtrQty" value="">
                                </div>
                            </div>    
                            <div class="form-group" id="pcbAlistdiv" hidden>
                                <label for="equipmentIdpcbA" class="col-lg-2 control-label">PCB ID Qual A * </label>
                                <div class="col-lg-8">                                      
                                    <select id="equipmentIdpcbA" name="equipmentIdpcbA" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${pcbItemListA}" var="group">
                                            <option value="${group.ItemID}" ${group.selected}>${group.ItemID} &emsp;(Quantity: ${group.OnHandQty})</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="pcbAQtydiv" hidden>
                                <label for="pcbAQty" class="col-lg-2 control-label">Quantity Qual A *</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="pcbAQty" name="pcbAQty" value="">
                                </div>
                            </div> 
                            <div class="form-group" id="pcbBlistdiv" hidden>
                                <label for="equipmentIdpcbB" class="col-lg-2 control-label">PCB ID Qual B * </label>
                                <div class="col-lg-8">                                      
                                    <select id="equipmentIdpcbB" name="equipmentIdpcbB" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${pcbItemListB}" var="group">
                                            <option value="${group.ItemID}" ${group.selected}>${group.ItemID} &emsp;(Quantity: ${group.OnHandQty})</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="pcbBQtydiv" hidden>
                                <label for="pcbBQty" class="col-lg-2 control-label">Quantity Qual B *</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="pcbBQty" name="pcbBQty" value="">
                                </div>
                            </div>    
                            <div class="form-group" id="pcbClistdiv" hidden>
                                <label for="equipmentIdpcbC" class="col-lg-2 control-label">PCB ID Qual C * </label>
                                <div class="col-lg-8">                                      
                                    <select id="equipmentIdpcbC" name="equipmentIdpcbC" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${pcbItemListC}" var="group">
                                            <option value="${group.ItemID}" ${group.selected}>${group.ItemID} &emsp;(Quantity: ${group.OnHandQty})</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="pcbCQtydiv" hidden>
                                <label for="pcbCQty" class="col-lg-2 control-label">Quantity Qual C *</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="pcbCQty" name="pcbCQty" value="">
                                </div>
                            </div>    

                            <div class="form-group" id="inventorypcblistdiv" hidden>
                                <label for="inventoryIdPcb" class="col-lg-2 control-label">PCB ID * </label>
                                <div class="col-lg-8">                                      
                                    <select id="inventoryIdPcb" name="inventoryIdPcb" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${inventoryListPCB}" var="group">
                                            <option value="${group.id}" ${group.selected}>${group.equipmentId}  &emsp;(Quantity: ${group.quantity})</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div> 
                            <div class="form-group" id="quantitydiv" hidden>
                                <label for="quantity" class="col-lg-2 control-label">Quantity *</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="quantity" name="quantity" value="">
                                </div>
                            </div>      
                            <div class="form-group" id="remarksdiv">
                                <label for="remarks" class="col-lg-2 control-label">Remarks </label>
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
        <script src="${contextPath}/resources/private/js/select2.min.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-select.js"></script>
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/resources/validation/additional-methods.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-datepicker.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {

                $(".js-example-basic-single").select2();

                var validator = $("#add_hardwarequest_form").validate({
                    rules: {
                        requestType: {
                            required: true
                        },
                        pcbType: {
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
                        equipmentIdpcbCtr: {
                            required: true
                        },
                        pcbCtrQty: {
                            required: true
                        },
                        equipmentIdpcbA: {
                            required: true
                        },
                        pcbAQty: {
                            required: true
                        },
                        equipmentIdpcbB: {
                            required: true
                        },
                        pcbBQty: {
                            required: true
                        },
                        equipmentIdpcbC: {
                            required: true
                        },
                        pcbCQty: {
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

            $('#equipmentType').on('change', function () {
                var element1 = $('#requestType');
                if ($(this).val() === "Motherboard" && element1.val() === "Ship") {
                    $("#mblistdiv").show();
                    $("#pcbTypeDiv").hide();
                    $("#listdiv").hide();
                    $("#stencillistdiv").hide();
                    $("#traylistdiv").hide();
                    $("#pcbAlistdiv").hide();
                    $("#pcbAQtydiv").hide();
                    $("#pcbBlistdiv").hide();
                    $("#pcbBQtydiv").hide();
                    $("#pcbClistdiv").hide();
                    $("#pcbCQtydiv").hide();
                    $("#pcbCtrlistdiv").hide();
                    $("#pcbCtrQtydiv").hide();
                    $("#quantity").val("");
                    $("#quantitydiv").hide();
                    $("#pcbType").val("").trigger('change');
                    $("#equipmentIdStencil").val("").trigger('change');
                    $("#equipmentIdTray").val("").trigger('change');
                    $("#equipmentIdpcbA").val("").trigger('change');
                    $("#pcbAQty").val("");
                    $("#equipmentIdpcbB").val("").trigger('change');
                    $("#pcbBQty").val("");
                    $("#equipmentIdpcbC").val("").trigger('change');
                    $("#pcbCQty").val("");
                    $("#equipmentIdpcbCtr").val("").trigger('change');
                    $("#pcbCtrQty").val("");
                    $("#quantity").val("");

                    $("#inventorypcblistdiv").hide();
                    $("#inventorymblistdiv").hide();
                    $("#inventorystencillistdiv").hide();
                    $("#inventorytraylistdiv").hide();
                    $("#inventoryIdMb").val("").trigger('change');
                    $("#inventoryIdStencil").val("").trigger('change');
                    $("#inventoryIdTray").val("").trigger('change');
                    $("#inventoryIdPcb").val("").trigger('change');
                } else if ($(this).val() === "Stencil" && element1.val() === "Ship") {
                    $("#stencillistdiv").show();
                    $("#pcbTypeDiv").hide();
                    $("#listdiv").hide();
                    $("#mblistdiv").hide();
                    $("#traylistdiv").hide();
                    $("#pcbAlistdiv").hide();
                    $("#pcbAQtydiv").hide();
                    $("#pcbBlistdiv").hide();
                    $("#pcbBQtydiv").hide();
                    $("#pcbClistdiv").hide();
                    $("#pcbCQtydiv").hide();
                    $("#pcbCtrlistdiv").hide();
                    $("#pcbCtrQtydiv").hide();
                    //                    $("#typediv").hide();
                    $("#quantitydiv").hide();
                    $("#pcbType").val("").trigger('change');
                    $("#equipmentIdMb").val("").trigger('change');
                    $("#equipmentIdTray").val("").trigger('change');
                    $("#equipmentIdpcbA").val("").trigger('change');
                    $("#pcbAQty").val("");
                    $("#equipmentIdpcbB").val("").trigger('change');
                    $("#pcbBQty").val("");
                    $("#equipmentIdpcbC").val("").trigger('change');
                    $("#pcbCQty").val("");
                    $("#equipmentIdpcbCtr").val("").trigger('change');
                    $("#pcbCtrQty").val("");
                    $("#quantity").val("");
                    //                    $("#type").val("");

                    $("#inventorypcblistdiv").hide();
                    $("#inventorymblistdiv").hide();
                    $("#inventorystencillistdiv").hide();
                    $("#inventorytraylistdiv").hide();
                    $("#inventoryIdMb").val("").trigger('change');
                    $("#inventoryIdStencil").val("").trigger('change');
                    $("#inventoryIdTray").val("").trigger('change');
                    $("#inventoryIdPcb").val("").trigger('change');
                } else if ($(this).val() === "Tray" && element1.val() === "Ship") {
                    $("#traylistdiv").show();
                    $("#quantitydiv").show();
                    $("#pcbTypeDiv").hide();
                    $("#listdiv").hide();
                    $("#stencillistdiv").hide();
                    $("#mblistdiv").hide();
                    $("#pcbAlistdiv").hide();
                    $("#pcbAQtydiv").hide();
                    $("#pcbBlistdiv").hide();
                    $("#pcbBQtydiv").hide();
                    $("#pcbClistdiv").hide();
                    $("#pcbCQtydiv").hide();
                    $("#pcbCtrlistdiv").hide();
                    $("#pcbCtrQtydiv").hide();
                    //                    $("#typediv").hide();
                    $("#pcbType").val("").trigger('change');
                    $("#equipmentIdMb").val("").trigger('change');
                    $("#equipmentIdStencil").val("").trigger('change');
                    $("#equipmentIdpcbA").val("").trigger('change');
                    $("#pcbAQty").val("");
                    $("#equipmentIdpcbB").val("").trigger('change');
                    $("#pcbBQty").val("");
                    $("#equipmentIdpcbC").val("").trigger('change');
                    $("#pcbCQty").val("");
                    $("#equipmentIdpcbCtr").val("").trigger('change');
                    $("#pcbCtrQty").val("");
                    //                    $("#type").val("");

                    $("#inventorypcblistdiv").hide();
                    $("#inventorymblistdiv").hide();
                    $("#inventorystencillistdiv").hide();
                    $("#inventorytraylistdiv").hide();
                    $("#inventoryIdMb").val("").trigger('change');
                    $("#inventoryIdStencil").val("").trigger('change');
                    $("#inventoryIdTray").val("").trigger('change');
                    $("#inventoryIdPcb").val("").trigger('change');
                } else if ($(this).val() === "PCB" && element1.val() === "Ship") {
                    $("#pcbTypeDiv").show();
                    $("#pcbAlistdiv").show();
                    $("#pcbAQtydiv").show();
                    $("#pcbBlistdiv").show();
                    $("#pcbBQtydiv").show();
                    $("#pcbClistdiv").show();
                    $("#pcbCQtydiv").show();
                    $("#pcbCtrlistdiv").show();
                    $("#pcbCtrQtydiv").show();
                    //                    $("#typediv").hide();
                    //                    $("#quantitydiv").show();
                    $("#quantitydiv").hide();
                    $("#listdiv").hide();
                    $("#stencillistdiv").hide();
                    $("#mblistdiv").hide();
                    $("#traylistdiv").hide();
                    $("#equipmentIdMb").val("").trigger('change');
                    $("#equipmentIdStencil").val("").trigger('change');
                    $("#equipmentIdTray").val("").trigger('change');

                    $("#inventorypcblistdiv").hide();
                    $("#inventorymblistdiv").hide();
                    $("#inventorystencillistdiv").hide();
                    $("#inventorytraylistdiv").hide();
                    $("#inventoryIdMb").val("").trigger('change');
                    $("#inventoryIdStencil").val("").trigger('change');
                    $("#inventoryIdTray").val("").trigger('change');
                    $("#inventoryIdPcb").val("").trigger('change');
                } else if ($(this).val() === "Motherboard" && element1.val() === "Retrieve") {
                    $("#inventorymblistdiv").show();
                    $("#pcbTypeDiv").hide();
                    $("#listdiv").hide();
                    $("#inventorystencillistdiv").hide();
                    $("#inventorytraylistdiv").hide();
                    $("#inventorypcblistdiv").hide();
                    $("#quantitydiv").hide();
                    $("#pcbType").val("").trigger('change');
                    $("#inventoryIdStencil").val("").trigger('change');
                    $("#inventoryIdTray").val("").trigger('change');
                    $("#equipmentIdpcbA").val("").trigger('change');
                    $("#pcbAQty").val("");
                    $("#equipmentIdpcbB").val("").trigger('change');
                    $("#pcbBQty").val("");
                    $("#equipmentIdpcbC").val("").trigger('change');
                    $("#pcbCQty").val("");
                    $("#equipmentIdpcbCtr").val("").trigger('change');
                    $("#pcbCtrQty").val("");
                    $("#quantity").val("");

                    $("#pcbAlistdiv").hide();
                    $("#pcbAQtydiv").hide();
                    $("#pcbBlistdiv").hide();
                    $("#pcbBQtydiv").hide();
                    $("#pcbClistdiv").hide();
                    $("#pcbCQtydiv").hide();
                    $("#pcbCtrlistdiv").hide();
                    $("#pcbCtrQtydiv").hide();
                    $("#mblistdiv").hide();
                    $("#stencillistdiv").hide();
                    $("#traylistdiv").hide();
                    $("#equipmentIdMb").val("").trigger('change');
                    $("#equipmentIdStencil").val("").trigger('change');
                    $("#equipmentIdTray").val("").trigger('change');
                    $("#equipmentIdPcb").val("").trigger('change');
                } else if ($(this).val() === "Stencil" && element1.val() === "Retrieve") {
                    $("#inventorystencillistdiv").show();
                    $("#pcbTypeDiv").hide();
                    $("#listdiv").hide();
                    $("#inventorymblistdiv").hide();
                    $("#inventorytraylistdiv").hide();
                    $("#inventorypcblistdiv").hide();
                    $("#quantitydiv").hide();
                    $("#pcbType").val("").trigger('change');
                    $("#inventoryIdMb").val("").trigger('change');
                    $("#inventoryIdTray").val("").trigger('change');
                    $("#equipmentIdpcbA").val("").trigger('change');
                    $("#pcbAQty").val("");
                    $("#equipmentIdpcbB").val("").trigger('change');
                    $("#pcbBQty").val("");
                    $("#equipmentIdpcbC").val("").trigger('change');
                    $("#pcbCQty").val("");
                    $("#equipmentIdpcbCtr").val("").trigger('change');
                    $("#pcbCtrQty").val("");
                    $("#quantity").val("");

                    $("#pcbAlistdiv").hide();
                    $("#pcbAQtydiv").hide();
                    $("#pcbBlistdiv").hide();
                    $("#pcbBQtydiv").hide();
                    $("#pcbClistdiv").hide();
                    $("#pcbCQtydiv").hide();
                    $("#pcbCtrlistdiv").hide();
                    $("#pcbCtrQtydiv").hide();
                    $("#mblistdiv").hide();
                    $("#stencillistdiv").hide();
                    $("#traylistdiv").hide();
                    $("#equipmentIdMb").val("").trigger('change');
                    $("#equipmentIdStencil").val("").trigger('change');
                    $("#equipmentIdTray").val("").trigger('change');
                    $("#equipmentIdPcb").val("").trigger('change');
                } else if ($(this).val() === "Tray" && element1.val() === "Retrieve") {
                    $("#inventorytraylistdiv").show();
                    $("#pcbTypeDiv").hide();
                    //                    $("#quantitydiv").show();
                    $("#quantitydiv").hide();
                    $("#listdiv").hide();
                    $("#inventorystencillistdiv").hide();
                    $("#inventorymblistdiv").hide();
                    $("#inventorypcblistdiv").hide();
                    $("#pcbType").val("").trigger('change');
                    $("#inventoryIdMb").val("").trigger('change');
                    $("#inventoryIdStencil").val("").trigger('change');
                    $("#equipmentIdpcbA").val("").trigger('change');
                    $("#pcbAQty").val("");
                    $("#equipmentIdpcbB").val("").trigger('change');
                    $("#pcbBQty").val("");
                    $("#equipmentIdpcbC").val("").trigger('change');
                    $("#pcbCQty").val("");
                    $("#equipmentIdpcbCtr").val("").trigger('change');
                    $("#pcbCtrQty").val("");

                    $("#pcbAlistdiv").hide();
                    $("#pcbAQtydiv").hide();
                    $("#pcbBlistdiv").hide();
                    $("#pcbBQtydiv").hide();
                    $("#pcbClistdiv").hide();
                    $("#pcbCQtydiv").hide();
                    $("#pcbCtrlistdiv").hide();
                    $("#pcbCtrQtydiv").hide();
                    $("#mblistdiv").hide();
                    $("#stencillistdiv").hide();
                    $("#traylistdiv").hide();
                    $("#equipmentIdMb").val("").trigger('change');
                    $("#equipmentIdStencil").val("").trigger('change');
                    $("#equipmentIdTray").val("").trigger('change');
                    $("#equipmentIdPcb").val("").trigger('change');
                } else if ($(this).val() === "PCB" && element1.val() === "Retrieve") {
                    $("#inventorypcblistdiv").show();
                    $("#pcbTypeDiv").hide();
                    //                    $("#quantitydiv").show();
                    $("#quantitydiv").hide();
                    $("#listdiv").hide();
                    $("#inventorystencillistdiv").hide();
                    $("#inventorymblistdiv").hide();
                    $("#inventorytraylistdiv").hide();
                    $("#inventoryIdMb").val("").trigger('change');
                    $("#inventoryIdStencil").val("").trigger('change');
                    $("#inventoryIdTray").val("").trigger('change');


                    $("#pcbAlistdiv").hide();
                    $("#pcbAQtydiv").hide();
                    $("#pcbBlistdiv").hide();
                    $("#pcbBQtydiv").hide();
                    $("#pcbClistdiv").hide();
                    $("#pcbCQtydiv").hide();
                    $("#pcbCtrlistdiv").hide();
                    $("#pcbCtrQtydiv").hide();
                    $("#mblistdiv").hide();
                    $("#stencillistdiv").hide();
                    $("#traylistdiv").hide();
                    $("#equipmentIdMb").val("").trigger('change');
                    $("#equipmentIdStencil").val("").trigger('change');
                    $("#equipmentIdTray").val("").trigger('change');
                    $("#equipmentIdPcbA").val("").trigger('change');
                    $("#pcbAQty").val("");
                    $("#equipmentIdPcbB").val("").trigger('change');
                    $("#pcbBQty").val("");
                    $("#equipmentIdPcbC").val("").trigger('change');
                    $("#pcbCQty").val("");
                    $("#equipmentIdPcbCtr").val("").trigger('change');
                    $("#pcbCtrQty").val("");
                } else {
                    $("#listdiv").show();
                    $("#pcbTypeDiv").hide();
                    $("#pcbAlistdiv").hide();
                    $("#pcbAQtydiv").hide();
                    $("#pcbBlistdiv").hide();
                    $("#pcbBQtydiv").hide();
                    $("#pcbClistdiv").hide();
                    $("#pcbCQtydiv").hide();
                    $("#pcbCtrlistdiv").hide();
                    $("#pcbCtrQtydiv").hide();
                    $("#mblistdiv").hide();
                    $("#stencillistdiv").hide();
                    $("#traylistdiv").hide();
                    //                    $("#typediv").hide();
                    $("#quantitydiv").hide();
                    $("#pcbType").val("").trigger('change');
                    $("#equipmentIdMb").val("").trigger('change');
                    $("#equipmentIdStencil").val("").trigger('change');
                    $("#equipmentIdTray").val("").trigger('change');
                    $("#equipmentIdpcbA").val("").trigger('change');
                    $("#pcbAQty").val("");
                    $("#equipmentIdpcbB").val("").trigger('change');
                    $("#pcbBQty").val("");
                    $("#equipmentIdpcbC").val("").trigger('change');
                    $("#pcbCQty").val("");
                    $("#equipmentIdpcbCtr").val("").trigger('change');
                    $("#pcbCtrQty").val("");
                    $("#quantity").val("");
                    $("#inventorypcblistdiv").hide();
                    $("#inventorymblistdiv").hide();
                    $("#inventorystencillistdiv").hide();
                    $("#inventorytraylistdiv").hide();
                    $("#inventoryIdMb").val("").trigger('change');
                    $("#inventoryIdStencil").val("").trigger('change');
                    $("#inventoryIdTray").val("").trigger('change');
                    $("#inventoryIdPcb").val("").trigger('change');
                    //                    $("#type").val("");
                }

            });

            $('#equipmentIdpcbCtr').on('change', function () {
                var elementA = $('#equipmentIdpcbA');
                var elementB = $('#equipmentIdpcbB');
                var elementC = $('#equipmentIdpcbC');
                if ($(this).val() !== "" && ($(this).val() === elementA.val() || ($(this).val() === elementB.val()) || ($(this).val() === elementC.val()))) {
                    $('#equipmentIdpcbCtr').select2('val', '');
                }
            });

            $('#equipmentIdpcbA').on('change', function () {
                var elementCtr = $('#equipmentIdpcbCtr');
                var elementB = $('#equipmentIdpcbB');
                var elementC = $('#equipmentIdpcbC');
                if ($(this).val() !== "" && ($(this).val() === elementCtr.val() || ($(this).val() === elementB.val()) || ($(this).val() === elementC.val()))) {
                    $('#equipmentIdpcbA').select2('val', '');
                }
            });

            $('#equipmentIdpcbB').on('change', function () {
                var elementA = $('#equipmentIdpcbA');
                var elementC = $('#equipmentIdpcbC');
                var elementCtr = $('#equipmentIdpcbCtr');
                if ($(this).val() !== "" && ($(this).val() === elementA.val() || ($(this).val() === elementCtr.val()) || ($(this).val() === elementC.val()))) {
                    //                    alert("This pcb already selected");
                    $('#equipmentIdpcbB').select2('val', '');
                }
            });

            $('#equipmentIdpcbC').on('change', function () {
                var elementA = $('#equipmentIdpcbA');
                var elementB = $('#equipmentIdpcbB');
                var elementCtr = $('#equipmentIdpcbCtr');
                if ($(this).val() !== "" && ($(this).val() === elementA.val() || ($(this).val() === elementCtr.val()) || ($(this).val() === elementB.val()))) {
                    //                    alert("This pcb already selected");
                    $('#equipmentIdpcbC').select2('val', '');
                }
            });



        </script>
    </s:layout-component>
</s:layout-render>