<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Funds</title>
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
    
    <h2>Add Funds</h2>
    
    <c:if test="${not empty success}">
        <div class="success">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    
    <form action="/addFunds/${investorId}" method="post">
        <label for="amount">Amount:</label>
        <input type="number" id="amount" name="amount" step="0.01" required><br><br>
        <button type="submit">Add Funds</button>
    </form>
</body>
</html>
