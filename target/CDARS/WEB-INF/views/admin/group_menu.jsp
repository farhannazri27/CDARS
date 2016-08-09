<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/base.jsp">
    <s:layout-component name="page_css">
    </s:layout-component>
    <s:layout-component name="page_css_inline">
    </s:layout-component>
    <s:layout-component name="page_container">
        <div class="col-lg-12">
            <h1>Group Menu Access</h1>
            <div class="row">
                <div class="col-lg-12">
                    <div class="main-box clearfix">
                        <div class="clearfix">
                            <h2 class="pull-left">Access List</h2>
                        </div>
                        <hr/>
                        <form id="admin_group_access" action="${contextPath}/admin/group/menu/save" method="post">
                            <div class="clearfix">
                                <div class="form-group">
                                    <input type="hidden" id="selectedGroup" name="groupId" value="${groupId}" >
                                    <a href="${contextPath}/admin/group" class="btn btn-info"><i class="fa fa-reply"></i> Back</a>
                                    <button type="reset" id="selectedGroupReset" class="btn btn-secondary">Reset</button>
                                    <button type="submit" class="btn btn-primary">Save</button>
                                </div>
                            </div>
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th style="width: 70px;"><span><input id="group_access_all" type="checkbox" class="group_access"></span></th>
                                            <th style="width: 200px;"><span>Menu</span>
                                            <th style="width: 50px;"><span>&nbsp;</span></th>
                                            <th style="width: 200px;"><span>Sub Menu</span></th>
                                            <th>&nbsp;</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:if test="${empty userGroupAccessList}">
                                            <tr>
                                                <td colspan="5">
                                                    <p align="center" style="padding-top: 6px; font-size: 13px;">Please select group!</p>
                                                </td>
                                            </tr>
                                        </c:if>
                                        <c:forEach items="${userGroupAccessList}" var="access" varStatus="accessLoop">
                                            <tr>
                                                <td style="padding-left: 25px;">
                                                    <c:if test="${access.parentCode == '0'}">
                                                        <input id="code_<c:out value="${access.code}"/>" parentcode="<c:out value="${access.parentCode}"/>" code="<c:out value="${access.code}"/>" type="checkbox" name="groupAccess" value="<c:out value="${access.menuId}"/>" class="group_access group_access_count parent_<c:out value="${access.parentCode}"/>" <c:out value="${access.selected}"/>>
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <div class="form-group" style="margin-top: 0px; margin-bottom: 0px;">
                                                        <c:if test="${access.parentCode == '0'}">
                                                            <c:out value="${access.name}"/>
                                                        </c:if>
                                                    </div>
                                                </td>
                                                <td>
                                                    <c:if test="${access.parentCode != '0'}">
                                                        <input id="code_<c:out value="${access.code}"/>" parentcode="<c:out value="${access.parentCode}"/>" code="<c:out value="${access.code}"/>" type="checkbox" name="groupAccess" value="<c:out value="${access.menuId}"/>" class="group_access group_access_count parent_<c:out value="${access.parentCode}"/>" <c:out value="${access.selected}"/>>  
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <c:if test="${access.parentCode != '0'}">
                                                        <c:out value="${access.name}"/>
                                                    </c:if>
                                                </td>
                                                <td>
                                                    &nbsp;
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </s:layout-component>
    <s:layout-component name="page_js">
    </s:layout-component>
    <s:layout-component name="page_js_inline">
        <script>
            $(document).ready(function () {
                $(".group_access_count").change(function () {
                    group_access();
                    if ($(this).attr("parentCode") === "0") {
                        unchecked_child_access($(this).attr("code"));
                    } else {
                        var parentCode = $(this).attr("parentCode");
                        if ($(this).prop("checked")) {
                            $("#code_" + parentCode).prop("checked", true);
                        } else {
                            if ($(".parent_" + parentCode + ":checked").length === 0) {
                                $("#code_" + parentCode).prop("checked", false);
                            } else {
                                $("#code_" + parentCode).prop("checked", true);
                            }
                        }
                    }
                    ;
                });
                $("#group_access_all").change(function () {
                    if ($("#group_access_all").prop("checked")) {
                        $(".group_access").prop("checked", true);
                    } else {
                        $(".group_access").prop("checked", false);
                    }
                });
                $("#selectedGroupReset").click(function (event) {
                    event.preventDefault();
                    $(this).closest('form').get(0).reset();
                    group_access();
                });
                function group_access() {
                    if ($('.group_access_count:checked').length === $('.group_access_count').length) {
                        $("#group_access_all").prop("checked", true);
                    } else {
                        $("#group_access_all").prop("checked", false);
                    }
                }
                function unchecked_child_access(code) {
                    if ($("#code_" + code).prop("checked")) {
                        $(".parent_" + code).prop("checked", true);
                    } else {
                        $(".parent_" + code).prop("checked", false);
                    }
                    group_access();
                }
                group_access();
            });
        </script>
    </s:layout-component>
</s:layout-render>