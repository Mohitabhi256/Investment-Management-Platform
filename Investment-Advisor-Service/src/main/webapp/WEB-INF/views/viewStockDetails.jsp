<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Stock Details</title>
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
    
    <h2>Stock Details for <span>${stock.symbol}</span></h2>
    <p>Company Name: <span>${stock.companyName}</span></p>
    <p>Industry: <span>${stock.industry}</span></p>
    <p>Highest Price: <span>${stock.highestPrice}</span></p>
    <p>Lowest Price: <span>${stock.lowestPrice}</span></p>
    <p>Average Price: <span>${stock.averagePrice}</span></p>
    <p>Performance: <span>${stock.percentageChange}</span></p>

    <h2>Prices for the Last 90 Days</h2>
    <table>
        <thead>
            <tr>
                <th>Date</th>
                <th>Price</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="price" items="${stockPrices}">
                <tr>
                    <td>${price.sdate}</td>
                    <td>${price.price}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
