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
            <h1>Edit Parameter</h1>
            <div class="row">
                <div class="col-lg-6">
                    <div class="main-box">
                        <h2>Parameter Information</h2>
                        <form id="add_hardwarequeue_form" class="form-horizontal" role="form" action="${contextPath}/admin/parameterMaster/update" method="post">
                            <input type="hidden" name="parameterMasterId" value="${parameterMaster.id}" />
                            <div class="form-group">
                                <label for="code" class="col-lg-4 control-label">Code *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="code" name="code" placeholder="Code" value="${parameterMaster.code}" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="name" class="col-lg-4 control-label">Name *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="name" name="name" placeholder="Name" value="${parameterMaster.name}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="startDate" class="col-lg-4 control-label">Remarks</label>
                                <div class="col-lg-8">
                                    <textarea class="form-control" rows="5" id="remarks" name="remarks">${parameterMaster.remarks}</textarea>
                                </div>
                            </div>
                            <a href="${contextPath}/admin/parameterMaster" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
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