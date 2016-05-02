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
    <div class="jumbotron">
        <h3>오늘의 특가</h3>
        <p>라즈베리파이 3</p>
        <p><a class="btn btn-primary" href="/products/prod-001" role="button">상품 보기</a></p>
    </div>
</div>

<jsp:include page="/WEB-INF/view/common/footer.jsp" />

</body>
</html>