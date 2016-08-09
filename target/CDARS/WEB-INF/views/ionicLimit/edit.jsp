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
            <h1>Edit Ionic Test Limit</h1>
            <div class="row">
                <div class="col-lg-6">
                    <div class="main-box">
                        <h2>Ionic Test Limit Information</h2>
                        <form id="edit_limit_form" class="form-horizontal" role="form" action="${contextPath}/admin/ionicLimit/update" method="post">
                            <input type="hidden" name="ionicLimitId" value="${ionicLimit.id}" />
                            <div class="form-group">
                                <label for="name" class="col-lg-4 control-label">Name *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="name" name="name" placeholder="Name" value="${ionicLimit.name}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="value" class="col-lg-4 control-label">Value Limit *</label>
                                <div class="col-lg-8">
                                    <input class="form-control" id="value" name="value" placeholder="Value Limit" value="${ionicLimit.value}">
                                </div>
                            </div>
                            <a href="${contextPath}/admin/ionicLimit" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
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
                var validator = $("#edit_limit_form").validate({
                    rules: {
                        name: {
                            required: true,
                            lsnandbasicsymbol: true,
                            minlength: 2,
                            maxlength: 100
                        },
                        value: {
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