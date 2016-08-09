<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/landing.jsp">
    <s:layout-component name="page_title">
        CMTS - User
    </s:layout-component>
    <s:layout-component name="page_body">
        <h1><f:message key="page.label.user.login"/></h1>
        <c:if test="${not empty logout}">
            <p>${logout}</p>
        </c:if>
        <c:if test="${not empty error}">
            <p>${error}</p>
        </c:if>
        <c:url var="loginUrl" value="/"/>
        <form action="${loginUrl}" method="post">
            Username: <input type="text" id="username" name="username"/><br/>
            Password: <input type="password" id="password" name="password"/><br/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="submit" value="Log in" />
        </form>
    </s:layout-component>
</s:layout-render>