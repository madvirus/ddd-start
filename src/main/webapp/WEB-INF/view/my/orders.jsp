<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>주문 목록</title>
    <jsp:include page="/WEB-INF/view/common/css.jsp" />
</head>
<body>

<jsp:include page="/WEB-INF/view/common/navi.jsp" />

<div class="container">
    <h2>주문 목록</h2>
    <c:if test="${empty orders}">
        <div class="alert alert-info" role="alert">
            주문 내역이 없습니다.
        </div>
    </c:if>

    <c:if test="${! empty orders}">
        <table class="table">
            <thead>
            <tr>
                <th>주문번호</th>
                <th>금액</th>
                <th>상태</th>
                <th>상품</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td><a href="/my/orders/${order.number}">${order.number}</a></td>
                    <td>${order.totalAmounts}</td>
                    <td><spring:message code="${order.state}"/></td>
                    <td>${order.productName} 등</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>

<jsp:include page="/WEB-INF/view/common/footer.jsp" />

</body>
</html>