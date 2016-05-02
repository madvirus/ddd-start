<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>관리자 : 주문 목록</title>
    <jsp:include page="/WEB-INF/view/common/css.jsp"/>
</head>
<body>

<jsp:include page="/WEB-INF/view/common/navi.jsp"/>

<div class="container">
    <ol class="breadcrumb">
        <li><a href="/admin/main">관리자</a></li>
        <li class="active"><a href="/admin/orders">주문 관리</a></li>
    </ol>
    <h2>주문 목록</h2>
    <c:if test="${empty orderPage.items}">
        <div class="alert alert-info" role="alert">
            주문 내역이 없습니다.
        </div>
    </c:if>

    <c:if test="${! empty orderPage.items}">
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
            <c:forEach var="order" items="${orderPage.items}">
                <tr>
                    <td><a href="/admin/orders/${order.number}">${order.number}</a></td>
                    <td>${order.totalAmounts}</td>
                    <td><spring:message code="${order.state}"/></td>
                    <td>${order.productName} 등</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${orderPage.totalPages > 0}">
        ${pageMod = orderPage.page % 5 ; ''}
        ${beginPage = pageMod == 0 ? orderPage.page - 5 + 1 : orderPage.page - pageMod + 1 ; ''}
        ${endPage = beginPage + 5 - 1 ; ''}
        <c:if test="${endPage > orderPage.totalPages}">${endPage = orderPage.totalPages ; ''}</c:if>
        <nav>
            <ul class="pagination">
                <c:if test="${beginPage > 5}">
                    <li>
                        <a href="/admin/orders?p=${beginPage - 5}" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:if>
                <c:forEach var="pNo" begin="${beginPage}" end="${endPage}">
                    ${liClass = (pNo == orderPage.page ? 'class="active"' : '') ; ''}
                    <li ${liClass}><a href="/admin/orders?p=${pNo}">${pNo}</a></li>
                </c:forEach>
                <c:if test="${endPage < orderPage.totalPages}">
                    <li>
                        <a href="/admin/orders?p=${beginPage + 5}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </c:if>
</div>

<jsp:include page="/WEB-INF/view/common/footer.jsp"/>

</body>
</html>