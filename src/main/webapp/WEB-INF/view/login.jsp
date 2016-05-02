<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
    <jsp:include page="/WEB-INF/view/common/css.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/view/common/navi.jsp" />

<div class="container">
    <form method="post" class="form-inline">
        <div class="form-group">
            <label for="username">아이디</label>
            <input type="text" class="form-control" id="username" name="username">
        </div>
        <div class="form-group">
            <label for="password">암호</label>
            <input type="password" class="form-control" id="password" name="password">
        </div>
        <button type="submit" class="btn btn-default">로그인</button>
    </form>
</div>

<jsp:include page="/WEB-INF/view/common/footer.jsp" />
</body>
</html>