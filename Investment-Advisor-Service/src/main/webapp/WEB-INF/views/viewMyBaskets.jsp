<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>View My Baskets</title>
    <style><%@include file="/WEB-INF/style/style.css"%></style>
</head>
<body>
    <header>
        <div class="company-name">I-Invest</div>
        <div class="profile-dropdown">
            <button class="dropbtn">Profile</button>
            <div class="dropdown-content">
                <a href="/viewProfile">View Profile</a>
                <a href="/updateProfile">Update Profile</a>
                <a href="/logout">Logout</a>
            </div>
        </div>
    </header>
    
    <h2>View My Baskets</h2>
    
    <table>
        <thead>
            <tr>
                <th>Basket Name</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="basket" items="${baskets}">
                <tr>
                    <td>${basket.name}</td>
                    <td><a href="/basketDetails/${advisorId}/${basket.id}">View Details</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
