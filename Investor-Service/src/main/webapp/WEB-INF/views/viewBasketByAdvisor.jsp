<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Baskets by Advisor</title>
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
    
    <h2>Baskets by Advisor: ${advisorName}</h2>
    
    <div class="baskets-list">
        <c:forEach var="basket" items="${baskets}">
            <div class="basket-item">
                <a href="/viewBasketDetails/${investorId}/${basket.id}">${basket.name}</a>
            </div>
        </c:forEach>
    </div>
</body>
</html>
