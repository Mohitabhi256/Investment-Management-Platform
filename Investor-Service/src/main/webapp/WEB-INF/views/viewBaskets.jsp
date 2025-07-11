<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Baskets</title>
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
    
    <h2>All Baskets</h2>
    
    <div class="baskets-list">
        <c:forEach var="basket" items="${baskets}">
            <div class="basket-item">
                <a href="/viewBasketDetails/${investorId}/${basket.id}">${basket.name}</a>
                <p>Price: ${basket.totalPrice}</p>
                <p>Created by: <a href="/viewBasketsByAdvisor/${investorId}/${basket.createdBy}">${basket.createdBy}</a></p>
                <form action="/investInBasket/${investorId}/${basket.id}" method="post">
                    <label for="units">Units to Invest:</label>
                    <input type="number" id="units" name="units" min="1" required><br><br>
                    <button type="submit">Invest</button>
                </form>
            </div>
        </c:forEach>
    </div>
</body>
</html>
