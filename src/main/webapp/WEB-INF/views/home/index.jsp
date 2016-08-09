<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<c:choose>
    <c:when test="${not empty pageContext.request.remoteUser}">
        <%@include file="/WEB-INF/views/home/home.jsp" %>
    </c:when>
    <c:otherwise>
        <%@include file="/WEB-INF/views/home/login.jsp" %>
    </c:otherwise>
</c:choose>
