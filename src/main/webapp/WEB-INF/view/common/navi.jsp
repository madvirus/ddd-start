<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%
    String uri = (String)request.getAttribute("javax.servlet.forward.request_uri");
%>
<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="/home">DIB</a>
            <ul class="nav navbar-nav">
                <li <%= uri.startsWith("/home") ? "class=\"active\"" : ""%>><a href="/home">홈</a></li>
                <li <%= uri.startsWith("/products") ? "class=\"active\"" : ""%>><a href="/categories">카테고리</a></li>
            </ul>
        </div>

        <ul class="nav navbar-nav navbar-right">
            <sec:authorize access="!isAuthenticated()">
                <li><a href="/login">로그인</a></li>
            </sec:authorize>
            <sec:authorize access="hasRole('ADMIN')">
                <li><a href="/admin/main">관리자</a></li>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <li><a href="/my/main"><sec:authentication property="principal.username"/></a></li>
                <li><a href="/logout">로그아웃</a></li>
            </sec:authorize>
        </ul>
    </div>
</nav>
