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
    
    <h3>Investment Details</h3>
    <p>Units Bought: ${units}</p>
    <p>Price Invested: ${investment.amountInvested}</p>
    <p>Current Value: ${investment.currentValue}</p>
    <p>%Change: ${((investment.currentValue - investment.amountInvested)*100)/ investment.amountInvested}
    <h3>Stocks in Basket</h3>
    <table>
        <thead>
            <tr>
                <th>Symbol</th>
                <th>Company Name</th>
                <th>Investment Day Value</th>
                <th>Current Day Value</th>
                <th>% Change</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="stock" items="${basket.stocks}">
                <tr>
                    <td>${stock.symbol}</td>
                    <td>${stock.companyName}</td>
                    <td>${investmentDayPrices[stock]}</td>
                    <td>${currentDayPrices[stock]}</td>
                    <td>
                        <c:choose>
                            <c:when test="${currentDayPrices[stock] > investmentDayPrices[stock]}">
                                <span style="color: green;">+${((currentDayPrices[stock] - investmentDayPrices[stock]) * 100) / investmentDayPrices[stock]}%</span>
                            </c:when>
                            <c:otherwise>
                                <span style="color: red;">${((currentDayPrices[stock] - investmentDayPrices[stock])  * 100)/ investmentDayPrices[stock]}%</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <!--  <h2>Basket Details</h2>
    
    <p>Basket Name: ${basket.name}</p>
    <p>Strategy: ${basket.strategy}</p>
    <p>Total Price: ${basket.totalPrice}</p>
    <p>Created By: <a href="/viewBasketsByAdvisor/${investorId}/${basket.createdBy}">${basket.createdBy}</a></p>
    
    <h3>Investment Details</h3>
    <p>Units Bought: ${units}</p>
    <p>Price Invested: ${investment.amountInvested}</p>
    <p>Current Value: ${investment.currentValue}</p>
    <p>% Change : ${((investment.currentValue - investment.amountInvested)*100)/investment.amountInvested}</p>
    <h3>Stocks in Basket</h3>
    <table>
        <thead>
            <tr>
                <th>Symbol</th>
                <th>Company Name</th>
                <th>Investment Day Value</th>
                <th>Current Day Value</th>
                <th>% Change</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="stock" items="${basket.stocks}">
                <tr>
                    <td>${stock.symbol}</td>
                    <td>${stock.companyName}</td>
                    <td>${stock.closingPrice}</td>
                    <td>${stock.closingPrice}</td>
                    <td>
                        <c:choose>
                            <c:when test="${stock.closingPrice >= stock.closingPrice}">
                                <span style="color: green;">+${(stock.closingPrice - stock.closingPrice) / stock.closingPrice * 100}%</span>
                            </c:when>
                            <c:otherwise>
                                <span style="color: red;">${(stock.closingPrice - stock.closingPrice) / stock.closingPrice * 100}%</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>-->
</body>
</html>
