<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/landing.jsp">
    <s:layout-component name="page_title">
        CMTS - Edit User
    </s:layout-component>
    <s:layout-component name="page_body">
        <h1><spring:message code="page.label.user.add"/></h1>
        <div>
            <form:form action="${contextPath}/user/update" commandName="user" method="POST">
                <form:hidden path="id"/>
                <form:hidden path="authority"/>
                <form:hidden path="active"/>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div>
                    <form:label path="loginId"><spring:message code="entity.user.login_id"/>:</form:label>
                    <form:input path="loginId" size="20"/>
                </div>
                <div>
                    <form:label path="password"><spring:message code="entity.user.password"/>:</form:label>
                    <form:input path="password" size="20"/>
                </div>
                <div>
                    <input type="submit" value="<spring:message code="general.label.submit"/>"/>
                </div>
            </form:form>
        </div>
    </s:layout-component>
</s:layout-render>