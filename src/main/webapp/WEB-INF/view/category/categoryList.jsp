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
    <h3>카테고리 목록</h3>
    <ul>
    <c:forEach var="cat" items="${categories}">
        <li><a href="/categories/${cat.id.value}">${cat.name}</a></li>
    </c:forEach>
    </ul>
</div>

<jsp:include page="/WEB-INF/view/common/footer.jsp" />

</body>
</html>