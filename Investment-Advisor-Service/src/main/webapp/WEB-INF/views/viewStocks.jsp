<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Stocks</title>
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
    
    <h2>View Stocks</h2>
    
    <div class="stock-list">
        <table>
            <thead>
                <tr>
                    <th>Symbol</th>
                    <th>Company Name</th>
                    <th>Closing Price</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="stock" items="${stocks}">
                    <tr>
                        <td>${stock.symbol}</td>
                        <td>${stock.companyName}</td>
                        <td>${stock.closingPrice}</td>
                        <td><a href="${pageContext.request.contextPath}/viewStock/${id}/${stock.symbol}">View Details</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
