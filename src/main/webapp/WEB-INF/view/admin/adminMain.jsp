<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>관리자 페이지</title>
    <jsp:include page="/WEB-INF/view/common/css.jsp" />
</head>
<body>

<jsp:include page="/WEB-INF/view/common/navi.jsp" />

<div class="container">
    <h2>관리자</h2>
    <ul>
        <li><a href="/admin/orders">주문 관리</a></li>
    </ul>
</div>

<jsp:include page="/WEB-INF/view/common/footer.jsp" />

</body>
</html>