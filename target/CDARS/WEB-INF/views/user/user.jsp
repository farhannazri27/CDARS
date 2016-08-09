<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/landing.jsp">
    <s:layout-component name="page_title">
        CMTS - User
    </s:layout-component>
    <s:layout-component name="page_body">
        <h1><f:message key="page.label.user"/></h1>
        <p><a href="${contextPath}/user/add"><f:message key="page.label.user.add"/></p>
        <table>
            <thead>
                <tr>
                    <td><spring:message code="entity.user.login_id"/></td>
                    <td><spring:message code="entity.user.password"/></td>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td><c:out value="${user.loginId}"/></td>
                        <td><c:out value="${user.password}"/></td>
                        <td><a href="${contextPath}/user/edit/<c:out value="${user.id}"/>"><spring:message code="general.label.edit"/></a></td>
                        <td><a href="${contextPath}/user/delete/<c:out value="${user.id}"/>"><spring:message code="general.label.delete"/></a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </s:layout-component>
</s:layout-render>