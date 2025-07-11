<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Basket Details</title>
    <style><%@include file="/WEB-INF/style/style.css"%></style>
</head>
<body>
    <header>
        <div class="company-name">I-Invest</div>
        <div class="profile-dropdown">
            <button class="dropbtn">Profile</button>
            <div class="dropdown-content">
                <a href="/viewProfile/${advisorId}">View Profile</a>
                <a href="/updateProfile/${advisorId}">Update Profile</a>
                <a href="/logout">Logout</a>
            </div>
        </div>
    </header>
    
    <h2>Basket Details</h2>
    
    <p>Basket Name: ${basket.name}</p>
    <p>Strategy: ${basket.strategy}</p>
    <p>Total Price: ${basket.totalPrice}</p>
    
    <h3>Stocks in Basket</h3>
    <table>
        <thead>
            <tr>
                <th>Symbol</th>
                <th>Company Name</th>
                <th>Closing Price</th>
                <th>Units</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="stock" items="${basket.stocks}">
                <tr>
                    <td>${stock.symbol}</td>
                    <td>${stock.companyName}</td>
                    <td>${stock.closingPrice}</td>
                    <td>${basket.stockUnits[stock]}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
