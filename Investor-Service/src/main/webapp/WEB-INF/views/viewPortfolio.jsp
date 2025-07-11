<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Portfolio</title>
    <style><%@include file="/WEB-INF/style/style.css"%></style>
</head>
<body>
    <header>
        <div class="company-name">I-Invest</div>
        <div class="profile-dropdown">
            <button class="dropbtn">Profile</button>
            <div class="dropdown-content">
                <a href="/viewProfile/${investor.id}">View Profile</a>
                <a href="/updateProfile/${investor.id}">Update Profile</a>
                <a href="/logout">Logout</a>
            </div>
        </div>
    </header>
    
    <h2>Portfolio</h2>
    
    <p>Investor Name: ${investor.name}</p>
    <p>Email: ${investor.email}</p>
    <p>Cash Balance: ${investor.cashBalance}</p>
    
    <h3>Invested Baskets</h3>
    <table>
        <thead>
            <tr>
                <th>Basket Name</th>
                <th>Amount Invested</th>
                <th>Current Value</th>
                <th>% Profit/Loss</th>
                <th>Details</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="investment" items="${investments}">
                <tr>
                    <td>${investment.basket.name}</td>
                    <td>${investment.amountInvested}</td>
                    <td>${investment.currentValue}</td>
                    <td>
                        <c:choose>
                            <c:when test="${investment.currentValue > investment.amountInvested}">
                                <span style="color: green;">+${(investment.currentValue - investment.amountInvested) / investment.amountInvested * 100}%</span>
                            </c:when>
                            <c:otherwise>
                                <span style="color: red;">${(investment.currentValue - investment.amountInvested) / investment.amountInvested * 100}%</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td><a href="/viewInvestedBasketDetails/${investorId}/${investment.id}">View Details</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
    <h3>Summary</h3>
    <p>Total Amount Invested: ${totalAmountInvested}</p>
    <p>Total Current Value: ${totalCurrentValue}</p>
    <p>Total % Profit/Loss: 
        <c:choose>
            <c:when test="${totalCurrentValue > totalAmountInvested}">
                <span style="color: green;">+${(totalCurrentValue - totalAmountInvested) / totalAmountInvested * 100}%</span>
            </c:when>
            <c:otherwise>
                <span style="color: red;">${(totalCurrentValue - totalAmountInvested) / totalAmountInvested * 100}%</span>
            </c:otherwise>
        </c:choose>
    </p>
</body>
</html>
