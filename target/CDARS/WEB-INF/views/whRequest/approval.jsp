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
            <h1>Approval for Hardware Request</h1>
            <div class="row">
                <div class="col-lg-10">
                    <div class="main-box">
                        <h2>Hardware Request Information</h2>
                        <form id="approval_hardwarequest_form" class="form-horizontal" role="form" action="${contextPath}/wh/whRequest/approvalupdate" method="post">
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
                                <div class="col-lg-4">
                                    <input type="text" class="form-control" id="equipmentType" name="equipmentType" value="${whRequest.equipmentType}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="equipmentId" class="col-lg-2 control-label">${IdLabel}</label>
                                <div class="col-lg-6">
                                    <input type="text" class="form-control" id="equipmentId" name="equipmentId" value="${whRequest.equipmentId}" readonly>
                                </div>
                                 <label for="quantity" class="col-lg-1 control-label">Total Quantity </label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="quantity" name="quantity" placeholder="Quantity" value="${whRequest.quantity}" readonly>
                                </div>
                            </div>
                            <div class="form-group" id="quantitydiv" hidden>
                                <label for="quantity" class="col-lg-2 control-label">Quantity </label>
                                <div class="col-lg-2">
                                    <input type="text" class="form-control" id="quantity" name="quantity" placeholder="Quantity" value="${whRequest.quantity}" readonly>
                                </div>
                            </div>
                            <div class="form-group" id="pcbADiv" hidden>
                                <label for="pcbA" class="col-lg-2 control-label">Qual A</label>
                                <div class="col-lg-6">
                                    <input type="text" class="form-control" id="pcbA" name="pcbA" value="${whRequest.pcbA}" readonly>
                                </div>
                             <label for="pcbAQty" class="col-lg-1 control-label">Quantity</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="pcbAQty" name="pcbAQty" value="${whRequest.pcbAQty}" readonly>
                                </div>   
                            </div>
                            <div class="form-group" id="pcbBDiv" hidden>
                                <label for="pcbB" class="col-lg-2 control-label">Qual B</label>
                                <div class="col-lg-6">
                                    <input type="text" class="form-control" id="pcbB" name="pcbB" value="${whRequest.pcbB}" readonly>
                                </div>
                                <label for="pcbBQty" class="col-lg-1 control-label">Quantity</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="pcbBQty" name="pcbBQty" value="${whRequest.pcbBQty}" readonly>
                                </div>
                            </div>
                            <div class="form-group" id="pcbCDiv" hidden>
                                <label for="pcbC" class="col-lg-2 control-label">Qual C</label>
                                <div class="col-lg-6">
                                    <input type="text" class="form-control" id="pcbC" name="pcbC" value="${whRequest.pcbC}" readonly>
                                </div>
                                <label for="pcbCQty" class="col-lg-1 control-label">Quantity</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="pcbCQty" name="pcbCQty" value="${whRequest.pcbCQty}" readonly>
                                </div>
                            </div>
                            <div class="form-group" id="pcbCtrDiv" hidden>
                                <label for="equipmentId" class="col-lg-2 control-label">Control</label>
                                <div class="col-lg-6">
                                    <input type="text" class="form-control" id="pcbCtr" name="pcbCtr" value="${whRequest.pcbCtr}" readonly>
                                </div>
                                <label for="pcbCtrQty" class="col-lg-1 control-label">Quantity</label>
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="pcbCtrQty" name="pcbCtrQty" value="${whRequest.pcbCtrQty}" readonly>
                                </div>
                            </div>
                            <div class="form-group" id="remarksDiv" >
                                <label for="remarks" class="col-lg-2 control-label">Remarks </label>
                                <div class="col-lg-6">
                                    <textarea class="form-control" rows="5" id="remarks" name="remarks" readonly>${whRequest.remarksLog}</textarea>
                                </div>
                            </div>

                            <!--approval-->
                            <!--<br>-->
                            <br>
                            <div class="form-group" id="approvalDiv" >
                                <label for="finalApprovedStatus" class="col-lg-2 control-label">Approved / Not Approved * </label>
                                <div class="col-lg-6">                                      
                                    <select id="finalApprovedStatus" name="finalApprovedStatus" class="form-control">
                                        <option value=""></option>
                                        <c:forEach items="${approvalStatus}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="remarksApprover" class="col-lg-2 control-label">Remarks </label>
                                <div class="col-lg-6">
                                    <textarea class="form-control" rows="5" id="remarksApprover" name="remarksApprover">${whRequest.remarksApprover}</textarea>
                                </div>
                            </div>

                            <a href="${contextPath}/wh/whRequest" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
                            <div class="pull-right">
                                <button type="reset" class="btn btn-secondary cancel">Reset</button>
                                <button type="submit" id ="submit" name="submit" class="btn btn-primary">Save</button>
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
                
                var elementReqType = $('#requestType');
                if (elementReqType.val() === "Retrieve") {
                     $("#submit").attr("disabled", true);
                    $("#finalApprovedStatus").attr("disabled", true);
                    $("#remarksApprover").attr("readonly", true);
                }

                var element = $('#equipmentType');
                if (element.val() === "Motherboard") {
                    $("#typediv").hide();
                    $("#quantitydiv").hide();
                } else if (element.val() === "Stencil") {
                    $("#typediv").hide();
                    $("#quantitydiv").hide();
                } else if (element.val() === "Tray") {
                    $("#typediv").hide();
                } else if (element.val() === "PCB") {
                    $("#typediv").hide();
                    $("#pcbADiv").show();
                    $("#pcbBDiv").show();
                    $("#pcbCDiv").show();
                    $("#pcbCtrDiv").show();
                } else {
                    $("#typediv").hide();
                    $("#quantitydiv").hide();
                }

                var element = $('#finalApprovedStatus');
                if (element.val() === "Approved") {
                    $("#submit").attr("disabled", true);
                    $("#finalApprovedStatus").attr("disabled", true);
                    $("#remarksApprover").attr("readonly", true);
                }


                var validator = $("#approval_hardwarequest_form").validate({
                    rules: {
                        finalApprovedStatus: {
                            required: true
                        }
                    }
                });
                $(".cancel").click(function () {
                    validator.resetForm();
                });
            });

            $('#finalApprovedStatus').on('change', function () {
                if ($(this).val() === "Not Approved") {
                    $("#remarksApprover").attr("required", true);
                } else {
                    $("#remarksApprover").attr("required", false);
                }
            });


        </script>
    </s:layout-component>
</s:layout-render>