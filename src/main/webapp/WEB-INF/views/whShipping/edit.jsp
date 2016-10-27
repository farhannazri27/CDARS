<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />
    </s:layout-component>
    <s:layout-component name="page_css_inline">
    </s:layout-component>
    <s:layout-component name="page_container">
        <style>
            .highlight {
                border-color: red;
                box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset, 0 0 8px rgba(126, 239, 104, 0.6);
                outline: 0 none;
            }
        </style>
        <div class="col-lg-12">
            <h1>Shipping</h1>
            <div class="row">
                <div class="col-lg-6">
                    <div class="main-box">
                        <h2>Details</h2>
                        <form id="detail_form" class="form-horizontal" role="form">
                            <div class="form-group">
                                <label for=" hardwareType" class="col-lg-3 control-label">Hardware Type </label>
                                <div class="col-lg-5">
                                    <input type="text" class="form-control" id="hardwareType" name="hardwareType" value="${whShipping.requestEquipmentType}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for=" hardwareId" class="col-lg-3 control-label">${IdLabel} </label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="hardwareId" name="hardwareId" value="${whShipping.requestEquipmentId}" readonly>
                                </div>
                            </div>
                            <div class="form-group" id="qualAnBDiv" hidden>
                                <label for=" quantity" class="col-lg-3 control-label">Quantity Qual A </label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="quantity" name="quantity" value="${whShipping.pcbAQty}" readonly>
                                </div>
                                <label for=" quantity" class="col-lg-2 control-label">Quantity Qual B </label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="quantity" name="quantity" value="${whShipping.pcbBQty}" readonly>
                                </div>
                            </div>
                            <div class="form-group" id="qualCnCtrDiv" hidden>
                                <label for=" quantity" class="col-lg-3 control-label">Quantity Qual C </label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="quantity" name="quantity" value="${whShipping.pcbCQty}" readonly>
                                </div>
                                <label for=" quantity" class="col-lg-2 control-label">Quantity CONTROL </label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="quantity" name="quantity" value="${whShipping.pcbCtrQty}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for=" quantity" class="col-lg-3 control-label">Total Quantity </label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="quantity" name="quantity" value="${whShipping.requestQuantity}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="requestedBy" class="col-lg-3 control-label">Requested By </label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="requestedBy" name="requestedBy" value="${whShipping.requestRequestedBy}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="requestedDate" class="col-lg-3 control-label">Requested Date </label>
                                <div class="col-lg-4">
                                    <input type="text" class="form-control" id="requestedDate" name="requestedDate" value="${whShipping.requestViewRequestedDate}" readonly>
                                </div>
                            </div>
                            <a href="${contextPath}/wh/whShipping" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                            <div class="clearfix"></div>
                        </form>
                    </div>
                </div>	
            </div>
        </div>
        <hr class="separator">
        <div class="col-lg-12">
            <br>
            <div class="row">
                <ul class="nav nav-tabs">
                    <li class="${mpActive}"><a data-toggle="tab" href="#mp">Material Pass</a></li>
                    <li class="${ttActive}"><a data-toggle="tab" href="#tt">Scan Trip Ticket</a></li>
                    <li class="${bsActive}"><a data-toggle="tab" href="#bs">Scan Barcode Sticker </a></li>
                </ul>
                <div class="tab-content">

                    <!--Tab for Material Pass-->
                    <div id="mp" class="tab-pane fade ${mpActiveTab}">

                        <h6></h6>
                        <div class="col-lg-7">
                            <div class="main-box">
                                <h2>Material Pass</h2>
                                <form id="mp_form" class="form-horizontal" role="form" action="${contextPath}/wh/whShipping/updateMp" method="post">
                                    <input type="hidden" name="id" value="${whShipping.id}" />
                                    <input type="hidden" name="status" value="${whShipping.status}" />
                                    <input type="hidden" name="tab" value="${mpActiveTab}" />
                                    <div class="form-group">
                                        <label for=" mpNo" class="col-lg-3 control-label">Material Pass Number *</label>
                                        <div class="col-lg-5">
                                            <input type="text" class="form-control" id="mpNo" name="mpNo" autofocus="autofocus" value="${whShipping.mpNo}">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="mpExpiryDate" class="col-lg-3 control-label">Material Pass Expiry Date *</label>
                                        <div class="col-lg-5">
                                            <div class="input-group">
                                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                <input type="text" name="mpExpiryDate" class="form-control" id="mpExpiryDate" value="${whShipping.mpExpiryDate}">
                                            </div>
                                            <label id="datepickerDate-error" class="error" for="mpExpiryDate" style="display: none;"></label>
                                        </div>
                                    </div>
                                    <div class="pull-right">
                                        <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                        <button type="submit" id="submit" name="submit" class="btn btn-primary">Save & View Trip Ticket</button>
                                    </div>
                                    <div class="clearfix"></div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <!--tab for scan trip ticket-->

                    <div id="tt" class="tab-pane fade ${ttActiveTab}">
                        <!--<br>-->
                        <h6></h6>
                        <div class="col-lg-7">
                            <div class="main-box">
                                <h2>Scan Trip Ticket</h2>
                                <form id="tt_form" class="form-horizontal" role="form" action="${contextPath}/wh/whShipping/updateScanTt" method="post">
                                    <input type="hidden" name="id" value="${whShipping.id}" />
                                    <input type="hidden" name="status" value="${whShipping.status}" />
                                    <input type="hidden" name="tab" value="${ttActiveTab}" />
                                    <div class="form-group">
                                        <label for=" hardwareBarcode1" class="col-lg-3 control-label">${IdLabel} *</label>
                                        <div class="col-lg-5">
                                            <input type="text" class="form-control" id="hardwareBarcode1" name="hardwareBarcode1" autofocus="autofocus" placeholder="Please scan trip ticket" value="${whShipping.hardwareBarcode1}">
                                        </div>
                                    </div>
                                    <input type="hidden" class="form-control" id="hardwareIdV" name="hardwareIdV" value="${whShipping.requestEquipmentId}">
                                    <input type="hidden" class="form-control" id="hardwareId2" name="hardwareId2" value="${whShipping.hardwareBarcode1}">
                                    <input type="hidden" class="form-control" id="mpNoVe" name="mpNoVe" value="${whShipping.mpNo}">
                                    <div class="pull-right">
                                        <button type="reset" class="btn btn-secondary cancel1">Reset</button>
                                        <button type="submit" id="submit1" name="submit1" class="btn btn-primary">Verify</button>
                                    </div>
                                    <div class="clearfix"></div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <!--tab for scan barcode sticker-->

                    <div id="bs" class="tab-pane fade ${bsActiveTab}">
                        <!--<br>-->
                        <h6></h6>
                        <div class="col-lg-7">
                            <div class="main-box">
                                <h2>Scan Barcode Sticker</h2>
                                <form id="bs_form" class="form-horizontal" role="form" action="${contextPath}/wh/whShipping/updateScanBs" method="post">
                                    <input type="hidden" name="id" value="${whShipping.id}" />
                                    <input type="hidden" name="status" value="${whShipping.status}" />
                                    <input type="hidden" name="tab" value="${bsActiveTab}" />
                                    <div class="form-group">
                                        <label for=" hardwareBarcode2" class="col-lg-3 control-label">Barcode Sticker(MP No) *</label>
                                        <div class="col-lg-5">
                                            <input type="text" class="form-control" id="hardwareBarcode2" name="hardwareBarcode2" autofocus="autofocus" placeholder="Please scan barcode sticker" value="${whShipping.hardwareBarcode2}">                                        
                                        </div>
                                    </div>
                                    <div id = "alert_placeholder"></div>
                                    <input type="hidden" class="form-control" id="mpNoV" name="mpNoV" value="${whShipping.mpNo}">
                                    <input type="hidden" class="form-control" id="mpNo2" name="mpNo2" value="${whShipping.hardwareBarcode2}">
                                    <input type="hidden" class="form-control" id="hardwareIdVe" name="hardwareIdVe" value="${whShipping.hardwareBarcode1}">
                                    <div class="pull-right">
                                        <button type="reset" class="btn btn-secondary cancel2">Reset</button>
                                        <button type="submit" id="submit2" name="submit2" class="btn btn-primary">Verify</button>
                                    </div>
                                    <div class="clearfix"></div>

                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
        <script src="${contextPath}/resources/validation/jquery.validate.min.js"></script>
        <script src="${contextPath}/resources/validation/additional-methods.js"></script>
        <script src="${contextPath}/resources/private/js/bootstrap-datepicker.js"></script>
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {

                $('#hardwareId').bind('copy paste cut', function (e) {
                    e.preventDefault(); //this line will help us to disable cut,copy,paste		
                });
                
//                $('#mpNo').bind('copy paste cut', function (e) {
//                    e.preventDefault();		
//                });
                
                $('#hardwareBarcode1').bind('copy paste cut', function (e) {
                    e.preventDefault(); 	
                });
                
                $('#hardwareBarcode2').bind('copy paste cut', function (e) {
                    e.preventDefault(); 		
                });


                var elementhardwareType = $('#hardwareType');
                if (elementhardwareType.val() === "PCB") {
                    $("#qualAnBDiv").show();
                    $("#qualCnCtrDiv").show();
                } else {
                    $("#qualAnBDiv").hide();
                    $("#qualCnCtrDiv").hide();
                }

                var element = $('#mpNo');
                var element1 = $('#mpNoVe');
                var element2 = $('#hardwareIdVe');
                var element3 = $('#hardwareIdV')
                var element4 = $('#hardwareBarcode1');
                var element5 = $('#mpNo2')
                var element6 = $('#hardwareBarcode2');

                if (element.val() !== "" && element.val() === element5.val()) {
                    $("#submit").attr("disabled", true);
                    $("#mpNo").attr("readonly", true);
                    $("#mpExpiryDate").attr("readonly", true);
                }

                if (element1.val() === "") {
                    $("#submit1").attr("disabled", true);
                    $("#hardwareBarcode1").attr("readonly", true);
                }

                if (element3.val() === element4.val()) {
                    $("#submit1").attr("disabled", true);
                    $("#hardwareBarcode1").attr("readonly", true);
                }

                if (element2.val() === "") {
                    $("#submit2").attr("disabled", true);
                    $("#hardwareBarcode2").attr("readonly", true);
                }

                if (element6.val() === element1.val()) {
                    $("#submit2").attr("disabled", true);
                    $("#hardwareBarcode2").attr("readonly", true);
                }


                jQuery.extend(jQuery.validator.messages, {
                    required: "This field is required.",
                    equalTo: "Value is not match! Please re-scan.",
                    email: "Please enter a valid email.",
                });

                var idVerify = $('#hardwareBarcode1').val();
                var idOri = $('#hardwareIdV').val();
                if (idVerify !== idOri) {
                    $('#hardwareBarcode1').val("");
                }

                bootstrap_alert = function () {}
                bootstrap_alert.warning = function (message) {
                    $('#alert_placeholder').html('<div class="alert alert-danger alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><span>' + message + '</span></div>')
                }
                if ($('#hardwareBarcode2').val() !== "" && $('#hardwareBarcode2').val() !== $('#mpNoV').val()) {
                    bootstrap_alert.warning('Barcode Sticker NOT MATCH with Trip Ticket! Please re-check. Email has been send to Requestor.');
                    //                    $("#hardwareBarcode2").effect("highlight", {}, 1000);
                    $("#hardwareBarcode2").addClass('highlight');
                }

                $('#mpExpiryDate').datepicker({
                    format: 'yyyy-mm-dd',
                    autoclose: true
                });

                var validator = $("#mp_form").validate({
                    rules: {
                        mpNo: {
                            required: true,
                            minlength: 17,
                            maxlength: 17
                        },
                        mpExpiryDate: {
                            required: true
                        }
                    }
                });

                var validator1 = $("#tt_form").validate({
                    rules: {
                        hardwareBarcode1: {
                            required: true,
                            equalTo: "#hardwareIdV"
                        }
                    }
                });

                var validator2 = $("#bs_form").validate({
                    rules: {
                        hardwareBarcode2: {
                            required: true,
                            equalTo: "#mpNoV"
                        }
                    }
                });

                $(".cancel").click(function () {
                    validator.resetForm();
                });
                $(".cancel1").click(function () {
                    validator1.resetForm();
                });
                $(".cancel2").click(function () {
                    validator2.resetForm();
                });
            });

        </script>
    </s:layout-component>
</s:layout-render>