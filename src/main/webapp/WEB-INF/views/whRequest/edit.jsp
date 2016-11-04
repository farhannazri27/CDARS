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
            <h1>Edit Hardware Request</h1>
            <div class="row">
                <div class="col-lg-9">
                    <div class="main-box">
                        <h2>Hardware Request Information</h2>
                        <form id="edit_hardwarequest_form" class="form-horizontal" role="form" action="${contextPath}/wh/whRequest/update" method="post">
                            <input type="hidden" name="id" value="${whRequest.id}" />
                            <div class="form-group">
                                <label for="requestBy" class="col-lg-2 control-label">Request By</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="requestBy" name="requestBy" placeholder="Request For" value="${whRequest.requestedBy}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="requestType" class="col-lg-2 control-label">Request For</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="requestType" name="requestType" value="${whRequest.requestType}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="equipmentType" class="col-lg-2 control-label">Hardware Category</label>
                                <div class="col-lg-5">
                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" value="${whRequest.equipmentType}" readonly>
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
                                <label for="maxQtyPcbType" class="col-lg-2 control-label">Quantity per Box Limit</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="maxQtyPcbType" name="maxQtyPcbType" value="${PcbLimitQty}" readonly >
                                </div>
                            </div>
                            <div class="form-group" id="mblistdiv" hidden>
                                <label for="equipmentIdMb" class="col-lg-2 control-label">Motherboard ID * </label>
                                <div class="col-lg-7">                                      
                                    <select id="equipmentIdMb" name="equipmentIdMb" class="js-example-basic-single" style="width: 100%" >
                                        <option value="" selected=""></option>
                                        <c:forEach items="${itemListbib}" var="group">
                                            <option mbquantity="${group.OnHandQty}" value="${group.ItemID}">${group.ItemID}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="maxQtyMb" class="col-lg-1 control-label">Quantity:</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="maxQtyMb" name="maxQtyMb" value="" readonly >
                                </div>
                            </div>
                            <div class="form-group" id="stencillistdiv" hidden>
                                <label for="equipmentIdStencil" class="col-lg-2 control-label">Stencil ID * </label>
                                <div class="col-lg-7">                                      
                                    <select id="equipmentIdStencil" name="equipmentIdStencil" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${itemListstencil}" var="group">
                                            <option stencilquantity="${group.OnHandQty}" value="${group.ItemID}" >${group.ItemID}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="maxQtyStencil" class="col-lg-1 control-label">Quantity:</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="maxQtyStencil" name="maxQtyStencil" value="" readonly >
                                </div>
                            </div>
                            <div class="form-group" id="traylistdiv" hidden>
                                <label for="equipmentIdTray" class="col-lg-2 control-label">Tray ID * </label>
                                <div class="col-lg-7">                                                    
                                    <select id="equipmentIdTray" name="equipmentIdTray" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${itemListtray}" var="group">
                                            <option trayquantity="${group.OnHandQty}" value="${group.ItemID}" ${group.selected}>${group.ItemID}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label for="maxQtyTray" class="col-lg-1 control-label">Quantity:</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="maxQtyTray" name="maxQtyTray" value="" readonly >
                                </div>
                            </div>
                            <div class="form-group" id="pcbCtrlistdiv" hidden>
                                <label for="equipmentIdpcbCtr" class="col-lg-2 control-label">PCB ID Control*  </label>
                                <div class="col-lg-7">                                      
                                    <select id="equipmentIdpcbCtr" name="equipmentIdpcbCtr" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${itemListpcbCtr}" var="group">
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
                                    <input type="text" class="form-control" id="pcbCtrQty" name="pcbCtrQty" value="${whRequest.pcbCtrQty}">
                                </div>
                            </div>    
                            <div class="form-group" id="pcbAlistdiv" hidden>
                                <label for="equipmentIdpcbA" class="col-lg-2 control-label">PCB ID Qual A * </label>
                                <div class="col-lg-7">                                      
                                    <select id="equipmentIdpcbA" name="equipmentIdpcbA" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${itemListpcbQualA}" var="group">
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
                                    <input type="text" class="form-control" id="pcbAQty" name="pcbAQty" value="${whRequest.pcbAQty}">
                                </div>
                            </div> 
                            <div class="form-group" id="pcbBlistdiv" hidden>
                                <label for="equipmentIdpcbB" class="col-lg-2 control-label">PCB ID Qual B * </label>
                                <div class="col-lg-7">                                      
                                    <select id="equipmentIdpcbB" name="equipmentIdpcbB" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${itemListpcbQualB}" var="group">
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
                                    <input type="text" class="form-control" id="pcbBQty" name="pcbBQty" value="${whRequest.pcbBQty}">
                                </div>
                            </div>    
                            <div class="form-group" id="pcbClistdiv" hidden>
                                <label for="equipmentIdpcbC" class="col-lg-2 control-label">PCB ID Qual C * </label>
                                <div class="col-lg-7">                                      
                                    <select id="equipmentIdpcbC" name="equipmentIdpcbC" class="js-example-basic-single" style="width: 100%">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${itemListpcbQualC}" var="group">
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
                                    <input type="text" class="form-control" id="pcbCQty" name="pcbCQty" value="${whRequest.pcbCQty}">
                                </div>
                            </div>    
                            <div class="form-group" id="quantitydiv" hidden>
                                <label for="quantity" class="col-lg-2 control-label">Quantity *</label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="quantity" name="quantity" value="${whRequest.quantity}">
                                </div>
                            </div>  

                            <div class="form-group" id="quantitytestdiv" hidden>
                                <label for="quantitytest" class="col-lg-2 control-label">Total Quantity</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="quantitytest" name="quantitytest" value="${whRequest.quantity}" readonly>
                                </div>
                            </div> 
                            <div class="form-group" id="remarksdiv">
                                <label for="remarks" class="col-lg-2 control-label" id="remarksLabel">Remarks </label>
                                <div class="col-lg-5" id="remarksDiv2">
                                    <textarea class="form-control" rows="5" id="remarks" name="remarks"></textarea>
                                </div>
                                <div class="col-lg-5">
                                    <textarea class="form-control" rows="5" id="remarksLog" name="remarksLog" readonly>${whRequest.remarksLog}</textarea>
                                </div>
                            </div>
                            <div class="form-group" id="statusDiv" hidden>
                                <label for="status" class="col-lg-2 control-label">Status</label>                                     
                                <div class="col-lg-6">
                                    <input type="text" class="form-control" id="status" name="status" value="${whRequest.status}" readonly>
                                </div>
                            </div>
                            <div class="form-group"id="approvalRemarksDiv" hidden>
                                <label for="remarksApprover" class="col-lg-2 control-label">Remarks </label>
                                <div class="col-lg-8">
                                    <textarea class="form-control" rows="5" id="remarksApprover" name="remarksApprover" readonly>${whRequest.remarksApprover}</textarea>
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

                var element = $('#status');
                if (element.val() === "Approved") {
                    $("#remarksDiv2").hide();
                    $("#statusDiv").show();
                    $("#submit").attr("disabled", true);
                    $("#status").attr("readonly", true);
                    $("#remarksApprover").attr("readonly", true);
                    $("#statusDiv").show();
                    $("#approvalRemarksDiv").show();
                    $(".js-example-basic-single").prop("readonly", true);
                    $("#pcbCtrQty").attr("readonly", true);
                    $("#pcbAQty").attr("readonly", true);
                    $("#pcbBQty").attr("readonly", true);
                    $("#pcbCQty").attr("readonly", true);
                    $("#remarks").attr("readonly", true);

//                } else if (element.val() === "Waiting for Approval") { //original 3/11/16
                } else if (element.val() === "Pending Approval") { //as requested 2/11/16
                    $("#statusDiv").hide();
                    $("#approvalRemarksDiv").hide();
                    $("#status").attr("readonly", true);
                    $("#remarksApprover").attr("readonly", true);
                } else if (element.val() === "Not Approved") {
                    $("#remarksDiv2").hide();
                    $("#statusDiv").show();
                    $("#approvalRemarksDiv").show();
                    $(".js-example-basic-single").prop("readonly", true);
                    $("#pcbCtrQty").attr("readonly", true);
                    $("#pcbAQty").attr("readonly", true);
                    $("#pcbBQty").attr("readonly", true);
                    $("#pcbCQty").attr("readonly", true);
                    $("#status").attr("readonly", true);
                    $("#remarksApprover").attr("readonly", true);
                    $("#submit").attr("disabled", true);
                    $("#remarks").attr("readonly", true);
                } else {
                    $("#statusDiv").hide();
                    $("#approvalRemarksDiv").hide();
                    $("#status").attr("readonly", true);
                    $("#remarksApprover").attr("readonly", true);

                }


                $('#pcbType').val("<c:out value="${whRequest.pcbType}"/>");
                $('#equipmentIdMb').val("<c:out value="${whRequest.equipmentId}"/>");
                $('#equipmentIdTray').val("<c:out value="${whRequest.equipmentId}"/>");
                $('#equipmentIdStencil').val("<c:out value="${whRequest.equipmentId}"/>");
                $('#equipmentIdpcbA').val("<c:out value="${whRequest.pcbA}"/>");
                $('#equipmentIdpcbB').val("<c:out value="${whRequest.pcbB}"/>");
                $('#equipmentIdpcbC').val("<c:out value="${whRequest.pcbC}"/>");
                $('#equipmentIdpcbCtr').val("<c:out value="${whRequest.pcbCtr}"/>");

                var trayId = $('#equipmentIdTray');
                $('#maxQtyTray').val($('option:selected', trayId).attr('trayquantity'));
                var stencilId = $('#equipmentIdStencil');
                $('#maxQtyStencil').val($('option:selected', stencilId).attr('stencilquantity'));
                var mbId = $('#equipmentIdMb');
                $('#maxQtyMb').val($('option:selected', mbId).attr('mbquantity'));
                var pcbA = $('#equipmentIdpcbA');
                $('#maxQtyA').val($('option:selected', pcbA).attr('pcbAquantity'));
                if (pcbA.val() === "") {
                    $('#maxQtyA1').val("0");
                } else {
                    $('#maxQtyA1').val($('option:selected', pcbA).attr('pcbAquantity'));
                }
                var pcbB = $('#equipmentIdpcbB');
                $('#maxQtyB').val($('option:selected', pcbB).attr('pcbBquantity'));
                if (pcbB.val() === "") {
                    $('#maxQtyB1').val("0");
                } else {
                    $('#maxQtyB1').val($('option:selected', pcbB).attr('pcbBquantity'));
                }
                var pcbC = $('#equipmentIdpcbC');
                $('#maxQtyC').val($('option:selected', pcbC).attr('pcbCquantity'));
                if (pcbC.val() === "") {
                    $('#maxQtyC1').val("0");
                } else {
                    $('#maxQtyC1').val($('option:selected', pcbC).attr('pcbCquantity'));
                }
                var pcbCtr = $('#equipmentIdpcbCtr');
                $('#maxQtyCtr').val($('option:selected', pcbCtr).attr('pcbCtrquantity'));
                if (pcbCtr.val() === "") {
                    $('#maxQtyCtr1').val("0");
                } else {
                    $('#maxQtyCtr1').val($('option:selected', pcbCtr).attr('pcbCtrquantity'));
                }


                $("form").keyup(function () {
//                    if ($('#equipmentType').val() === "PCB" && $("#quantitytest").val() === "0" && $('#status').val() === "Waiting for Approval") { //original 3/11/16
                    if ($('#equipmentType').val() === "PCB" && $("#quantitytest").val() === "0" && $('#status').val() === "Pending Approval Approval") { //as requested 2/11/16
                        $("#submit").attr("disabled", true);
                    } else if ($('#status').val() === "Approved" || $('#status').val() === "Not Approved") {
                        $("#submit").attr("disabled", true);
                    } else {
                        $("#submit").removeAttr('disabled');
                    }

                });

                $("form").mouseup(function () {
//                    if ($('#equipmentType').val() === "PCB" && $("#quantitytest").val() === "0" && $('#status').val() === "Waiting for Approval") { //original 3/11/16
                    if ($('#equipmentType').val() === "PCB" && $("#quantitytest").val() === "0" && $('#status').val() === "Pending Approval") { //as requested 2/11/16
                        $("#submit").attr("disabled", true);
                    } else if ($('#status').val() === "Approved" || $('#status').val() === "Not Approved") {
                        $("#submit").attr("disabled", true);
                    } else {
                        $("#submit").removeAttr('disabled');
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
                    if ($(this).val() === "") {
                        $('#maxQtyCtr1').val("0");
                    }
                });
                $('#equipmentIdpcbA').change(function () {
                    $('#maxQtyA').val($('option:selected', this).attr('pcbAquantity'));
                    $('#maxQtyA1').val($('option:selected', this).attr('pcbAquantity'));
                    if ($(this).val() === "") {
                        $('#maxQtyA1').val("0");
                    }
                });
                $('#equipmentIdpcbB').change(function () {
                    $('#maxQtyB').val($('option:selected', this).attr('pcbBquantity'));
                    $('#maxQtyB1').val($('option:selected', this).attr('pcbBquantity'));
                    if ($(this).val() === "") {
                        $('#maxQtyB1').val("0");
                    }
                });
                $('#equipmentIdpcbC').change(function () {
                    $('#maxQtyC').val($('option:selected', this).attr('pcbCquantity'));
                    $('#maxQtyC1').val($('option:selected', this).attr('pcbCquantity'));
                    if ($(this).val() === "") {
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

                $(".js-example-basic-single").select2({
                    placeholder: "Choose one",
                    allowClear: true
                });
                var validator = $("#edit_hardwarequest_form").validate({
                    rules: {
                        equipmentId: {
                            required: true
                        },
                        equipmentIdMb: {
                            required: true
                        },
                        equipmentIdStencil: {
                            required: true
                        },
                        equipmentIdTray: {
                            required: true
                        },
                        remarks: {
                            required: true
                        },
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

                var element = $('#equipmentType');
                if (element.val() === "Motherboard") {
                    $("#mblistdiv").show();
                    $("#pcbTypeDiv").hide();
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
                } else if (element.val() === "Stencil") {
                    $("#stencillistdiv").show();
                    $("#pcbTypeDiv").hide();
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
                } else if (element.val() === "Tray") {
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
                } else if (element.val() === "PCB") {
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
                } else {
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
                }
            });
        </script>
    </s:layout-component>
</s:layout-render>