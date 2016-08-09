<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
    </s:layout-component>
    <s:layout-component name="page_css_inline">
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>Add Group</h1>
            <div class="row">
                <div class="col-lg-6">
                    <div class="main-box">
                        <h2>Group Information</h2>
                        <form id="add_group_form" class="form-horizontal" role="form" action="${contextPath}/admin/group/update" method="post">
                            <input type="hidden" name="groupId" value="${userGroup.id}">
                            <div class="form-group">
                                <label for="groupCode" class="col-lg-4 control-label">Code *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="groupCode" name="groupCode" placeholder="Code" value="${userGroup.code}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="groupName" class="col-lg-4 control-label">Name *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="groupName" name="groupName" placeholder="Name" value="${userGroup.name}">
                                </div>
                            </div>
                            <a href="${contextPath}/admin/group" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
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
                var validator = $("#add_group_form").validate({
                    rules: {
                        groupCode: {
                            required: true,
                            alphanumericdu: true,
                            minlength: 2,
                            maxlength: 10
                        },
                        groupName: {
                            required: true,
                            alphanumericdash: true,
                            minlength: 2
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