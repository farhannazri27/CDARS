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

            .no-border {
                border: 0;
                box-shadow: none; /* You may want to include this as bootstrap applies these styles too */
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
                                            <option pcbTypeQty="${group.quantity}" value="${group.pcbType}" ${group.selected}>${group.pcbType}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="maxQtyPcbType" class="col-lg-1 control-label">Quantity:</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="maxQtyPcbType" name="maxQtyPcbType" value="" readonly >
                                </div>
                            </div>
                            <div class="form-group" id="listdiv">
                                <label for="equipmentId" class="col-lg-2 control-label">Equipment ID * </label>
                                <div class="col-lg-7">                                      
                                    <select id="equipmentId" name="equipmentId" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="mblistdiv" hidden>
                                <label for="equipmentIdMb" class="col-lg-2 control-label">Motherboard ID * </label>
                                <div class="col-lg-7">                                      
                                    <select id="equipmentIdMb" name="equipmentIdMb" class="js-example-basic-single" style="width: 100%" >
                                        <option value="" selected=""></option>
                                        <c:forEach items="${bibItemList}" var="group">
                                            <option mbquantity="${group.OnHandQty}" value="${group.ItemID}">${group.ItemID}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="maxQtyMb" class="col-lg-1 control-label">Quantity:</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="maxQtyMb" name="maxQtyMb" value="" readonly >
                                </div>
                            </div>
                            <div class="form-group" id="inventorymblistdiv" hidden>
                                <label for="inventoryIdMb" class="col-lg-2 control-label">Motherboard ID * </label>
                                <div class="col-lg-7">                  
                                    <select id="inventoryIdMb" name="inventoryIdMb" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${inventoryListMb}" var="group">
                                            <option invQtyMbValue="${group.quantity}" value="${group.id}" ${group.selected}>${group.equipmentId}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="invQtyMb" class="col-lg-1 control-label">Quantity:</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="invQtyMb" name="invQtyMb" value="" readonly >
                                </div>
                            </div>
                            <div class="form-group" id="stencillistdiv" hidden>
                                <label for="equipmentIdStencil" class="col-lg-2 control-label">Stencil ID * </label>
                                <div class="col-lg-7">                                      
                                    <select id="equipmentIdStencil" name="equipmentIdStencil" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${StencilItemList}" var="group">
                                            <option stencilquantity="${group.OnHandQty}" value="${group.ItemID}" >${group.ItemID}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="maxQtyStencil" class="col-lg-1 control-label">Quantity:</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="maxQtyStencil" name="maxQtyStencil" value="" readonly >
                                </div>
                            </div>
                            <div class="form-group" id="inventorystencillistdiv" hidden>
                                <label for="inventoryIdStencil" class="col-lg-2 control-label">Stencil ID * </label>
                                <div class="col-lg-7">                                      
                                    <select id="inventoryIdStencil" name="inventoryIdStencil" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${inventoryListStencil}" var="group">
                                            <option invQtyStencilValue="${group.quantity}" value="${group.id}" ${group.selected}>${group.equipmentId}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="invQtyStencil" class="col-lg-1 control-label">Quantity:</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="invQtyStencil" name="invQtyStencil" value="" readonly >
                                </div>
                            </div>
                            <div class="form-group" id="traylistdiv" hidden>
                                <label for="equipmentIdTray" class="col-lg-2 control-label">Tray ID * </label>
                                <div class="col-lg-7">                                                    
                                    <select id="equipmentIdTray" name="equipmentIdTray" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${trayItemList}" var="group">
                                            <option trayquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="maxQtyTray" class="col-lg-1 control-label">Quantity:</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="maxQtyTray" name="maxQtyTray" value="" readonly >
                                </div>
                            </div>
                            <div class="form-group" id="inventorytraylistdiv" hidden>
                                <label for="inventoryIdTray" class="col-lg-2 control-label">Tray ID * </label>
                                <div class="col-lg-7">                                      
                                    <select id="inventoryIdTray" name="inventoryIdTray" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${inventoryListTray}" var="group">
                                            <option invQtyTrayValue="${group.quantity}" value="${group.id}" ${group.selected}>${group.equipmentId}/(mpNo: ${group.mpNo})</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="invQtyTray" class="col-lg-1 control-label">Quantity:</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="invQtyTray" name="invQtyTray" value="" readonly >
                                </div>
                            </div>

                            <div class="form-group" id="pcbCtrlistdiv" hidden>
                                <label for="equipmentIdpcbCtr" class="col-lg-2 control-label">PCB ID Control*  </label>
                                <div class="col-lg-7">                                      
                                    <select id="equipmentIdpcbCtr" name="equipmentIdpcbCtr" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${pcbItemListCtr}" var="group">
                                            <option pcbCtrquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="maxQtyCtr" class="col-lg-1 control-label">Quantity:</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="maxQtyCtr" name="maxQtyCtr" value="" readonly >
                                    <input type="hidden" class="form-control" id="maxQtyCtr1" name="maxQtyCtr1" value="0" readonly >
                                </div>
                            </div>
                            <div class="form-group" id="pcbCtrQtydiv" hidden>
                                <label for="pcbCtrQty" class="col-lg-2 control-label">Quantity Control* </label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="pcbCtrQty" name="pcbCtrQty" value="0">
                                </div>
                            </div>    
                            <div class="form-group" id="pcbAlistdiv" hidden>
                                <label for="equipmentIdpcbA" class="col-lg-2 control-label">PCB ID Qual A * </label>
                                <div class="col-lg-7">                                      
                                    <select id="equipmentIdpcbA" name="equipmentIdpcbA" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${pcbItemListA}" var="group">
                                            <option pcbAquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="maxQtyA" class="col-lg-1 control-label">Quantity:</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="maxQtyA" name="maxQtyA" value="" readonly >
                                    <input type="hidden" class="form-control" id="maxQtyA1" name="maxQtyA1" value="0" readonly >
                                </div>
                            </div>
                            <div class="form-group" id="pcbAQtydiv" hidden>
                                <label for="pcbAQty" class="col-lg-2 control-label">Quantity Qual A *</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="pcbAQty" name="pcbAQty" value="0">
                                </div>
                            </div> 
                            <div class="form-group" id="pcbBlistdiv" hidden>
                                <label for="equipmentIdpcbB" class="col-lg-2 control-label">PCB ID Qual B * </label>
                                <div class="col-lg-7">                                      
                                    <select id="equipmentIdpcbB" name="equipmentIdpcbB" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${pcbItemListB}" var="group">
                                            <option pcbBquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="maxQtyB" class="col-lg-1 control-label">Quantity:</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="maxQtyB" name="maxQtyB" value="" readonly >
                                    <input type="hidden" class="form-control" id="maxQtyB1" name="maxQtyB1" value="0" readonly >
                                </div>
                            </div>
                            <div class="form-group" id="pcbBQtydiv" hidden>
                                <label for="pcbBQty" class="col-lg-2 control-label">Quantity Qual B *</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="pcbBQty" name="pcbBQty" value="0">
                                </div>
                            </div>    
                            <div class="form-group" id="pcbClistdiv" hidden>
                                <label for="equipmentIdpcbC" class="col-lg-2 control-label">PCB ID Qual C * </label>
                                <div class="col-lg-7">                                      
                                    <select id="equipmentIdpcbC" name="equipmentIdpcbC" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${pcbItemListC}" var="group">
                                            <option pcbCquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="maxQtyC" class="col-lg-1 control-label">Quantity:</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="maxQtyC" name="maxQtyC" value="" readonly >
                                    <input type="hidden" class="form-control" id="maxQtyC1" name="maxQtyC1" value="0" readonly >
                                </div>
                            </div>
                            <div class="form-group" id="pcbCQtydiv" hidden>
                                <label for="pcbCQty" class="col-lg-2 control-label">Quantity Qual C *</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="pcbCQty" name="pcbCQty" value="0">
                                </div>
                            </div>    

                            <div class="form-group" id="inventorypcblistdiv" hidden>
                                <label for="inventoryIdPcb" class="col-lg-2 control-label">PCB ID * </label>
                                <div class="col-lg-7">                                      
                                    <select id="inventoryIdPcb" name="inventoryIdPcb" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${inventoryListPCB}" var="group">
                                            <option invQtyPcbValue="${group.quantity}" 
                                                    invQtyPcbAValue="${group.pcbAQty}" 
                                                    invQtyPcbBValue="${group.pcbBQty}" 
                                                    invQtyPcbCValue="${group.pcbCQty}" 
                                                    invQtyPcbCtrValue="${group.pcbCtrQty}"
                                                    value="${group.id}" ${group.selected}>${group.equipmentId}/(${group.mpNo})</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="invQtyPcb" class="col-lg-1 control-label">Quantity:</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="invQtyPcb" name="invQtyPcb" value="" readonly >
                                </div>
                            </div> 
                            <div class="form-group" id="quantityABdiv" hidden>
                                <div class="col-lg-1"></div>      
                                <label for="quantityA" class="col-lg-2 control-label">Quantity Qual A:</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="quantityA" name="quantityA" value="" readonly>
                                </div>
                                <label for="quantityB" class="col-lg-2 control-label">Quantity Qual B:</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="quantityB" name="quantityB" value="" readonly>
                                </div>
                            </div>  
                            <div class="form-group" id="quantityCCtrdiv" hidden>
                                <div class="col-lg-1"></div>      
                                <label for="quantityC" class="col-lg-2 control-label">Quantity Qual C:</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="quantityC" name="quantityC" value="" readonly>
                                </div>
                                <label for="quantityCtr" class="col-lg-2 control-label">Quantity Control:</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="quantityCtr" name="quantityCtr" value="" readonly>
                                </div>
                            </div>  
                            <div class="form-group" id="quantitydiv" hidden>
                                <label for="quantity" class="col-lg-2 control-label">Quantity *</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="quantity" name="quantity" value="">
                                </div>
                            </div>  

                            <div class="form-group" id="quantitytestdiv" hidden>
                                <label for="quantitytest" class="col-lg-2 control-label">Total Quantity</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="quantitytest" name="quantitytest" value="0" readonly>
                                </div>
                            </div> 
                            <div class="form-group" id="remarksdiv">
                                <label for="remarks" class="col-lg-2 control-label">Remarks </label>
                                <div class="col-lg-7">
                                    <textarea class="form-control" rows="5" id="remarks" name="remarks">${whRequest.remarks}</textarea>
                                </div>
                            </div>
                            <a href="${contextPath}/wh/whRequest" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                            <div class="pull-right">
                                <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                <button type="submit" id="submit" class="btn btn-primary">Send</button>
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

                $("form").keyup(function () {
////                    if ($('#equipmentType').val() === "PCB" && $('#requestType').val() === "Ship" && $("#quantitytest").val() === "0") {
                    if (!($('#equipmentType').val() === "PCB" && $('#requestType').val() === "Ship" && $("#quantitytest").val() === "0")) {
                        $("#submit").removeAttr('disabled');
////            $("#submit").attr("disabled", true);
                    } else {
////                        $("#submit").removeAttr('disabled');
                        $("#submit").attr("disabled", true);
                    }
                });

                $("form").mouseup(function () {
                    if (!($('#equipmentType').val() === "PCB" && $('#requestType').val() === "Ship" && $("#quantitytest").val() === "0")) {
                        $("#submit").removeAttr('disabled');
                    } else {
                        $("#submit").attr("disabled", true);
                    }
                });

                $('#pcbCtrQty').change(function () {
                    $('#quantitytest').val(parseInt($('#pcbCtrQty').val()) + parseInt($('#pcbAQty').val()) + parseInt($('#pcbBQty').val()) + parseInt($('#pcbCQty').val()));
                });
                $('#pcbAQty').change(function () {
                    $('#quantitytest').val(parseInt($('#pcbCtrQty').val()) + parseInt($('#pcbAQty').val()) + parseInt($('#pcbBQty').val()) + parseInt($('#pcbCQty').val()));
                });
                $('#pcbBQty').change(function () {
                    $('#quantitytest').val(parseInt($('#pcbCtrQty').val()) + parseInt($('#pcbAQty').val()) + parseInt($('#pcbBQty').val()) + parseInt($('#pcbCQty').val()));
                });
                $('#pcbCQty').change(function () {
                    $('#quantitytest').val(parseInt($('#pcbCtrQty').val()) + parseInt($('#pcbAQty').val()) + parseInt($('#pcbBQty').val()) + parseInt($('#pcbCQty').val()));
                });
                $('#pcbType').change(function () {
                    $('#maxQtyPcbType').val($('option:selected', this).attr('pcbTypeQty'));
                });
                $('#equipmentIdpcbCtr').change(function () {
                    $('#maxQtyCtr').val($('option:selected', this).attr('pcbCtrquantity'));
                    $('#maxQtyCtr1').val($('option:selected', this).attr('pcbCtrquantity'));
                    if($(this).val() === ""){
                         $('#maxQtyCtr1').val("0");
                    }
                });
                $('#equipmentIdpcbA').change(function () {
                    $('#maxQtyA').val($('option:selected', this).attr('pcbAquantity'));
                    $('#maxQtyA1').val($('option:selected', this).attr('pcbAquantity'));
                     if($(this).val() === ""){
                         $('#maxQtyA1').val("0");
                    }
                });
                $('#equipmentIdpcbB').change(function () {
                    $('#maxQtyB').val($('option:selected', this).attr('pcbBquantity'));
                    $('#maxQtyB1').val($('option:selected', this).attr('pcbBquantity'));
                     if($(this).val() === ""){
                         $('#maxQtyB1').val("0");
                    }
                });
                $('#equipmentIdpcbC').change(function () {
                    $('#maxQtyC').val($('option:selected', this).attr('pcbCquantity'));
                    $('#maxQtyC1').val($('option:selected', this).attr('pcbCquantity'));
                     if($(this).val() === ""){
                         $('#maxQtyC1').val("0");
                    }
                });
                $('#equipmentIdMb').change(function () {
                    $('#maxQtyMb').val($('option:selected', this).attr('mbquantity'));
                });
                $('#equipmentIdTray').change(function () {
                    $('#maxQtyTray').val($('option:selected', this).attr('trayquantity'));
                });
                $('#equipmentIdStencil').change(function () {
                    $('#maxQtyStencil').val($('option:selected', this).attr('stencilquantity'));
                });
                $('#inventoryIdPcb').change(function () {
                    $('#invQtyPcb').val($('option:selected', this).attr('invQtyPcbValue'));
                    $('#quantityA').val($('option:selected', this).attr('invQtyPcbAValue'));
                    $('#quantityB').val($('option:selected', this).attr('invQtyPcbBValue'));
                    $('#quantityC').val($('option:selected', this).attr('invQtyPcbCValue'));
                    $('#quantityCtr').val($('option:selected', this).attr('invQtyPcbCtrValue'));
                });
                $('#inventoryIdMb').change(function () {
                    $('#invQtyMb').val($('option:selected', this).attr('invQtyMbValue'));
                });
                $('#inventoryIdTray').change(function () {
                    $('#invQtyTray').val($('option:selected', this).attr('invQtyTrayValue'));
                });
                $('#inventoryIdStencil').change(function () {
                    $('#invQtyStencil').val($('option:selected', this).attr('invQtyStencilValue'));
                });

                $(".js-example-basic-single").select2({
                    placeholder: "Choose one",
                    allowClear: true
                });
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
//                        equipmentIdpcbCtr: {
//                            required: true
//                        },
                        pcbCtrQty: {
//                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyCtr1').val());
                            }
                        },
//                        equipmentIdpcbA: {
//                            required: true
//                        },
                        pcbAQty: {
//                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyA1').val());
                            }
                        },
//                        equipmentIdpcbB: {
//                            required: true
//                        },
                        pcbBQty: {
//                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyB1').val());
                            }
                        },
//                        equipmentIdpcbC: {
//                            required: true
//                        },
                        pcbCQty: {
//                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyC1').val());
                            }
                        },
                        quantitytest: {
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyPcbType').val());
                            }
                        },
                        quantity: {
                            required: true,
                            number: true,
                            max: function () {
                                return parseInt($('#maxQtyTray').val());
                            }
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
                    $("#quantitytest").val("0");
                    $("#quantitydiv").hide();
                    $("#quantitytestdiv").hide();
                    $("#quantityABdiv").hide();
                    $("#quantityCCtrdiv").hide();
                    $("#pcbType").val("").trigger('change');
                    $("#equipmentIdStencil").val("").trigger('change');
                    $("#equipmentIdTray").val("").trigger('change');
                    $("#equipmentIdpcbA").val("").trigger('change');
                    $("#pcbAQty").val("0");
                    $("#equipmentIdpcbB").val("").trigger('change');
                    $("#pcbBQty").val("0");
                    $("#equipmentIdpcbC").val("").trigger('change');
                    $("#pcbCQty").val("0");
                    $("#equipmentIdpcbCtr").val("").trigger('change');
                    $("#pcbCtrQty").val("0");
                    $("#quantity").val("");
                    $("#inventorypcblistdiv").hide();
                    $("#inventorymblistdiv").hide();
                    $("#inventorystencillistdiv").hide();
                    $("#inventorytraylistdiv").hide();
                    $("#inventoryIdMb").val("").trigger('change');
                    $("#inventoryIdStencil").val("").trigger('change');
                    $("#inventoryIdTray").val("").trigger('change');
                    $("#inventoryIdPcb").val("").trigger('change');
                    $("#maxQtyCtr1").val("0");
                    $("#maxQtyA1").val("0");
                    $("#maxQtyB1").val("0");
                    $("#maxQtyC1").val("0");
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
                    $("#quantitydiv").hide();
                    $("#quantitytestdiv").hide();
                    $("#quantityABdiv").hide();
                    $("#quantityCCtrdiv").hide();
                    $("#pcbType").val("").trigger('change');
                    $("#equipmentIdMb").val("").trigger('change');
                    $("#equipmentIdTray").val("").trigger('change');
                    $("#equipmentIdpcbA").val("").trigger('change');
                    $("#pcbAQty").val("0");
                    $("#equipmentIdpcbB").val("").trigger('change');
                    $("#pcbBQty").val("0");
                    $("#equipmentIdpcbC").val("").trigger('change');
                    $("#pcbCQty").val("0");
                    $("#equipmentIdpcbCtr").val("").trigger('change');
                    $("#pcbCtrQty").val("0");
                    $("#quantity").val("");
                    $("#quantitytest").val("0");
                    $("#inventorypcblistdiv").hide();
                    $("#inventorymblistdiv").hide();
                    $("#inventorystencillistdiv").hide();
                    $("#inventorytraylistdiv").hide();
                    $("#inventoryIdMb").val("").trigger('change');
                    $("#inventoryIdStencil").val("").trigger('change');
                    $("#inventoryIdTray").val("").trigger('change');
                    $("#inventoryIdPcb").val("").trigger('change');
                    $("#maxQtyCtr1").val("0");
                    $("#maxQtyA1").val("0");
                    $("#maxQtyB1").val("0");
                    $("#maxQtyC1").val("0");
                } else if ($(this).val() === "Tray" && element1.val() === "Ship") {
                    $("#traylistdiv").show();
                    $("#quantitydiv").show();
                    $("#quantitytestdiv").hide();
                    $("#quantityABdiv").hide();
                    $("#quantityCCtrdiv").hide();
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
                    $("#pcbType").val("").trigger('change');
                    $("#equipmentIdMb").val("").trigger('change');
                    $("#equipmentIdStencil").val("").trigger('change');
                    $("#equipmentIdpcbA").val("").trigger('change');
                    $("#pcbAQty").val("0");
                    $("#equipmentIdpcbB").val("").trigger('change');
                    $("#pcbBQty").val("0");
                    $("#equipmentIdpcbC").val("").trigger('change');
                    $("#pcbCQty").val("0");
                    $("#equipmentIdpcbCtr").val("").trigger('change');
                    $("#pcbCtrQty").val("0");
                    $("#quantitytest").val("0");
                    $("#inventorypcblistdiv").hide();
                    $("#inventorymblistdiv").hide();
                    $("#inventorystencillistdiv").hide();
                    $("#inventorytraylistdiv").hide();
                    $("#inventoryIdMb").val("").trigger('change');
                    $("#inventoryIdStencil").val("").trigger('change');
                    $("#inventoryIdTray").val("").trigger('change');
                    $("#inventoryIdPcb").val("").trigger('change');
                    $("#maxQtyCtr1").val("0");
                    $("#maxQtyA1").val("0");
                    $("#maxQtyB1").val("0");
                    $("#maxQtyC1").val("0");
                } else if ($(this).val() === "PCB" && element1.val() === "Ship") {
//                    if ($("#quantitytest").val() === "0") {
//                        $("#submit").attr("disabled", true);
//                    }
                    $("#pcbTypeDiv").show();
                    $("#pcbAlistdiv").show();
                    $("#pcbAQtydiv").show();
                    $("#pcbBlistdiv").show();
                    $("#pcbBQtydiv").show();
                    $("#pcbClistdiv").show();
                    $("#pcbCQtydiv").show();
                    $("#pcbCtrlistdiv").show();
                    $("#pcbCtrQtydiv").show();
                    $("#quantitytestdiv").show();
                    $("#quantityABdiv").hide();
                    $("#quantityCCtrdiv").hide();
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
                    $("#quantitytestdiv").hide();
                    $("#quantityABdiv").hide();
                    $("#quantityCCtrdiv").hide();
                    $("#pcbType").val("").trigger('change');
                    $("#inventoryIdStencil").val("").trigger('change');
                    $("#inventoryIdTray").val("").trigger('change');
                    $("#inventoryIdPcb").val("").trigger('change');
                    $("#equipmentIdpcbA").val("").trigger('change');
                    $("#pcbAQty").val("0");
                    $("#equipmentIdpcbB").val("").trigger('change');
                    $("#pcbBQty").val("0");
                    $("#equipmentIdpcbC").val("").trigger('change');
                    $("#pcbCQty").val("0");
                    $("#equipmentIdpcbCtr").val("").trigger('change');
                    $("#pcbCtrQty").val("0");
                    $("#quantity").val("");
                    $("#quantitytest").val("0");
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
                    $("#maxQtyCtr1").val("0");
                    $("#maxQtyA1").val("0");
                    $("#maxQtyB1").val("0");
                    $("#maxQtyC1").val("0");
                } else if ($(this).val() === "Stencil" && element1.val() === "Retrieve") {
                    $("#inventorystencillistdiv").show();
                    $("#pcbTypeDiv").hide();
                    $("#listdiv").hide();
                    $("#inventorymblistdiv").hide();
                    $("#inventorytraylistdiv").hide();
                    $("#inventorypcblistdiv").hide();
                    $("#quantitydiv").hide();
                    $("#quantitytestdiv").hide();
                    $("#quantityABdiv").hide();
                    $("#quantityCCtrdiv").hide();
                    $("#pcbType").val("").trigger('change');
                    $("#inventoryIdMb").val("").trigger('change');
                    $("#inventoryIdTray").val("").trigger('change');
                    $("#inventoryIdPcb").val("").trigger('change');
                    $("#equipmentIdpcbA").val("").trigger('change');
                    $("#pcbAQty").val("0");
                    $("#equipmentIdpcbB").val("").trigger('change');
                    $("#pcbBQty").val("0");
                    $("#equipmentIdpcbC").val("").trigger('change');
                    $("#pcbCQty").val("0");
                    $("#equipmentIdpcbCtr").val("").trigger('change');
                    $("#pcbCtrQty").val("0");
                    $("#quantity").val("");
                    $("#quantitytest").val("0");
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
                    $("#maxQtyCtr1").val("0");
                    $("#maxQtyA1").val("0");
                    $("#maxQtyB1").val("0");
                    $("#maxQtyC1").val("0");
                } else if ($(this).val() === "Tray" && element1.val() === "Retrieve") {
                    $("#inventorytraylistdiv").show();
                    $("#pcbTypeDiv").hide();
                    $("#quantitydiv").hide();
                    $("#quantitytestdiv").hide();
                    $("#quantityABdiv").hide();
                    $("#quantityCCtrdiv").hide();
                    $("#listdiv").hide();
                    $("#inventorystencillistdiv").hide();
                    $("#inventorymblistdiv").hide();
                    $("#inventorypcblistdiv").hide();
                    $("#pcbType").val("").trigger('change');
                    $("#inventoryIdMb").val("").trigger('change');
                    $("#inventoryIdStencil").val("").trigger('change');
                    $("#inventoryIdPcb").val("").trigger('change');
                    $("#equipmentIdpcbA").val("").trigger('change');
                    $("#pcbAQty").val("0");
                    $("#equipmentIdpcbB").val("").trigger('change');
                    $("#pcbBQty").val("0");
                    $("#equipmentIdpcbC").val("").trigger('change');
                    $("#pcbCQty").val("0");
                    $("#equipmentIdpcbCtr").val("").trigger('change');
                    $("#pcbCtrQty").val("0");
                    $("#quantitytest").val("0");
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
                    $("#maxQtyCtr1").val("0");
                    $("#maxQtyA1").val("0");
                    $("#maxQtyB1").val("0");
                    $("#maxQtyC1").val("0");
                } else if ($(this).val() === "PCB" && element1.val() === "Retrieve") {
                    $("#inventorypcblistdiv").show();
                    $("#pcbTypeDiv").hide();
                    $("#quantitydiv").hide();
                    $("#quantitytestdiv").hide();
                    $("#quantityABdiv").show();
                    $("#quantityCCtrdiv").show();
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
                    $("#pcbAQty").val("0");
                    $("#equipmentIdPcbB").val("").trigger('change');
                    $("#pcbBQty").val("0");
                    $("#equipmentIdPcbC").val("").trigger('change');
                    $("#pcbCQty").val("0");
                    $("#equipmentIdPcbCtr").val("").trigger('change');
                    $("#pcbCtrQty").val("0");
                    $("#quantitytest").val("0");
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
                    $("#quantitydiv").hide();
                    $("#quantitytestdiv").hide();
                    $("#quantityABdiv").hide();
                    $("#quantityCCtrdiv").hide();
                    $("#pcbType").val("").trigger('change');
                    $("#equipmentIdMb").val("").trigger('change');
                    $("#equipmentIdStencil").val("").trigger('change');
                    $("#equipmentIdTray").val("").trigger('change');
                    $("#equipmentIdpcbA").val("").trigger('change');
                    $("#pcbAQty").val("0");
                    $("#equipmentIdpcbB").val("").trigger('change');
                    $("#pcbBQty").val("0");
                    $("#equipmentIdpcbC").val("").trigger('change');
                    $("#pcbCQty").val("0");
                    $("#equipmentIdpcbCtr").val("").trigger('change');
                    $("#pcbCtrQty").val("0");
                    $("#quantity").val("");
                    $("#quantitytest").val("0");
                    $("#inventorypcblistdiv").hide();
                    $("#inventorymblistdiv").hide();
                    $("#inventorystencillistdiv").hide();
                    $("#inventorytraylistdiv").hide();
                    $("#inventoryIdMb").val("").trigger('change');
                    $("#inventoryIdStencil").val("").trigger('change');
                    $("#inventoryIdTray").val("").trigger('change');
                    $("#inventoryIdPcb").val("").trigger('change');
                    $("#maxQtyCtr1").val("0");
                    $("#maxQtyA1").val("0");
                    $("#maxQtyB1").val("0");
                    $("#maxQtyC1").val("0");
                }

            });
//           



        </script>
    </s:layout-component>
</s:layout-render>