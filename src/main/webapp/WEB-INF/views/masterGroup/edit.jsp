<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
    </s:layout-component>
    <s:layout-component name="page_css_inline">
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>Add Master Group</h1>
            <div class="row">
                <div class="col-lg-6">
                    <div class="main-box">
                        <h2>Master Group Information</h2>
                        <form id="add_group_form" class="form-horizontal" role="form" action="${contextPath}/admin/masterGroup/update" method="post">
                            <div class="form-group">
                                <label for="group" class="col-lg-4 control-label">Group *</label>
                                <div class="col-lg-8">
                                     <input type="hidden" class="form-control" id="id" name="id" placeholder="group" value="${masterGroup.id}">
                                    <input type="text" class="form-control" id="group" name="group" placeholder="group" value="${masterGroup.groupMaster}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="hardware" class="col-lg-4 control-label">Hardware *</label>
                                <div class="col-lg-8">
                                    <input type="text" class="form-control" id="hardware" name="hardware" placeholder="hardware" value="${masterGroup.hardware}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="remarks" class="col-lg-4 control-label">Remarks </label>
                                <div class="col-lg-8">
                                   <textarea class="form-control" rows="5" id="remarks" name="remarks">${masterGroup.remarks}</textarea>
                                </div>
                            </div>
                            <a href="${contextPath}/admin/masterGroup" class="btn btn-info pull-left"><i class="fa fa-reply"></i> Back</a>
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
                        group: {
                            required: true
                        },
                        hardware: {
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