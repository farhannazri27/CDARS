<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
        <link rel="stylesheet" href="${contextPath}/resources/private/css/libs/datepicker.css" type="text/css" />
    </s:layout-component>
    <s:layout-component name="page_css_inline">
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>Shipping</h1>
            <div class="row">
                <div class="col-lg-6">
                    <div class="main-box">
                        <h2>Details</h2>
                        <form id="detail_form" class="form-horizontal" role="form">
                            <div class="form-group">
                                <label for=" hardwareType" class="col-lg-4 control-label">Hardware Type </label>
                                <div class="col-lg-5">
                                    <input type="text" class="form-control" id="hardwareType" name="hardwareType" value="${whShipping.requestEquipmentType}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for=" hardwareId" class="col-lg-4 control-label">${IdLabel} </label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="hardwareId" name="hardwareId" value="${whShipping.requestEquipmentId}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for=" quantity" class="col-lg-4 control-label">Quantity </label>
                                <div class="col-lg-4">
                                    <input type="text" class="form-control" id="quantity" name="quantity" value="${whShipping.requestQuantity}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="requestedBy" class="col-lg-4 control-label">Requested By </label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="requestedBy" name="requestedBy" value="${whShipping.requestRequestedBy}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="requestedDate" class="col-lg-4 control-label">Requested Date </label>
                                <div class="col-lg-4">
                                    <input type="text" class="form-control" id="requestedDate" name="requestedDate" value="${whShipping.requestRequestedDate}" readonly>
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
                                    <input type="hidden" name="id" value="${mpActiveTab}" />
                                    <div class="form-group">
                                        <label for=" mpNo" class="col-lg-4 control-label">Material Pass Number *</label>
                                        <div class="col-lg-5">
                                            <input type="text" class="form-control" id="mpNo" name="mpNo" autofocus="autofocus" value="${whShipping.mpNo}">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="mpExpiryDate" class="col-lg-4 control-label">Material Pass Expiry Date *</label>
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
                                        <button type="submit" class="btn btn-primary">Save & Print Trip Ticket</button>
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
                                    <input type="hidden" name="id" value="${ttActiveTab}" />
                                    <div class="form-group">
                                        <label for=" hardwareBarcode1" class="col-lg-4 control-label">${IdLabel} *</label>
                                        <div class="col-lg-5">
                                            <input type="text" class="form-control" id="hardwareBarcode1" name="hardwareBarcode1" autofocus="autofocus" placeholder="Please scan trip ticket" value="${whShipping.hardwareBarcode1}">
                                        </div>
                                    </div>
                                    <div class="pull-right">
                                        <button type="reset" class="btn btn-secondary cancel1">Reset</button>
                                        <button type="submit" class="btn btn-primary">Save</button>
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
                                    <input type="hidden" name="id" value="${bsActiveTab}" />
                                    <div class="form-group">
                                        <label for=" hardwareBarcode2" class="col-lg-4 control-label">${IdLabel} *</label>
                                        <div class="col-lg-5">
                                            <input type="text" class="form-control" id="hardwareBarcode2" name="hardwareBarcode2" autofocus="autofocus" placeholder="Please scan barcode sticker" value="${whShipping.hardwareBarcode2}">
                                        </div>
                                    </div>
                                    <div class="form-group" hidden>
                                        <label for=" hardwareBarcode1" class="col-lg-4 control-label">${IdLabel} *</label>
                                        <div class="col-lg-8">
                                            <input type="text" class="form-control" id="hardwareBarcode1" name="hardwareBarcode1" placeholder="Please scan trip ticket" value="${whShipping.hardwareBarcode1}">
                                        </div>
                                    </div>
                                    <div class="pull-right">
                                        <button type="reset" class="btn btn-secondary cancel2">Reset</button>
                                        <button type="submit" class="btn btn-primary">Save</button>
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

                $('#mpExpiryDate').datepicker({
                    format: 'yyyy-mm-dd',
                    autoclose: true
                });

                var validator = $("#mp_form").validate({
                    rules: {
                        mpNo: {
                            required: true
                        },
                        mpExpiryDate: {
                            required: true
                        }
                    }
                });

                var validator1 = $("#tt_form").validate({
                    rules: {
                        hardwareBarcode1: {
                            required: true
                        }
                    }
                });

                var validator2 = $("#bs_form").validate({
                    rules: {
                        hardwareBarcode2: {
                            required: true
                        }
                    }
                });

                $(".cancel").click(function () {
                    validator.resetForm();
                });
                $(".cancel1").click(function () {
                    validator1.resetForm();
                });
                $(".cancel1").click(function () {
                    validator2.resetForm();
                });
            });
            
            if($('#hardwareBarcode2').val() !== $('#hardwareBarcode1').val()){
                alert("Barcode Sticker not matched with Barcode Trip Ticket!!");
            }
            
            

//            $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
//                var target = e.target.attributes.href.value;
//                $(target + ' input').focus();
//            })

        </script>
    </s:layout-component>
</s:layout-render>