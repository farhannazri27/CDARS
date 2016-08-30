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
            <h1>Add Email Management</h1>
            <div class="row">
                <div class="col-lg-6">
                    <div class="main-box">
                        <h2>Email management Information</h2>
                        <form id="add_hardwarequest_form" class="form-horizontal" role="form" action="${contextPath}/admin/emailConfig/save" method="post">
                            <div class="form-group">
                                <label for="taskPdetailsCode" class="col-lg-4 control-label">For *</label>
                                <div class="col-lg-5">
                                    <select id="taskPdetailsCode" name="taskPdetailsCode" class="form-control">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${emailTo}" var="group">
                                            <option value="${group.name}" ${group.selected}>${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="userOncid" class="col-lg-4 control-label">User*</label>
                                <div class="col-lg-8">
                                    <select id="userOncid" name="userOncid" class="form-control">
                                        <option value="" selected=""></option>
                                        <c:forEach items="${user}" var="group">
                                            <option value="${group.oncid}" ${group.selected}>${group.firstname} ${group.lastname}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>      
                            <div class="form-group" id="remarksdiv">
                                <label for="remarks" class="col-lg-4 control-label">Remarks </label>
                                <div class="col-lg-8">
                                    <textarea class="form-control" rows="5" id="remarks" name="remarks"></textarea>
                                </div>
                            </div>
                            <a href="${contextPath}/admin/emailConfig" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
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

                var validator = $("#add_hardwarequest_form").validate({
                    rules: {
                        taskPdetailsCode: {
                            required: true
                        },
                        userOncid: {
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