<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>관리자 주문 상세 : ${order.number}</title>
    <jsp:include page="/WEB-INF/view/common/css.jsp"/>
    <jsp:include page="/WEB-INF/view/common/js.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/view/common/navi.jsp"/>

<div class="container">
    <ol class="breadcrumb">
        <li><a href="/admin/main">관리자</a></li>
        <li><a href="/admin/orders">주문 관리</a></li>
        <li class="active">주문 ${order.number}</li>
    </ol>

    <h2>주문 상세</h2>

    <jsp:include page="/WEB-INF/view/common/order/orderDetailPart.jsp" />

    <div class="panel panel-default">
        <div class="panel-body">
            <c:if test="${order.notYetShipped}">
                <button id="shippedBtn" class="btn btn-default">배송 처리하기</button>
            </c:if>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/view/common/footer.jsp"/>
<form id="goShppingForm" action="/admin/orders/${order.number}/shipping" method="post">
    <input type="hidden" name="version" value="${order.version}">
</form>

<script>
    $(function () {
        $("#shippedBtn").click(function () {
            if (confirm("배송 상태로 처리하시겠습니까?")) {
                $("#goShppingForm").submit();
            }
        });
    });
</script>
</body>
</html>