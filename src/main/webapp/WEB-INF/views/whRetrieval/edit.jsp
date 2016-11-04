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
            <h1>HW Verification for Retrieval from SBN Factory</h1>
            <div class="row">
                <div class="col-lg-6">
                    <div class="main-box">
                        <h2>Details</h2>
                        <form id="detail_form" class="form-horizontal" role="form">
                            <div class="form-group">
                                <input type="hidden" name="groupId" id="groupId" value="${groupId}" />
                                <label for=" hardwareType" class="col-lg-4 control-label">Hardware Type </label>
                                <div class="col-lg-5">
                                    <input type="text" class="form-control" id="hardwareType" name="hardwareType" value="${whRetrieval.hardwareType}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for=" hardwareId" class="col-lg-4 control-label">${IdLabel} </label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="hardwareId" name="hardwareId" value="${whRetrieval.hardwareId}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for=" quantity" class="col-lg-4 control-label">Quantity </label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="quantity" name="quantity" value="${whRetrieval.hardwareQty}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for=" retrievalReason" class="col-lg-4 control-label">Reason for Retrieval </label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="retrievalReason" name="retrievalReason" value="${whRetrieval.retrievalReason}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="mpNo" class="col-lg-4 control-label">Material Pass No. </label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="requestedBy" name="mpNo" value="${whRetrieval.mpNo}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="mpExpiryDate" class="col-lg-4 control-label">Material Pass Expiry Date </label>
                                <div class="col-lg-4">
                                    <input type="text" class="form-control" id="requestedDate" name="mpExpiryDate" value="${whRetrieval.viewMpExpiryDate}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="rack" class="col-lg-4 control-label">Rack </label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="rack" name="rack" value="${whRetrieval.rack}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="shelf" class="col-lg-4 control-label">Shelf </label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="shelf" name="shelf" value="${whRetrieval.shelf}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="requestedBy" class="col-lg-4 control-label">Requested By </label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="requestedDate" name="requestedBy" value="${whRetrieval.requestedBy}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="requestedDate" class="col-lg-4 control-label">Requested Date </label>
                                <div class="col-lg-5">
                                    <input type="text" class="form-control" id="requestedDate" name="requestedDate" value="${whRetrieval.viewRequestedDate}" readonly>
                                </div>
                            </div>
                            <div class="clearfix">           
                                <a href="${contextPath}/wh/whRetrieval" class="btn btn-info pull-left" id="backBtn"><i class="fa fa-reply"></i> Back</a>
                                <a href="${contextPath}/wh/whRetrieval" class="btn btn-primary pull-right finishBtn" id="finishBtn">Finish <i class="fa fa-arrow-right"></i></a>
                                <div class="clearfix"></div>
                            </div>
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
                    <li class="${bsActive}"><a data-toggle="tab" href="#bs">Scan Barcode Sticker </a></li>
                    <li class="${ttActive}"><a data-toggle="tab" href="#tt">Scan Trip Ticket</a></li>
                    <li class="${disActive}" id="disTab"><a data-toggle="tab" href="#dis">Supervisor Verification</a></li>
                </ul>
                <div class="tab-content">

                    <!--tab for scan barcode sticker-->

                    <div id="bs" class="tab-pane fade ${bsActiveTab}">
                        <!--<br>-->
                        <h6></h6>
                        <div class="col-lg-7">
                            <div class="main-box">
                                <h2>Scan Barcode Sticker</h2>
                                <form id="bs_form" class="form-horizontal" role="form" action="${contextPath}/wh/whRetrieval/updateScanBs" method="post">
                                    <input type="hidden" name="id" id="id" value="${whRetrieval.id}" />
                                    <input type="hidden" name="status" id="statusBs" value="${whRetrieval.status}" />
                                    <input type="hidden" name="tab" value="${bsActiveTab}" />
                                    <input type="hidden" id="mpNoV" name="mpNoV" value="${whRetrieval.mpNo}">
                                    <div class="form-group">
                                        <label for=" barcodeVerification" class="col-lg-4 control-label">Barcode Sticker(MP No) *</label>
                                        <div class="col-lg-5">
                                            <input type="text" class="form-control" id="barcodeVerification" name="barcodeVerification" autofocus="autofocus" placeholder="Please scan barcode sticker" value="${whRetrieval.barcodeVerification}">   
                                            <small id="noteBs" class="form-text text-muted">Verified by ${whRetrieval.barcodeDispositionBy}.</small>
                                            <small id="noteBsEmail" class="form-text text-muted">Email has been sent to supervisor.</small>
                                        </div>
                                    </div>
                                    <!--<div id = "alert_placeholder"></div>-->
                                    <div class="filter-block pull-left" id="sendEmailBs" hidden>
                                        <a href="${contextPath}/wh/whRetrieval/emailWrongBs/${whRetrieval.id}" class="btn btn-danger pull-right">
                                            <i class="fa fa-envelope fa-lg"></i> Send Email to Supervisor
                                        </a>
                                    </div>
                                    <div class="pull-right">
                                        <button type="reset" class="btn btn-secondary cancel2">Reset</button>
                                        <button type="submit" id="submit2" name="submit2" class="btn btn-primary">Verify</button>
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
                                <form id="tt_form" class="form-horizontal" role="form" action="${contextPath}/wh/whRetrieval/updateScanTt" method="post">
                                    <input type="hidden" name="id" id="id" value="${whRetrieval.id}" />
                                    <input type="hidden" name="status" id="statusTt" value="${whRetrieval.status}" />
                                    <input type="hidden" name="tab" value="${ttActiveTab}" />
                                    <div class="form-group">
                                        <label for=" ttVerification" class="col-lg-4 control-label">${IdLabel} *</label>
                                        <div class="col-lg-5">
                                            <input type="text" class="form-control" id="ttVerification" name="ttVerification" autofocus="autofocus" placeholder="Please scan trip ticket" value="${whRetrieval.ttVerification}">
                                            <small id="noteTt" class="form-text text-muted">Verified by ${whRetrieval.ttDispositionBy}.</small>
                                            <small id="noteTtEmail" class="form-text text-muted">Email has been sent to supervisor.</small>
                                        </div>
                                    </div>
                                    <input type="hidden" id="hardwareIdV" name="hardwareIdV" value="${whRetrieval.hardwareId}">
                                    <input type="hidden" id="barcodeVe" name="barcodeVe" value="${whRetrieval.barcodeVerification}">
                                    <div class="filter-block pull-left" id="sendEmailTt" hidden>
                                        <a href="${contextPath}/wh/whRetrieval/emailWrongTt/${whRetrieval.id}" class="btn btn-danger pull-right">
                                            <i class="fa fa-envelope fa-lg"></i> Send Email to Supervisor
                                        </a>
                                    </div>
                                    <div class="pull-right">
                                        <button type="reset" class="btn btn-secondary cancel1">Reset</button>
                                        <button type="submit" id="submit1" name="submit1" class="btn btn-primary">Verify</button>
                                    </div>
                                    <div class="clearfix"></div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <!--tab for supervisor verification-->

                    <div id="dis" class="tab-pane fade ${disActiveTab}">
                        <!--<br>-->
                        <h6></h6>
                        <div class="col-lg-7">
                            <div class="main-box">
                                <h2>Supervisor Verification</h2>
                                <form id="dis_form" class="form-horizontal" role="form" action="${contextPath}/wh/whRetrieval/updateBarcodeAndTtDisposition" method="post">
                                    <input type="hidden" name="id" id="id" value="${whRetrieval.id}" />
                                    <input type="hidden" name="statusforDisposition" id="statusforDisposition" value="${whRetrieval.status}" />
                                    <input type="hidden" name="requestId" id="requestId" value="${whRetrieval.requestId}" />
                                    <input type="hidden" name="tab" value="${disActiveTab}" />
                                    <div class="form-group" id="barcodeDispositionDiv">
                                        <input type="hidden" class="form-control" id="mpNoBySupervisor" name="mpNoBySupervisor" value="${whRetrieval.mpNo}">
                                        <label for=" barcodeDisposition" class="col-lg-3 control-label">Barcode Sticker Disposition *</label>
                                        <div class="col-lg-5">
                                            <select id="barcodeDisposition" name="barcodeDisposition" class="form-control">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${SupervisorDispositionBarcode}" var="group">
                                                    <option value="${group.name}" ${group.selected}>${group.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group" id="remarksBarcodediv">
                                        <label for="barcodeDispositionRemarks" class="col-lg-3 control-label">Remarks </label>
                                        <div class="col-lg-7">
                                            <textarea class="form-control" rows="5" id="barcodeDispositionRemarks" name="barcodeDispositionRemarks">${whRetrieval.barcodeDispositionRemarks}</textarea>
                                        </div>
                                    </div>
                                    <br>
                                    <div class="form-group" id="ttDispositionDiv">
                                        <input type="hidden" class="form-control" id="hardwareIdBySupervisor" name="hardwareIdBySupervisor" value="${whRetrieval.hardwareId}">
                                        <label for=" ttDisposition" class="col-lg-3 control-label">Trip Ticket Disposition *</label>
                                        <div class="col-lg-5">
                                            <select id="ttDisposition" name="ttDisposition" class="form-control">
                                                <option value="" selected=""></option>
                                                <c:forEach items="${SupervisorDispositionTt}" var="group">
                                                    <option value="${group.name}" ${group.selected}>${group.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group" id="remarksTtdiv">
                                        <label for="ttDispositionRemarks" class="col-lg-3 control-label">Remarks </label>
                                        <div class="col-lg-7">
                                            <textarea class="form-control" rows="5" id="ttDispositionRemarks" name="ttDispositionRemarks">${whRetrieval.ttDispositionRemarks}</textarea>
                                        </div>
                                    </div>
                                    <input type="hidden" id="mpNoBySupervisor" name="hardwareIdV" value="${whRetrieval.mpNo}">
                                    <input type="hidden" id="hardwareIdBySupervisor" name="barcodeVe" value="${whRetrieval.hardwareId}">
                                    <div class="pull-right">
                                        <button type="reset" class="btn btn-secondary cancel3">Reset</button>
                                        <button type="submit" id="submitdispose" name="submitdispose" class="btn btn-primary">Verify</button>
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

//                if ($("#barcodeDisposition").val() === "") {
//                    $("#disTab").addClass("disabled", true);
//                    $('a[data-toggle="tab"]').on('click', function () {
//                        if ($(this).parent('li').hasClass('disabled')) {
//                            return false;
//                        }
//                        ;
//                    });
//                }

                var statusF = $('#statusTt'); //for finish button
                if (statusF.val() === "Closed" || statusF.val() === "Closed. Verified By Supervisor") {
                    $(".finishBtn").show();
                    $("#backBtn").hide();
                } else {
                    $("#finishBtn").hide();
                    $("#backBtn").show();
                }


                $('#barcodeVerification').bind('copy paste cut', function (e) {
                    e.preventDefault(); //this line will help us to disable cut,copy,paste		
                });

                $('#ttVerification').bind('copy paste cut', function (e) {
                    e.preventDefault(); //this line will help us to disable cut,copy,paste		
                });

                $('#barcodeVerification').change(function () {
                    if ($('#barcodeVerification').val() !== "" && $('#barcodeVerification').val() !== $('#mpNoV').val()) {
                        $("#sendEmailBs").show();
                    } else {
                        $("#sendEmailBs").hide();
                    }
                });

                $('#submit2').click(function () {
                    if ($('#barcodeVerification').val() !== "" && $('#barcodeVerification').val() !== $('#mpNoV').val()) {
                        $("#sendEmailBs").show();
                    } else {
                        $("#sendEmailBs").hide();
                    }
                });

                $('#ttVerification').change(function () {
                    if ($('#ttVerification').val() !== "" && $('#ttVerification').val() !== $('#hardwareIdV').val()) {
                        $("#sendEmailTt").show();
                    } else {
                        $("#sendEmailTt").hide();
                    }
                });

                $('#submit1').click(function () {
                    if ($('#ttVerification').val() !== "" && $('#ttVerification').val() !== $('#hardwareIdV').val()) {
                        $("#sendEmailTt").show();
                    } else {
                        $("#sendEmailTt").hide();
                    }
                });

                var egroup = $('#groupId');
                if (egroup.val() !== "1" && egroup.val() !== "2" && egroup.val() !== "25" && egroup.val() !== "29") {
                    $("#submitdispose").attr("disabled", true);
                    $('#ttDisposition').attr("disabled", true);
                    $("#barcodeDisposition").attr("disabled", true);
                    $("#ttDispositionRemarks").attr("readonly", true);
                    $("#barcodeDispositionRemarks").attr("readonly", true);
                }

                var ebs = $('#statusBs');
                if (ebs.val() === "Barcode Sticker Scanning Mismatched") {
                    $("#noteBsEmail").show();
                } else {
                    $("#noteBsEmail").hide();
                }

                var ett = $('#statusTt');
                if (ett.val() === "Trip Ticket Scanning Mismatched") {
                    $("#noteTtEmail").show();
                } else {
                    $("#noteTtEmail").hide();
                }

                var ebsDis = $('#barcodeDisposition');
                if (ebsDis.val() === "Verified") { //original 
                    $("#noteBs").show();
                } else {
                    $("#noteBs").hide();
                }

                var ettDis = $('#ttDisposition');
                if (ettDis.val() === "Verified") {
                    $("#noteTt").show();
                } else {
                    $("#noteTt").hide();
                }

                var estatus = $('#statusforDisposition');
//                if (estatus.val() === "Barcode Sticker Scanning Mismatched" || estatus.val() === "Barcode Sticker - Unverified By Supervisor" || estatus.val() === "Barcode Sticker - Hold By Supervisor") { //original 3/11/16
                if (estatus.val() === "Barcode Sticker Scanning Mismatched" || estatus.val() === "Barcode Sticker - Not Verified By Supervisor" || estatus.val() === "Barcode Sticker - Hold By Supervisor") { //as requested 2/11/16
                    $('#ttDisposition').attr("disabled", true);
                    $("#barcodeDisposition").focus();
                    $("#ttDispositionRemarks").attr("readonly", true);
                    $("#ttDisposition").attr("required", false);
                    $("#ttDispositionRemarks").attr("required", false);
//                } else if (estatus.val() === "Trip Ticket Scanning Mismatched" || estatus.val() === "Trip Ticket - Unverified By Supervisor" || estatus.val() === "Trip Ticket - Hold By Supervisor") { //original 3/11/16
                } else if (estatus.val() === "Trip Ticket Scanning Mismatched" || estatus.val() === "Trip Ticket - Not Verified By Supervisor" || estatus.val() === "Trip Ticket - Hold By Supervisor") { //as requested 2/11/16
                    $("#ttDisposition").focus();
                    $("#barcodeDisposition").attr("disabled", true);
                    $("#barcodeDispositionRemarks").attr("readonly", true);
                    $("#barcodeDisposition").attr("required", false);
                    $("#barcodeDispositionRemarks").attr("required", false);
                } else {
                    $('#ttDisposition').attr("disabled", true);
                    $("#barcodeDisposition").attr("disabled", true);
                    $("#ttDispositionRemarks").attr("readonly", true);
                    $("#barcodeDispositionRemarks").attr("readonly", true);
                    $("#submitdispose").attr("disabled", true);
                }

                var element1 = $('#barcodeVerification');
                var element2 = $('#mpNoV');
                if (element1.val() === element2.val()) {
                    $("#submit2").attr("disabled", true);
                    $("#barcodeVerification").attr("readonly", true);
                }

                var element3 = $('#barcodeVe');
                var element4 = $('#ttVerification');
                var element5 = $('#hardwareIdV');
                if (element3.val() === "") {
                    $("#submit1").attr("disabled", true);
                    $("#ttVerification").attr("readonly", true);
                }
                if (element4.val() === element5.val()) {
                    $("#submit1").attr("disabled", true);
                    $("#ttVerification").attr("readonly", true);
                }

                jQuery.extend(jQuery.validator.messages, {
                    required: "This field is required.",
                    equalTo: "Value is not match! Please re-scan.",
                    email: "Please enter a valid email.",
                });

//                var idVerify = $('#hardwareBarcode1').val();
//                var idOri = $('#hardwareIdV').val();
//                if (idVerify !== idOri) {
//                    $('#hardwareBarcode1').val("");
//                }
//
//                bootstrap_alert = function () {}
//                bootstrap_alert.warning = function (message) {
//                    $('#alert_placeholder').html('<div class="alert alert-danger alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><span>' + message + '</span></div>')
//                }
//                if ($('#hardwareBarcode2').val() !== "" && $('#hardwareBarcode2').val() !== $('#mpNoV').val()) {
//                    bootstrap_alert.warning('Barcode Sticker NOT MATCH with Trip Ticket! Please re-check. Email has been send to Requestor.');
//                    //                    $("#hardwareBarcode2").effect("highlight", {}, 1000);
//                    $("#hardwareBarcode2").addClass('highlight');
//                }

                var validator1 = $("#tt_form").validate({
                    rules: {
                        ttVerification: {
                            required: true,
                            equalTo: "#hardwareIdV"
                        }
                    }
                });

                var validator2 = $("#bs_form").validate({
                    rules: {
                        barcodeVerification: {
                            required: true,
                            equalTo: "#mpNoV"
                        }
                    }
                });

                var validator3 = $("#dis_form").validate({
                    rules: {
                        barcodeDisposition: {
                            required: true
                        },
                        barcodeDispositionRemarks: {
                            required: true
                        },
                        ttDisposition: {
                            required: true
                        },
                        ttDispositionRemarks: {
                            required: true
                        }
                    }
                });

                $(".cancel1").click(function () {
                    validator1.resetForm();
                });
                $(".cancel2").click(function () {
                    validator2.resetForm();
                });
                $(".cancel3").click(function () {
                    validator3.resetForm();
                });
            });

        </script>
    </s:layout-component>
</s:layout-render>