<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>제품 상세</title>
    <jsp:include page="/WEB-INF/view/common/css.jsp" />
    <jsp:include page="/WEB-INF/view/common/js.jsp" />
</head>
<body>

<jsp:include page="/WEB-INF/view/common/navi.jsp" />

<div class="container">
    <div class="row">
        <div class="col-md-4"><img src="${product.images[0].url}" class="img-responsive"></div>
        <div class="col-md-8">
            <div class="panel panel-default">
                <div class="panel-heading">${product.name}</div>
                <div class="panel-body">
                    가격 : ${product.price}
                    <br>
                    상세 : ${product.detail}
                </div>
            </div>
            <div>
                <button type="button" class="btn btn-primary btn-lg" id="orderBtn">구매하기</button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/view/common/footer.jsp" />

<script>
    $(function() {
        $("#orderBtn").click(function() {
            handleOrder();
        });
    });

    <sec:authorize access="!isAuthenticated()">
    function handleOrder() {
        alert("로그인을 먼저 하세요!");
    }
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
    function handleOrder() {
        alert("주문 확인 화면으로 넘어갑니다.");
        $("#goConfirmForm").submit();
    }
    </sec:authorize>
</script>

<sec:authorize access="isAuthenticated()">
<form id="goConfirmForm" action="/orders/orderConfirm" method="post">
    <input type="hidden" name="orderProducts[0].productId" value="${product.id.id}">
    <input type="hidden" name="orderProducts[0].quantity" value="1">
</form>
</sec:authorize>
</body>
</html>