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
            <h1>Add-hoc</h1>
            <div class="row">
                <div class="col-lg-6">
                    <div class="main-box">
                        <h2>Add-Hoc Information</h2>
                        <form id="add_hardwarequeue_form" class="form-horizontal" role="form" action="${contextPath}/hardwarequeue/saveAddHoc" method="post">
                            <div class="form-group">
                                <label for="classification" class="col-lg-4 control-label">Classification</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="classification" name="classification" value="Ad-hoc" readonly>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="groupId" class="col-lg-4 control-label">Source *</label>
                                <div class="col-lg-8">
                                    <select id="source" name="source" class="form-control">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${sourcelist}" var="group">
                                            <option value="${group.detailCode}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="groupId" class="col-lg-4 control-label">Category *</label>
                                <div class="col-lg-8">
                                    <select id="category" name="category" class="form-control">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${categorylist}" var="group">
                                            <option value="${group.detailCode}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="hardwareIdDiv">
                                <label for="hardwareId" class="col-lg-4 control-label">Hardware ID *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="hardwareId" name="hardwareId" placeholder="Please scan hardware ID" value="">
                                </div>
                            </div>
                            <div class="form-group" id="pcIdDiv" hidden>
                                <label for="pcardId" class="col-lg-4 control-label">Program Card ID *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="pcardId" name="pcardId" placeholder="Please scan program card ID" value="">
                                </div>
                            </div>
                             <div class="form-group" id="pcQtyDiv" hidden>
                            <label for="pcardQty" class="col-lg-4 control-label">Program Card Quantity </label>
                                <div class="col-lg-3">
                                    <input class="form-control" id="pcardQty" name="pcardQty" value="">
                                </div>
                            </div>  
                            <div class="form-group" id="lcIdDiv" hidden>
                                <label for="lcardId" class="col-lg-4 control-label">Load Card ID *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="lcardId" name="lcardId" placeholder="Please scan load card ID" value="">
                                </div>    
                            </div>
                            <div class="form-group" id="lcQtyDiv" hidden>
                            <label for="lcardQty" class="col-lg-4 control-label">Load Card Quantity </label>
                                <div class="col-lg-3">
                                    <input class="form-control" id="lcardQty" name="lcardQty" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="rms" class="col-lg-4 control-label">RMS# *</label>
                                <div class="col-lg-5">
                                    <input type="text" class="form-control" id="rms" name="rms" placeholder="Please scan RMS ID" value="N/A">
                                    <input type="checkbox" id="rmsCheck"> If RMS # exists
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="event" class="col-lg-4 control-label">Event *</label>
                                <div class="col-lg-8">
                                    <select id="event" name="event" class="form-control">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${evenlist}" var="group">
                                            <option value="${group.detailCode}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="equipmentId" class="col-lg-4 control-label">Equipment ID *</label>
                                <div class="col-lg-8">
                                    <select id="equipmentId" name="equipmentId" class="form-control">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${equipmentlist}" var="group">
                                            <option value="${group.detailCode}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <a href="${contextPath}/hardwarequeue" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
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
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {
                $('#datepickerDate').datepicker({
                    format: 'yyyy-mm-dd',
                    autoclose: true
                });
                var validator = $("#add_hardwarequeue_form").validate({
                    rules: {
                        category: {
                            required: true
                        },
                        event: {
                            required: true
                        },
                        equipmentId: {
                            required: true
                        },
                         pcardQty: {
                            number: true
                        },
                         lcardQty: {
                            number: true
                        },
                        source: {
                            required: true
                        }
                    }
                });
                $(".cancel").click(function () {
                    validator.resetForm();
                });
            });

            $("#rmsCheck").click(function () {
                var check = $(this).prop('checked');
                if (check === true) {
                    $("#rms").val("");
                    $('#rms').focus();
                } else {
                    $("#rms").val("N/A");
                }
            });

            $('#category').on('change', function () {
                if ($(this).val() === "002002") {
                    $("#pcIdDiv").show();
                    $("#lcIdDiv").show();
                     $("#pcQtyDiv").show();
                    $("#lcQtyDiv").show();
                    $("#hardwareIdDiv").hide();
                    $("#hardwareId").val("");
                } else {
                    $("#pcIdDiv").hide();
                    $("#lcIdDiv").hide();
                    $("#pcQtyDiv").hide();
                    $("#lcQtyDiv").hide();
                    $("#hardwareIdDiv").show();
                    $("#hardwareId").val("");
                    $("#pcardId").val("");
                    $("#lcardId").val("");
                    $("#pcardQty").val("");
                    $("#lcardQty").val("");
                }

            });
        </script>
    </s:layout-component>
</s:layout-render>