<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <a href="/viewProfile/${investorId}">View Profile</a>
                <a href="/updateProfile/${investorId}">Update Profile</a>
                <a href="/logout">Logout</a>
            </div>
        </div>
    </header>
    
    <h2>Basket Details</h2>
    
    <p>Basket Name: ${basket.name}</p>
    <p>Strategy: ${basket.strategy}</p>
    <p>Total Price: ${basket.totalPrice}</p>
    <p>Created By: <a href="/viewBasketsByAdvisor/${investorId}/${basket.createdBy}">${basket.createdBy}</a></p>
    
    <h3>Stocks in Basket</h3>
    <table>
        <thead>
            <tr>
                <th>Symbol</th>
                <th>Company Name</th>
                <th>Closing Price</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="stock" items="${basket.stocks}">
                <tr>
                    <td>${stock.symbol}</td>
                    <td>${stock.companyName}</td>
                    <td>${stock.closingPrice}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
    <h3>Invest in this Basket</h3>
    <form action="/investInBasket/${investorId}/${basket.id}" method="post">
        <label for="units">Units to Invest:</label>
        <input type="number" id="units" name="units" min="1" required><br><br>
        <button type="submit">Invest</button>
    </form>
</body>
</html>

