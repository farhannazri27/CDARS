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
            <h1>Extend Material Pass Expiry Date</h1>
            <div class="row">
                <div class="col-lg-10">
                    <div class="main-box">
                        <h2>Details</h2>
                        <form id="detail_form" class="form-horizontal" role="form">
                            <div class="form-group">
                                <input type="hidden" name="id" id="id" value="${whInventory.id}" />
                                <label for=" hardwareType" class="col-lg-1 control-label">Hardware Type </label>
                                <div class="col-lg-5">
                                    <input type="text" class="form-control" id="hardwareType" name="hardwareType" value="${whInventory.equipmentType}" readonly>
                                </div>
                            </div>
                            <!--start loadcard-->
                            <c:choose>
                                <c:when test="${whInventory.equipmentType == 'Load Card'}">
                                    <div class="form-group">
                                        <label for=" hardwareId" class="col-lg-1 control-label">Pair ID </label>
                                        <div class="col-lg-3">
                                            <input type="text" class="form-control" id="hardwareId" name="hardwareId" value="${whInventory.equipmentId}" readonly>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for=" hardwareId" class="col-lg-1 control-label">Load Card ID </label>
                                        <div class="col-lg-6">
                                            <input type="text" class="form-control" id="hardwareId" name="hardwareId" value="${whInventory.loadCard}" readonly>
                                        </div>
                                        <label for=" quantity" class="col-lg-1 control-label">Quantity </label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="quantity" name="quantity" value="${whInventory.quantity}" readonly>
                                        </div>
                                    </div>
                                </c:when>
                                <c:when test="${whInventory.equipmentType == 'Program Card'}">
                                    <div class="form-group">
                                        <label for=" hardwareId" class="col-lg-1 control-label">Pair ID </label>
                                        <div class="col-lg-3">
                                            <input type="text" class="form-control" id="hardwareId" name="hardwareId" value="${whInventory.equipmentId}" readonly>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for=" hardwareId" class="col-lg-1 control-label">Program Card ID </label>
                                        <div class="col-lg-6">
                                            <input type="text" class="form-control" id="hardwareId" name="hardwareId" value="${whInventory.programCard}" readonly>
                                        </div>
                                        <label for=" quantity" class="col-lg-1 control-label">Quantity </label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="quantity" name="quantity" value="${whInventory.quantity}" readonly>
                                        </div>
                                    </div>
                                </c:when>
                                <c:when test="${whInventory.equipmentType == 'Load Card & Program Card'}">
                                    <div class="form-group">
                                        <label for=" hardwareId" class="col-lg-1 control-label">Pair ID </label>
                                        <div class="col-lg-3">
                                            <input type="text" class="form-control" id="hardwareId" name="hardwareId" value="${whInventory.equipmentId}" readonly>
                                        </div>
                                        <label for=" quantity" class="col-lg-4 control-label">Quantity </label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="quantity" name="quantity" value="${whInventory.quantity}" readonly>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for=" hardwareId" class="col-lg-1 control-label">Load Card ID </label>
                                        <div class="col-lg-6">
                                            <input type="text" class="form-control" id="hardwareId" name="hardwareId" value="${whInventory.loadCard}" readonly>
                                        </div>
                                        <label for=" lcquantity" class="col-lg-1 control-label">Load Card Quantity </label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="lcquantity" name="lcquantity" value="${whInventory.loadCardQty}" readonly>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for=" hardwareId" class="col-lg-1 control-label">Program Card ID </label>
                                        <div class="col-lg-6">
                                            <input type="text" class="form-control" id="hardwareId" name="hardwareId" value="${whInventory.programCard}" readonly>
                                        </div>
                                        <label for=" pcquantity" class="col-lg-1 control-label">Program Card Quantity </label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="pcquantity" name="pcquantity" value="${whInventory.programCardQty}" readonly>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="form-group">
                                        <label for=" hardwareId" class="col-lg-1 control-label">${IdLabel} </label>
                                        <div class="col-lg-6">
                                            <input type="text" class="form-control" id="hardwareId" name="hardwareId" value="${whInventory.equipmentId}" readonly>
                                        </div>
                                        <label for=" quantity" class="col-lg-1 control-label">Quantity </label>
                                        <div class="col-lg-2">
                                            <input type="text" class="form-control" id="quantity" name="quantity" value="${whInventory.quantity}" readonly>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <!--end loadcard-->
<!--                            <div class="form-group">
                                <label for=" hardwareId" class="col-lg-3 control-label">${IdLabel} </label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="hardwareId" name="hardwareId" value="${whInventory.equipmentId}" readonly>
                                </div>
                            </div>-->
<!--                            <div class="form-group">
                                <label for=" quantity" class="col-lg-3 control-label">Quantity </label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="quantity" name="quantity" value="${whInventory.quantity}" readonly>
                                </div>
                            </div>-->
                            <div class="form-group">
                                <label for="rack" class="col-lg-1 control-label">Rack </label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="rack" name="rack" value="${whInventory.inventoryRack}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="shelf" class="col-lg-1 control-label">Shelf </label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="shelf" name="shelf" value="${whInventory.inventoryShelf}" readonly>
                                </div>
                            </div>
                            <div class="clearfix">           
                                <a href="${contextPath}/wh/whInventory" class="btn btn-info pull-left" id="backBtn"><i class="fa fa-reply"></i> Back</a>
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
                    <li class="active"><a data-toggle="tab" href="#bs">Material Pass</a></li>
                </ul>
                <div class="tab-content">

                    <!--tab for scan barcode sticker-->

                    <div id="bs" class="tab-pane fade in active">
                        <!--<br>-->
                        <h6></h6>
                        <div class="col-lg-7">
                            <div class="main-box">
                                <h2>Extend Material Pass Expiry Date</h2>
                                <form id="mp_form" class="form-horizontal" role="form" action="${contextPath}/wh/whInventory/update" method="post">
                                    <input type="hidden" name="id" id="id" value="${whInventory.id}" />
                                    <input type="hidden" id="requestId" name="requestId" value="${whInventory.requestId}">
                                    <div class="form-group">
                                        <label for="mpNo" class="col-lg-3 control-label">Material Pass No. </label>
                                        <div class="col-lg-5">
                                            <input type="text" class="form-control" id="mpNo" name="mpNo" value="${whInventory.mpNo}" readonly>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="viewMpExpiryDate" class="col-lg-3 control-label">Material Pass Expiry Date </label>
                                        <div class="col-lg-4">
                                            <input type="text" class="form-control" id="viewMpExpiryDate" name="viewMpExpiryDate" value="${whInventory.viewMpExpiryDate}" readonly>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="mpExpiryDate" class="col-lg-3 control-label">New Material Pass Expiry Date *</label>
                                        <div class="col-lg-4">
                                            <div class="input-group">
                                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                <input type="text" name="mpExpiryDate" class="form-control" id="mpExpiryDate" value="">
                                            </div>
                                            <label id="datepickerDate-error" class="error" for="mpExpiryDate" style="display: none;"></label>
                                        </div>
                                    </div>
                                    <div class="pull-right">
                                        <button type="reset" class="btn btn-secondary cancel2">Reset</button>
                                        <button type="submit" id="submit2" name="submit2" class="btn btn-primary">Update</button>
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

                jQuery.extend(jQuery.validator.messages, {
                    required: "This field is required.",
                    equalTo: "Value is not match! Please re-scan.",
                    email: "Please enter a valid email.",
                });

                $('#mpExpiryDate').datepicker({
                    format: 'yyyy-mm-dd',
                    autoclose: true
                });

                var validator1 = $("#mp_form").validate({
                    rules: {
                        mpExpiryDate: {
                            required: true
                        }
                    }
                });

                $(".cancel2").click(function () {
                    validator1.resetForm();
                });
            });

        </script>
    </s:layout-component>
</s:layout-render>