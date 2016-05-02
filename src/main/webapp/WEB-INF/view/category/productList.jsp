<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>제품 목록</title>
    <jsp:include page="/WEB-INF/view/common/css.jsp" />
</head>
<body>

<jsp:include page="/WEB-INF/view/common/navi.jsp" />

<div class="container">
    <h2>${productInCategory.category.name}</h2>

    <c:if test="${empty productInCategory.items}">
        상품이 없습니다.
    </c:if>

    <c:if test="${! empty productInCategory.items}">
        <c:forEach var="product" items="${productInCategory.items}">
            <div class="media">
                <div class="media-left">
                    <a href="/products/${product.id}">
                        <img class="media-object" style="width: 64px; height: 64px;" src="${product.image}" alt="${product.name}">
                    </a>
                </div>
                <div class="media-body">
                    <h4 class="media-heading">${product.name}</h4>
                    가격 : ${product.price}
                </div>
            </div>
        </c:forEach>
    </c:if>

</div>

<jsp:include page="/WEB-INF/view/common/footer.jsp" />

</body>
</html>