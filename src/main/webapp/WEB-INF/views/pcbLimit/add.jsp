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
            <h1>PCB Quantity Limit</h1>
            <div class="row">
                <div class="col-lg-6">
                    <div class="main-box">
                        <h2>PCB Quantity Limit Information</h2>
                        <form id="add_hardwarequeue_form" class="form-horizontal" role="form" action="${contextPath}/admin/pcbLimit/save" method="post">
                            <div class="form-group">
                                <label for="pcbType" class="col-lg-4 control-label">PCB Type *</label>
                                <div class="col-lg-8">
                                    <select id="pcbType" name="pcbType" class="form-control">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${pcbType}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="quantity" class="col-lg-4 control-label">Quantity Limit *</label>
                                <div class="col-lg-3">
                                    <input class="form-control" id="quantity" name="quantity" placeholder="Qty Limit" value="${pcbLimit.quantity}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="remarksApprover" class="col-lg-4 control-label">Remarks </label>
                                <div class="col-lg-8">
                                    <textarea class="form-control" rows="5" id="remarksApprover" name="remarksApprover">${pcbLimit.remarks}</textarea>
                                </div>
                            </div>
                            <a href="${contextPath}/admin/pcbLimit" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
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
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {

                var validator = $("#add_hardwarequeue_form").validate({
                    rules: {
                        pcbType: {
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
        </script>
    </s:layout-component>
</s:layout-render>