<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>마이샵</title>
    <jsp:include page="/WEB-INF/view/common/css.jsp" />
</head>
<body>

<jsp:include page="/WEB-INF/view/common/navi.jsp" />

<div class="container">
    <div class="alert alert-success" role="alert">
        주문[${orderNo}]을 취소했습니다.
    </div>

    <a class="btn btn-primary" href="/my/orders/${orderNo}" role="button">주문 내역 보기</a>
    <a class="btn btn-default" href="/my/orders" role="button">주문 목록 보기</a>
    <a class="btn btn-default" href="/home" role="button">첫 화면으로 이동하기</a>
</div>

<jsp:include page="/WEB-INF/view/common/footer.jsp" />

</body>
</html>