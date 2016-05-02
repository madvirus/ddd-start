<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>마이 페이지</title>
    <jsp:include page="/WEB-INF/view/common/css.jsp" />
</head>
<body>

<jsp:include page="/WEB-INF/view/common/navi.jsp" />

<div class="container">
    <h2><sec:authentication property="principal.username" /> 님</h2>
    <ul>
        <li><a href="/my/orders">주문 목록 보기</a></li>
    </ul>
</div>

<jsp:include page="/WEB-INF/view/common/footer.jsp" />

</body>
</html>