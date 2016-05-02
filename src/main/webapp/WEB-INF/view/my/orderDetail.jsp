<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>주문 상세: ${order.number}</title>
    <jsp:include page="/WEB-INF/view/common/css.jsp"/>
    <jsp:include page="/WEB-INF/view/common/js.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/view/common/navi.jsp"/>

<div class="container">
    <h2>주문 상세</h2>

    <jsp:include page="/WEB-INF/view/common/order/orderDetailPart.jsp" />

    <div class="panel panel-default">
        <div class="panel-body">
            <c:if test="${order.notYetShipped}">
                <button id="cancelBtn" class="btn btn-default">주문 취소하기</button>
                <button id="changeShippingBtn" class="btn btn-default">배송지 변경하기</button>
            </c:if>
            <a class="btn btn-default" href="/my/orders" role="button">주문 목록 보기</a>
            <a class="btn btn-default" href="/home" role="button">첫 화면으로 이동하기</a>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/view/common/footer.jsp"/>
<form id="goCancelForm" action="/my/orders/${order.number}/cancel" method="post"></form>
<form id="goChangeShippingForm" action="/my/orders/${order.number}/shippingInfo" method="get"></form>

<script>
    $(function () {
        $("#cancelBtn").click(function () {
            if (confirm("주문을 취소하시겠습니까?")) {
                $("#goCancelForm").submit();
            }
        });
        $("#changeShippingBtn").click(function () {
            $("#goChangeShippingForm").submit();
        });
    });
</script>
</body>
</html>