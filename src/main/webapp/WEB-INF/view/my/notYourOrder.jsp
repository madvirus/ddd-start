<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>내 주문</title>
    <jsp:include page="/WEB-INF/view/common/css.jsp" />
</head>
<body>

<jsp:include page="/WEB-INF/view/common/navi.jsp" />

<div class="container">
    <div class="alert alert-danger" role="alert">
        존재하지 않는 주문입니다.[NYO]
    </div>
</div>

<jsp:include page="/WEB-INF/view/common/footer.jsp" />

</body>
</html>