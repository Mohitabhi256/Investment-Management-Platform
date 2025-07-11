<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Investments</title>
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
    
    <h2>Your Investments</h2>
    <table>
        <thead>
            <tr>
                <th>Basket Name</th>
                <th>Amount Invested</th>
                <th>Current Value</th>
                <th>Investment Date</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="investment" items="${investments}">
                <tr>
                    <td><a href="/viewInvestedBasketDetails/${investorId}/${investment.id}">${investment.basket.name}
                    <td>${investment.amountInvested}</td>
                    <td>${investment.currentValue}</td>
                    <td>${investment.investmentDate}</td>
                    <td>
                        <form action="/redeemInvestment/${investment.id}" method="post">
                            <button type="submit">Redeem</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>

