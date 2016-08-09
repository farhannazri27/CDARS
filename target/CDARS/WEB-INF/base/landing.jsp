<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/base/taglibs.jsp" %>

<s:layout-definition>
    <html>
        <head>
            <title>
                <s:layout-component name="page_title">
                    Page title
                </s:layout-component>
            </title>
            <link rel="SHORTCUT ICON" href="${contextPath}/resources/img/favicon.ico" />
            <link rel="STYLESHEET" href="${contextPath}/resources/css/opensans.css" />
            <link rel="STYLESHEET" href="${contextPath}/resources/css/landing.css" />
        </head>
        <body>
            <c:if test="${not empty pageContext.request.remoteUser}">
                <p>Hello <b><c:out value="${sessionScope.sessionUser.loginId}"/></b></p>
                <c:url var="logoutUrl" value="/logout"/>
                <a href="${logoutUrl}">Logout</a>
            </c:if>
            <s:layout-component name="page_body">
                Page body
            </s:layout-component>
        </body>
    </html>
</s:layout-definition>
