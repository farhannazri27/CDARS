<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
    </s:layout-component>
    <s:layout-component name="page_css_inline">
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>Add User</h1>
            <div class="row">
                <div class="col-lg-6">
                    <div class="main-box">
                        <h2>User Information</h2>
                        <form id="add_user_form" class="form-horizontal" role="form" action="${contextPath}/admin/user/save" method="post">
                            <div class="form-group">
                                <label for="loginId" class="col-lg-4 control-label">Login ID *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="loginId" name="loginId" placeholder="Login ID" value="${loginId}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="fullname" class="col-lg-4 control-label">Name *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="fullname" name="fullname" placeholder="Name" value="${fullname}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="email" class="col-lg-4 control-label">Email *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="email" name="email" placeholder="Email" value="${email}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="password" class="col-lg-4 control-label">Password *</label>
                                <div class="col-lg-8">
                                    <input type="password" class="form-control" id="password" name="password" placeholder="Password">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="confirmPassword" class="col-lg-4 control-label">Confirm Password *</label>
                                <div class="col-lg-8">
                                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="Confirm Password">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="groupId" class="col-lg-4 control-label">Group *</label>
                                <div class="col-lg-8">
                                    <select id="groupId" name="groupId" class="form-control">
                                        <option value="" selected="">Select Group...</option>
                                        <c:forEach items="${userGroupList}" var="group">
                                            <option value="${group.id}" ${group.selected}>${group.code} - ${group.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <a href="${contextPath}/admin/user" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
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
                var validator = $("#add_user_form").validate({
                    rules: {
                        loginId: {
                            required: true,
                            alphanumeric: true,
                            minlength: 2
                        },
                        fullname: {
                            required: true,
                            letterspace: true,
                            minlength: 2
                        },
                        email: {
                            required: true,
                            email: true
                        },
                        password: {
                            required: true,
                            minlength: 8
                        },
                        confirmPassword: {
                            required: true,
                            minlength: 8,
                            equalTo: password
                        },
                        groupId: {
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