<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/base/landing.jsp">
    <s:layout-component name="page_title">
        CMTS - User
    </s:layout-component>
    <s:layout-component name="page_body">
        <h1><f:message key="page.label.user.signup"/></h1>
        <p>The time on the server is ${serverTime}.</p>
    </s:layout-component>
</s:layout-render>