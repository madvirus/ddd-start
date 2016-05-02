<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

    <form class="form-horizontal">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">주문</h3>
            </div>
            <div class="panel-body">
                <div class="form-group">
                    <label class="col-sm-2 control-label">번호</label>
                    <div class="col-sm-10">
                        <p class="form-control-static">${order.number}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">상태</label>
                    <div class="col-sm-10">
                        <p class="form-control-static"><spring:message code="${order.state}"/></p>
                    </div>
                </div>
            </div>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">주문자</h3>
            </div>
            <div class="panel-body">
                <div class="form-group">
                    <label class="col-sm-2 control-label">이름</label>
                    <div class="col-sm-10">
                        <p class="form-control-static">${order.orderer.name}</p>
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
                    <label class="col-sm-2 control-label">이름</label>
                    <div class="col-sm-10">
                        <p class="form-control-static">${order.shippingInfo.receiver.name}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">연락처</label>
                    <div class="col-sm-10">
                        <p class="form-control-static">${order.shippingInfo.receiver.phone}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">주소</label>
                    <div class="col-sm-10">
                        <p class="form-control-static">
                            ${order.shippingInfo.address.zipCode}
                            ${order.shippingInfo.address.address1}
                            ${order.shippingInfo.address.address2}
                        </p>
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
                    <c:forEach var="ol" items="${order.orderLines}" varStatus="status">
                        <tr>
                            <td>${ol.productName}</td>
                            <td>${ol.price}</td>
                            <td>${ol.quantity}</td>
                            <td>${ol.amounts}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td colspan="3">총합</td>
                        <td>${order.totalAmounts}</td>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </form>
