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
            <h1>Edit Hardware Queue</h1>
            <div class="row">
                <div class="col-lg-6">
                    <div class="main-box">
                        <h2>Hardware Queue Information</h2>
                        <form id="add_hardwarequeue_form" class="form-horizontal" role="form" action="${contextPath}/relLab/hardwarequeue/update" method="post">
                            <input type="hidden" name="hardwareQueueId" value="${hardwareQueue.id}" />
                            <div class="form-group">
                                <label for="code" class="col-lg-4 control-label">Code *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="code" name="code" placeholder="Code" value="${hardwareQueue.code}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="name" class="col-lg-4 control-label">Name *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="name" name="name" placeholder="Name" value="${hardwareQueue.name}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="startDate" class="col-lg-4 control-label">Start Date *</label>
                                <div class="col-lg-8">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" name="startDate" class="form-control" id="datepickerDate" value="${hardwareQueue.startDate}">
                                    </div>
                                    <label id="datepickerDate-error" class="error" for="datepickerDate" style="display: none;"></label>
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
                        code: {
                            required: true,
                            alphanumericdu: true,
                            minlength: 2,
                            maxlength: 10
                        },
                        name: {
                            required: true,
                            lsnandbasicsymbol: true,
                            minlength: 2,
                            maxlength: 100
                        },
                        startDate: {
                            required: true
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