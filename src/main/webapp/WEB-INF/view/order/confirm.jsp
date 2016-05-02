<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>주문 확인</title>
    <jsp:include page="/WEB-INF/view/common/css.jsp" />
    <jsp:include page="/WEB-INF/view/common/js.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/view/common/navi.jsp" />

<div class="container">
    <form:form commandName="orderReq" cssClass="form-horizontal" action="/orders/order">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">주문자</h3>
            </div>
            <div class="panel-body">
                <div class="form-group">
                    <label class="col-sm-2 control-label">이름</label>
                    <div class="col-sm-10">
                        <p class="form-control-static">${orderReq.orderer.name}</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">받는 사람</h3>
            </div>
            <div class="panel-body">
                <div class="form-group">
                    <label for="shippingInfo.receiver.name" class="col-sm-2 control-label">이름
                        <span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span></label>
                    <div class="col-sm-10">
                        <form:input path="shippingInfo.receiver.name" cssClass="form-control" id="shippingInfo.receiver.name" />
                        <form:errors path="shippingInfo.receiver.name"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="shippingInfo.receiver.phone" class="col-sm-2 control-label">연락처
                        <span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span></label>
                    <div class="col-sm-10">
                        <form:input path="shippingInfo.receiver.phone" cssClass="form-control" id="shippingInfo.receiver.phone" />
                        <form:errors path="shippingInfo.receiver.phone"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="shippingInfo.address.zipCode" class="col-sm-2 control-label">우편번호
                        <span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span></label>
                    <div class="col-sm-10">
                        <form:input path="shippingInfo.address.zipCode" cssClass="form-control" id="shippingInfo.address.zipCode" />
                        <form:errors path="shippingInfo.address.zipCode"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="shippingInfo.address.address1" class="col-sm-2 control-label">주소1
                        <span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span></label>
                    <div class="col-sm-10">
                        <form:input path="shippingInfo.address.address1" cssClass="form-control" id="shippingInfo.address.address1" />
                        <form:errors path="shippingInfo.address.address1"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="shippingInfo.address.address2" class="col-sm-2 control-label">주소2
                        <span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span></label>
                    <div class="col-sm-10">
                        <form:input path="shippingInfo.address.address2" cssClass="form-control" id="shippingInfo.address.address2" />
                        <form:errors path="shippingInfo.address.address2"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">주문 상품</h3>
            </div>
            <div class="panel-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th>상품</th>
                        <th>가격</th>
                        <th>개수</th>
                        <th>합</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="totalAmounts" value="${0}" />
                    <c:forEach var="op" items="${orderReq.orderProducts}" varStatus="status">
                        <c:set var="prod" value="${products[status.index]}"/>
                        <tr>
                            <td>${prod.name} ${status.index}</td>
                            <td>${prod.price}</td>
                            <td>${op.quantity}
                                <form:hidden path="orderProducts[${status.index}].productId" />
                                <form:hidden path="orderProducts[${status.index}].quantity" />
                            </td>
                            <td>${prod.price.value * op.quantity} ${totalAmounts = totalAmounts + prod.price.value * op.quantity ; ''}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td colspan="3">총합</td>
                        <td>${totalAmounts}</td>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-body">
                <button type="submit" class="btn btn-primary">구매하기</button>
            </div>
        </div>
    </form:form>
</div>

<jsp:include page="/WEB-INF/view/common/footer.jsp" />
</body>
</html>